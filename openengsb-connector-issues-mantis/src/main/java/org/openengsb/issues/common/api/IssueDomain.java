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