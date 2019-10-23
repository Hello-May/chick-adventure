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
	private ArrayList<Actor> others; // 路人+敵人
	private int x; // camera左上前一個坐標
	private int y;
	private int mapXCount; // 鏡頭邊界判斷用
	private int mapYCount;
	private String str;
	private ArrayList<SceneObject> sceneObjects;
	private int[][] groundLocation;
//	private ArrayList<SceneObject> groundObjects;
	private ArrayList<SceneObject> coverObjects;
	private Color blackWindows; // 印對話框
	private Color whiteString;
	private String dialogue;
	private boolean yesNo;
	private int focusIndex;
	private DelayCounter delayCounter; // 想計算對話回復的時間
	private int x2; // 給白線用的東西
	private int y2;
	private boolean isNight; // 天黑後使用
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
		dialogue = "開發者說我是雞，我就是隻雞！";
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
		// 鋪草地groundLocation
		sceneHelper.paintGround(g, groundLocation, x, y);

		// 印白線
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
		// 印物品sceneObjects
		for (int i = 0; i < sceneObjects.size(); i++) {
			sceneObjects.get(i).paint(g, x, y);
		}

		// 印配角others(如果是夜晚，村民回家了，士兵出來巡邏)
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

		// 印主角actor
//		collisionHelper.checkMainCollision(actor);
		checkMainCollision(actor);
		actor.paint(g, x, y);

		// 印覆蓋物coverObjects
		for (int i = 0; i < coverObjects.size(); i++) {
			coverObjects.get(i).paint(g, x, y);
		}

		if (Global.DEBUG) {
			// 鏡頭座標
			g.setColor(Color.BLUE);
			g.drawString("鏡頭: " + x + "," + y, 10, 20);

			// 地圖大小
			g.setColor(Color.RED);
			g.drawString("地圖: " + mapXCount + " x " + mapYCount, 10, 40);
		}

		// 夜晚模式
		if (Global.CAMERA_NIGHT) {
			dayDark();
		}
		if (isNight) { // 夜晚模式先放這
			g.setColor(night);
			g.fillRect(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
		}
	}

	public void update(Actor actor) { // 想要鏡頭有滑順感(可修)
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
		// 場景物品判定
		for (int i = 0; i < sceneObjects.size(); i++) {
			if (actor.isCollision(sceneObjects.get(i)) && sceneObjects.get(i).getPicture() != Global.GROUND_ROAD
					&& sceneObjects.get(i).getPicture() != Global.GROUND_TURF_LADDER) {
				// 移動判定
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
				// 對話判定
				switch (sceneObjects.get(i).getPicture()) {
				case Global.GROUND_WATER:
					dialogue = "好美的蓮花池！(回復魔力)";
					yesNo = true;
					break;
				case Global.GROUND_TREE:
					dialogue = "芳草萋萋、鬱鬱蔥蔥！";
					yesNo = false;
					break;
				case Global.GROUND_WATERPOOL:
					dialogue = "氣勢磅礡的瀑布！";
					yesNo = false;
					break;
				case Global.GROUND_TURF_WALL:
					dialogue = "有些峭壁還纏繞著藤蔓！";
					yesNo = false;
					break;
				case Global.GROUND_TURF_LADDER:
					dialogue = "階梯上長滿了青苔！";
					yesNo = false;
					break;
				case Global.GROUND_CHAIR:
					dialogue = "供人歇息的長椅，可惜開發者不讓我坐QQ！";
					yesNo = false;
					break;
				case Global.GROUND_STATUE1:
					dialogue = "氣勢非凡的飛龍雕像！";
					yesNo = false;
					break;
				case Global.GROUND_FRUIT:
					dialogue = "這些水果看起來好好吃！";
					yesNo = false;
					break;
				case Global.GROUND_GOODS:
				case Global.GROUND_BOXES:
					dialogue = "這裡堆放著雜物！";
					yesNo = false;
					break;
				case Global.GROUND_STATUE2:
					dialogue = "女神雕像面露著祥和平靜的神情！";
					yesNo = false;
					break;
				case Global.GROUND_EQUIPMENT1:
				case Global.GROUND_EQUIPMENT2:
				case Global.GROUND_EQUIPMENT3:
					dialogue = "好多屌炸天的炫酷裝備！";
					yesNo = false;
					break;
				case Global.GROUND_BONE:
					dialogue = "不知名生物的殘骸，令人毛骨悚然！";
					yesNo = false;
					break;
				case Global.GROUND_PROP1:
				case Global.GROUND_PROP2:
				case Global.GROUND_PROP3:
				case Global.GROUND_PROP4:
					dialogue = "好多神神秘秘的古怪道具！";
					yesNo = false;
					break;
				case Global.GROUND_GROSS:
					dialogue = "教堂標誌性的十字架標誌！";
					yesNo = false;
					break;
				case Global.GROUND_GRAVE:
					dialogue = "Rest In Peace……";
					yesNo = false;
					break;
				case Global.GROUND_OBSTACLE_CHURCH:
					dialogue = "這裡是教堂！(可進入)";
					yesNo = true;
					break;
				case Global.GROUND_OBSTACLE_EQUIPMENT:
					dialogue = "這裡是裝備店！(可進入)";
					yesNo = true;
					break;
				case Global.GROUND_OBSTACLE_PROP:
					dialogue = "這裡是道具店！(可進入)";
					yesNo = true;
					break;
				case Global.GROUND_OBSTACLE_STORE:
					dialogue = "這裡是商店！(可進入)";
					yesNo = true;
					break;
				case Global.GROUND_OBSTACLE_CENTER:
					dialogue = "這裡是村長家！(可進入)";
					yesNo = true;
					break;
				case Global.GROUND_OBSTACLE_HOME:
					dialogue = "這裡是村民給我暫住的家！(可進入)";
					yesNo = true;
					break;
				}
			}
			// 對話回復初始
			if (!actor.isCollision(sceneObjects.get(i)) && delayCounter.update()) {
				dialogue = "開發者說我是雞，我就是隻雞！";
				yesNo = false;
			}
		}
		// 場景覆蓋物判定
		for (int i = 0; i < coverObjects.size(); i++) {
			if (actor.isCollision(coverObjects.get(i))) {
				yesNo = false;
				// 對話判定
				switch (coverObjects.get(i).getPicture()) {
				case Global.GROUND_SKYTREE:
					dialogue = "遮天蓋地的天空樹！";
					break;
				case Global.GROUND_TENT:
					dialogue = "是個帳篷，不要隨便亂闖進去好了！";
					break;
				case Global.GROUND_SHELF:
					dialogue = "愜意又涼爽的休息處！";
					break;
				case Global.GROUND_HOUSE:
					dialogue = "房子蓋的很堅固安全！";
					break;
				}
			}
			// 對話回復初始
			if (!actor.isCollision(sceneObjects.get(i)) && delayCounter.update()) {
				dialogue = "開發者說我是雞，我就是隻雞！";
				yesNo = false;
			}
		}
	}

	private void checkCollision(Actor actor) {
		// 場景物品判定
		for (int i = 0; i < sceneObjects.size(); i++) {
			if (actor.isCollision(sceneObjects.get(i)) && sceneObjects.get(i).getPicture() != Global.GROUND_ROAD) {
				// 移動碰撞判定
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
		// 移動NPC判定
		if (actor.isCollision(this.actor)) {
			switch (actor.getPicture()) {
			case Global.BUTTERFLY:
				dialogue = "蝴蝶～蝴蝶～生的真美麗！";
				yesNo = false;
				break;
			case Global.SLIME:
				dialogue = "新手村必刷的史萊姆！(可攻擊)";
				yesNo = true;
				break;
			case Global.NIGHT_MONSTER:
				dialogue = "可怕的黑影怪物！(可攻擊)";
				yesNo = true;
				break;
			case Global.NIGHT_SOLDIER:
				dialogue = "保衛村莊安全的無名英雄！";
				yesNo = false;
				break;
			}
			if (actor.getPicture() >= 80 && actor.getPicture() < 110) {
				dialogue = "純樸可愛的村民們！";
				yesNo = false;
			}
		}
		// 對話回復初始
		if (!actor.isCollision(this.actor) && delayCounter.update()) {
			dialogue = "開發者說我是雞，我就是隻雞！";
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
			g.drawString("按enter繼續", 840, 630);
			ImageController irc = ImageController.getInstance(); // 不知道放這裡好不好，先放這
			BufferedImage img = irc.tryGetImage(PathBuilder.getImg(ImagePath.Bird.Facial.B0));
			g.drawImage(img, 50, 380, 50 + 272, 380 + 288, 0, 0, 272, 288, null);

			if (this.yesNo) {
				g.drawString("是", 680, 600);
				g.drawString("否", 720, 600);
				g.drawRect((yesNo ? 670 : 710), 580, 30, 30);
			}
		}
	}

}
