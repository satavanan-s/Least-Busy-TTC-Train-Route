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
     * two nodes in a weighted directed graph represented
     * as an adjacency list.
     * @param stations a weighted directed graph represented
     *             as an adjacency list.
     * @param start  The starting node.
     * @param end The destination node.
     * @return ArrayList<Station> The shortest path.
     */
    public static ArrayList<Station> dijkstraShortestPath(Station[] stations, Station start, Station end){
        if(start == null || end == null){
            return null;
        }
        Queue<Station> q = new LinkedList<>();
        int[] distance = new int[stations.length];
        for(int  i = 0; i < distance.length; i ++){
            distance[i] = Integer.MAX_VALUE;
        }
        distance[start.getId()] = 0;
        Station[] prevStationID = new Station[stations.length];
        for(int i = 0; i < prevStationID.length; i++){
            prevStationID[i] = null;
        }
        q.add(start);
        while(!q.isEmpty()){
            Station currentVertex = q.remove();
            for(Station a: currentVertex.getConnectingStations()){
                    if(distance[a.getId()] > distance[currentVertex.getId()] + a.getRidership()){
                        distance[a.getId()] = distance[currentVertex.getId()] + a.getRidership();
                        prevStationID[a.getId()] = currentVertex;
                        q.add(a);
                    }
            }
        }
        ArrayList<Station> path = new ArrayList<>();
        path.add(end);
        while(path.get(path.size() - 1) != start){
            if(prevStationID[path.get(path.size() - 1).getId()] == null){
                return null;
            }
            path.add(prevStationID[path.get(path.size() - 1).getId()]);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * This method appends a Station into a Station array.
     * @param currentArray an array of Stations
     * @param newElement  The station to append in currentArray.
     * @return Station[] The array currentArray and newElement
     *              appended at the end.
     */
    public static Station[] appendArray(Station[] currentArray, Station newElement){
        Station[] newArr = new Station[currentArray.length + 1];
        for(int i = 0; i < currentArray.length; i++){
            newArr[i] = currentArray[i];
        }
        newArr[newArr.length - 1] = newElement;
        return newArr;
    }

    /**
     * This method performs a linear search that finds the Station
     * object in an array from the Station name.
     * @param listStations an array of Station objects.
     * @param name  The name of the station.
     * @return Station The station object with the stated name.
     */
    public static Station linearSearchStation(Station[] listStations, String name) throws Exception {
        for(Station i: listStations){
            if(i.getName().equals(name)){
                return i;
            }
        }
        throw new Exception("Station not found");
    }

    /**
     * This method reads TTC_Subway_Ridership_Data.txt and
     * creates an Array of Station instances representing each
     * TTC subway station
     * @return Station[] An array of all the TTC Subway Stations
     */
    public static Station[] initializeSubwayStations(){
        Station[] stations = new Station[0];
        try {
            File myObj = new File(".\\data\\TTC_Subway_Ridership_Data.txt");
            Scanner myReader = new Scanner(myObj);
            int numOfStations = myReader.nextInt();
            myReader.nextLine();
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
        return stations;
    }

    /**
     * This method reads TTC_Subway_Lines.txt and sets the
     * connecting stations for all subway stations
     * @param stations an array of Station objects.
     * @return Station[] an array of Station objects with connected
     * nodes.
     */
    public static Station[] initializeSubwayConnections(Station[] stations){
        try {
            File myObj = new File(".\\data\\TTC_Subway_Lines.txt");
            Scanner myReader = new Scanner(myObj);
            int numOfLines = myReader.nextInt();
            myReader.nextLine();
            for(int i = 0; i < numOfLines; i++){
                String line = myReader.nextLine();
                String[] data = line.split(":")[1].split(",");
                for(int pair = 1; pair < data.length; pair += 1){
                    Station[] one = appendArray(linearSearchStation(stations, data[pair-1].trim().toUpperCase()).getConnectingStations(), linearSearchStation(stations, data[pair].trim().toUpperCase()));
                    linearSearchStation(stations, data[pair-1].trim().toUpperCase()).setConnectingStations(one);
                    Station[] two = appendArray(linearSearchStation(stations, data[pair].trim().toUpperCase()).getConnectingStations(), linearSearchStation(stations, data[pair-1].trim().toUpperCase()));
                    linearSearchStation(stations, data[pair].trim().toUpperCase()).setConnectingStations(two);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stations;
    }

    /**
     * This method obtains a valid staring and destination ttc
     * subway station from the user.
     * @param stations an array of Station objects.
     * @return Station[] the starting station (index=0) and the
     * destination station (index=1).
     */
    public static Station[] getValidInput(Station[] stations){
        System.out.println("All Possible Stations:");
        for(Station i: stations){
            System.out.print(i.getName() + ", ");
        }
        System.out.println();
        Scanner in = new Scanner(System.in);
        boolean isValid = false;
        Station start, end;
        start = end = null;
        while(!isValid){
            try {
            System.out.println("What is the Starting Station? ");
            String startingStation = in.nextLine().trim().toUpperCase();
            System.out.println("What is the Destination Station? ");
            String destination = in.nextLine().trim().toUpperCase();
            start = linearSearchStation(stations, startingStation);
            end = linearSearchStation(stations, destination);
            isValid = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        in.close();
        return new Station[]{start,end};
    }

    /**
     * This method generates and prints the path of least human
     * contact.
     * @param stations an array of Station objects (represents the TTC
     *                 subway system).
     *
     * @param inputNodes an array that holdes the starting (index = 0) and
     *                   destination (index = 1) node.
     */
    public static void generateAndDisplayPath(Station[] stations, Station[] inputNodes){
        System.out.println("Generating The Shortest Path with Minimum Human Interaction, from " + inputNodes[0] + " to " + inputNodes[1]);
        ArrayList<Station> stationPath = dijkstraShortestPath(stations, inputNodes[0], inputNodes[1]);
        System.out.println("Path of Least Human Contact");
        for(Station i: stationPath){
            System.out.println(i);
        }
    }
    public static void main(String[] args) {
        Station[] stations = initializeSubwayConnections(initializeSubwayStations());
        generateAndDisplayPath(stations, getValidInput(stations));
    }
}
