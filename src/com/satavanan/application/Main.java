package com.satavanan.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The Main program that reads the data from text files &
 * discovers the path of least human interaction given a
 * starting and ending station.
 *
 * @author  Satavanan Senthuran
 */
public class Main {
    /**
     * This method is used to find the shortest path between
     * two points in a weighted directed graph represented
     * as an adjacency matrix.
     * @param graph a directed network represented as an adjacency
     *             matrix.
     * @param start  The starting node represented as an int.
     * @param end The destination node represented as an int.
     * @return ArrayList<Integer> The shortest path.
     */
    public static ArrayList<Integer> dijkstraShortestPath(int[][] graph, int start, int end){
        Queue<Integer> q = new LinkedList<>();
        int[] distance = new int[graph.length];
        for(int  i = 0; i < distance.length; i ++){
            distance[i] = Integer.MAX_VALUE;
        }
        distance[start] = 0;
        int[] prevStationID = new int[graph.length];
        for(int i = 0; i < prevStationID.length; i++){
            prevStationID[i] = Integer.MIN_VALUE;
        }
        q.add(start);
        while(!q.isEmpty()){
            int currentVertex = q.remove();
            int neighbourVertex = 0;
            for(int a: graph[currentVertex]){
                if(a != 0){
                    if(distance[neighbourVertex] > distance[currentVertex] + a){
                        distance[neighbourVertex] = distance[currentVertex] + a;
                        prevStationID[neighbourVertex] = currentVertex;
                        q.add(neighbourVertex);
                    }
                }
                neighbourVertex += 1;
            }
        }
        ArrayList<Integer> path = new ArrayList<Integer>();
        path.add(end);
        while(path.get(path.size() - 1) != start){
            path.add(prevStationID[path.get(path.size() - 1)]);
        }
        Collections.reverse(path);
        return path;
    }
    /**
     * This method performs a linear search that finds the Station
     * object in an array from the Station name.
     * @param stations an array of Station objects.
     * @param name  The name of the station.
     * @return Station The station object with the stated name.
     */
    public static Station linearSearchStation(Station[] stations, String name) throws Exception {
        for(Station i: stations){
            if(i.getName().equals(name)){
                return i;
            }
        }
        throw new Exception("Station not found");
    }
    public static void main(String[] args) throws Exception {
        Station[] stations = new Station[0];
        int[][] ttcGraph = new int[0][0];
        // Read and parse the data/TTC_Subway_Ridership_Data.txt file
        try {
            File myObj = new File(".\\data\\TTC_Subway_Ridership_Data.txt");
            Scanner myReader = new Scanner(myObj);
            int numOfStations = myReader.nextInt();
            myReader.nextLine();
            ttcGraph = new int[numOfStations][numOfStations];
            stations = new Station[numOfStations];
            int ctr = 0;
            while (ctr != numOfStations) {
                String line = myReader.nextLine();
                String[] data = line.split(":");
                stations[ctr] = new Station(data[0].trim().toUpperCase(), Integer.parseInt(data[1]), ctr);
                ctr += 1;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        // Read and parse the data/TTC_Subway_Lines.txt file
        try {
            File myObj = new File(".\\data\\TTC_Subway_Lines.txt");
            Scanner myReader = new Scanner(myObj);
            int numOfLines = myReader.nextInt();
            myReader.nextLine();
            for(int i = 0; i < numOfLines; i++){
                String line = myReader.nextLine();
                String[] data = line.split(":")[1].split(",");
                for(int pair = 1; pair < data.length; pair += 1){
                    ttcGraph[linearSearchStation(stations, data[pair - 1].trim().toUpperCase()).getId()][linearSearchStation(stations, data[pair].trim().toUpperCase()).getId()] = linearSearchStation(stations, data[pair - 1].trim().toUpperCase()).getRidership();
                    ttcGraph[linearSearchStation(stations, data[pair].trim().toUpperCase()).getId()][linearSearchStation(stations, data[pair - 1].trim().toUpperCase()).getId()] = linearSearchStation(stations, data[pair].trim().toUpperCase()).getRidership();
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println("All Possible Stations:");
        for(Station i: stations){
            System.out.print(i.getName() + ", ");
        }
        // Get the starting and destination TTC Subway Station through console input
        System.out.println();
        Scanner in = new Scanner(System.in);
        System.out.println("What is the Starting Station? ");
        String startingStation = in.nextLine().trim().toUpperCase();
        System.out.println("What is the Destination Station? ");
        String destination = in.nextLine().trim().toUpperCase();
        in.close();
        System.out.println("Generating The Shortest Path with Minimum Human Interaction, from " + startingStation + " to " + destination);
        // Generate the shortest path while avoiding the most human contact
        ArrayList<Integer> encodedPath = dijkstraShortestPath(ttcGraph, linearSearchStation(stations, startingStation).getId(), linearSearchStation(stations, destination).getId());
        ArrayList<String> path = new ArrayList<String>();
        for(int i: encodedPath){
            path.add(stations[i].getName());
        }
        System.out.println(path);
    }
}
