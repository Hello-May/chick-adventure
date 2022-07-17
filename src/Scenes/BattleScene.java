package Scenes;

import fighting.Button;
import Controllers.ImageController;
import Controllers.MusicController;
import fighting.DirectionGroup;
import fighting.Status;
import fighting.StatusBar;
import IO.CommandSolver.KeyCommandListener;
import Values.PathBuilder;
import Controllers.SceneController;
import Controllers.StoryController;
import GameObject.Actor;
import GameObject.BattleActor;
import Utils.Global;
import Values.ImagePath;
import Values.MusicPath;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import popup.SkillPopUpWindow;
import popup.VictoryPopUpWindow;

public class BattleScene extends Scene {

	private BattleActor chicken;
	private BattleActor emeny;
	private BufferedImage img;
	private Button menu;
	private DirectionGroup dg;
	private Status playerStatus;
	private Status enemyStatus;
	private StatusBar playerBar;
	private StatusBar enemyBar;
	private KeyCommandListener keyCommandListener;
	private KeyCommandListener menuCommandListener;
	private SkillPopUpWindow skillPopUpWindow;
	private VictoryPopUpWindow victoryPopUpWindow;
	private MusicController bgm;
	private static StoryController storyController = StoryController.getInstance();
	private Actor target;
	private int[] random = { 0, 0, 0, 1, 1, 1, 1, 2, 2, 2 };// 0是攻擊1是暴擊2是放技能(敵人的招式機率)
	private int skillIndex;

	public BattleScene(SceneController sceneController, Actor chick, Actor target) {
		super(sceneController);
		chicken = new BattleActor(720, 450, 96, 96, Global.ACT_SPEED, chick.getStatus(), chick.getPicture());
		chicken.setMood(0);
		playerBar = new StatusBar(Global.WINDOWS_WIDTH - 200 - 30, 10, 200, 85, chick.getStatus());
		emeny = new BattleActor(296 - target.getWidth() * 3, 546 - target.getHeight() * 3, target.getWidth() * 3,
				target.getHeight() * 3, Global.ACT_SPEED, target.getStatus(), target.getPicture());
		enemyBar = new StatusBar(10, 10, 200, 85, target.getStatus());
		this.target = target;
		img = getImage(0);
		img = getImage(1);

		keyCommandListener = menuCommandListener = new KeyCommandListener() {

			@Override
			public void keyPressed(int commandCode, long time) {
			}

			@Override
			public void keyReleased(int commandCode, long time) {
				switch (commandCode) {
				case Global.LEFT:
					menu.lastChoose();
					break;
				case Global.RIGHT:
					menu.nextChoose();
					break;
				case Global.ENTER:
					menu.onClick();
					break;
				case Global.ESC:
					if (emeny.getStatus().getCurrentHP() <= 0) {
						target.setIsDead(true);
						if (target.getStatus() == Global.STATUS_BIG_SLIME) {
							storyController.nextSF1();
						}
						sceneController.changeScene(sceneController.getMainScene());
					}

				}
			}
		};
	}

	private BufferedImage getImage(int tmp) {
		ImageController irc = ImageController.getInstance();
		if (tmp == 0) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.BattleScene.GRASSLAND));
		}
		if (tmp == 1) {
			return irc.tryGetImage(PathBuilder.getImg(ImagePath.BattleScene.SWORD));
		}
		return null;
	}

	@Override
	public void sceneBegin() {
		bgm = new MusicController(PathBuilder.getAudio(MusicPath.BGM.B3));
		bgm.play();

		menu = new Button(400, 550, 48, 57);
		menu.setButtonListener(new Button.ButtonListener() {

			@Override
			public void onClick(int count) {
				if (emeny.getMood() == -2) {
//					

					chicken.setMood(0);

//    
//    if (emeny.getStatus().getCurrentHP() <= 0) {    
//     victoryPopUpWindow = new VictoryPopUpWindow(250, 200, 500, 200, chicken);
//    }
					switch (count) {
					case 0:// 攻擊
						if (chicken.getX() == 720 && emeny.getX() <= 200 && emeny.getMood() == -2) {// 只監聽按下瞬間的X
//						System.out.println("A");
							emeny.setMood(-3);
							chicken.setUseSkillIndex(0);
							chicken.changeDirection(Global.LEFT);
							emeny.changeDirection(Global.RIGHT);
//      chicken.useSkill(emeny, count);

//      emeny.setMood(-3);
//      if (emeny.getStatus().getCurrentHP() > 0) {
//       emeny.startSkillDelay();// 一定要放這裡 不然enter太快 敵人會打兩次
//      }

						}
						break;
					case 1:// 技能
						skillPopUpWindow = new SkillPopUpWindow(445, 470, 140, 60);
						skillPopUpWindow.setOnClickListener(new SkillPopUpWindow.ButtonListener() {
							@Override
							public void onClick(int count) {
								if (emeny.getMood() == -2) {
									if ((count == 0 && chicken.getStatus()
											.getCurrentMP() >= chicken.getStatus().getAttack() * 2)
											|| (count == 1 && chicken.getStatus()
													.getCurrentMP() >= chicken.getStatus().getAttack() * 3)
											|| (count == 2 && chicken.getStatus()
													.getCurrentMP() >= chicken.getStatus().getAttack() * 4)) {
										chicken.setMpIndication(-1);// 可以按進來表示還有魔
										dg = new DirectionGroup(340, 400, 51, 54,
												new DirectionGroup.OnCompleteListener() {
													@Override
													public void success() {
														dg = null;
														chicken.setMood(1);// 成功畫愛心
														emeny.setMood(-3);
														switch (count) {
														case 0:
															chicken.resetSkillCount();// 才會從第一張圖開始畫
															chicken.startSkillDelay();
															chicken.setUseSkillIndex(1);
															chicken.setPaintSkillIndex(1);// 當前要畫出哪個技能
															chicken.changeDirection(Global.LEFT);
															emeny.changeDirection(Global.RIGHT);

															break;
														case 1:
															chicken.resetSkillCount();
															chicken.startSkillDelay();
															chicken.setUseSkillIndex(2);
															chicken.setPaintSkillIndex(2);
															chicken.changeDirection(Global.LEFT);
															emeny.changeDirection(Global.RIGHT);

															break;
														case 2:
															chicken.resetSkillCount();
															chicken.startSkillDelay();
															chicken.setUseSkillIndex(3);
															chicken.setPaintSkillIndex(3);
															chicken.changeDirection(Global.LEFT);
															emeny.changeDirection(Global.RIGHT);

															break;
														}

														keyCommandListener = menuCommandListener;
													}

													@Override
													public void failed() {
														emeny.setMood(-3);
														dg = null;
														chicken.setMood(-1);// 失敗畫冒汗
														emeny.startSkillDelay();
														keyCommandListener = menuCommandListener;
													}
												});
										keyCommandListener = dg.getKeyCommandListener();
										skillPopUpWindow = null;
									} else {
										chicken.setMpIndication(1);// 畫出法力不足
										keyCommandListener = menuCommandListener;
										// currentWindow = null; //也可以null啦
									}
								}
							}

						});

						break;

					case 2:
						if (emeny.getStatus().getCurrentHP() > 0) {// >0才可以逃跑<強制怪死掉的時候只能按esc返回村莊,這樣才能把死掉的怪清掉>
							sceneController.changeScene(sceneController.getMainScene());
						}
						break;
					}
				}
//    chicken.setMood(0);// 畫出點點點
			}
		});

	}

	@Override
	public void sceneUpdate() {
//		System.out.println("mood" + chicken.getMood());
//		System.out.println("X" + emeny.getX() + "direction:" + emeny.getDirection());
//		System.out.println("direction:" + chicken.getDirection());
//		System.out.println("index" + emeny.getUseSkillIndex());
		chicken.move();
		emeny.emenyMove();
//		System.out.println("direction:" + chicken.getDirection() + "mood" + chicken.getMood());

		if (emeny.getDirection() == 1) {
			chicken.changeDirection(0);
		}
		if (chicken.getDirection() == 2) {
			emeny.changeDirection(0);
		}

		if (chicken.getStatus().getCurrentHP() <= 0) {
			sceneController.changeScene(new Game_Over(sceneController));
		}
		if (emeny.getStatus().getCurrentHP() <= 0) {
			victoryPopUpWindow = new VictoryPopUpWindow(250, 200, 500, 250, chicken);
			emeny.disActor();// 獲勝的時候把敵人畫面清掉(不能null會出錯)
		}
		if (skillPopUpWindow != null) {
			skillPopUpWindow.windowUpdate();
			if (skillPopUpWindow.off() == -1) {
				skillPopUpWindow = null;
				chicken.setMpIndication(-1);// 把法力不足的字清掉
			}
		}
		if (dg != null) {
			dg.move();
		}
		if (emeny.getSkillDelay().updatePause()) {
			skillIndex = -1;
			while (skillIndex == -1
					|| (skillIndex == 1 && emeny.getStatus().getCurrentMP() < emeny.getStatus().getAttack() * 2)
					|| (skillIndex == 2 && emeny.getStatus().getCurrentMP() < emeny.getStatus().getAttack() * 3)) {

				skillIndex = random[(int) (Math.random() * 10)];
			} // 電腦隨機選取敵人招式(魔不足會重新選取)

			if (skillIndex == 0) {
				emeny.setUseSkillIndex(skillIndex);
				emeny.setPaintSkillIndex(4);
				chicken.setMood(-3);
				emeny.changeDirection(Global.RIGHT);
				chicken.changeDirection(Global.LEFT);
			} else {
				if (skillIndex == 1) {
					emeny.setUseSkillIndex(skillIndex);
					emeny.setPaintSkillIndex(5);
				}
				if (skillIndex == 2) {
					emeny.setUseSkillIndex(skillIndex);
					emeny.setPaintSkillIndex(6);
				}
				chicken.setMood(-3);
				emeny.changeDirection(Global.RIGHT);
				chicken.changeDirection(Global.LEFT);
			}

		}

		if (emeny.getDirection() == 2 && (skillIndex == 0 || skillIndex == 1)) {
			if (emeny.getX() == chicken.getX() && chicken.getMood() == -3) {

				emeny.useSkill(chicken, skillIndex);

				chicken.setMood(8);// 畫生氣
				emeny.getSkillDelay().stop();

			}

		}

		if (emeny.getDirection() == 2 && skillIndex == 2) {
//			System.out.println(emeny.getX());
//			System.out.println(skillIndex);
			if (emeny.getX() == 300) {
				emeny.useSkill(chicken, skillIndex);

				chicken.setMood(8);// 畫生氣
				emeny.getSkillDelay().stop();
			}
		}

		if (chicken.getDirection() == 1 && chicken.getX() == emeny.getX() && emeny.getMood() == -3
				&& chicken.getMood() == 0) {
			chicken.useSkill(emeny, 0);

//			chicken.setMood(-2);
			emeny.setMood(-3);
			if (emeny.getStatus().getCurrentHP() > 0) {
				emeny.startSkillDelay();
			}
		}
		if (chicken.getDirection() == 1 && chicken.getX() == 620 && emeny.getMood() == -3 && chicken.getMood() == 1) {
			chicken.useSkill(emeny, chicken.getUseSkillIndex());

//			chicken.setMood(-2);
			emeny.setMood(-3);
			if (emeny.getStatus().getCurrentHP() > 0) {
				emeny.startSkillDelay();
			}
		}

		if (chicken.getStatus().getCurrentHP() <= 0) {
			sceneController.changeScene(new Game_Over(sceneController));
		}

	}

	@Override
	public void sceneEnd() {
		bgm.stop();
		if (emeny.getStatus().getCurrentHP() <= 0) {
			chicken.getStatus().setTotalHP(chicken.getStatus().getTotalHP() + chicken.getStatus().getLevel() * 10);
			chicken.getStatus().setTotalMP(chicken.getStatus().getTotalMP() + chicken.getStatus().getLevel() * 10);
			chicken.getStatus().setAttack(chicken.getStatus().getAttack() + 5);
			chicken.getStatus().setLevel((chicken.getStatus().getLevel() + 1));
			chicken.setSpeed(0);
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(getImage(0), 0, 0, 1000, 700, null);
		g.drawImage(getImage(1), 450, 20, 100, 89, null); // 114 103

		emeny.paint(g);
		chicken.paint(g);
		emeny.paintEmenyAttack(g);

		if (dg != null) {
			dg.paint(g);
		}

		playerBar.paint(g);
		enemyBar.paint(g);

		menu.paint(g);

		if (skillPopUpWindow != null) {
			skillPopUpWindow.paint(g);
		}
		if (victoryPopUpWindow != null) {
			victoryPopUpWindow.paint(g);
		}

	}

	@Override
	public KeyCommandListener getKeyCommandListener() {
		return (skillPopUpWindow == null) ? keyCommandListener : skillPopUpWindow.getKeyCommandListener();
	}

	@Override
	public KeyCommandListener getMouseCommandListener() {
		return null;
	}
}