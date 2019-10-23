/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import utils.Global;

/**
 *
 * @author User
 */
public class Notes { //記錄使用者按的方向鍵
    private static final int[] combos = {Global.UP, Global.DOWN, Global.LEFT, Global.RIGHT};
    private static final int COMBO_LENGTH = 6; //裝六個
    private int[] commands;//裝隨機的上下左右
    private int count;
    private int[] currentCombos; //紀錄使用者輸入
    private boolean isFull;
    private boolean isReady;

    public Notes() {
        currentCombos = genComboLib();
        commands = new int[COMBO_LENGTH];
        count = 0;
        isReady = true; //預設準備好所以可以add
        isFull = false; //使用者未輸入前還沒滿
    }

    private int[] genComboLib() { //隨機上下左右
        int[] c = new int[COMBO_LENGTH];
        for (int i = 0; i < combos.length; i++) {
            int r = (int) (Math.random() * 4);
            c[i] = combos[r];
        }
        return c;
    }

    public int[] getCombo() {
        return currentCombos;
    }

    public void addValue(int x) {
        if (isFull) {
            return;
        }
        commands[count++] = x;
        int i;
        for (i = 0; i < count; i++) {
            if (commands[i] != currentCombos[i]) {//輸入不相等的時候
                isReady = false; //true的話(並且沒有滿)能繼續往下按 就有可能在時間內按完 就會觸發當前的成功條件(條件為時間內按滿了就成功)        
                break;
            }
        }
        if (i == count) { //有輸入完
            isReady = true;
        }
        if (count == COMBO_LENGTH) { //使用者輸入完了(有輸到滿)
            isFull = true;
        }
    }

    public int getCount(){
        return count;
    }
    
    public int get(int index) {
        if (index < 0 || index >= count) {
            return -1;
        }
        return commands[index];
    }

    public boolean isFull() {//初始false沒滿
        return isFull;
    }

    public boolean isReady() {//初始true
        return isReady;
    }
}
