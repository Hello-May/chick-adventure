package Scenes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import Controllers.StoryController;
import GameObject.Actor;
import GameObject.GameObject;
import GameObject.SceneObject;
import Utils.DelayCounter;
import Utils.Global;

public class Camera {
	private static StoryController storyController = StoryController.getInstance();
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
	private ArrayList<SceneObject> groundObjects;
	private ArrayList<SceneObject> coverObjects;
	private int x2; // 給白線用的東西
	private int y2;
	private GameObject currentCollision; // 沒碰撞的話 他要設回null
	private boolean isCollision;
	private boolean isRain; // 以下是天氣的東西
	private DelayCounter delayRain;
	private DelayCounter delayCloud;
	private ArrayList<Point> arr;
	private Color dark;
	private Color darkRed;
	private Color rain;
	private Color cloud;

	public Camera(Actor actor, int x, int y, int mapXCount, int mapYCount) {
		sceneHelper = new SceneHelper();
		this.actor = actor;
		this.x = x;
		this.y = y;
		this.mapXCount = mapXCount;
		this.mapYCount = mapYCount;
		currentCollision = null;
		update(this.actor);
		isCollision = false;
		delayRain = new DelayCounter(2);
		delayCloud = new DelayCounter(1);
		arr = new ArrayList<>();
		dark = new Color(0, 0, 0, 100);
		darkRed = new Color(139, 0, 0, 100);
		cloud = new Color(255, 255, 255, 5);
		rain = new Color(0x9391d6);
		if (Global.DEBUG) {
			isRain = Global.RAINING;
		} else {
			isRain = false;
		}
		for (int i = 0; i < 30; i++) { // 30朵雲
			arr.add(new Point((int) (Math.random() * Global.SCREEN_WIDTH),
					(int) (Math.random() * Global.SCREEN_HEIGHT)));
		}
	}

	public GameObject currentCollision() {
		if (!isCollision) {
			currentCollision = null;
		}
		isCollision = false;
		return currentCollision;
	}

	public void setIsRain(boolean isRain) {
		this.isRain = isRain;
	}

	public boolean getIsRain() {
		return this.isRain;
	}

	public void setObjectsAndOthers(int[][] groundLocation, ArrayList<SceneObject> sceneObjects,
			ArrayList<SceneObject> coverObjects, ArrayList<Actor> others) {
		this.groundLocation = groundLocation;
		this.sceneObjects = sceneObjects;
		this.coverObjects = coverObjects;
		this.others = others;
	}

	public void setObjectsAndOthers(ArrayList<SceneObject> groundObjects, ArrayList<SceneObject> sceneObjects,
			ArrayList<SceneObject> coverObjects, ArrayList<Actor> others) {
		this.groundObjects = groundObjects;
		this.sceneObjects = sceneObjects;
		this.coverObjects = coverObjects;
		this.others = others;
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
//		for(int i=0;i<groundObjects.size();i++) {
//			if (groundObjects.get(i) != null) {
//				groundObjects.get(i).paint(g, x, y);
//			}
//		}

		// 印白線
		if (Global.DEBUG && Global.LINE) {
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
			if (sceneObjects.get(i) != null) {
				sceneObjects.get(i).paint(g, x, y);
			}
		}

		// 印配角others
		for (int i = 0; i < others.size(); i++) {
			if (others.get(i) != null) {
				others.get(i).paint(g, x, y);
			}
		}

		// 印主角actor
		actor.paint(g, x, y);

		// 印覆蓋物coverObjects
		for (int i = 0; i < coverObjects.size(); i++) {
			if (storyController.getSF1() != 15 && (coverObjects.get(i).getPicture() == Global.GROUND_FIRE
					|| coverObjects.get(i).getPicture() == Global.GROUND_SMALL_FIRE)) {
				continue;
			}
			coverObjects.get(i).paint(g, x, y);
		}

		// 如果沒下雨 (飄雲)
		if (!isRain) {
			g.setColor(cloud);
			for (int i = 0; i < arr.size(); i++) {
				if (arr.get(i) != null) {
					g.fillOval(arr.get(i).x, arr.get(i).y, 70, 70);
					g.fillOval(arr.get(i).x - 30, arr.get(i).y, 70, 70);
					g.fillOval(arr.get(i).x - 60, arr.get(i).y - 5, 80, 80);
					g.fillOval(arr.get(i).x - 90, arr.get(i).y - 5, 80, 80);
					g.fillOval(arr.get(i).x - 120, arr.get(i).y, 70, 70);
					g.fillOval(arr.get(i).x - 150, arr.get(i).y, 70, 70);
					g.fillOval(arr.get(i).x - 55, arr.get(i).y + 25, 70, 70);
					g.fillOval(arr.get(i).x - 55, arr.get(i).y - 25, 70, 70);
					g.fillOval(arr.get(i).x - 110, arr.get(i).y + 25, 70, 70);
					g.fillOval(arr.get(i).x - 110, arr.get(i).y - 25, 70, 70);
					g.fillOval(arr.get(i).x - 165, arr.get(i).y, 70, 70);
					g.fillOval(arr.get(i).x - 100, arr.get(i).y - 15, 100, 100);
					g.fillOval(arr.get(i).x - 40, arr.get(i).y - 10, 90, 90);
					g.fillOval(arr.get(i).x - 150, arr.get(i).y - 10, 90, 90);
				}
			}
		}

		// 如果下雨
		if (isRain) {
			if (storyController.getSF1() >= 14) {
				g.setColor(darkRed);
				g.fillRect(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
			}
			g.setColor(dark);
			g.fillRect(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
			if (storyController.getSF1() >= 14) {
				g.setColor(Color.RED);
			} else {
				g.setColor(rain);
			}
			if (arr != null) {
				for (int i = 0; i < arr.size(); i++) {
					if (arr.get(i) != null) {
						((Graphics2D) g).setStroke(new BasicStroke(2));
						g.drawLine(arr.get(i).x, arr.get(i).y, arr.get(i).x + 2, arr.get(i).y + 5);
					}
				}
			}
		}

		if (Global.DEBUG) {
			// 鏡頭座標
			g.setColor(Color.BLUE);
			g.drawString("鏡頭: " + x + "," + y, 10, 20);

			// 地圖大小
			g.setColor(Color.RED);
			g.drawString("地圖: " + mapXCount + " x " + mapYCount, 10, 40);
		}
	}

	public void update(Actor actor) {
		checkMainCollision(actor);
		setX(actor.getX() - (Global.WINDOWS_WIDTH / 2 - Global.CHICK_SIZE_X / 2));
		setY(actor.getY() - (Global.WINDOWS_HEIGHT / 2 - Global.CHICK_SIZE_Y / 2));
		this.actor = actor;

		if (isRain && delayRain.update()) { // 下雨
			if (arr != null) {
				arr.clear();
				for (int i = 0; i < 300; i++) {
					arr.add(new Point((int) (Math.random() * Global.SCREEN_WIDTH),
							(int) (Math.random() * Global.SCREEN_HEIGHT)));
				}
			}
		}
		if (!isRain && delayCloud != null && delayCloud.update()) { // 雲飄動
			for (int i = 0; i < arr.size(); i++) {
				arr.get(i).setLocation(arr.get(i).x - 1, arr.get(i).y + (int) ((Math.random() * 10) - 5));
				if (arr.get(i).x < 0) {
					arr.set(i, new Point(Global.SCREEN_WIDTH + 150, (int) (Math.random() * Global.SCREEN_HEIGHT)));
				}
			}
		}
	}

	private void checkMainCollision(Actor actor) {
		// 場景覆蓋物判定
		for (int i = 0; coverObjects != null && i < coverObjects.size(); i++) {
			if (actor.isCollision(coverObjects.get(i))) {
				if (coverObjects.get(i).getPicture() == Global.GROUND_FIRE
						|| coverObjects.get(i).getPicture() == Global.GROUND_SMALL_FIRE) {
					continue;
				}
				currentCollision = coverObjects.get(i);
				isCollision = true;
			}
		}

		// 配角判定
		for (int i = 0; others != null && i < others.size(); i++) {
			if (others.get(i) != null) {
				if (others.get(i).getIsDead()) {
					others.set(i, null);
				} else {
					checkCollision(others.get(i));
				}
			}
		}

		// 場景物品判定
		for (int i = 0; sceneObjects != null && i < sceneObjects.size(); i++) {
			if (sceneObjects.get(i) == null) {
				continue;
			}
			if (actor.isCollision(sceneObjects.get(i)) && sceneObjects.get(i).getPicture() != Global.GROUND_ROAD
					&& sceneObjects.get(i).getPicture() != Global.GROUND_TURF_LADDER
					&& sceneObjects.get(i).getPicture() != Global.GROUND_BRIDGE) {
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
				currentCollision = sceneObjects.get(i);
				isCollision = true;
			}
		}
	}

	private void checkCollision(Actor actor) {
		// 移動NPC判定
		if (actor.isCollision(this.actor)) {
			currentCollision = actor;
			isCollision = true;
		}

		// 場景物品判定
		for (int i = 0; i < sceneObjects.size(); i++) {
			if (sceneObjects.get(i) == null) {
				continue;
			}
			if (actor.getPicture() != Global.MONSTER) {
				if ((sceneObjects.get(i).getPicture() < -1 ? actor.isCollision(sceneObjects.get(i), 32)
						: actor.isCollision(sceneObjects.get(i)))
						&& sceneObjects.get(i).getPicture() != Global.GROUND_ROAD) {
//			if (actor.isCollision(sceneObjects.get(i)) && sceneObjects.get(i).getPicture() != Global.GROUND_ROAD) {
					// 移動碰撞判定
					switch (actor.getDirection()) {
					case Global.UP:
						actor.setY(
								sceneObjects.get(i).getBottom() + (sceneObjects.get(i).getPicture() < -1 ? 32 : 0) + 1);
						break;
					case Global.LEFT:
						actor.setX(
								sceneObjects.get(i).getRight() + (sceneObjects.get(i).getPicture() < -1 ? 32 : 0) + 1);
						break;
					case Global.DOWN:
						actor.setY(sceneObjects.get(i).getTop() - (sceneObjects.get(i).getPicture() < -1 ? 32 : 0)
								- Global.ACTOR_SIZE - 1);
						break;
					case Global.RIGHT:
						actor.setX(sceneObjects.get(i).getLeft() - (sceneObjects.get(i).getPicture() < -1 ? 32 : 0)
								- Global.ACTOR_SIZE - 1);
						break;
					}
				}
			}
			if (actor.getPicture() == Global.MONSTER) {
				if (actor.isCollision(sceneObjects.get(i)) && sceneObjects.get(i).getPicture() != Global.GROUND_ROAD) {
					switch (actor.getDirection()) {
					case Global.UP:
						actor.setY(sceneObjects.get(i).getBottom() + 1);
						break;
					case Global.LEFT:
						actor.setX(sceneObjects.get(i).getRight() + 1);
						break;
					case Global.DOWN:
						actor.setY(sceneObjects.get(i).getTop() - 60 - 1);
						break;
					case Global.RIGHT:
						actor.setX(sceneObjects.get(i).getLeft() - 60 - 1);
						break;
					}
				}
			}

		}
	}

}
