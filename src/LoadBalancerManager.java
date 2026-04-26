import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class LoadBalancerManager {
    private final Map<String, Server> serverMap = new ConcurrentHashMap<>();
    private volatile LoadBalancerStrategy loadBalancerStrategy;
    private volatile List<Server> healthyServers = List.of();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    public LoadBalancerManager(LoadBalancerStrategy loadBalancerStrategy){
        this.loadBalancerStrategy = loadBalancerStrategy;
        scheduledExecutorService.scheduleAtFixedRate(new HealthCheckerService(serverMap,this),0,5,TimeUnit.SECONDS);
    }

    public void setLoadBalancerStrategy(LoadBalancerStrategy loadBalancerStrategy) {
        this.loadBalancerStrategy = loadBalancerStrategy;

    }

    public void addServer(Server server){
       serverMap.put(server.getId(), server);
        setHealthyServers();
    }
    private void setHealthyServers(){
        List<Server> servers = new ArrayList<>();
        for(Server server : serverMap.values()){
            if(server.isHealthy())
              servers.add(server);
        }
        healthyServers = servers;
    }
    public void removeServer(String serverId){
        serverMap.remove(serverId);
        setHealthyServers();
    }

    public Server getServer(){
        List<Server> servers = healthyServers;
            return loadBalancerStrategy.selectServer(servers);

    }

    public void updateServer(List<Server> servers){
        healthyServers = Collections.unmodifiableList(servers);
    }

    public void shutdown() {
        scheduledExecutorService.shutdown();

        try {
            if (!scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduledExecutorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduledExecutorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

public Server handleRequest(){
        Server server = getServer();
        server.incrementActiveConnections();
        return  server;
    }
    public void releaseServer(Server server){
        server.decrementActiveConnection();
    }


}
