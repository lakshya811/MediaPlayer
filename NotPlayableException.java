package studiplayer.audio;

public class NotPlayableException extends Exception {
    private String pathname;

    public NotPlayableException(String pathname, String msg) {
        super(msg);
        this.pathname = pathname;
    }

    public NotPlayableException(String pathname, Throwable t) {
        super(t);
        this.pathname = pathname;
    }

    public NotPlayableException(String pathname, String msg, Throwable t) {
        super(msg, t);
        this.pathname = pathname;
    }

    public String getPathname() {
        return pathname;
    }

    @Override
    public String getMessage() {
        return "Path: " + pathname + ", Message: " + super.getMessage();
    }

    @Override
    public String toString() {
        return "Path: " + pathname + ", " + super.toString();
    }
}
