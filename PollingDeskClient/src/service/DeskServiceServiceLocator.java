/**
 * DeskServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package service;

public class DeskServiceServiceLocator extends org.apache.axis.client.Service implements service.DeskServiceService {

    public DeskServiceServiceLocator() {
    }


    public DeskServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DeskServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DeskService
    private java.lang.String DeskService_address = "http://localhost:8080/Project-_Polling_Server/services/DeskService";

    public java.lang.String getDeskServiceAddress() {
        return DeskService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DeskServiceWSDDServiceName = "DeskService";

    public java.lang.String getDeskServiceWSDDServiceName() {
        return DeskServiceWSDDServiceName;
    }

    public void setDeskServiceWSDDServiceName(java.lang.String name) {
        DeskServiceWSDDServiceName = name;
    }

    public service.DeskService getDeskService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DeskService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDeskService(endpoint);
    }

    public service.DeskService getDeskService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            service.DeskServiceSoapBindingStub _stub = new service.DeskServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getDeskServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDeskServiceEndpointAddress(java.lang.String address) {
        DeskService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (service.DeskService.class.isAssignableFrom(serviceEndpointInterface)) {
                service.DeskServiceSoapBindingStub _stub = new service.DeskServiceSoapBindingStub(new java.net.URL(DeskService_address), this);
                _stub.setPortName(getDeskServiceWSDDServiceName());
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
        if ("DeskService".equals(inputPortName)) {
            return getDeskService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service", "DeskServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service", "DeskService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DeskService".equals(portName)) {
            setDeskServiceEndpointAddress(address);
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
