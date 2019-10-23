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
public abstract class GameObject {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

//    private String name;
    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // coordinate start
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    // coordinate end

    // Bound start
    public int getBottom() {
        return y + height;
    }

    public int getTop() {
        return y;
    }

    public int getLeft() {
        return x;
    }

    public int getRight() {
        return x + width;
    }
    // Bound end

    public boolean isCollision(GameObject obj) {
        if (getLeft() > obj.getRight()) {
            return false;
        }
        if (getRight() < obj.getLeft()) {
            return false;
        }
        if (getTop() > obj.getBottom()) {
            return false;
        }
        if (getBottom() < obj.getTop()) {
            return false;
        }
        return true;
    }

    public abstract void paint(Graphics g);
}
