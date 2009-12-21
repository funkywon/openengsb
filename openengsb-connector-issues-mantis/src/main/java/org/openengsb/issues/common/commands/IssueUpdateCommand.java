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
package org.openengsb.issues.common.commands;

import javax.jbi.messaging.NormalizedMessage;
import javax.xml.bind.JAXBException;
import org.apache.commons.logging.Log;
import org.openengsb.issues.common.api.IssueHandler;
import org.openengsb.issues.common.api.exceptions.IssueDomainException;
import org.openengsb.issues.common.pojos.IssueUpdateMessage;
import org.openengsb.issues.common.util.JAXBUtil;
import org.openengsb.mantis.util.XmlParserFunctions;
import org.w3c.dom.DOMException;

public class IssueUpdateCommand implements IssueCommand {

	private IssueHandler handler;
	@SuppressWarnings("unused")
	private Log log;
	
	
	
	public IssueUpdateCommand(IssueHandler handler, Log log) {
		this.handler = handler;
		this.log = log;
	}
	@SuppressWarnings("unused")
	private IssueUpdateCommand() {
		
	}
	
	@Override
	public String execute(NormalizedMessage in) throws IssueDomainException{
		IssueUpdateMessage requestMessage = null;
		
		
		try {
			requestMessage = JAXBUtil.extractIssueUpdateMessage(in);
		
		} catch (DOMException e) {
			e.printStackTrace();
			throw new IssueDomainException("DomException: "+e.getMessage());
		} catch (JAXBException e) {
			throw new IssueDomainException("JAXBException: "+e.getMessage());
		}
		
		try {
			handler.updateIssue(requestMessage);
		} catch (IssueDomainException e) {
			return XmlParserFunctions.prepareUpdateIssueResponse("Failure updating issue!");
		}
		return XmlParserFunctions.prepareUpdateIssueResponse("success");
	}

}
