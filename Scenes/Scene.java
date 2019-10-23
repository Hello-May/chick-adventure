package Scenes;

import java.awt.Graphics;

import Controllers.SceneController;
import GameObject.Actor;
import IO.CommandSolver.KeyCommandListener;

public abstract class Scene {
	protected SceneController sceneController;
	protected Actor actor;
	protected int [][] groundLocation;
	protected int [][] objectsLocation;
	protected int [][] coverLocation;
	protected Camera camera;
//	protected int mapXCount;
//	protected int mapYCount;
//	protected int mapXBound;
//	protected int mapYBound;

	public Scene(SceneController sceneController) {
		this.sceneController = sceneController;		
//		mapXBound=14;
//		mapYBound=9;
	}

	public abstract void sceneBegin();

	public abstract void sceneUpdate();

	public abstract void sceneEnd();

	public abstract void paint(Graphics g);
	
	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public abstract KeyCommandListener getCommandListener();

}
