package Scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Controllers.ImageController;
import Controllers.MusicController;
import Controllers.SceneController;
import IO.CommandSolver.KeyCommandListener;
import Utils.Global;
import Values.ImagePath;
import Values.MusicPath;
import Values.PathBuilder;

public class Menu_Instructions extends Scene {
	private String[] body;
	private KeyCommandListener keycommandListener;
	private ImageController irc;
	private BufferedImage bg;
	private Color dark;
	private MusicController bgm;

	public Menu_Instructions(SceneController sceneController) {
		super(sceneController);
		irc = ImageController.getInstance();
		bg = irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.INSTRUCTIONS));
		dark = new Color(0, 0, 0, 200);
		body = new String[] { "｜AWDS｜選單操作/人物移動", "｜ENTER｜確認/繼續", "｜ESC｜遊戲內選單" };
		keycommandListener = new KeyCommandListener() {
			@Override
			public void keyPressed(int commandCode, long time) {
				switch (commandCode) {
				case Global.ESC:
					sceneController.changeScene(sceneController.getMenuScene());
					break;
				}
			}

			@Override
			public void keyReleased(int commandCode, long time) {
			}
		};
	}

	@Override
	public void sceneBegin() {
		bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B1));
		bgm.play();
	}

	@Override
	public void sceneUpdate() {
	}

	@Override
	public void sceneEnd() {
		bgm.stop();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(bg, 0, 0, Global.WINDOWS_WIDTH, Global.WINDOWS_HEIGHT, null);
		g.setColor(dark);
		g.fillRect(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(new Font("微軟正黑體", Font.PLAIN, 50));
		g.drawString("操作說明", 375, 160);
		g.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
		for (int i = 0; i < body.length; i++) {
			g.drawString(body[i], 345, 270 + (i * 50));
		}
		g.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		g.drawString("按esc回主選單", 430, 550);
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