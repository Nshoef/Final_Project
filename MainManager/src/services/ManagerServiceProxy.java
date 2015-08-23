package services;

public class ManagerServiceProxy implements services.ManagerService {
  private String _endpoint = null;
  private services.ManagerService managerService = null;
  
  public ManagerServiceProxy() {
    _initManagerServiceProxy();
  }
  
  public ManagerServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initManagerServiceProxy();
  }
  
  private void _initManagerServiceProxy() {
    try {
      managerService = (new services.ManagerServiceServiceLocator()).getManagerService();
      if (managerService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)managerService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)managerService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (managerService != null)
      ((javax.xml.rpc.Stub)managerService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public services.ManagerService getManagerService() {
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService;
  }
  
  public int getResult(java.lang.String election, java.lang.String area, java.lang.String can, int voteNum) throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.getResult(election, area, can, voteNum);
  }
  
  public services.AreaInfo getAreaInfo(java.lang.String area) throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.getAreaInfo(area);
  }
  
  public services.Area[] getSystem(java.lang.String name) throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.getSystem(name);
  }
  
  public boolean setNewArea(java.lang.String name, java.lang.String system, int novpv, boolean isRanked) throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.setNewArea(name, system, novpv, isRanked);
  }
  
  public java.lang.String[] getSavedSystems() throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.getSavedSystems();
  }
  
  public boolean setNewElection(java.lang.String name, java.lang.String electingSystem) throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.setNewElection(name, electingSystem);
  }
  
  public boolean setCandidates(java.lang.String[] cans, java.lang.String electionName, java.lang.String area) throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.setCandidates(cans, electionName, area);
  }
  
  public java.lang.String[] getCans(java.lang.String election, java.lang.String area) throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.getCans(election, area);
  }
  
  public boolean setNewElectingSystem(java.lang.String name, int numOfAreas) throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.setNewElectingSystem(name, numOfAreas);
  }
  
  public java.lang.String[] getLastElectionCans(java.lang.String area, java.lang.String system) throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.getLastElectionCans(area, system);
  }
  
  public java.lang.String[] getSavedElectionsNames() throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.getSavedElectionsNames();
  }
  
  public boolean setCurrentElection(java.lang.String electionName) throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.setCurrentElection(electionName);
  }
  
  public boolean endRunningElection() throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.endRunningElection();
  }
  
  public java.lang.String getSystemElection(java.lang.String election) throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.getSystemElection(election);
  }
  
  
}