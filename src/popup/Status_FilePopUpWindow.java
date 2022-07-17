package popup;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import Controllers.ImageController;
import GameObject.Actor;
import GameObject.ActorHelper;
import IO.CommandSolver;
import Utils.GameRecord;
import Utils.Global;
import Values.ImagePath;
import Values.PathBuilder;

public class Status_FilePopUpWindow extends PopUpWindow {

	public interface ButtonListener {
		public void onClick(int count);
	}

	private String[] string;
	private String[] saveFiles;
	private GameRecord gameRecord;
	private int count;
	private Color window;
	private ButtonListener buttonListener;
	private CommandSolver.KeyCommandListener keyCommandListener;
	private int offWindow;
	private boolean saveOk;
	private ActorHelper actorHelper;
	private BufferedImage image;
// private CommandSolver.TypedListener typedListener;
// private char[]chars;

	public Status_FilePopUpWindow(int x, int y, int width, int height, Actor actor, int storyFlow1) {
		super(x, y, width, height);
		this.saveOk = false;
		this.count = 0;
		this.offWindow = 0;
		window = new Color(0, 0, 0, 128);
		gameRecord = new GameRecord(actor.genRecord(), storyFlow1); // 要怎麼存當前的劇情進度 記得讀檔測試
		string = new String[] { "存檔紀錄1", "存檔紀錄2", "存檔紀錄3" };
		saveFiles = new String[] { "save1", "save2", "save3" };
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
//     write(gameRecord, count);
					buttonListener.onClick(count);
					break;
				}
			}

			@Override
			public void keyReleased(int commandCode, long time) {
				switch (commandCode) {
				case Global.ESC:
					offWindow = -1;
					break;
				}
			}
		};
	}

	private BufferedImage getImage(int tmp) {// 要化成功的圖
		ImageController irc = ImageController.getInstance();
		if (tmp == 0) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.SAVE_OK));
		}
		return null;
	}

//  typedListener = new CommandSolver.TypedListener() {
//   public void keyPressed(int commandCode, long time) {
//   }
//
//   @Override
//   public void keyReleased(int commandCode, long time) {
//    switch (commandCode) {
//    case Global.LEFT:
//     lastChoose();
//     break;
//    case Global.RIGHT:
//     nextChoose();
//     break;
//    case Global.ENTER:
////     buttonListener.onClick(count);
////     write(gameRecord);
//     break;
//    case Global.ESC:
////     test = -1;
//     break;
//    }
//   }
//  };
	public void setOnClickListener(ButtonListener buttonListener) {
		this.buttonListener = buttonListener;
	}

	public void nextChoose() {
		if (count < 2) {
			count++;
		}
	}

	public void lastChoose() {
		if (count > 0) {
			count--;
		}
	}

	public void changeCount(int count) {
		if (this.count < 0 || this.count > 2) {
			return;
		}
		this.count = count;
	}

	@Override
	public void windowUpdate() {
	}

	public void write(int count) {
//  FileOutputStream fos;
		try {
			switch (count) {
			case 0:
				FileOutputStream fos1 = new FileOutputStream(saveFiles[0]);
				ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
				oos1.writeObject(gameRecord);
				fos1.close();
				oos1.close();
				this.saveOk = true;
				break;
			case 1:
				FileOutputStream fos2 = new FileOutputStream(saveFiles[1]);
				ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
				oos2.writeObject(gameRecord);
				fos2.close();
				oos2.close();
				this.saveOk = true;
				break;
			case 2:
				FileOutputStream fos3 = new FileOutputStream(saveFiles[2]);
				ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
				oos3.writeObject(gameRecord);
				fos3.close();
				oos3.close();
				this.saveOk = true;
				break;
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		g.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		g.setColor(window);
		g.fillRect(400, 190, 300, 250);
		for (int i = 0; i < string.length; i++) {
			if (i == this.count) {
				g.setColor(Color.YELLOW);
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawString(string[i], 520, 270 + 50 * i);
		}
		if (saveOk) {
			g.drawImage(getImage(0), 630, 390, 630 + 50, 390 + 38, 0, 0, 68, 56, null);

		}
		// g.setColor(window);
		// g.fillRect(400, 190, 300, 250);
		// g.setColor(Color.WHITE);
		// g.drawString("還無法存讀檔QQ~", 430, 200 + 40 * 1);
		//
		// g.setFont(new Font("微軟正黑體", Font.BOLD, 16));
		//
		// g.drawString("yes", 450, 300);
		// g.setColor(Color.WHITE);
		// g.drawRect(430, 270, 50, 50);
		// int x = 720;
		// for (int i = this.mf.getOptSize() - 1; i >= 0; i--) {
		// g.drawString(mf.getOpt(i).hint, x, 600);
		// if (i == count) {
		// g.drawRect(x - 10, 576, mf.getOpt(i).hint.length() * 15 + 20, 34);// =>
		// 大小要依照文字寬度適應
		// }
		// x -= mf.getOpt(i).hint.length() * 11 + 30;
		// }
	}

	@Override
	public CommandSolver.KeyCommandListener getKeyCommandListener() {
		return keyCommandListener;
	}

	public int off() {
		return this.offWindow;
	}

}