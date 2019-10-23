package GameObject;

import fighting.Job;
import fighting.Status;
import GameObject.MovableGameObject;
import java.awt.Graphics;
import Utils.DelayCounter;
import Utils.Global;
import static Utils.Global.*;

public class Chicken extends MovableGameObject {

	private static final int[] ACT = { 0, 1, 2, 1 };
	private int act;
	private int delay;
	private int direction;
	private ChickenHelper chickenHelper;
	private DelayCounter movedDelay;

	private Status status;
	private Job job;

	public Chicken(int x, int y, int width, int height, int speed, Status status, Job job) {
		super(x, y, width, height, speed);
		delay = act = 0;
		direction = DOWN;
		movedDelay = new DelayCounter(5);
		chickenHelper = new ChickenHelper();
		this.status = status;
		this.job = job;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void changeDirection(int direction) {
		this.direction = direction;
	}

	@Override
	public void move() { 
		if (movedDelay.update()) {
			act = ++act % 4; 
		}
		if (direction == 1) {
			this.x -= 0.5 * Global.ACT_SPEED;
			if (this.x == 100) {
				changeDirection(2);
			}
		}
		if (direction == 2) {
			this.x += 0.5 * Global.ACT_SPEED;
			if (this.x == 720) {
				changeDirection(0);
			}
		}
	}

	public void attack() {
		this.direction = Global.LEFT;

	}

//    public void fire() { 
//        this.direction = Global.DOWN;
//    }
	@Override
	public void paint(Graphics g) {
		chickenHelper.paint(g, x, y, width, height, ACT[act], direction);
	}

	@Override
	public void randomMove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics g, int cx, int cy) {
		// TODO Auto-generated method stub

	}
}
