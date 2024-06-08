package studiplayer.audio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ControllablePlayListIterator implements Iterator<AudioFile> {

    private List<AudioFile> audioFiles;
    private int currentIndex;

    public ControllablePlayListIterator(List<AudioFile> audioFiles) {
        this.audioFiles = new ArrayList<>(audioFiles);
        this.currentIndex = 0;
    }

    public ControllablePlayListIterator(List<AudioFile> audioFiles, String search, SortCriterion sortCriterion) {
        List<AudioFile> filteredFiles = filterFiles(audioFiles, search);
        this.audioFiles = sortFiles(filteredFiles, sortCriterion);
        this.currentIndex = 0;
    }

    private List<AudioFile> filterFiles(List<AudioFile> audioFiles, String search) {
        if (search == null || search.isEmpty()) {
            return new ArrayList<>(audioFiles);
        }
        List<AudioFile> filteredFiles = new ArrayList<>();
        String lowerSearch = search.toLowerCase();
        for (AudioFile file : audioFiles) {
            if (file.getAuthor().toLowerCase().contains(lowerSearch) ||
                file.getTitle().toLowerCase().contains(lowerSearch) ||
                (file instanceof TaggedFile && 
                 ((TaggedFile) file).getAlbum() != null && 
                 ((TaggedFile) file).getAlbum().toLowerCase().contains(lowerSearch))) {
                filteredFiles.add(file);
            }
        }
        return filteredFiles;
    }

    private List<AudioFile> sortFiles(List<AudioFile> audioFiles, SortCriterion sortCriterion) {
        switch (sortCriterion) {
            case AUTHOR:
                audioFiles.sort(new AuthorComparator());
                break;
            case TITLE:
                audioFiles.sort(new TitleComparator());
                break;
            case ALBUM:
                audioFiles.sort(new AlbumComparator());
                break;
            case DURATION:
                audioFiles.sort(new DurationComparator());
                break;
            default:
                break;
        }
        return audioFiles;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < audioFiles.size();
    }

    @Override
    public AudioFile next() {
        if (!hasNext()) {
            currentIndex = 0; // Reset index if reached the end
        }
        return audioFiles.get(currentIndex++);
    }

    public AudioFile jumpToAudioFile(AudioFile file) {
        int index = audioFiles.indexOf(file);
        if (index != -1) {
            currentIndex = index + 1; // Move to the next index
            return file;
        } else {
            System.out.println("AudioFile not found in the playlist: " + file);
            return null;
        }
    }
}
