import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class Test {
    public static void main(String[] args) {
        // 测试获取附近站点的功能
        String stationName = "华中科技大学站";
        double distanceThreshold = 1.0;
        System.out.println("Nearby stations within " + distanceThreshold + " km of " + stationName + ":");
        Set<String> nearbyStations = subwaySystem.getNearbyStations(stationName, distanceThreshold);
        for (String station : nearbyStations) {
            System.out.println(station);
        }


    }
}
