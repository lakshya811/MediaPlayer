package studiplayer.audio;

import java.io.File;

public abstract class AudioFile {
    private String pathname = "";
    private String filename = "";
    protected String author = "";
    protected String title = "";
    protected long duration;

    public abstract void play() throws NotPlayableException;

    public abstract void togglePause();

    public abstract void stop();
    public AudioFile() {}
    public abstract String formatDuration() throws NotPlayableException;

    public abstract String formatPosition() throws NotPlayableException;

    public static void main(String[] args) {
        String filename = "Hans-Georg Sonstwas - Blue-eyed boy-friend";
        AudioFile af = new AudioFile() {
            @Override
            public void play() throws NotPlayableException {
                // Implementing play functionality
            }

            @Override
            public void togglePause() {
                // Implementing togglePause functionality
            }

            @Override
            public void stop() {
                // Implementing stop functionality
            }

            @Override
            public String formatDuration() {
                // Implementing formatDuration functionality
                return null; // Replacing with actual implementation
            }

            @Override
            public String formatPosition() {
                return null; // Replacing with actual implementation
            }
        };
        af.parsePathname(filename);
    }


   protected AudioFile(String pathname) throws NotPlayableException {
        this.pathname = pathname.trim(); 
        if (!isReadable(pathname)) {
            throw new NotPlayableException(pathname, "File is not readable");
        }
        parsePathname(this.getPathname());
        parseFilename(this.getFilename());
    }

    public void parsePathname(String path) {
        if (path.isBlank())
            return;

        // Remove leading and trailing spaces
        String cleanPath = path.strip();

        String fileName = "";

        if (isWindows()) {
            // Converting forward slashes to backslashes
            StringBuilder sb = new StringBuilder();
            boolean prevBackslash = false;
            for (char c : cleanPath.toCharArray()) {
                if (c == '/') {
                    if (!prevBackslash) {
                        sb.append('\\');
                        prevBackslash = true;
                    }
                } else {
                    sb.append(c);
                    prevBackslash = false;
                }
            }
            cleanPath = sb.toString();

            // Removing redundant backslashes
            cleanPath = cleanPath.replaceAll("\\\\+", "\\\\");

            // Splitting path elements using backslash as separator
            String[] pathElements = cleanPath.split("\\\\");
            if (!cleanPath.endsWith("\\")) // Checking if path doesn't end with a backslash
                fileName = pathElements[pathElements.length - 1].trim();
        } else {
            // Replacing backslashes with forward slashes
            cleanPath = cleanPath.replace("\\", "/");

            // Removing redundant slashes
            cleanPath = cleanPath.replaceAll("/+", "/");

            // Handling drive letters for Linux
            if (cleanPath.matches("^\\w:\\/.*")) {
                char driveLetter = cleanPath.charAt(0);
                cleanPath = "/" + driveLetter + cleanPath.substring(2);
            }

            // Splitting path elements using slash as separator
            String[] pathElements = cleanPath.split("/");

            // Checking if path doesn't end with a slash
            boolean endsWithSlash = cleanPath.endsWith("/");
            if (!cleanPath.isEmpty() && !endsWithSlash)
                fileName = pathElements[pathElements.length - 1].trim();
        }

        this.pathname = cleanPath;
        this.filename = fileName;
    }

    public void parseFilename(String filename) {
        if (filename.isBlank()) {
            return;
        }

        String name = filename; // Trimming the filename

        // Removing file extension
        int extensionIndex = name.lastIndexOf('.');
        if (extensionIndex != -1) {
            name = name.substring(0, extensionIndex);
        }

        // Split filename into author and title
        String[] divide = name.split(" -");
        if (divide.length == 2) {
            author = divide[0].strip(); // Trimming author
            title = divide[1].strip(); // Trimming title
        } else {
            title = name.strip();
        }
    }

    protected long getDuration() {
        return duration;
    }

    public String getPathname() {
        return pathname;
    }

    public String getFilename() {
        return filename;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        if (!author.isBlank() && !title.isBlank()) {
            return String.format("%s - %s", author, title);
        } else if (!author.isBlank()) {
            return author;
        } else {
            return title;
        }
    }

    private boolean isReadable(String path) {
        File file = new File(path);
        return file.exists() && file.canRead();
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
