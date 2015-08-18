/**
 * ManagerService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package service;

public interface ManagerService extends java.rmi.Remote {
    public boolean updateInfo(java.lang.String area, java.lang.String name) throws java.rmi.RemoteException;
    public boolean sentResult() throws java.rmi.RemoteException;
    public int[] getResults(java.lang.String can) throws java.rmi.RemoteException;
    public service.AreaInfo getAreaInfo() throws java.rmi.RemoteException;
}
