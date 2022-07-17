package Utils;

import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;

public class DelayCounter implements Serializable {
	static final long serialVersionUID = 111146l;
	private int delay;// �n���𪺮ɶ�
	private int count;// �p�⩵��
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
		if (isPause) {// �Ȱ��F�N����s
			return false;
		}
		if (count++ < delay) {// �ɶ����S��J���Nfalse
			return false;
		}
		count = 0; // count=delay�N����s���k�s
		return true;
	}

	public int getDeltaTime() {
//         return count * Global.MILLISEC_PER_UPDATE;// ���o�g�L���@��
		return countDown--;
	}

	public void reset() {
		count = 0;
	}

	public void stop() {
		isPause = true;
		count = 0; // ����᭫��
	}

	public void pause() {// �Ȱ�
		isPause = true;
	}

	public void start() {
		isPause = false;
	}

	public int getCount() {
		return this.count;
	}

	public void paint(Graphics g) {
		String s = "�˼ƭp�ɶ}�l:  " + getDeltaTime() / 100;
		g.setFont(new Font("�L�n������", Font.BOLD, 20));
		g.drawString(s, 440, 380);

	}

}