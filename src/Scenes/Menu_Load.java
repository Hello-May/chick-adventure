package Scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Controllers.ImageController;
import Controllers.MusicController;
import Controllers.SceneController;
import Controllers.StoryController;
import GameObject.Actor;
import IO.CommandSolver;
import IO.CommandSolver.KeyCommandListener;
import Utils.GameRecord;
import Utils.Global;
import Values.ImagePath;
import Values.MusicPath;
import Values.PathBuilder;
import popup.Status_FilePopUpWindow.ButtonListener;

public class Menu_Load extends Scene {
	public interface ButtonListener {
		public void onClick(int count);
	}

	private static StoryController storyController = StoryController.getInstance();
	private GameRecord gameRecord;
	private ButtonListener buttonListener;
	private CommandSolver.KeyCommandListener keyCommandListener;
	private ImageController irc;
	private BufferedImage bg;
	private Color dark;
	private MusicController bgm;
	private String[] string;
	private String[] loadFiles;
	private int count;
	private int offWindow;

	public Menu_Load(SceneController sceneController) {
		super(sceneController);
		this.count = 0;
		this.offWindow = 0;
		irc = ImageController.getInstance();
		bg = irc.tryGetImage(PathBuilder.getImg(ImagePath.Scene.LOAD));
		dark = new Color(0, 0, 0, 150);
		string = new String[] { "讀取紀錄1", "讀取紀錄2", "讀取紀錄3" };
		loadFiles = new String[] { "save1", "save2", "save3" };

		keyCommandListener = new KeyCommandListener() {
			@Override
			public void keyPressed(int commandCode, long time) {
				switch (commandCode) {
				case Global.UP:
					lastChoose();
					break;
				case Global.DOWN:
					nextChoose();
					break;
				case Global.ENTER:
					System.out.println(count);
					load(count);
//     sceneController.changeScene(sceneController.getMainMenu()[0]);
//     buttonListener.onClick(count);
					break;
				}
			}

			@Override
			public void keyReleased(int commandCode, long time) {
				switch (commandCode) {
				case Global.ESC:
					sceneController.changeScene(sceneController.getMenuScene());
					break;
				}
			}
		};
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

	public void load(int count) {
//  FileInputStream fis;
		try {
			switch (count) {
			case 0:
				// 連結到檔案位置
				FileInputStream fis1 = new FileInputStream(loadFiles[0]);
				// 準備讀取檔案
				ObjectInputStream ois1 = new ObjectInputStream(fis1);
				// 把讀取的檔案放到變數裡(強轉)
				gameRecord = (GameRecord) ois1.readObject();
				fis1.close();
				ois1.close();
				break;
			case 1:
				FileInputStream fis2 = new FileInputStream(loadFiles[1]);
				ObjectInputStream ois2 = new ObjectInputStream(fis2);
				gameRecord = (GameRecord) ois2.readObject();
				fis2.close();
				ois2.close();
				break;
			case 2:
				FileInputStream fis3 = new FileInputStream(loadFiles[2]);
				ObjectInputStream ois3 = new ObjectInputStream(fis3);
				gameRecord = (GameRecord) ois3.readObject();
				fis3.close();
				ois3.close();
				break;
			}
			// sc.setSF1();
			// scene.setActor();
			// sc.changeScene(scene);
			storyController.setSF1(gameRecord.getStory());
			Game_MainScene gm = new Game_MainScene(sceneController);
			sceneController.setMainScene(gm);
			gm.setActor(new Actor(gameRecord.getActorRecord()));
			sceneController.changeScene(gm);
		} catch (FileNotFoundException ex) {// 沒有找到檔案位置的處理方式
			ex.printStackTrace();
		} catch (IOException ex) {// 讀檔發生錯誤的時候會怎麼處理
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {// 如果沒找到gameRecord的處理方式
			e.printStackTrace();
		}
	}

	@Override
	public void sceneBegin() {
		bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B1));
		bgm.play();

	}

	@Override
	public void sceneUpdate() {

	}

	@Override
	public void sceneEnd() {
		bgm.stop();

	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(bg, 0, 0, Global.WINDOWS_WIDTH, Global.WINDOWS_HEIGHT, null);
		g.setColor(dark);
		g.fillRect(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);

		g.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		g.fillRect(350, 190, 300, 250);
		for (int i = 0; i < string.length; i++) {
			if (i == this.count) {
				g.setColor(Color.YELLOW);
			} else {
				g.setColor(Color.WHITE);
			}
			g.drawString(string[i], 470, 240 + 60 * i);
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("微軟正黑體", Font.BOLD, 14));
		g.drawString("(按esc鍵返回主選單)", 445, 430);

	}

	@Override
	public KeyCommandListener getKeyCommandListener() {
		return keyCommandListener;
	}

	@Override
	public KeyCommandListener getMouseCommandListener() {
		return null;
	}

	public void setOnClickListener(ButtonListener buttonListener) {
		this.buttonListener = buttonListener;
	}

	public int off() {
		return this.offWindow;
	}
}