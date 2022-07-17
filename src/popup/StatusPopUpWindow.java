package popup;

import java.awt.Color;
import java.awt.Graphics;
import IO.CommandSolver;
import Utils.Global;

public class StatusPopUpWindow extends PopUpWindow {
	public interface ButtonListener {
		public void onClick(int count);
	}

	private ButtonListener buttonListener;
	private CommandSolver.KeyCommandListener keyCommandListener;
	private int focusIndex;
	private int test;
	private String[] options;
	private Color window;

	public StatusPopUpWindow(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.focusIndex = 0;
		this.test = 0;
		window = new Color(0, 0, 0, 128);
		options = new String[] { "返回村莊", "勇者資訊", "技能說明", "存檔紀錄", "回主選單" };
		keyCommandListener = new CommandSolver.KeyCommandListener() {
			public void keyPressed(int commandCode, long time) {
				switch (commandCode) {
				case Global.UP:
					lastChoose();
					break;
				case Global.DOWN:
					nextChoose();
					break;
				case Global.ENTER:
					buttonListener.onClick(focusIndex);
					break;
				}
			}

			@Override
			public void keyReleased(int commandCode, long time) {
				switch (commandCode) {
				case Global.ESC:
					test = -1;
					break;
				}
			}
		};
	}

	public void setOnClickListener(ButtonListener buttonListener) {
		this.buttonListener = buttonListener;
	}

	public void nextChoose() {
		if (focusIndex < options.length - 1) {
			focusIndex++;
		}
	}

	public void lastChoose() {
		if (focusIndex > 0) {
			focusIndex--;
		}
	}

	public void changeCount(int focusIndex) {
		if (this.focusIndex < 0 || this.focusIndex > options.length) {
			return;
		}
		this.focusIndex = focusIndex;
	}

	@Override
	public void windowUpdate() {

	}

	@Override
	public void paint(Graphics g) {
		g.setColor(window);
		g.fillRect(250, 190, 100, 250);
		for (int i = 0; i < options.length; i++) {
			if (i == this.focusIndex) {
				g.setColor(Color.YELLOW);
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], 275, 230 + 45 * i);
		}
	}

	@Override
	public CommandSolver.KeyCommandListener getKeyCommandListener() {
		return keyCommandListener;
	}

	public int off() {
		return this.test;
	}

}