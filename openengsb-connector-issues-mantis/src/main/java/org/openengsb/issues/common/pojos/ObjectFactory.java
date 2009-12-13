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
package org.openengsb.issues.common.pojos;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.openengsb.issues.common.pojos package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.openengsb.issues.common.pojos
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AttachmentDataArray }
     * 
     */
    public AttachmentDataArray createAttachmentDataArray() {
        return new AttachmentDataArray();
    }

    /**
     * Create an instance of {@link CustomFieldValueForIssueDataArray }
     * 
     */
    public CustomFieldValueForIssueDataArray createCustomFieldValueForIssueDataArray() {
        return new CustomFieldValueForIssueDataArray();
    }

    /**
     * Create an instance of {@link IssueNoteDataArray }
     * 
     */
    public IssueNoteDataArray createIssueNoteDataArray() {
        return new IssueNoteDataArray();
    }

    /**
     * Create an instance of {@link IssueDataType }
     * 
     */
    public IssueDataType createIssueDataType() {
        return new IssueDataType();
    }

    /**
     * Create an instance of {@link IssueDeleteMessage }
     * 
     */
    public IssueDeleteMessage createIssueDeleteMessage() {
        return new IssueDeleteMessage();
    }

    /**
     * Create an instance of {@link IssueGetMessage }
     * 
     */
    public IssueGetMessage createIssueGetMessage() {
        return new IssueGetMessage();
    }

    /**
     * Create an instance of {@link IssueCreateMessage }
     * 
     */
    public IssueCreateMessage createIssueCreateMessage() {
        return new IssueCreateMessage();
    }

    /**
     * Create an instance of {@link AccountDataType }
     * 
     */
    public AccountDataType createAccountDataType() {
        return new AccountDataType();
    }

    /**
     * Create an instance of {@link IssueUpdateMessage }
     * 
     */
    public IssueUpdateMessage createIssueUpdateMessage() {
        return new IssueUpdateMessage();
    }

}
