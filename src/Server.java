import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private String ip;
    private String host;
    private String name;
    private String port;
    private String id;
    private volatile boolean isHealthy;
    private AtomicInteger activeConnections;

    public Server(String id, String ip, String host, String port){
        this.id = id;
        this.ip = ip;
        this.host = host;
        this.port = port;
        isHealthy = true;
        activeConnections = new AtomicInteger(0);
    }

    public String getId() {
        return id;
    }

    public Server setId(String id) {
        this.id = id;
        return this;
    }


    public String getIp() {
        return ip;
    }

    public Server setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getHost() {
        return host;
    }

    public Server setHost(String host) {
        this.host = host;
        return this;
    }

    public String getName() {
        return name;
    }

    public Server setName(String name) {
        this.name = name;
        return this;
    }

    public String getPort() {
        return port;
    }

    public Server setPort(String port) {
        this.port = port;
        return this;
    }

    public boolean isHealthy() {
        return isHealthy;
    }

    public Server setHealthy(boolean healthy) {
        isHealthy = healthy;
        return this;
    }

    public int incrementActiveConnections(){
         return activeConnections.incrementAndGet();
    }

    public int decrementActiveConnection(){
        return activeConnections.decrementAndGet();
    }

    public int getActiveConnection(){
        return activeConnections.get();
    }

}
