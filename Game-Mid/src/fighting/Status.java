/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fighting;

/**
 *
 * @author User
 */
public class Status { //狀態

    private int totalHP;       //總血量
    private int currentHP;     //當前血量
    private int totalMP;       //總魔力
    private int currentMP;     //當前魔力
    private int totalAttack;
    private int currentAttack;

    public Status(int totalHp, int totalMP, int totalAttack) {
        this.currentHP = this.totalHP = totalHp;
        this.currentMP = this.totalMP = totalMP;
        this.currentAttack = this.totalAttack = totalAttack;
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

    public int getTotalAttack() {
        return totalAttack;
    }

    public void setTotalAttack(int totalAttack) {
        this.totalAttack = totalAttack;
    }

    public int getCurrentAttack() {
        return currentAttack;
    }

    public void setCurrentAttack(int currentAttack) {
        this.currentAttack = currentAttack;
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

}
