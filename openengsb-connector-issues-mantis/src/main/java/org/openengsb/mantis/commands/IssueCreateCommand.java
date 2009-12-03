package org.openengsb.mantis.commands;

import org.apache.commons.logging.Log;
import org.openengsb.issues.common.api.IssueHandler;

public class IssueCreateCommand implements IssueCommand {

	
	IssueHandler handler;
	Log log;
	
	public IssueCreateCommand(IssueHandler handler, Log log) {
		this.handler = handler;
		this.log = log;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
