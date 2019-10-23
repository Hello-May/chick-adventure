package Values;

public class PathBuilder {
	//eclipse使用
	private static final String ROOT = "";
	
	private static final String IMG_ROOT = "";

	public static String getImg(String path) {
		return ROOT + IMG_ROOT + path;
	}

	private static final String AUDIO_ROOT = "";

	public static String getAudio(String path) {
		return ROOT + AUDIO_ROOT + path;
	}
	
	
	//netbean使用
//    private static final String ROOT = "/resources";
//    
//    //圖片
//    private static final String IMG_ROOT = "/imgs";
//    public static String getImg(String path){
//        return ROOT + IMG_ROOT + path;
//    }
//    
//    //音樂
//    private static final String AUDIO_ROOT = "/audios";
//    public static String getAudio(String path){
//        return ROOT + AUDIO_ROOT + path;
//    }

}
