package Scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Controllers.ImageController;
import Controllers.MusicController;
import Controllers.SceneController;
import Controllers.StoryController;
import GameObject.ActorHelper;
import IO.CommandSolver.KeyCommandListener;
import Utils.DelayCounter;
import Utils.Global;
import Values.ImagePath;
import Values.MusicPath;
import Values.PathBuilder;

public class Menu_Menu extends Scene {
	private static StoryController storyController = StoryController.getInstance();
	private String gameName;
	private String[] menus;
	private int focusIndex;
	private KeyCommandListener keycommandListener;
	private ImageController irc;
	private BufferedImage bg;
	private ActorHelper actorHelper;
	protected static final int[] ACT = { 0, 1, 2, 1 };
	private DelayCounter delay;
	private int act;
	private int y;
	private Color dark;
	private MusicController bgm;

	public Menu_Menu(SceneController sceneController) {
		super(sceneController);
		gameName = "CHICK  ADVENTURE";
		menus = new String[] { "新遊戲", "讀檔", "操作說明", "離開" };
		focusIndex = 0;
		y = 0;
		act = 0;
		dark = new Color(0, 0, 0, 200);
		actorHelper = new ActorHelper(Global.MENU_FIRE);
		delay = new DelayCounter(5);
		irc = ImageController.getInstance();
		bg = irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.MENU));
		keycommandListener = new KeyCommandListener() {
			@Override
			public void keyPressed(int commandCode, long time) {
				switch (commandCode) {
				case Global.UP:
				case Global.DOWN:
					changeFocusIndex(commandCode);
					break;
				case Global.ENTER:
//					System.out.println(focusIndex + " , " + menus[focusIndex]);
					enterActor(focusIndex);
					break;
				}
			}

			@Override
			public void keyReleased(int commandCode, long time) {
			}
		};
	}

	private void changeFocusIndex(int upDown) {
		focusIndex = (focusIndex + (upDown == Global.UP ? (menus.length - 1) : 1)) % menus.length;
	}

	private void enterActor(int focusIndex) {
		switch (focusIndex) {
		case 0:
			storyController.setSF1(0);
			sceneController.changeScene(sceneController.getBlackScene(),0,actor);
			break;
		case 1:
			sceneController.changeScene(new Menu_Load(sceneController));
			break;
		case 2:
			sceneController.changeScene(sceneController.getInstructionsScene());
			break;
		case 3:
			System.exit(1);
			break;
		}
	}

	@Override
	public void sceneBegin() {
		bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B1));
		bgm.play();
	}

	@Override
	public void sceneUpdate() {
		if (delay.update()) {
			act = ++act % 4;
		}
	}

	@Override
	public void sceneEnd() {
		bgm.stop();
	}

	@Override
	public void paint(Graphics g) {
//		g.drawImage(img,0,0,null);
//		g.drawImage(img,0,0,Global.WINDOWS_WIDTH,Global.WINDOWS_HEIGHT,null);
//		g.drawImage(img,0,0,Global.WINDOWS_WIDTH,Global.WINDOWS_HEIGHT,0,y,Global.WINDOWS_WIDTH,Global.WINDOWS_HEIGHT+y++,null);
//		if(y>=1600-Global.WINDOWS_HEIGHT) {
//			y=1600-Global.WINDOWS_HEIGHT;
//		}
		g.drawImage(bg, 0, 0, Global.WINDOWS_WIDTH, Global.WINDOWS_HEIGHT, 0, 1600 - Global.WINDOWS_HEIGHT + y,
				Global.WINDOWS_WIDTH, 1600 + y--, null);
		if (y <= -1600 + Global.WINDOWS_HEIGHT) {
			y = -1600 + Global.WINDOWS_HEIGHT;
		}
		g.setColor(dark);
		g.fillRect(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Times New Roman", Font.BOLD, 56));
		g.drawString(gameName, 200, 150);
		for (int i = 0; i < 2; i++) {
			g.drawLine(187, 90 + 80 * i, 780, 90 + 80 * i);
		}
		g.setFont(new Font("微軟正黑體", Font.BOLD, 24));
		for (int i = 0; i < menus.length; i++) {
			if (i == focusIndex) {
				g.setColor(Color.YELLOW);
				actorHelper.paint(g, 385, 245 + (i * 50), Global.ACTOR_SIZE, Global.ACTOR_SIZE, ACT[act], 0);
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawString(menus[i], 435, 270 + (i * 50));
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
		g.drawString("按enter繼續", 435, 550);
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
