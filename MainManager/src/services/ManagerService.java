/**
 * ManagerService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package services;

public interface ManagerService extends java.rmi.Remote {
    public services.AreaResult[] getResult(java.lang.String election) throws java.rmi.RemoteException;
    public services.AreaInfo getAreaInfo(java.lang.String area) throws java.rmi.RemoteException;
    public services.Area[] getSystem(java.lang.String name) throws java.rmi.RemoteException;
    public boolean setCurrentElection(java.lang.String electionName) throws java.rmi.RemoteException;
    public boolean endRunningElection() throws java.rmi.RemoteException;
    public boolean setNewElection(java.lang.String name, java.lang.String electingSystem) throws java.rmi.RemoteException;
    public java.lang.String[] getLastElectionCans(java.lang.String area) throws java.rmi.RemoteException;
    public boolean setNewElectingSystem(java.lang.String name, int numOfAreas) throws java.rmi.RemoteException;
    public boolean setNewArea(java.lang.String name, java.lang.String system, int novpv, boolean isRanked) throws java.rmi.RemoteException;
    public boolean setCandidates(java.lang.String[] cans, java.lang.String electionName, java.lang.String area) throws java.rmi.RemoteException;
}