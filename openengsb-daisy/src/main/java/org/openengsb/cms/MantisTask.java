/**

   Copyright 2009 OpenEngSB Division, Vienna University of Technology

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
   
 */

package org.openengsb.cms;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.xml.rpc.ServiceException;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.EngineConfigurationFactoryFinder;
import org.apache.axis.configuration.SimpleProvider;
import org.apache.axis.transport.http.CommonsHTTPSender;


import biz.futureware.mantisconnect.FilterData;
import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.MantisConnectLocator;
import biz.futureware.mantisconnect.MantisConnectPortType;


public class MantisTask {
	public static void main(String[] args) {
		MantisTask mt = new MantisTask();
		mt.getIssues();
	}
	
	public IssueData[] getIssues() {
		
		IssueData[] issues=null;
		
		try {
			InputStream istream = MantisTask.class.getResourceAsStream("config.properties");
	        Properties properties = new Properties(System.getProperties());
	        properties.load(istream);
	        EngineConfiguration engine = EngineConfigurationFactoryFinder.newFactory().getClientEngineConfig();
	        SimpleProvider provider = new SimpleProvider(engine);
	        provider.deployTransport("http", new CommonsHTTPSender());
	        MantisConnectLocator locator = new MantisConnectLocator(provider);
//	        locator.setMantisConnectPortEndpointAddress(properties.getProperty("host"));
	        String user = properties.getProperty("username");
		    String pass = properties.getProperty("password");
		    
		    
			MantisConnectPortType porttype =locator.getMantisConnectPort();
			FilterData[] filters = porttype.mc_filter_get(user,pass, new BigInteger("1"));
			FilterData m4=null;
			for(FilterData tmp:filters) {
				if(tmp.getName().equals("M4-filter")) m4=tmp;
			}
			System.out.println(m4.getName());
			System.out.println(m4.getId());
			issues = porttype.mc_filter_get_issues(user,pass, new BigInteger("1"), m4.getId(), 
					new BigInteger("1"), new BigInteger("15"));
			System.out.println(issues.length+" found!");
			
		} catch(RemoteException ex) {
			ex.printStackTrace();
		} catch(ServiceException ex) {
			ex.printStackTrace();
		}catch (IOException ex) {
			ex.printStackTrace();
	    }
		
		return issues;
	}

}
