/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openengsb.mantis.commands;

import java.io.IOException;

import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.openengsb.issues.common.api.IssueHandler;
import org.openengsb.issues.common.api.exceptions.IssueDomainException;
import org.openengsb.mantis.pojos.UserCredential;
import org.openengsb.mantis.util.MantisUtil;
import org.openengsb.mantis.util.XmlParserFunctions;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import biz.futureware.mantisconnect.IssueData;

public class IssueGetCommand implements IssueCommand {
	
	IssueHandler handler;
	Log log;
	
	public IssueGetCommand(IssueHandler handler, Log log) {
		this.handler = handler;
		this.log = log;
	}
	
	@SuppressWarnings("unused")
	private IssueGetCommand() {
		
	}

	@Override
	public String execute(NormalizedMessage in) throws IssueDomainException {
		IssueData data;
		UserCredential userCred;
		try {
			data = MantisUtil.extractIssueData(in);
			userCred = MantisUtil.extractUserCredentials(in);
		} catch (DOMException e) {
			e.printStackTrace();
			throw new IssueDomainException("DomException: "+e.getMessage());
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new IssueDomainException("MessagingException: "+e.getMessage());
		} catch (TransformerException e) {
			e.printStackTrace();
			throw new IssueDomainException("TransformerException: "+e.getMessage());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw new IssueDomainException("ParserConfigurationException: "+e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new IssueDomainException("IOException: "+e.getMessage());
		} catch (SAXException e) {
			e.printStackTrace();
			throw new IssueDomainException("SAXException: "+e.getMessage());
		} catch (JAXBException e) {
			throw new IssueDomainException("JAXBException: "+e.getMessage());
		}
		
		IssueData returnData;
		try {
			returnData = handler.getIssue(data.getId(), userCred.getUser(), userCred.getPassword());
		} catch(IssueDomainException e) {
			return XmlParserFunctions.parseIssueGetResponse("Error getting isssue: "+e.getMessage());
		}
		return  XmlParserFunctions.parseIssueGetResponse(returnData);
	}

}