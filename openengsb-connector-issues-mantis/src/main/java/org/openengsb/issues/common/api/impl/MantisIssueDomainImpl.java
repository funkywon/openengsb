package org.openengsb.issues.common.api.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.transform.TransformerException;

import org.openengsb.issues.common.api.IssueDomain;
import org.openengsb.issues.common.enums.IssuePriority;
import org.openengsb.issues.common.enums.IssueSeverity;
import org.openengsb.issues.common.exceptions.IssueDomainException;
import org.openengsb.issues.common.model.Issue;
import org.openengsb.mantis.IssueEndpoint;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.MantisConnectLocator;
import biz.futureware.mantisconnect.MantisConnectPortType;
import biz.futureware.mantisconnect.ObjectRef;

public class MantisIssueDomainImpl implements IssueDomain {

	MantisConnectPortType porttype = null;
	
	
	public MantisIssueDomainImpl() {
		MantisConnectLocator locator = new MantisConnectLocator();
    	try {
			MantisConnectPortType porttype =locator.getMantisConnectPort();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	@Override
	public String createIssue(IssueData issue,String user, String pass) throws IssueDomainException{
		
		BigInteger returnInt = null;
		try {
			returnInt = porttype.mc_issue_add(user,pass, issue);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new IssueDomainException("Error adding issue.");
		}
		return returnInt.toString();
	}

	@Override
	public void deleteIssue(BigInteger issueId, IssueData issue, String user, String password) throws IssueDomainException {
		try {
			porttype.mc_issue_update(user, password, issueId, issue);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new IssueDomainException ("Error deleting issue.");
		}
	}

	@Override
	public void updateIssue(BigInteger issueId, IssueData issue, String user, String password) throws IssueDomainException {
		try {
			porttype.mc_issue_update(user, password, issueId, issue);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new IssueDomainException ("Error updating issue.");			
		}

	}
	
	

}
