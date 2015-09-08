/**
 * StationServiceService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mainServer;

public interface StationServiceService extends javax.xml.rpc.Service {
    public java.lang.String getStationServiceAddress();

    public mainServer.StationService getStationService() throws javax.xml.rpc.ServiceException;

    public mainServer.StationService getStationService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
