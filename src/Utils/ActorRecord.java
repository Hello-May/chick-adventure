package Utils;
import java.io.Serializable;
import fighting.Status;

public class ActorRecord implements Serializable {
	public int direction;
	public Status status;
	public int x;
	public int y;
	public int width;
	public int height;
	public int speed;
	public int actor;
	public int delay;

	public ActorRecord(int x, int y, int width, int height, int speed, int actor, int delay, Status status,
			int direction) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.actor = actor;
		this.delay = delay;
		this.status = status;
		this.direction = direction;
	}

	public int getX() {
		return this.x;
	}
}
