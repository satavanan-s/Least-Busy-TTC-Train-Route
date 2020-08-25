package com.satavanan.tests;

import com.satavanan.application.Main;
import com.satavanan.application.Station;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;

public class MainTest {

    Station testNode1 = new Station("1", 1, 0);
    Station testNode2 = new Station("2", 2, 1);
    Station testNode3 = new Station("3", 3, 2);
    Station testNode4 = new Station("4", 4, 3);
    Station[] testHelper = {testNode1, testNode2, testNode3, testNode4};

    @org.junit.Test
    public void dijkstraShortestPathTest1() {
        assert Main.dijkstraShortestPath(testHelper, testNode1, testNode4) == null;
    }
    @org.junit.Test
    public void dijkstraShortestPathTest2() {
        testNode1.setConnectingStations(new Station[]{testNode2, testNode3});
        testNode2.setConnectingStations(new Station[]{testNode4});
        testNode3.setConnectingStations(new Station[]{testNode4});
        ArrayList<Station> expectedOutput = new ArrayList<>(Arrays.asList(testNode1, testNode2, testNode4));
        assert expectedOutput.equals(Main.dijkstraShortestPath(testHelper, testNode1, testNode4));
    }
//
//    @org.junit.Test
//    public void dijkstraShortestPathTest3() {
//        ArrayList<Integer> expectedOutput = new ArrayList<Integer>(Arrays.asList(1));
//        assert expectedOutput.equals(Main.dijkstraShortestPath(testGraph, 1, 1));
//    }

    @org.junit.Test
    public void linearSearchStationTest1() throws Exception {
        assert Main.linearSearchStation(testHelper, "1").equals(testNode1);
    }

    @org.junit.Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @org.junit.Test
    public void linearSearchStationTest2() throws Exception {
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Station not found");
        Main.linearSearchStation(testHelper, "0");
    }

    @org.junit.Test
    public void appendArrayTest1(){
        assert Main.appendArray(testHelper, new Station("test", 13, 4)).length == (testHelper.length + 1);
    }

    @org.junit.Test
    public void appendArrayTest2(){
        assert Main.appendArray(new Station[]{}, new Station("test", 13, 4)).length == 1;
    }

    @org.junit.Test
    public void initializeSubwayStationsTest(){
        assert Main.initializeSubwayStations().length == 75;
    }

    @org.junit.Test
    public void initializeSubwayConnectionsTest1(){
        assert Main.initializeSubwayConnections(Main.initializeSubwayStations())[0].getConnectingStations().length == 2;
    }

    @org.junit.Test
    public void initializeSubwayConnectionsTest2() throws Exception {
        // no assertion because an exception is handled as a Failure
        Main.linearSearchStation(Main.initializeSubwayConnections(Main.initializeSubwayStations())[0].getConnectingStations(), "FINCH WEST");
    }
}