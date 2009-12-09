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

package org.openengsb.mantis.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

import javax.xml.rpc.ServiceException;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.EngineConfigurationFactoryFinder;
import org.apache.axis.configuration.SimpleProvider;
import org.apache.axis.transport.http.CommonsHTTPSender;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openengsb.issues.MantisIssueAdd;
import org.openengsb.mantis.commands.IssueCreateCommand;

import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.MantisConnectLocator;
import biz.futureware.mantisconnect.MantisConnectPortType;
import biz.futureware.mantisconnect.ObjectRef;

public class IssueCreateCommandTest {

	private static final String USER_KEY = "user";
	private static final String PASSWORD_KEY = "password";
	private static final String HOST_KEY = "host";

	private static final String USER = "administrator";
	private static final String PASS = "root";
	private static final String HOST = "http://localhost/mantisbt-1.1.8/api/soap/mantisconnect.php";

	
	private static Preferences prefs=null;
	MantisConnectLocator locator;
	InputStream istream;
	String user;
	String pass;
	String host;

	@BeforeClass
	public static void setUpBeforeClass() {
		prefs = Preferences.userRoot().node("/org/openengsb/mantis/commands");
		prefs.put(USER_KEY, USER);
		prefs.put(PASSWORD_KEY, PASS);
		prefs.put(HOST_KEY, HOST);
		
		try {
			FileOutputStream fos = new FileOutputStream("prefs.out");
			prefs.exportNode(fos);
		} catch (Exception e) {
			System.err.println("Unable to export nodes: " + e);
		}
	}

	@Before
	public void setUp() throws IOException {
		
		try {
			Preferences.importPreferences(new FileInputStream(new File("prefs.out")));
		} catch (InvalidPreferencesFormatException e) {
			
			e.printStackTrace();
		}
    	
		EngineConfiguration engine = EngineConfigurationFactoryFinder.newFactory().getClientEngineConfig();
        SimpleProvider provider = new SimpleProvider(engine);
        provider.deployTransport("http", new CommonsHTTPSender());
        
    	prefs=Preferences.userNodeForPackage(getClass());
    	user=prefs.get(USER_KEY,"");
		pass=prefs.get(PASSWORD_KEY,"");
		host=prefs.get(HOST_KEY,"");
		locator = new MantisConnectLocator(provider);
		locator.setMantisConnectPortEndpointAddress(host);
		
		
		

	}

	@AfterClass
	public static void tearDownAfterClass() {
		try {
			prefs.removeNode();
		} catch (BackingStoreException e) {
			System.err.println("Unable to access backing store: " + e);
		}

	}

	@After
	public void tearDown() {

	}

	@Test
	public void createIssue() throws Exception {
		try {

			MantisConnectPortType porttype = locator.getMantisConnectPort();

			
			IssueData data1 = new IssueData();
			data1.setCategory("cat1");
			data1.setDescription("This is a unit test.");
			data1.setSummary("this is the test summary of data.");
			data1.setProject(new ObjectRef(new BigInteger("1"), "engsb"));
			porttype.mc_issue_add(user, pass, data1);

		} catch (RemoteException ex) {
			ex.printStackTrace();
		} catch (ServiceException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
