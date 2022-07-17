package GameObject;

import fighting.SkillList;
import fighting.Status;
import GameObject.MovableGameObject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import Utils.DelayCounter;
import static Utils.Global.*;

public class BattleActor extends MovableGameObject {

	private static final int[] ACT = { 0, 1, 2, 1 };
// private static final int[] HAPPY = { 0, 1, 2, 3, 4, 5, 6, 7 };
// private static final int[] CRY = { 0, 1, 2, 3, 4, 5, 6, 7 };
// private static final int[] NORMAL = { 0, 1, 2, 3, 4, 5, 6, 7 };
// private static final int[] SKILL = { 0, 1, 2, 3, 4, };// �Ҧ����ޯೣ����
	private int act;
	private int delay;
	private int direction;
	private ActorHelper actorHelper;
	private DelayCounter movedDelay;
	private Status status;
	private SkillList.Skill[] skills;

	private int mood;// �]�w�T�ت���
	private int count;// ����
	private int skillCountX;
	private int skillCountY;
	private DelayCounter skillDelay;// �ĤH�ϥΧ������ɶ�
	private int mpIndication;// MP����
	private int emenyAttact;
	private int useSkillIndex;
	private DelayCounter turnDelay;

	public BattleActor(int x, int y, int width, int height, int speed, Status status, int picture) {
		super(x, y, width, height, speed);
		delay = act = count = skillCountX = skillCountY = emenyAttact = 0;
		direction = DOWN;
		turnDelay = new DelayCounter(LIMIT_DELTA_TIME * 5);
		movedDelay = new DelayCounter(5);
		skillDelay = new DelayCounter(LIMIT_DELTA_TIME * 7, true);// LIMIT_DELTA_TIME�e3�i�A�ޯ���`�@25�i
		actorHelper = new ActorHelper(picture);
		this.status = status;
		skills = new SkillList.Skill[] { new SkillList.Attack(), new SkillList.FireBall(), new SkillList.Curse(),
				new SkillList.Summon() };
		this.mood = -2;
		this.mpIndication = -1;
		useSkillIndex = -1;
	}

// public BattleActor(Actor actor, int speed) {
//  super(actor.x, actor.y, actor.width, actor.height, speed);
//  delay = act = 0;
//  direction = DOWN;
//  movedDelay = new DelayCounter(5);
//  skillDelay = new DelayCounter(LIMIT_DELTA_TIME * 7, true);
//  actorHelper = new ActorHelper(actor.getPicture());
//  status = actor.getStatus();
//  this.mood = 0;
//
// }

// public BattleActor(Actor actor, int speed, int picture) {
//  super(actor.x, actor.y, actor.width, actor.height, speed);
//  delay = act = count = skillCountX = skillCountY = 0;
//  emenyAttact = 0;
//  direction = DOWN;
//  movedDelay = new DelayCounter(5);
//  skillDelay = new DelayCounter(LIMIT_DELTA_TIME * 6, true);
//  actorHelper = new ActorHelper(picture);
//  status = actor.getStatus();
//  this.mood = 0;
//  skills = new SkillList.Skill[] { new SkillList.Attack(), new SkillList.FireBall(), new SkillList.Curse(),
//    new SkillList.Summon() };
// }
//
// public SkillList.Skill getSkill(int index) {
//  return skills[index];
// }
	public void setUseSkillIndex(int useSkillIndex) {
		this.useSkillIndex = useSkillIndex;
	}

	public void useSkill(BattleActor target, int skill) {
		this.skills[skill].action(this, target);
	}

	public Status getStatus() {
		return status;
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
			count = ++count % 8;
			skillCountX = ++skillCountX % 5;// 0 1 2 3 4
			if (skillCountX == 4) {
				skillCountY = ++skillCountY % 5;// 1 2 3 4 0
			}
		}

		if (direction == 1) {
			if (useSkillIndex == 0) {
				this.x -= 1 * getSpeed();
				if (this.x == 100) {
					changeDirection(2);
				}
			}
			if ((useSkillIndex == 1 || useSkillIndex == 2 || useSkillIndex == 3) && (mood == 8 || mood == 1)) {
				if (x >= 620) {
					this.x -= 1 * getSpeed();
				} else {
					if (turnDelay.update()) {
						changeDirection(2);
					}

				}
			}

		}

		if (direction == 2 && (mood == 0 || mood == 8 || mood == 1)) {
			this.x += 1 * getSpeed();
			if (this.x == 720) {
				changeDirection(0);
				resetSkillCount();
				useSkillIndex = -1;
				mood = -2;
			}
		}
		if (skillDelay.updatePause()) {
			actorHelper.setPaintSkillIndex(-1);// ���e�ޯ�F
			skillDelay.stop();
			this.emenyAttact = 1;// ���ĤH����
		}
	}

	public void setHp(int minus) {
		status.setAttackHP(minus);
	}

	public void setMood(int mood) {
		this.mood = mood;
	}

	public void paintEmenyAttack(Graphics g) {
		if (this.direction == 2 && this.x > 720) {
			actorHelper.paintSkill(g, x, y, width, height, skillCountX, skillCountY);
		}
		if (this.direction == 2 && useSkillIndex == 2 && this.x == 304) {
			actorHelper.paintSkill(g, x, y, width, height, skillCountX, skillCountY);
		}

	}

	@Override
	public void paint(Graphics g) {
		actorHelper.paint(g, x, y, width, height, ACT[act], direction);
		if (this.direction == 1 && this.x < 200) {
			setPaintSkillIndex(0);// �p�G�g�bBattleScene�|��]��e�ܩ�
			// resetSkillCount();//�g�o��u�|�T�w�e�e�X�i��
			actorHelper.paintSkill(g, x, y, width, height, skillCountX, skillCountY);
			// SKILL[skillCountX]�S�t�O ���|���@�˪���
		}

		if ((useSkillIndex == 1 || useSkillIndex == 2 || useSkillIndex == 3)) {
			if (mood == 1) {
				actorHelper.paint(g, x, y, width, height, count, direction, 906);
				if (x == 616) {
					actorHelper.paintSkill(g, x, y, width, height, skillCountX, skillCountY);
				}

				// HAPPY[count]

			}
		}

		if (mood == -1) {// CRY[count]
			actorHelper.paint(g, x, y, width, height, count, direction, 907);
		}

		if (mood == 0) {// NORMAL[count]
			actorHelper.paint(g, x, y, width, height, count, direction, 905);
		}

		if (mood == 8) {// �e�ͮ𪺹�
			actorHelper.paint(g, x, y, width, height, count, direction, 908);
		}
		if (mpIndication == 1) {
			String s = "�k�O�����I�Ѿl : " + status.getCurrentMP();
			g.setColor(Color.black);
			g.setFont(new Font("�L�n������", Font.BOLD, 20));
			g.drawString(s, 435, 450);
		}
	}

	@Override
	public void randomMove() {
	}

	@Override
	public void paint(Graphics g, int cx, int cy) {
	}

	public void setPaintSkillIndex(int skillIndex) {// ��J��e�n�e���ޯ�
		actorHelper.setPaintSkillIndex(skillIndex);
	}

	public void resetSkillCount() {
		this.skillCountX = 0;
		this.skillCountY = 0;
	}

	public void startSkillDelay() {// �Ǫ��}�l����
		skillDelay.start();
	}

	public void setMpIndication(int mp) {// �e�k�O������
		this.mpIndication = mp;
	}

	public boolean IsEmenyAttact() {
		if (this.emenyAttact == 1) {
			return true;
		}
		return false;
	}

	public DelayCounter getSkillDelay() {
		return this.skillDelay;
	}

	public void emenyMove() {
		if (movedDelay.update()) {
			act = ++act % 4;
			count = ++count % 8;
			skillCountX = ++skillCountX % 5;// 0 1 2 3 4
			if (skillCountX == 4) {
				skillCountY = ++skillCountY % 5;// 1 2 3 4 0
			}
		}
		if (direction == 2) {

			if (useSkillIndex == 0 || useSkillIndex == 1) {
				x += 1 * getSpeed();
				if (x == 820) {
					changeDirection(1);
				}
			}
			if (useSkillIndex == 2) {
				if (x <= 300) {
					x += 1 * getSpeed();
				} else {
					if (turnDelay.update()) {
						changeDirection(1);
					}
				}

			}
		}

		if (direction == 1) {
			x -= 1 * getSpeed();

			if (x == 200) {
				changeDirection(0);
				resetSkillCount();
				useSkillIndex = -1;
				this.mood = -2;
			}
		}
	}

	public int getMood() {
		return this.mood;
	}

	public void disActor() {
		super.width = 0;
		super.height = 0;

	}

	@Override
	public int getDirection() {
		return this.direction;
	}

	public int getUseSkillIndex() {
		return this.useSkillIndex;
	}
}