package org.openengsb.mantis.util;

import javax.jbi.messaging.NormalizedMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.openengsb.issues.common.pojos.IssueCreateMessage;
import org.openengsb.issues.common.pojos.IssueDataType;
import org.openengsb.issues.common.pojos.IssueDeleteMessage;
import org.openengsb.issues.common.pojos.IssueGetMessage;
import org.openengsb.issues.common.pojos.IssueUpdateMessage;
import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.IssueNoteData;

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

	public static IssueData convertIssueData(IssueDataType issue) {
		IssueData issueData = new IssueData();
	
		issueData.setId(issue.getId());
		issueData.setProject(issue.getProject());
		issueData.setSummary(issue.getSummary());
		issueData.setVersion(issue.getVersion());
		issueData.setDescription(issue.getDescription());
		issueData.setPriority(issue.getPriority());
		issueData.setSeverity(issue.getSeverity());
		issueData.setHandler(issue.getHandler());
		issueData.setStatus(issue.getStatus());
		issueData.setResolution(issue.getResolution());
		issueData.setReporter(issue.getReporter());
		if(issue.getDateSubmitted() != null){
			issueData.setDate_submitted(issue.getDateSubmitted()
					.toGregorianCalendar());
			
		}
		if(issue.getLastUpdated() != null) {
			issueData.setLast_updated(issue.getLastUpdated()
					.toGregorianCalendar());
			
		}
		if(issue.getNotes() != null) {
			issueData.setNotes((IssueNoteData[]) issue.getNotes()
					.getIssueNoteData().toArray());
		}
		return issueData;
	}

}
