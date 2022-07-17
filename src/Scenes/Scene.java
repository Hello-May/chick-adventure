package Scenes;

import java.awt.Graphics;

import Controllers.SceneController;
import Controllers.StoryController.Trigger;
import GameObject.Actor;
import IO.CommandSolver.KeyCommandListener;

public abstract class Scene implements Trigger {
	protected SceneController sceneController;
	protected Actor actor;
	protected int[][] groundLocation;
	protected int[][] objectsLocation;
	protected int[][] coverLocation;
	protected Camera camera;
	protected int storyFlow1;
	protected int entrance;

	public Scene(SceneController sceneController) {
		this.sceneController = sceneController;
	}

	public abstract void sceneBegin();

	public abstract void sceneUpdate();

	public abstract void sceneEnd();

	public abstract void paint(Graphics g);

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public abstract KeyCommandListener getKeyCommandListener();

	public abstract KeyCommandListener getMouseCommandListener();

	@Override
	public void notify(int storyFlow1) {
		this.storyFlow1 = storyFlow1;
	}

	public void setEntrance(int entrance) {
		this.entrance = entrance;
	}

	public int getEntrance() {
		return this.entrance;
	}

	public Camera getCamera() {
		return this.camera;
	}

}
