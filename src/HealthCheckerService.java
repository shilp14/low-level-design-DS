import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HealthCheckerService implements Runnable{

    private  final Map<String, Server> serverMap;
   private final LoadBalancerManager manager;

    public HealthCheckerService(Map<String, Server> serverMap, LoadBalancerManager manager){
        this.serverMap = serverMap;
        this.manager = manager;
    }

    @Override
    public void run() {
        checkHealth(serverMap, manager);
    }

    public void checkHealth(Map<String, Server> serverMap, LoadBalancerManager manager){

        List<Server> healthyServers = new ArrayList<>();
         for(Server server : serverMap.values()){
             boolean isAlive = ping(server);
              server.setHealthy(isAlive);
              if(isAlive){
                  healthyServers.add(server);
              }

         }

        manager.updateServer(healthyServers);
        }


    private boolean ping(Server server){
        // server
        return true;
    }


    }


