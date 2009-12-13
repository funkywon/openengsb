/**

   Copyright 2009 OpenEngSB Division, Vienna University of Technology

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE\-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
 */
package org.openengsb.mantis.util;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.Properties;

import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;

import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.xpath.CachedXPathAPI;
import org.openengsb.issues.common.api.exceptions.IssueDomainException;
import org.openengsb.issues.common.pojos.IssueDataType;
import org.openengsb.mantis.pojos.UserCredential;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.IssueNoteData;

public class MantisUtil {

	private static final CachedXPathAPI XPATH = new CachedXPathAPI();
	

	public static Properties load(String propsName) throws IOException {
		Properties props = new Properties();
		URL url = ClassLoader.getSystemResource(propsName);
		props.load(url.openStream());
		return props;
	}


	

	
	private static IssueData convertIssueData(IssueDataType issue) {
		IssueData issueData = new IssueData();
		
		issueData.setId(issue.getId());
		issueData.setProject(issue.getProject());
		issueData.setSummary(issue.getSummary());
		issueData.setVersion(issue.getVersion());
		issueData.setDescription(issue.getDescription());
		issueData.setPriority(issue.getPriority());
		issueData.setSeverity(issue.getSeverity());
		issueData.setHandler(issue.getHandler());
		issueData.setDate_submitted(issue.getDateSubmitted().toGregorianCalendar());
		issueData.setLast_updated(issue.getLastUpdated().toGregorianCalendar());
		issueData.setStatus(issue.getStatus());
		issueData.setResolution(issue.getResolution());
		issueData.setReporter(issue.getReporter());
		issueData.setNotes((IssueNoteData[])issue.getNotes().getIssueNoteData().toArray());
		return issueData;
	}
	
	@SuppressWarnings("unchecked")
	public static IssueData extractIssueData(NormalizedMessage message)
			throws IssueDomainException, JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance("org.openengsb.issues.common.pojos");
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		JAXBElement<IssueDataType> issueData= (JAXBElement<IssueDataType>) unmarshaller
				.unmarshal(message.getContent());
		IssueDataType issue= issueData.getValue();
		return MantisUtil.convertIssueData(issue);
		
		
		/*
		try {

			issueNode = extractSingleNode(message, ISSUEDATA_PARAM);
		} catch (ParserConfigurationException e) {
			throw new IssueDomainException(e.getMessage());
		} catch (IOException e) {
			throw new IssueDomainException(e.getMessage());
		} catch (SAXException e) {
			throw new IssueDomainException(e.getMessage());
		} catch (TransformerException e) {
			throw new IssueDomainException(e.getMessage());
		} catch (MessagingException e) {
			throw new IssueDomainException(e.getMessage());
		}
		String description;
		String summary;
		String project;
		int projectId;
		String severity;
		String priority;
		String id;

		java.util.Calendar last_updated;
		biz.futureware.mantisconnect.AccountData reporter;
		java.util.Calendar date_submitted;
		biz.futureware.mantisconnect.AccountData handler;
		biz.futureware.mantisconnect.ObjectRef resolution;
		biz.futureware.mantisconnect.AttachmentData[] attachments;
		biz.futureware.mantisconnect.IssueNoteData[] notes;
		biz.futureware.mantisconnect.CustomFieldValueForIssueData[] custom_fields;
		String version;
		String os;

		try {

			id = MantisUtil.extractStringParameter(issueNode, "/id");
			description = MantisUtil.extractStringParameter(message,
					"//description");
			summary = MantisUtil.extractStringParameter(message, "//summary");
			project = MantisUtil.extractStringParameter(message, "//project");

			projectId = Integer.parseInt((MantisUtil.extractStringParameter(
					message, "//projectid")));

			severity = MantisUtil.extractStringParameter(message, "//severity");
			priority = MantisUtil.extractStringParameter(message, "//priority");
			version = MantisUtil.extractStringParameter(message, "//version");

			os = MantisUtil.extractStringParameter(message, "//os");

		} catch (DOMException e) {
			throw new IssueDomainException("DomException: " + e.getMessage());
		} catch (MessagingException e) {
			throw new IssueDomainException("MessagingException: "
					+ e.getMessage());
		} catch (TransformerException e) {
			throw new IssueDomainException("TransformerException: "
					+ e.getMessage());
		} catch (ParserConfigurationException e) {
			throw new IssueDomainException("ParserConfigurationException: "
					+ e.getMessage());
		} catch (IOException e) {
			throw new IssueDomainException("IOException: " + e.getMessage());
		} catch (SAXException e) {
			throw new IssueDomainException("SAXException: " + e.getMessage());
		}

		if (description == null || summary == null || project == null) {
			throw new IssueDomainException(
					"One or more required field(s) is(are) not set.");
		}

		IssueData data = new IssueData();

		// Required fields
		data.setDescription(description);
		data.setSummary(summary);

		data.setId(new BigInteger(id));
		data.setProject(new ObjectRef(BigInteger.valueOf(projectId), project));
		data.setPriority(new ObjectRef(BigInteger.valueOf(0), priority));
		data.setSeverity(new ObjectRef(BigInteger.valueOf(0), severity));

		data.setVersion(version);
		data.setOs(os);

		return data;*/
	}

	/**
	 * Convenience method to extract a single Node from a normalized message,
	 * indicated by an x-path.
	 * 
	 * @param inMessage
	 *            The normalized message.
	 * @param xPath
	 *            The x-path
	 * @return The Extracted Node
	 * @throws MessagingException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static Node extractSingleNode(NormalizedMessage inMessage,
			String xPath) throws MessagingException, TransformerException,
			ParserConfigurationException, IOException, SAXException {
		Node rootNode = getRootNode(inMessage);
		if (rootNode == null) {
			return null;
		} else {
			return MantisUtil.XPATH.selectSingleNode(rootNode, xPath);
		}
	}

	/**
	 * Convenience method to extract a NodeList from a normalized message,
	 * indicated by an x-path.
	 * 
	 * @param inMessage
	 *            The normalized message.
	 * @param xPath
	 *            The x-path
	 * @return The extracted NodeList
	 * @throws MessagingException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static NodeList extractNodeList(NormalizedMessage inMessage,
			String xPath) throws MessagingException, TransformerException,
			ParserConfigurationException, IOException, SAXException {
		Node rootNode = getRootNode(inMessage);
		if (rootNode == null) {
			return null;
		} else {
			return MantisUtil.XPATH.selectNodeList(rootNode, xPath);
		}
	}

	/**
	 * Convenience method to extract parameter (attribute) from a normalized
	 * message, indicated by an x-path.
	 * 
	 * @param inMessage
	 *            The normalized message.
	 * @param xPath
	 *            The x-path.
	 * @return The extracted value.
	 * @throws MessagingException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static String extractStringParameter(NormalizedMessage inMessage,
			String xpath) throws MessagingException, TransformerException,
			ParserConfigurationException, IOException, SAXException {
		// get parameter
		Node node = extractSingleNode(inMessage, xpath);

		// validate them
		if (node == null) {
			return null;
		} else {
			return node.getNodeValue();
		}
	}

	public static String extractStringParameter(Node inNode, String xpath)
			throws TransformerException {
		// get parameter
		Node node = MantisUtil.XPATH.selectSingleNode(inNode, xpath);

		// validate them
		if (node == null) {
			return null;
		} else {
			return node.getNodeValue();
		}
	}

	/**
	 * Extracts to root-Node (actually XML-Element) from a NormalizedMessage.
	 * This helper is needed, since, depending on the way the request was sent
	 * (via the domain, or directly) Either a Document-Node or Element-Node is
	 * the message's root. To be able to apply the same XPaths either way, this
	 * helper "normalizes" the root-Node.
	 * 
	 * @param message
	 * @return
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @throws TransformerException
	 */
	private static Node getRootNode(NormalizedMessage message)
			throws ParserConfigurationException, IOException, SAXException,
			TransformerException {
		SourceTransformer sourceTransformer = new SourceTransformer();
		DOMSource messageXml = sourceTransformer.toDOMSource(message
				.getContent());

		Node rootNode = messageXml.getNode();

		if (rootNode instanceof Document) {
			return rootNode.getFirstChild();
		} else {
			return rootNode;
		}
	}

	public static UserCredential extractUserCredentials(NormalizedMessage in)
			throws DOMException, MessagingException, TransformerException,
			ParserConfigurationException, IOException, SAXException {
		org.openengsb.mantis.pojos.UserCredential uc = new UserCredential();
		String user = extractSingleNode(in, "//username").getNodeValue();
		String pass = extractSingleNode(in, "//password").getNodeValue();
		uc.setPassword(pass);
		uc.setUser(user);
		return uc;

	}

}
