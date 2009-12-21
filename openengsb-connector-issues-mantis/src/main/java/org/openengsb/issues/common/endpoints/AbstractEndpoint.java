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
package org.openengsb.issues.common.endpoints;


import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.NormalizedMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.servicemix.common.endpoints.ProviderEndpoint;

public abstract class AbstractEndpoint extends ProviderEndpoint {
    

    private Log log = null;

    /* ProviderEndpoint overrides */

    @Override
    protected void processInOnly(MessageExchange exchange, NormalizedMessage in) throws Exception {
        getLog().info(getEndpointBehaviour());
        try {
            if (exchange.getStatus() == ExchangeStatus.ACTIVE) {
                // call template method
                processInOnlyRequest(exchange, in);
            } else {
                getLog().warn("Exchange was not ACTIVE. Ignoring it.");
            }
        } catch (Exception exception) {
            getLog().error("Encountered an error while " + getEndpointBehaviour(), exception);
            throw exception;
        }
    }

    @Override
    protected void processInOut(MessageExchange exchange, NormalizedMessage in, NormalizedMessage out) throws Exception {
        getLog().info(getEndpointBehaviour());
        try {
            if (exchange.getStatus() == ExchangeStatus.ACTIVE) {
                // call template method
                processInOutRequest(exchange, in, out);
            } else {
                getLog().warn("Exchange was not ACTIVE. Ignoring it.");
            }
        } catch (Exception exception) {
            getLog().error("Encountered an error while " + getEndpointBehaviour(), exception);
            throw exception;
        }
    }

    /* end ProviderEndpoint overrides */

    /* template methods and default implementations */

    /**
     * Returns a description for the Endpoint's behavior. This is used for
     * logging purposes. If you implement this method, describe, what the
     * Endpoint is about to do. Something like "Adding files".
     */
    protected abstract String getEndpointBehaviour();

    /**
     * Template method that is called from the implementation for processInOut.
     * The default implementation just calls the super-method of processInOut
     * which in turn throws an Exception telling the caller, that this MEP is
     * not supported.
     * 
     * @param exchange see {@link ProviderEndpoint#processInOut}
     * @param in see {@link ProviderEndpoint#processInOut}
     * @param out see {@link ProviderEndpoint#processInOut}
     * @throws Exception see {@link ProviderEndpoint#processInOut}
     */
    protected void processInOutRequest(MessageExchange exchange, NormalizedMessage in, NormalizedMessage out)
            throws Exception {
        super.processInOut(exchange, in, out);
    }

    /**
     * Template method that is called from the implementation for processInOnly.
     * The default implementation just calls the super-method of processInOut
     * which in turn throws an Exception telling the caller, that this MEP is
     * not supported.
     * 
     * @param exchange see {@link ProviderEndpoint#processInOut}
     * @param in see {@link ProviderEndpoint#processInOut}
     * @throws Exception see {@link ProviderEndpoint#processInOut}
     */
    protected void processInOnlyRequest(MessageExchange exchange, NormalizedMessage in) throws Exception {
        super.processInOnly(exchange, in);
    }

    /* end template methods and default implementations */

    

    /**
     * This method may seem not very useful, since logger is protected already
     * and could be accessed directly. It exists to change loggers easily. I.e.
     * to exchange the jbi-default-logger with a self-instantiated one.
     * 
     * @return
     */
    protected Log getLog() {
        if (this.logger != null) {
            return this.logger;
        }

        if (this.log == null) {
            this.log = LogFactory.getLog(this.getClass());
        }

        return this.log;
    }

    /* end helpers */
}
