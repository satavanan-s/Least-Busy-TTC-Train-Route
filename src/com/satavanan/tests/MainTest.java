package com.satavanan.tests;

import com.satavanan.application.Main;
import com.satavanan.application.Station;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;

public class MainTest {

    int[][] testGraph = {
            {0, 1, 5, 3, 0},
            {1, 0, 0, 1, 1},
            {8, 0, 0, 0, 1},
            {1, 1, 2, 0, 0},
            {0, 3, 1, 1, 0}
    };

    @org.junit.Test
    public void dijkstraShortestPathTest1() {
        ArrayList<Integer> expectedOutput = new ArrayList<Integer>(Arrays.asList(0, 1, 4));
        assert expectedOutput.equals(Main.dijkstraShortestPath(testGraph, 0, 4));
    }
    @org.junit.Test
    public void dijkstraShortestPathTest2() {
        ArrayList<Integer> expectedOutput = new ArrayList<Integer>(Arrays.asList(4, 3, 1));
        assert expectedOutput.equals(Main.dijkstraShortestPath(testGraph, 4, 1));
    }

    @org.junit.Test
    public void dijkstraShortestPathTest3() {
        ArrayList<Integer> expectedOutput = new ArrayList<Integer>(Arrays.asList(1));
        assert expectedOutput.equals(Main.dijkstraShortestPath(testGraph, 1, 1));
    }

    Station[] testHelper = {new Station("0", 0, 0), new Station("1", 1, 1), new Station("2", 2, 2)};

    @org.junit.Test
    public void linearSearchStationTest1() throws Exception {
        assert Main.linearSearchStation(testHelper, "0").equals(testHelper[0]);
    }

    @org.junit.Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @org.junit.Test()
    public void linearSearchStationTest2() throws Exception {
        exceptionRule.expect(Exception.class);
        exceptionRule.expectMessage("Station not found");
        Main.linearSearchStation(testHelper, "3");
    }
}