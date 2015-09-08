package mainServer;

public class StationServiceProxy implements mainServer.StationService {
  private String _endpoint = null;
  private mainServer.StationService stationService = null;
  
  public StationServiceProxy() {
    _initStationServiceProxy();
  }
  
  public StationServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initStationServiceProxy();
  }
  
  private void _initStationServiceProxy() {
    try {
      stationService = (new mainServer.StationServiceServiceLocator()).getStationService();
      if (stationService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)stationService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)stationService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (stationService != null)
      ((javax.xml.rpc.Stub)stationService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public mainServer.StationService getStationService() {
    if (stationService == null)
      _initStationServiceProxy();
    return stationService;
  }
  
  public boolean updateResults(mainServer.Results results) throws java.rmi.RemoteException{
    if (stationService == null)
      _initStationServiceProxy();
    return stationService.updateResults(results);
  }
  
  public mainServer.AreaInfo getUpdates(java.lang.String area) throws java.rmi.RemoteException{
    if (stationService == null)
      _initStationServiceProxy();
    return stationService.getUpdates(area);
  }
  
  
}