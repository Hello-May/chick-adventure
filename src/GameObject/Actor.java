package GameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import Utils.ActorRecord;
import Utils.DelayCounter;
import Utils.Global;
import fighting.Status;

public class Actor extends MovableGameObject {
	static final long serialVersionUID = 111145l;
	private DelayCounter fly;
	private Status status;
	private int mood;// 設定三種表情用
	private int count;// 表情用
	private boolean isDead;
	private boolean hasPotLid;
	private int hasBoon;
	private boolean hasCross;
	private int fixedDirection;
	private int r;

	public Actor(ActorRecord record) {
		this(record.x, record.y, record.width, record.height, record.speed, record.actor, record.delay, record.status);
		direction = record.direction;
	}

	public ActorRecord genRecord() {
		return new ActorRecord(x, y, width, height, speed, picture, delay, status, direction);
	}

	public Actor(int x, int y, int width, int height, int speed, int actor, int delay, Status status) {
		super(x, y, width, height, speed, delay);
		fly = new DelayCounter(delay * 2);
		this.delay = delay;
		act = 0;
		direction = Global.DOWN;
		picture = actor;
		actorHelper = new ActorHelper(picture);
		this.status = status;
		step = Global.ACTOR_STEP;
		name = Global.CHICK_NAME;
		count = 0;
		mood = -2;
		isDead = false;
		fixedDirection = 4; // 沒有固定方向的意思
		if (Global.DEBUG) {
			hasPotLid = Global.HAS_POTLID;
			hasBoon = Global.HAS_BOON;
			hasCross = Global.HAS_CROSS;
		} else {
			hasPotLid = false;
			hasCross = false;
			hasBoon = 0;
		}
	}

	public int getFixedDirection() {
		return this.fixedDirection;
	}

	public void setFixedDirection(int fixedDirection) {
		changeDirection(fixedDirection);
		this.fixedDirection = fixedDirection;
	}

	public void setHasCross(boolean hasCross) {
		this.hasCross = hasCross;
	}
	
	public boolean getHasCross() {
		return this.hasCross;
	}

	public void setHasPotLid(boolean hasPotLid) {
		this.hasPotLid = hasPotLid;
	}

	public void IncreaseHasBoon() {
		hasBoon++;
	}

	public void setHasBoon(int hasBoon) {
		this.hasBoon = hasBoon;
	}

	public boolean getHasPotLid() {
		return this.hasPotLid;
	}

	public int getHasBoon() {
		return this.hasBoon;
	}

	public void setIsDead(boolean isDead) {
		this.isDead = isDead;
	}

	public boolean getIsDead() {
		return this.isDead;
	}

	public Status getStatus() {
		return this.status;
	}

	@Override
	public void move() {
		if (fly.update()) {
			if (mood != -2) {
				count = ++count % 8;
			}
			act = ++act % 4;
		}
		if (delayCounter.update()) {
			switch (direction) {
			case Global.UP:
				y -= getStep() * getSpeed();
				break;
			case Global.LEFT:
				x -= getStep() * getSpeed();
				break;
			case Global.DOWN:
				y += getStep() * getSpeed();
				break;
			case Global.RIGHT:
				x += getStep() * getSpeed();
				break;
			}
		}
	}

	@Override
	public void randomMove() {
		if (delayCounter.update()) {
			if (mood != -2) {
				count = ++count % 8;
			}
			act = ++act % 4;
			if (fixedDirection == 4) {
				r = (int) (Math.random() * 6);
				if (r >= 4) {
					return;
				}
				changeDirection(r);
			} else {
				r = fixedDirection;
			}
			switch (r) {
			case Global.UP:
				y -= getStep() * getSpeed();
				break;
			case Global.LEFT:
				x -= getStep() * getSpeed();
				break;
			case Global.DOWN:
				y += getStep() * getSpeed();
				break;
			case Global.RIGHT:
				x += getStep() * getSpeed();
				break;
			}
		}
	}

	public void followMove(Actor actor) {
		if (delayCounter.update()) {
			if (mood != -2) {
				count = ++count % 8;
			}
			act = ++act % 4;
			if (isCollision(actor)) {
				return;
			} else {
				if ((int) (Math.random() * 2) == 0) {
					r = (actor.getX() > getX() ? Global.RIGHT : Global.LEFT);
				} else {
					r = (actor.getY() > getY() ? Global.DOWN : Global.UP);
				}
				changeDirection(r);
				switch (r) {
				case Global.UP:
					y -= getStep() * getSpeed() * 2;
					break;
				case Global.LEFT:
					x -= getStep() * getSpeed() * 2;
					break;
				case Global.DOWN:
					y += getStep() * getSpeed() * 2;
					break;
				case Global.RIGHT:
					x += getStep() * getSpeed() * 2;
					break;
				}
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		actorHelper.paint(g, x, y, width, height, ACT[act], direction);

		if (Global.DEBUG) {
			// 印座標
			g.setColor(Color.YELLOW);
			g.drawString(x + "," + y, x - 6, y - 3);
		}

		switch (mood) {
		case 1:
			actorHelper.paintMood(g, x, y - 25, width, height, count, direction, 906);
			// HAPPY[count]
			break;
		case -1:
			actorHelper.paintMood(g, x, y - 25, width, height, count, direction, 907);
			// CRY[count]
			break;
		case 0:
			actorHelper.paintMood(g, x, y - 25, width, height, count, direction, 905);
			// NORMAL[count]
			break;
		}
	}

	public void update() {
		if (delayCounter.update()) {
			count = ++count % 8;
		}
	}

	public void setMood(int mood) {
		this.mood = mood;
	}

	@Override
	public void paint(Graphics g, int cx, int cy) {
		actorHelper.paint(g, x - cx, y - cy, width, height, ACT[act], direction);

		if (Global.DEBUG) {
			// 印座標
			g.setColor(Color.YELLOW);
			g.drawString(x + "," + y, (x - cx - 6), (y - cy - 3));
		}

		switch (mood) {
		case 1:
			actorHelper.paintMood(g, x - cx, y - cy - 25, width, height, count, direction, 906);
			// HAPPY[count]
			break;
		case -1:
			actorHelper.paintMood(g, x - cx, y - cy - 25, width, height, count, direction, 907);
			// CRY[count]
			break;
		case 0:
			actorHelper.paintMood(g, x - cx, y - cy - 25, width, height, count, direction, 905);
			// NORMAL[count]
			break;
		}
	}

}