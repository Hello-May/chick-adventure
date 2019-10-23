
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest5th;

import io.CommandSolver;
import io.CommandSolver.CommandConverter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import utils.Global;
import java.awt.Font;

/**
 *
 * @author user1
 */
public class GameTest5th {

    public static void main(String args[]) {
        JFrame j = new JFrame();
        j.setTitle("Test Title");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setSize(Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);

        GameJPanel jp = new GameJPanel();
        j.add(jp);

        j.setVisible(true);
        // ↓每次更新的間隔  
        CommandSolver cs = new CommandSolver.Builder(Global.MILLISEC_PER_UPDATE, new int[][]{
            {KeyEvent.VK_W, Global.UP},
            {KeyEvent.VK_A, Global.LEFT},
            {KeyEvent.VK_S, Global.DOWN},
            {KeyEvent.VK_D, Global.RIGHT},
            {KeyEvent.VK_ENTER, Global.ENTER},
            {KeyEvent.VK_ESCAPE, Global.ESCAPE}
        }).enableMouseTrack(j).keyTypedMode().gen();//加了keyTypedMode()才會判斷完一個keyReleased
        addKeyListener(j, cs);
        cs.start(); //沒有跑這個會爆錯

        long startTime = System.currentTimeMillis();
        long lastRepaintTime = System.currentTimeMillis();
        long passedFrames = 0;
        while (true) {
            long currentTime = System.currentTimeMillis();
            long totalTime = currentTime - startTime;
            long targetTotalFrames = totalTime / Global.MILLISEC_PER_UPDATE;
            // input
            // input end
            while (passedFrames < targetTotalFrames) {
                jp.update(cs.update()); //資料更新 順便更新CommandSolver
                passedFrames++;
            }

            if (Global.LIMIT_DELTA_TIME <= currentTime - lastRepaintTime) {
                lastRepaintTime = currentTime;
                j.repaint();
            }
        }
    }

    public static void addKeyListener(JFrame j, CommandSolver cs) {
        j.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                cs.trig(e.getKeyCode(), true); //按下去
            }

            @Override
            public void keyReleased(KeyEvent e) {
                cs.trig(e.getKeyCode(), false); //放開
            }
        });
        j.setFocusable(true);
    }
}
