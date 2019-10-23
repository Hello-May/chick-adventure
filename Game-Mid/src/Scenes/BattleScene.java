/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import fighting.Button;
import controllers.ImageResourceController;
import fighting.DirectionGroup;
import fighting.Status;
import fighting.StatusBar;
import io.CommandSolver.KeyCommandListener;
//import controllers.BattleController;
import controllers.Notes;
import controllers.PathBuilder;
import controllers.SceneController;
import actor.Actor;
import actor.Chicken;
import fighting.Job;

import utils.Global;
import values.ImagePath;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import popup.PopUpWindow;
import popup.SkillPopUpWindow;

/**
 *
 * @author User
 */
public class BattleScene extends Scene {

    private Chicken chicken;
    private Actor actor;
    private BufferedImage img;
    private Button menu;
    private DirectionGroup dg;

    private StatusBar playerBar;
    private StatusBar enemyBar;
    private Status playerStatus;
    private Status enemyStatus;

    private KeyCommandListener keyCommandListener;
    private KeyCommandListener menuCommandListener;

    private SkillPopUpWindow currentWindow;

    public BattleScene(SceneController sceneController) {
        super(sceneController);
        img = getImage(0);
        img = getImage(1);
        keyCommandListener = menuCommandListener = new KeyCommandListener() {

            @Override
            public void keyPressed(int commandCode, long time) {
                switch (commandCode) {
                    case Global.LEFT:
                        menu.lastChoose();
                        break;
                    case Global.RIGHT:
                        menu.nextChoose();
                        break;
                    case Global.ENTER:
                        menu.onClick();
                        break;
                }
            }

            @Override
            public void keyReleased(int CommandCode, long time) {
            }
        };
    }

    private BufferedImage getImage(int tmp) {
        ImageResourceController irc = ImageResourceController.getInstance();
        if (tmp == 0) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.GRASSLAND));
        }
        if (tmp == 1) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.SWORD));
        }
        return null;
    }

    @Override
    public void sceneBegin() {
        chicken = new Chicken(720, 450, 96, 96, 2, playerStatus, new Job("法師"));
        playerStatus = new Status(200, 200, 10);// 血 魔 攻擊

        actor = new Actor(200, 450, 96, 96, 2, 14, enemyStatus);
        enemyStatus = new Status(200, 200, 10);

        playerBar = new StatusBar(10, 10, 200, 85, playerStatus); //位置、大小
        enemyBar = new StatusBar(Global.SCREEN_WIDTH - 200 - 30, 10, 200, 85, enemyStatus);

        menu = new Button(330, 550, 48, 57);
        menu.setButtonListener(new Button.ButtonListener() {
            @Override
            public void onClick(int count) {
                switch (count) {
                    case 0://攻擊
                        chicken.attack();
                        actor.attack();
                        break;
                    case 1://技能
                        currentWindow = new SkillPopUpWindow(415, 470, 140, 60);
                        currentWindow.setOnClickListener(new SkillPopUpWindow.ButtonListener() {
                            @Override
                            public void onClick(int count) {
                                dg = new DirectionGroup(340, 400, 51, 54, new DirectionGroup.OnCompleteListener() { //new出來後監聽成功還是失敗
                                    @Override
                                    public void success() {
                                        System.out.println("Success!!!!");
                                        dg = null;
                                        System.out.println("A");
                                        switch (count) {
                                            case 0:
                                                System.out.println("火球");

                                                break;
                                            case 1:
                                                System.out.println("詛咒");
                                                break;
                                            case 2:
                                                System.out.println("召喚");
                                                break;
                                        }
                                    }
                                    @Override
                                    public void failed() {
                                        System.out.println("Failed!!!!");
                                        dg = null;

                                    }
                                });
                                keyCommandListener = dg.getKeyCommandListener();
                                currentWindow = null;
                            }
                        });
                        break;
                    case 2://投擲
                        System.out.println("投擲");
                        break;
                    case 3://治療
                        System.out.println("治療");
                        break;
                    case 4://逃跑
                        System.out.println("逃跑");
                        break;
                }
            }
        });

    }

    @Override
    public void sceneUpdate() {

        chicken.move();
        actor.move();

        if (currentWindow != null) {
            currentWindow.windowUpdate();
            if (currentWindow.off() == -1) {
                currentWindow = null;
            }
        }

        if (dg != null) {
            dg.move();
        }
    }

    @Override
    public void sceneEnd() {

    }

    @Override
    public void paint(Graphics g) {

        g.drawImage(getImage(0), 0, 0, 1000, 700, null);
        g.drawImage(getImage(1), 450, 20, 100, 89, null);  //114 103

        chicken.paint(g);
        actor.paint(g);

        if (dg != null) {
            dg.paint(g);
        }

        playerBar.paint(g);
        enemyBar.paint(g);

        menu.paint(g);

        if (currentWindow != null) { //畫在最上面
            currentWindow.paint(g);
        }
    }

    @Override
    public KeyCommandListener getKeyCommandListener() {
        return (currentWindow == null) ? keyCommandListener : currentWindow.getKeyCommandListener();
    }
}
