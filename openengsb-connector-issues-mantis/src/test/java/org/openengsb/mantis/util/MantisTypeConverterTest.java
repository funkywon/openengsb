package org.openengsb.mantis.util;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openengsb.drools.model.Comment;
import org.openengsb.drools.model.Issue;
import org.openengsb.drools.model.Project;

import biz.futureware.mantisconnect.AccountData;
import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.IssueNoteData;
import biz.futureware.mantisconnect.ObjectRef;

public class MantisTypeConverterTest {
	
	private MantisTypeConverter typeConverter;
	
	@Before
	public void setup() {
		typeConverter = new MantisTypeConverter();
	}
	
	private ObjectRef generateObjectRef(String name, int id) {
	    ObjectRef or = new ObjectRef();
	    or.setId(BigInteger.valueOf(id));
	    or.setName(name);
	    return or;
	}
	private AccountData generateAccountData(String owner) {
        AccountData account = new AccountData();
        account.setName(owner);
        return account;
    }
    private Project generateProject(int id, String name) {
        Project p = new Project();
        p.setId(""+id);
        p.setName(name);
        return p;
    }
	
	@Test
	public void convertIssueDataToSpecific() {
		Issue genericIssue = generateGenericIssue();
		IssueData mantisIssue = typeConverter.convertIssueDataToSpecific(genericIssue);
		
		assertIssues(mantisIssue, genericIssue);
		
		
	}
	@Test
	public void convertIssueDataToGeneric() {
		IssueData mantisIssue = generateMantisIssue();
		Issue genericIssue = typeConverter.convertIssueDataToGeneric(mantisIssue);
		assertIssues(mantisIssue,genericIssue);
	}
	
	private void assertIssues(IssueData mantisIssue, Issue genericIssue) {
		assertEquals(genericIssue.getId(),mantisIssue.getId().toString());
		assertEquals(genericIssue.getSummary(),mantisIssue.getSummary());
		assertEquals(genericIssue.getDescription(),mantisIssue.getDescription());
		
		assertNotNull(mantisIssue.getReporter());
		assertEquals(genericIssue.getReporter(),mantisIssue.getReporter().getName());
		assertNotNull(mantisIssue.getHandler());
		assertEquals(genericIssue.getOwner(),mantisIssue.getHandler().getName());
		assertEquals(genericIssue.getAffectedVersion(),mantisIssue.getVersion());
	    assertNotNull(mantisIssue.getPriority());
		assertEquals(genericIssue.getPriority(),mantisIssue.getPriority().getId().intValue());
		assertNotNull(mantisIssue.getSeverity());
		assertEquals(genericIssue.getSeverity(),mantisIssue.getSeverity().getId().intValue());
		assertNotNull(mantisIssue.getResolution());
		assertEquals(genericIssue.getResolution(),mantisIssue.getResolution().getId().intValue());
		assertNotNull(mantisIssue.getStatus());
		assertEquals(genericIssue.getStatus(),mantisIssue.getStatus().getId().intValue());
		
	    assertNotNull(mantisIssue.getDate_submitted());
		assertEquals(genericIssue.getCreationTime(),mantisIssue.getDate_submitted().getTime());
		assertNotNull(mantisIssue.getLast_updated());
		assertEquals(genericIssue.getLastChange(),mantisIssue.getLast_updated().getTime());
		assertNotNull(mantisIssue.getNotes());
		assertNotes(mantisIssue.getNotes(),genericIssue.getComments(),genericIssue.getComments().size());
		
	}

	private IssueData generateMantisIssue() {
		IssueData issue = new IssueData();
		BigInteger id = new BigInteger("234");
		Calendar date_submitted = Calendar.getInstance();
	    Calendar last_updated = Calendar.getInstance();
	    ObjectRef project = null;
	    String category = "test category";
	    ObjectRef priority = generateObjectRef("", 44);
	    ObjectRef severity = generateObjectRef("", 55);
	    ObjectRef status = generateObjectRef("", 22);
	    AccountData reporter = generateAccountData("test reporter");
	    String summary = "test summary";
	    String version = "test version";
	    
	    AccountData handler = generateAccountData("test handler");
	    ObjectRef resolution = generateObjectRef("", 777);
	    String description = "test description";
	    String steps_to_reproduce = "steps to reproduce";
	    String additional_information = "additional info";
	    IssueNoteData[] notes = new IssueNoteData[2];
	    
	    IssueNoteData note1 = new IssueNoteData();
	    note1.setDate_submitted(Calendar.getInstance());
	    note1.setId(new BigInteger("445"));
	    note1.setLast_modified(Calendar.getInstance());
	    note1.setReporter(generateAccountData("sepp"));
	    note1.setText("note 1");
	    note1.setView_state(generateObjectRef("public", 1));
	    
	    IssueNoteData note2 = new IssueNoteData();
	    note2.setDate_submitted(Calendar.getInstance());
	    note2.setId(new BigInteger("445"));
	    note2.setLast_modified(Calendar.getInstance());
	    note2.setReporter(generateAccountData("sepp"));
	    note2.setText("note 1");
	    note2.setView_state(generateObjectRef("public", 1));
	    notes[0]=note1;
	    notes[1]=note2;
	    
	    issue.setId(id);
	    issue.setDate_submitted(date_submitted);
	    issue.setLast_updated(last_updated);
	    issue.setCategory(category);
	    issue.setPriority(priority);
	    issue.setSeverity(severity);
	    issue.setStatus(status);
	    issue.setReporter(reporter);
	    issue.setSummary(summary);
	    issue.setVersion(version);
	    issue.setHandler(handler);
	    issue.setResolution(resolution);
	    issue.setDescription(description);
	    issue.setSteps_to_reproduce(steps_to_reproduce);
	    issue.setAdditional_information(additional_information);
	    issue.setNotes(notes);
	    issue.setProject(project);
	    return issue;
		
	}

	private void assertNotes(IssueNoteData[] notes, List<Comment> comments, int size) {
		for(int i=0;i<size;i++) {
			assertEquals(notes[i].getDate_submitted().getTime(),comments.get(i).getCreationTime());
			assertEquals(notes[i].getId().toString(),comments.get(i).getId());
			assertEquals(notes[i].getLast_modified().getTime(),comments.get(i).getLastChange());
			assertEquals(notes[i].getReporter().getName(),comments.get(i).getReporter());
			assertEquals(notes[i].getText(),comments.get(i).getText());
		}
		
	}
	private Issue generateGenericIssue() {
		Issue issue = new Issue();
		
		String id = "123";
	    String summary = "test summary";
	    String description = "test description";
	    String reporter = "test reporter";
	    String owner = "test owner";
	    String affectedVersion = "test version";
	    int priority = 55;
	    int severity = 33;
	    int resolution = 345;
	    int status = 67;
	    int type = 2;
	    Date creationTime = new GregorianCalendar(2010,1,11,10,00).getTime();
	    Date lastChange = new GregorianCalendar(2010,1,15,11,00).getTime();
	    List<Comment> comments = new ArrayList<Comment>();
	    
	    
	    Comment c1 = new Comment();
	    c1.setCreationTime(new GregorianCalendar(2010,1,12,12,10).getTime());
	    c1.setId("1");
	    c1.setLastChange(new GregorianCalendar(2010,1,14,12,00).getTime());
	    c1.setReporter("sepp");
	    c1.setText("first comment");
	    
	    Comment c2 = new Comment();
	    c2.setCreationTime(new GregorianCalendar(2010,1,14,22,10).getTime());
	    c2.setId("2");
	    c2.setLastChange(new GregorianCalendar(2010,1,15,11,00).getTime());
	    c2.setReporter("alois");
	    c2.setText("second comment");
	    
	    comments.add(c1);
	    comments.add(c2);

	    Project project = generateProject(43,"test project name");
	    
	    issue.setId(id);
	    issue.setSummary(summary);
	    issue.setDescription(description);
	    issue.setReporter(reporter);
	    issue.setOwner(owner);
	    issue.setAffectedVersion(affectedVersion);
	    issue.setPriority(priority);
	    issue.setSeverity(severity);
	    issue.setResolution(resolution);
	    issue.setStatus(status);
	    issue.setType(type);
	    issue.setCreationTime(creationTime);
	    issue.setLastChange(lastChange);
	    issue.setComments(comments);
	    issue.setProject(project);

	    return issue;
	}
}
