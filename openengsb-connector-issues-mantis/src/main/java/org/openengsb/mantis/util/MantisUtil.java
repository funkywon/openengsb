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

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.Properties;

import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;

import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.xpath.CachedXPathAPI;
import org.openengsb.issues.common.api.exceptions.IssueDomainException;
import org.openengsb.mantis.pojos.UserCredential;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.ObjectRef;

public class MantisUtil {

	private static final CachedXPathAPI XPATH = new CachedXPathAPI();
	private static final String ISSUEDATA_PARAM = "issueData";

	public static Properties load(String propsName) throws IOException {
        Properties props = new Properties();
        URL url = ClassLoader.getSystemResource(propsName);
        props.load(url.openStream());
        return props;
    }
	
	public static IssueData extractIssueData(NormalizedMessage message)
			throws IssueDomainException {
		
		try {
			
			Node issueNode = extractSingleNode(message,ISSUEDATA_PARAM);
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
		String category;
		String description;
		String summary;
		String project;
		int projectId;
		String reproducibility;
		String severity;
		String priority;
		String id;
		biz.futureware.mantisconnect.ObjectRef view_state;

//		java.util.Calendar last_updated;
//		biz.futureware.mantisconnect.AccountData reporter;
//		java.util.Calendar date_submitted;
//		java.math.BigInteger sponsorship_total;
//		biz.futureware.mantisconnect.AccountData handler;
//		biz.futureware.mantisconnect.ObjectRef projection;
//		biz.futureware.mantisconnect.ObjectRef eta;
//		biz.futureware.mantisconnect.ObjectRef resolution;
//		
//		biz.futureware.mantisconnect.AttachmentData[] attachments;
//		biz.futureware.mantisconnect.RelationshipData[] relationships;
//		biz.futureware.mantisconnect.IssueNoteData[] notes;
//		biz.futureware.mantisconnect.CustomFieldValueForIssueData[] custom_fields;

		
		String version;
		String build;
		String platform;
		String os;
		String os_build;
		String fixed_in_version;
		String target_version;
		String steps_to_reproduce;
		String additional_information;
		
		
		try {
			category = MantisUtil.extractStringParameter(message, "//category");
			id = MantisUtil.extractStringParameter(message, "//id");
			description = MantisUtil.extractStringParameter(message, "//description");
			summary = MantisUtil.extractStringParameter(message, "//summary");
			project = MantisUtil.extractStringParameter(message, "//project");
			
			projectId = Integer.parseInt((MantisUtil.extractStringParameter(message,
					"//projectid")));
			reproducibility = MantisUtil.extractStringParameter(message,
					"//reproducibility");
			severity = MantisUtil.extractStringParameter(message, "//severity");
			priority = MantisUtil.extractStringParameter(message, "//priority");
			version = MantisUtil.extractStringParameter(message, "//version");
			build = MantisUtil.extractStringParameter(message, "//build");
			platform = MantisUtil.extractStringParameter(message, "//platform");
			os = MantisUtil.extractStringParameter(message, "//os");
			os_build = MantisUtil.extractStringParameter(message, "//os_build");
			fixed_in_version = MantisUtil.extractStringParameter(message, "//fixed_in_version");
			target_version = MantisUtil.extractStringParameter(message, "//target_version");
			steps_to_reproduce = MantisUtil.extractStringParameter(message, "//steps_to_reproduce");
			additional_information = MantisUtil.extractStringParameter(message, "//additional_information");
			
		
		} catch (DOMException e) {
			throw new IssueDomainException("DomException: "+e.getMessage());
		} catch (MessagingException e) {
			throw new IssueDomainException("MessagingException: "+e.getMessage());
		} catch (TransformerException e) {
			throw new IssueDomainException("TransformerException: "+e.getMessage());
		} catch (ParserConfigurationException e) {
			throw new IssueDomainException("ParserConfigurationException: "+e.getMessage());
		} catch (IOException e) {
			throw new IssueDomainException("IOException: "+e.getMessage());
		} catch (SAXException e) {
			throw new IssueDomainException("SAXException: "+e.getMessage());
		}

		if (category == null || description == null || summary == null
				|| project == null) {
			throw new IssueDomainException(
					"One or more required field(s) is(are) not set.");
		}

		IssueData data = new IssueData();
		

		// Required fields
		data.setCategory(category);
		data.setDescription(description);
		data.setSummary(summary);
		
		data.setId(new BigInteger(id));
		data.setProject(new ObjectRef(BigInteger.valueOf(projectId), project));
		data.setPriority(new ObjectRef(BigInteger.valueOf(0), priority));
		data.setSeverity(new ObjectRef(BigInteger.valueOf(0), severity));
		data.setReproducibility(new ObjectRef(BigInteger.valueOf(0),
				reproducibility));

		data.setVersion(version);
		data.setBuild(build);
		data.setPlatform(platform);
		data.setOs(os);
		data.setOs_build(os_build);
		data.setFixed_in_version(fixed_in_version);
		data.setTarget_version(target_version);
		data.setSteps_to_reproduce(steps_to_reproduce);
		data.setAdditional_information(additional_information);
		
		return data;
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

	public static UserCredential extractUserCredentials(NormalizedMessage in) throws DOMException, MessagingException, TransformerException, ParserConfigurationException, IOException, SAXException {
		org.openengsb.mantis.pojos.UserCredential uc = new UserCredential();
		String user = extractSingleNode(in, "//username").getNodeValue();
		String pass = extractSingleNode(in, "//password").getNodeValue();
		uc.setPassword(pass);
		uc.setUser(user);
		return uc;
		
	}

}
