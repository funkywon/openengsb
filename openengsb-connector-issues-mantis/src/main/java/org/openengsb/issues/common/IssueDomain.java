package org.openengsb.issues.common;

import org.openengsb.issues.common.exceptions.IssueDomainException;
import org.openengsb.issues.common.model.Issue;

/**
 * Interface describing a generic issue domain.
 */
public interface IssueDomain {

    /**
     * Creates an issue.
     *
     * @param issue The issue to create.
     * @return ID of created issue
     */
    String createIssue(Issue issue) throws IssueDomainException;

    /**
     * Updates the given issue.
     *
     * @param issue The issue to update (the ID of the issue must be set).
     * @throws IssueDomainException
     */
    void updateIssue(Issue issue) throws IssueDomainException;

    /**
     * Deletes the issue with the given ID.
     *
     * @param id ID of the issue being deleted.
     * @throws IssueDomainException
     */
    void deleteIssue(String id) throws IssueDomainException;

}