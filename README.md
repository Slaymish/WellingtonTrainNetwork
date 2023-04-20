# Wellington Region Public Transport Network Analysis Program
This is a Java-based program that analyzes the Wellington Region Public Transport Network and provides me with four essential tasks. 

These tasks are:
- Displaying the network of stops and connections with the option to set the maximum acceptable walking distance
- Finding the shortest route between two places within the network
- Identifying the sub-components of the network that aren't fully connected to each other
- Identifying critical stops which would disconnect the network if they were blocked or out of service
- The program utilizes several algorithms to accomplish these tasks, and I will be completing four classes to build the graph data structure and implement the core algorithms needed for the tasks.

### Classes
The four classes that I will work on are as follows:

Graph: Represents the information about the network, particularly the graph structure of all the Stops and the Edges. It includes the code that builds the graph from data loaded from the file.
AStar and PathItem: Searches for, and returns, a path between two Stops, using the AStar algorithm.
Components: Searches for strongly connected subgraphs in the network and labels all the Stops with a number identifying the component it is in. Uses Kosaraju's algorithm.
ArticulationPoints: Searches for, and returns, a collection of Stops that are articulation points in the undirected version of the graph (non including any walking edges).
