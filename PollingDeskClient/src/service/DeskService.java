/**
 * DeskService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package service;

public interface DeskService extends java.rmi.Remote {
    public service.AreaInfo getInfo() throws java.rmi.RemoteException;
    public boolean addVote(java.lang.String[] cans) throws java.rmi.RemoteException;
}
