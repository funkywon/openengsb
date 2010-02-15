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
package org.openengsb.issues.mantis;

import org.openengsb.contextcommon.ContextHelper;
import org.openengsb.drools.IssueDomain;
import org.openengsb.drools.IssueDomainException;
import org.openengsb.issues.common.endpoints.AbstractIssueEndpoint;
import org.openengsb.issues.trac.TracConnector;
import org.openengsb.mantis.MantisIssueHandlerImpl;
import org.openengsb.core.MessageProperties;
/**
 * @org.apache.xbean.XBean element="issueTrackerProvider"
 */
public class IssueEndpoint extends AbstractIssueEndpoint {

	private IssueDomain domain1;
	private IssueDomain domain2;
	private int counter=0;
	
	private String url;
	private String user;
	private String password;
	

	public void init() {
	    try {
            createIssueDomain();
        } catch (IssueDomainException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
    @Override
    protected IssueDomain createIssueDomain() throws org.openengsb.drools.IssueDomainException {
        if(domain1==null) {
            domain1 = new MantisIssueHandlerImpl(user, password, url);
        }
        if(domain2==null) 
            domain2=new TracConnector(url,user,password);
        return domain2;
    }

    @Override
    protected IssueDomain getImplementation(ContextHelper arg0,MessageProperties props) {
        counter++;
        if(counter%2==0) {
            return domain1;
        }
        return domain2;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}