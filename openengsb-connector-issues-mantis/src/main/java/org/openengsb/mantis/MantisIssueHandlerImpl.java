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
import org.openengsb.drools.IssueDomain;
import org.openengsb.drools.model.IssueDomainException;
import org.openengsb.drools.model.Issue;
import org.openengsb.issues.common.util.TypeConverter;
import org.openengsb.mantis.util.MantisTypeConverter;
import org.w3c.dom.DOMException;
import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.MantisConnectLocator;
import biz.futureware.mantisconnect.MantisConnectPortType;

public class MantisIssueHandlerImpl implements IssueDomain {

	private MantisConnectPortType porttype = null;

    private TypeConverter<IssueData,
    biz.futureware.mantisconnect.IssueNoteData,
    biz.futureware.mantisconnect.ProjectData>  typeConverter=null;
	
	private String user;
	private String pass;
	private String uri;
	
	public MantisIssueHandlerImpl(String user, String pass, String uri) {
	    this.user=user;
	    this.pass=pass;
	    this.uri=uri;
	    
		//this has to be done because of the error faultString: (0)null
		EngineConfiguration engine = EngineConfigurationFactoryFinder.newFactory().getClientEngineConfig();
        SimpleProvider provider = new SimpleProvider(engine);
        provider.deployTransport("http", new CommonsHTTPSender());
        MantisConnectLocator locator = new MantisConnectLocator(provider);
        
        typeConverter = new MantisTypeConverter();
        
		java.net.URL endpoint;
    	try {
    	    if(uri!=null&&uri!=""){
    	        try {
    	             endpoint = new java.net.URL(uri);
    	        }
    	        catch (java.net.MalformedURLException e) {
    	            throw new javax.xml.rpc.ServiceException(e);
    	        }
    	        this.porttype =locator.getMantisConnectPort(endpoint);
    	    } else {
    	        this.porttype =locator.getMantisConnectPort();
    	    }
		} catch (ServiceException e) {			
			e.printStackTrace();
		}
	}
	
	
	

	@Override
    public void deleteIssue(String issueId) throws org.openengsb.drools.model.IssueDomainException {
	    try {
            if(!porttype.mc_issue_delete(user, pass, new BigInteger(issueId))) {
                throw new IssueDomainException ("Could not delete issue.");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new IssueDomainException ("Error deleting issue: "+e.getMessage());
        } catch (NumberFormatException e) {
            throw new IssueDomainException("Wrong issue ID format.");
        }
    }
	
	@Override
	public void updateIssue(Issue issue) throws org.openengsb.drools.model.IssueDomainException{
	    IssueData mantisIssueData = typeConverter.convertIssueDataToSpecific(issue);
        try {
            porttype.mc_issue_update(user, pass, mantisIssueData.getId(), mantisIssueData);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new IssueDomainException ("Error updating issue: "+e.getMessage());           
        }
	    
	}
	
    @Override
    public String createIssue(Issue issue) throws org.openengsb.drools.model.IssueDomainException{
        BigInteger returnInt = null;
        IssueData mantisIssueData;
        try {
            
            mantisIssueData = typeConverter.convertIssueDataToSpecific(issue);
            returnInt = porttype.mc_issue_add(user, pass, mantisIssueData);
        } catch (DOMException e) {
            e.printStackTrace();
            throw new IssueDomainException("Error during DOM parsing.");
        } catch (RemoteException e) {
            throw new IssueDomainException("Error during invoking Webservice.");
        }
        return returnInt.toString();
    }

    @Override
    public Issue getIssue(String id) throws org.openengsb.drools.model.IssueDomainException {
        IssueData mantisIssueData;
        try {
            mantisIssueData = porttype.mc_issue_get(user, pass, new BigInteger(id));
            return typeConverter.convertIssueDataToGeneric(mantisIssueData);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new IssueDomainException("Error getting issue.");
        }
    }
    
    public MantisConnectPortType getPorttype() {
        return porttype;
    }
    public void setPorttype(MantisConnectPortType porttype) {
        this.porttype = porttype;
    }
    public TypeConverter<IssueData, biz.futureware.mantisconnect.IssueNoteData, biz.futureware.mantisconnect.ProjectData> getTypeConverter() {
        return typeConverter;
    }
    public void setTypeConverter(
            TypeConverter<IssueData, biz.futureware.mantisconnect.IssueNoteData, biz.futureware.mantisconnect.ProjectData> typeConverter) {
        this.typeConverter = typeConverter;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    

}