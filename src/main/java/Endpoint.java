import java.util.TreeMap;

/**
 * Created by nikos on 24/2/2017.
 */
public class Endpoint {
    private int id;
    private int latencyFromDataCenter;
    private int numberOfCacheServers;
    private TreeMap<Integer, Integer> latencyOfCacheServers;
    private TreeMap<Integer, Integer> videoRequests;
    private TreeMap<Integer, TreeMap<Integer, Integer>> scoresForEachCacheServer;

    public Endpoint(int id) {
        this.id = id;

        latencyOfCacheServers = new TreeMap<Integer, Integer>();
        videoRequests = new TreeMap<Integer, Integer>();
        scoresForEachCacheServer = new TreeMap<Integer, TreeMap<Integer, Integer>>();
    }

    public void calculateScores() {
        for (int cacheId: latencyOfCacheServers.keySet()) {
            TreeMap<Integer, Integer> videoScores = new TreeMap<Integer, Integer>();
            int cacheLatency = latencyOfCacheServers.get(cacheId);

            for (int videoId: videoRequests.keySet()) {
                int requestsNum = videoRequests.get(videoId);
                videoScores.put(videoId, calculateScore(cacheLatency, requestsNum));
            }

            scoresForEachCacheServer.put(cacheId, videoScores);
        }
    }

    public int calculateScore(int cacheLatency, int requestsNum) {
        return (latencyFromDataCenter-cacheLatency)*requestsNum;
    }

    public void setCacheLatency(int cacheId, int cacheLatency) {
        latencyOfCacheServers.put(cacheId, cacheLatency);
    }

    public void setVideoRequests(int videoId, int requestsNumber) {
        videoRequests.put(videoId, requestsNumber);
    }

    public int getId() {
        return id;
    }

    public int getLatencyFromDataCenter() {
        return latencyFromDataCenter;
    }

    public void setLatencyFromDataCenter(int latencyFromDataCenter) {
        this.latencyFromDataCenter = latencyFromDataCenter;
    }

    public int getNumberOfCacheServers() {
        return numberOfCacheServers;
    }

    public void setNumberOfCacheServers(int numberOfCacheServers) {
        this.numberOfCacheServers = numberOfCacheServers;
    }

    public TreeMap<Integer, Integer> getLatencyOfCacheServers() {
        return latencyOfCacheServers;
    }

    public TreeMap<Integer, Integer> getScoresForCacheServer(int cacheId) {
        return scoresForEachCacheServer.get(cacheId);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(String.format("id: %d\n", id));
        str.append(String.format("latencyFromDataCenter: %d\n", latencyFromDataCenter));

        return str.toString();
    }
}
