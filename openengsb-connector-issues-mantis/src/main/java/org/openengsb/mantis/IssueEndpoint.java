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
package org.openengsb.mantis;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jbi.management.DeploymentException;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.jbi.messaging.MessageExchange.Role;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.transform.TransformerException;


import org.openengsb.issues.common.api.IssueDomain;
import org.openengsb.issues.common.api.impl.MantisIssueDomainImpl;
import org.openengsb.issues.common.enums.IssuePriority;
import org.openengsb.issues.common.enums.IssueResolution;
import org.openengsb.issues.common.enums.IssueSeverity;
import org.openengsb.issues.common.enums.IssueStatus;
import org.openengsb.issues.common.exceptions.IssueDomainException;
import org.openengsb.issues.common.model.Comment;
import org.openengsb.issues.common.model.Issue;
import org.openengsb.issues.common.model.Project;
import org.openengsb.mantis.AbstractEndpoint;
import org.openengsb.mantis.common.exception.IssueException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.apache.servicemix.common.ServiceUnit;

import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.MantisConnectLocator;
import biz.futureware.mantisconnect.MantisConnectPortType;
import biz.futureware.mantisconnect.ObjectRef;

/**
 * @org.apache.xbean.XBean element="mantis provider"
 */
public class IssueEndpoint extends AbstractEndpoint{

//	private static final String BEHAVIOR = "";
	private static final String OPERATION_CREATE_ISSUE="create_issue";
	private static final String OPERATION_UPDATE_ISSUE="update_issue";
	private static final String OPERATION_DELETE_ISSUE="delete_issue";
	
	private static final Map<String,Integer> PRIORITIES = new HashMap<String,Integer>();
	private static final Map<String,Integer> SEVERITIES = new HashMap<String,Integer>();
	
	//FIX THIS
	private IssueDomain handler = new MantisIssueDomainImpl(); 
	
	static {
		
		PRIORITIES.put("immediate", IssuePriority.IMMEDIATE.ordinal());
		PRIORITIES.put("urgent", IssuePriority.URGENT.ordinal());
		PRIORITIES.put("high", IssuePriority.HIGH.ordinal());
		PRIORITIES.put("normal", IssuePriority.NORMAL.ordinal());
		PRIORITIES.put("low", IssuePriority.LOW.ordinal());
		
		
		SEVERITIES.put("trivial", IssueSeverity.TRIVIAL.ordinal());
		SEVERITIES.put("critical", IssueSeverity.CRITICAL.ordinal());
		SEVERITIES.put("blocker", IssueSeverity.BLOCKER.ordinal());
		SEVERITIES.put("enhancement", IssueSeverity.ENHANCEMENT.ordinal());
		SEVERITIES.put("major", IssueSeverity.MAJOR.ordinal());
		SEVERITIES.put("minor", IssueSeverity.MINOR.ordinal());
		
	}
	
	
    public IssueEndpoint() {
    	
    }

	@Override
	protected String getEndpointBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected void processInOnlyRequest(MessageExchange exchange, NormalizedMessage in)
	throws Exception {
		processInOutRequest(exchange,in,null);
	}

	@Override
    protected void processInOutRequest(MessageExchange exchange, NormalizedMessage in, NormalizedMessage out)
            throws Exception {
		
		String op = exchange.getOperation().getLocalPart();
        String body = null;
        
        String user = extractSingleNode(in,"//user").getNodeValue();
    	String pass = extractSingleNode(in,"//password").getNodeValue();
    	IssueData data1 = extractIssueData(in);
        
        if(op.equals(IssueEndpoint.OPERATION_CREATE_ISSUE)) {
        	
        	
        	String issueId = handler.createIssue(data1,user,pass);

        
        } else if (op.equals(IssueEndpoint.OPERATION_UPDATE_ISSUE)) {
        	handler.updateIssue(data1.getId(), data1, user, pass);
        } else if (op.equals(IssueEndpoint.OPERATION_DELETE_ISSUE)) {
        	handler.deleteIssue(data1.getId(), data1, user, pass);
        	
        }
        
	}
	
	protected IssueData extractIssueData(NormalizedMessage message) throws IssueDomainException {
		String category = null;
		String description = null;
		String summary = null;
		String project = null;
		
		
		//set the default values
		String reproducibility="have not tried";
		String severity="minor";
		String priority="normal";
		
		try {
			category = extractSingleNode(message,"//category").getNodeValue();
			description = extractSingleNode(message,"//description").getNodeValue();
			summary = extractSingleNode(message,"//summary").getNodeValue();
			project = extractSingleNode(message,"//project").getNodeValue();
			
			reproducibility = extractSingleNode(message,"//reproducibility").getNodeValue();
			severity = extractSingleNode(message,"//severity").getNodeValue();
			priority = extractSingleNode(message,"//priority").getNodeValue();
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
		
		if (category==null||description==null||summary==null||project==null) {
			throw new IssueDomainException("One or more required field(s) is(are) not set.");
		}
    	
    	
    	IssueData data1 = new IssueData();
    	
    	//Required fields
		data1.setCategory(category);
		data1.setDescription(description);
		data1.setSummary(summary);
		//needs to be fixed
		data1.setProject(new ObjectRef(new BigInteger("1"),project));
		
		//not sure yet, if this is the right index
		data1.setPriority(new ObjectRef(new BigInteger(IssueEndpoint.PRIORITIES.get(priority).toString()),priority));
		data1.setSeverity(new ObjectRef(new BigInteger(IssueEndpoint.SEVERITIES.get(severity).toString()),severity));
//		data1.setReproducibility()
		
		return data1;
	}

	
		
	
	
	
    
}
