package studiplayer.audio;

import java.util.Comparator;

public class TitleComparator implements Comparator<AudioFile> {

    @Override
    public int compare(AudioFile file1, AudioFile file2) {
        String t1 = file1.getTitle();
        String t2 = file2.getTitle();

        if (t1 == null || t2 == null) {
            if (t1 == null && t2 == null) {
                return 0;
            }
            return t1 == null ? -1 : 1;
        }

        return t1.compareTo(t2);
    }
}
