import java.io.FileNotFoundException;
import java.util.*;
public class Test {
    public static void main(String[] args) {
        try {
            SubwaySystem subwaySystem = new SubwaySystem("D:/subway.txt");
            System.out.println(subwaySystem);
            System.out.println();


            //寻找中转站
            subwaySystem.findTransferStations();
            System.out.println();

            // 测试获取附近站点的功能
            String stationName = "华中科技大学站";
            double distanceThreshold = 1.0;
            System.out.println("Nearby stations within " + distanceThreshold + " km of " + stationName + ":");
            Set<String> nearbyStations = subwaySystem.getNearbyStations(stationName, distanceThreshold);
            for (String station : nearbyStations) {
                System.out.println(station);
            }
            System.out.println();


            //查询两站点间所有路线
            String start = "华中科技大学";
            String end = "洪山广场";
            List<List<String>> allPaths = subwaySystem.getAllPaths(start, end);
            System.out.println("All paths from " + start + " to " + end + ":");
            for (List<String> path : allPaths) {
                System.out.println(path);
            }
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到：" + e.getMessage());
        }
    }
}