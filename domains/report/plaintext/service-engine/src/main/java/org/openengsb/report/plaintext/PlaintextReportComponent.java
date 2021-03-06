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
package org.openengsb.report.plaintext;

import java.util.List;

import org.apache.servicemix.common.DefaultComponent;

/**
 * @org.apache.xbean.XBean element="plaintextReportComponent"
 *                         description="Plaintext Report Component"
 */
public class PlaintextReportComponent extends DefaultComponent {
    private PlaintextReportEndpoint[] endpoints;

    public PlaintextReportEndpoint[] getEndpoints() {
        return this.endpoints;
    }

    public void setEndpoints(PlaintextReportEndpoint[] endpoints) {
        this.endpoints = endpoints;
    }

    @Override
    protected List<?> getConfiguredEndpoints() {
        return asList(this.endpoints);
    }

    @Override
    protected Class<?>[] getEndpointClasses() {
        return new Class[] { PlaintextReportEndpoint.class };
    }

}
