import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Statistics {
    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private static HashSet<String> paths = new HashSet<>();
    private static HashMap<String, Integer> frequencyOS = new HashMap<>();

    public Statistics() {
        this.maxTime = null;
        this.minTime = null;
        this.totalTraffic = 0;
    }

    public void addEntry(LogEntry entry) {
        totalTraffic += entry.getResponseSize();
        LocalDateTime time = entry.getDateTime();
        if (minTime == null || time.isBefore(minTime))
            minTime = time;
        if (maxTime == null || time.isAfter(maxTime))
            maxTime = time;
        if (entry.getResponse() == 200) {
            paths.add(entry.getPath());
        UserAgent ua = new UserAgent(entry.getUserAgent());
        String os = ua.getOS().name();
        if (!frequencyOS.containsKey(os)) {
            frequencyOS.put(os, 1);
        } else {
            frequencyOS.put(os, frequencyOS.get(os) + 1);
        }
        }
    }

    public double getTrafficRace() {
    if (minTime == null || maxTime == null || minTime.equals(maxTime))
        return 0.0;
    long seconds = Duration.between(minTime, maxTime).getSeconds();
    double hours = seconds / 3600;
    return (totalTraffic / hours);
    }

    public HashSet<String> getURL() {
        return new HashSet<>(paths);
    }

    public HashMap<String, Integer> getFrequencyOS() {
        return new HashMap<>(frequencyOS);
    }

    public HashMap<String, Double> getOSStatistics() {
        HashMap<String, Double> osStats = new HashMap<>();
        int total = 0;
        for (Integer count : frequencyOS.values()) {
            total += count;
        }
        for (String os : frequencyOS.keySet()) {
            int count = frequencyOS.get(os);
            double share = (double) count / total;
            osStats.put(os, share);
        }
        return osStats;
    }
}
