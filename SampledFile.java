package studiplayer.audio;

import java.io.File;    
import studiplayer.basic.BasicPlayer;

public abstract class SampledFile extends AudioFile {
    protected long duration; // Duration of the audio file in microseconds

    public SampledFile() {
        // Default constructor
    }

    public SampledFile(String pathname) throws NotPlayableException {
        super(pathname);
        if (!isReadable(pathname)) {
            throw new NotPlayableException(pathname, "Cannot read file with path: " + pathname);
        }
    }

    // Method to format time in microseconds to "mm:ss" format
    public static String timeFormatter(long timeInMicroSeconds) {
        if (timeInMicroSeconds < 0 || timeInMicroSeconds > Long.MAX_VALUE) {
            throw new IllegalArgumentException("Invalid time format");
        }
        // Convert time to minutes and seconds
        long totalSeconds = timeInMicroSeconds / 1000000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        if (minutes > 99) {
            throw new IllegalArgumentException("Invalid time format");
        }

        return String.format("%02d:%02d", minutes, seconds);
    }

    // Method to play, pause, and stop the audio file
    @Override
    public void play() throws NotPlayableException {
        try {
            BasicPlayer.play(super.getPathname());
        } catch (Exception e) {
            throw new NotPlayableException(super.getPathname(), "Error playing audio file", e);
        }
    }

    @Override
    public void togglePause() {
        BasicPlayer.togglePause();
    }

    @Override
    public void stop() {
        BasicPlayer.stop();
    }

    @Override
    public String formatDuration() {
        return timeFormatter(this.duration);
    }

    @Override
    public String formatPosition()  {
        return timeFormatter(BasicPlayer.getPosition());
    }

    protected long getDuration() {
        return this.duration;
    }

    protected boolean isReadable(String path) {
        File file = new File(path);
        return file.exists() && file.canRead();
    }
}
