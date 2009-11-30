package org.openengsb.issues.common.model;

import java.util.Date;

import org.openengsb.issues.common.enums.ViewState;

public class Comment {
	
	private String id;
    private String reporter;
    private String text;
    private ViewState viewState;
    private Date creationTime;
    private Date lastChange;
	
	/**
	 * Sets the id of the comment to identify it.
	 * @param id - the id identifies the comment
	 */
	public void setId(String id) {
		this.id=id;
	}
	
	/**
	 * Supplies the identifier of the comment
	 * @return id - identifier of the comment
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * Sets the reporter of the comment
	 * @param reporter - author of the comment
	 */
	public void setReporter(String reporter) {
		this.reporter=reporter;
	}
	
	/**
	 * Returns the reporter of the comment
	 * @return reporter - author of the comment
	 */
	public String getReporter() {
		return this.reporter;
	}
	
	/**
	 * Sets the text of the comment
	 * @param text - the content of the comment
	 */
	public void setText(String text) {
		this.text=text;
	}
	
	/**
	 * Returns the text of the comment
	 * @return text - content of the comment
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Sets the state of the view for the comment
	 * It decides if everybody is allowed to read it.
	 * The default value is public.
	 * @param viewState - uses the enumeration for setting
	 * @see ViewState
	 */
	public void setViewState(ViewState viewState) {
		this.viewState = viewState;
	}
	
	/**
	 * Returns the view state of the comment
	 * @return viewState - public, private or any
	 * @see ViewState
	 */
	public ViewState getViewState() {
		return this.viewState;
	}
	
	/**
	 * Sets the creation time of the comment due to the issue tracker
	 * @param creationTime - time the comment was created
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
	/**
	 * Returns the creation time of the comment
	 * @return creationTime - time the comment was created
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
	
}