package fighting;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.GameObject;
import utils.Global;
import values.ImagePath;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author user1
 */
public class Button extends GameObject { //選單

    public interface ButtonListener {

        public void onClick(int count);
    }

    private ButtonListener buttonListener;
    private BufferedImage[] MENU = {getMenu(0), getMenu(1), getMenu(2), getMenu(3), getMenu(4)};
    private int count;
    private static final int OFFSET = 143;
    private static final int SOURCE = 171;
    private static int MARGIN_FONT_BAR = 80;//間距

    public Button(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.count = 0;
    }

    public int getCount() {
        return this.count;
    }

    private BufferedImage getMenu(int count) {
        ImageResourceController irc = ImageResourceController.getInstance();
        if (count == 0) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Battle.battle.ATTACK));
        }
        if (count == 1) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Battle.battle.SKILL));
        }
        if (count == 2) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Battle.battle.CAST));
        }
        if (count == 3) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Battle.battle.TREATMENT));
        }
        if (count == 4) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Battle.battle.SOS));
        }
        return null;
    }

    public void nextChoose() { //分開寫比較有彈性、管理
        if (count < 4) {
            count++;
        }
    }

    public void lastChoose() { //分開寫比較有彈性、管理
        if (count > 0) {
            count--;
        }
    }

    public void changeCount(int count) { //分開寫比較有彈性、管理
        if (this.count < 0 || this.count > 4) {
            return;
        }
        this.count = count;
    }

    public void onClick() { //點擊後觸發當前count
        if (buttonListener != null) {
            buttonListener.onClick(count);
        }
    }

    public void setButtonListener(ButtonListener buttonListener) { //重要 監聽button
        this.buttonListener = buttonListener;
    }

    public boolean isCollision(int x, int y) {
        if (x < this.x || x > this.x + this.width) {
            return false;
        }
        if (y < this.y || y > this.y + this.height) {
            return false;
        }
        return true;
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < MENU.length; i++) {
            if (i == this.count) {
                g.drawImage(MENU[i], x - 11 + (MARGIN_FONT_BAR * i), y - 14, width + x + 22 + (MARGIN_FONT_BAR * i), y + 28 + height, 0, 0, OFFSET, SOURCE, null);
            } else {
                g.drawImage(MENU[i], x + (MARGIN_FONT_BAR * i), y, width + x + (MARGIN_FONT_BAR * i), y + height, 0, 0, OFFSET, SOURCE, null);

            }
        }
    }
}
