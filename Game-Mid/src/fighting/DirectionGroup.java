/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fighting;

import io.CommandSolver;
import io.CommandSolver.*;
import io.CommandSolver.KeyCommandListener;
import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.GameObject;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import utils.DelayCounter;
import utils.Global;
import values.ImagePath;

import controllers.Notes;
/**
 *
 * @author User
 */
public class DirectionGroup extends GameObject {

    public interface OnCompleteListener {

        public void success();//成功的時候

        public void failed();//失敗的時候
    }

    private static final int OFFSET = 41;//實際圖片大小去切
    private static final int SOURCE = 44;
    private BufferedImage img;
    private Notes comboNotes;
    private int direction;

    private int count;
    private DelayCounter animaDelayCounter;
    private DelayCounter timeLimitCounter;//限時輸入

    private CommandSolver.KeyCommandListener keyCommandListener;
    private OnCompleteListener onCompleteListener;//用來監聽 成功 失敗

    public DirectionGroup(int x, int y, int width, int height, OnCompleteListener onCompleteListener) {//建構子帶入介面參數
        super(x, y, width, height);
        this.count = 0;
        img = getImage(0);  //預設圖片
        direction = -1;     //預設方向
        comboNotes = new Notes();
        animaDelayCounter = new DelayCounter(1000 / Global.MILLISEC_PER_UPDATE, true);//讓使用者看到輸入錯誤的紅燈  預設true暫停不更新  
        timeLimitCounter = new DelayCounter(1000 / Global.MILLISEC_PER_UPDATE);       //限制時間內輸入 

        this.onCompleteListener = onCompleteListener;//判斷成功失敗的監聽

        keyCommandListener = new CommandSolver.KeyCommandListener() {
            public void keyPressed(int commandCode, long time) {
                switch (commandCode) {
                    case Global.UP:
                    case Global.DOWN:
                    case Global.LEFT:
                    case Global.RIGHT:
                        if (!comboNotes.isFull() && comboNotes.isReady()) {//還沒滿並且ready的時候
                            comboNotes.addValue(commandCode);              //紀錄使用者輸入的方向鍵
                        }
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
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Direction.WHITE));
        }
        if (tmp == 1) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Direction.GREEN));
        }
        if (tmp == 2) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Direction.RED));
        }
        return null;
    }

    public void move() {
        if (animaDelayCounter.update()) {  //if(false)不更新
            onCompleteListener.failed();
        }
        if (!comboNotes.isReady() | timeLimitCounter.update()) {//如果記錄沒ready或者限時內沒輸入完 (有輸入完是true才會更新)
            animaDelayCounter.start();//監聽錯誤紅燈就false  就不暫停  開始數 跑onCompleteListener.failed();
        } else if (comboNotes.isFull()) {//如果紀錄輸滿了
            onCompleteListener.success();//就實現成功的介面  (條件為時間內按滿了就成功)
            timeLimitCounter.stop();//成功後計時結束
        }
    }

    private void paintWhite(Graphics g, int commandCode, int x, int y) {
        switch (commandCode) {
            case Global.LEFT:
                g.drawImage(getImage(0), x, y, width + x, y + height, 0, 0, OFFSET, SOURCE, null);
                break;
            case Global.RIGHT:
                g.drawImage(getImage(0), x, y, width + x, y + height, OFFSET, 0, OFFSET * 2, SOURCE, null);
                break;
            case Global.UP:
                g.drawImage(getImage(0), x, y, width + x, y + height, OFFSET * 2, 0, OFFSET * 3, SOURCE, null);
                break;
            case Global.DOWN:
                g.drawImage(getImage(0), x, y, width + x, y + height, OFFSET * 3, 0, OFFSET * 4, SOURCE, null);
                break;
        }
    }

    private void paintGreen(Graphics g, int commandCode, int x, int y) {
        switch (commandCode) {
            case Global.LEFT:
                g.drawImage(getImage(1), x, y, width + x, y + height, 0, 0, OFFSET, SOURCE, null);
                break;
            case Global.RIGHT:
                g.drawImage(getImage(1), x, y, width + x, y + height, OFFSET, 0, OFFSET * 2, SOURCE, null);
                break;
            case Global.UP:
                g.drawImage(getImage(1), x, y, width + x, y + height, OFFSET * 2, 0, OFFSET * 3, SOURCE, null);
                break;
            case Global.DOWN:
                g.drawImage(getImage(1), x, y, width + x, y + height, OFFSET * 3, 0, OFFSET * 4, SOURCE, null);
                break;
        }
    }

    private void paintRed(Graphics g, int commandCode, int x, int y) {
        switch (commandCode) {
            case Global.LEFT:
                g.drawImage(getImage(2), x, y, width + x, y + height, 0, 0, OFFSET, SOURCE, null);
                break;
            case Global.RIGHT:
                g.drawImage(getImage(2), x, y, width + x, y + height, OFFSET, 0, OFFSET * 2, SOURCE, null);
                break;
            case Global.UP:
                g.drawImage(getImage(2), x, y, width + x, y + height, OFFSET * 2, 0, OFFSET * 3, SOURCE, null);
                break;
            case Global.DOWN:
                g.drawImage(getImage(2), x, y, width + x, y + height, OFFSET * 3, 0, OFFSET * 4, SOURCE, null);
                break;
        }
    }

    @Override
    public void paint(Graphics g) {// width間距變等比例縮放 60是自訂間距 圖片大小不變 如果小於寬會重疊  
        if (!comboNotes.isFull() && comboNotes.isReady()) {
            System.out.println(timeLimitCounter.getDeltaTime());
            timeLimitCounter.paint(g);
        }

        int[] currentCombos = comboNotes.getCombo();
        for (int i = 0; i < currentCombos.length; i++) {
            if (comboNotes.get(i) == currentCombos[i]) {
                paintGreen(g, currentCombos[i], x + 60 * i, y); //相等就畫綠色
            } else if (comboNotes.get(i) == -1) {
                paintWhite(g, currentCombos[i], x + 60 * i, y);
            } else {
                paintRed(g, currentCombos[i], x + 60 * i, y);
            }
        }

    }

    public Notes getNotes() {
        return this.comboNotes;
    }

    public CommandSolver.KeyCommandListener getKeyCommandListener() {
        return keyCommandListener;
    }
}
