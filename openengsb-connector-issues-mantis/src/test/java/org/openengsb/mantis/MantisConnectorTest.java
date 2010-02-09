package org.openengsb.mantis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnit44Runner;
import org.openengsb.mantis.util.MantisTypeConverter;

import biz.futureware.mantisconnect.MantisConnectPortType;

@RunWith(MockitoJUnit44Runner.class)
public class MantisConnectorTest {

    private MantisIssueHandlerImpl mantisConnector;
    @Mock private MantisTypeConverter typeConverter;
    @Mock private MantisConnectPortType portType;
    
    @Before
    public void setup() {
        mantisConnector = new MantisIssueHandlerImpl("", "", "");
        mantisConnector.setPorttype(portType);
        mantisConnector.setTypeConverter(typeConverter);
        
        
    }
    
    @Test
    public void createIssue() {
        
    }
    
    
    
    
}
