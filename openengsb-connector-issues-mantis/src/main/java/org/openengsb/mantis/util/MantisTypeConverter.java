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
package org.openengsb.mantis.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openengsb.drools.model.Comment;
import org.openengsb.drools.model.Issue;
import org.openengsb.drools.model.Project;
import org.openengsb.issues.common.util.TypeConverter;
import biz.futureware.mantisconnect.AccountData;
import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.IssueNoteData;
import biz.futureware.mantisconnect.ObjectRef;
import biz.futureware.mantisconnect.ProjectData;

public class MantisTypeConverter 
	implements TypeConverter<IssueData,
	biz.futureware.mantisconnect.IssueNoteData,
	biz.futureware.mantisconnect.ProjectData> {
	
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
	
	
	private Calendar convertDateToCalendar(Date date) {
	    
	    Calendar c = Calendar.getInstance();
	    if(date!=null) c.setTime(date);
        return c;
	}
	
    
	@Override
	public IssueData convertIssueDataToSpecific(Issue genericIssue) {
		IssueData issueData = new IssueData();
		
		if(genericIssue.getId()!=null) issueData.setId(new BigInteger(genericIssue.getId()));
		if(genericIssue.getProject()!=null) issueData.setProject(
		        generateObjectRef(genericIssue.getProject().getName(),Integer.parseInt(genericIssue.getProject().getId())));
		
		//TODO set category here (where to get?)
		issueData.setCategory("");
		
		issueData.setSummary(genericIssue.getSummary());
		issueData.setVersion(genericIssue.getAffectedVersion());
		issueData.setDescription(genericIssue.getDescription());
		issueData.setPriority(generateObjectRef("",genericIssue.getPriority()));
		issueData.setSeverity(generateObjectRef("",genericIssue.getSeverity()));
		issueData.setHandler(generateAccountData(genericIssue.getOwner()));
		
		issueData.setStatus(generateObjectRef("",genericIssue.getStatus()));
		issueData.setResolution(generateObjectRef("",genericIssue.getResolution()));
		issueData.setReporter(generateAccountData(genericIssue.getReporter()));
		
		if(genericIssue.getCreationTime() != null){
			issueData.setDate_submitted(convertDateToCalendar(genericIssue.getCreationTime()));
		}
		if(genericIssue.getLastChange() != null) {
			issueData.setLast_updated(convertDateToCalendar(genericIssue.getLastChange()));
		}
		if(genericIssue.getComments() != null && genericIssue.getComments().size()>0) {
			IssueNoteData[] notes = new IssueNoteData[genericIssue.getComments().size()];			
			for(int i=0;i<genericIssue.getComments().size();i++) {
				notes[i]=convertCommentToSpecific(genericIssue.getComments().get(i));
			}
			
			issueData.setNotes(notes);
		}
		return issueData;
	}
	
	
	
    @Override
	public Issue convertIssueDataToGeneric(biz.futureware.mantisconnect.IssueData mantisIssue) {
		Issue issue = new Issue();
		
		issue.setId(mantisIssue.getId().toString());
		if(mantisIssue.getProject()!=null) issue.setProject(generateProject(mantisIssue.getProject().getId().intValue(),mantisIssue.getProject().getName()));
//		issue.setCategory(mantisIssue.getCategory());
		issue.setSummary(mantisIssue.getSummary());
		issue.setAffectedVersion(mantisIssue.getVersion());
		issue.setDescription(mantisIssue.getDescription());
		issue.setPriority(mantisIssue.getPriority().getId().intValue());
		issue.setSeverity(mantisIssue.getSeverity().getId().intValue());
		issue.setOwner(mantisIssue.getHandler().getName());
		issue.setStatus(mantisIssue.getStatus().getId().intValue());
		issue.setResolution(mantisIssue.getResolution().getId().intValue());
		issue.setReporter(mantisIssue.getReporter().getName());
		
		if(mantisIssue.getDate_submitted() != null){
			issue.setCreationTime(mantisIssue.getDate_submitted().getTime());
			
		}
		if(mantisIssue.getLast_updated() != null) {
			issue.setLastChange(mantisIssue.getLast_updated().getTime());
			
		}
		
		if(mantisIssue.getNotes() != null&&mantisIssue.getNotes().length>0) {
		    List<Comment> commentsList = new ArrayList<Comment>();
			for(IssueNoteData note:mantisIssue.getNotes()){
				commentsList.add(convertCommentToGeneric(note));
			}
			issue.setComments(commentsList);
		}
		
	
		return issue;
	}
	
	@Override
	public Comment convertCommentToGeneric(biz.futureware.mantisconnect.IssueNoteData note) {
	    Comment comment = new Comment();
	    comment.setCreationTime(note.getDate_submitted().getTime());
	    comment.setId(note.getId().toString());
	    if(note.getLast_modified()!=null) comment.setLastChange(note.getLast_modified().getTime());
	    comment.setReporter(note.getReporter().getName());
	    comment.setText(note.getText());
	    if(note.getView_state()!=null) comment.setViewState(note.getView_state().getId().intValue());
	    return comment;
	}
	
	@Override
	public biz.futureware.mantisconnect.IssueNoteData convertCommentToSpecific(Comment comment){
		IssueNoteData specComment = new IssueNoteData();
		specComment.setDate_submitted(convertDateToCalendar(comment.getCreationTime()));
		specComment.setId(new BigInteger(comment.getId()));
		specComment.setLast_modified(convertDateToCalendar(comment.getLastChange()));
		specComment.setReporter(generateAccountData(comment.getReporter()));
		specComment.setText(comment.getText());
		return specComment;
		
	}
    @Override
    public Project convertProjectToGeneric(ProjectData specProject) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public ProjectData convertProjectToSpecific(Project genericProject) {
        // TODO Auto-generated method stub
        return null;
    }	
}