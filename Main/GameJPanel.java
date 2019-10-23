/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gametest5th;

import controllers.SceneController;
import io.CommandSolver.CommandWrapper;
import scene.BattleScene;
import java.awt.*;
import javax.swing.JPanel;


/**
 *
 * @author user1
 */
public class GameJPanel extends JPanel { //場景管理

    private SceneController sceneController;

    public static final int BATTLE = 1;

    public GameJPanel() {
        sceneController = new SceneController();
//        sceneController.changeScene(new MainScene(sceneController));
        sceneController.changeScene(new BattleScene(sceneController));

    }

    public void update(CommandWrapper commands) {
        sceneController.sceneUpdate(commands);

    }

    @Override
    public void paintComponent(Graphics g) {
        sceneController.paint(g);

    }
}
