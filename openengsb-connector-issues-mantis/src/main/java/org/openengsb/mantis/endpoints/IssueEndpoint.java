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
package org.openengsb.mantis.endpoints;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;


import org.openengsb.issues.common.api.IssueHandler;
import org.openengsb.issues.common.api.exceptions.IssueDomainException;
import org.openengsb.mantis.MantisIssueHandlerImpl;
import org.openengsb.mantis.commands.IssueCommand;
import org.openengsb.mantis.commands.IssueCreateCommand;
import org.openengsb.mantis.commands.IssueDeleteCommand;
import org.openengsb.mantis.commands.IssueGetCommand;
import org.openengsb.mantis.commands.IssueUpdateCommand;
import org.openengsb.mantis.endpoints.AbstractEndpoint;
import org.openengsb.mantis.util.IssueOpType;
import org.apache.servicemix.jbi.jaxp.StringSource;
import org.dom4j.DocumentException;

import org.openengsb.mantis.util.XmlParserFunctions;
import org.xml.sax.SAXException;

/**
 * @org.apache.xbean.XBean element="mantis provider"
 */
public class IssueEndpoint extends AbstractEndpoint {

	private static final Map<IssueOpType, IssueCommand> commandMap = new HashMap<IssueOpType, IssueCommand>();

	// FIX THIS -> Factory
	private IssueHandler handler = new MantisIssueHandlerImpl();

	public IssueEndpoint() {
	}

	@Override
	protected String getEndpointBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void processInOnlyRequest(MessageExchange exchange,
			NormalizedMessage in) throws Exception {
		processInOutRequest(exchange, in, null);
	}

	@Override
	protected void processInOutRequest(MessageExchange exchange,
			NormalizedMessage in, NormalizedMessage out)  throws IOException, SAXException, TransformerException, DocumentException, IssueDomainException, MessagingException{
//		String op = exchange.getOperation().getLocalPart();
		IssueOpType op;
		op = XmlParserFunctions.getMessageType(in);
		String body = null;
		body = IssueEndpoint.commandMap.get(op).execute(in);
		// body =
		// "<?xml version=\"1.0\" encoding=\"UTF-8\"?><acmResponseMessage><body>"
		// + body
		// + "</body></acmResponseMessage>";
		Source response = new StringSource(body);
		this.logger.info(body);
		out.setContent(response);
		getChannel().send(exchange);
	}

	public void init() {
		commandMap.put(IssueOpType.CREATE_ISSUE, new IssueCreateCommand(
				handler, getLog()));
		commandMap.put(IssueOpType.DELETE_ISSUE, new IssueDeleteCommand(
				handler, getLog()));
		commandMap.put(IssueOpType.UPDATE_ISSUE, new IssueUpdateCommand(
				handler, getLog()));
		commandMap.put(IssueOpType.GET_ISSUE, new IssueGetCommand(
				handler, getLog()));
	}
	
}