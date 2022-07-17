package Controllers;

import java.awt.Graphics;

import GameObject.Actor;
import IO.CommandSolver.CommandWrapper;
import Scenes.Menu_Instructions;
import Scenes.Menu_Menu;
import Scenes.Game_BlackScene;
import Scenes.Game_MainScene;
import Scenes.Game_Over;
import Scenes.Scene;

public class SceneController {
	private Scene menuScene;
	private Scene instructionsScene;
	private Scene mainScene;
	private Scene currentScene;
	private Scene blackScene;
	private Scene gameOverScene;

	public SceneController() {
		menuScene = new Menu_Menu(this);
		instructionsScene = new Menu_Instructions(this);
		blackScene = new Game_BlackScene(this);
		gameOverScene = new Game_Over(this);
		changeScene(menuScene);
	}

	public void changeScene(Scene scene, int entrance, Actor actor) {
		blackScene.setEntrance(entrance);
		blackScene.setActor(actor);
		changeScene(scene);
	}

	public void changeScene(Scene scene) {
		if (currentScene != null) {
			StoryController.getInstance().remove(scene);
			currentScene.sceneEnd();
		}
		if (scene instanceof Game_MainScene) {
			mainScene = scene;
		}
		currentScene = scene;
		StoryController.getInstance().bind(scene);
		currentScene.sceneBegin();
	}

	public void sceneUpdate(CommandWrapper commands) {
		if (commands != null && currentScene.getKeyCommandListener() != null) {
			commands.actionCommand(currentScene.getKeyCommandListener());
		}
		if (commands != null && currentScene.getMouseCommandListener() != null) {
			commands.actionCommand(currentScene.getMouseCommandListener());
		}
		currentScene.sceneUpdate();
	}

	public Scene getGameOverScene() {
		return gameOverScene;
	}

	public Scene getBlackScene() {
		return blackScene;
	}

	public Scene getInstructionsScene() {
		return instructionsScene;
	}

	public Scene getMenuScene() {
		return menuScene;
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