import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Statistics {
    private int totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private static HashSet<String> existingPaths = new HashSet<>();
    private static HashMap<String, Integer> frequencyOS = new HashMap<>();
    private static HashSet<String> notExistingPaths = new HashSet<>();
    private static HashMap<String, Integer> frequencyBrowser = new HashMap<>();

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
        if (entry.getResponse() == 200) { //Добавление пути в existingsPaths, если статус код 200
            existingPaths.add(entry.getPath());
        }
        UserAgent ua = new UserAgent(entry.getUserAgent()); //создание переменной типа UA, чтобы
        String os = ua.getOS().name(); //применить методы getOS и name для определения названия OS
        if (!frequencyOS.containsKey(os)) { //Если такой OS нет в списке, то добавляем первой
            frequencyOS.put(os, 1);
        } else {
            frequencyOS.put(os, frequencyOS.get(os) + 1);//если есть OS, то увеличиваем на 1
        }
        if (entry.getResponse() == 404) { //Добавление пути в existingsPaths, если статус код 200
            notExistingPaths.add(entry.getPath());
        }
        UserAgent uaBrowser = new UserAgent(entry.getUserAgent()); //создание переменной типа UA, чтобы
        String browser = uaBrowser.getBrowser().name(); //применить методы getBrowser и name для определения браузера
        if (!frequencyBrowser.containsKey(browser)) { //Если такого браузера нет в списке, то добавляем первым
            frequencyBrowser.put(browser, 1);
        } else {
            frequencyBrowser.put(browser, frequencyBrowser.get(browser) + 1);//если есть OS, то увеличиваем на 1
        }
    }

    public double getTrafficRace() {
    if (minTime == null || maxTime == null || minTime.equals(maxTime))
        return 0.0;
    long seconds = Duration.between(minTime, maxTime).getSeconds();
    double hours = seconds / 3600;
    return (totalTraffic / hours);
    }

    public HashSet<String> getURL() { //возвращает список существующих страниц
        return new HashSet<>(existingPaths);
    }

    public HashSet<String> getNotExistingURL() { //возвращает список несуществующих страниц
        return new HashSet<>(notExistingPaths);
    }

    public HashMap<String, Integer> getFrequencyOS() { //возвращает список OS
        return new HashMap<>(frequencyOS);
    }

    public HashMap<String, Double> getOSStatistics() { //Расчет долей
        HashMap<String, Double> osStats = new HashMap<>();
        int total = 0;
        for (Integer count : frequencyOS.values()) { //Общее количество ОС
            total += count;
        }
        for (String os : frequencyOS.keySet()) { //возвращаем ключи из карты
            int count = frequencyOS.get(os);
            double share = (double) count / total;
            osStats.put(os, share);
        }
        return osStats;
    }

    public HashMap<String, Integer> getFrequencyBrowser() { //возвращает список браузеров
        return new HashMap<>(frequencyBrowser);
    }

    public HashMap<String, Double> getBrowserStatistics() { //Расчет долей
        HashMap<String, Double> browserStats = new HashMap<>();
        int total = 0;
        for (Integer count : frequencyBrowser.values()) { //Общее количество браузеров
            total += count;
        }
        for (String browser : frequencyBrowser.keySet()) { //возвращаем ключи из карты
            int count = frequencyBrowser.get(browser);
            double share = (double) count / total;
            browserStats.put(browser, share);
        }
        return browserStats;
    }
}
