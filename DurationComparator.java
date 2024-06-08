package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile> {

    @Override
    public int compare(AudioFile file1, AudioFile file2) {
        if (file1 == null || file2 == null) {
            throw new NullPointerException("One or both of the files are null.");
        }

        // Handle files with missing duration information
        if (file1.getDuration() == -1) {
            return -1;
        } else if (file2.getDuration() == -1) {
            return 1;
        }

        return Long.compare(file1.getDuration(), file2.getDuration());
    }
}