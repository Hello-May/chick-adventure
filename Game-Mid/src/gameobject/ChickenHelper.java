/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actor;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static utils.Global.IMG_X_CHICKEN;
import static utils.Global.IMG_X_OFFSET;
import static utils.Global.IMG_Y_CHICKEN;
import static utils.Global.IMG_Y_OFFSET;
import values.ImagePath;

/**
 *
 * @author user1
 */
public class ChickenHelper {

    private BufferedImage img;
    private int actorPosition;

    public ChickenHelper() {
        img = getChicken(0);
    }

    private BufferedImage getChicken(int tmp) {
        ImageResourceController irc = ImageResourceController.getInstance();
        if (tmp == 0) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.CHICKEN));
        }
        if (tmp == 1) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.Skill.ATTACK));
        }
        if (tmp == 2) {//用2有bug 可能誰也拿了2的圖片在這裡使用
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.Mage.FIRE));//法師技能-火球
        }
        return null;
    }

    public void paint(Graphics g, int x, int y, int width, int height, int act, int direction) {
        if (img == null) {
            return;
        }
        int cx = 135 * (actorPosition % 4); //沒寫0會有殘影
        int cy = 200 * (actorPosition / 4);
        g.drawImage(img, x, y, x + width, y + height,
                cx + act * IMG_X_CHICKEN, cy + direction * IMG_Y_CHICKEN,
                cx + 45 + act * IMG_X_CHICKEN, cy + 50 + direction * IMG_Y_CHICKEN, null);
        if (direction == 1 && x <= 250) {//攻擊後的效果   應該要寫碰撞後攻擊
            int n = 960 * (actorPosition % 6);
            int m = 960 * (actorPosition / 5);
            g.drawImage(getChicken(1), 200, 400, 200 + width, 400 + height,
                    n + act * 192, m + direction * 192,
                    n + 192 + act * 192, m + 192 + direction * 192, null);
            
        }

    }

    public void paintFire(Graphics g, int x, int y, int width, int height, int act, int direction) {

    }

}
