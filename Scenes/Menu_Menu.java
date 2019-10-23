package Scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Controllers.SceneController;
import IO.CommandSolver.KeyCommandListener;
import Utils.Global;

public class Menu_Menu extends Scene {
	private String gameName;
	private String[] menus;
	private int focusIndex;
	private KeyCommandListener commandListener;
	private boolean isReleased;

	public Menu_Menu(SceneController sceneController) {
		super(sceneController);
		gameName = "Chick Adventure";
		menus = new String[] { "新遊戲", "讀檔", "操作說明", "商城", "製作者/資源", "離開" };
		focusIndex = 0;
		isReleased = true;
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
					System.out.println(focusIndex + " , " + menus[focusIndex]);
					enterActor(focusIndex);
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
		focusIndex = (focusIndex + (upDown == Global.UP ? (menus.length - 1) : 1)) % menus.length;
	}

	private void enterActor(int focusIndex) {
		switch (focusIndex) {
		case 0:
			sceneController.changeScene(new Game_MainScene(sceneController));
			break;
		case 1:
			break;
		case 2:
			sceneController.changeScene(sceneController.getMainMenu()[1]);
			break;
		case 3:
			sceneController.changeScene(sceneController.getMainMenu()[2]);
			break;
		case 4:
			sceneController.changeScene(sceneController.getMainMenu()[3]);
			break;
		case 5:
			System.exit(1);
			break;
		}
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
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Global.WINDOWS_WIDTH, Global.WINDOWS_HEIGHT);
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Times New Roman", Font.BOLD, 24));
		g.drawString(gameName, 360, 150);
		g.setFont(new Font("細明體", Font.PLAIN, 12));
		for (int i = 0; i < menus.length; i++) {
			if (i == focusIndex) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawString(menus[i], 400, 250 + (i * 30));
		}
		g.setColor(Color.WHITE);
		g.drawString("按enter繼續", 400, 550);
	}

	@Override
	public KeyCommandListener getCommandListener() {
		return commandListener;
	}

}
