import knapsack.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by nikos on 24/2/2017.
 */
public class CacheServer {
    public static int CAPACITY;
    public static ArrayList<Integer> SIZE_OF_VIDEOS;

    private int id;
    private ArrayList<Endpoint> endpointConnections;
    private TreeMap<Integer, Integer> videoScores;
    private List<Item> knapsackItems;

    public static CacheServer createFromEndpoints(int id, ArrayList<Endpoint> endpoints) {
        CacheServer cacheServer = new CacheServer(id);

        for (Endpoint endpoint: endpoints) {
            if (endpoint.getLatencyOfCacheServers().containsKey(id))
                cacheServer.addEndpointConnection(endpoint);
        }

        return cacheServer;
    }

    public CacheServer(int id) {
        this.id = id;
        endpointConnections = new ArrayList<Endpoint>();
        videoScores = new TreeMap<Integer, Integer>();
    }

    public List<Item> knapsack(KnapsackType type) {
        for (Endpoint endpoint: endpointConnections) {
            TreeMap<Integer, Integer> scores = endpoint.getScoresForCacheServer(id);
            for (int videoId: scores.keySet()) {
                if (!videoScores.containsKey(videoId))
                    videoScores.put(videoId, 0);

                videoScores.put(videoId, videoScores.get(videoId) + scores.get(videoId));
            }
        }

        ArrayList<Item> items = new ArrayList<Item>();
        for (int videoId: videoScores.keySet()) {
            Item item = new Item();
            item.label = videoId;
            item.value = videoScores.get(videoId);
            item.weight = SIZE_OF_VIDEOS.get(videoId);

            items.add(item);
        }

        KnapsackSolver knapsackSolver = new DynamicProgrammingSolver(items, CAPACITY); // default
        if (type == KnapsackType.BnB) {
            knapsackSolver = new BranchAndBoundSolver(items, CAPACITY);
        } else if (type == KnapsackType.Dynamic) {
            knapsackSolver = new DynamicProgrammingSolver(items, CAPACITY);
        } else if (type == KnapsackType.Greedy) {
            knapsackSolver = new GreedySolver(items, CAPACITY);
        }

        KnapsackSolution solution = knapsackSolver.solve();
        knapsackItems = solution.items;

        return knapsackItems;
    }

    public void addEndpointConnection(Endpoint endpoint) {
        endpointConnections.add(endpoint);
    }

    public int getId() {
        return id;
    }

    public List<Item> getKnapsackItems() {
        return knapsackItems;
    }

    public ArrayList<Endpoint> getEndpointConnections() {
        return endpointConnections;
    }
}
