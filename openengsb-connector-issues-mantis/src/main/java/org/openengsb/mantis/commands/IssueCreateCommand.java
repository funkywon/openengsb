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

import javax.jbi.messaging.NormalizedMessage;
import javax.xml.bind.JAXBException;
import org.apache.commons.logging.Log;
import org.openengsb.issues.common.api.IssueHandler;
import org.openengsb.issues.common.api.exceptions.IssueDomainException;
import org.openengsb.issues.common.pojos.AccountDataType;
import org.openengsb.issues.common.pojos.IssueCreateMessage;
import org.openengsb.mantis.util.JAXBUtil;
import org.openengsb.mantis.util.XmlParserFunctions;
import org.w3c.dom.DOMException;
import biz.futureware.mantisconnect.IssueData;

public class IssueCreateCommand implements IssueCommand {

	
	IssueHandler handler;
	Log log;
	
	public IssueCreateCommand(IssueHandler handler, Log log) {
		this.handler = handler;
		this.log = log;
	}
	@SuppressWarnings("unused")
	private IssueCreateCommand() {
		
	}
	
	@Override
	public String execute(NormalizedMessage in) throws IssueDomainException {
		IssueData data;
		IssueCreateMessage requestMessage = null;
		AccountDataType userCred;
		try {
			requestMessage = JAXBUtil.extractIssueCreateMessage(in);
			userCred = requestMessage.getAccountData();
			data = JAXBUtil.convertIssueData(requestMessage.getIssueData());
		} catch (DOMException e) {
			e.printStackTrace();
			throw new IssueDomainException("DomException: "+e.getMessage());
		} catch (JAXBException e) {
			throw new IssueDomainException("JAXBException: "+e.getMessage());
		}
		
		String response = handler.createIssue(data, userCred.getUsername(), userCred.getPassword());
		response = XmlParserFunctions.prepareCreateIssueResponse(response);
		return response;
	}
}