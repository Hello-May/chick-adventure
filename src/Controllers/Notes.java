package Controllers;

import Utils.Global;

public class Notes { 
    private static final int[] combos = {Global.UP, Global.DOWN, Global.LEFT, Global.RIGHT};
    private static final int COMBO_LENGTH = 6;
    private int[] commands;
    private int count;
    private int[] currentCombos;
    private boolean isFull;
    private boolean isReady;

    public Notes() {
        currentCombos = genComboLib();
        commands = new int[COMBO_LENGTH];
        count = 0;
        isReady = true;
        isFull = false;
    }

    private int[] genComboLib() {
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
            if (commands[i] != currentCombos[i]) {
                isReady = false; 
                break;
            }
        }
        if (i == count) {
            isReady = true;
        }
        if (count == COMBO_LENGTH) {
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

    public boolean isFull() {
        return isFull;
    }

    public boolean isReady() {
        return isReady;
    }
}
