package service;

public class ManagerServiceProxy implements service.ManagerService {
  private String _endpoint = null;
  private service.ManagerService managerService = null;
  
  public ManagerServiceProxy() {
    _initManagerServiceProxy();
  }
  
  public ManagerServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initManagerServiceProxy();
  }
  
  private void _initManagerServiceProxy() {
    try {
      managerService = (new service.ManagerServiceServiceLocator()).getManagerService();
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
  
  public service.ManagerService getManagerService() {
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService;
  }
  
  public int[] getResults(java.lang.String can) throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.getResults(can);
  }
  
  public boolean updateInfo(java.lang.String area, java.lang.String name) throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.updateInfo(area, name);
  }
  
  public service.AreaInfo getAreaInfo() throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.getAreaInfo();
  }
  
  public boolean sentResult() throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.sentResult();
  }
  
  public boolean closeStation() throws java.rmi.RemoteException{
    if (managerService == null)
      _initManagerServiceProxy();
    return managerService.closeStation();
  }
  
  
}