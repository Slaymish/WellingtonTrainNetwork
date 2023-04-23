/**
 * Implements the A* search algorithm to find the shortest path
 * in a graph between a start node and a goal node.
 * It returns a Path consisting of a list of Edges that will
 * connect the start node to the goal node.
 */

import java.util.*;


public class AStar {


    private static String timeOrDistance = "distance";    // way of calculating cost: "time" or "distance"



    // find the shortest path between two stops
    public static List<Edge> findShortestPath(Stop start, Stop goal, String timeOrDist) {
        if (start == null || goal == null) {return null;}
        timeOrDistance= (timeOrDist.equals("time"))?"time":"distance";

        // fringe = currentNode, previousEdge, costSoFar, estimatedTotalCost

        Queue<PathItem> fringe = new PriorityQueue<PathItem>();
        Set<Stop> visited = new HashSet<Stop>();
        Map<Stop,Edge> backpointers = new HashMap<Stop,Edge>();

        fringe.add(new PathItem(start, null, 0, heuristic(start, goal)));

        while (!fringe.isEmpty()) {
            PathItem current = fringe.poll();
            if (!visited.contains(current.currentNode())) {
                visited.add(current.currentNode());
                backpointers.put(current.currentNode(), current.previousEdge());
                if (current.currentNode() == goal) {
                    // Return list of edges
                    List<Edge> path = new ArrayList<Edge>();
                    Stop currentStop = goal;
                    while (currentStop != start) {
                        Edge edge = backpointers.get(currentStop);
                        path.add(edge);
                        currentStop = edge.fromStop();
                    }
                    Collections.reverse(path);
                    return path;
                }
                for (Edge edge : current.currentNode().getForwardEdges()) {
                    if (!visited.contains(edge.toStop())) {
                        double costSoFar = current.costSoFar() + edgeCost(edge);
                        double estimatedTotalCost = costSoFar + heuristic(edge.toStop(), goal);
                        fringe.add(new PathItem(edge.toStop(), edge, costSoFar, estimatedTotalCost));
                    }
                }
            }
        }
        return null;   // fix this!!!
    }







    /** Return the heuristic estimate of the cost to get from a stop to the goal */
    public static double heuristic(Stop current, Stop goal) {
        if (timeOrDistance=="distance"){ return current.distanceTo(goal);}
        else if (timeOrDistance=="time"){return current.distanceTo(goal) / Transport.TRAIN_SPEED_MPS;}
        else {return 0;}
    }

    /** Return the cost of traversing an edge in the graph */
    public static double edgeCost(Edge edge){
        if (timeOrDistance=="distance"){ return edge.distance();}
        else if (timeOrDistance=="time"){return edge.time();}
        else {return 1;}
    }




}
