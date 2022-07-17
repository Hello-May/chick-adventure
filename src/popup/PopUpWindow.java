package popup;

import IO.CommandSolver;
import java.awt.Graphics;

public abstract class PopUpWindow {

	private int x;
	private int y;
	private int width;
	private int height;

	public PopUpWindow(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getRight() {
		return x + width;
	}

	public int getBottom() {
		return y + height;
	}

	public abstract void windowUpdate();

	public abstract void paint(Graphics g);

	public CommandSolver.KeyCommandListener getKeyCommandListener() {
		return null;
	}

	public CommandSolver.MouseCommandListener getMouseCommandListener() {
		return null;
	}
}