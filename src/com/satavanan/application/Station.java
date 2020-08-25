package com.satavanan.application;

/** Represents a TTC Subway Station.
 * @author Satavanan Senthuran
 */
public class Station {
    private int ridership;
    private String name;
    private int id;
    private Station[] connectingStations;

    /** Creates an TTC Subway Station with a name, ID, and ridership data.
     * @param name The TTC Subway Station's name.
     * @param ridership The number of people on the station in one day.
     * @param id A unique identification number for the TTC Subway Station.
     */
    public Station(String name, int ridership, int id){
        this.name = name;
        this.ridership = ridership;
        this.id = id;
        this.connectingStations = new Station[0];
    }

    public void setConnectingStations(Station[] connectingStations) {
        this.connectingStations = connectingStations;
    }

    public Station[] getConnectingStations() {
        return this.connectingStations;
    }

    /** Gets the TTC Subway Station’s ridership data.
     * @return An int representing the ridership data.
     */
    public int getRidership() {
        return this.ridership;
    }

    /** Gets the TTC Subway Station’s name.
     * @return A String representing the name.
     */
    public String getName() {
        return this.name;
    }

    /** Gets the TTC Subway Station’s unique ID.
     * @return An int representing the ID.
     */
    public int getId() {
        return this.id;
    }

    public String toString(){
        return this.getName();
    }
}
