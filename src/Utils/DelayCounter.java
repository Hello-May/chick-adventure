package Utils;

import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;

public class DelayCounter implements Serializable {
	static final long serialVersionUID = 111146l;
	private int delay;// 要延遲的時間
	private int count;// 計算延遲
	private boolean isPause;
	private int countDown;

	public DelayCounter(int delay) {
		this.delay = delay * Global.ANIMA_DELAY;
		count = 0;
		countDown = 600;
	}

	public DelayCounter(int delay, boolean isPause) {
		this.delay = delay * Global.ANIMA_DELAY;
		count = 0;
		this.isPause = isPause;
	}

	public boolean update() {
		if (count++ < delay) {
			return false;
		}
		count = 0;
		return true;
	}

	public boolean updatePause() {
		if (isPause) {// 暫停了就不更新
			return false;
		}
		if (count++ < delay) {// 時間內沒輸入完就false
			return false;
		}
		count = 0; // count=delay就做更新並歸零
		return true;
	}

	public int getDeltaTime() {
//         return count * Global.MILLISEC_PER_UPDATE;// 取得經過的毫秒
		return countDown--;
	}

	public void reset() {
		count = 0;
	}

	public void stop() {
		isPause = true;
		count = 0; // 停止後重來
	}

	public void pause() {// 暫停
		isPause = true;
	}

	public void start() {
		isPause = false;
	}

	public int getCount() {
		return this.count;
	}

	public void paint(Graphics g) {
		String s = "倒數計時開始:  " + getDeltaTime() / 100;
		g.setFont(new Font("微軟正黑體", Font.BOLD, 20));
		g.drawString(s, 440, 380);

	}

}