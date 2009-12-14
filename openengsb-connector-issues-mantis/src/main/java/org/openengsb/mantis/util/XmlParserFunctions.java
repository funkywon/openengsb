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

import java.io.IOException;

import javax.jbi.messaging.NormalizedMessage;
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

	private final static String SCHEMA_ISSUECREATE_ELEMENT= "issue_create_message";
	private final static String SCHEMA_ISSUEUPDATE_ELEMENT= "issue_update_message";
	private final static String SCHEMA_ISSUEDELETE_ELEMENT= "issue_delete_message";
	private final static String SCHEMA_ISSUEGET_ELEMENT= "issue_get_message";
	
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
		
		if (doc.getRootElement().getName().equals(XmlParserFunctions.SCHEMA_ISSUECREATE_ELEMENT)) {
			return IssueOpType.CREATE_ISSUE;
			
		} else if (doc.getRootElement().getName().equals(
				XmlParserFunctions.SCHEMA_ISSUEUPDATE_ELEMENT)) {
			return IssueOpType.UPDATE_ISSUE;
			
		} else if (doc.getRootElement().getName().equals(
				XmlParserFunctions.SCHEMA_ISSUEDELETE_ELEMENT)) {
			return IssueOpType.DELETE_ISSUE;
			
		} else if (doc.getRootElement().getName().equals(
				XmlParserFunctions.SCHEMA_ISSUEGET_ELEMENT)) {
			return IssueOpType.GET_ISSUE;
		}
		else {
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
		//<issue_create_messageResponse>
		StringBuilder builder = new StringBuilder();
		builder.append("<issue_create_messageResponse>");
		builder.append("<issue_id>");
		builder.append(response);
		builder.append("</issue_id>");
		builder.append("</issue_create_messageResponse>");
		return builder.toString();
	}

	public static String prepareDeleteIssueResponse(String response) {
		StringBuilder builder = new StringBuilder();
		builder.append("<issue_delete_messageResponse>");
		builder.append("<message>");
		builder.append(response);
		builder.append("</message>");
		builder.append("</issue_delete_messageResponse>");
		return builder.toString();
	}
	
	public static String prepareGetIssueResponse(String response) {
		StringBuilder builder = new StringBuilder();
		builder.append("<issue_get_messageResponse>");
		builder.append(response);
		builder.append("</issue_get_messageResponse>");
		return builder.toString();
	}
	

	public static String prepareUpdateIssueResponse(String response) {
		StringBuilder builder = new StringBuilder();
		builder.append("<issue_update_messageResponse>");
		builder.append(response);
		builder.append("</issue_update_messageResponse>");
		return builder.toString();
	}

	

}
