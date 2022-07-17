package Scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Controllers.MusicController;
import Controllers.SceneController;
import Controllers.StoryController;
import GameObject.Actor;
import GameObject.GameObject;
import GameObject.SceneObject;
import IO.CommandSolver.KeyCommandListener;
import Utils.DelayCounter;
import Utils.Global;
import Utils.Messages;
import Utils.Messages.MsgFlow;
import Values.MusicPath;
import Values.PathBuilder;
import popup.Status_InfoPopUpWindow;
import popup.Status_SkillPopUpWindow;
import popup.MsgPopUpWindow;
import popup.StatusPopUpWindow;
import popup.Status_FilePopUpWindow;
import popup.MsgPopUpWindow.Action;
import popup.PopUpWindow;

public class Game_MainScene extends Scene {
	private ArrayList<SceneObject> sceneObjects;
	private ArrayList<SceneObject> groundObjects;
	private ArrayList<SceneObject> coverObjects;
	private ArrayList<Actor> targets;
	private ArrayList<Actor> passers;
	private ArrayList<Actor> others; // 路人+敵人
	private Actor villageHead;
	private Actor bigSlime;
	private KeyCommandListener keycommandListener;
	private boolean enter; // 對話框使用
	private boolean[] keyInput = { false, false, false, false };
	private StatusPopUpWindow currentWindow;
	private MsgPopUpWindow msgPopUp;
	private PopUpWindow secondaryPopUp;
	private static StoryController storyController = StoryController.getInstance();
	private MusicController bgm;
	private MusicController sound;
	private Actor tmpActor;
	private SceneObject tmp;
	private Status_FilePopUpWindow status_FilePopUpWindow;
	private DelayCounter delayScene;
	private GameObject obj1;
	private GameObject obj2;
	private boolean isAllReleased;

	@Override
	public void notify(int storyFlow1) {
		super.notify(storyFlow1);
		switch (storyFlow1) {
		case 1:
			villageHead = new Actor(1380, 1350, Global.ACTOR_SIZE, Global.ACTOR_SIZE, 0, Global.VILLAGE_HEAD, // 村長
					Global.PASSERS_DELAY, null);
			villageHead.setMood(0);
			others.add(villageHead);
			break;
		case 2:
			bigSlime = new Actor(2048, 300, Global.ACTOR_SIZE * 2, Global.ACTOR_SIZE * 2, 0, Global.SLIME, // 大史萊姆
					Global.PASSERS_DELAY, Global.STATUS_BIG_SLIME);
			others.add(bigSlime); // 打贏大史萊姆會推動劇情

			for (int i = 0; i < 18; i++) { // 史萊姆小囉囉
				tmpActor = new Actor((int) (Math.random() * (64 * 8)) + 64 * 28,
						(int) (Math.random() * (64 * 5)) + 64 * 3, Global.ACTOR_SIZE, Global.ACTOR_SIZE,
						Global.ACTOR_SPEED, Global.SLIME, Global.PASSERS_DELAY, Global.STATUS_SLIME.cloneAndRefresh());
				targets.add(tmpActor);
				others.add(tmpActor);
			}

			for (int i = 0; i < 4; i++) { // 被欺負的村民
				tmpActor = new Actor(2048 - 200 + (int) (Math.random() * 100), 256 + 200 + (int) (Math.random() * 100),
						Global.ACTOR_SIZE, Global.ACTOR_SIZE, 0, Global.CHILD, 0, null);
				tmpActor.changeDirection(i);
				others.add(tmpActor);
			}
			break;
		case 3: // 打完出現大史萊姆的殘渣、找小朋友爸媽
			clear(others, Global.SLIME); // 史萊姆們消失
			villageHead.setMood(-2);
			bigSlime = null;
			tmp = new SceneObject(2048 - 100, 256 + 50, Global.GROUND_SIZE, Global.GROUND_SIZE,
					Global.GROUND_KEY_INSLIME);
			sceneObjects.add(tmp);
			break;
		case 4: // 跟爸媽講話後 => 出現樹下的箱子、黑影怪
			clear(others, 70); // 小朋友消失
			clear(sceneObjects, Global.GROUND_KEY_INSLIME); // 史萊姆殘渣消失
			tmp = new SceneObject(960, 320, Global.GROUND_SIZE, Global.GROUND_SIZE, Global.GROUND_SHINING); // 閃光物
			sceneObjects.add(tmp);

			for (int i = 0; i < 10; i++) { // 替代黑影怪
				tmpActor = new Actor((int) (Math.random() * (64 * 5)) + 64 * 13,
						(int) (Math.random() * (64 * 6)) + 64 * 3, Global.ACTOR_SIZE, Global.ACTOR_SIZE,
						Global.ACTOR_SPEED, Global.SLIME - 2, Global.PASSERS_DELAY,
						Global.STATUS_SLIME.cloneAndRefresh());
				targets.add(tmpActor);
				others.add(tmpActor);
			}
			break;
		case 7:
			clear(sceneObjects, Global.GROUND_SHINING); // 閃光物消失
			clear(others, Global.VILLAGE_HEAD);
			villageHead.setX(1536);
			villageHead.setY(1728);
			others.add(villageHead);
			break;
		case 10:
			getCamera().setIsRain(true);
			clear(others, Global.SOLDIER);
			clear(passers, Global.SOLDIER);
			for (int i = 80; i < 110; i++) { // 村民消失一半
				if (i == 86) {
					continue;
				}
				if (i == 88) {
					i = 102;
				}
				clear(others, i);
			}
			for (int i = 0; i < 24; i++) { // 巡邏士兵24個
				tmpActor = new Actor((int) (Math.random() * (64 * 38)) + 64 * 5,
						(int) (Math.random() * (64 * 14)) + 64 * 14, Global.ACTOR_SIZE, Global.ACTOR_SIZE,
						Global.ACTOR_SPEED / 2, Global.SOLDIER, Global.PASSERS_DELAY,
						Global.STATUS_SOLDIER.cloneAndRefresh());
				passers.add(tmpActor);
				others.add(tmpActor);
			}
			for (int i = 0; i < 16; i++) { // 門口士兵
				tmpActor = new Actor(1020 + 33 * (i % 8), 1420 + 33 * (i / 8), Global.ACTOR_SIZE, Global.ACTOR_SIZE, 0,
						Global.SOLDIER, Global.PASSERS_DELAY, Global.STATUS_SOLDIER.cloneAndRefresh());
				tmpActor.setFixedDirection(Global.UP);
				passers.add(tmpActor);
				others.add(tmpActor);
			}
			for (int i = 0; i < 16; i++) { // 門口士兵
				if (i % 8 >= 2 && i % 8 <= 5) {
					continue;
				}
				tmpActor = new Actor(1020 + 33 * (i % 8), 1354 + 33 * (i / 8), Global.ACTOR_SIZE, Global.ACTOR_SIZE, 0,
						Global.SOLDIER, Global.PASSERS_DELAY, Global.STATUS_SOLDIER.cloneAndRefresh());
				tmpActor.setFixedDirection((i % 8 >= 0 && i % 8 <= 2 ? Global.RIGHT : Global.LEFT));
				passers.add(tmpActor);
				others.add(tmpActor);
			}
			break;
		case 15: // clear全部、加入全新村民、還有魔化的村長
			if (!Global.DEBUG) {
				actor.setStep(2);
			}
			others.clear();
			passers.clear();
			targets.clear();
			for (int j = 0; j < 3; j++) {
				for (int i = 127; i < 136; i++) { // 魔化村民 (原120-136調整至127-136)
//					if (i == 126) {
//						continue;
//					}
					tmpActor = new Actor((int) (Math.random() * (64 * 38)) + 64 * 5,
							(int) (Math.random() * (64 * 14)) + 64 * 15, Global.ACTOR_SIZE, Global.ACTOR_SIZE,
							Global.ACTOR_SPEED / 2, i, Global.PASSERS_DELAY, Global.STATUS_PEOPLE.cloneAndRefresh());
					targets.add(tmpActor);
					others.add(tmpActor);
				}
			}
			for (int i = 0; i < 5; i++) { // 士兵(原16調整5)
				tmpActor = new Actor((int) (Math.random() * (64 * 38)) + 64 * 5,
						(int) (Math.random() * (64 * 14)) + 64 * 15, Global.ACTOR_SIZE, Global.ACTOR_SIZE,
						Global.ACTOR_SPEED / 2, Global.SOLDIER, Global.PASSERS_DELAY,
						Global.STATUS_SOLDIER.cloneAndRefresh());
				targets.add(tmpActor);
				others.add(tmpActor);
			}
			// 魔化村長 => 黑影怪
			villageHead = new Actor(2730, 1765, 60, 60, Global.ACTOR_SPEED / 2, Global.MONSTER, Global.PASSERS_DELAY,
					Global.STATUS_VILLAGE_HEAD.cloneAndRefresh());
			targets.add(villageHead);
			others.add(villageHead);
			break;
		}

	}

	private void clear(ArrayList<? extends GameObject> arr, int pic) {
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i) == null) {
				continue;
			} else if (arr.get(i).getPicture() == pic) {
				arr.set(i, null);
//				arr.remove(i);
			}
		}
	}

	public Game_MainScene(SceneController sceneController) {
		super(sceneController);
		builder();
		keycommandListener = new KeyCommandListener() {
			@Override
			public void keyPressed(int commandCode, long time) {
				if (commandCode < 4 && commandCode >= 0) {
					keyInput[commandCode] = true;
				}
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
				}
			}

			@Override
			public void keyReleased(int commandCode, long time) {
				isAllReleased = true;
				if (commandCode < 4 && commandCode >= 0) {
					keyInput[commandCode] = false;
				}
				switch (commandCode) {
				case Global.UP:
				case Global.DOWN:
					for (boolean b : keyInput) {
						if (b) {
							isAllReleased = false;
							break;
						}
					}
					if (isAllReleased) {
						actor.setSpeed(0);
					}
					break;
				case Global.LEFT:
				case Global.RIGHT:
					for (boolean b : keyInput) {
						if (b) {
							isAllReleased = false;
							break;
						}
					}
					if (isAllReleased) {
						actor.setSpeed(0);
					}
					break;
				case Global.ESC:
					if (currentWindow == null) {
						currentWindow = new StatusPopUpWindow(250, 200, 100, 250);
						currentWindow.setOnClickListener(new StatusPopUpWindow.ButtonListener() {
							@Override
							public void onClick(int count) {
								switch (count) {
								case 0:
									currentWindow = null;
									secondaryPopUp = null;
									break;
								case 1:
									secondaryPopUp = new Status_InfoPopUpWindow(400, 190, 300, 250, actor);
									break;
								case 2:
									secondaryPopUp = new Status_SkillPopUpWindow(400, 190, 300, 250, actor);
									break;
								case 3:
									secondaryPopUp = null;
									status_FilePopUpWindow = new Status_FilePopUpWindow(400, 190, 300, 250, actor,
											storyFlow1);
									status_FilePopUpWindow
											.setOnClickListener(new Status_FilePopUpWindow.ButtonListener() {
												@Override
												public void onClick(int count) {
													switch (count) {
													case 0:
														status_FilePopUpWindow.write(count);
														break;
													case 1:
														status_FilePopUpWindow.write(count);
														break;
													case 2:
														status_FilePopUpWindow.write(count);
														break;
													}
												}
											});
									break;
								case 4:
									sceneController.changeScene(sceneController.getMenuScene());
									break;
								}
							}
						});
					}
					secondaryPopUp = null;
					break;
				case Global.ENTER:
					if (obj1 != null && !actor.isCollision(obj1, 1)) {
						obj1 = null;
					}
					if (obj1 != null && msgPopUp == null && obj1.getStatus() == null) {
						isAllReleased = true;
						for (int i = 0; i < keyInput.length; i++) {
							keyInput[i] = false;
							actor.setSpeed(0);
						}
						MsgFlow mf = Messages.quickGen(sceneController, actor, obj1); // 原來是傳入obj.getPicture()改成obj
						msgPopUp = new MsgPopUpWindow(mf, actor, new Action() {
							@Override
							public void action() {
								msgPopUp = null;
							}
						});
					}
					break;

				}
			}
		};
	}

	private void builder() {
		delayScene = new DelayCounter(120);
		sceneObjects = new ArrayList<>();
		groundObjects = new ArrayList<>();
		coverObjects = new ArrayList<>();
		targets = new ArrayList<>();
		passers = new ArrayList<>();
		others = new ArrayList<>();
		enter = false;
		// 主角
		actor = new Actor(Global.CHICK_X, Global.CHICK_Y, Global.CHICK_SIZE_X, Global.CHICK_SIZE_Y, 0, 0,
				Global.CHICK_DELAY, Global.STATUS_CHICK.cloneAndRefresh());
		// 配角
		for (int i = 0; i < 30; i++) { // 蝴蝶
			tmpActor = new Actor((int) (Math.random() * (64 * 41)) + 64 * 2,
					(int) (Math.random() * (64 * 18)) + 64 * 10, Global.ACTOR_SIZE, Global.ACTOR_SIZE,
					Global.ACTOR_SPEED / 2, Global.BUTTERFLY, Global.PASSERS_DELAY, null);
			passers.add(tmpActor);
			others.add(tmpActor);
		}
		for (int j = 0; j < 2; j++) {
			for (int i = 40; i < 45; i++) { // 小動物
				tmpActor = new Actor((int) (Math.random() * (64 * 41)) + 64 * 2,
						(int) (Math.random() * (64 * 18)) + 64 * 10, Global.ACTOR_SIZE, Global.ACTOR_SIZE,
						Global.ACTOR_SPEED / 2, i, Global.PASSERS_DELAY, null);
				passers.add(tmpActor);
				others.add(tmpActor);
			}
		}
		for (int i = 80; i < 110; i++) { // 村民
			if (i == 86 || i == 102) {
				continue;
			}
			tmpActor = new Actor((int) (Math.random() * (64 * 38)) + 64 * 5,
					(int) (Math.random() * (64 * 14)) + 64 * 15, Global.ACTOR_SIZE, Global.ACTOR_SIZE,
					Global.ACTOR_SPEED / 2, i, Global.PASSERS_DELAY, null);
			passers.add(tmpActor);
			others.add(tmpActor);
		}
		for (int i = 0; i < 8; i++) { // 巡邏士兵
			tmpActor = new Actor((int) (Math.random() * (64 * 38)) + 64 * 5,
					(int) (Math.random() * (64 * 14)) + 64 * 14, Global.ACTOR_SIZE, Global.ACTOR_SIZE,
					Global.ACTOR_SPEED / 2, Global.SOLDIER, Global.PASSERS_DELAY, null);
			passers.add(tmpActor);
			others.add(tmpActor);
		}
		// 場景地面
		int x = 0;
		int y = 0;
		groundLocation = Global.VILLAGE_GROUND_LOCATION;
//		for (int i = 0; i < groundLocation.length; i++) {
//			for (int j = 0; j < groundLocation[0].length; j++) {
//					tmp = new SceneObject(x, y, Global.GROUND_SIZE, Global.GROUND_SIZE, groundLocation[i][j]);
//					groundObjects.add(tmp);
//				x += 64;
//			}
//			x = 0;
//			y += 64;
//		}
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
		for (int i = 0; i < coverLocation.length; i++) {
			for (int j = 0; j < coverLocation[0].length; j++) {
				if (coverLocation[i][j] != 0) {
					switch (coverLocation[i][j]) {
					case Global.GROUND_SMALL_FIRE:
						tmp = new SceneObject(x, y, Global.GROUND_SIZE / 2, Global.GROUND_SIZE, coverLocation[i][j]);
						break;
					case Global.GROUND_TURF_LADDER:
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
		camera.setObjectsAndOthers(groundLocation, sceneObjects, coverObjects, others);
//		camera.setObjectsAndOthers(groundObjects, sceneObjects, coverObjects, others);
//		storyController.setSF1(0);
		if (Global.DEBUG) {
			storyController.setSF1(Global.STORY);
		}
	}

	@Override
	public void sceneBegin() {
		isAllReleased = true;
		for (int i = 0; i < keyInput.length; i++) {
			keyInput[i] = false;
			actor.setSpeed(0);
		}
		if (storyController.getSF1() == 0) {
			storyController.nextSF1();
		}
		if (storyController.getSF1() >= 10) {
			getCamera().setIsRain(true);
		}
				
		System.out.println("讀檔故事: "+storyController.getSF1());
		
		if (storyController.getSF1() < 4) {
			bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B2));
			bgm.play();
		}
		if (this.camera.getIsRain()) {
			sound = new MusicController(PathBuilder.getAudio(MusicPath.Sound.THUNDER));
			sound.playOnce();
		}
		if (storyController.getSF1() >= 4 && storyController.getSF1() <= 7) {
			bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B8));
			bgm.play();
		}
		if (storyController.getSF1() == 8 || storyController.getSF1() == 9) {
			bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B7));
			bgm.play();
		}
		if (storyController.getSF1() == 10 || storyController.getSF1() == 11) {
			bgm = new MusicController(PathBuilder.getAudio(MusicPath.Sound.STORY10));
			bgm.play();
		}
		if (storyController.getSF1() > 11) {
			bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B6));
			bgm.play();
		}
	}

	@Override
	public void sceneUpdate() {
		// 怪物主動攻擊
		obj2 = camera.currentCollision();
		if (obj2 != null) {
			obj1 = obj2;
		}
		if (obj2 != null && msgPopUp == null && obj2.getStatus() != null) {
			actor.setSpeed(0);
//					sceneController.changeScene(new BattleScene(sceneController,actor,(Actor)obj2));
			MsgFlow mf = Messages.quickGen(sceneController, actor, obj2);
			msgPopUp = new MsgPopUpWindow(mf, actor, new Action() {
				@Override
				public void action() {
					msgPopUp = null;
					obj2 = null;
				}
			});
		}

		// 主角
		if (actor.getStatus().getCurrentHP() <= 0) {
			sceneController.changeScene(sceneController.getGameOverScene());
		}
		actor.move();
		camera.update(actor);

		// 非主角們
		if (villageHead != null) {
			if (storyController.getSF1() >= 15) {
				villageHead.followMove(actor);
			}
			villageHead.move();
		}
		if (bigSlime != null) {
			if (storyController.getSF1() >= 15) {
				bigSlime.followMove(actor);
			}
			bigSlime.move();
		}
		for (int i = 0; i < passers.size(); i++) {
			if (storyController.getSF1() >= 15) {
				passers.get(i).followMove(actor);
			}
			if (passers.get(i) != null) {
				passers.get(i).randomMove();
			}
		}
		for (int i = 0; i < targets.size(); i++) {
			if (storyController.getSF1() >= 15) {
				targets.get(i).followMove(actor);
			}
			targets.get(i).randomMove();
		}

		// 彈跳視窗
		if (msgPopUp != null) {
			msgPopUp.windowUpdate();
		}
		if (currentWindow != null) {
			currentWindow.windowUpdate();
			if (currentWindow.off() == -1) {
				currentWindow = null;
				secondaryPopUp = null;
			}
		}
		if (secondaryPopUp != null) {
			secondaryPopUp.windowUpdate();
		}
		if (status_FilePopUpWindow != null) {
			status_FilePopUpWindow.windowUpdate();
			if (status_FilePopUpWindow.off() == -1) {
				status_FilePopUpWindow = null;
			}
		}

		// 音樂
		if (sound != null && this.camera.getIsRain() && (int) (Math.random() * 2000) == 0) { // 打雷聲
			sound.stop();
			sound.playOnce();
		}

		// 劇情
		if (storyController.getSF1() == 10 || storyController.getSF1() == 11) {
			actor.setStep(0);
			if (delayScene.update()) {
				sceneController.changeScene(sceneController.getBlackScene(), 0, actor);
			}
		}
	}

	@Override
	public void sceneEnd() {
		if (bgm != null) {
			bgm.stop();
//			curBGM = "無";
		}
	}

	@Override
	public void paint(Graphics g) {
		camera.paint(g);

		if (Global.DEBUG) {
			g.setColor(Color.MAGENTA);
			g.drawString("劇情: " + storyController.getSF1(), 10, 60);
		}

		// 印對話框
		if (msgPopUp != null) {
			msgPopUp.paint(g);
		}

		// 印狀態欄
		if (currentWindow != null) {
			currentWindow.paint(g);
		}
		if (secondaryPopUp != null) {
			secondaryPopUp.paint(g);
		}
		if (status_FilePopUpWindow != null) {
			status_FilePopUpWindow.paint(g);
		}

	}

	@Override
	public KeyCommandListener getKeyCommandListener() {
		if (secondaryPopUp != null && secondaryPopUp.getKeyCommandListener() != null) {
			return secondaryPopUp.getKeyCommandListener();
		}

		if (status_FilePopUpWindow != null && status_FilePopUpWindow.getKeyCommandListener() != null) {
			return status_FilePopUpWindow.getKeyCommandListener();
		}

		if (currentWindow != null) {
			return currentWindow.getKeyCommandListener();
		} else if (msgPopUp != null) {
			return msgPopUp.getKeyCommandListener();
		}

		return keycommandListener;
	}

	@Override
	public KeyCommandListener getMouseCommandListener() {
		return null;
	}

	public Camera getCamera() {
		return this.camera;
	}

}