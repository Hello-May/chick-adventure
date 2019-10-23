package Scenes;

import java.awt.Color;
import java.awt.Graphics;

import Controllers.SceneController;
import IO.CommandSolver.KeyCommandListener;
import Utils.Global;

public class Menu_Instructions extends Scene {
	private String title;
	private String[] body;
	private String back;
	private KeyCommandListener commandListener;

	public Menu_Instructions(SceneController sceneController) {
		super(sceneController);
		title = "�ާ@����";
		body = new String[] { "AWDS: ���ާ@/�H������", "Space��: �o�ʧޯ�/������", "Esc��: �C�����", "Enter��: �T�{/�~��" };
		back = "��enter�^�D���";
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
		for (int i = 0; i < body.length; i++) {
			g.drawString(body[i], 400, 250 + (i * 30));
		}
		g.drawString(back, 400, 550);
	}

	@Override
	public KeyCommandListener getCommandListener() {
		return commandListener;
	}

	@Override
	public KeyCommandListener getKeyCommandListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyCommandListener getMouseCommandListener() {
		// TODO Auto-generated method stub
		return null;
	}


}
