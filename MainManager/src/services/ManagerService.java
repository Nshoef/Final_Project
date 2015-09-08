/**
 * ManagerService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package services;

public interface ManagerService extends java.rmi.Remote {
    public int getResult(java.lang.String election, java.lang.String area, java.lang.String can, int voteNum) throws java.rmi.RemoteException;
    public services.Area[] getSystem(java.lang.String name) throws java.rmi.RemoteException;
    public java.lang.String getRunningElection() throws java.rmi.RemoteException;
    public java.lang.String getSystemElection(java.lang.String election) throws java.rmi.RemoteException;
    public boolean endRunningElection() throws java.rmi.RemoteException;
    public boolean setCurrentElection(java.lang.String electionName) throws java.rmi.RemoteException;
    public java.lang.String[] getSavedElectionsNames() throws java.rmi.RemoteException;
    public java.lang.String[] getLastElectionCans(java.lang.String area, java.lang.String system) throws java.rmi.RemoteException;
    public boolean createNewElection(java.lang.String name, java.lang.String system) throws java.rmi.RemoteException;
    public boolean setNewElectingSystem(java.lang.String name, java.lang.String[] areas, int[] novpvs, boolean[] areRanked) throws java.rmi.RemoteException;
    public boolean removeElection(java.lang.String name) throws java.rmi.RemoteException;
    public java.lang.String[] getCans(java.lang.String election, java.lang.String area) throws java.rmi.RemoteException;
    public boolean removeSystem(java.lang.String name) throws java.rmi.RemoteException;
    public services.AreaInfo getAreaInfo(java.lang.String area) throws java.rmi.RemoteException;
    public boolean setCandidates(java.lang.String[] cans, java.lang.String electionName, java.lang.String area) throws java.rmi.RemoteException;
    public java.lang.String[] getSavedSystems() throws java.rmi.RemoteException;
}
