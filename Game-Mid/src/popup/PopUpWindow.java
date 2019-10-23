/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package popup;

import io.CommandSolver;
import java.awt.Graphics;

/**
 *
 * @author User
 */
public abstract class PopUpWindow {

    private int x;// 跳出的位置
    private int y;// 跳出的位置
    private int width;// 視窗大小
    private int height;// 視窗大小

    public PopUpWindow(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRight() {
        return x + width;
    }

    public int getBottom() {
        return y + height;
    }

    public abstract void windowUpdate();// 視窗的更新

    public abstract void paint(Graphics g);// 視窗畫出的內容

    public CommandSolver.KeyCommandListener getKeyCommandListener() {
        return null;
    }

    public CommandSolver.MouseCommandListener getMouseCommandListener() {
        return null;
    }
}
