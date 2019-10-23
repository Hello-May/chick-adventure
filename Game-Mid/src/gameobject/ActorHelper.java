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
import static utils.Global.IMG_X_OFFSET;
import static utils.Global.IMG_Y_OFFSET;
import values.ImagePath;

/**
 *
 * @author user1
 */
public class ActorHelper {

    private BufferedImage img;
    private int actorPosition;

    public ActorHelper(int actor) {
        img = getActor(actor);// 依照使用者指定的Actor去抓圖
       
        actorPosition = actor % 8; // 計算這個actor 在圖中的位置
    }

    private BufferedImage getActor(int actor) {
        ImageResourceController irc = ImageResourceController.getInstance();
        if (actor >= 0 && actor < 8) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.A1));
        }
        if (actor < 16) {
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Character.Actor.A2));
        }
        return null;
    }
    
    public void paint(Graphics g, int x, int y, int width, int height, int act, int direction){
        if(img == null){
            return;
        }
        int cx = 96 * (actorPosition % 4);
        int cy = 128 * (actorPosition / 4);
        g.drawImage(img, x, y, x + width, y + height,
                cx + act * IMG_X_OFFSET, cy + direction * IMG_Y_OFFSET,
                cx + 32 + act * IMG_X_OFFSET, cy + 32 + direction * IMG_Y_OFFSET, null);
    }
}
