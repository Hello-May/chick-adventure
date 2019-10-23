package Utils;

public class DelayCounter {

	private int delay; // 要延遲的時間
	private int count; // 計算延遲

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
