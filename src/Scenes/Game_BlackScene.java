package Scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Controllers.ImageController;
import Controllers.MusicController;
import Controllers.SceneController;
import Controllers.StoryController;
import GameObject.Actor;
import GameObject.ActorHelper;
import IO.CommandSolver.KeyCommandListener;
import Utils.DelayCounter;
import Utils.Global;
import Utils.Messages;
import Utils.Messages.MsgFlow;
import Values.ImagePath;
import Values.MusicPath;
import Values.PathBuilder;
import popup.MsgPopUpWindow;
import popup.MsgPopUpWindow.Action;

public class Game_BlackScene extends Scene {
	private KeyCommandListener keycommandListener;
	private MsgPopUpWindow msgPopUp;
	private static StoryController storyController = StoryController.getInstance();
	private MusicController bgm;
	private MusicController sound;
	private ImageController irc;
	private BufferedImage bg;
	private Color dark;
	private int x; // 各別場景圖的右下座標
	private int y;
	private Actor villageHead; // 以下村長變身使用
	private Actor monster;
	private ActorHelper ah;
	private int skillCountX;
	private int skillCountY;
	private DelayCounter delay;
	private int appear;
	private BufferedImage img;
	private int count;

	public Game_BlackScene(SceneController sceneController) {
		super(sceneController);
		dark = new Color(0, 0, 0, 100);
		ah = new ActorHelper(0);
		skillCountX = skillCountY = 0;
		delay = new DelayCounter(10);
		appear = 0;
		count = 0;
		irc = ImageController.getInstance();
		keycommandListener = new KeyCommandListener() {
			@Override
			public void keyPressed(int commandCode, long time) {
			}

			@Override
			public void keyReleased(int commandCode, long time) {
				switch (commandCode) {
				case Global.ENTER:
					switch (entrance) {
					case 0: // 主場景
					case Global.GROUND_OBSTACLE_EQUIPMENT:
					case Global.GROUND_OBSTACLE_MOUNTAIN:
					case Global.GROUND_OBSTACLE_CENTER:
					case Global.GROUND_OBSTACLE_STORE:
					case Global.GROUND_OBSTACLE_HOME:
					case Global.GROUND_OBSTACLE_PROP:
						callMsgPopUp();
						break;
					case Global.GROUND_OBSTACLE_CHURCH: // 450 250
						villageHead = new Actor(550, 200, Global.ACTOR_SIZE * 2, Global.ACTOR_SIZE * 2, 0,
								Global.VILLAGE_HEAD, // 村長
								Global.PASSERS_DELAY, null);
						villageHead.setFixedDirection(Global.DOWN);

						monster = new Actor(villageHead.getX() - 32, villageHead.getY() - 32, 60 * 2, 60 * 2,
								Global.ACTOR_SPEED, Global.MONSTER, Global.PASSERS_DELAY, null);
						monster.setFixedDirection(Global.DOWN);

						irc = ImageController.getInstance();
						img = irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Facial.MONSTER));

						if (appear >= 350) {
							callMsgPopUp();
						}
						break;
					default:
						sceneController.changeScene(sceneController.getMainScene());
					}

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
		switch (entrance) {
		case Global.GROUND_OBSTACLE_CHURCH:
			bg = irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.CHURCH));
			x = 605;
			y = 315;
			bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B3));
			bgm.play();
			break;
		case Global.GROUND_OBSTACLE_EQUIPMENT:
			bg = irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.EQUIPMENT));
			x = 750;
			y = 542;
			sound = new MusicController(PathBuilder.getAudio(MusicPath.Sound.HIT));
			sound.playOnce();
			break;
		case Global.GROUND_OBSTACLE_MOUNTAIN:
			bg = irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.MOUNTAIN));
			x = 750;
			y = 421;
			bgm = new MusicController(PathBuilder.getAudio(MusicPath.Sound.WIND));
			bgm.play();
			break;
		case Global.GROUND_OBSTACLE_CENTER:
			bg = irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.CENTER));
			x = 800;
			y = 400;
			bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B11));
			bgm.play();
			break;
		case Global.GROUND_OBSTACLE_STORE:
			bg = irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.STORE));
			x = 1500;
			y = 1040;
			bgm = new MusicController(PathBuilder.getAudio(MusicPath.Sound.NOISE));
			bgm.play();
			break;
		case Global.GROUND_OBSTACLE_HOME:
			bg = irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.HOME));
			x = 1024;
			y = 640;
			bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B5));
			bgm.play();
			break;
		case Global.GROUND_OBSTACLE_PROP:
			bg = irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.PROP));
			x = 735;
			y = 413;
			bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B5));
			bgm.play();
			break;
		}
		if (storyController.getSF1() == 0 && entrance == 0) {
			bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B4));
			bgm.play();
		}
		if (storyController.getSF1() == 10) {
			sound = new MusicController(PathBuilder.getAudio(MusicPath.Sound.BOON));
			sound.playOnce();
		}
		if (storyController.getSF1() == 11) {
			sound = new MusicController(PathBuilder.getAudio(MusicPath.Sound.BOON)); // 之後換格檔聲
			sound.playOnce();
		}

	}

	@Override
	public void sceneUpdate() {
		if (msgPopUp != null) {
			msgPopUp.windowUpdate();
		}
		if (villageHead != null) {
			villageHead.move();
			if (delay.update()) {
				count++;
				skillCountX = ++skillCountX % 5;// 0 1 2 3 4
				if (skillCountX == 4) {
					skillCountY = ++skillCountY % 5;// 1 2 3 4 0
				}
			}
		}
		if (img != null) {
			if (appear < 350 && count >= 10) {
				appear += 30;
			}
		}
		if (entrance == Global.GROUND_OBSTACLE_EQUIPMENT) {
			if (delay.update()) {
				sound.stop();
				sound.playOnce();
			}
		}
	}

	@Override
	public void sceneEnd() {
		bg = null;
		if (bgm != null) {
			bgm.stop();
		}
		entrance = -1;
		msgPopUp = null;

		if (villageHead != null) {
			villageHead = null;
		}
		
		if (monster != null) {
			monster= null;
		}
		
		if (img != null) {
			img= null;
		}

	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Global.WINDOWS_WIDTH, Global.WINDOWS_HEIGHT);

		if (bg != null) {
			g.drawImage(bg, (entrance == Global.GROUND_OBSTACLE_CHURCH ? -300 : 0), 0, Global.WINDOWS_WIDTH,
					Global.WINDOWS_HEIGHT, 0, 0, x, y, null);
		}

		if (msgPopUp != null) {
			g.setColor(dark);
			g.fillRect(0, 0, Global.WINDOWS_WIDTH, Global.WINDOWS_HEIGHT);
		}

		if (Global.DEBUG) {
			g.setColor(Color.WHITE);
			g.setColor(Color.MAGENTA);
			g.drawString("劇情: " + storyController.getSF1(), 10, 60);
		}

		if (villageHead != null) {
			villageHead.paint(g);
			ah.paintVillageHeadSkill(g, villageHead.getX() - 64, villageHead.getY() - 80, 96 * 2, 96 * 2, skillCountX,
					skillCountY);
		}

		if (img != null) {
			g.drawImage(img, 550 - 150 - appear - 100, 200 + 120 - appear, 550 + 220 + appear - 100, 200 + 120 + appear,
					0, 0, 355, 280, null);
			if (appear >= 100) {
				villageHead=null;
			}
		}

		// 印對話框
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
