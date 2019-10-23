/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actor;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import fighting.Status;
import gameobject.MovableGameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import utils.DelayCounter;
import utils.Global;
import static utils.Global.*;
import values.ImagePath;

/**
 *
 * @author user1
 */
public class Actor extends MovableGameObject {

    private static final int[] ACT = {0, 1, 2, 1};
    private int act;
    private int delay;
    private int direction;
    private ActorHelper actorHelper;
    private DelayCounter movedDelay;
    private Status status;

    public Actor(int x, int y, int width, int height, int speed, int actor, Status status) {
        super(x, y, width, height, speed);
        delay = act = 0;
        direction = DOWN;
        movedDelay = new DelayCounter(5);
        actorHelper = new ActorHelper(actor);
        this.status = status;
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
    }

    public void attack() { //按下攻擊的時候怪物轉向右邊
        this.direction = Global.RIGHT;
        //應該要寫if碰撞後轉向右邊else正面
    }

    @Override
    public void paint(Graphics g) {
        actorHelper.paint(g, x, y, width, height, ACT[act], direction);
    }

}
