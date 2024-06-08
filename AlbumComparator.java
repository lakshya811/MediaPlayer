package studiplayer.audio;

import java.util.Comparator;

public class AlbumComparator implements Comparator<AudioFile> {
    @Override
    public int compare(AudioFile file1, AudioFile file2) {
        // Check if both files are instances of TaggedFile
        boolean isFile1Tagged = file1 instanceof TaggedFile;
        boolean isFile2Tagged = file2 instanceof TaggedFile;

        // Case when either or both files are not TaggedFile instances
        if (!isFile1Tagged || !isFile2Tagged) {
            // Place non-TaggedFile instances first in the sorted list
            if (!isFile1Tagged && !isFile2Tagged) {
                return 0; // Both files are not tagged, so they are considered equal
            } else {
                return isFile1Tagged ? 1 : -1; // Place the TaggedFile instance first
            }
        }

        // Both files are instances of TaggedFile, compare their album information
        String album1 = ((TaggedFile) file1).getAlbum();
        String album2 = ((TaggedFile) file2).getAlbum();

        // Compare album information case-insensitively
        return album1.compareToIgnoreCase(album2);
    }
}
