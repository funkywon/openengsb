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
package org.openengsb.issues.common.endpoints;

import java.net.URI;
import java.net.URISyntaxException;
import org.openengsb.contextcommon.ContextHelper;
import org.openengsb.drools.IssueDomain;
import org.openengsb.drools.IssueDomainException;
import org.openengsb.issues.trac.TracConnector;
import org.openengsb.mantis.MantisIssueHandlerImpl;

/**
 * @org.apache.xbean.XBean element="issueTrackerProvider"
 */
public class IssueEndpoint extends AbstractIssueEndpoint {


	private IssueDomain domain1;
	private IssueDomain domain2;
	private int counter=0;
	public IssueEndpoint() {
		init();
	}

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
            try {
                domain1 = new MantisIssueHandlerImpl("","",new URI(""));
            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(domain2==null) 
            domain2=new TracConnector(endpoint,endpoint,endpoint);
        return domain2;
    }

    @Override
    protected IssueDomain getImplementation(ContextHelper arg0) {
        counter++;
        if(counter%2==0) {
            return domain1;
        }
        return domain2;
    }
}