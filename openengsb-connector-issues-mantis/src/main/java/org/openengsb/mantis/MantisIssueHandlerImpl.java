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
import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.EngineConfigurationFactoryFinder;
import org.apache.axis.configuration.SimpleProvider;
import org.apache.axis.transport.http.CommonsHTTPSender;
import org.openengsb.issues.common.api.IssueHandler;
import org.openengsb.issues.common.api.exceptions.IssueDomainException;
import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.MantisConnectLocator;
import biz.futureware.mantisconnect.MantisConnectPortType;

public class MantisIssueHandlerImpl implements IssueHandler {

	MantisConnectPortType porttype = null;
	
	public MantisIssueHandlerImpl() {
		//this has to be done because of the error faultString: (0)null
		EngineConfiguration engine = EngineConfigurationFactoryFinder.newFactory().getClientEngineConfig();
        SimpleProvider provider = new SimpleProvider(engine);
        provider.deployTransport("http", new CommonsHTTPSender());
        
		MantisConnectLocator locator = new MantisConnectLocator(provider);
    	try {
			porttype =locator.getMantisConnectPort();
		} catch (ServiceException e) {			
			e.printStackTrace();
		}
	}
	@Override
	public String createIssue(IssueData issue, String user, String pass) throws IssueDomainException{
		BigInteger returnInt = null;
		try {
			returnInt = porttype.mc_issue_add(user,pass, issue);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new IssueDomainException("Error adding issue: "+e.getMessage());
		}
		return returnInt.toString();
	}

	@Override
	public void deleteIssue(BigInteger issueId, String user, String password) throws IssueDomainException {
		try {
			if(!porttype.mc_issue_delete(user, password, issueId)) {
				throw new IssueDomainException ("Could not delete issue.");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new IssueDomainException ("Error deleting issue: "+e.getMessage());
		}
	}

	@Override
	public void updateIssue(BigInteger issueId, IssueData issue, String user, String password) throws IssueDomainException {
		try {
			porttype.mc_issue_update(user, password, issueId, issue);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new IssueDomainException ("Error updating issue: "+e.getMessage());			
		}
	}

	@Override
	public IssueData getIssue(BigInteger issueId, String user, String password) throws IssueDomainException{
		IssueData data;
		try {
			 data = porttype.mc_issue_get(password, user, issueId);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new IssueDomainException ("Error getting issue: "+e.getMessage());			
		}
		return data;
	}
}