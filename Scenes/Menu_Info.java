package Scenes;

import java.awt.Color;
import java.awt.Graphics;

import Controllers.SceneController;
import IO.CommandSolver.KeyCommandListener;
import Utils.Global;

public class Menu_Info extends Scene {
	private String title;
	private String back;
	private KeyCommandListener commandListener;
	private boolean isReleased;

	public Menu_Info(SceneController sceneController) {
		super(sceneController);
		title = "製作者/資源";
		back = "按enter回主選單";
		commandListener = new KeyCommandListener() {
			@Override
			public void keyPressed(int commandCode, long time) {
				System.out.println("pressed at: " + time + " -> " + commandCode);
				switch (commandCode) {
				case Global.ENTER:
					if (isReleased) {
						sceneController.changeScene(sceneController.getMainMenu()[0]);
						isReleased = false;
					}
					break;
				}
			}

			@Override
			public void keyReleased(int commandCode, long time) {
				switch (commandCode) {
				case Global.ENTER:
					isReleased = true;
					break;
				}
			}
		};
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
		g.setColor(Color.WHITE);
		g.drawString(title, 400, 180);
		g.drawString(back, 400, 550);
	}

	@Override
	public KeyCommandListener getCommandListener() {
		return commandListener;
	}

}
