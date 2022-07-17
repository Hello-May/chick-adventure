package GameObject;

import java.awt.Graphics;

import fighting.Status;

public abstract class GameObject {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected String name;
	protected int picture;

	public GameObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Status getStatus() {
		return null;
	}

	public int getPicture() {
		return this.picture;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	// coordinate start
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	// coordinate end

	// Bound start
	public int getBottom() {
		return this.y + height;
	}

	public int getTop() {
		return this.y;
	}

	public int getLeft() {
		return this.x;
	}

	public int getRight() {
		return this.x + width;
	}
	// Bound end

	public boolean isCollision(GameObject obj) {
		if (getLeft() > obj.getRight()) {
			return false;
		}
		if (getRight() < obj.getLeft()) {
			return false;
		}
		if (getTop() > obj.getBottom()) {
			return false;
		}
		if (getBottom() < obj.getTop()) {
			return false;
		}
		return true;
	}

	public boolean isCollision(GameObject obj, int distance) {
		if (getLeft() > obj.getRight() + distance) {
			return false;
		}
		if (getRight() < obj.getLeft() - distance) {
			return false;
		}
		if (getTop() > obj.getBottom() + distance) {
			return false;
		}
		if (getBottom() < obj.getTop() - distance) {
			return false;
		}
		return true;
	}

	public String getName() {
		return this.name;
	}

	public void move() {
		// 預設物品靜止不動
	}

	public abstract void paint(Graphics g); // 物件可能是多張圖片

	public abstract void paint(Graphics g, int cx, int cy);

}
