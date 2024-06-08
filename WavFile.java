package studiplayer.audio;

import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {

    public WavFile(String path) throws NotPlayableException {
        super(path);
        readAndSetDurationFromFile();
    }

    public WavFile() {
        // Default constructor
    }

    public static long computeDuration(long numberOfFrames, float frameRate) {
        return (long) ((numberOfFrames / frameRate) * 1000000);
    }
    // Change visibility to public
    public void readAndSetDurationFromFile() throws NotPlayableException {
        try {
            WavParamReader.readParams(super.getPathname());
            long numberOfFrames = WavParamReader.getNumberOfFrames();
            float frameRate = WavParamReader.getFrameRate();
            duration = computeDuration(numberOfFrames, frameRate);
        } catch (Exception e) {
            throw new NotPlayableException(super.getPathname(), "Error reading WAV file parameters");
        }
    }


    @Override
    public String toString() {
        String filename = getFilename();
        int extensionIndex = filename.lastIndexOf('.');
        String title = (extensionIndex != -1) ? filename.substring(0, extensionIndex) : filename;
        return String.format("%s - %s", title, formatDuration());

    }
}
