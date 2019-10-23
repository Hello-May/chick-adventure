package GameObject;

import java.awt.Color;
import java.awt.Graphics;
import GameObject.GameObject;
import Scenes.SceneHelper;
import Utils.Global;

public class SceneObject extends GameObject {
	private SceneHelper sceneHelper;
	private int picture;
	private boolean decoration;

	public SceneObject(int x, int y, int width, int height, int picture) {
		super(x, y, width, height);
		this.picture = picture;
		sceneHelper = new SceneHelper();
		decoration = false;
	}

	public int getPicture() {
		return this.picture;
	}
	
	public void setDecoration(boolean decoration) {
		this.decoration=decoration;
	}
	
	public boolean getDecoration() {
		return this.decoration;
	}
	
	@Override
	public void paint(Graphics g, int cx, int cy) {
		sceneHelper.paintObject(g, x - cx, y - cy, width, height, picture,decoration);

		if(Global.DEBUG) {
		// ¦L®y¼Ð
		g.setColor(Color.YELLOW);
		g.drawString(x + "," + y, (x - cx + 11), (y - cy - 3));
		}
	}

	@Override
	public void paint(Graphics g) {
		sceneHelper.paintObject(g, x, y, width, height, picture,decoration);

		if(Global.DEBUG) {
		// ¦L®y¼Ð
		g.setColor(Color.YELLOW);
		g.drawString(x + "," + y, (x + 11), (y - 3));
		}
	}

}
