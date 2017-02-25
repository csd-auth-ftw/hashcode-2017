package three.hundred.billion;

import three.hundred.billion.io.InputReader;
import three.hundred.billion.io.OutputWriter;
import three.hundred.billion.knapsack.KnapsackType;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by nikos on 24/2/2017.
 */
public class HashCode {
    private InputReader ir;
    private OutputWriter ow;

    private int numberOfVideos;
    private int numberOfEndpoints;
    private int numberOfRequestDescriptions;
    private int numberOfCacheServers;
    private int sizeOfCacheServer;
    private ArrayList<Integer> sizeOfVideos;

    private ArrayList<Endpoint> endpoints;
    private ArrayList<CacheServer> cacheServers;

    public HashCode() {
        sizeOfVideos = new ArrayList<Integer>();
        endpoints = new ArrayList<Endpoint>();
        cacheServers = new ArrayList<CacheServer>();

        String inputFilename = appendDesktopPath("\\hashcode\\kittens.in");
        readInput(inputFilename);

        // init cache servers
        for (int i=0; i<numberOfCacheServers; i++) {
            CacheServer cacheServer = CacheServer.createFromEndpoints(i, endpoints);
            cacheServers.add(cacheServer);
        }

        sortCacheServers();

        // knapsack for each server
        for (CacheServer cacheServer: cacheServers) {
            System.out.format("knapsack for #%d\n", cacheServer.getId());
            cacheServer.knapsack(KnapsackType.Dynamic);
        }

        writeOutput(inputFilename + ".output.txt");
    }

    private void sortCacheServers() {
        cacheServers.sort(new Comparator<CacheServer>() {
            public int compare(CacheServer o1, CacheServer o2) {
                if (o1.getEndpointConnNumber() < o2.getEndpointConnNumber()) {
                    return 1;
                } else if (o1.getEndpointConnNumber() > o2.getEndpointConnNumber()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    private String appendDesktopPath(String filename) {
        String sep = System.getProperty("file.separator");
        return System.getProperty("user.home") + sep + "Desktop" + sep + filename;
    }

    private void readInput(String filename) {
        ir = new InputReader(filename);
        ArrayList<Integer> line;

        // first line
        line = ir.readNextLine();
        numberOfVideos = line.get(0);
        numberOfEndpoints = line.get(1);
        numberOfRequestDescriptions = line.get(2);
        numberOfCacheServers = line.get(3);
        sizeOfCacheServer = CacheServer.CAPACITY = line.get(4);

        // second line
        sizeOfVideos = CacheServer.SIZE_OF_VIDEOS = ir.readNextLine();

        // endpoint data
        for (int i=0; i<numberOfEndpoints; i++) {
            line = ir.readNextLine();

            Endpoint endpoint = new Endpoint(i);
            endpoint.setLatencyFromDataCenter(line.get(0));
            endpoint.setNumberOfCacheServers(line.get(1));

            for (int j=0; j<endpoint.getNumberOfCacheServers(); j++) {
                line = ir.readNextLine();
                endpoint.setCacheLatency(line.get(0), line.get(1));
            }

            endpoints.add(endpoint);
        }

        // request data
        for (int k=0; k<numberOfRequestDescriptions; k++) {
            line = ir.readNextLine();

            Endpoint endpoint = endpoints.get(line.get(1));
            endpoint.setVideoRequests(line.get(0), line.get(2));
        }
    }

    private void writeOutput(String filename) {
        ow = new OutputWriter(filename);
        ow.writeNumberOfServers(numberOfCacheServers);

        for (CacheServer cacheServer: cacheServers) {
            ow.writeCache(cacheServer.getId(), cacheServer.getKnapsackItems());
        }

        ow.close();
    }

    public static void main(String[] args) {
        new HashCode();
    }

}
