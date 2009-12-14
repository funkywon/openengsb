/**

   Copyright 2009 OpenEngSB Division, Vienna University of Technology

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
 */

package org.openengsb.mantis.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.jbi.JBIException;
import javax.jbi.messaging.InOut;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.EngineConfigurationFactoryFinder;
import org.apache.axis.configuration.SimpleProvider;
import org.apache.axis.transport.http.CommonsHTTPSender;
import org.apache.servicemix.client.DefaultServiceMixClient;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.jbi.jaxp.StringSource;
import org.apache.servicemix.tck.SpringTestSupport;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import biz.futureware.mantisconnect.MantisConnectLocator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:testBeans.xml" })
public class IssueCommandTest extends SpringTestSupport {

	DefaultServiceMixClient client;

	private static final String USER_KEY = "user";
	private static final String PASSWORD_KEY = "password";
	private static final String HOST_KEY = "host";
	private static final String PROPS_FILE = "config1.properties";
	private static final String ISSUE_SERVICE_NAME = "mantis";
	private static final String TEST_NAMESPACE = "urn:test";

	private static String USER;
	private static String PASS;
	private static String HOST;

	private MantisConnectLocator locator;
	
	

	@Override
	protected AbstractXmlApplicationContext createBeanFactory() {
		return new ClassPathXmlApplicationContext("testXBean.xml");
	}

	private DefaultServiceMixClient createClient() throws JBIException {
		return new DefaultServiceMixClient(this.jbi);
	}

	private static Properties load(String propsName) throws IOException {
		Properties props = new Properties();
		URL url = ClassLoader.getSystemResource(propsName);
		props.load(url.openStream());
		return props;
	}

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {

		Properties props;
		props = load(PROPS_FILE);
		USER = props.getProperty(USER_KEY);
		PASS = props.getProperty(PASSWORD_KEY);
		HOST = props.getProperty(HOST_KEY);
	}

	@Before
	public void setUp() throws IOException, JBIException, Exception {
		super.setUp();
		initParams();
		EngineConfiguration engine = EngineConfigurationFactoryFinder
				.newFactory().getClientEngineConfig();
		SimpleProvider provider = new SimpleProvider(engine);
		provider.deployTransport("http", new CommonsHTTPSender());
		locator = new MantisConnectLocator(provider);
		locator.setMantisConnectPortEndpointAddress(HOST);
		this.client = createClient();
	}

	private void initParams() {
		Element root = DocumentHelper.createElement("issue_create_message");
		Element accountData = root.addElement("account_data");
		accountData.addElement("username").setText(USER);
		accountData.addElement("password").setText(PASS);

	}

	@AfterClass
	public static void tearDownAfterClass() {

	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	private InOut createInOutMessage(String message) throws MessagingException {
		InOut inOut = this.client.createInOutExchange();
		inOut.setService(new QName(IssueCommandTest.TEST_NAMESPACE,
				IssueCommandTest.ISSUE_SERVICE_NAME));
		inOut.getInMessage().setContent(new StringSource(message));
		return inOut;
	}

	private InOut createInOutMessage(Source message) throws MessagingException {
		InOut inOut = this.client.createInOutExchange();
		inOut.setService(new QName(IssueCommandTest.TEST_NAMESPACE,
				IssueCommandTest.ISSUE_SERVICE_NAME));
		inOut.getInMessage().setContent(message);
		return inOut;
	}

	private Document sendMessageAndParseResponse(String message)
			throws MessagingException, IOException, SAXException,
			TransformerException {
		InOut inout = createInOutMessage(message);
		this.client.sendSync(inout);
		return parseResponse(inout.getOutMessage());
	}

	private Document sendMessageAndParseResponse(Source message) throws MessagingException, IOException, SAXException,
			TransformerException {
		InOut inout = createInOutMessage(message);
		this.client.sendSync(inout);
		return parseResponse(inout.getOutMessage());
	}

	private Document sendMessageAndParseResponse(Document doc)
			throws MessagingException, IOException, SAXException,
			TransformerException {
		return sendMessageAndParseResponse(doc.asXML());
	}

	private Document parseResponse(NormalizedMessage outMessage)
			throws TransformerException, IOException, SAXException {
		SourceTransformer st = new SourceTransformer();
		SAXSource rSource = st.toSAXSource(outMessage.getContent());
		SAXReader saxreader = new SAXReader(rSource.getXMLReader());
		try {
			return saxreader.read(rSource.getInputSource());
		} catch (DocumentException e) {
			StreamSource rawSource = st.toStreamSource(outMessage.getContent());
			BufferedReader br = new BufferedReader(rawSource.getReader());
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			this.log.error(e.getMessage());
			this.log.error(sb.toString());
			return null;
		}
	}

	private Document createDeleteDocument(int id) {
		Element root = DocumentHelper.createElement("issue_delete_message");
        Element accountdata = root.addElement("account_data");
        accountdata.addElement("username").setText(USER);
        accountdata.addElement("password").setText(PASS);
        root.addElement("issue_id").setText(""+id);
        return DocumentHelper.createDocument(root);
	}
	
	@Ignore
	@Test
	public void createIssue() throws ServiceException, MessagingException, FileNotFoundException, IOException, SAXException, TransformerException {
		Document response = sendMessageAndParseResponse(new StreamSource(new FileInputStream(
				new File("createIssueTest.xml"))));
		Element root = response.getRootElement();
		assertEquals("issue_create_messageResponse",root.getName());
		Element issueIdElement = root.element("issue_id");
		assertNotNull(issueIdElement);
		Integer id = Integer.valueOf(issueIdElement.getText());
		assertNotNull(id);
	}

	@Test
	public void deleteIssue() throws ServiceException, MessagingException, FileNotFoundException, IOException, SAXException, TransformerException {
		Document response = sendMessageAndParseResponse(new StreamSource(new FileInputStream(
				new File("createIssueTest.xml"))));
		Element root = response.getRootElement();
		assertEquals("issue_create_messageResponse",root.getName());
		Element issueIdElement = root.element("issue_id");
		assertNotNull(issueIdElement);
		Integer id = Integer.valueOf(issueIdElement.getText());
		assertNotNull(id);
		
		Document deleteIssueDocument = createDeleteDocument(id);
		
		response = sendMessageAndParseResponse(deleteIssueDocument);
		root = response.getRootElement();
		assertEquals("issue_delete_messageResponse",root.getName());
		Element message = root.element("message");
		assertEquals("success",message.getText());
	}

}