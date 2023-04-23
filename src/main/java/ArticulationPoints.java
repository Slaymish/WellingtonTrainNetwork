import java.util.Collections;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;


//=============================================================================
//   TODO   Finding Articulation Points
//   Finds and returns a collection of all the articulation points in the undirected
//   graph, without walking edges
//=============================================================================

public class ArticulationPoints{

    // Use the algorithm from the lectures, but you will need a loop to check through
    // all the Stops in the graph to find any Stops which were not connected to the
    // previous Stops, and apply the lecture slide algorithm starting at each such stop.
    static Set<Stop> visited = new HashSet<Stop>();


    public static Collection<Stop> findArticulationPoints(Graph graph) {
        System.out.println("calling findArticulationPoints");
        graph.computeNeighbours();   // To ensure that all stops have a set of (undirected) neighbour stops

        // Declare vars
        Set<Stop> articulationPoints = new HashSet<Stop>();
        visited = new HashSet<>();
        Map<Stop, Integer> dfsNum = new HashMap<>();

        // Loop through all stops
        for (Stop stop : graph.getStops()) {
            if (!visited.contains(stop)) {
                dfs(stop, stop, dfsNum, articulationPoints);
            }
        }

        return articulationPoints;
    }

    private static int dfs(Stop stop, Stop parent, Map<Stop, Integer> dfsNum, Set<Stop> articulationPoints) {
        visited.add(stop);
        int dfsNumStop = dfsNum.size();
        dfsNum.put(stop, dfsNumStop);
        int min = dfsNumStop;
        int children = 0;

        for (Stop neighbour : stop.getNeighbours()) {
            if (!visited.contains(neighbour)) {
                children++;
                int dfsNumNeighbour = dfs(neighbour, stop, dfsNum, articulationPoints);
                min = Math.min(min, dfsNumNeighbour);
                if (dfsNumNeighbour >= dfsNumStop && parent != stop) {
                    articulationPoints.add(stop);
                }
            } else if (neighbour != parent) {
                min = Math.min(min, dfsNum.get(neighbour));
            }
        }

        if (parent == stop && children > 1) {
            articulationPoints.add(stop);
        }

        return min;
    }






}
