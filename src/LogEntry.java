import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {
    public enum HttpMethod {
        GET,
        POST,
        PUT,
        PATCH,
        DELETE,
        HEAD,
        OPTIONS
    }
    private final String ip;
    private final LocalDateTime dateTime;
    private final HttpMethod method;
    private final String path;
    private final String referer;
    private final String userAgent;
    private final int response;
    private final int responseSize;



    public LogEntry(String line) {
        int firstSpace = line.indexOf(' ');
        this.ip = line.substring(0, firstSpace); // подстрока IP

        int openBracket = line.indexOf('[');
        int closeBracket = line.indexOf(']', openBracket);
        String dateStr = line.substring(openBracket + 1, closeBracket);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
        ZonedDateTime zdt = ZonedDateTime.parse(dateStr, dtf);
        this.dateTime = zdt.toLocalDateTime();

        int mark1 = line.indexOf('"', closeBracket);
        int spaceAfterMethod = line.indexOf(' ', mark1);
        this.method = HttpMethod.valueOf(line.substring(mark1 + 1, spaceAfterMethod));

        int markAfterPath = line.indexOf('"', spaceAfterMethod);
        this.path = line.substring(spaceAfterMethod + 1, markAfterPath);

        int spaceAfterResponse = line.indexOf(' ', markAfterPath + 2);
        this.response = Integer.parseInt(line.substring(markAfterPath + 2,spaceAfterResponse));

        int spaceAfterResponseSize = line.indexOf(' ', spaceAfterResponse + 1);
        this.responseSize = Integer.parseInt(line.substring(spaceAfterResponse + 1, spaceAfterResponseSize));

        int uaEnd = line.lastIndexOf('"');
        int uaStart = line.lastIndexOf('"', uaEnd - 1);
        this.userAgent = line.substring(uaStart + 1, uaEnd);

        int refEnd = line.lastIndexOf('"', uaStart - 1);
        int refStart = line.lastIndexOf('"', refEnd - 1);
        this.referer = line.substring(refStart + 1, refEnd);
    }

    public int getResponseSize() {
        return responseSize;
    }

    public int getResponse() {
        return response;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getReferer() {
        return referer;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getIp() {
        return ip;
    }
}
