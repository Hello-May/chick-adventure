package popup;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Controllers.ImageController;
import GameObject.Actor;
import IO.CommandSolver;
import IO.CommandSolver.KeyCommandListener;
import Utils.Global;
import Utils.Messages.MsgFlow;
import Values.ImagePath;
import Values.PathBuilder;

public class MsgPopUpWindow extends PopUpWindow {
	public interface Action {
		public void action();
	}

	private MsgFlow mf;
	private int currentOpt;
	private Color blackWindows;
	private Color grayWindows;
	private Color whiteString;
	private Actor actor;
	private KeyCommandListener keyCommandListener;
	private Action closeAction;
	private boolean isDark;

	public MsgPopUpWindow(MsgFlow mf, Actor actor, Action closeAction) {
		super(40, 500, 900, 150);
		isDark = false;
		this.actor = actor;
		this.mf = mf;
		this.closeAction = closeAction;
		System.out.println(mf.getOptSize());
		currentOpt = 0;
		blackWindows = new Color(0, 0, 0, 128);
		grayWindows = new Color(255, 255, 255, 20);
		whiteString = new Color(255, 255, 255, 180);
		keyCommandListener = new KeyCommandListener() {
			public void keyPressed(int commandCode, long time) {
			}

			@Override
			public void keyReleased(int commandCode, long time) {
				switch (commandCode) {
				case Global.LEFT:
					lastChoose();
					break;
				case Global.RIGHT:
					nextChoose();
					break;
				case Global.ENTER:
					if (!choose(currentOpt)) {
						closeAction.action();
					}
					currentOpt = 0;
					break;
				case Global.ESC:
					closeAction.action();
					break;
				}
			}
		};
	}

	public void setIsDark(boolean isDark) {
		this.isDark = isDark;
	}

	public void lastChoose() {
		if (currentOpt <= 0) {
			return;
		}
		currentOpt--;
	}

	public void nextChoose() {
		if (currentOpt >= mf.getOptSize() - 1) {
			return;
		}
		currentOpt++;
	}

	public boolean choose(int i) {
		return mf.choose(i);
	}

	@Override
	public void windowUpdate() {
	}

	@Override
	public void paint(Graphics g) {
		if (isDark) {
			g.setColor(grayWindows);
		} else {
			g.setColor(blackWindows);
		}
		g.fillRect(40, 500, 900, 150);
		g.setColor(whiteString);
		g.setFont(new Font("微軟正黑體", Font.BOLD, 16));
		if (mf.getName().equals("") && actor != null) {
			g.drawString(actor.getName(), 350, 540);
		} else {
			g.drawString(mf.getName(), 350, 540);
		}
		g.drawString(mf.getMsgText(), 350, 540 + 30);
		g.setFont(new Font("微軟正黑體", Font.BOLD, 12));
		g.drawString("按enter繼續", 840, 630);
		ImageController irc = ImageController.getInstance();
		BufferedImage img = irc.tryGetImage(PathBuilder.getImg(mf.getAvatarPath()));
		if (mf.getName() == "村長") {
			g.drawImage(img, 80, 415, 80 + 201 + 30, 415 + 206 + 30, 0, 0, 201, 206, null);
		} else {
			g.drawImage(img, 50, 380, 50 + 272, 380 + 288, 0, 0, 272, 288, null);
		}

		g.setFont(new Font("微軟正黑體", Font.BOLD, 16));
		int x = 720;
		for (int i = this.mf.getOptSize() - 1; i >= 0; i--) {
			g.drawString(mf.getOpt(i).hint, x, 600);
			if (i == currentOpt) {
				((Graphics2D) g).setStroke(new BasicStroke(2));
				g.drawRect(x - 10, 576, mf.getOpt(i).hint.length() * 15 + 20, 34);// => 大小要依照文字寬度適應
			}
			x -= mf.getOpt(i).hint.length() * 11 + 30;
		}
	}

	@Override
	public CommandSolver.KeyCommandListener getKeyCommandListener() {
		return keyCommandListener;
	}
}
