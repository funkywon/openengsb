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
import java.net.URL;
import java.util.Properties;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.xpath.CachedXPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MantisUtil {
	private static final CachedXPathAPI XPATH = new CachedXPathAPI();

	public static Properties load(String propsName) throws IOException {
		Properties props = new Properties();
		URL url = ClassLoader.getSystemResource(propsName);
		props.load(url.openStream());
		return props;
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
}
