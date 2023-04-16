import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

//=============================================================================
//   TODO   Finding Components
//   Finds all the strongly connected subgraphs in the graph
//   Labels each stop with the number of the subgraph it is in and
//   sets the subGraphCount of the graph to the number of subgraphs.
//   Uses Kosaraju's_algorithm   (see lecture slides, based on
//   https://en.wikipedia.org/wiki/Kosaraju%27s_algorithm)
//=============================================================================

public class Components{

    // Use Kosaraju's algorithm.
    // In the forward search, record which nodes are visited with a visited set.
    // In the backward search, use the setSubGraphId and getSubGraphID methods
    // on the stop to record the component (and whether the node has been visited
    // during the backward search).
    // Alternatively, during the backward pass, you could use a Map<Stop,Stop>
    // to record the "root" node of each component, following the original version
    // of Kosaraju's algorithm, but this is unnecessarily complex.


    public static void findComponents(Graph graph) {
        System.out.println("calling findComponents");
        graph.resetSubGraphIds();
        graph.computeNeighbours();   // To ensure that all stops have a set of (undirected) neighbour stops

        Set<Stop> visited = new HashSet<Stop>();
        List<Stop> stack = new ArrayList<Stop>();

        for (Stop stop : graph.getStops()) {
            if (!visited.contains(stop)) {
                dfsForward(stop, visited, stack);
            }
        }

        visited.clear();
        int subGraphId = 0;
        for (int i = stack.size() - 1; i >= 0; i--) {
            Stop stop = stack.get(i);
            if (!visited.contains(stop)) {
                dfsBackward(stop, visited, subGraphId);
                subGraphId++;
            }
        }

        graph.setSubGraphCount(subGraphId);


        // finish and recheck!!!
    }

    private static void dfsBackward(Stop stop, Set<Stop> visited, int subGraphId) {
        visited.add(stop);
        stop.setSubGraphId(subGraphId);
        for (Edge edge : stop.getBackwardEdges()) {
            Stop neighbour = edge.fromStop();
            if (!visited.contains(neighbour)) {
                dfsBackward(neighbour, visited, subGraphId);
            }
        }
    }

    private static void dfsForward(Stop stop, Set<Stop> visited, List<Stop> stack) {
        visited.add(stop);
        for (Edge edge : stop.getForwardEdges()) {
            Stop neighbour = edge.toStop();
            if (!visited.contains(neighbour)) {
                dfsForward(neighbour, visited, stack);
            }
        }
        stack.add(stop);
    }


}
