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
