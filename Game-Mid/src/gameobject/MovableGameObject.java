/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobject;

import java.awt.Graphics;
import utils.Global;

/**
 *
 * @author user1
 */
public abstract class MovableGameObject extends GameObject{
    private int speed;
    
    public MovableGameObject(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        setSpeed(speed);
    }
    
    public final void setSpeed(int speed){
        this.speed = speed * Global.ACT_SPEED;
    }
    
    public final int getSpeed(){
        return speed;
    }
    
    public abstract void move();
}
