
package fighting;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

import Utils.Global;

public class Status implements Serializable {

	private int totalHP;
	private int currentHP;
	private int totalMP;
	private int currentMP;
	private int attack;
	private int level;

	public Status(int totalHP, int totalMP, int attack) {
		this.currentHP = this.totalHP = totalHP;
		this.currentMP = this.totalMP = totalMP;
		this.attack = attack;
		this.level = 1;
	}

	public Status cloneAndRefresh() {
		Status status = new Status(totalHP, totalMP, attack);
		return status;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public int getTotalHP() {
		return totalHP;
	}

	public void setTotalHP(int totalHP) {
		this.totalHP = totalHP;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		if (currentHP > totalHP) {
			this.currentHP = totalHP;
			return;
		}
		if (currentHP < 0) {
			this.currentHP = 0;
			return;
		}
		this.currentHP = currentHP;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getTotalMP() {
		return totalMP;
	}

	public void setTotalMP(int totalMP) {
		this.totalMP = totalMP;
	}

	public int getCurrentMP() {
		return currentMP;
	}

	public void setCurrentMP(int currentMP) {
		if (currentMP > totalMP) {
			this.currentMP = totalMP;
			return;
		}
		if (currentMP < 0) {
			this.currentMP = 0;
			return;
		}
		this.currentMP = currentMP;
	}

	public void setAttackHP(int attackHP) {// 一般攻擊扣當前血量 (記得和上面合併優化)
		if (currentHP > totalHP) {
			this.currentHP = totalHP;
			return;
		}
		currentHP += attackHP;// 一定要放在這裡 不然會有-0的情形
		if (currentHP <= 0) {
			this.currentHP = 0;
			return;
		}

	}

	public void setSkillMP(int mp) {// (記得和上面合併優化)
		if (currentMP > totalMP) {
			this.currentMP = totalMP;
			return;
		}
		currentMP += mp;
		if (currentMP <= 0) {
			this.currentMP = 0;
			return;
		}
	}
}