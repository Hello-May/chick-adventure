package fighting;

import IO.CommandSolver;
import Controllers.ImageController;
import Values.PathBuilder;
import GameObject.GameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import Utils.DelayCounter;
import Utils.Global;
import Values.ImagePath;
import Controllers.Notes;

public class DirectionGroup extends GameObject {
    public interface OnCompleteListener {
        public void success();
        public void failed();
    }

    private static final int OFFSET = 41;
    private static final int SOURCE = 44;
    private BufferedImage img;
    private Notes comboNotes;
    private int direction;
    private int count;
    private DelayCounter animaDelayCounter;
    private DelayCounter timeLimitCounter;
    private CommandSolver.KeyCommandListener keyCommandListener;
    private OnCompleteListener onCompleteListener;

    public DirectionGroup(int x, int y, int width, int height, OnCompleteListener onCompleteListener) {
        super(x, y, width, height);
        this.count = 0;
        img = getImage(0);
        direction = -1;
        comboNotes = new Notes();
        animaDelayCounter = new DelayCounter(1000 / Global.MILLISEC_PER_UPDATE, true);
        timeLimitCounter = new DelayCounter(1000 / Global.MILLISEC_PER_UPDATE); 

        this.onCompleteListener = onCompleteListener;

        keyCommandListener = new CommandSolver.KeyCommandListener() {
            public void keyPressed(int commandCode, long time) {
                switch (commandCode) {
                    case Global.UP:
                    case Global.DOWN:
                    case Global.LEFT:
                    case Global.RIGHT:
                        if (!comboNotes.isFull() && comboNotes.isReady()) {
                            comboNotes.addValue(commandCode);
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
        ImageController irc = ImageController.getInstance();
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
        if (animaDelayCounter.update()) {  
            onCompleteListener.failed();
        }
        if (!comboNotes.isReady() | timeLimitCounter.update()) {
            animaDelayCounter.start();
        } else if (comboNotes.isFull()) {
            onCompleteListener.success();
            timeLimitCounter.stop();
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
    public void paint(Graphics g) {
        if (!comboNotes.isFull() && comboNotes.isReady()) {
            System.out.println(timeLimitCounter.getDeltaTime());
            timeLimitCounter.paint(g);
        }

        int[] currentCombos = comboNotes.getCombo();
        for (int i = 0; i < currentCombos.length; i++) {
            if (comboNotes.get(i) == currentCombos[i]) {
                paintGreen(g, currentCombos[i], x + 60 * i, y); ‰²
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

	@Override
	public void paint(Graphics g, int cx, int cy) {
		// TODO Auto-generated method stub
		
	}
}
