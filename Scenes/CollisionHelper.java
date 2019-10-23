package Scenes;

import java.awt.Color;
import java.util.ArrayList;

import GameObject.Actor;
import GameObject.SceneObject;
import Utils.DelayCounter;
import Utils.Global;

public class CollisionHelper {	//想拆、壞掉了
	private Actor actor;
//	private ArrayList<Actor> others; // 路人+敵人
	private ArrayList<SceneObject> sceneObjects;
	private ArrayList<SceneObject> coverObjects;
	private String dialogue;
	private DelayCounter delayCounter; // 想計算對話回復的時間

	public CollisionHelper(ArrayList<SceneObject> sceneObjects, ArrayList<SceneObject> coverObjects,
			ArrayList<Actor> others) {
		this.sceneObjects = sceneObjects;
		this.coverObjects = coverObjects;
//		this.others = others;
		dialogue = "開發者說我是雞，我就是隻雞！";
		delayCounter = new DelayCounter(10000);
	}

	public String getDialogue() {
		return this.dialogue;
	}

	public void checkMainCollision(Actor actor) {
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
					break;
				case Global.GROUND_TREE:
					dialogue = "芳草萋萋、鬱鬱蔥蔥！";
					break;
				case Global.GROUND_WATERPOOL:
					dialogue = "氣勢磅礡的瀑布！";
					break;
				case Global.GROUND_TURF_WALL:
					dialogue = "有些峭壁還纏繞著藤蔓！";
					break;
				case Global.GROUND_TURF_LADDER:
					dialogue = "階梯上長滿了青苔！";
					break;
				case Global.GROUND_CHAIR:
					dialogue = "供人歇息的長椅，可惜開發者不讓我坐QQ！";
					break;
				case Global.GROUND_STATUE1:
					dialogue = "氣勢非凡的飛龍雕像！";
					break;
				case Global.GROUND_FRUIT:
					dialogue = "這些水果看起來好好吃！";
					break;
				case Global.GROUND_GOODS:
				case Global.GROUND_BOXES:
					dialogue = "這裡堆放著雜物！";
					break;
				case Global.GROUND_STATUE2:
					dialogue = "女神雕像面露著祥和平靜的神情！";
					break;
				case Global.GROUND_EQUIPMENT1:
				case Global.GROUND_EQUIPMENT2:
				case Global.GROUND_EQUIPMENT3:
					dialogue = "好多屌炸天的炫酷裝備！";
					break;
				case Global.GROUND_BONE:
					dialogue = "不知名生物的殘骸，令人毛骨悚然！";
					break;
				case Global.GROUND_PROP1:
				case Global.GROUND_PROP2:
				case Global.GROUND_PROP3:
				case Global.GROUND_PROP4:
					dialogue = "好多神神秘秘的古怪道具！";
					break;
				case Global.GROUND_GROSS:
					dialogue = "教堂標誌性的十字架標誌！";
					break;
				case Global.GROUND_GRAVE:
					dialogue = "Rest In Peace……";
					break;
				case Global.GROUND_OBSTACLE_CHURCH:
					dialogue = "這裡是教堂！(進入教堂)";
					break;
				}
			}
			// 對話回復初始
			if (!actor.isCollision(sceneObjects.get(i)) && delayCounter.update()) {
				dialogue = "開發者說我是雞，我就是隻雞！";
			}
		}
		// 場景覆蓋物判定
		for (int i = 0; i < coverObjects.size(); i++) {
			if (actor.isCollision(coverObjects.get(i))) {
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
			}
		}
	}

	public void checkCollision(Actor actor) {
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
				break;
			case Global.SLIME:
				dialogue = "新手村必刷的史萊姆！(可攻擊)";
				break;
			case Global.NIGHT_MONSTER:
				dialogue = "可怕的黑影怪物！(可攻擊)";
				break;
			case Global.NIGHT_SOLDIER:
				dialogue = "保衛村莊安全的無名英雄！";
				break;
			}
			if (actor.getPicture() >= 80 && actor.getPicture() < 110) {
				dialogue = "純樸可愛的村民們！";
			}
		}
		// 對話回復初始
		if (!actor.isCollision(this.actor) && delayCounter.update()) {
			dialogue = "開發者說我是雞，我就是隻雞！";
		}
	}

}
