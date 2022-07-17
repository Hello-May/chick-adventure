package Controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//��ҼҦ� Singleton
public class ImageController {
	private static class KeyPair {
		private String path;
		private BufferedImage img;

		public KeyPair(String path, BufferedImage img) {
			this.path = path;
			this.img = img;
		}
	}

	// ���
	private static ImageController irc;

	// ���e
	private ArrayList<KeyPair> imgPairs;

	private ImageController() {
		imgPairs = new ArrayList<>();
	}

//	private static void outside() {	//���p�b�~����ImageResourceController�A�O���P�@�]ImageResourceController
//		ImageResourceController irc = new ImageResourceController();
//		irc = ImageResourceController.getInstance();
//	}

	public static ImageController getInstance() {
		if (irc == null) {
			irc = new ImageController();
		}
		return irc;
	}

	public BufferedImage tryGetImage(String path) {
		KeyPair pair = findKeyPair(path);
		if (pair == null) {
			return addImage(path);
		}
		return pair.img;
	}

	private BufferedImage addImage(String path) {
		try {
			BufferedImage img = ImageIO.read(getClass().getResource(path));
			imgPairs.add(new KeyPair(path, img));
			return img;
		} catch (IOException e) {
		}
		return null;
	}

	private KeyPair findKeyPair(String path) {
		for (int i = 0; i < imgPairs.size(); i++) {
			KeyPair pair = imgPairs.get(i);
			if (pair.path.equals(path)) {
				return pair;
			}
		}
		return null;
	}

}
