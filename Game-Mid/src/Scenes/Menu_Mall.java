package Scenes;

import java.awt.Color;
import java.awt.Graphics;

import Controllers.SceneController;
import IO.CommandSolver.KeyCommandListener;
import Utils.Global;

public class Menu_Mall extends Scene {
	private String title = "商城";
	private String back = "按enter回主選單";
	private KeyCommandListener commandListener;

	public Menu_Mall(SceneController sceneController) {
		super(sceneController);
		commandListener = new KeyCommandListener() {
			@Override
			public void keyPressed(int commandCode, long time) {
				System.out.println("pressed at: " + time + " -> " + commandCode);
				switch (commandCode) {
				case Global.UP:
					break;
				case Global.LEFT:
					break;
				case Global.DOWN:
					break;
				case Global.RIGHT:
					break;
				case Global.SPACE:					
					break;
				case Global.ENTER:
					sceneController.changeScene(sceneController.getMainMenu()[0]);
					break;
				}
			}

			@Override
			public void keyReleased(int CommandCode, long time) {
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
