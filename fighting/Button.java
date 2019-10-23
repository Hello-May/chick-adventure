package fighting;

import Controllers.ImageController;
import Values.PathBuilder;
import GameObject.GameObject;
import Values.ImagePath;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Button extends GameObject { 

    public interface ButtonListener {

        public void onClick(int count);
    }

    private ButtonListener buttonListener;
    private BufferedImage[] MENU = {getMenu(0), getMenu(1), getMenu(2), getMenu(3), getMenu(4)};
    private int count;
    private static final int OFFSET = 143;
    private static final int SOURCE = 171;
    private static int MARGIN_FONT_BAR = 80;

    public Button(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.count = 0;
    }

    public int getCount() {
        return this.count;
    }

    private BufferedImage getMenu(int count) {
        ImageController irc = ImageController.getInstance();
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

    public void nextChoose() { 
        if (count < 4) {
            count++;
        }
    }

    public void lastChoose() {
        if (count > 0) {
            count--;
        }
    }

    public void changeCount(int count) { 
        if (this.count < 0 || this.count > 4) {
            return;
        }
        this.count = count;
    }

    public void onClick() { 
        if (buttonListener != null) {
            buttonListener.onClick(count);
        }
    }

    public void setButtonListener(ButtonListener buttonListener) { 
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

	@Override
	public void paint(Graphics g, int cx, int cy) {
		// TODO Auto-generated method stub
		
	}
}
