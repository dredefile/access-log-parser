import java.util.Locale;

public class UserAgent {
    public enum OS {
        WINDOWS, MACOS, LINUX, OTHER
    }

    enum Browser {
        EDGE, FIREFOX, CHROME, OPERA, OTHER
    }

    private final OS os;
    private final Browser browser;

    public UserAgent(String userAgentString) {
        String ua = userAgentString;
        if (ua.contains("Windows")) {
            this.os = OS.WINDOWS;
        } else if (ua.contains("Linux")) {
            this.os = OS.LINUX;
        } else if (ua.contains("Mac OS") || (ua.contains("MacOS"))) {
            this.os = OS.MACOS;
        } else {
            this.os = OS.OTHER;
        }

        if (ua.contains("Edge")) {
            this.browser = Browser.EDGE;
        } else if (ua.contains("Firefox")) {
            this.browser = Browser.FIREFOX;
        } else if (ua.contains("Chrome")) {
            this.browser = Browser.CHROME;
        } else if (ua.contains("Opera")) {
            this.browser = Browser.OPERA;
        } else {
            this.browser = Browser.OTHER;
        }
    }

    public OS getOs() {
        return os;
    }

    public Browser getBrowser() {
        return browser;
    }
}

