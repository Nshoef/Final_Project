/**
 * StationService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mainServer;

public interface StationService extends java.rmi.Remote {
    public boolean updateResults(mainServer.Results results) throws java.rmi.RemoteException;
    public mainServer.AreaInfo getUpdates(java.lang.String area) throws java.rmi.RemoteException;
}
