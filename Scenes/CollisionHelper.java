package Scenes;

import java.awt.Color;
import java.util.ArrayList;

import GameObject.Actor;
import GameObject.SceneObject;
import Utils.DelayCounter;
import Utils.Global;

public class CollisionHelper {	//�Q��B�a���F
	private Actor actor;
//	private ArrayList<Actor> others; // ���H+�ĤH
	private ArrayList<SceneObject> sceneObjects;
	private ArrayList<SceneObject> coverObjects;
	private String dialogue;
	private DelayCounter delayCounter; // �Q�p���ܦ^�_���ɶ�

	public CollisionHelper(ArrayList<SceneObject> sceneObjects, ArrayList<SceneObject> coverObjects,
			ArrayList<Actor> others) {
		this.sceneObjects = sceneObjects;
		this.coverObjects = coverObjects;
//		this.others = others;
		dialogue = "�}�o�̻��ڬO���A�ڴN�O�����I";
		delayCounter = new DelayCounter(10000);
	}

	public String getDialogue() {
		return this.dialogue;
	}

	public void checkMainCollision(Actor actor) {
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
					break;
				case Global.GROUND_TREE:
					dialogue = "�گ�ֵ֡B�{�{�����I";
					break;
				case Global.GROUND_WATERPOOL:
					dialogue = "��սS򡪺�r���I";
					break;
				case Global.GROUND_TURF_WALL:
					dialogue = "���Ǯk������¶���ý��I";
					break;
				case Global.GROUND_TURF_LADDER:
					dialogue = "����W�����F�C�a�I";
					break;
				case Global.GROUND_CHAIR:
					dialogue = "�ѤH���������ȡA�i���}�o�̤����ڧ�QQ�I";
					break;
				case Global.GROUND_STATUE1:
					dialogue = "��իD�Z�����s�J���I";
					break;
				case Global.GROUND_FRUIT:
					dialogue = "�o�Ǥ��G�ݰ_�Ӧn�n�Y�I";
					break;
				case Global.GROUND_GOODS:
				case Global.GROUND_BOXES:
					dialogue = "�o�̰��������I";
					break;
				case Global.GROUND_STATUE2:
					dialogue = "�k���J�����S�۲��M���R�������I";
					break;
				case Global.GROUND_EQUIPMENT1:
				case Global.GROUND_EQUIPMENT2:
				case Global.GROUND_EQUIPMENT3:
					dialogue = "�n�h�x���Ѫ����Ÿ˳ơI";
					break;
				case Global.GROUND_BONE:
					dialogue = "�����W�ͪ������e�A�O�H�򰩮��M�I";
					break;
				case Global.GROUND_PROP1:
				case Global.GROUND_PROP2:
				case Global.GROUND_PROP3:
				case Global.GROUND_PROP4:
					dialogue = "�n�h�����������j�ǹD��I";
					break;
				case Global.GROUND_GROSS:
					dialogue = "�а�лx�ʪ��Q�r�[�лx�I";
					break;
				case Global.GROUND_GRAVE:
					dialogue = "Rest In Peace�K�K";
					break;
				case Global.GROUND_OBSTACLE_CHURCH:
					dialogue = "�o�̬O�а�I(�i�J�а�)";
					break;
				}
			}
			// ��ܦ^�_��l
			if (!actor.isCollision(sceneObjects.get(i)) && delayCounter.update()) {
				dialogue = "�}�o�̻��ڬO���A�ڴN�O�����I";
			}
		}
		// �����л\���P�w
		for (int i = 0; i < coverObjects.size(); i++) {
			if (actor.isCollision(coverObjects.get(i))) {
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
			}
		}
	}

	public void checkCollision(Actor actor) {
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
				break;
			case Global.SLIME:
				dialogue = "�s������ꪺ�v�ܩi�I(�i����)";
				break;
			case Global.NIGHT_MONSTER:
				dialogue = "�i�Ȫ��¼v�Ǫ��I(�i����)";
				break;
			case Global.NIGHT_SOLDIER:
				dialogue = "�O�ç����w�����L�W�^���I";
				break;
			}
			if (actor.getPicture() >= 80 && actor.getPicture() < 110) {
				dialogue = "�¾�i�R�������̡I";
			}
		}
		// ��ܦ^�_��l
		if (!actor.isCollision(this.actor) && delayCounter.update()) {
			dialogue = "�}�o�̻��ڬO���A�ڴN�O�����I";
		}
	}

}
