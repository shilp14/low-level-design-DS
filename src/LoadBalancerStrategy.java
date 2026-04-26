import java.util.List;

public interface LoadBalancerStrategy {

    Server selectServer(List<Server>servers);
}
