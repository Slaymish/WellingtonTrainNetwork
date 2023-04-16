/**
 * AStar search (and Dijkstra search) uses a priority queue of partial paths
 * that the search is building.
 * Each partial path needs several pieces of information, to specify
 * the path to that point, its cost so far, and its estimated total cost
 */

import java.util.List;

public record PathItem(Stop currentNode, Edge previousEdge, double costSoFar, double estimatedTotalCost) implements Comparable<PathItem> {
    @Override
    public int compareTo(PathItem other) {
        if (this.estimatedTotalCost < other.estimatedTotalCost) {
            return -1;
        } else if (this.estimatedTotalCost > other.estimatedTotalCost) {
            return 1;
        } else {
            return 0;
        }
    }
}

