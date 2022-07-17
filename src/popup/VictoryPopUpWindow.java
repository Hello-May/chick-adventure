package popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Controllers.ImageController;
import GameObject.Actor;
import GameObject.BattleActor;
import IO.CommandSolver;
import IO.CommandSolver.KeyCommandListener;
import Utils.DelayCounter;
import Utils.Global;
import Values.ImagePath;
import Values.PathBuilder;
import fighting.SkillList;
import fighting.Status;

public class VictoryPopUpWindow extends PopUpWindow {
	private Status status;
	private BattleActor chicken;
	private Color window;
	private KeyCommandListener keyCommandListener;

	public VictoryPopUpWindow(int x, int y, int width, int height, BattleActor chicken) {
		super(x, y, width, height);
		this.chicken = chicken;
		window = new Color(0, 0, 0, 128);
		keyCommandListener = new KeyCommandListener() {
			public void keyPressed(int commandCode, long time) {
			}

			@Override
			public void keyReleased(int commandCode, long time) {
				switch (commandCode) {
				case Global.LEFT:
					break;
				case Global.RIGHT:
					break;
				case Global.ENTER:
					break;
				case Global.ESC:
					break;
				}
			}
		};
	}

	@Override
	public void windowUpdate() {
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(window);
		g.fillRoundRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), 50, 50);
//  g.setColor(Color.YELLOW);
//  g.drawRect(super.getX() + 5, super.getY() + 5, super.getWidth() - 10, super.getHeight() - 10);
		g.setColor(Color.WHITE);
		g.drawString("�i��q�j" + chicken.getStatus().getTotalHP() + " + " + chicken.getStatus().getLevel() * 10,
				super.getX() + 100, super.getY() + 20 * 4);
		g.drawString("�i�k�O�j" + chicken.getStatus().getTotalMP() + " + " + chicken.getStatus().getLevel() * 10,
				super.getX() + 100, super.getY() + 20 * 6);
		g.drawString("�i�����j" + chicken.getStatus().getAttack() + " + 5", super.getX() + 100, super.getY() + 20 * 8);

		g.drawString("�i���y�j " + "�ˮ`��X  " + chicken.getStatus().getAttack() * 2 + "  +  10", super.getX() + 250,
				super.getY() + 20 * 4);
		g.drawString("�k�O����  " + chicken.getStatus().getAttack() + "  +  5", super.getX() + 304, super.getY() + 20 * 5);
		g.drawString("�i�A�G�j " + "�ˮ`��X  " + chicken.getStatus().getAttack() * 3 + "  +  15", super.getX() + 250,
				super.getY() + 20 * 6);
		g.drawString("�k�O����  " + chicken.getStatus().getAttack() * 2 + "  +  10", super.getX() + 304,
				super.getY() + 20 * 7);
		g.drawString("�i�l��j " + "�ˮ`��X  " + chicken.getStatus().getAttack() * 4 + "  +  20", super.getX() + 250,
				super.getY() + 20 * 8);
		g.drawString("�k�O����  " + chicken.getStatus().getAttack() * 3 + "  +  15", super.getX() + 304,
				super.getY() + 20 * 9);

		g.setFont(new Font("�L�n������", Font.BOLD, 20));
		g.drawString(
				"<< ���ߡI���Ŵ��� " + chicken.getStatus().getLevel() + " �� " + (chicken.getStatus().getLevel() + 1) + " >>",
				370, super.getY() + 20 * 2);
		g.setFont(new Font("�L�n������", Font.BOLD, 14));
		g.drawString("��esc���^����", 450, 430);

	}

	@Override
	public CommandSolver.KeyCommandListener getKeyCommandListener() {
		return keyCommandListener;
	}
}