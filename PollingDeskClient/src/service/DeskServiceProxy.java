package service;

public class DeskServiceProxy implements service.DeskService {
  private String _endpoint = null;
  private service.DeskService deskService = null;
  
  public DeskServiceProxy() {
    _initDeskServiceProxy();
  }
  
  public DeskServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initDeskServiceProxy();
  }
  
  private void _initDeskServiceProxy() {
    try {
      deskService = (new service.DeskServiceServiceLocator()).getDeskService();
      if (deskService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)deskService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)deskService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (deskService != null)
      ((javax.xml.rpc.Stub)deskService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public service.DeskService getDeskService() {
    if (deskService == null)
      _initDeskServiceProxy();
    return deskService;
  }
  
  public service.AreaInfo getInfo() throws java.rmi.RemoteException{
    if (deskService == null)
      _initDeskServiceProxy();
    return deskService.getInfo();
  }
  
  public boolean addVote(java.lang.String[] cans) throws java.rmi.RemoteException{
    if (deskService == null)
      _initDeskServiceProxy();
    return deskService.addVote(cans);
  }
  
  
}