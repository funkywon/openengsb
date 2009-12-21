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
package org.openengsb.issues.common.util;

import javax.jbi.messaging.NormalizedMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.openengsb.issues.common.pojos.IssueCreateMessage;
import org.openengsb.issues.common.pojos.IssueDeleteMessage;
import org.openengsb.issues.common.pojos.IssueGetMessage;
import org.openengsb.issues.common.pojos.IssueUpdateMessage;

public class JAXBUtil {

	private static JAXBContext jaxbContext = null;
	static {
		try {
			jaxbContext = JAXBContext
					.newInstance("org.openengsb.issues.common.pojos");
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static IssueCreateMessage extractIssueCreateMessage(
			NormalizedMessage message)throws JAXBException {

		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		IssueCreateMessage issueCreateMessage = (IssueCreateMessage) unmarshaller
				.unmarshal(message.getContent());
		return issueCreateMessage;
	}

	public static IssueDeleteMessage extractIssueDeleteMessage(
			NormalizedMessage message) throws JAXBException {
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		IssueDeleteMessage issueDeleteMessage = (IssueDeleteMessage) unmarshaller
				.unmarshal(message.getContent());
		return issueDeleteMessage;
	}

	public static IssueGetMessage extractIssueGetMessage(NormalizedMessage message) throws JAXBException {
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		IssueGetMessage issueGetMessage = (IssueGetMessage) unmarshaller
				.unmarshal(message.getContent());
		return issueGetMessage;
	}

	public static IssueUpdateMessage extractIssueUpdateMessage(
			NormalizedMessage message) throws JAXBException {
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		IssueUpdateMessage issueUpdateMessage = (IssueUpdateMessage) unmarshaller
				.unmarshal(message.getContent());
		return issueUpdateMessage;
	}

}

