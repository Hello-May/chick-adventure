package Controllers;

import java.awt.Graphics;
import IO.CommandSolver.CommandWrapper;
import Scenes.Menu_Instructions;
import Scenes.Menu_Mall;
import Scenes.Menu_Menu;
import Scenes.Game_MainScene;
import Scenes.Game_Menu;
import Scenes.Menu_Info;
import Scenes.Scene;

public class SceneController {
	private Scene[] mainMenu;
	private Scene[] gameMenu;
	private Scene mainScene;
	private Scene currentScene;

	public SceneController() {
		mainMenu = new Scene[4];
		mainMenu[0] = new Menu_Menu(this);
		mainMenu[1] = new Menu_Instructions(this);
		mainMenu[2] = new Menu_Mall(this);
		mainMenu[3] = new Menu_Info(this);
		currentScene = mainMenu[0];
		gameMenu = new Scene[6];
		gameMenu[0] = new Game_Menu(this);
	}

//	private Scene[] doubleArr(Scene[] arr) {
//		Scene [] tmp = new Scene [arr.length*2];
//		for(int i=0;i<count;i++) {
//			tmp[i]=arr[i];
//		}
//		return tmp;
//	}
//	
//	private void addScene(Scene scene) {
//		scenes[count++]=scene;
//	}

	public void changeScene(Scene scene) {
		if (currentScene != null) {
			currentScene.sceneEnd();
		}
		if (scene instanceof Game_MainScene) {
			mainScene = scene;
		}
		currentScene = scene;
		currentScene.sceneBegin();
	}

	public void sceneUpdate(CommandWrapper commands) {
		if (commands != null && currentScene.getCommandListener() != null) {
			commands.actionCommand(currentScene.getCommandListener());
		}
		if (commands != null && currentScene.getKeyCommandListener() != null) {
			commands.actionCommand(currentScene.getKeyCommandListener());
		}
		if (commands != null && currentScene.getMouseCommandListener() != null) {
			commands.actionCommand(currentScene.getMouseCommandListener());
		}
		currentScene.sceneUpdate();
	}

	public Scene[] getMainMenu() {
		return mainMenu;
	}

	public Scene[] getGameMenu() {
		return gameMenu;
	}

	public Scene getCurrentScene() {
		return currentScene;
	}

	public Scene getMainScene() {
		return mainScene;
	}

	public void setMainScene(Scene scene) {
		mainScene = scene;
	}

	public void paint(Graphics g) {
		currentScene.paint(g);
	}

}
