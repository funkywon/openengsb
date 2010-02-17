package org.openengsb.mantis;


import java.math.BigInteger;
import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import org.mockito.runners.MockitoJUnit44Runner;

import org.openengsb.mantis.util.MantisTypeConverter;
import org.openengsb.drools.model.IssueDomainException;
import org.openengsb.drools.model.Issue;
import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.MantisConnectPortType;

@RunWith(MockitoJUnit44Runner.class)
public class MantisConnectorTest {

    private MantisIssueHandlerImpl mantisConnector;
    @Mock private MantisTypeConverter typeConverter;
    @Mock private MantisConnectPortType portType;
    
    @Before
    public void setup() throws RemoteException {
    	mantisConnector = new MantisIssueHandlerImpl("", "", "");

        mantisConnector.setPorttype(portType);
        mantisConnector.setTypeConverter(typeConverter);
        
        IssueData issuedata = new IssueData();
        issuedata.setId(new BigInteger("1234"));
        issuedata.setSummary("mantis test summary");

        //create
        when(typeConverter.convertIssueDataToSpecific(any(Issue.class))).thenReturn(issuedata);
        when(portType.mc_issue_add(anyString(), anyString(), any(IssueData.class))).thenReturn(new BigInteger("1"));
        
        //get
        Issue issue = new Issue();
        issue.setId("123");
        issue.setSummary("test summary");
        
        when(portType.mc_issue_get(anyString(),anyString(),any(BigInteger.class))).thenReturn(issuedata);
        when(typeConverter.convertIssueDataToGeneric(issuedata)).thenReturn(issue);
        
        //update
        when(typeConverter.convertIssueDataToSpecific(any(Issue.class))).thenReturn(issuedata);
        
        //delete
        when(portType.mc_issue_delete("", "", new BigInteger("234"))).thenReturn(true);
        
        
    }
    
    @Test
    public void createIssue() throws RemoteException{
        Issue issue = generateGenericIssue();
        String result = null;
        try {
			 result = mantisConnector.createIssue(issue);
		} catch (IssueDomainException e) {
			fail("Creating issue failed.");
		}
		assertEquals("1",result);
    }

    @Test
    public void getIssue() throws IssueDomainException {
    	Issue result = null;
    	result = mantisConnector.getIssue("1");
    	assertEquals("123",result.getId());
    	assertEquals("test summary",result.getSummary());
    	
    }
    
    @Test
    public void deleteIssue() throws RemoteException {
    	try {
			mantisConnector.deleteIssue("234");
		} catch (IssueDomainException e) {
			fail("Delete Issue test failed: "+e.getMessage());
		}
		verify(portType).mc_issue_delete("", "", new BigInteger("234"));
    }
    
    
    @Test
    public void updateIssue() throws IssueDomainException, RemoteException {
    	mantisConnector.updateIssue(new Issue());
    	verify(portType).mc_issue_update(anyString(), anyString(), any(BigInteger.class), any(IssueData.class));
    	
    }

	private Issue generateGenericIssue() {
		return new Issue();
	}
    
}
