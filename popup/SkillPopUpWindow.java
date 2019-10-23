
package popup;

import IO.CommandSolver;
import Controllers.ImageController;
import Values.PathBuilder;
import Utils.Global;
import Values.ImagePath;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SkillPopUpWindow extends PopUpWindow {

    private Graphics g;

    public interface ButtonListener {

        public void onClick(int count);
    }
    private ButtonListener buttonListener;
    private CommandSolver.KeyCommandListener keyCommandListener;
    private int count;

    private BufferedImage[] MAGE = {getMageSkill(0), getMageSkill(1), getMageSkill(2)};
    private static int MARGIN = 40;
    private int test;

    public SkillPopUpWindow(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.count = 0;
        this.test = 0;

        keyCommandListener = new CommandSolver.KeyCommandListener() {
            public void keyPressed(int commandCode, long time) {
                switch (commandCode) {
                    case Global.LEFT:
                        lastChoose();
                        break;
                    case Global.RIGHT:
                        nextChoose();
                        break;
                    case Global.ENTER:
                        buttonListener.onClick(count);
                        break;
                    case Global.ESC:
                        test = -1;
                        break;
                }
            }

            @Override
            public void keyReleased(int CommandCode, long time) {
            }
        };
    }
    
    public void setOnClickListener(ButtonListener buttonListener){
        this.buttonListener = buttonListener;
    }

    public void nextChoose() {
        if (count < 2) {
            count++;
        }
    }

    public void lastChoose() {
        if (count > 0) {
            count--;
        }
    }

    public void changeCount(int count) {
        if (this.count < 0 || this.count > 2) {
            return;
        }
        this.count = count;
    }
//    private void setButtonListener(SkillPopUpWindow.ButtonListener buttonListener) {
//        this.buttonListener = buttonListener;
//    }
    private BufferedImage getMageSkill(int count) {
        ImageController irc = ImageController.getInstance();
        if (count == 0) { 
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.Skill.FIREBALL));
        }
        if (count == 1) { 
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.Skill.CURSE));
        }
        if (count == 2) { 
            return irc.tryGetImage(PathBuilder.getImg(ImagePath.Skills.Skill.CALL));
        }
        return null;
    }

    @Override
    public void windowUpdate() {

    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(0, 0, 0, 128)); 
        g.fillRect(415, 470, 140, 60);
        g.setColor(Color.WHITE);
        g.drawRect(420, 475, 130, 50); 
        for (int i = 0; i < MAGE.length; i++) {
            g.drawImage(MAGE[i], 429 + (MARGIN * i), 484, 32 + 429 + (MARGIN * i), 484 + 32, 0, 0, 32, 32, null);
        }
        for (int i = 0; i < MAGE.length; i++) {
            if (i == this.count) {
                g.setColor(Color.YELLOW);
                g.drawRect(427 + (MARGIN * i), 482, 36, 36);
            }
        }
    }
    
    @Override
    public CommandSolver.KeyCommandListener getKeyCommandListener() {
        return keyCommandListener;
    }

    public int off() {
        return this.test;
    }

}
