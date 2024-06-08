package studiplayer.audio;

import java.util.Comparator;

public class AuthorComparator implements Comparator<AudioFile> {

    @Override
    public int compare(AudioFile file1, AudioFile file2) {
        if (file1 == null || file2 == null) {
            throw new NullPointerException("One or both of the files are null.");
        }

        String author1 = (file1 instanceof TaggedFile) ? ((TaggedFile) file1).getAuthor() : null;
        String author2 = (file2 instanceof TaggedFile) ? ((TaggedFile) file2).getAuthor() : null;

        if (author1 != null && author2 != null) {
            return author1.compareToIgnoreCase(author2);
        } else if (author1 != null) {
            return -1; // author1 is not null, but author2 is
        } else if (author2 != null) {
            return 1; // author2 is not null, but author1 is
        } else {
            return file1.getPathname().compareToIgnoreCase(file2.getPathname());
        }
    }
}
