package studiplayer.audio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;
import java.util.ArrayList;

public class PlayList implements Iterable<AudioFile> {

    private LinkedList<AudioFile> audioFiles;
    private ControllablePlayListIterator iterator;
    private AudioFile currentFile;
    private String searchQuery;
    private SortCriterion sortCriterion;

    // Constructors
    public PlayList() {
        this.audioFiles = new LinkedList<>();
        this.searchQuery = "";
        this.sortCriterion = SortCriterion.DEFAULT;
        resetIterator();
    }

    public PlayList(String m3uFilePath) {
        this();
        loadFromM3U(m3uFilePath);
    }

    // Adding and removing audio files
    public void add(AudioFile file) {
        audioFiles.add(file);
        resetIterator();
    }

    public void remove(AudioFile file) {
        audioFiles.remove(file);
        resetIterator();
    }

    public int size() {
        return audioFiles.size();
    }

    // Iterator control
    public void nextSong() {
        if (audioFiles.isEmpty()) {
            currentFile = null;
            return;
        }
        if (iterator == null || !iterator.hasNext()) {
            resetIterator();
        } else {
            currentFile = iterator.next();
        }
    }

    // Loading and saving M3U files
    public void loadFromM3U(String path) {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty() && !line.startsWith("#")) {
                    lines.add(line);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading M3U file: " + e.getMessage(), e);
        }

        audioFiles.clear();
        for (String filepath : lines) {
            try {
                audioFiles.add(AudioFileFactory.createAudioFile(filepath));
            } catch (NotPlayableException e) {
                System.err.printf("Skipping file %s due to NotPlayableException%n", filepath);
            }
        }
        resetIterator();
    }

    public void saveAsM3U(String path) {
        try (FileWriter writer = new FileWriter(path)) {
            for (AudioFile file : audioFiles) {
                writer.write(file.getPathname() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving M3U file: " + e.getMessage(), e);
        }
    }

    // Jumping to a specific audio file
    public void jumpToAudioFile(AudioFile audioFile) {
        if (audioFiles.contains(audioFile)) {
            currentFile = audioFile;
            iterator.jumpToAudioFile(audioFile);
        }
    }

    // Getters
    public LinkedList<AudioFile> getList() {
        return audioFiles;
    }

    public AudioFile currentAudioFile() {
        return currentFile;
    }

    public String getSearch() {
        return searchQuery;
    }

    public SortCriterion getSortCriterion() {
        return sortCriterion;
    }

    // Setters
    public void setSearch(String query) {
        this.searchQuery = query;
        this.iterator = new ControllablePlayListIterator(this.audioFiles, this.searchQuery, this.sortCriterion);
        resetIterator();
    }

    public void setSortCriterion(SortCriterion sortCriterion) {
        this.sortCriterion = sortCriterion;
        this.iterator = new ControllablePlayListIterator(this.audioFiles, this.searchQuery, this.sortCriterion);
        resetIterator();
    }

    private void resetIterator() {
        this.iterator = new ControllablePlayListIterator(audioFiles, searchQuery, sortCriterion);
        if (!audioFiles.isEmpty()) {
            currentFile = iterator.next();
        } else {
            currentFile = null;
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (Iterator<AudioFile> it = audioFiles.iterator(); it.hasNext();) {
            result.append(it.next().toString());
            if (it.hasNext()) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }


    @Override
    public Iterator<AudioFile> iterator() {
        return new ControllablePlayListIterator(audioFiles, searchQuery, sortCriterion);
    }
}
