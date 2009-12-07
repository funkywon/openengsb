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
package org.openengsb.mantis;


import java.math.BigInteger;
import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;
import org.openengsb.issues.common.api.IssueHandler;
import org.openengsb.issues.common.api.exceptions.IssueDomainException;
import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.MantisConnectLocator;
import biz.futureware.mantisconnect.MantisConnectPortType;

public class MantisIssueHandlerImpl implements IssueHandler {

	MantisConnectPortType porttype = null;
	
	
	public MantisIssueHandlerImpl() {
		MantisConnectLocator locator = new MantisConnectLocator();
    	try {
			porttype =locator.getMantisConnectPort();
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
