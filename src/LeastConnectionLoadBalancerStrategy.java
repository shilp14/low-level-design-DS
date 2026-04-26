import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class LeastConnectionLoadBalancerStrategy implements LoadBalancerStrategy{
    @Override
    public Server selectServer(List<Server> servers) {
         return servers.stream().min(Comparator.comparingInt(s -> s.getActiveConnection())).orElseThrow(()-> new RuntimeException("no server available"));

    }
}
