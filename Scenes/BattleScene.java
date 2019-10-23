/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import fighting.Button;
import Controllers.ImageController;
import fighting.DirectionGroup;
import fighting.Status;
import fighting.StatusBar;
import IO.CommandSolver.KeyCommandListener;
//import controllers.BattleController;
import Controllers.Notes;
import Values.PathBuilder;
import Controllers.SceneController;
import GameObject.Actor;
import GameObject.Chicken;
import fighting.Job;

import Utils.Global;
import Values.ImagePath;
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
        ImageController irc = ImageController.getInstance();
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
        chicken = new Chicken(720, 450, 96, 96, 2, playerStatus, new Job("瘜葦"));
        playerStatus = new Status(200, 200, 10);// 銵� 擳� ����

        actor = new Actor(200, 450, 96, 96, 2, 14, enemyStatus);
        enemyStatus = new Status(200, 200, 10);

        playerBar = new StatusBar(10, 10, 200, 85, playerStatus); //雿蔭�之撠�
        enemyBar = new StatusBar(Global.SCREEN_WIDTH - 200 - 30, 10, 200, 85, enemyStatus);

        menu = new Button(330, 550, 48, 57);
        menu.setButtonListener(new Button.ButtonListener() {
            @Override
            public void onClick(int count) {
                switch (count) {
                    case 0://����
                        chicken.attack();
                        actor.attack();
                        break;
                    case 1://���
                        currentWindow = new SkillPopUpWindow(415, 470, 140, 60);
                        currentWindow.setOnClickListener(new SkillPopUpWindow.ButtonListener() {
                            @Override
                            public void onClick(int count) {
                                dg = new DirectionGroup(340, 400, 51, 54, new DirectionGroup.OnCompleteListener() { //new�靘������憭望��
                                    @Override
                                    public void success() {
                                        System.out.println("Success!!!!");
                                        dg = null;
                                        System.out.println("A");
                                        switch (count) {
                                            case 0:
                                                System.out.println("����");

                                                break;
                                            case 1:
                                                System.out.println("閰��");
                                                break;
                                            case 2:
                                                System.out.println("����");
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
                    case 2://��
                        System.out.println("��");
                        break;
                    case 3://瘝餌��
                        System.out.println("瘝餌��");
                        break;
                    case 4://���
                        System.out.println("���");
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

        if (currentWindow != null) { //����銝
            currentWindow.paint(g);
        }
    }

    @Override
    public KeyCommandListener getKeyCommandListener() {
        return (currentWindow == null) ? keyCommandListener : currentWindow.getKeyCommandListener();
    }

	@Override
	public KeyCommandListener getCommandListener() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyCommandListener getMouseCommandListener() {
		// TODO Auto-generated method stub
		return null;
	}
}
