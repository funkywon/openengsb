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
package org.openengsb.issues.common.commands;

import javax.jbi.messaging.NormalizedMessage;
import javax.xml.bind.JAXBException;
import org.apache.commons.logging.Log;
import org.openengsb.issues.common.api.IssueHandler;
import org.openengsb.issues.common.api.exceptions.IssueDomainException;
import org.openengsb.issues.common.pojos.IssueDataType;
import org.openengsb.issues.common.pojos.IssueGetMessage;
import org.openengsb.issues.common.util.JAXBUtil;
import org.openengsb.issues.common.util.XmlParserFunctions;
import org.w3c.dom.DOMException;

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
		IssueGetMessage requestMessage = null;
		try {
			requestMessage = JAXBUtil.extractIssueGetMessage(in);
		} catch (DOMException e) {
			e.printStackTrace();
			throw new IssueDomainException("DomException: "+e.getMessage());
		} catch (JAXBException e) {
			throw new IssueDomainException("JAXBException: "+e.getMessage());
		}
		IssueDataType returnData = handler.getIssue(requestMessage);
		return  XmlParserFunctions.parseIssueGetResponse(returnData);
	}

}