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
    public static List<Edge> findShortestPath(Stop start, Stop goal, String timeOrDistance) {
        if (start == null || goal == null) {return null;}
        timeOrDistance= (timeOrDistance.equals("time"))?"time":"distance";

        // Stop currentNode, double costSoFar, double estimatedTotalCost, List<Edge> pathSoFar

        Queue<PathItem> fringe = new PriorityQueue<PathItem>();
        Set<Stop> visited = new HashSet<Stop>();
        Map<Stop,Edge> backpointers = new HashMap<Stop,Edge>();

        fringe.add(new PathItem(start, null, 0, new ArrayList<Edge>()));





        /*
	    while fringe is not empty:
            <node,edge,length-to-node,est-total,path> (from fringe)
            if node is not visited:
                visit node
                put <node,edge> into backpointers
                if node=goal:
                    return ReconstructPath(start,goal,backpointers)
                for each neigh-edge out of node to a neighbour:
                    if neighbour is not visited:
                        length-to-neighbour = length-to-node + neigh-edge.length
                        est-total-path = length-to-neighbour + est(neighbour,goal)
                        add <neighbour, neigh-edge, length-to-neigh, est-total-path> to fringe
         */





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
