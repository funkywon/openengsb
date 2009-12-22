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

import java.util.List;

import org.openengsb.issues.common.pojos.IssueDataType;
import org.openengsb.issues.common.util.TypeConverter;
import biz.futureware.mantisconnect.AttachmentData;
import biz.futureware.mantisconnect.IssueData;
import biz.futureware.mantisconnect.IssueNoteData;

public class MantisTypeConverter 
	implements TypeConverter<IssueData,
	biz.futureware.mantisconnect.AccountData,
	biz.futureware.mantisconnect.ObjectRef,
	biz.futureware.mantisconnect.IssueNoteData,
	biz.futureware.mantisconnect.AttachmentData> {
	
	@Override
	public biz.futureware.mantisconnect.ObjectRef convertObjectRefToSpecific(org.openengsb.issues.common.pojos.ObjectRef ob) {
		if(ob==null)
			return null;
		biz.futureware.mantisconnect.ObjectRef object = new biz.futureware.mantisconnect.ObjectRef();
		object.setId(ob.getId());
		object.setName(ob.getName());
		return object;
	}
	
	@Override
	public biz.futureware.mantisconnect.AccountData convertAccountDataToSpecific(org.openengsb.issues.common.pojos.AccountData ac) {
		if(ac==null)
			return null;
		biz.futureware.mantisconnect.AccountData account = new biz.futureware.mantisconnect.AccountData();
		account.setEmail(ac.getEmail());
		account.setId(ac.getId());
		account.setName(ac.getName());
		account.setReal_name(ac.getRealName());
		return account;
	}
	
	@Override
	public IssueData convertIssueDataToSpecific(IssueDataType genericIssue) {
		IssueData issueData = new IssueData();
		
		issueData.setId(genericIssue.getId());
		issueData.setProject(convertObjectRefToSpecific(genericIssue.getProject()));
		issueData.setCategory(genericIssue.getCategory());
		issueData.setSummary(genericIssue.getSummary());
		issueData.setVersion(genericIssue.getVersion());
		issueData.setDescription(genericIssue.getDescription());
		issueData.setPriority(convertObjectRefToSpecific(genericIssue.getPriority()));
		issueData.setSeverity(convertObjectRefToSpecific(genericIssue.getSeverity()));
		issueData.setHandler(convertAccountDataToSpecific(genericIssue.getHandler()));
		issueData.setStatus(convertObjectRefToSpecific(genericIssue.getStatus()));
		issueData.setResolution(convertObjectRefToSpecific(genericIssue.getResolution()));
		issueData.setReporter(convertAccountDataToSpecific(genericIssue.getReporter()));
		
		if(genericIssue.getDateSubmitted() != null){
			issueData.setDate_submitted(genericIssue.getDateSubmitted());
			
		}
		if(genericIssue.getLastUpdated() != null) {
			issueData.setLast_updated(genericIssue.getLastUpdated());
			
		}
		if(genericIssue.getNotes() != null&&genericIssue.getNotes().getIssueNoteData().size()>0) {
			IssueNoteData[] notes = new IssueNoteData[genericIssue.getNotes().getIssueNoteData().size()];
			
			List<org.openengsb.issues.common.pojos.IssueNoteData> list =  genericIssue.getNotes().getIssueNoteData();
			for(int i=0;i<list.size();i++) {
				notes[i]=convertIssueNoteToSpecific(list.get(i));
			}
			
			issueData.setNotes(notes);
		}
		if(genericIssue.getAttachments() != null) {
			issueData.setAttachments((AttachmentData[]) genericIssue.getAttachments()
					.getAttachmentData().toArray());
		}
		return issueData;
	}
	
	@Override
	public org.openengsb.issues.common.pojos.IssueDataType convertIssueDataToGeneric(biz.futureware.mantisconnect.IssueData issue) {
		IssueDataType issueData = new IssueDataType();
		
		issueData.setId(issue.getId());
		issueData.setProject(convertObjectRefToGeneric(issue.getProject()));
		issueData.setCategory(issue.getCategory());
		issueData.setSummary(issue.getSummary());
		issueData.setVersion(issue.getVersion());
		issueData.setDescription(issue.getDescription());
		issueData.setPriority(convertObjectRefToGeneric(issue.getPriority()));
		issueData.setSeverity(convertObjectRefToGeneric(issue.getSeverity()));
		issueData.setHandler(convertAccountDataToGeneric(issue.getHandler()));
		issueData.setStatus(convertObjectRefToGeneric(issue.getStatus()));
		issueData.setResolution(convertObjectRefToGeneric(issue.getResolution()));
		issueData.setReporter(convertAccountDataToGeneric(issue.getReporter()));
		
		if(issue.getDate_submitted() != null){
			issueData.setDateSubmitted(issue.getDate_submitted());
			
		}
		if(issue.getLast_updated() != null) {
			issueData.setLastUpdated(issue.getLast_updated());
			
		}
		if(issue.getNotes() != null&&issue.getNotes().length>0) {
			for(IssueNoteData note:issue.getNotes()){
				issueData.getNotes().getIssueNoteData().add(convertIssueNoteToGeneric(note));
			}
		}
		
		if(issue.getAttachments() != null && issue.getAttachments().length>0) {
			for(AttachmentData attachment:issue.getAttachments()){
				issueData.getAttachments().getAttachmentData().add(convertAttachmentToGeneric(attachment));
			}
		}
		return null;
	}
	
	@Override
	public org.openengsb.issues.common.pojos.AttachmentData convertAttachmentToGeneric(
			biz.futureware.mantisconnect.AttachmentData attachment) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public org.openengsb.issues.common.pojos.IssueNoteData convertIssueNoteToGeneric(
			biz.futureware.mantisconnect.IssueNoteData note) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public biz.futureware.mantisconnect.IssueNoteData convertIssueNoteToSpecific(
			org.openengsb.issues.common.pojos.IssueNoteData note){
		IssueNoteData specNote = new IssueNoteData();
		specNote.setDate_submitted(note.getDateSubmitted());
		specNote.setId(note.getId());
		specNote.setLast_modified(note.getLastModified());
		specNote.setText(note.getText());
		return specNote;
		
	}
	
	@Override
	public org.openengsb.issues.common.pojos.ObjectRef convertObjectRefToGeneric(biz.futureware.mantisconnect.ObjectRef ob) {
		if(ob==null)
			return null;
		org.openengsb.issues.common.pojos.ObjectRef object = new org.openengsb.issues.common.pojos.ObjectRef();
		object.setId(ob.getId());
		object.setName(ob.getName());
		return object;
		
	}
	
	@Override
	public org.openengsb.issues.common.pojos.AccountData convertAccountDataToGeneric(biz.futureware.mantisconnect.AccountData ac) {
		if(ac==null)
			return null;
		org.openengsb.issues.common.pojos.AccountData account = new org.openengsb.issues.common.pojos.AccountData();
		account.setEmail(ac.getEmail());
		account.setId(ac.getId());
		account.setName(ac.getName());
		account.setRealName(ac.getReal_name());
		return account;
	}
}
