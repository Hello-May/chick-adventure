package Main;

import java.awt.Graphics;
import javax.swing.*;

import Controllers.MusicController;
import Controllers.SceneController;
import IO.CommandSolver.CommandWrapper;
import Values.ImagePath;
import Values.PathBuilder;

public class GameJPanel extends JPanel {
	
	private SceneController sceneController;

	public GameJPanel() {
//		setPreferredSize(new Dimension(100,100));
		sceneController = new SceneController();
	}

	public void update(CommandWrapper commands) {
		sceneController.sceneUpdate(commands);
	}

	@Override
	public void paint(Graphics g) {
		sceneController.paint(g);
	}
	

}
