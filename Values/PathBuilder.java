package Values;

public class PathBuilder {
	//eclipse�ϥ�
	private static final String ROOT = "";
	
	private static final String IMG_ROOT = "";

	public static String getImg(String path) {
		return ROOT + IMG_ROOT + path;
	}

	private static final String AUDIO_ROOT = "";

	public static String getAudio(String path) {
		return ROOT + AUDIO_ROOT + path;
	}
	
	
	//netbean�ϥ�
//    private static final String ROOT = "/resources";
//    
//    //�Ϥ�
//    private static final String IMG_ROOT = "/imgs";
//    public static String getImg(String path){
//        return ROOT + IMG_ROOT + path;
//    }
//    
//    //����
//    private static final String AUDIO_ROOT = "/audios";
//    public static String getAudio(String path){
//        return ROOT + AUDIO_ROOT + path;
//    }

}
