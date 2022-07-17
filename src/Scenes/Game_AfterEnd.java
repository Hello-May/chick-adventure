package Scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Controllers.MusicController;
import Controllers.SceneController;
import IO.CommandSolver.KeyCommandListener;
import Utils.Global;
import Values.MusicPath;
import Values.PathBuilder;

public class Game_AfterEnd extends Scene {
	private KeyCommandListener keycommandListener;
	private MusicController bgm;
	private int y;

	public Game_AfterEnd(SceneController sceneController) {
		super(sceneController);
		y = 0;
		keycommandListener = new KeyCommandListener() {
			@Override
			public void keyPressed(int commandCode, long time) {
			}

			@Override
			public void keyReleased(int commandCode, long time) {
				switch (commandCode) {
				case Global.ENTER:
					if (y == -600) {
						sceneController.changeScene(sceneController.getMenuScene());
					}
				}
			}
		};
	}

	@Override
	public void sceneBegin() {
		bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B9));
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
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(new Font("�L�n������", Font.BOLD, 30));
		String[] strs = new String[] { "�P�±z����̫�I", "���U�i���������_�I�A", "�g�Ѥ@�s�ꪺ�ƥ��A", "�o���F���������K�A", "�æ��\�^���Ӫ��@�ɡI" };
		for (int i = 0; i < strs.length; i++) {
			g.drawString(strs[i], 300, Global.SCREEN_HEIGHT + 50 * i + y);
		}
		if (y > -600) {
			y--;
		}
		;
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
