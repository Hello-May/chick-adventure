package GameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Controllers.ImageController;
import Values.PathBuilder;
import Utils.Global;
import Values.ImagePath;

public class ActorHelper {

	private BufferedImage img;
	private int actorPosition;
	private int actor;
	private int px; // 圖片中各動作座標
	private int py;
	private int w2; // 給主角存寬高用
	private int h2;

	public ActorHelper(int actor) {
		this.actor = actor;
		img = getActor(actor);
		if (actor == 1 || actor == 2 || actor == 3) {
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
		// 戰鬥場景用
		if (actor >= 8 &&actor < 16) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.A2));
		}
		// 戰鬥場景用
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
		if (actor == Global.NIGHT_MONSTER) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Monster.BM));
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
		if (actor == Global.NIGHT_SOLDIER) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.People.P4));
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
		} else if (actor == Global.NIGHT_MONSTER) {
			px = 60 * 3 * (actorPosition % 4);
			py = 60 * 4 * (actorPosition / 4);
		} else {
			px = 96 * (actorPosition % 4); // 一個角色的圖片寬:32*3=96 (cx:1,2,3,0)
			py = 128 * (actorPosition / 4); // 一個角色的圖片長:32*4=128 (cy:0,1)
		}

		w2 = (actor == 0 || actor == 1 || actor == 2 ? Global.CHICK_SIZE_X : width);
		h2 = (actor == 0 || actor == 1 || actor == 2 ? Global.CHICK_SIZE_Y : height);

		g.drawImage(img, x, y, x + width, y + height, px + act * w2, py + direction * h2, px + w2 + act * w2,
				py + h2 + direction * h2, null);
		
//		int cx = 96 * (actorPosition % 4);
//        int cy = 128 * (actorPosition / 4);
//        g.drawImage(img, x, y, x + width, y + height,
//                cx + act * IMG_X_OFFSET, cy + direction * IMG_Y_OFFSET,
//                cx + 32 + act * IMG_X_OFFSET, cy + 32 + direction * IMG_Y_OFFSET, null);
    
	}

	public void paint(Graphics g, int hp, int mp) {

	}

}
