package services;

public class PollingStationServiceProxy implements services.PollingStationService {
  private String _endpoint = null;
  private services.PollingStationService pollingStationService = null;
  
  public PollingStationServiceProxy() {
    _initPollingStationServiceProxy();
  }
  
  public PollingStationServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initPollingStationServiceProxy();
  }
  
  private void _initPollingStationServiceProxy() {
    try {
      pollingStationService = (new services.PollingStationServiceServiceLocator()).getPollingStationService();
      if (pollingStationService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)pollingStationService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)pollingStationService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (pollingStationService != null)
      ((javax.xml.rpc.Stub)pollingStationService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public services.PollingStationService getPollingStationService() {
    if (pollingStationService == null)
      _initPollingStationServiceProxy();
    return pollingStationService;
  }
  
  public boolean updateResults(java.lang.String id, java.lang.String election, java.lang.String area, java.lang.String[] votes) throws java.rmi.RemoteException{
    if (pollingStationService == null)
      _initPollingStationServiceProxy();
    return pollingStationService.updateResults(id, election, area, votes);
  }
  
  public services.AreaInfo getAreaInfo(java.lang.String area) throws java.rmi.RemoteException{
    if (pollingStationService == null)
      _initPollingStationServiceProxy();
    return pollingStationService.getAreaInfo(area);
  }
  
  
}