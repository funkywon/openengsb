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
package org.openengsb.mantis.util;

import java.io.BufferedReader;
import java.io.IOException;

import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXSource;

import org.apache.log4j.Logger;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;


import org.xml.sax.SAXException;

import biz.futureware.mantisconnect.IssueData;

public class XmlParserFunctions {

	private static SourceTransformer sourceTransformer = new SourceTransformer();

	private static Logger logger = Logger.getLogger(XmlParserFunctions.class);

	
	public static String parseIssueGetResponse(IssueData data) {
		return "";
	}
	
	public static String parseIssueGetResponse(String errormsg) {
		return "";
	}
	
	public static IssueOpType getMessageType(NormalizedMessage in) throws IOException, SAXException, TransformerException,
			DocumentException {
		Document doc = readMessage(in);
		
		if (doc.getRootElement().getName().equals("CreateIssueRequestMessage")) {
			return IssueOpType.CREATE_ISSUE;
			
		} else if (doc.getRootElement().getName().equals(
				"UpdateIssueRequestMessage")) {
			return IssueOpType.UPDATE_ISSUE;
			
		} else if (doc.getRootElement().getName().equals(
				"DelteIssueRequestMessage")) {
			return IssueOpType.DELETE_ISSUE;
			
		} else {
			throw new RuntimeException("root element could not be sorted..."
					+ doc.getRootElement().getName());
		}
	}

	private static Document readMessage(NormalizedMessage msg)
			throws IOException, SAXException, TransformerException,
			DocumentException {
		SAXSource saxSource = XmlParserFunctions.sourceTransformer
				.toSAXSource(msg.getContent());
		XmlParserFunctions.logger.info("converted to saxsource");
		SAXReader reader = new SAXReader(saxSource.getXMLReader());
		XmlParserFunctions.logger.info("saxreader initialized");
		reader.setValidation(false);
		Document doc = reader.read(saxSource.getInputSource());
		return doc;
	}

	public static String prepareCreateIssueResponse(String response) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String prepareDeleteIssueResponse(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String prepareUpdateIssueResponse(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
