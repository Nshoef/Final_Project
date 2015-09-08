/**
 * PollingStationService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package services;

public interface PollingStationService extends java.rmi.Remote {
    public boolean updateResults(java.lang.String id, java.lang.String election, java.lang.String area, java.lang.String[] votes) throws java.rmi.RemoteException;
    public services.AreaInfo getAreaInfo(java.lang.String area) throws java.rmi.RemoteException;
}
