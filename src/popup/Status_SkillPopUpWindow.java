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
		g.drawString("�i���y�j", 420, 195 + 20 * 1);
		g.drawString("�Q���]�k�E�����y�����ĤH", 500, 200 + 20 * 2);
		g.drawString(
				"�]�ˮ`-" + chicken.getStatus().getAttack() * 2 + " , �k�O-" + chicken.getStatus().getAttack() * 1 + "�^",
				500, 200 + 20 * 3);
		g.drawString("�i�A�G�j", 420, 195 + 20 * 5);
		g.drawString("�I�i�G�N���z�ĤH", 500, 200 + 20 * 6);
		g.drawString(
				"�]�ˮ`-" + chicken.getStatus().getAttack() * 3 + " , �k�O-" + chicken.getStatus().getAttack() * 2 + "�^",
				500, 200 + 20 * 7);
//  g.drawString(
//    "�]�ˮ`-" + SkillList.Curse.getMakeDamge() + " , �k�O-" + SkillList.Curse.getMakeDamge()+ "�^",
//    500, 200 + 20 * 7);
		g.drawString("�i�l��j ", 420, 195 + 20 * 9);
		g.drawString("�l��������F�����ĤH", 500, 200 + 20 * 10);
		g.drawString(
				"�]�ˮ`-" + chicken.getStatus().getAttack() * 4 + " , �k�O-" + chicken.getStatus().getAttack() * 3 + "�^",
				500, 200 + 20 * 11);
		for (int i = 0; i < MAGE.length; i++) {
			g.drawImage(MAGE[i], 450, 230 + (MARGIN * i), 450 + 32, 230 + 32 + (MARGIN * i), 0, 0, 32, 32, null);
		}
	}

}