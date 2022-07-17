package GameObject;

import Utils.DelayCounter;
import Utils.Global;

public abstract class MovableGameObject extends GameObject {
	protected int speed;
	protected int step;
	protected static final int[] ACT = { 0, 1, 2, 1 };
	protected int direction;
	protected ActorHelper actorHelper;
	protected DelayCounter delayCounter;
	protected int act;
	protected int delay;

	public MovableGameObject(int x, int y, int width, int height, int speed) {
		super(x, y, width, height);
		setSpeed(speed);
	}

	public MovableGameObject(int x, int y, int width, int height, int speed, int delay) {
		super(x, y, width, height);
		setSpeed(speed);
		direction = Global.DOWN;
		delayCounter = new DelayCounter(delay);
	}

	public final void setSpeed(int speed) {
		this.speed = speed * Global.ACT_SPEED;
	}

	public int getStep() {
		return this.step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void changeDirection(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return this.direction;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getDelay() {
		return this.delay;
	}

	public abstract void move();

	public abstract void randomMove();

	public void useSkill(Actor target) {

	}

}
