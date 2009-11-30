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
package org.openengsb.issues.common.api;

import java.math.BigInteger;

import org.openengsb.issues.common.exceptions.IssueDomainException;
import org.openengsb.issues.common.model.Issue;

import biz.futureware.mantisconnect.IssueData;

/**
 * Interface describing a generic issue domain.
 */
public interface IssueDomain {

    /**
     * Creates an issue.
     *
     * @param issue The issue to create.
     * @param pass 
     * @param user 
     * @return ID of created issue
     */
    String createIssue(IssueData issue, String user, String pass) throws IssueDomainException;

    /**
     * Updates the given issue.
     *
     * @param issue The issue to update (the ID of the issue must be set).
     * @param issueId Id of the issue to update
     * @param user
     * @param pass
     * @throws IssueDomainException
     */
    void updateIssue(BigInteger issueId, IssueData issue, String user,
			String password) throws IssueDomainException;
    /**
     * Deletes the issue with the given ID.
     *
     * @param issueId ID of the issue being deleted.
     * @param issue Data of Issue
     * @param user
     * @param pass
     * @throws IssueDomainException
     */
	void deleteIssue(BigInteger issueId, IssueData issue, String user,
			String password) throws IssueDomainException;

	

}