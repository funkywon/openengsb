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
package org.openengsb.issues.common.api.model;

import java.util.Date;
import java.util.List;

import org.openengsb.issues.common.api.enums.IssuePriority;
import org.openengsb.issues.common.api.enums.IssueResolution;
import org.openengsb.issues.common.api.enums.IssueSeverity;
import org.openengsb.issues.common.api.enums.IssueStatus;

public class Issue {
	
    private String id;
    private String summary;
    private String description;
    private String reporter;
    private String owner;
    @SuppressWarnings("unused")
	private String affectedVersion;
    private IssuePriority priority;
    private IssueSeverity severity;
    private IssueResolution resolution;
    private IssueStatus status;
//    private IssueType type;
    private List<Comment> comments;
    private Project project;
    private Date creationTime;
    private Date lastChange;
	
	
	/**
	 * Sets the id of the task to identify it.
	 * @param id - the id identifies the task
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Supplies the identifier of the task
	 * @return id - identifier of the task
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * Sets the summary, a short description of the task
	 * @param summary - short description
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	/**
	 * Returns the summary of the task
	 * @return summary - short task description
	 */
	public String getSummary() {
		return this.summary;
	}
	
	/**
	 * Sets the detailed description of the task
	 * @param description - detailed description of the task
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Return the detailed task description
	 * @return description - detailed description
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Sets the priority of the task
	 * @param priority - uses the enumeration values for setting
	 * @see IssuePriority
	 */
	public void setPriority(IssuePriority priority) {
		this.priority = priority;
	}
	
	/**
	 * Returns the priority of the task
	 * @return priority - the priority of the task
	 * @see IssuePriority
	 */
	public IssuePriority getPriority() {
		return this.priority;
	}
	
	/**
	 * Sets the severity of the task
	 * @param severity - uses the enumeration values for setting
	 * @see IssueSeverity
	 */
	public void setSeverity(IssueSeverity severity) {
		this.severity = severity;
	}
	
	/**
	 * Returns the severity of the task
	 * @return severity - the severity of the task
	 * @see IssueSeverity
	 */
	public IssueSeverity getSeverity() {
		return this.severity;
	}
	
	/**
	 * Sets the resolution of the task
	 * @param resolution - uses the enumeration values for setting
	 * @see IssueResolution
	 */
	public void setResolution(IssueResolution resolution) {
		this.resolution = resolution;
	}
	
	/**
	 * Returns the resolution of the task
	 * @return resolution - the resolution of the task
	 * @see IssueResolution
	 */
	public IssueResolution getResolution() {
		return this.resolution;
	}
	
	/**
	 * Sets the status of the task
	 * @param status - uses the enumeration values for setting
	 * @see IssueStatus
	 */
	public void setStatus(IssueStatus status) {
		this.status = status;
	}
	
	/**
	 * Returns the status of the task
	 * @return status - the status of the task
	 * @see IssueStatus
	 */
	public IssueStatus getStatus() {
		return this.status;
	}
	
	/**
	 * Sets the creation time of the task due to the issue tracker
	 * @param creationTime - time the task was created
	 */
	public void setCreationTime(Date creationTime){
		this.creationTime = creationTime;
	}
	
	/**
	 * Returns the creation time of the task
	 * @return creationTime - time the task was created
	 */
	public Date getCreationTime() {
		return this.creationTime;
	}
	
	/**
	 * Sets the time the last change was updated
	 * @param lastChange - the time the last change was updated
	 */
	public void setLastChange(Date lastChange) {
		this.lastChange = lastChange;
	}
	
	/**
	 * Returns the last change time
	 * @return lastChange - the time the last change was committed
	 */
	public Date getLastChange() {
		return this.lastChange;
	}
	
	/**
	 * Sets the reporter(user of the issue tracker) of the task
	 * @param reporter - the author of the task
	 */
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	
	/**
	 * Returns the reporter of the task
	 * @return reporter - author of the task
	 */
	public String getReporter() {
		return this.reporter;
	}
	
	/**
	 * Sets the owner of the task
	 * @param owner - the person who has assigned to
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	/**
	 * Return the owner of the task
	 * @return owner - person who has assigned to
	 */
	public String getOwner() {
		return this.owner;
	}
	
	/**
	 * Sets the project of task
	 * @param project - the project the task is being filed against
	 * @see Project
	 */
	public void setProject(Project project) {
		this.project = project;
	}
	
	/**
	 * Returns the project the task is being filed against
	 * @return project - the project the task is being filed against
	 * @see Project
	 */
	public Project getProject() {
		return this.project;
	}
	
	/**
	 * Sets the comments that are added to the task
	 * At the creation time of course null
	 * @param comments - a list of comments, default null
	 * @see Comment
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	/**
	 * Returns a list of comments added to the task
	 * @return comments - a list of comments
	 * @see Comment
	 */
	public List<Comment> getComments() {
		return this.comments;
	}
}