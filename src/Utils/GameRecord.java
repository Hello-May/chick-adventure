package Utils;

import java.io.Serializable;

import Utils.ActorRecord;

public class GameRecord implements Serializable {
	static final long serialVersionUID = 111144l;
	private ActorRecord actor;
	private int storyFlow;

	public GameRecord(ActorRecord actor, int storyFlow) {
		this.actor = actor;
		this.storyFlow = storyFlow;
	}

	public ActorRecord getActorRecord() {
		return actor;
	}

	public int getStory() {
		return storyFlow;
	}
}