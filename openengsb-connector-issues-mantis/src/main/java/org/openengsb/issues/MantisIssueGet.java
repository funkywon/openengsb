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
package org.openengsb.issues;

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
import biz.futureware.mantisconnect.ObjectRef;

public class MantisIssueGet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		try {
			InputStream istream = MantisIssueGet.class.getResourceAsStream("config.properties");
			System.out.println("update");
	        Properties properties = new Properties(System.getProperties());
	        properties.load(istream);
	        System.out.println(1);
	        EngineConfiguration engine = EngineConfigurationFactoryFinder.newFactory().getClientEngineConfig();
	        System.out.println(2);
	        SimpleProvider provider = new SimpleProvider(engine);
	        System.out.println(3);
	        provider.deployTransport("http", new CommonsHTTPSender());
	        System.out.println(4);
	        MantisConnectLocator locator = new MantisConnectLocator(provider);
	        System.out.println(5);
//	        locator.setMantisConnectPortEndpointAddress(properties.getProperty("host"));
	        System.out.println(6);
	        String user = properties.getProperty("username");
		    String pass = properties.getProperty("password");
		    
		    
			MantisConnectPortType porttype =locator.getMantisConnectPort();
			System.out.println(7);
			FilterData[] filters = porttype.mc_filter_get(user,pass, new BigInteger("1"));
			System.out.println(8);
			FilterData m4=null;
			System.out.println(9);
			for(FilterData tmp:filters) {
				System.out.println(10);
				if(tmp.getName().equals("M4-filter")) m4=tmp;
			}
			System.out.println(m4.getName());
			System.out.println(m4.getId());
			System.out.println(11);
			IssueData[] issues = porttype.mc_filter_get_issues(user,pass, new BigInteger("1"), m4.getId(), 
					new BigInteger("1"), new BigInteger("15"));
			System.out.println(12);
			System.out.println(issues.length+" found!");
			
		} catch(RemoteException ex) {
			ex.printStackTrace();
		} catch(ServiceException ex) {
			ex.printStackTrace();
		}catch (IOException ex) {
			ex.printStackTrace();
	    }

	}

}
