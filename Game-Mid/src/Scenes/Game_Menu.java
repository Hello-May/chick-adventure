package Scenes;

import java.awt.Color;
import java.awt.Graphics;

import Controllers.SceneController;
import GameObject.Actor;
import IO.CommandSolver.KeyCommandListener;
import Utils.Global;

public class Game_Menu extends Scene {
	private String[] options;
	private int focusIndex;
	private int options_x;
	private int[] options_ys;
	private KeyCommandListener commandListener;
	private int tmpX;
	private int tmpY;
	private int tmpD;
	private int tmpW;
	private int tmpH;
	private int count;
	private boolean isReleased;

	public Game_Menu(SceneController sceneController) {
		super(sceneController);
		options = new String[] { "返回村莊", "角色資訊", "角色技能", "裝備/道具", "存檔/讀檔", "離開遊戲" };
		focusIndex = 0;
		options_x = 70;
		options_ys = new int[] { 80, 180, 280, 380, 480, 580 };
		commandListener = new KeyCommandListener() {
			@Override
			public void keyPressed(int commandCode, long time) {
				System.out.println("pressed at: " + time + " -> " + commandCode);
				switch (commandCode) {
				case Global.UP:
				case Global.DOWN:
					if (isReleased) {
						changeFocusIndex(commandCode);
						isReleased = false;
					}
					break;
				case Global.ENTER:
					System.out.println(focusIndex + " , " + options[focusIndex]);
					spaceActor(focusIndex);
					break;
				case Global.SPACE:
					count++;
					if (count == 3) {
						count = 0;
					}
					actor = new Actor(actor.getX(), actor.getY(), Global.CHICK_SIZE_X, Global.CHICK_SIZE_Y,
							Global.ACTOR_SPEED, count, Global.CHICK_DELAY);
					break;
				}
			}

			@Override
			public void keyReleased(int commandCode, long time) {
				switch (commandCode) {
				case Global.UP:
				case Global.DOWN:
					isReleased = true;
					break;
				}
			}
		};
	}

	private void changeFocusIndex(int upDown) {
		focusIndex = (focusIndex + (upDown == Global.UP ? (options.length - 1) : 1)) % options.length;
	}

	private void spaceActor(int focusIndex) {
		switch (focusIndex) {
		case 0:
			sceneController.changeScene(sceneController.getMainScene());
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			sceneController.changeScene(sceneController.getMainMenu()[0]);
			break;
		}
	}

	@Override
	public void sceneBegin() {
		focusIndex = 0;
		tmpX = actor.getX();
		tmpY = actor.getY();
		tmpD = actor.getDirection();
		tmpW = actor.getWidth();
		tmpH = actor.getHeight();
	}

	@Override
	public void sceneUpdate() {
		actor.move();
	}

	@Override
	public void sceneEnd() {
		sceneController.getMainScene().setActor(actor);
		actor.setX(tmpX);
		actor.setY(tmpY);
		actor.changeDirection(tmpD);
		actor.setWidth(tmpW);
		actor.setHeight(tmpH);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, Global.WINDOWS_WIDTH, Global.WINDOWS_HEIGHT);
		for (int i = 0; i < options.length; i++) {
			int x = options_x;
			int y = options_ys[i];
			if (i == focusIndex) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], x, y);
		}
//		g.setColor(new Color(0x9391d6));
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0 + 200, 0, Global.WINDOWS_WIDTH - 200, Global.WINDOWS_HEIGHT);
		g.setColor(Color.BLACK);
		optionField(focusIndex, g);

	}

	private void optionField(int focusIndex, Graphics g) {
		String str;
		switch (focusIndex) {
		case 0:
			g.drawString("按enter返回村莊", 550, 550);
			int x = 425;
			int y = 125;
			int d = 64;
			int countY = 0;
			int countX = 0;
			g.setColor(Color.BLACK);
			do {
				do {
					g.drawLine(x, 125, x, 125 + d * 5);
					if (y != 125 && x != 425 + d * 5) {
						str = (countX++) + "," + countY;
						g.drawString(str, x, y);
					}
					x += d;
				} while (x < 425 + d * 6);
				x = 425;
				countX = 0;
				g.drawLine(x, y, x + d * 5, y);
				countY += (y == 125 ? 0 : 1);
				y += d;
			} while (y < 125 + d * 6);
			g.setColor(Color.BLUE);
			g.fillOval(425 + d * 2 + 32 - 10, 125 + d * 2 + 32 - 10, 20, 20);
			break;
		case 1:
			g.drawString("名字: " + actor.getName(), 250, 50 * 1);
			g.drawString("HP: " + actor.getHp(), 250, 50 * 2);
			g.drawString("MP: " + actor.getMp(), 250, 50 * 3);
			g.drawString("ATK: " + actor.getAtk(), 250, 50 * 4);
			g.drawString("DEF: " + actor.getDef(), 250, 50 * 5);
			g.drawString("狀態: " + actor.getStateName(), 250, 50 * 6);
			g.drawString("等級: " + actor.getLevel(), 250, 50 * 7);
			g.drawString("職業: 還沒設定", 250, 50 * 8);
			g.drawString("經驗值: " + actor.getExp(), 250, 50 * 9);
			actor.changeDirection(Global.DOWN);
			actor.setX(550);
			actor.setY(150);
			actor.setWidth(Global.CHICK_SIZE_X * 3);
			actor.setHeight(Global.CHICK_SIZE_Y * 3);
			actor.paint(g);
			g.drawString("按space換角色造型", 550, 300);
			break;
		case 2:
			str = "";
			for (int i = 0; i < actor.getSkills().length; i++) {
				str += actor.getSkills()[i].getName() + " ,";
			}
			g.drawString("技能: " + str, 250, 50);
			break;
		case 3:
			g.drawString("你手無寸鐵又身無分文", 550, 300);
			break;
		case 4:
			g.drawString("還無法存檔QQ", 550, 300);
			break;
		case 5:
			g.drawString("按enter回到主選單", 550, 300);
			break;
		}
	}

	@Override
	public KeyCommandListener getCommandListener() {
		return commandListener;
	}

}
