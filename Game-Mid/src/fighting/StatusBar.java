/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fighting;

import controllers.ImageResourceController;
import controllers.PathBuilder;
import gameobject.GameObject;
import values.ImagePath;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import sun.font.FontDesignMetrics;

/**
 *
 * @author User
 */
public class StatusBar extends GameObject { //框跟血(法)還是要分開new 才可分開定位

    private static final String HP = "HP";
    private static final String MP = "MP";
    private static int MARGIN = 10; //血魔間距
    private static int MARGIN_FONT_BAR = 3;//字的間距
    private Status status;
    private static Font f1;
    private int heightPerBar;
    private int widthPerBar;

    private int fontAscent; //上升
    private int fontHeight;
    private int hpWidth;
    private int mpWidth;

    public StatusBar(int x, int y, int width, int height, Status status) {
        super(x, y, width, height);
        this.status = status;
        if (f1 == null) {
            f1 = new Font("Helvetica", Font.BOLD, 30);
        }
        AffineTransform affinetransform = new AffineTransform();//可以做各種形狀的變化(ex:放大縮小)
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);//字體類別和介面    
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
        ((Graphics2D)g).setStroke(new BasicStroke(3)); //黑框多粗
        paintHP(g, MARGIN + x, MARGIN + y);
        paintMP(g, MARGIN + x, heightPerBar + MARGIN * 2 + y);
//        ((Graphics2D)g).setStroke(new BasicStroke(1));
    }
}
