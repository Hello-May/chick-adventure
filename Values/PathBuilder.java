package Values;

public class PathBuilder {
	private static final String ROOT = "";
	
	private static final String IMG_ROOT = "";

	public static String getImg(String path) {
		return ROOT + IMG_ROOT + path;
	}

	private static final String AUDIO_ROOT = "";

	public static String getAudio(String path) {
		return ROOT + AUDIO_ROOT + path;
	}

}
