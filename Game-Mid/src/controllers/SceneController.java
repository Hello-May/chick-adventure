/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import io.CommandSolver.CommandWrapper;
import java.awt.Graphics;
import javax.swing.JPanel;
import scene.Scene;

/**
 *
 * @author user1
 */
// State Pattern 狀態模式
public class SceneController { //按鈕
    
    private Scene currentScene;
    
    public void changeScene(Scene scene){
        if(currentScene != null){
            currentScene.sceneEnd();
        }
        currentScene = scene;
        currentScene.sceneBegin();
        
    }
    
    public void sceneUpdate(CommandWrapper commands){
        if(commands != null && currentScene.getKeyCommandListener() != null){
            commands.actionCommand(currentScene.getKeyCommandListener());
        }
        if(commands != null && currentScene.getMouseCommandListener() != null){
            commands.actionCommand(currentScene.getMouseCommandListener());
        }
        currentScene.sceneUpdate();
    }
    
    public void paint(Graphics g){
        
        currentScene.paint(g);
    }
    
}
