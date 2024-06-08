package studiplayer.audio;

public class AudioFileFactory {

    public static AudioFile createAudioFile(String path) throws NotPlayableException {
        String extension = getFileExtension(path);
        extension = extension.toLowerCase();
        try {
            switch (extension) {
                case "wav":
                    return new WavFile(path);
                case "ogg":
                case "mp3":
                    return new TaggedFile(path);
                default:
                    throw new NotPlayableException(path, "Unknown file extension");
            }
        } catch (IllegalArgumentException e) {
            throw new NotPlayableException(path, "Error creating audio file", e);
        }
    }

    private static String getFileExtension(String path) throws NotPlayableException {
        int lastDotIndex = path.lastIndexOf('.');
        if (lastDotIndex == -1) {
            throw new NotPlayableException(path, "No file extension found in path");
        }
        return path.substring(lastDotIndex + 1);
    }
}
