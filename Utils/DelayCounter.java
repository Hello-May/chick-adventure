package Utils;

public class DelayCounter {

	private int delay; // �n���𪺮ɶ�
	private int count; // �p�⩵��

	public DelayCounter(int delay) {
		this.delay = delay* Global.ANIMA_DELAY;
		count = 0;
	}

	public boolean update() {
		if (count++ < delay) {
			return false;
		}
		count = 0;
		return true;
	}

}
