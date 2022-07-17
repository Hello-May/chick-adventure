package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Controllers.ImageController;
import Values.PathBuilder;
import Utils.Global;
import Values.ImagePath;

public class ActorHelper {
	static final long serialVersionUID = 1111150l;

	private BufferedImage img;
	private int actorPosition;
	private int actor;
	private int px; // 圖片中各動作座標
	private int py;
	private int w2; // 給主角存寬高用
	private int h2;
	private int paintSkillIndex;

	public ActorHelper(int actor) {
		this.actor = actor;
		paintSkillIndex = -1;
		img = getActor(actor);
		if (actor == 0 || actor == 1 || actor == 2) {
			actorPosition = 0;
		} else {
			actorPosition = actor % 8;
		}
	}

	private BufferedImage getActor(int actor) {
		ImageController irc = ImageController.getInstance();
		switch (actor) {
		case 0:
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Bird.Body.B1));
		case 1:
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Bird.Body.B2));
		case 2:
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Bird.Body.B3));
		}
		if (actor >= 8 && actor < 16) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Monster.WOLF));
		}
		if (actor == Global.MENU_FIRE) { // 17
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.FIRE));
		}
		if (actor >= 40 && actor < 48) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Animal.A1));
		}
		if (actor >= 48 && actor < 56) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Monster.M1));
		}
		if (actor >= 56 && actor < 62) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Monster.M2));
		}
		if (actor >= 62 && actor < 70) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Monster.M3));
		}
		if (actor == Global.MONSTER) { // 118
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Monster.BM));
		}
		if (actor >= 70 && actor < 74) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Damage.D1));
		}
		if (actor >= 80 && actor < 88) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.People.P1));
		}
		if (actor >= 88 && actor < 96) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.People.P2));
		}
		if (actor >= 96 && actor < 102) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.People.P3));
		}
		if (actor >= 102 && actor < 110) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.People.P5));
		}
		if (actor == Global.SOLDIER) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.People.P4));
		}
		if (actor == Global.VILLAGE_HEAD) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.People.P5));
		}
		if (actor >= 120 && actor < 128) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Crazy.C1));
		}
		if (actor >= 128 && actor < 136) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Crazy.C2));
		}
		if (actor == 901) {// 攻擊後的效果圖
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.SkillResult.ATTACK));
		}
		if (actor == 902) {// 火球後的效果圖
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.SkillResult.FIREBALL));
		}
		if (actor == 903) {// 詛咒後的效果圖
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.SkillResult.CURSE));
		}
		if (actor == 904) {// 召喚後的效果圖
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.SkillResult.SUMMON));
		}
		if (actor == 905) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Emoticons.NORMAL));
		}
		if (actor == 906) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Emoticons.HAPPY));
		}
		if (actor == 907) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Emoticons.CRY));
		}
		if (actor == 908) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Emoticons.ANGRY));
		}
		if (actor == 909) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Emoticons.CONFUSION));
		}
		if (actor == 910) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Emoticons.LIGHT));
		}
		if (actor == 911) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Emoticons.MUSIC));
		}
		if (actor == 912) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Emoticons.QUESTION));
		}
		if (actor == 913) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Emoticons.SURPRISED));
		}
		if (actor == 914) {// 暴擊的效果圖
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.SkillResult.CRITICAL));
		}
		if (actor == 915) {// 敵人的技能效果
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.SkillResult.ENEMY_SKILL));
		}

		return null;
	}

	public void paint(Graphics g, int x, int y, int width, int height, int act, int direction) {
		if (img == null) {
			return;
		}
		if (actor == 0 || actor == 1 || actor == 2) {
			px = 45 * 3 * (actorPosition % 4);
			py = 50 * 4 * (actorPosition / 4);

		} else if (actor == Global.MONSTER) {
			px = 60 * 3 * (actorPosition % 4);
			py = 60 * 4 * (actorPosition / 4);
		} else {
			px = 96 * (actorPosition % 4); // 一個角色的圖片寬:32*3=96 (cx:1,2,3,0)
			py = 128 * (actorPosition / 4); // 一個角色的圖片長:32*4=128 (cy:0,1)
		}

		w2 = (actor == 0 || actor == 1 || actor == 2 ? Global.CHICK_SIZE_X : Global.ACTOR_SIZE);
		h2 = (actor == 0 || actor == 1 || actor == 2 ? Global.CHICK_SIZE_Y : Global.ACTOR_SIZE);
		if (actor == Global.MONSTER) {
			w2 = 60;
			h2 = 60;
		}

		if (actor == 14) { // 戰鬥場景的狼
			w2 = h2 = 32;
		}

		g.drawImage(img, x, y, x + width, y + height, px + act * w2, py + direction * h2, px + w2 + act * w2,
				py + h2 + direction * h2, null);

	}

	public void paint(Graphics g, int x, int y, int width, int height, int act, int direction, int num) {// 畫心情
		int cx = 384 * (actorPosition % 9);
		g.drawImage(getActor(num), 750, 410, 750 + 40, 410 + 40, cx + act * 48, direction * 48, cx + 48 + act * 48,
				48 + direction * 48, null);
	}

	public void paintMood(Graphics g, int x, int y, int width, int height, int act, int direction, int num) {// 畫心情
		g.drawImage(getActor(num), x, y, x + width, y + height, act * 48, direction * 48, 48 + act * 48,
				48 + direction * 48, null);
	}
	
	public void paintVillageHeadSkill(Graphics g, int x, int y, int width, int height, int act, int direction) {
		g.drawImage(getActor(915), x, y, x + width, y + height, act * 192, direction * 192, 192 + act * 192,
				192 + direction * 192, null);
	}

	public void paintSkill(Graphics g, int x, int y, int width, int height, int act, int direction) {
		if (paintSkillIndex == 0) {
			g.drawImage(getActor(901), 200, 430, 200 + 96, 430 + 96, act * 192, direction * 192, 192 + act * 192,
					192 + direction * 192, null);
		}

		if (paintSkillIndex == 1) {
			g.drawImage(getActor(902), 195, 435, 195 + 192, 435 + 192, act * 192, direction * 192, 192 + act * 192,
					192 + direction * 192, null);
		}
		if (paintSkillIndex == 2) {
			g.drawImage(getActor(903), 140, 420, 140 + 192, 420 + 192, act * 192, direction * 192, 192 + act * 192,
					192 + direction * 192, null);
		}

		if (paintSkillIndex == 3) {
			g.drawImage(getActor(904), 140, 420, 140 + 192, 420 + 192, act * 192, direction * 192, 192 + act * 192,
					192 + direction * 192, null);
		}
		if (paintSkillIndex == 4) {
			g.drawImage(getActor(901), 720, 430, 720 + 96, 430 + 96, act * 192, direction * 192, 192 + act * 192,
					192 + direction * 192, null);
		}
		if (paintSkillIndex == 5) {// 畫敵人的技能
			g.drawImage(getActor(914), 720, 430, 720 + 132, 430 + 68, act * 192, direction * 192, 192 + act * 192,
					192 + direction * 192, null);
		}
		if (paintSkillIndex == 6) {// 畫敵人的技能
			g.drawImage(getActor(915), 720, 430, 720 + 96, 430 + 96, act * 192, direction * 192, 192 + act * 192,
					192 + direction * 192, null);
		}
	}

	public void setPaintSkillIndex(int skillIndex) {
		this.paintSkillIndex = skillIndex;
	}
}