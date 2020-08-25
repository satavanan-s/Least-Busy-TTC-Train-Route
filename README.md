# Least-Busy-TTC-Train-Route
This Java Program uses Dijkstra's Algorithm to find the path of the least human contact (the least busy path) in the TTC Subway. 
- [How it Works](#how-it-works)
- [Development Process and Testing](#development-process-and-testing)
- [Documentation](#documentation)
- [Data Sources](#data-sources)

<h2>How it Works</h2>

Every instance of the Station class is a specific TTC Subway Station.
[The station class contains member fields for the Station name, ridership data, and a unique ID.](https://github.com/satavanan-s/Least-Busiest-TTC-Train-Route/blob/master/src/com/satavanan/application/Station.java)
Instead of being hardcoded the station and ridership data is in [text files located in the data folder](https://github.com/satavanan-s/Least-Busiest-TTC-Train-Route/tree/master/data).
The [main application](https://github.com/satavanan-s/Least-Busiest-TTC-Train-Route/blob/master/src/com/satavanan/application/Main.java) reads the data in the text files and 
represents the TTC Subway System as a weighted directed graph (adjacency list). The nodes represent TTC Subway Stations,
the edges represent a path from one station to another. Edges will always point in both directions however, each direction has a different
weight. The weight of all outgoing edges from a node is the number of people that ride the station per day. I've
assumed that the number of people in a station is constant throughout the day (this was due to the lack of data).
Because of this assumption, it is safe to use the average daily ridership since the ratios are constant.

<h4>An Example to Understand the Graph Representation:</h4>

Assume we have: Station A with a daily ridership of 8,
Station B with a daily ridership of 2. <br>
If there is a path between Station A and Station B then there will be an edge from
A to B that is weighted 8, and an edge from B to A that is weighted 2.

<h3>This means we're assuming that the number of people at a station is equivalent to the number of people boarding the train.</h3>

After representing the TTC Subway System, I obtain the user input (Starting and Destination Station)
and apply Dijkstra's algorithm to find the path with the least cost (cost = number of humans in a Station).

<h2>Development Process and Testing</h2>

This project was built using the Test-Driven Development software development process.
You will find corresponding tests to all the methods in the [tests folder](https://github.com/satavanan-s/Least-Busiest-TTC-Train-Route/tree/1/src/com/satavanan/tests "Tests Directory").

<h2>Documentation</h2>

The documentation for this project was written for the Javadoc tool. [Here is the guide I followed.](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html)

## [Data](https://github.com/satavanan-s/Least-Busy-TTC-Train-Route/tree/master/data) Sources

- [TTC_Subway_Ridership_Data.txt](https://github.com/satavanan-s/Least-Busy-TTC-Train-Route/blob/master/data/TTC_Subway_Ridership_Data.txt)
  * [The TTC ridership data was scraped from this document.](http://www.ttc.ca/PDF/Transit_Planning/Subway%20ridership%20-%202018.pdf)
- [TTC_Subway_Lines.txt](https://github.com/satavanan-s/Least-Busy-TTC-Train-Route/blob/master/data/TTC_Subway_Lines.txt)
  * [The TTC line data was scraped from this document.](https://www.ttc.ca/Subway/index.jsp)
