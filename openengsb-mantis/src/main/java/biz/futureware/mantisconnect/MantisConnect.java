/**
 * MantisConnect.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis #axisVersion# #today# WSDL2Java emitter.
 */

package biz.futureware.mantisconnect;

public interface MantisConnect extends javax.xml.rpc.Service {
    public java.lang.String getMantisConnectPortAddress();

    public biz.futureware.mantisconnect.MantisConnectPortType getMantisConnectPort() throws javax.xml.rpc.ServiceException;

    public biz.futureware.mantisconnect.MantisConnectPortType getMantisConnectPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
