package GameObject;

import Utils.DelayCounter;
import Utils.Global;

public abstract class MovableGameObject extends GameObject {
	public interface Skill {
		public void skill(Actor actor, Actor target);

		public String getName();
	}

	public static class Heal implements Skill {
		public void skill(Actor actor, Actor target) {
			if (actor.getCurrentHp() < actor.getHp()) {
				actor.setCurrentHp(actor.getCurrentHp() + actor.getAtk());
				System.out.println("µo°Ê¸É¦å§Þ¯à" + target.getAtk() + "hp");
//				paintSkill(new Graphics(), target);
			} else {
				System.out.println("¦å¼Ñ¤wº¡¡A¤£»Ý¸É¦å");
			}
		}

		public String getName() {
			return "¸É¦å";
		}
	}

	public static class Attack implements Skill {
		public void skill(Actor actor, Actor target) {
//			target.hp -= actor.atk;
			target.setCurrentHp(target.getCurrentHp() - actor.getAtk());
		}

		public String getName() {
			return "´¶§ð";
		}
	}

	public static class Critrcal implements Skill {
		public void skill(Actor actor, Actor target) {
			target.hp -= actor.atk * 1.5;
		}

		public String getName() {
			return "ÃzÀ»";
		}
	}

	private int speed;
	protected int hp;
	protected int mp;
	protected int atk;
	protected int def;
	protected int currentHp;
	protected int currentMp;
	protected Skill[] skills;
	protected int state;
	protected int level;
	protected int exp;

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

	public MovableGameObject(int x, int y, int width, int height, int speed,int delay) {
		super(x, y, width, height);
		setSpeed(speed);
		state = Global.HEALTH;
		act = exp = 0;
		level = 1;
		direction = Global.DOWN;
		delayCounter = new DelayCounter(delay);
	}

	public final void setSpeed(int speed) {
		this.speed = speed * Global.ACT_SPEED;
	}

//	public void paintSkill(Graphics g, Actor target) {
//		int x2 = 0;
//		int y2 = 0;
//		for (int i = 0; i < 3; i++) {
//			x2 += 10;
//			y2 += 10;
//			g.drawString(target.getAtk() + "hp", x + x2, y - y2);
//		}
//	}

	public int getSpeed() {
		return this.speed;
	}

	public int getHp() {
		return this.hp;
	}

	public int getMp() {
		return this.mp;
	}

	public int getLevel() {
		return this.level;
	}

	public int getExp() {
		return this.exp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void setMp(int mp) {
		this.mp = mp;
	}

	public void stLevel(int level) {
		this.level = level;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getCurrentHp() {
		return this.currentHp;
	}

	public int getCurrentMp() {
		return this.currentMp;
	}

	public void setCurrentHp(int currentHp) {
		this.currentHp = currentHp;
	}

	public void setCurrentMp(int currentMp) {
		this.currentMp = currentMp;
	}

	public int getAtk() {
		return this.atk;
	}

	public int getDef() {
		return this.def;
	}

	public String getStateName() {
		switch (this.state) {
		case Global.HEALTH:
			return "°·±d";
		case Global.WEAK:
			return "µê®z";
		}
		return null;
	}

	public int getState() {
		return this.state;
	}

	public Skill[] getSkills() {
		return this.skills;
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
