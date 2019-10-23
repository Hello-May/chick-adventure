/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author user1
 */
public class Global {

    // 資料刷新時間
    public static final int UPDATE_TIMES_PER_SEC = 30;
    public static final int MILLISEC_PER_UPDATE = 1000 / UPDATE_TIMES_PER_SEC; //一禎33毫秒
    // 畫面更新時間
    public static final int FRAME_LIMIT = 120;
    public static final int LIMIT_DELTA_TIME = 1000 / FRAME_LIMIT;
    // 物件速度計算
    public static final int ACT_SPEED = 240 / UPDATE_TIMES_PER_SEC;
    public static final int ANIMA_DELAY = UPDATE_TIMES_PER_SEC / 15;

    // 圖片大小制定
    public static final int IMG_X_OFFSET = 32;
    public static final int IMG_Y_OFFSET = 32;
    public static final int IMG_X_CHICKEN = 45;
    public static final int IMG_Y_CHICKEN = 50;

    // 上下左右
    public static final int UP = 3;
    public static final int LEFT = 1;
    public static final int DOWN = 0;
    public static final int RIGHT = 2;
    public static final int ENTER = -3;
    public static final int ESCAPE = -2;

    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 700;

}
