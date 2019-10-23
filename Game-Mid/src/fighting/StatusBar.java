
package fighting;

import GameObject.GameObject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import sun.font.FontDesignMetrics;

public class StatusBar extends GameObject { 

    private static final String HP = "HP";
    private static final String MP = "MP";
    private static int MARGIN = 10; 
    private static int MARGIN_FONT_BAR = 3;
    private Status status;
    private static Font f1;
    private int heightPerBar;
    private int widthPerBar;
    private int fontAscent; 
    private int fontHeight;
    private int hpWidth;
    private int mpWidth;

    public StatusBar(int x, int y, int width, int height, Status status) {
        super(x, y, width, height);
        this.status = status;
        if (f1 == null) {
            f1 = new Font("Helvetica", Font.BOLD, 30);
        }
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);    
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(f1);
        hpWidth = (int) (f1.getStringBounds(HP, frc).getWidth());
        fontAscent = metrics.getAscent();
        fontHeight = metrics.getHeight();
        mpWidth = (int) (f1.getStringBounds(MP, frc).getWidth());

        this.heightPerBar = (height - MARGIN * 3) / 2;
        this.widthPerBar = width - MARGIN * 2 - ((hpWidth > mpWidth) ? hpWidth : mpWidth) - MARGIN_FONT_BAR;
    }

    private void paintHP(Graphics g, int x, int y) {
        int cHp = (int)(widthPerBar * (status.getCurrentHP()/(float)status.getTotalHP()));
        g.setColor(Color.RED);
        g.setFont(f1);
        g.drawString(HP, x, y + (heightPerBar - fontHeight)/2 + fontAscent);
        g.fillRoundRect(x + MARGIN_FONT_BAR + ((hpWidth > mpWidth) ? hpWidth : mpWidth), y, cHp, heightPerBar, 30, 30);
        g.setColor(Color.BLACK);
        g.drawRoundRect(x + MARGIN_FONT_BAR + ((hpWidth > mpWidth) ? hpWidth : mpWidth), y, widthPerBar, heightPerBar, 30, 30);
    }

    private void paintMP(Graphics g, int x, int y) {
        int cMp = (int)(widthPerBar * (status.getCurrentMP()/(float)status.getTotalMP()));
        g.setColor(Color.BLUE);
        g.setFont(f1);
        g.drawString(MP, x, y + (heightPerBar - fontHeight)/2 + fontAscent);
        g.fillRoundRect(x + MARGIN_FONT_BAR + ((hpWidth > mpWidth) ? hpWidth : mpWidth), y, cMp, heightPerBar, 30, 30);
        g.setColor(Color.BLACK);
        g.drawRoundRect(x + MARGIN_FONT_BAR + ((hpWidth > mpWidth) ? hpWidth : mpWidth), y, widthPerBar, heightPerBar, 30, 30);
    }

    @Override
    public void paint(Graphics g) {
        ((Graphics2D)g).setStroke(new BasicStroke(3));
        paintHP(g, MARGIN + x, MARGIN + y);
        paintMP(g, MARGIN + x, heightPerBar + MARGIN * 2 + y);
//        ((Graphics2D)g).setStroke(new BasicStroke(1));
    }

	@Override
	public void paint(Graphics g, int cx, int cy) {
		// TODO Auto-generated method stub
		
	}
}
