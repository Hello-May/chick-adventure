/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package popup;

import actor.Chicken;
import io.CommandSolver;
import controllers.ImageResourceController;
import controllers.PathBuilder;
import controllers.SceneController;
import fighting.DirectionGroup;
import scene.BattleScene;
import utils.Global;
import values.ImagePath;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import jdk.nashorn.internal.ir.BreakNode;

/**
 *
 * @author User
 */
//待修改
//沒彈性的寫法，鎖死只有三個技能、還有不同職業不同技能
//把關掉的esc觸發寫在windowUpdate()
//paint用到(int x, int y, int width, int height)
public class SkillPopUpWindow extends PopUpWindow {

    private Graphics g;

    public interface ButtonListener {

        public void onClick(int count);
    }
    private ButtonListener buttonListener;
    private CommandSolver.KeyCommandListener keyCommandListener;
    private int count;

    private BufferedImage[] MAGE = {getMageSkill(0), getMageSkill(1), getMageSkill(2)}; //法師技能
    private static int MARGIN = 40;
    private int test;

    public SkillPopUpWindow(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.count = 0;
        this.test = 0;

        keyCommandListener = new CommandSolver.KeyCommandListener() {
            public void keyPressed(int commandCode, long time) {
                switch (commandCode) {
                    case Global.LEFT:
                        lastChoose();
                        break;
                    case Global.RIGHT:
                        nextChoose();
                        break;
                    case Global.ENTER:
                        buttonListener.onClick(count);
                        break;
                    case Global.ESCAPE:
                        test = -1;
                        break;
                }
            }

            @Override
            public void keyReleased(int CommandCode, long time) {
            }
        };
    }
    
    public void setOnClickListener(ButtonListener buttonListener){//重要 監聽
        this.buttonListener = buttonListener;
    }

    public void nextChoose() {
        if (count < 2) {
            count++;
        }
    }

    public void lastChoose() {
        if (count > 0) {
            count--;
        }
    }

    public void changeCount(int count) {
        if (this.count < 0 || this.count > 2) {
            return;
        }
        this.count = count;
    }

//    private void setButtonListener(SkillPopUpWindow.ButtonListener buttonListener) { //重要 監聽button
//        this.buttonListener = buttonListener;
//    }
    private BufferedImage getMageSkill(int count) {
        ImageResourceController irc = ImageResourceController.getInstance();
        if (count == 0) { //火球
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.Skill.FIREBALL));
        }
        if (count == 1) { //共同-詛咒
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.Skill.CURSE));
        }
        if (count == 2) { //共同-召換
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.Skill.CALL));
        }
        return null;
    }

    @Override
    public void windowUpdate() {

    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(0, 0, 0, 128)); // int透明度0-255
        g.fillRect(415, 470, 140, 60);//實心框
        g.setColor(Color.WHITE);
        g.drawRect(420, 475, 130, 50); //空心框
        for (int i = 0; i < MAGE.length; i++) {
            g.drawImage(MAGE[i], 429 + (MARGIN * i), 484, 32 + 429 + (MARGIN * i), 484 + 32, 0, 0, 32, 32, null);
        }
        for (int i = 0; i < MAGE.length; i++) {
            if (i == this.count) {
                g.setColor(Color.YELLOW);
                g.drawRect(427 + (MARGIN * i), 482, 36, 36); //空心框    
            }
        }
    }
    

    @Override
    public CommandSolver.KeyCommandListener getKeyCommandListener() {
        return keyCommandListener;
    }

    public int off() {
        return this.test;
    }

}
