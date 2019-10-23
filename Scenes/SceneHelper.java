package Scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Controllers.ImageController;
import Utils.DelayCounter;
import Utils.Global;
import Values.PathBuilder;
import Values.ImagePath;

public class SceneHelper {

	private BufferedImage img;
//	private int ground;
	private int x2; // �Ϥ��W�n�������W�y��
	private int y2;
	private int count; // ���r����
	private DelayCounter delayCounter;
	private int[] tmpX; // ������̰ʥ�
	private int[] tmpY;

	public SceneHelper() {
		x2 = 0;
		y2 = 0;
		count = 0;
		delayCounter = new DelayCounter(5);
		tmpX = new int[] { -1, 0, 1 };
		tmpY = new int[] { -1, 0, 1 };
	}

	private BufferedImage getScene(int print) {
		ImageController irc = ImageController.getInstance();
		switch (print) {
		case Global.GROUND_TURF:
			x2 = 0;
			y2 = 0;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Outside.O2));
		case Global.GROUND_TREE:
			x2 = 0;
			y2 = 448;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Outside.OB));
		case Global.GROUND_ROAD:
//			x2 = 0;	//�d��
//			y2 = 96;
//			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Outside.O2));
			x2 = 128; // ���Y��
			y2 = 32 * 9;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Outside.OB));
		case Global.GROUND_WATER:
			x2 = 0;
			y2 = 32;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Outside.O1));
		case Global.GROUND_LOTUS:
			x2 = 448;
			y2 = 224;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Outside.OB));
		case Global.GROUND_TURF_WALL:
			x2 = 0;
			y2 = 416;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Outside.O4));
		case Global.GROUND_WATERFALL1:
			x2 = 64 * 3;
			y2 = 192 + 32 * count;
			waitDelay();
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Outside.O1));
		case Global.GROUND_WATERFALL2:
			x2 = 64 * 3;
			y2 = 288 + 32 * count;
			waitDelay();
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Outside.O1));
		case Global.GROUND_TURF_LADDER:
			x2 = 0;
			y2 = 32 * 3;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Outside.O5));
		case Global.GROUND_WALL_VINE:
			x2 = 64 * 4;
			y2 = 32 * 3;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Dungeon.DB));
		case Global.GROUND_WATERPOOL:
			x2 = 64 * count + 12;
			y2 = 32 + 12;
			waitDelay();
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.World.W1));
		case Global.GROUND_LEAF:
			x2 = 192;
			y2 = 0;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Outside.O1));
		case Global.GROUND_WATERPOOL_UNDER:
			x2 = 64 * 4;	//�Ħ�(��5)
			y2 = 64 * 6+32;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Dungeon.D4));
//			x2 = 64 * 4;	//�Ǧ�(��7)
//			y2 = 64 * 5;
//			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.I1));
		case Global.GROUND_OBSTACLE:
		case Global.GROUND_OBSTACLE_CHURCH:
		case Global.GROUND_OBSTACLE_EQUIPMENT:
		case Global.GROUND_OBSTACLE_PROP:
		case Global.GROUND_OBSTACLE_STORE:
		case Global.GROUND_OBSTACLE_CENTER:
		case Global.GROUND_OBSTACLE_HOME:
			x2 = 64 * 6;
			y2 = 64 * 3;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.I2));
		case Global.GROUND_SKYTREE:
			x2 = 64;
			y2 = 64 * 43;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Other.XP));
		case Global.GROUND_TENT:
			x2 = 64 + 32;
			y2 = 64 * 70 - 32;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Other.XP));
		case Global.GROUND_SHELF:
			x2 = 64 * 3;
			y2 = 64 * 2;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Other.P1));
		case Global.GROUND_BRIDGE:
			x2 = 64;
			y2 = 64 * 3;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.I4));
		case Global.GROUND_HOUSE:
			x2 = 0;
			y2 = 64 * 102+32;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Other.XP));
		case Global.GROUND_DOOR:
			x2 = 64*7;
			y2 = 64 *4;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.IB));
		case Global.GROUND_WINDOW:
			x2 = 64*4;
			y2 = 64 *2;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Dungeon.DC));
		case Global.GROUND_FLOOR:
			x2 = 0;
			y2 = 32;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Outside.O4));
		case Global.GROUND_FLOOR_SHADOW:
			x2 = 0;
			y2 = 96;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Outside.O4));
		case Global.GROUND_CHAIR:
			x2 = 0;
			y2 = 64*6-10;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Other.P1));
		case Global.GROUND_STATUE1:
			x2 = 64*5+32;
			y2 = 64*5;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.IC));
		case Global.GROUND_FRUIT:
			x2 = 64*4;
			y2 = 64*6+32;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.IB));
		case Global.GROUND_GOODS:
			x2 = 64*5;
			y2 = 64*7;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.IB));
		case Global.GROUND_BOXES:
			x2 = 64*5;
			y2 = 64*6;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.IB));
		case Global.GROUND_STATUE2:
			x2 = 64*5-32;
			y2 = 64*2+32;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.IC));
		case Global.GROUND_EQUIPMENT1:
			x2 = 64*4;
			y2 = 64+32;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.IC));
		case Global.GROUND_EQUIPMENT2:
			x2 = 64*5;
			y2 = 64+32;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.IC));
		case Global.GROUND_EQUIPMENT3:
			x2 = 64*6;
			y2 = 64+32;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.IC));
		case Global.GROUND_BONE:
			x2 = 64*4;
			y2 = 64*7;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Dungeon.DB));
		case Global.GROUND_PROP1:
			x2 = 64;
			y2 = 64*5+32;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.IC));
		case Global.GROUND_PROP2:
			x2 = 64*2+32;
			y2 = 64*5+32;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.IC));
		case Global.GROUND_PROP3:
			x2 = 64;
			y2 = 64*4+32;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.IC));
		case Global.GROUND_PROP4:
			x2 = 64*3;
			y2 = 64*6;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.IC));
		case Global.GROUND_GROSS:
			x2 = 64*4;
			y2 = 64*2+32;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.IC));
		case Global.GROUND_GRAVE:
			x2 = 64*4;
			y2 = 64*5;
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Tileset.Inside.IC));
		}
		return null;
	}

	private void waitDelay() {
		if (delayCounter.update()) {
			count++;
			if (count == 2) {
				count = 0;
			}
		}
	}

	public void paintGround(Graphics g, int[][] groundLocation, int cx, int cy) {
		// �Q��a�B�a�O
		int x = 0;
		int y = 0;
		for (int i = 0; i < groundLocation.length; i++) { // �����Ʀr�ѷ�Global.GROUND
			for (int j = 0; j < groundLocation[0].length; j++) {
				img = getScene(groundLocation[i][j]);
				switch (groundLocation[i][j]) {
				case Global.GROUND_TURF:
				case Global.GROUND_FLOOR:
				case Global.GROUND_FLOOR_SHADOW:
					g.drawImage(img, x - cx, y - cy, x + Global.GROUND_SIZE - cx, y + Global.GROUND_SIZE - cy, x2, y2,
							x2 + Global.GROUND_SIZE, y2 + Global.GROUND_SIZE, null);
					break;
				}
				x += Global.GROUND_SIZE;
			}
			x = 0;
			y += Global.GROUND_SIZE;
		}
		// �л\��a����:���U�����Y
		x = 0;
		y = 0;
		for (int i = 0; i < groundLocation.length; i++) { // �����Ʀr�ѷ�Global.GROUND
			for (int j = 0; j < groundLocation[0].length; j++) {
				img = getScene(groundLocation[i][j]);
				switch (groundLocation[i][j]) {
				case Global.GROUND_WATERPOOL_UNDER: 
					g.drawImage(img, x - 3 - cx, y - 3 - cy, x + Global.GROUND_SIZE + 3 - cx,
							y + Global.GROUND_SIZE + 3 - cy, x2, y2, x2 + Global.GROUND_SIZE, y2 + Global.GROUND_SIZE,
							null);
					break;
//				case Global.GROUND_WATERPOOL:
//					paintObject(g, x - cx, y - cy, Global.GROUND_SIZE, Global.GROUND_SIZE, Global.GROUND_WATERPOOL,
//							false);
//					break;
				}
				x += Global.GROUND_SIZE;
			}
			x = 0;
			y += Global.GROUND_SIZE;
		}	
		// �л\���Y����:��
				x = 0;
				y = 0;
				for (int i = 0; i < groundLocation.length; i++) { // �����Ʀr�ѷ�Global.GROUND
					for (int j = 0; j < groundLocation[0].length; j++) {
						img = getScene(groundLocation[i][j]);
						switch (groundLocation[i][j]) {
						case Global.GROUND_WATERPOOL_UNDER:
						case Global.GROUND_BRIDGE:
							paintObject(g, x - cx, y - cy, Global.GROUND_SIZE, Global.GROUND_SIZE, groundLocation[i][j], false);
							break;
						}
						x += Global.GROUND_SIZE;
					}
					x = 0;
					y += Global.GROUND_SIZE;
				}
	}

	public void paintObject(Graphics g, int x, int y, int width, int height, int picture, boolean decoration) {
		img = getScene(picture);
		if (img == null) {
			return;
		}
		img = getScene(picture);
		// �S��L�k
		switch (picture) {
		case Global.GROUND_ROAD: // ���Y���n�Q�b��l����
			g.drawImage(img, x + (width / 4), y + (width / 4), x + width - (width / 4), y + height - (width / 4), x2,
					y2, x2 + (width / 2), y2 + (height / 2), null);
			break;
		case Global.GROUND_TURF_LADDER: // �p�G�O�󪺶���A���n�q32*32��j��64*64
			for (int i = 0; i < 2; i++) { // �Ĥ@�檽�L
				g.drawImage(img, x, y + (height / 2) * i, x + (width / 2), y + (height / 2) + (height / 2) * i, x2, y2,
						x2 + (width / 2), y2 + (height / 2), null);
			}
			for (int i = 0; i < 2; i++) { // �ĤG�檽�L
				g.drawImage(img, x + (width / 2), y + (height / 2) * i, x + (width / 2) + (width / 2),
						y + (height / 2) + (height / 2) * i, x2 + width, y2, x2 + (width / 2) + width,
						y2 + (height / 2), null);
			}
			break;
		case Global.GROUND_WATERPOOL: // �L����
			g.drawImage(img, x, y, x + width, y + height, x2, y2, x2 + width - 24, y2 + height - 24, null);
			break;
		case Global.GROUND_OBSTACLE: // �p�G�O��ê��(�Φb�л\���U�������I���P�_)
			g.drawImage(img, x + (width / 4), y + (width / 4), x + width - (width / 4), y + height - (width / 4), x2,
					y2, x2 + (width / 2), y2 + (height / 2), null);
			break;
		case Global.GROUND_SKYTREE: // �Ѫž���3:2�令3:3
			g.drawImage(img, x, y, x + width, y + height, x2, y2, x2 + width, y2 + height - 32, null);
			break;
		case Global.GROUND_TENT: // �b�O���2:2�令3:3
			g.drawImage(img, x, y, x + width, y + height, x2, y2, x2 + width - 32, y2 + height - 32, null);
			break;
		case Global.GROUND_SHELF: // �վ��ý��[�l���
			g.drawImage(img, x, y, x + width, y + height, x2, y2, x2 + width - 32, y2 + height, null);
			break;
		case Global.GROUND_HOUSE: // �վ�Фl���
			g.drawImage(img, x, y, x + width, y + height, x2, y2, x2 + width -96, y2 + height, null);
			break;
		case Global.GROUND_CHAIR: // �վ�Ȥl���
			g.drawImage(img, x, y, x + width, y + height, x2, y2, x2 + width +32, y2 + height, null);
			break;
		case Global.GROUND_GROSS: // �վ�Q�r�[���
			g.drawImage(img, x, y, x + width, y + height, x2, y2, x2 + width -32, y2 + height, null);
			break;
		default: // ���`�L�k
			g.drawImage(img, x, y, x + width, y + height, x2, y2, x2 + width, y2 + height, null);
		}

		// �������~�W�A�[�W�F��
		if (picture == Global.GROUND_TURF_WALL && decoration) { // �p�G�O����A�����v�L�X�ý�
			img = getScene(Global.GROUND_WALL_VINE);
			g.drawImage(img, x + (width / 4), y, x + width - (width / 4), y + height, x2, y2, x2 + (width / 2),
					y2 + height, null);
		} else if (picture == Global.GROUND_WATER || (picture == Global.GROUND_WATERPOOL && decoration)) { // �p�G�O�����A�n�L����b�W��
			img = getScene((picture == Global.GROUND_WATER ? Global.GROUND_LOTUS : Global.GROUND_LEAF)); // �p�G�O����W�A�����v������
			if (delayCounter.update()) { // �̰�
				count++;
				if (count == 2) {
					count = 0;
				}
				tmpX = new int[] { (int) (Math.random() * 3) - 1, (int) (Math.random() * 3) - 1,
						(int) (Math.random() * 3) - 1 };
				tmpY = new int[] { (int) (Math.random() * 3) - 1, (int) (Math.random() * 3) - 1,
						(int) (Math.random() * 3) - 1 };
			}
			g.drawImage(img, x + (width / 4) + tmpX[count], y + (width / 4) + tmpY[count],
					x + width - (width / 4) + tmpX[count], y + height - (width / 4) + tmpY[count], x2, y2,
					x2 + (width / 2), y2 + (height / 2), null);
		}else if(picture == Global.GROUND_HOUSE && decoration) {	//�Фl�[�W����
			img = getScene(Global.GROUND_DOOR);
			g.drawImage(img, x + (width / 2-32), y+(height-64), x + (width / 2+32), y + height, x2, y2, x2 + 32,
					y2 +32, null);
			img = getScene(Global.GROUND_WINDOW);
			for(int i=0;i<2;i++) {
			g.drawImage(img, x + (60+96*i), y+(height-68*2), x + (68+32+96*i), y + (height-60), x2, y2, x2 + 32,
					y2 +64, null);
			}
		}

	}

}
