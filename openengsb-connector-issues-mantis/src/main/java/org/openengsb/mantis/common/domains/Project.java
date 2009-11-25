package org.openengsb.mantis.common.domains;

import java.io.File;
import java.util.List;

import org.openengsb.mantis.common.domains.enums.ProjectStatus;

public interface Project {
	
	/**
	 * Sets the id of the project to identify it.
	 * @param id - the id identifies the project
	 */
	public void setId(Long id);
	
	/**
	 * Supplies the identifier of the project
	 * @return id - identifier of the project
	 */
	public Long getId();
	
	/**
	 * Sets the name of the project in the form of a short
	 * @param name - short description
	 */
	public void setName(String name);
	
	/**
	 * Supplies the name of the project
	 * @return name - short description of the project
	 */
	public String getName();
	
	/**
	 * Sets the status of the project
	 * @param projectStatus - use the enumaration values for setting
	 * @see ProjectStatus
	 */
	public void setStatus(ProjectStatus projectStatus);
	
	/**
	 * Supplies the topically status of the project
	 * @return status - the topically status of the project
	 * @see ProjectStatus
	 */
	public ProjectStatus getStatus();
	
	/**
	 * Sets the description of the project
	 * @param description - a detailed description of the project
	 */
	public void setDescription(String description);
	
	/**
	 * Supplies the detailed description of the project
	 * @return description - in detailed form
	 */
	public String getDescription();
	
	/**
	 * Sets the URL of the project 
	 * @param url - place on the file system(remote in most cases)
	 */
	public void setUrl(File url);
	
	/**
	 * Supplies the URL of the project
	 * @return url - place on the file system
	 */
	public File getUrl();
	
	/**
	 * Sets the tasks for a project from the issue tracker
	 * @param tasks - a list of tasks
	 * @see Issue
	 */
	public void setIssues(List<Issue> tasks);
	
	/**
	 * Returns the tasks of the project
	 * @return tasks - reported tasks of the project
	 * @see Issue
	 */
	public List<Issue> getIssues();
}