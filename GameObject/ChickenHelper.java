
package GameObject;

import Controllers.ImageController;
import Values.PathBuilder;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static Utils.Global.IMG_X_CHICKEN;
import static Utils.Global.IMG_Y_CHICKEN;
import Values.ImagePath;

public class ChickenHelper {

    private BufferedImage img;
    private int actorPosition;

    public ChickenHelper() {
        img = getChicken(0);
    }

    private BufferedImage getChicken(int tmp) {
        ImageController irc = ImageController.getInstance();
        if (tmp == 0) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.CHICKEN));
        }
        if (tmp == 1) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.Skill.ATTACK));
        }
        if (tmp == 2) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.Mage.FIRE));
        }
        return null;
    }

    public void paint(Graphics g, int x, int y, int width, int height, int act, int direction) {
        if (img == null) {
            return;
        }
        int cx = 135 * (actorPosition % 4); 
        int cy = 200 * (actorPosition / 4);
        g.drawImage(img, x, y, x + width, y + height,
                cx + act * IMG_X_CHICKEN, cy + direction * IMG_Y_CHICKEN,
                cx + 45 + act * IMG_X_CHICKEN, cy + 50 + direction * IMG_Y_CHICKEN, null);
        if (direction == 1 && x <= 250) {
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
