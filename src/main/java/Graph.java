import java.util.*;


/**
 * Graph is the data structure that stores the collection of stops, lines and connections. 
 * The Graph constructor is passed a Map of the stops, indexed by stopId and
 *  a Map of the Lines, indexed by lineId.
 * The Stops in the map have their id, name and GIS location.
 * The Lines in the map have their id, and lists of the stopIDs an times (in order)
 *
 * To build the actual graph structure, it is necessary to
 *  build the list of Edges out of each stop and the list of Edges into each stop
 * Each pair of adjacent stops in a Line is an edge.
 * We also need to create walking edges between every pair of stops in the whole
 *  network that are closer than walkingDistance.
 */
public class Graph {

    private Collection<Stop> stops;
    private Collection<Line> lines;
    private Collection<Edge> edges = new HashSet<Edge>();      // edges between Stops

    private Collection<Transfer> transfers;

    private int numComponents = 0;     // number of connected subgraphs (graph components)

    /**
     * Construct a new graph given a collection of stops and a collection of lines.
     */
    public Graph(Collection<Stop> stops, Collection<Line> lines, Collection<Transfer> transfers) {
        this.stops = new TreeSet<Stop>(stops);
        this.lines = lines;
        this.transfers = transfers;

        // These are two of the key methods you must complete:
        createAndConnectEdges();
        addTransfersToEdges();
        computeNeighbours();

        // printGraphData();   // you could uncomment this to help in debugging your code
    }

    public Graph(Collection<Stop> stops, Collection<Line> lines) {
        System.out.println("Creating non challenge graph");
        this.stops = new TreeSet<Stop>(stops);
        this.lines = lines;

        // These are two of the key methods you must complete:
        createAndConnectEdges();
        computeNeighbours();
    }

    private void addTransfersToEdges() {
        for (Transfer transfer : transfers) {
            // Find edge between fromStop and toStop
            Edge edge = null;
            for (Edge e : edges) {
                if (e.fromStop().equals(transfer.getFromStop()) && e.toStop().equals(transfer.getToStop())) {
                    edge = e;
                    break;
                }
            }
            if (edge == null) {System.out.println("Edge not found for transfer: " + transfer);}
            else{
                edge.addTransfer(transfer);
                System.out.println("Added " + transfer.toString() + " to edge");
            }
        }
        System.out.println("Added transfers to edges");
    }


    /** Print out the lines and stops in the graph to System.out */
    public void printGraphData(){
        System.out.println("============================\nLines:");
        for (Line line : lines){
            System.out.println(line.getId()+ "("+line.getStops().size()+" stops)");
        }
        System.out.println("\n=============================\nStops:");
        for (Stop stop : stops){
            System.out.println(stop+((stop.getSubGraphId()<0)?"":" subG:"+stop.getSubGraphId()));
            System.out.println("  "+stop.getForwardEdges().size()+" out edges; "+
                               stop.getBackwardEdges().size() +" in edges; " +
                               stop.getNeighbours().size() +" neighbours");
        }
        System.out.println("===============");
    }


    //============================================
    // Methods to build the graph structure. 
    //============================================

    /** 
     * From the loaded Line and Stop information,
     *  identify all the edges that connect stops along a Line.
     * - Construct the collection of all Edges in the graph  and
     * - Construct the forward and backward neighbour edges of each Stop.
     */
    private void createAndConnectEdges() {
        for(Line line : lines){
            List<Stop> stops = line.getStops();
            List<Integer> times = line.getTimes();
            for(int i=0; i<stops.size()-1; i++){
                Stop from = stops.get(i);
                Stop to = stops.get(i+1);
                double time = times.get(i+1) - times.get(i);
                double distance = from.distanceTo(to);
                Edge edge = new Edge(from, to,line.getType(),line, time,distance);
                edges.add(edge);
                from.addForwardEdge(edge);
                to.addBackwardEdge(edge);
            }
        }
    }

    /** 
     * Construct the undirected graph of neighbours for each Stop:
     * For each Stop, construct a set of the stops that are its neighbours
     * from the forward and backward neighbour edges.
     * It may assume that there are no walking edges at this point.
     */
    public void computeNeighbours(){
        for(Stop stop: stops){
            Set<Stop> neighbours = new HashSet<Stop>();
            for(Edge edge: stop.getForwardEdges()){
                neighbours.add(edge.toStop());
            }
            for(Edge edge: stop.getBackwardEdges()){
                neighbours.add(edge.fromStop());
            }
            neighbours.forEach(stop::addNeighbour);
        }


    }

    //=============================================================================
    //    Recompute Walking edges and add to the graph
    //=============================================================================
    //
    /** 
     * Reconstruct all the current walking edges in the graph,
     * based on the specified walkingDistance:
     * identify all pairs of stops * that are at most walkingDistance apart,
     * and construct edges (both ways) between the stops
     * add the edges to the forward and backward neighbours of the Stops
     * add the edges to the walking edges of the graph.
     * Assume that all the previous walking edges have been removed
     */
    public void recomputeWalkingEdges(double walkingDistance) {
        int count = 0;
        for(Stop stop1: stops){
            for(Stop stop2: stops){
                if(stop1.distanceTo(stop2) <= walkingDistance && !stop1.equals(stop2)){
                    double distance = stop1.distanceTo(stop2);
                    double time = distance/Transport.WALKING_SPEED_MPS;
                    Edge edge1 = new Edge(stop1, stop2, Transport.WALKING, null, time, distance);
                    Edge edge2 = new Edge(stop2, stop1, Transport.WALKING, null, time, distance);
                    stop1.addForwardEdge(edge1);
                    stop1.addBackwardEdge(edge2);

                    stop2.addForwardEdge(edge2);
                    stop2.addBackwardEdge(edge1);

                    count++;

                    edges.add(edge1);
                    edges.add(edge2);
                }
            }
        }

        System.out.println("Number of walking edges added: " + count);
    }

    /** 
     * Remove all the current walking edges in the graph
     * - from the edges field (the collection of all the edges in the graph)
     * - from the forward and backward neighbours of each Stop.
     * - Resets the number of components back to 0 by
     *   calling  resetSubGraphIds()
     */
    public void removeWalkingEdges() {
        resetSubGraphIds();
        for (Stop stop : stops) {
            stop.deleteEdgesOfType(Transport.WALKING);// remove all edges of type walking
        }
        edges.removeIf((Edge e)-> Transport.WALKING.equals(e.transpType()));
        
    }

    //=============================================================================
    //  Methods to access data from the graph. 
    //=============================================================================
    /**
     * Return a collection of all the stops in the network
     */        
    public Collection<Stop> getStops() {
        return Collections.unmodifiableCollection(stops);
    }
    /**
     * Return a collection of all the edges in the network
     */        
    public Collection<Edge> getEdges() {
        return Collections.unmodifiableCollection(edges);
    }

    /**
     * Return the first stop that starts with the specified prefix
     * (first by alphabetic order of name)
     */
    public Stop getFirstMatchingStop(String prefix) {
        for (Stop stop : stops) {
            if (stop.getName().startsWith(prefix)) {
                return stop;
            }
        }
        return null;
    }

    /** 
     * Return all the stops that start with the specified prefix
     * in alphabetic order.
     */
    public List<Stop> getAllMatchingStops(String prefix) {
        List<Stop> ans = new ArrayList<Stop>();
        for (Stop stop : stops) {
            if (stop.getName().startsWith(prefix)) {
                ans.add(stop);
            }
        }
        return ans;
    }

    public int getSubGraphCount() {
        return numComponents;
    }
    public void setSubGraphCount(int num) {
        numComponents = num;
        if (num==0){ resetSubGraphIds(); }
    }

    /**
     * reset the subgraph ID of all stops
     */
    public void resetSubGraphIds() {
        for (Stop stop : stops) {
            stop.setSubGraphId(-1);
        }
        numComponents = 0;
    }

}
