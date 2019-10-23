package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import Controllers.MusicController;
import IO.CommandSolver;
import Utils.Global;
import Values.MusicPath;
import Values.PathBuilder;

public class Main {

	public static void main(String[] args){	
		
		MusicController bgm;
		bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B1));
//		bgm.playBgMusic();
		bgm.play();

		JFrame j = new JFrame("RPG遊戲");
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setSize(Global.WINDOWS_WIDTH, Global.WINDOWS_HEIGHT);

		int x = Global.SCREEN_WIDTH / 2 - Global.WINDOWS_WIDTH / 2;
		int y = Global.SCREEN_HEIGHT / 2 - Global.WINDOWS_HEIGHT / 2;
		j.setLocation(x, y - 15);

		GameJPanel jp = new GameJPanel();
		j.add(jp);

		j.setVisible(true);
		
//		java.awt.EventQueue.invokeLater(new java.lang.Runnable(){
//            @Override 
//            public void run(){      	
//            	  j.addComponentListener(new ComponentAdapter(){
//            		  @Override 
//            		  public void componentResized(ComponentEvent e){
//            			  SwingUtilities.invokeLater(new Runnable(){
//            		           public void run() {
//            			 j.setSize(500, 500);
//            		           }
//            		      });
//            			  
//            		  }
//            	  });        	
//            	  j.setVisible(true);         	
//            }
//		 });
//		

		CommandSolver cs = new CommandSolver.Builder(
				Global.MILLISEC_PER_UPDATE,
				new int[][] {
					{ KeyEvent.VK_W, Global.UP }, 
					{ KeyEvent.VK_A, Global.LEFT },
					{ KeyEvent.VK_S, Global.DOWN }, 
					{ KeyEvent.VK_D, Global.RIGHT },
					{ KeyEvent.VK_SPACE, Global.SPACE }, 
					{ KeyEvent.VK_ESCAPE, Global.ESC },
					{ KeyEvent.VK_ENTER, Global.ENTER }
					}).enableMouseTrack(jp).keyCleanMode().gen();
//                  }).enableMouseTrack(j).keyTypedMode().gen(); //戰鬥場景用
//					}).enableMouseTrack(jp).trackChar().gen();
		addKeyListener(j, cs);
		cs.start();

		long startTime = System.currentTimeMillis();
		long lastRepaintTime = System.currentTimeMillis();
		long passedFrames = 0;
		while (true) {
			long currentTime = System.currentTimeMillis();
			long totalTime = currentTime - startTime;
			long targetTotalFrames = totalTime / Global.MILLISEC_PER_UPDATE;
			// input
			// input end
			while (passedFrames < targetTotalFrames) {
				jp.update(cs.update());
				passedFrames++;
			}
			if (Global.LIMIT_DELTA_TIME <= currentTime - lastRepaintTime) {
				lastRepaintTime = currentTime;
				j.repaint();
			}
		}

	}

	public static void addKeyListener(JFrame j, CommandSolver cs) {
		j.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				cs.trig(e.getKeyCode(), true);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				cs.trig(e.getKeyCode(), false);
			}
		});
		j.setFocusable(true);
	}

}
