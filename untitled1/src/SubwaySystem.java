

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SubwaySystem {
    private Map<String, Map<String, Double>> map = new HashMap<>();
    private Map<String, List<String>> lines = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public SubwaySystem(String filename) throws FileNotFoundException {
        readData(filename);
    }

    private void readData(String filename) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(filename));
        String currentLine = null;

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            if (line.contains("号线")) {
                currentLine = line.trim();
                lines.putIfAbsent(currentLine, new ArrayList<>());
            } else if (line.contains("---")) {
                String[] parts = line.split("\t");
                String[] stations = parts[0].split("---");
                Double distance = Double.parseDouble(parts[1]);
                String station1 = stations[0].trim();
                String station2 = stations[1].trim();
                addEdge(station1, station2, distance);
                addEdge(station2, station1, distance);
                lines.get(currentLine).add(station1);
                lines.get(currentLine).add(station2);
            }
        }
        fileScanner.close();
    }

    private void addEdge(String from, String to, double distance) {
        map.putIfAbsent(from, new HashMap<>());
        map.get(from).put(to, distance);
    }

    public void findTransferStations() {
        System.out.println("所有中转站：");

        for (String station : map.keySet()) {
            if (linesContainMultiple(station)) {
                System.out.println(station + ": " + getStationLines(station));
            }
        }

        if (!hasTransferStations()) {
            System.out.println("未找到任何中转站。");
        }
    }

    private String getStationLines(String station) {
        StringBuilder linesBuilder = new StringBuilder();
        Set<String> stationLines = new HashSet<>();

        for (String line : lines.keySet()) {
            if (lines.get(line).contains(station)) {
                stationLines.add(line);
            }
        }

        for (String line : stationLines) {
            linesBuilder.append(line).append(", ");
        }

        return linesBuilder.substring(0, linesBuilder.length() - 2); // Remove the trailing comma and space
    }

    private boolean hasTransferStations() {
        for (String station : map.keySet()) {
            if (linesContainMultiple(station)) {
                return true;
            }
        }
        return false;
    }

    private boolean linesContainMultiple(String station) {
        Set<String> stationLines = new HashSet<>();

        for (String line : lines.keySet()) {
            if (lines.get(line).contains(station)) {
                stationLines.add(line);
            }
        }

        return stationLines.size() > 1;
    }
    @Override
    public String toString() {
        return this.map.values().toString();
    }

    public Set<String> getNearbyStations(String stationName, double distanceThreshold) {
        Set<String> nearbyStations = new HashSet<>();

        for (String line : map.keySet()) {
            Map<String, Double> stationsOnLine = map.get(line);
            for (String station : stationsOnLine.keySet()) {
                if (!station.equals(stationName)) {
                    double distance = stationsOnLine.get(station);
                    if (distance <= distanceThreshold) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("<").append(station).append(", ")
                                .append(line).append(" 号线, ").append(distance).append(">");
                        nearbyStations.add(sb.toString());
                    }
                }
            }
        }

        return nearbyStations;
    }

    public List<List<String>> getAllPaths(String startStation, String endStation) {
        List<List<String>> allPaths = new ArrayList<>();
        if (!map.containsKey(startStation) || !map.containsKey(endStation)) {
            return allPaths; // Return empty list if start or end station is not found
        }
        Set<String> visited = new HashSet<>();
        List<String> path = new ArrayList<>();
        path.add(startStation);
        dfs(startStation, endStation, visited, path, allPaths);
        return allPaths;
    }

    private void dfs(String currentStation, String endStation, Set<String> visited, List<String> path, List<List<String>> allPaths) {
        visited.add(currentStation);
        if (currentStation.equals(endStation)) {
            allPaths.add(new ArrayList<>(path));
        } else {
            Map<String, Double> stationsOnLine = map.getOrDefault(currentStation, new HashMap<>());
            for (String nextStation : stationsOnLine.keySet()) {
                if (!visited.contains(nextStation)) {
                    path.add(nextStation);
                    dfs(nextStation, endStation, visited, path, allPaths);
                    path.remove(path.size() - 1);
                }
            }
        }
        visited.remove(currentStation);
    }
}