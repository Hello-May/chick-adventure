package Scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Controllers.ImageController;
import Controllers.MusicController;
import Controllers.SceneController;
import Controllers.StoryController;
import IO.CommandSolver.KeyCommandListener;
import Utils.Global;
import Values.ImagePath;
import Values.MusicPath;
import Values.PathBuilder;

public class Game_Over extends Scene {
	private KeyCommandListener keycommandListener;
	private static StoryController storyController = StoryController.getInstance();
	private ImageController irc;
	private BufferedImage bg;
	private MusicController sound;

	public Game_Over(SceneController sceneController) {
		super(sceneController);
		sound = new MusicController(PathBuilder.getAudio(MusicPath.Sound.GAME_OVER));
		irc = ImageController.getInstance();
		bg = irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.GAME_OVER));
		keycommandListener = new KeyCommandListener() {
			@Override
			public void keyPressed(int commandCode, long trigTime) {
			}

			@Override
			public void keyReleased(int commandCode, long trigTime) {
				switch (commandCode) {
				case Global.ENTER:
					sceneController.changeScene(sceneController.getMenuScene());
					break;
				}
			}

		};
	}

	@Override
	public void sceneBegin() {
		sound.playOnce();
	}

	@Override
	public void sceneUpdate() {
	}

	@Override
	public void sceneEnd() {
		sound.stop();
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Global.WINDOWS_WIDTH, Global.WINDOWS_HEIGHT);
		g.drawImage(bg, 230, 50, 230 + 500, 50 + 500, 0, 0, 1000, 1000, null);
		g.setColor(Color.RED);
		g.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		g.drawString("雞：我怎麼掛了！？QQ", 422, 600);
		g.drawString("迷：不作死不會死……", 422, 620);
		g.drawString("按enter回到主畫面", 430, 640);
		if (Global.DEBUG) {
			g.setColor(Color.MAGENTA);
			g.drawString("劇情: " + storyController.getSF1(), 10, 60);
		}
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
