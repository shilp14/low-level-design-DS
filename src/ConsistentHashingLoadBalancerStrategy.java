import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConsistentHashingLoadBalancerStrategy{
    private final TreeMap<Long, Server> serverMap = new TreeMap<>();
    private final int virtualNodes;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Map<String, List<Long>> serverHashes = new HashMap<>();
    public  ConsistentHashingLoadBalancerStrategy(int virtualNodes){
        this.virtualNodes = virtualNodes;
    }

    public Server getServer(String requestKey){
        readWriteLock.readLock().lock();

        try{
            if(serverMap.isEmpty())
                throw new RuntimeException("no servers available");
            long idx = hash(requestKey);
            Map.Entry<Long, Server> entry = serverMap.ceilingEntry(idx);
            if(entry == null)
                entry = serverMap.firstEntry();

            return  entry.getValue();
        }
        finally {
            readWriteLock.readLock().unlock();
        }

    }

    public void addServer(Server server){
        readWriteLock.writeLock().lock();
        List<Long> hashes = new ArrayList<>();
        try {
            for (int i = 0; i < virtualNodes; i++) {
                String key = server.getId() + i;
                long idx = hash(key);
                while(serverMap.containsKey(idx)){
                    idx++;
                }
                serverMap.put(idx, server);
                hashes.add(idx);
            }
            serverHashes.put(server.getId(), hashes);
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void removeServer(Server server){
        readWriteLock.writeLock().lock();
        try{
              List<Long> hashes = serverHashes.get(server.getId());
              if(hashes != null) {
                  for(long idx : hashes)
                    serverMap.remove(idx);
              }


        }
        finally {
            readWriteLock.writeLock().unlock();
        }

    }

    private long hash(String key){
        long hash = 2166136261L;
        for(char c : key.toCharArray()){
            hash^=c;
            hash*=16777619L;

        }

        return hash & 0xFFFFFFFFL;
    }
}
