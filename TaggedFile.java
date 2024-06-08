package studiplayer.audio;

import java.util.Map;
import studiplayer.basic.TagReader;


public class TaggedFile extends SampledFile {
    private String album;

    // Define a default constructor
    public TaggedFile() {
        
    }

    public TaggedFile(String path) throws NotPlayableException {
        super(path);
        readAndStoreTags();
    }

    @Override
    public String getTitle() {
        return super.title;
    }

    @Override
    public String getAuthor() {
        return super.author;
    }

    public String getAlbum() {
        return album != null ? album.strip() : "";
    }

    // Change visibility to protected
    public void readAndStoreTags() throws NotPlayableException {
        try {
            Map<String, Object> tagMap = TagReader.readTags(getPathname());
            String title = (String) tagMap.getOrDefault("title", super.title);
            String author = (String) tagMap.getOrDefault("author", super.author);
            album = (String) tagMap.getOrDefault("album", "");

            Object durationObj = tagMap.get("duration");
            if (durationObj != null && durationObj instanceof Long) {
                duration = (long) durationObj;
            } else {
                System.err.println("Duration tag is missing or invalid.");
            }

            super.title = title.strip();
            super.author = author.strip();
        } catch (Exception e) {
            throw new NotPlayableException(getPathname(), "Error reading tags", e);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (!getAuthor().isEmpty()) {
            builder.append(getAuthor()).append(" - ");
        }
        if (!getTitle().isEmpty()) {
            builder.append(getTitle()).append(" - ");
        }
        if (!getAlbum().isEmpty()) {
            builder.append(getAlbum()).append(" - ");
        }
        builder.append(formatDuration());
        return builder.toString();
    }


    public static void main(String[] args) {
        try {
            TaggedFile f3 = new TaggedFile("audiofiles/beethoven-ohne-album.mp3");
            System.out.println(f3.toString());
            f3.play();
        } catch (NotPlayableException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }
}
