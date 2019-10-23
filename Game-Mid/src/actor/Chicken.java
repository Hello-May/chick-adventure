/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actor;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import fighting.Job;
import fighting.Status;
import fighting.StatusBar;
import gameobject.MovableGameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import jdk.nashorn.internal.ir.BreakNode;
import utils.DelayCounter;
import utils.Global;
import static utils.Global.*;
import values.ImagePath;

/**
 *
 * @author user1
 */
//三個職業共用這個類別
public class Chicken extends MovableGameObject {

    private static final int[] ACT = {0, 1, 2, 1};
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
    public void move() { //改變狀態
        if (movedDelay.update()) {     //腳步切換速度
            act = ++act % 4;            //餘數變化 1 2 3 0
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

    public void attack() { //按下攻擊的時候轉向左邊
        this.direction = Global.LEFT;

    }

//    public void fire() { //按下火球的時候
//        this.direction = Global.DOWN;
//    }
    @Override
    public void paint(Graphics g) {
        chickenHelper.paint(g, x, y, width, height, ACT[act], direction);
    }
}
