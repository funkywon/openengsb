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
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;


import org.openengsb.issues.common.api.IssueHandler;
import org.openengsb.issues.common.api.enums.IssuePriority;
import org.openengsb.issues.common.api.enums.IssueSeverity;
import org.openengsb.issues.common.api.exceptions.IssueDomainException;
import org.openengsb.mantis.MantisIssueHandlerImpl;
import org.openengsb.mantis.commands.IssueCommand;
import org.openengsb.mantis.commands.IssueCreateCommand;
import org.openengsb.mantis.commands.IssueDeleteCommand;
import org.openengsb.mantis.commands.IssueUpdateCommand;
import org.openengsb.mantis.endpoints.AbstractEndpoint;
import org.openengsb.mantis.util.IssueOpType;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;
import org.apache.servicemix.jbi.jaxp.StringSource;
import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.ObjectRef;
import org.openengsb.mantis.util.XmlParserFunctions;

/**
 * @org.apache.xbean.XBean element="mantis provider"
 */
public class IssueEndpoint extends AbstractEndpoint {

	private static final Map<IssueOpType, IssueCommand> commandMap = new HashMap<IssueOpType, IssueCommand>();

	// FIX THIS -> Factory
	private IssueHandler handler = new MantisIssueHandlerImpl();

	// private static final Map<String,Integer> PRIORITIES = new
	// HashMap<String,Integer>();
	// private static final Map<String,Integer> SEVERITIES = new
	// HashMap<String,Integer>();
	// static {
	// for(IssuePriority priority:IssuePriority.values()) {
	// PRIORITIES.put(priority.toString().toLowerCase(), priority.ordinal());
	// }
	// for(IssueSeverity severity:IssueSeverity.values()) {
	// SEVERITIES.put(severity.toString().toLowerCase(), severity.ordinal());
	// }
	// }

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
			NormalizedMessage in, NormalizedMessage out) throws Exception {
//		String op = exchange.getOperation().getLocalPart();
//		String user = extractSingleNode(in, "//user").getNodeValue();
//		String pass = extractSingleNode(in, "//password").getNodeValue();
		@SuppressWarnings("unused")
		IssueData data1 = extractIssueData(in);

		IssueOpType op;
		try {
			op = XmlParserFunctions.getMessageType(in);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
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
	}

	// Refactor: make UTIL class
	protected IssueData extractIssueData(NormalizedMessage message)
			throws IssueDomainException {
		String category = null;
		String description = null;
		String summary = null;
		String project = null;

		// set the default values
		@SuppressWarnings("unused")
		String reproducibility = "have not tried";
		String severity = "minor";
		String priority = "normal";
		try {
			category = extractSingleNode(message, "//category").getNodeValue();
			description = extractSingleNode(message, "//description")
					.getNodeValue();
			summary = extractSingleNode(message, "//summary").getNodeValue();
			project = extractSingleNode(message, "//project").getNodeValue();

			reproducibility = extractSingleNode(message, "//reproducibility")
					.getNodeValue();
			severity = extractSingleNode(message, "//severity").getNodeValue();
			priority = extractSingleNode(message, "//priority").getNodeValue();
		} catch (DOMException e) {
			throw new IssueDomainException("DomException");
		} catch (MessagingException e) {
			throw new IssueDomainException("MessagingException");
		} catch (TransformerException e) {
			throw new IssueDomainException("TransformerException");
		} catch (ParserConfigurationException e) {
			throw new IssueDomainException("ParserConfigurationException");
		} catch (IOException e) {
			throw new IssueDomainException("IOException");
		} catch (SAXException e) {
			throw new IssueDomainException("SAXException");
		}

		if (category == null || description == null || summary == null
				|| project == null) {
			throw new IssueDomainException(
					"One or more required field(s) is(are) not set.");
		}

		IssueData data1 = new IssueData();

		// Required fields
		data1.setCategory(category);
		data1.setDescription(description);
		data1.setSummary(summary);

		// needs to be fixed
		data1.setProject(new ObjectRef(new BigInteger("1"), project));

		// not sure yet, if this is the right index
		data1.setPriority(new ObjectRef(BigInteger.valueOf(IssuePriority
				.valueOf(priority).ordinal()), priority));
		data1.setSeverity(new ObjectRef(BigInteger.valueOf(IssueSeverity
				.valueOf(severity).ordinal()), severity));

		// keep this until we know, the upper lines are working
		// data1.setPriority(new ObjectRef(new
		// BigInteger(IssueEndpoint.PRIORITIES.get(priority).toString()),priority));
		// data1.setSeverity(new ObjectRef(new
		// BigInteger(IssueEndpoint.SEVERITIES.get(severity).toString()),severity));


		 data1.setReproducibility(reproducibility);

		return data1;
	}

}
