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
import org.openengsb.issues.common.pojos.IssueCreateMessage;
import org.openengsb.issues.common.pojos.IssueDataType;
import org.openengsb.issues.common.pojos.IssueDeleteMessage;
import org.openengsb.issues.common.pojos.IssueGetMessage;
import org.openengsb.issues.common.pojos.IssueUpdateMessage;
import org.openengsb.issues.common.pojos.UserCredentials;
import org.openengsb.issues.common.util.TypeConverter;
import org.openengsb.mantis.util.MantisTypeConverter;
import org.w3c.dom.DOMException;
import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.MantisConnectLocator;
import biz.futureware.mantisconnect.MantisConnectPortType;

public class MantisIssueHandlerImpl implements IssueHandler {

	MantisConnectPortType porttype = null;
	TypeConverter<IssueData,
	biz.futureware.mantisconnect.AccountData,
	biz.futureware.mantisconnect.ObjectRef,
	biz.futureware.mantisconnect.IssueNoteData,
	biz.futureware.mantisconnect.AttachmentData> typeConverter=null;
	public MantisIssueHandlerImpl() {
		//this has to be done because of the error faultString: (0)null
		EngineConfiguration engine = EngineConfigurationFactoryFinder.newFactory().getClientEngineConfig();
        SimpleProvider provider = new SimpleProvider(engine);
        provider.deployTransport("http", new CommonsHTTPSender());
        
        typeConverter = new MantisTypeConverter();
		MantisConnectLocator locator = new MantisConnectLocator(provider);
    	try {
			porttype =locator.getMantisConnectPort();
		} catch (ServiceException e) {			
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteIssue(IssueDeleteMessage message) throws IssueDomainException {
		UserCredentials uc = message.getAccountData();
		try {
			if(!porttype.mc_issue_delete(uc.getUsername(), uc.getPassword(), BigInteger.valueOf(message.getIssueId()))) {
				throw new IssueDomainException ("Could not delete issue.");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new IssueDomainException ("Error deleting issue: "+e.getMessage());
		}
	}

	@Override
	public void updateIssue(IssueUpdateMessage message) throws IssueDomainException {
		UserCredentials uc = message.getAccountData();
		IssueData data = typeConverter.convertIssueDataToSpecific(message.getIssueData());
		try {
			porttype.mc_issue_update(uc.getUsername(), uc.getPassword(), BigInteger.valueOf(message.getIssueId()), data);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new IssueDomainException ("Error updating issue: "+e.getMessage());			
		}
	}

	@Override
	public IssueDataType getIssue(IssueGetMessage getMessage) throws IssueDomainException{
		IssueData data;
		UserCredentials uc = getMessage.getAccountData();
		try {
			 data = porttype.mc_issue_get(uc.getUsername(),uc.getPassword(),BigInteger.valueOf(getMessage.getIssueId()));
		} catch (RemoteException e) {
			e.printStackTrace();
			throw new IssueDomainException ("Error getting issue: "+e.getMessage());			
		}
		return typeConverter.convertIssueDataToGeneric(data);
	}
	
	@Override
	public int createIssue(IssueCreateMessage createMessage)
			throws IssueDomainException {
		IssueData data;
		UserCredentials userCred;
		
		BigInteger returnInt = null;
		try {
			
			userCred = createMessage.getAccountData();
			data = typeConverter.convertIssueDataToSpecific(createMessage.getIssueData());
			returnInt = porttype.mc_issue_add(userCred.getUsername(), userCred.getPassword(), data);
		} catch (DOMException e) {
			e.printStackTrace();
			throw new IssueDomainException("Error during DOM parsing.");
		} catch (RemoteException e) {
			throw new IssueDomainException("Error during invoking Webservice.");
		}
		return returnInt.intValue();
	}
}