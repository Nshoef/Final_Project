/**
 * ManagerServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package services;

public interface ManagerServiceService extends javax.xml.rpc.Service {
    public java.lang.String getManagerServiceAddress();

    public services.ManagerService getManagerService() throws javax.xml.rpc.ServiceException;

    public services.ManagerService getManagerService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
