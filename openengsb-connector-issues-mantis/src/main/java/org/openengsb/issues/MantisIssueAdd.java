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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.xml.rpc.ServiceException;


import biz.futureware.mantisconnect.*;
public class MantisIssueAdd {

	
	public static void main(String[] args) {
		MantisConnectLocator locator = new MantisConnectLocator();
		
		try {
			InputStream istream = MantisIssueAdd.class.getResourceAsStream("config.properties");
	        System.out.println(istream.toString());
	        Properties properties = new Properties(System.getProperties());
	        properties.load(istream);
	        locator.setMantisConnectPortEndpointAddress(properties.getProperty("host"));

	        String user = properties.getProperty("username");
		    String pass = properties.getProperty("password");
		    

			MantisConnectPortType porttype =locator.getMantisConnectPort();
			
//			IssueData data = porttype.mc_issue_get("administrator", "root", new BigInteger("1"));
			IssueData data1 = new IssueData();
			data1.setCategory("cat1");
			data1.setDescription("This is a test issue.");
			data1.setSummary("this is the test summary of data.");
			data1.setProject(new ObjectRef(new BigInteger("1"),"enbsb"));
			porttype.mc_issue_add(user,pass, data1);
			
		} catch(RemoteException ex) {
			ex.printStackTrace();
		} catch(ServiceException ex) {
			ex.printStackTrace();
		}catch (IOException ex) {
			ex.printStackTrace();
	    }
	}

}
