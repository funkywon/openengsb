/**

Copyright 2010 OpenEngSB Division, Vienna University of Technology

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

 */

package org.openengsb.issues.common.test.unit;

import org.openengsb.contextcommon.ContextHelper;
import org.openengsb.core.MessageProperties;
import org.openengsb.drools.IssueDomain;
import org.openengsb.issues.common.endpoints.AbstractIssueEndpoint;

/**
 * @org.apache.xbean.XBean element="create-issue"
 */
public class TestCreateIssueEndpoint extends AbstractIssueEndpoint {

    private IssueDomain issueDomain;

    public void setIssueDomain(IssueDomain issueDomain) {
        this.issueDomain = issueDomain;
    }

    public IssueDomain getIssueDomain() {
        return this.issueDomain;
    }

    @Override
    protected IssueDomain createIssueDomain() {
        return getIssueDomain();
    }

    @Override
    public IssueDomain getImplementation(ContextHelper contextHelper, MessageProperties msgProperties) {
        return null;
    }

}