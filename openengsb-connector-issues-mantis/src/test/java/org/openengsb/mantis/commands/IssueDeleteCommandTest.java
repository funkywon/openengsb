package org.openengsb.mantis.commands;

import static org.junit.Assert.*;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Properties;
import javax.xml.rpc.ServiceException;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.EngineConfigurationFactoryFinder;
import org.apache.axis.configuration.SimpleProvider;
import org.apache.axis.transport.http.CommonsHTTPSender;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openengsb.mantis.util.MantisUtil;
import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.MantisConnectLocator;
import biz.futureware.mantisconnect.MantisConnectPortType;
import biz.futureware.mantisconnect.ObjectRef;

public class IssueDeleteCommandTest {
	private static final String USER_KEY = "user";
	private static final String PASSWORD_KEY = "password";
	private static final String HOST_KEY = "host";
	private static final String PROPS_FILE = "config.properties";
	
	private static String USER;
	private static String PASS;
	private static String HOST;
	
	MantisConnectLocator locator;
	InputStream istream;

	@BeforeClass
	public static void setUpBeforeClass() throws  IOException{
		Properties props;
		props = MantisUtil.load(PROPS_FILE);
		
		USER = props.getProperty(USER_KEY);
		PASS = props.getProperty(PASSWORD_KEY);
		HOST = props.getProperty(HOST_KEY);
	}

	@Before
	public void setUp() throws IOException {
		EngineConfiguration engine = EngineConfigurationFactoryFinder.newFactory().getClientEngineConfig();
        SimpleProvider provider = new SimpleProvider(engine);
        provider.deployTransport("http", new CommonsHTTPSender());
        
		locator = new MantisConnectLocator(provider);
		locator.setMantisConnectPortEndpointAddress(HOST);
	}

	@Test
	public void deleteIssue() throws ServiceException{
		MantisConnectPortType porttype;
		
		porttype = locator.getMantisConnectPort();
		
		IssueData data1 = new IssueData();
		data1.setCategory("cat1");
		data1.setDescription("This is a unit test.");
		data1.setSummary("this is the test summary of data.");
		data1.setProject(new ObjectRef(new BigInteger("1"), "engsb"));
		
		BigInteger issueId=null;
		try {
			issueId = porttype.mc_issue_add(USER, PASS, data1);
			boolean before = porttype.mc_issue_exists(USER, PASS, issueId);
			
			boolean deleteSuccess = porttype.mc_issue_delete(USER, PASS, issueId);
			boolean after = porttype.mc_issue_exists(USER, PASS, issueId);
			assertTrue(before);
			assertTrue(deleteSuccess);
			assertFalse(after);
		} catch (RemoteException e) {
			fail();
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	@After
	public void tearDown() throws Exception {
	}
}