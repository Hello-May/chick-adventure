package popup;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Controllers.ImageController;
import GameObject.Actor;
import Values.ImagePath;
import Values.PathBuilder;

public class Status_SkillPopUpWindow extends PopUpWindow {
	private Color window;
	private BufferedImage[] MAGE = { getMageSkill(0), getMageSkill(1), getMageSkill(2) };
	private static int MARGIN = 80;
	private Actor chicken;

	public Status_SkillPopUpWindow(int x, int y, int width, int height, Actor chicken) {
		super(x, y, width, height);
		window = new Color(0, 0, 0, 128);
		this.chicken = chicken;
	}

	@Override
	public void windowUpdate() {

	}

	private BufferedImage getMageSkill(int count) {
		ImageController irc = ImageController.getInstance();
		if (count == 0) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.Skill.FIREBALL));
		}
		if (count == 1) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.Skill.CURSE));
		}
		if (count == 2) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.Skill.CALL));
		}
		return null;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(window);
		g.fillRect(400, 190, 300, 250);
		g.setColor(Color.WHITE);
		g.drawString("【火球】", 420, 195 + 20 * 1);
		g.drawString("利用魔法聚集火球攻擊敵人", 500, 200 + 20 * 2);
		g.drawString(
				"（傷害-" + chicken.getStatus().getAttack() * 2 + " , 法力-" + chicken.getStatus().getAttack() * 1 + "）",
				500, 200 + 20 * 3);
		g.drawString("【詛咒】", 420, 195 + 20 * 5);
		g.drawString("施展咒術消弱敵人", 500, 200 + 20 * 6);
		g.drawString(
				"（傷害-" + chicken.getStatus().getAttack() * 3 + " , 法力-" + chicken.getStatus().getAttack() * 2 + "）",
				500, 200 + 20 * 7);
//  g.drawString(
//    "（傷害-" + SkillList.Curse.getMakeDamge() + " , 法力-" + SkillList.Curse.getMakeDamge()+ "）",
//    500, 200 + 20 * 7);
		g.drawString("【召喚】 ", 420, 195 + 20 * 9);
		g.drawString("召喚水之精靈攻擊敵人", 500, 200 + 20 * 10);
		g.drawString(
				"（傷害-" + chicken.getStatus().getAttack() * 4 + " , 法力-" + chicken.getStatus().getAttack() * 3 + "）",
				500, 200 + 20 * 11);
		for (int i = 0; i < MAGE.length; i++) {
			g.drawImage(MAGE[i], 450, 230 + (MARGIN * i), 450 + 32, 230 + 32 + (MARGIN * i), 0, 0, 32, 32, null);
		}
	}

}