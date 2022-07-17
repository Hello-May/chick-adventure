package Scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Controllers.ImageController;
import Controllers.MusicController;
import Controllers.SceneController;
import Controllers.StoryController;
import IO.CommandSolver.KeyCommandListener;
import Utils.Global;
import Utils.Messages;
import Utils.Messages.MsgFlow;
import Values.ImagePath;
import Values.MusicPath;
import Values.PathBuilder;
import popup.MsgPopUpWindow;
import popup.MsgPopUpWindow.Action;

public class Game_End extends Scene {
	private KeyCommandListener keycommandListener;
	private MsgPopUpWindow msgPopUp;
	private static StoryController storyController = StoryController.getInstance();
	private MusicController bgm;
	private MusicController sound;
	private ImageController irc;
	private BufferedImage bg;
	private Color dark;

	public Game_End(SceneController sceneController) {
		super(sceneController);
		dark = new Color(0, 0, 0, 200);
		keycommandListener = new KeyCommandListener() {
			@Override
			public void keyPressed(int commandCode, long time) {
			}

			@Override
			public void keyReleased(int commandCode, long time) {
				switch (commandCode) {
				case Global.ENTER:
					callMsgPopUp();
				}
			}
		};

	}

	private void callMsgPopUp() {
		if (msgPopUp == null) {
			MsgFlow mf = Messages.quickGen(sceneController, actor, null);
			msgPopUp = new MsgPopUpWindow(mf, actor, new Action() {
				@Override
				public void action() {
					msgPopUp.setIsDark(false);
					msgPopUp = null;
				}
			});
			msgPopUp.setIsDark(true);
		}
	}

	@Override
	public void sceneBegin() {
		bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B10));
		bgm.play();
		irc = ImageController.getInstance();
		bg = irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.END));
	}

	@Override
	public void sceneUpdate() {
	}

	@Override
	public void sceneEnd() {
		msgPopUp = null;
		bgm.stop();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(bg, 0, 0, Global.WINDOWS_WIDTH, Global.WINDOWS_HEIGHT, 0, 0, 1706, 960, null);
		g.setColor(dark);
		g.fillRect(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);

		// ¦L¹ï¸Ü®Ø
		if (msgPopUp != null) {
			msgPopUp.paint(g);
		}

	}

	@Override
	public KeyCommandListener getKeyCommandListener() {
		return (msgPopUp == null ? keycommandListener : msgPopUp.getKeyCommandListener());
	}

	@Override
	public KeyCommandListener getMouseCommandListener() {
		return null;
	}

}
