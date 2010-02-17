package org.openengsb.issues.mantis;

import javax.xml.namespace.QName;

import org.openengsb.contextcommon.ContextHelper;
import org.openengsb.core.endpoints.ForwardEndpoint;
import org.openengsb.drools.IssueDomain;

public class IssueForwardEndpoint extends ForwardEndpoint<IssueDomain> {

	@Override
	protected QName getForwardTarget(ContextHelper contextHelper) {
		String defaultName = contextHelper.getValue("issue/default");
        String serviceName = contextHelper.getValue("issue/" + defaultName + "/servicename");
        String namespace = contextHelper.getValue("issue/" + defaultName + "/namespace");
        return new QName(namespace, serviceName);
	}

}
