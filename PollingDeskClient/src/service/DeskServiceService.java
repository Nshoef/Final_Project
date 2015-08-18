/**
 * DeskServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package service;

public interface DeskServiceService extends javax.xml.rpc.Service {
    public java.lang.String getDeskServiceAddress();

    public service.DeskService getDeskService() throws javax.xml.rpc.ServiceException;

    public service.DeskService getDeskService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
