package Controllers;

import java.util.ArrayList;

import Scenes.Scene;
import Utils.Global;

public class StoryController {
	public interface Trigger {
		public void notify(int storyFlow);
	}

	private ArrayList<Scene> sceneObserve;
	private static StoryController sc;
	private int storyFlow1;

	private StoryController() {
		storyFlow1 = 0;
		sceneObserve = new ArrayList<>();
	}

	public static StoryController getInstance() {
		if (sc == null) {
			sc = new StoryController();
		}
		return sc;
	}

	public void bind(Scene scene) {
		this.sceneObserve.add(scene);
	}

	public void remove(Scene scene) {
		this.sceneObserve.remove(scene);
	}

	public void notifyChanged() {
		for (Scene scene : sceneObserve) {
			scene.notify(storyFlow1);
		}
	}

	public void nextSF1() {
		storyFlow1++;
		notifyChanged();
	}

	public int getSF1() {
		return storyFlow1;
	}

	public void setSF1(int storyFlow1) {
		this.storyFlow1 = 0;
		for (int i = 0; i < storyFlow1; i++) {
			nextSF1();
		}
	}
}
