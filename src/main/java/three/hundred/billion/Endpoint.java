package three.hundred.billion;

import three.hundred.billion.knapsack.Item;

import java.util.*;

/**
 * Created by nikos on 24/2/2017.
 */
public class Endpoint {
    private int id;
    private int latencyFromDataCenter;
    private int numberOfCacheServers;
    private TreeMap<Integer, Integer> latencyOfCacheServers;
    private TreeMap<Integer, Integer> videoRequests;
    private TreeMap<Integer, Integer> videoServedBy;

    public Endpoint(int id) {
        this.id = id;

        latencyOfCacheServers = new TreeMap<>();
        videoRequests = new TreeMap<>();
        videoServedBy = new TreeMap<>();
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
//        return scoresForEachCacheServer.get(cacheId);
        TreeMap<Integer, Integer> videoScores = new TreeMap<>();
        int cacheLatency = latencyOfCacheServers.get(cacheId);

        for (int videoId: videoRequests.keySet()) {
            int requestsNum = videoRequests.get(videoId);
            videoScores.put(videoId, calculateScore(cacheLatency, requestsNum));
        }

        return videoScores;
    }

    public int getVideoScoreForCacheServer(int videoId, int cacheId) {
        // check if already served by another server
        if (videoServedBy.containsKey(videoId)) {
            if (videoServedBy.get(videoId) != cacheId)
                return 0;
        }

//        return scoresForEachCacheServer.get(cacheId).get(videoId);
        int cacheLatency = latencyOfCacheServers.get(cacheId);
        int requestsNum = videoRequests.get(videoId);
        return calculateScore(cacheLatency, requestsNum);
    }

    public Set<Integer> getRequestedVideosIds() {
        return videoRequests.keySet();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(String.format("id: %d\n", id));
        str.append(String.format("latencyFromDataCenter: %d\n", latencyFromDataCenter));

        return str.toString();
    }

    public void updateVideoSources(List<Item> knapsackItems, int cacheId) {
        for (Item item: knapsackItems) {
            int videoId = item.label;

            if (videoRequests.containsKey(videoId))
                if (!videoServedBy.containsKey(videoId))
                    videoServedBy.put(videoId, cacheId);
        }
    }

    public ArrayList<Integer> getNotServedVideosIds() {
        ArrayList<Integer> videoIds = new ArrayList<>();
        for (int videoId: videoRequests.keySet()) {
            if (!videoServedBy.containsKey(videoId))
                videoIds.add(videoId);
        }

        return videoIds;
    }

//    public TreeMap<Integer, Integer> getNotServedVideosScores() {
//        for (int videoId: videoRequests.keySet()) {
//
//        }
//    }
}
