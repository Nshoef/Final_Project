/**
 * ManagerServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package services;

public class ManagerServiceServiceLocator extends org.apache.axis.client.Service implements services.ManagerServiceService {

    public ManagerServiceServiceLocator() {
    }


    public ManagerServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ManagerServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ManagerService
    private java.lang.String ManagerService_address = "http://localhost:8080/Project-_Main_Server/services/ManagerService";

    public java.lang.String getManagerServiceAddress() {
        return ManagerService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ManagerServiceWSDDServiceName = "ManagerService";

    public java.lang.String getManagerServiceWSDDServiceName() {
        return ManagerServiceWSDDServiceName;
    }

    public void setManagerServiceWSDDServiceName(java.lang.String name) {
        ManagerServiceWSDDServiceName = name;
    }

    public services.ManagerService getManagerService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ManagerService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getManagerService(endpoint);
    }

    public services.ManagerService getManagerService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            services.ManagerServiceSoapBindingStub _stub = new services.ManagerServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getManagerServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setManagerServiceEndpointAddress(java.lang.String address) {
        ManagerService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (services.ManagerService.class.isAssignableFrom(serviceEndpointInterface)) {
                services.ManagerServiceSoapBindingStub _stub = new services.ManagerServiceSoapBindingStub(new java.net.URL(ManagerService_address), this);
                _stub.setPortName(getManagerServiceWSDDServiceName());
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
        if ("ManagerService".equals(inputPortName)) {
            return getManagerService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://services", "ManagerServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://services", "ManagerService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ManagerService".equals(portName)) {
            setManagerServiceEndpointAddress(address);
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
