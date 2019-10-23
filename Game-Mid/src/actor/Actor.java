package GameObject;

import java.awt.Color;
import java.awt.Graphics;

import Utils.DelayCounter;
import Utils.Global;

public class Actor extends MovableGameObject {
	private DelayCounter fly;

	public Actor(int x, int y, int width, int height, int speed, int actor, int delay) {
		super(x, y, width, height, speed, delay);
		fly = new DelayCounter(delay*2);
		picture = actor;
		actorHelper = new ActorHelper(picture);
		name = "無名氏";
		hp = currentHp = 50;
		mp = currentMp = 20;
		atk = 5;
		def = 2;
		skills = new Skill[] { new Heal(), new Attack(), new Critrcal() };
	}

	@Override
	public void useSkill(Actor target) {
//		skills[(int) (Math.random() * skills.length)].skill(this, target);	
		skills[0].skill(this, target);
//		if (isCollision(target)) {
//				
//			
//		} else {
//			
//		}
	}

	@Override
	public void move() {
		if (fly.update()) {
			act = ++act % 4; // 1,2,3,0
		}
		if (delayCounter.update()) {
			switch (direction) {
			case Global.UP:
				y -= Global.ACTOR_STEP * getSpeed();
				break;
			case Global.LEFT:
				x -= Global.ACTOR_STEP * getSpeed();
				break;
			case Global.DOWN:
				y += Global.ACTOR_STEP * getSpeed();
				break;
			case Global.RIGHT:
				x += Global.ACTOR_STEP * getSpeed();
				break;
			}
		}
	}

	@Override
	public void randomMove() {
		if (delayCounter.update()) {
			act = ++act % 4; // 1,2,3,0;
			int r = (int) (Math.random() * 6);
			if (r >= 4) {
				return;
			}
			changeDirection(r);
			switch (r) {
			case Global.UP:
				y -= Global.ACTOR_STEP * getSpeed();
				break;
			case Global.LEFT:
				x -= Global.ACTOR_STEP * getSpeed();
				break;
			case Global.DOWN:
				y += Global.ACTOR_STEP * getSpeed();
				break;
			case Global.RIGHT:
				x += Global.ACTOR_STEP * getSpeed();
				break;
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		actorHelper.paint(g, x, y, width, height, ACT[act], direction);
		// 參數後兩個是走路中造型+4種方向的造型

		if (Global.DEBUG) {
			// 印座標
			g.setColor(Color.YELLOW);
			g.drawString(x + "," + y, x - 6, y - 3);
		}
	}

	@Override
	public void paint(Graphics g, int cx, int cy) {
		actorHelper.paint(g, x - cx, y - cy, width, height, ACT[act], direction);
		// 參數後兩個是走路中造型+4種方向的造型

		if (Global.DEBUG) {
			// 印座標
			g.setColor(Color.YELLOW);
			g.drawString(x + "," + y, (x - cx - 6), (y - cy - 3));
		}
	}

}
