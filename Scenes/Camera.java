package Scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Controllers.ImageController;
import GameObject.Actor;
import GameObject.SceneObject;
import IO.CommandSolver.KeyCommandListener;
import Utils.DelayCounter;
import Utils.Global;
import Values.ImagePath;
import Values.PathBuilder;

public class Camera {

	private SceneHelper sceneHelper;
	private Actor actor;
	private ArrayList<Actor> others; // ���H+�ĤH
	private int x; // camera���W�e�@�ӧ���
	private int y;
	private int mapXCount; // ���Y��ɧP�_��
	private int mapYCount;
	private String str;
	private ArrayList<SceneObject> sceneObjects;
	private int[][] groundLocation;
//	private ArrayList<SceneObject> groundObjects;
	private ArrayList<SceneObject> coverObjects;
	private Color blackWindows; // �L��ܮ�
	private Color whiteString;
	private String dialogue;
	private boolean yesNo;
	private int focusIndex;
	private DelayCounter delayCounter; // �Q�p���ܦ^�_���ɶ�
	private int x2; // ���սu�Ϊ��F��
	private int y2;
	private boolean isNight; // �Ѷ«�ϥ�
	private Color night;
//	private CollisionHelper collisionHelper;
	private KeyCommandListener commandListener;

	public Camera(Actor actor, int x, int y, int mapXCount, int mapYCount) {
		sceneHelper = new SceneHelper();
		this.actor = actor;
		this.x = x;
		this.y = y;
		this.mapXCount = mapXCount;
		this.mapYCount = mapYCount;
		isNight = false;
		blackWindows = new Color(0, 0, 0, 128);
		night = new Color(0, 0, 0, 96);
		whiteString = new Color(255, 255, 255, 180);
		dialogue = "�}�o�̻��ڬO���A�ڴN�O�����I";
		delayCounter = new DelayCounter(Global.DIALOGUE_DELAY);
		update(this.actor);
	}

	public void dayDark() {
		isNight = true;
	}

	public void dayBreak() {
		isNight = false;
	}

	public void setObjectsAndOthers(int[][] groundLocation, ArrayList<SceneObject> sceneObjects,
			ArrayList<SceneObject> coverObjects, ArrayList<Actor> others) {
//		this.groundObjects=groundObjects;
		this.groundLocation = groundLocation;
		this.sceneObjects = sceneObjects;
		this.coverObjects = coverObjects;
		this.others = others;
//		collisionHelper = new CollisionHelper(sceneObjects,coverObjects,others);
	}

	private void setX(int x) {
		if (x < 0) {
			this.x = 0;
		} else if (x > Global.GROUND_SIZE * mapXCount - Global.WINDOWS_WIDTH) {
			this.x = Global.GROUND_SIZE * mapXCount - Global.WINDOWS_WIDTH;
		} else {
			this.x = x;
		}
	}

	private void setY(int y) {
		if (y < 0) {
			this.y = 0;
		} else if (y > Global.GROUND_SIZE * mapYCount - Global.WINDOWS_HEIGHT) {
			this.y = Global.GROUND_SIZE * mapYCount - Global.WINDOWS_HEIGHT;
		} else {
			this.y = y;
		}
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void paint(Graphics g) {
		// �Q��agroundLocation
		sceneHelper.paintGround(g, groundLocation, x, y);

		// �L�սu
		if (Global.DEBUG) {
			x2 = 0;
			y2 = 0;
			str = "";
			g.setColor(Color.WHITE);
			do {
				y2 += 64;
				do {
					str = x2 + "," + y2;
					g.drawLine(x2 - x, 0, x2 - x, Global.GROUND_SIZE * mapYCount);
					g.drawString(str, x2 - x, y2 - y);
					x2 += 64;
				} while (x2 < Global.GROUND_SIZE * mapXCount);
				x2 = 0;
				g.drawLine(0, y2 - y, Global.GROUND_SIZE * mapXCount, y2 - y);
			} while (y2 < Global.GROUND_SIZE * mapYCount);
		}
		// �L���~sceneObjects
		for (int i = 0; i < sceneObjects.size(); i++) {
			sceneObjects.get(i).paint(g, x, y);
		}

		// �L�t��others(�p�G�O�]�ߡA�����^�a�F�A�h�L�X�Ө���)
		for (int i = 0; i < others.size(); i++) {
			if (!isNight && (others.get(i).getPicture() == Global.NIGHT_MONSTER
					|| others.get(i).getPicture() == Global.NIGHT_SOLDIER)) {
				continue;
			}
			if (isNight && others.get(i).getPicture() >= 80 && others.get(i).getPicture() < 110) {
				continue;
			}
//			collisionHelper.checkCollision(others.get(i));
			checkCollision(others.get(i));
			others.get(i).paint(g, x, y);
		}

		// �L�D��actor
//		collisionHelper.checkMainCollision(actor);
		checkMainCollision(actor);
		actor.paint(g, x, y);

		// �L�л\��coverObjects
		for (int i = 0; i < coverObjects.size(); i++) {
			coverObjects.get(i).paint(g, x, y);
		}

		if (Global.DEBUG) {
			// ���Y�y��
			g.setColor(Color.BLUE);
			g.drawString("���Y: " + x + "," + y, 10, 20);

			// �a�Ϥj�p
			g.setColor(Color.RED);
			g.drawString("�a��: " + mapXCount + " x " + mapYCount, 10, 40);
		}

		// �]�߼Ҧ�
		if (Global.CAMERA_NIGHT) {
			dayDark();
		}
		if (isNight) { // �]�߼Ҧ�����o
			g.setColor(night);
			g.fillRect(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
		}
	}

	public void update(Actor actor) { // �Q�n���Y���ƶ��P(�i��)
//		int tmpX = actor.getX() - Global.WINDOWS_WIDTH / 2 - x/3;
//		int tmpY = actor.getY() - Global.WINDOWS_HEIGHT / 2 - y/3;
//
//		if (actor.getX() - x > Global.WINDOWS_WIDTH * 3 / 5) {
//			tmpX = actor.getX() - Global.WINDOWS_WIDTH * 3 / 5;
//		} else if (actor.getX() - x <= Global.WINDOWS_WIDTH * 2 / 5) {
//			tmpX = actor.getX() - Global.WINDOWS_WIDTH * 2 / 5;
//		}
//
//		if (actor.getY() - y >= Global.WINDOWS_HEIGHT * 3 / 5) {
//			tmpY = actor.getY() - Global.WINDOWS_HEIGHT * 3 / 5;
//		} else if (actor.getY() - y <= Global.WINDOWS_HEIGHT * 2 / 5) {
//			tmpY = actor.getY() - Global.WINDOWS_HEIGHT * 2 / 5;
//		}
//
//		setX(tmpX);
//		setY(tmpY);

		setX(actor.getX() - (Global.WINDOWS_WIDTH / 2 - Global.CHICK_SIZE_X / 2));
		setY(actor.getY() - (Global.WINDOWS_HEIGHT / 2 - Global.CHICK_SIZE_Y / 2));
		this.actor = actor;
	}

	private void checkMainCollision(Actor actor) {
		// �������~�P�w
		for (int i = 0; i < sceneObjects.size(); i++) {
			if (actor.isCollision(sceneObjects.get(i)) && sceneObjects.get(i).getPicture() != Global.GROUND_ROAD
					&& sceneObjects.get(i).getPicture() != Global.GROUND_TURF_LADDER) {
				// ���ʧP�w
				switch (actor.getDirection()) {
				case Global.UP:
					actor.setY(sceneObjects.get(i).getBottom() + 1);
					break;
				case Global.LEFT:
					actor.setX(sceneObjects.get(i).getRight() + 1);
					break;
				case Global.DOWN:
					actor.setY(sceneObjects.get(i).getTop()
							- (actor.getPicture() <= 3 ? Global.CHICK_SIZE_Y : Global.ACTOR_SIZE) - 1);
					break;
				case Global.RIGHT:
					actor.setX(sceneObjects.get(i).getLeft()
							- (actor.getPicture() <= 3 ? Global.CHICK_SIZE_X : Global.ACTOR_SIZE) - 1);
					break;
				}
				// ��ܧP�w
				switch (sceneObjects.get(i).getPicture()) {
				case Global.GROUND_WATER:
					dialogue = "�n����������I(�^�_�]�O)";
					yesNo = true;
					break;
				case Global.GROUND_TREE:
					dialogue = "�گ�ֵ֡B�{�{�����I";
					yesNo = false;
					break;
				case Global.GROUND_WATERPOOL:
					dialogue = "��սS򡪺�r���I";
					yesNo = false;
					break;
				case Global.GROUND_TURF_WALL:
					dialogue = "���Ǯk������¶���ý��I";
					yesNo = false;
					break;
				case Global.GROUND_TURF_LADDER:
					dialogue = "����W�����F�C�a�I";
					yesNo = false;
					break;
				case Global.GROUND_CHAIR:
					dialogue = "�ѤH���������ȡA�i���}�o�̤����ڧ�QQ�I";
					yesNo = false;
					break;
				case Global.GROUND_STATUE1:
					dialogue = "��իD�Z�����s�J���I";
					yesNo = false;
					break;
				case Global.GROUND_FRUIT:
					dialogue = "�o�Ǥ��G�ݰ_�Ӧn�n�Y�I";
					yesNo = false;
					break;
				case Global.GROUND_GOODS:
				case Global.GROUND_BOXES:
					dialogue = "�o�̰��������I";
					yesNo = false;
					break;
				case Global.GROUND_STATUE2:
					dialogue = "�k���J�����S�۲��M���R�������I";
					yesNo = false;
					break;
				case Global.GROUND_EQUIPMENT1:
				case Global.GROUND_EQUIPMENT2:
				case Global.GROUND_EQUIPMENT3:
					dialogue = "�n�h�x���Ѫ����Ÿ˳ơI";
					yesNo = false;
					break;
				case Global.GROUND_BONE:
					dialogue = "�����W�ͪ������e�A�O�H�򰩮��M�I";
					yesNo = false;
					break;
				case Global.GROUND_PROP1:
				case Global.GROUND_PROP2:
				case Global.GROUND_PROP3:
				case Global.GROUND_PROP4:
					dialogue = "�n�h�����������j�ǹD��I";
					yesNo = false;
					break;
				case Global.GROUND_GROSS:
					dialogue = "�а�лx�ʪ��Q�r�[�лx�I";
					yesNo = false;
					break;
				case Global.GROUND_GRAVE:
					dialogue = "Rest In Peace�K�K";
					yesNo = false;
					break;
				case Global.GROUND_OBSTACLE_CHURCH:
					dialogue = "�o�̬O�а�I(�i�i�J)";
					yesNo = true;
					break;
				case Global.GROUND_OBSTACLE_EQUIPMENT:
					dialogue = "�o�̬O�˳Ʃ��I(�i�i�J)";
					yesNo = true;
					break;
				case Global.GROUND_OBSTACLE_PROP:
					dialogue = "�o�̬O�D�㩱�I(�i�i�J)";
					yesNo = true;
					break;
				case Global.GROUND_OBSTACLE_STORE:
					dialogue = "�o�̬O�ө��I(�i�i�J)";
					yesNo = true;
					break;
				case Global.GROUND_OBSTACLE_CENTER:
					dialogue = "�o�̬O�����a�I(�i�i�J)";
					yesNo = true;
					break;
				case Global.GROUND_OBSTACLE_HOME:
					dialogue = "�o�̬O�������ڼȦ��a�I(�i�i�J)";
					yesNo = true;
					break;
				}
			}
			// ��ܦ^�_��l
			if (!actor.isCollision(sceneObjects.get(i)) && delayCounter.update()) {
				dialogue = "�}�o�̻��ڬO���A�ڴN�O�����I";
				yesNo = false;
			}
		}
		// �����л\���P�w
		for (int i = 0; i < coverObjects.size(); i++) {
			if (actor.isCollision(coverObjects.get(i))) {
				yesNo = false;
				// ��ܧP�w
				switch (coverObjects.get(i).getPicture()) {
				case Global.GROUND_SKYTREE:
					dialogue = "�B�ѻ\�a���Ѫž�I";
					break;
				case Global.GROUND_TENT:
					dialogue = "�O�ӱb�O�A���n�H�K�����i�h�n�F�I";
					break;
				case Global.GROUND_SHELF:
					dialogue = "�g�N�S�D�n���𮧳B�I";
					break;
				case Global.GROUND_HOUSE:
					dialogue = "�Фl�\���ܰ�T�w���I";
					break;
				}
			}
			// ��ܦ^�_��l
			if (!actor.isCollision(sceneObjects.get(i)) && delayCounter.update()) {
				dialogue = "�}�o�̻��ڬO���A�ڴN�O�����I";
				yesNo = false;
			}
		}
	}

	private void checkCollision(Actor actor) {
		// �������~�P�w
		for (int i = 0; i < sceneObjects.size(); i++) {
			if (actor.isCollision(sceneObjects.get(i)) && sceneObjects.get(i).getPicture() != Global.GROUND_ROAD) {
				// ���ʸI���P�w
				switch (actor.getDirection()) {
				case Global.UP:
					actor.setY(sceneObjects.get(i).getBottom() + 1);
					break;
				case Global.LEFT:
					actor.setX(sceneObjects.get(i).getRight() + 1);
					break;
				case Global.DOWN:
					actor.setY(sceneObjects.get(i).getTop() - Global.ACTOR_SIZE - 1);
					break;
				case Global.RIGHT:
					actor.setX(sceneObjects.get(i).getLeft() - Global.ACTOR_SIZE - 1);
					break;
				}
			}
		}
		// ����NPC�P�w
		if (actor.isCollision(this.actor)) {
			switch (actor.getPicture()) {
			case Global.BUTTERFLY:
				dialogue = "�����㽹����ͪ��u���R�I";
				yesNo = false;
				break;
			case Global.SLIME:
				dialogue = "�s������ꪺ�v�ܩi�I(�i����)";
				yesNo = true;
				break;
			case Global.NIGHT_MONSTER:
				dialogue = "�i�Ȫ��¼v�Ǫ��I(�i����)";
				yesNo = true;
				break;
			case Global.NIGHT_SOLDIER:
				dialogue = "�O�ç����w�����L�W�^���I";
				yesNo = false;
				break;
			}
			if (actor.getPicture() >= 80 && actor.getPicture() < 110) {
				dialogue = "�¾�i�R�������̡I";
				yesNo = false;
			}
		}
		// ��ܦ^�_��l
		if (!actor.isCollision(this.actor) && delayCounter.update()) {
			dialogue = "�}�o�̻��ڬO���A�ڴN�O�����I";
			yesNo = false;
		}
	}

	public void printDialogue(Graphics g, String name, boolean enter, boolean yesNo) {
		if (enter) {
			g.setColor(blackWindows);
			g.fillRect(40, 500, 900, 150);
			g.setColor(whiteString);
			g.drawString(name, 350, 540);
			g.drawString(dialogue, 350, 540 + 30);
//			g.drawString(collisionHelper.getDialogue(), 350, 540 + 30);
			g.drawString("��enter�~��", 840, 630);
			ImageController irc = ImageController.getInstance(); // �����D��o�̦n���n�A����o
			BufferedImage img = irc.tryGetImage(PathBuilder.getImg(ImagePath.Bird.Facial.B0));
			g.drawImage(img, 50, 380, 50 + 272, 380 + 288, 0, 0, 272, 288, null);

			if (this.yesNo) {
				g.drawString("�O", 680, 600);
				g.drawString("�_", 720, 600);
				g.drawRect((yesNo ? 670 : 710), 580, 30, 30);
			}
		}
	}

}
