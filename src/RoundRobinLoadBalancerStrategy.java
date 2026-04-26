import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinLoadBalancerStrategy implements LoadBalancerStrategy{

    private final AtomicInteger counter = new AtomicInteger(0);


    @Override
    public Server selectServer(List<Server> servers) {
          if(servers.isEmpty())
              throw new RuntimeException("servers unavailable");

            int idx =  Math.abs(counter.getAndIncrement())% servers.size();
            return servers.get(idx);

    }
}
