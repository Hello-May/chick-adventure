package Scenes;

import java.awt.Graphics;
import java.util.ArrayList;
import Controllers.SceneController;
import GameObject.Actor;
import GameObject.SceneObject;
import IO.CommandSolver.KeyCommandListener;
import Utils.Global;

public class Game_MainScene extends Scene {
	private ArrayList<SceneObject> sceneObjects;
//	private ArrayList<SceneObject> groundObjects;
	private ArrayList<SceneObject> coverObjects;
	private ArrayList<Actor> targets;
	private ArrayList<Actor> passers;
	private ArrayList<Actor> others; // 路人+敵人
	private KeyCommandListener commandListener;
	private boolean enter; // 對話框使用
	private boolean yesNo;

	public Game_MainScene(SceneController sceneController) {
		super(sceneController);
		builder();
		commandListener = new KeyCommandListener() {
			@Override
			public void keyPressed(int commandCode, long time) {
				System.out.println("pressed at: " + time + " -> " + commandCode);
				switch (commandCode) {
				case Global.UP:
				case Global.DOWN:
					if (!enter) {
						actor.changeDirection(commandCode);
						actor.setSpeed(Global.ACTOR_SPEED);
					}
					break;
				case Global.LEFT:
				case Global.RIGHT:
					if (!enter) {
						actor.changeDirection(commandCode);
						actor.setSpeed(Global.ACTOR_SPEED);
					}
					break;
				case Global.SPACE:
					// actor.useSkill(target);
					break;
				case Global.ESC:
					sceneController.getGameMenu()[0].setActor(actor);
					sceneController.changeScene(sceneController.getGameMenu()[0]);
					break;
				case Global.ENTER:
					if (enter == true && yesNo == true) {
						System.out.println("換場景");
						sceneController.getGameMenu()[0].setActor(actor);
						sceneController.changeScene(sceneController.getGameMenu()[0]);
					}
					break;
				}
			}

			@Override
			public void keyReleased(int commandCode, long time) {
				switch (commandCode) {
				case Global.UP:
				case Global.DOWN:
					actor.setSpeed(0);
					break;
				case Global.LEFT:
				case Global.RIGHT:
					actor.setSpeed(0);
					yesNo = (yesNo == true ? false : true);
					break;
				case Global.ENTER:
					enter = (enter == true ? false : true);
					yesNo = false;
					break;
				}
			}
		};
	}

	private void builder() {
		sceneObjects = new ArrayList<>();
//		groundObjects = new ArrayList<>();
		coverObjects = new ArrayList<>();
		targets = new ArrayList<>();
		passers = new ArrayList<>();
		others = new ArrayList<>();
		enter = true;
		// 場景人物
		actor = new Actor(Global.CHICK_X, Global.CHICK_Y, Global.CHICK_SIZE_X, Global.CHICK_SIZE_Y, 0, 0,
				Global.CHICK_DELAY);
		Actor tmpActor;
		for (int i = 0; i < 12; i++) { // 史萊姆
			tmpActor = new Actor((int) (Math.random() * (64 * 41)) + 64 * 2, (int) (Math.random() * (64 * 8)) + 64 * 2,
					Global.ACTOR_SIZE, Global.ACTOR_SIZE, Global.ACTOR_SPEED, Global.SLIME, Global.PASSERS_DELAY);
			targets.add(tmpActor);
			others.add(tmpActor);
		}
		for (int i = 0; i < 6; i++) { // 夜晚黑怪
			tmpActor = new Actor((int) (Math.random() * (64 * 41)) + 64 * 2, (int) (Math.random() * (64 * 6)) + 64 * 2,
					60, 60, Global.ACTOR_SPEED, Global.NIGHT_MONSTER, Global.PASSERS_DELAY);
			targets.add(tmpActor);
			others.add(tmpActor);
		}
		for (int i = 0; i < 40; i++) { // 蝴蝶
			tmpActor = new Actor((int) (Math.random() * (64 * 41)) + 64 * 2,
					(int) (Math.random() * (64 * 18)) + 64 * 10, Global.ACTOR_SIZE, Global.ACTOR_SIZE,
					Global.ACTOR_SPEED / 2, Global.BUTTERFLY, Global.PASSERS_DELAY);
			passers.add(tmpActor);
			others.add(tmpActor);
		}
		for (int i = 80; i < 110; i++) { // 白天村民
			tmpActor = new Actor((int) (Math.random() * (64 * 41)) + 64 * 2,
					(int) (Math.random() * (64 * 14)) + 64 * 14, Global.ACTOR_SIZE, Global.ACTOR_SIZE,
					Global.ACTOR_SPEED / 2, i, Global.PASSERS_DELAY);
			passers.add(tmpActor);
			others.add(tmpActor);

		}
		for (int i = 0; i < 18; i++) { // 夜晚巡邏士兵
			tmpActor = new Actor((int) (Math.random() * (64 * 41)) + 64 * 2,
					(int) (Math.random() * (64 * 14)) + 64 * 14, Global.ACTOR_SIZE, Global.ACTOR_SIZE,
					Global.ACTOR_SPEED / 2, Global.NIGHT_SOLDIER, Global.PASSERS_DELAY);
			passers.add(tmpActor);
			others.add(tmpActor);
		}
		// 場景地面
		SceneObject tmp;
		int x = 0;
		int y = 0;
		groundLocation = Global.VILLAGE_GROUND_LOCATION;
		// 場景物品
		x = 0;
		y = 0;
		objectsLocation = Global.VILLAGE_OBJECTS_LOCATION;
		for (int i = 0; i < objectsLocation.length; i++) {
			for (int j = 0; j < objectsLocation[0].length; j++) {
				if (objectsLocation[i][j] != 0) {
					tmp = new SceneObject(x, y, Global.GROUND_SIZE, Global.GROUND_SIZE, objectsLocation[i][j]);
					sceneObjects.add(tmp);
					switch (objectsLocation[i][j]) {
					case Global.GROUND_TURF_WALL:
						tmp.setDecoration((int) (Math.random() * 4) == 0 ? true : false);
						break;
					case Global.GROUND_WATERPOOL:
						tmp.setDecoration((int) (Math.random() * 5) == 0 ? true : false);
						break;
					}
				}
				x += 64;
			}
			x = 0;
			y += 64;
		}
		// 場景覆蓋物
		x = 0;
		y = 0;
		coverLocation = Global.VILLAGE_COVER_LOCATION;
		for (int i = 0; i < coverLocation.length; i++) { // 對應數字參照Global.GROUND
			for (int j = 0; j < coverLocation[0].length; j++) {
				if (coverLocation[i][j] != 0) {
					switch (coverLocation[i][j]) {
					case Global.GROUND_BRIDGE:
						tmp = new SceneObject(x, y, Global.GROUND_SIZE, Global.GROUND_SIZE, coverLocation[i][j]);
						break;
					case Global.GROUND_HOUSE:
						tmp = new SceneObject(x, y, Global.GROUND_SIZE * 4, Global.GROUND_SIZE * 5,
								coverLocation[i][j]);
						tmp.setDecoration(true);
						break;
					default:
						tmp = new SceneObject(x, y, Global.GROUND_SIZE * 3, Global.GROUND_SIZE * 3,
								coverLocation[i][j]);
					}
					coverObjects.add(tmp);
				}
				x += 64;
			}
			x = 0;
			y += 64;
		}
		camera = new Camera(actor, Global.CAMERA_X, Global.CAMERA_Y, groundLocation[0].length, groundLocation.length);
//		camera.setObjectsAndOthers(groundObjects,sceneObjects, others);
		camera.setObjectsAndOthers(groundLocation, sceneObjects, coverObjects, others);
	}

	@Override
	public void sceneBegin() {
	}

	@Override
	public void sceneUpdate() {
		actor.move();
		for (int i = 0; i < passers.size(); i++) {
			passers.get(i).randomMove();
		}
		for (int i = 0; i < targets.size(); i++) {
			targets.get(i).randomMove();
		}
		camera.update(actor);
	}

	@Override
	public void sceneEnd() {
	}

	@Override
	public void paint(Graphics g) {
		camera.paint(g);

		// 印對話框
		camera.printDialogue(g, actor.getName(), enter, yesNo);
	}

	@Override
	public KeyCommandListener getCommandListener() {
		return commandListener;
	}

}
