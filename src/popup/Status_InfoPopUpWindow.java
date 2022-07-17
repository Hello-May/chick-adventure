package popup;

import java.awt.Color;
import java.awt.Graphics;

import GameObject.Actor;
import Utils.Global;
import fighting.Status;

public class Status_InfoPopUpWindow extends PopUpWindow {
	private Color window;
	private Status status;
	private Actor actor;
	private Actor mainCharacter;

	public Status_InfoPopUpWindow(int x, int y, int width, int height, Actor mainCharacter) {
		super(x, y, width, height);
		window = new Color(0, 0, 0, 128);
		actor = new Actor(535, 250, Global.CHICK_SIZE_X * 3, Global.CHICK_SIZE_Y * 3, 0, 0, Global.CHICK_DELAY, null);
		this.mainCharacter = mainCharacter;
	}

	@Override
	public void windowUpdate() {
		actor.move();
	}

	public void paint(Graphics g) {
		g.setColor(window);
		g.fillRect(400, 190, 300, 250);
		status = mainCharacter.getStatus();
		g.setColor(Color.WHITE);
		g.drawString("名字: " + mainCharacter.getName(), 430, 200 + 30 * 1);
		g.drawString("HP: " + status.getCurrentHP() + "/" + status.getTotalHP(), 430, 200 + 30 * 2);
		g.drawString("MP: " + status.getCurrentMP() + "/" + status.getTotalMP(), 430, 200 + 30 * 3);
		g.drawString("ATK: " + status.getAttack(), 430, 200 + 30 * 4);
		g.drawString("等級: " + status.getLevel(), 430, 200 +30 * 5);
		g.drawString("道具: " + (mainCharacter.getHasBoon() == 0 ? "無" : "骨頭" + mainCharacter.getHasBoon() + "個"), 430,
				200 + 30 * 6);
		g.drawString("裝備: " + (mainCharacter.getHasPotLid() ? "鍋蓋" : "無")+ (mainCharacter.getHasCross() ? "、十字架" : ""), 430, 200 + 30 * 7);
		this.actor.paint(g);
	}

}