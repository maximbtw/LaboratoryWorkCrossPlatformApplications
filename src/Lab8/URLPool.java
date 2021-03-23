package Lab8;

import Lab7.URLDepthPair;
import java.util.*;

public class URLPool {
    private HashMap<String, URLDepthPair> visited;
    private LinkedList<URLDepthPair> pool;

    public int getPoolSize() {
        return pool.size();
    }

    public URLPool(){
        visited = new HashMap<>();
        pool = new LinkedList<>();
    }

    public synchronized URLDepthPair getLink(){
        if(pool.size() == 0) return null;
        URLDepthPair link = pool.pop();
        visited.put(link.getURL(),link);
        return link;
    }

    public synchronized void addLink(URLDepthPair link){
        if(!visited.containsKey(link.getURL()))
            pool.add(link);
    }
}
