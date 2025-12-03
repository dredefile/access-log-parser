import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Statistics {
    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

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
    }

    public double getTrafficRace() {
    if (minTime == null || maxTime == null || minTime.equals(maxTime))
        return 0.0;
    long seconds = Duration.between(minTime, maxTime).getSeconds();
    double hours = seconds / 3600;
    return (totalTraffic / hours);
    }
}
