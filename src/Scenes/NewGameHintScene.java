package Scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Controllers.ImageController;
import Controllers.SceneController;
import IO.CommandSolver.KeyCommandListener;
import Utils.Global;
import Values.ImagePath;
import Values.PathBuilder;

public class NewGameHintScene extends Scene {
	private KeyCommandListener keycommandListener;
	private BufferedImage image;

	public NewGameHintScene(SceneController sceneController) {
		super(sceneController);
		keycommandListener = new KeyCommandListener() {
			@Override
			public void keyPressed(int commandCode, long time) {
			}

			@Override
			public void keyReleased(int commandCode, long time) {
				switch (commandCode) {
				case Global.ENTER:
					sceneController.changeScene(new Game_MainScene(sceneController));
				}
			}
		};
	}

	private BufferedImage getImage(int tmp) {
		ImageController irc = ImageController.getInstance();
		if (tmp == 0) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.HINT));
		}
		return null;
	}

	@Override
	public void sceneBegin() {
	}

	@Override
	public void sceneUpdate() {
	}

	@Override
	public void sceneEnd() {
	}

	@Override
	public void paint(Graphics g) {
//		g.drawImage(getImage(0), 0, 0, Global.WINDOWS_WIDTH, Global.WINDOWS_HEIGHT, null);
		g.drawImage(getImage(0), 0, 0, 985, 655+10, null);
	}

	@Override
	public KeyCommandListener getKeyCommandListener() {
		return keycommandListener;
	}

	@Override
	public KeyCommandListener getMouseCommandListener() {
		return null;
	}

}