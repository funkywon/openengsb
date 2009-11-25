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


import org.openengsb.mantis.AbstractEndpoint;
import org.openengsb.mantis.common.entities.Comment;
import org.openengsb.mantis.common.entities.Issue;
import org.openengsb.mantis.common.entities.Project;
import org.openengsb.mantis.common.entities.enums.IssuePriority;
import org.openengsb.mantis.common.entities.enums.IssueResolution;
import org.openengsb.mantis.common.entities.enums.IssueSeverity;
import org.openengsb.mantis.common.entities.enums.IssueStatus;
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
	private static final String OPERATION_ADD_ISSUE="add_issue";
	private static final String OPERATION_GET_ISSUE="get_issue";
	MantisConnectPortType porttype = null;
	private static final Map<String,Integer> PRIORITIES = new HashMap<String,Integer>();
	private static final Map<String,Integer> SEVERITIES = new HashMap<String,Integer>();
	
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
    	MantisConnectLocator locator = new MantisConnectLocator();
    	
    	try {
			MantisConnectPortType porttype =locator.getMantisConnectPort();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
        
        if(op.equals(IssueEndpoint.OPERATION_ADD_ISSUE)) {
        	
        	IssueData data1 = extractIssueData(in);
			
			String user = extractSingleNode(in,"//user").getNodeValue();
	    	String pass = extractSingleNode(in,"//password").getNodeValue();
        	
	    	porttype.mc_issue_add(user,pass, data1);
	    	
	    	
        } else if (op.equals(IssueEndpoint.OPERATION_GET_ISSUE)) {
        	
        }
        
	}
	
	protected IssueData extractIssueData(NormalizedMessage message) throws IssueException {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (category==null||description==null||summary==null||project==null) {
			throw new IssueException("One or more required field(s) is(are) not set.");
		}
    	
    	
    	IssueData data1 = new IssueData();
    	
    	//Required fields
		data1.setCategory(category);
		data1.setDescription(description);
		data1.setSummary(summary);
		//needs to be fixed
		data1.setProject(new ObjectRef(new BigInteger("1"),project));

		data1.setPriority(new ObjectRef(new BigInteger(IssueEndpoint.PRIORITIES.get(priority).toString()),priority));
		data1.setSeverity(new ObjectRef(new BigInteger(IssueEndpoint.SEVERITIES.get(severity).toString()),severity));
//		data1.setReproducibility()
		
		return data1;
	}

	
    
}
