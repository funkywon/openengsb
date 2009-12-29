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
package org.openengsb.mantis.commands;

import java.io.IOException;

import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
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

public class IssueDeleteCommand implements IssueCommand {

	IssueHandler handler;
	Log log;
	
	public IssueDeleteCommand(IssueHandler handler, Log log) {
		this.handler = handler;
		this.log = log;
	}
	@SuppressWarnings("unused")
	private IssueDeleteCommand() {
		
	}
	@Override
	public String execute(NormalizedMessage in) throws IssueDomainException{
		IssueData data1 = MantisUtil.extractIssueData(in);
		UserCredential userCred;
		try {
			userCred = MantisUtil.extractUserCredentials(in);
		} catch (DOMException e) {
			e.printStackTrace();
			throw new IssueDomainException("DomException");
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new IssueDomainException("MessagingException");
		} catch (TransformerException e) {
			e.printStackTrace();
			throw new IssueDomainException("TransformerException");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw new IssueDomainException("ParserConfigurationException");
		} catch (IOException e) {
			e.printStackTrace();
			throw new IssueDomainException("IOException");
		} catch (SAXException e) {
			e.printStackTrace();
			throw new IssueDomainException("SAXException");
		}
		try {
			
			handler.deleteIssue(data1.getId(),userCred.getUser(), userCred.getPassword());
		} catch(IssueDomainException e) {
			return XmlParserFunctions.prepareDeleteIssueResponse("Failure deleting issue!");
		}
		
		return XmlParserFunctions.prepareDeleteIssueResponse("success");
		
	}

}
