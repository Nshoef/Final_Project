/**
 * StationServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mainServer;

public class StationServiceServiceLocator extends org.apache.axis.client.Service implements mainServer.StationServiceService {

    public StationServiceServiceLocator() {
    }


    public StationServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public StationServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for StationService
    private java.lang.String StationService_address = "http://localhost:8080/GVS-MainServer/services/StationService";

    public java.lang.String getStationServiceAddress() {
        return StationService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String StationServiceWSDDServiceName = "StationService";

    public java.lang.String getStationServiceWSDDServiceName() {
        return StationServiceWSDDServiceName;
    }

    public void setStationServiceWSDDServiceName(java.lang.String name) {
        StationServiceWSDDServiceName = name;
    }

    public mainServer.StationService getStationService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(StationService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getStationService(endpoint);
    }

    public mainServer.StationService getStationService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            mainServer.StationServiceSoapBindingStub _stub = new mainServer.StationServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getStationServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setStationServiceEndpointAddress(java.lang.String address) {
        StationService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (mainServer.StationService.class.isAssignableFrom(serviceEndpointInterface)) {
                mainServer.StationServiceSoapBindingStub _stub = new mainServer.StationServiceSoapBindingStub(new java.net.URL(StationService_address), this);
                _stub.setPortName(getStationServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("StationService".equals(inputPortName)) {
            return getStationService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://mainServer", "StationServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://mainServer", "StationService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("StationService".equals(portName)) {
            setStationServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
