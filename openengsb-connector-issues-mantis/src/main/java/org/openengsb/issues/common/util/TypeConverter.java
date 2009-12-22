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

import org.openengsb.issues.common.pojos.AccountData;
import org.openengsb.issues.common.pojos.AttachmentData;
import org.openengsb.issues.common.pojos.IssueDataType;
import org.openengsb.issues.common.pojos.IssueNoteData;
import org.openengsb.issues.common.pojos.ObjectRef;




public interface TypeConverter<SpecificIssue,SpecificAccountData,SpecificObjectRef,SpecificNote,SpecificAttachment> {
	public SpecificObjectRef convertObjectRefToSpecific(ObjectRef ob);
	
	public SpecificAccountData convertAccountDataToSpecific(AccountData ac);
	
	public SpecificIssue convertIssueDataToSpecific(IssueDataType genericIssue);
	
	public IssueDataType convertIssueDataToGeneric(SpecificIssue issue);
	
	public AttachmentData convertAttachmentToGeneric(SpecificAttachment attachment);
	
	public SpecificNote convertIssueNoteToSpecific(IssueNoteData note);
	public IssueNoteData convertIssueNoteToGeneric(SpecificNote note);
	
	public ObjectRef convertObjectRefToGeneric(SpecificObjectRef ob);
	
	public AccountData convertAccountDataToGeneric(SpecificAccountData ac);

	

}
