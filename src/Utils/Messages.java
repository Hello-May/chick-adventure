package Utils;

import java.util.ArrayList;

import Controllers.MusicController;
import Controllers.SceneController;
import Controllers.StoryController;
import GameObject.Actor;
import GameObject.GameObject;
import Scenes.BattleScene;
import Scenes.Game_AfterEnd;
import Scenes.Game_End;
import Scenes.Game_MainScene;
import Scenes.Game_Over;
import Scenes.NewGameHintScene;
import Values.ImagePath;
import Values.MusicPath;
import Values.PathBuilder;

public class Messages {
	private static StoryController storyController = StoryController.getInstance();
	private static MusicController sound;
	
	public interface Event {
		public void action();
	}

	public static class MsgFlow {
		private Msg root;
		private Msg currentMsg;

		public MsgFlow(Msg msg) {
			currentMsg = root = msg;
		}

		public boolean choose(int i) {
			if (currentMsg == null || i >= currentMsg.options.size()) {
				return false;
			}
			Option opt = currentMsg.options.get(i);
			// do event
			if (opt == null) {
				return false;
			}

			if (opt.event != null) {
				opt.event.action();
			}
			// change mMsg
			currentMsg = opt.next;
			return true;
		}

		public void reset() {
			currentMsg = root;
		}

		public int getOptSize() {
			if (currentMsg == null) {
				return 0;
			}
			return currentMsg.options.size();
		}

		public String getAvatarPath() {
			if (currentMsg == null) {
				return "";
			}
			return currentMsg.avatarPath;
		}
		
		public String getName() {
			if (currentMsg == null) {
				return "";
			}
			return currentMsg.name;
		}
		
		public String getMsgText() {
			if (currentMsg == null) {
				return "";
			}
			return currentMsg.msg;
		}

		public Option getOpt(int i) {
			if (currentMsg == null) {
				return null;
			}
			return currentMsg.getOpt(i);
		}
	}

	public static class Msg {
		private String msg;
		private ArrayList<Option> options;
		private String avatarPath;
		private String name;

		public Msg(String hint) {
			this.msg = hint;
			options = new ArrayList<>();
			avatarPath = PathBuilder.getImg(ImagePath.Bird.Facial.B0);
//			name = "";
			name = Global.CHICK_NAME;
		}
		
		public Msg(String hint, String avatarPath, String name) {
			this.msg = hint;
			options = new ArrayList<>();
			this.avatarPath = avatarPath;
			this.name = name;
		}

		public Msg addOption(String hint) {
			return addOption(hint, null, null);
		}

		public Msg addOption(String hint, Msg next) {
			return addOption(hint, next, null);
		}

		public Msg addOption(String hint, Msg next, Event event) {
			options.add(new Option(hint, next, event));
			return this;
		}

		public Option getOpt(int i) {
			return options.get(i);
		}

		public static Msg normal(String msg) {
			Msg m = new Msg(msg);
			m.addOption("是");
			return m;
		}

		public static Msg yesNo(String msg, Event yes, Event no) {
			Msg m = new Msg(msg);
			m.addOption("是", new Msg("回到村莊！"), yes);
			m.addOption("否", new Msg("選擇不靠近……"), no);
			return m;
		}
	}

	public static class Option {
		public String hint;
		public Msg next;
		public Event event;

		public Option(String hint, Msg next, Event event) {
			this.hint = hint;
			this.next = next;
			this.event = event;
		}
	}

	public static MsgFlow quickGen(SceneController sc, Actor ac, GameObject obj) {
		String[] tmpArr;
		String[] tmpPath=new String[2];
		String msg;
		Msg m;
		int pic;
		if(obj == null) {
			pic =- 10;
		}else {
			pic = obj.getPicture();
		}
		if(pic >= 120 && pic < 136) {
			tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
			tmpPath[1]="村民";
			msg = "村民們像是失去理智般，一臉猙獰的朝你衝過來！";				
			m = new Msg(msg, tmpPath[0], tmpPath[1]);
			m.addOption("是",new Msg("戰鬥結束！"),new Event() {
				@Override
				public void action() {
					sc.changeScene(new BattleScene(sc,ac,(Actor)obj));
				}
			});
			return new MsgFlow(m);
		}
		if(pic >= 80 && pic < 110 && pic != 86 && pic != 102 ) {
			tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
			tmpPath[1]="村民";
			if(storyController.getSF1() == 3) {
				m = new Msg("你好，不好意思……");
				m.addOption("繼續", new Msg("（解釋事情來龍去脈）如此這般………")
								.addOption("繼續", new Msg("喔喔喔！你是說那個誰誰誰家的孩子們嗎？",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("痾……對喔！……應該吧～")
								.addOption("繼續", new Msg("那個誰誰誰家的爸媽，這時間點都會出沒在商店街！",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("好喔！我知道了～（OS：好可怕！隨便一個村民都清楚別人家的行蹤！）")
								.addOption("繼續", new Msg("你去借用商店街的大會廣播，就可以找到他們喔！",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("好的！謝謝你～"))))))));
				return new MsgFlow(m);
			}else if (storyController.getSF1() >= 10) {
				tmpArr= new String[]{"…………（神色匆忙）","…………（快速離去）","……………（不予搭理）"};
				return new MsgFlow(new Msg(tmpArr[(int)(Math.random()*3)],tmpPath[0], tmpPath[1]));
			}else {
				tmpArr= new String[]{"可愛純樸的村民們！","今天的村民們見到我也很熱情！","親切的打招呼～"};
				return new MsgFlow(new Msg(tmpArr[(int)(Math.random()*3)]));
			}
		}
		if(pic >= 40 && pic < 45) {
			tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
			tmpPath[1]="小動物";
			if(storyController.getSF1() >= 10) {
				tmpArr= new String[]{"…………（一臉不屑樣）","哼……（用鼻子噴氣）……","…………（斜眼瞪視著）"};
				return new MsgFlow(new Msg(tmpArr[(int)(Math.random()*3)],tmpPath[0], tmpPath[1]));
			}else {
				tmpArr= new String[]{"可愛的小動物們！","卡哇依ㄋㄟ～好療癒阿～","健康又鮮活的小生命啊～"};
				return new MsgFlow(new Msg(tmpArr[(int)(Math.random()*3)]));
			}
		}
		switch (pic) {	
			//////////////////// 劇情start////////////////////////////////
			case Global.GROUND_OBSTACLE_STORE:
				if(storyController.getSF1() >= 15) {
					return new MsgFlow(new Msg("現在不是進入的時機了！"));
				}
				if(storyController.getSF1() < 3) {
					return new MsgFlow(new Msg("平常供應村莊生活用品的商店街，現在還沒到營業時間！"));
				}
				if(storyController.getSF1() == 3) {
					m = new Msg("這裡就是商店街了嗎？");
					m.addOption("繼續", new Msg("已經開始營業了呢！")
							.addOption("繼續", new Msg("事不宜遲，趕快進入吧！")
							.addOption("進入", new Msg("回到主村莊！"),new Event() {
								@Override
								public void action() {
									sc.changeScene(sc.getBlackScene(),pic,ac);
									ac.changeDirection(Global.DOWN);
								}					
							}
							)));
					return new MsgFlow(m);
				}
				if(storyController.getSF1() > 3) {
					m = new Msg("這裡就是就是上次來過的商店街");
					m.addOption("繼續", new Msg("現在依然在營業呢！")
							.addOption("繼續", new Msg("要進去嗎？")
									.addOption("進入",new Msg("回到主村莊！"),new Event() {
								@Override
								public void action() {
									sc.changeScene(sc.getBlackScene(),pic,ac);
									ac.changeDirection(Global.DOWN);
								}					
							})).addOption("不了", new Msg("唉～怎麼說呢？所謂一文錢難倒英雄漢阿！")));
					return new MsgFlow(m);
				}
			case Global.VILLAGE_HEAD:
				tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.VILLAGE_HEAD);
				tmpPath[1]="村長";
				switch(storyController.getSF1()) {
				case 1:
					msg = "雞化身的勇者大人終於親臨這個世界了！";
					m = new Msg(msg, tmpPath[0], tmpPath[1]);
					m.addOption("蛤", new Msg("蛤？（一臉懵逼）")
							.addOption("繼續", new Msg("我是這個村莊的村長。",tmpPath[0], tmpPath[1])
							.addOption("繼續", new Msg("請問……這裡是？")
							.addOption("詢問", new Msg("您是因為受到需要幫助的村民們所呼喚而來的！",tmpPath[0], tmpPath[1])
							.addOption("繼續", new Msg("痾……那個……")
							.addOption("嘗試再次詢問", new Msg("現在村子裡發現大事了！",tmpPath[0], tmpPath[1])
							.addOption("繼續", new Msg("……我……你…………")
							.addOption("嘗試打斷村長", new Msg("也許您心中有很多疑問……",tmpPath[0], tmpPath[1])
							.addOption("繼續", new Msg("是…………（黑人問號）")
							.addOption("放棄打斷", new Msg("但來不及跟您解釋這麼多了！",tmpPath[0], tmpPath[1])
							.addOption("……", new Msg("…………QQ")
							.addOption("繼續", new Msg("請勇者大人趕快移駕過去！",tmpPath[0], tmpPath[1]),new Event() {
								@Override
								public void action() {
									storyController.nextSF1();	// => 劇情2
								}	
							})
							))))))))))).addOption("啥小",new Msg("我不聽！我不聽！我不聽！（捂住雙耳）"));
					return new MsgFlow(m);
				case 2:
					msg = "事態緊急，請勇者大人趕快移駕過去！";
					m = new Msg(msg,tmpPath[0], tmpPath[1]);
					m.addOption("繼續",new Msg("等等……")
							.addOption("繼續",new Msg("所以說是哪裡啦！？(崩潰狀)")));
					return new MsgFlow(m);
				case 3:
				case 4:
				case 5:
					m = new Msg("史萊姆的事件，很好的解決了呢！",tmpPath[0], tmpPath[1]);
					m.addOption("繼續", new Msg("不愧是勇者大人！村民們都很感謝你！",tmpPath[0], tmpPath[1])
						.addOption("繼續", new Msg("是呢……！（OS:黏答答的史萊姆液洗了我三天三夜）")
						.addOption("繼續",new Msg("請勇者大人暫時安居在村子中吧！",tmpPath[0], tmpPath[1])
								.addOption("啊！", new Msg("……咦等等！說好的解釋呢？")))));
					return new MsgFlow(m);
				default:
					msg = "喔呵呵～我是這個村莊的村長！";
					m = new Msg(msg,tmpPath[0], tmpPath[1]);
					return new MsgFlow(m);
				}
			case Global.GROUND_KEY_INSLIME:	// 打完大史萊姆 => 劇情3
				msg = "這是史萊姆的殘渣，它們的主食是……垃圾跟雜物？";
				m = new Msg(msg);
				m.addOption("打掃", new Msg("（翻弄中）其實史萊姆也算是傑出的清道夫……有它環境會更好")
						.addOption("自言自語", new Msg("就跟喇牙會吃蚊蟲蟑螂……儘管喇牙本身就很可怕！")
								.addOption("自言自語", new Msg("有個奇怪的垃圾～～是張紙條！")
								.addOption("自言自語", new Msg("到底為啥這麼說呢？因為它就像要給我線索般給我訊息")
								.addOption("迷：是要對誰講解？", new Msg("畫了一棵樹，樹下有個閃閃發光的東西……")
								.addOption("自言自語", new Msg("嗯……好喔？這麼多樹，我找總行吧！")
								.addOption("迷：夠了!停止自言自語", new Msg("先把村民的小朋友送回去吧！")
										)))))))
					.addOption("噁～",new Msg("喔不！這我不行，這惡臭，容我去旁邊吐一下！"));
				return new MsgFlow(m);
			case 70:	//被史萊姆欺負的村民
			case 71:
			case 73:
				tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
				tmpPath[1]="村中的小朋友";
				switch(storyController.getSF1()) {
				case 2:
					msg = "嗚嗚嗚～";
					m = new Msg(msg, tmpPath[0], tmpPath[1]);
					m.addOption("安撫", new Msg("哇嗚嗚嗚嗚嗚～", tmpPath[0], tmpPath[1])
							.addOption("嗯?（倒退三步）", new Msg("OS：怎麼哭更兇了！？")
							.addOption("繼續", new Msg("秀秀齁～發生什麼事跟大哥哥說喔～")
							.addOption("繼續", new Msg("雞哥哥～黏黏怪欺負我們嗚嗚嗚～", tmpPath[0], tmpPath[1])
							.addOption("……", new Msg("OS：雞哥哥？")
							))))).addOption("賣靠",new Msg("別哭了熊孩子！"));
					return new MsgFlow(m);
				case 3:	
					msg = "雞哥哥謝謝你～";
					m = new Msg(msg, tmpPath[0], tmpPath[1]);
					m.addOption("繼續", new Msg("哈！舉手之勞～你們也趕快回家吧！")
							.addOption("繼續", new Msg("可是……身體痛痛……無法走回去了……", tmpPath[0], tmpPath[1])
									.addOption("安撫", new Msg("呼呼～痛痛飛走了～痛痛搭著火箭回外太空囉！")		//選項一
											.addOption("繼續", new Msg("雞哥哥騙人～哇嗚嗚～還是痛痛～～～", tmpPath[0], tmpPath[1])
											.addOption("繼續", new Msg("…………（雞哥哥受到精神傷害-50MP）"),new Event() {
												@Override
												public void action() {
													ac.getStatus().setCurrentMP(ac.getStatus().getCurrentMP()-50);
												}	
											})))
									.addOption("沉默", new Msg("………………")		//選項二
											.addOption("繼續", new Msg("……………………", tmpPath[0], tmpPath[1])
											.addOption("繼續", new Msg("…………………………")
											.addOption("繼續", new Msg("……嗚嗚……（偷瞥）……", tmpPath[0], tmpPath[1])
											.addOption("繼續", new Msg("………………………………")
											.addOption("繼續", new Msg("………………（大眼瞪小眼們）", tmpPath[0], tmpPath[1])))))))	
									.addOption("護送", new Msg("不然你們家在哪，我送你們回家～")	//選項三
											.addOption("繼續", new Msg("但我們暫時無法動彈了，請去村莊找我們爸媽！", tmpPath[0], tmpPath[1])
											.addOption("繼續", new Msg("那你們爸媽在哪裡？")
											.addOption("繼續", new Msg("隨便找一個阿姨阿伯，他們會知道我們爸媽在哪！", tmpPath[0], tmpPath[1])
							))))));
					return new MsgFlow(m);
				}
			case Global.GROUND_SHINING:		// 跟殘渣說話、找爸媽送小朋友回家 => 劇情4
				switch(storyController.getSF1()) {
				case 4:
					msg = "奇怪，剛剛這裡有這東西嗎？";
					m = new Msg(msg);
					m.addOption("好奇", new Msg("這東西聖光霸體，害我不敢靠近……")
							.addOption("自言自語", new Msg("這些食人花就像趨光性的飛蛾，卻又不敢輕易靠近這東西")
							.addOption("自言自語", new Msg("所以才聚集這麼多在這邊徘徊不去……")
							.addOption("拿取", new Msg("想拿，但我有點怕怕～")
									.addOption("自言自語", new Msg("這附近有什麼長條物之類的可以戳一下嗎？"),new Event() {
										@Override
										public void action() {
											storyController.nextSF1();	// => 劇情5
										}					
									}))
									.addOption("不拿",new Msg("怪怪的，媽媽說不能亂撿東西！")
							)))).addOption("遠離",new Msg("這東西閃瞎我的鈦合金狗眼！"));
					return new MsgFlow(m);
				case 5:
					return new MsgFlow(new Msg("不是說要先找個長條物的東西嗎？"));
				case 6:
					msg = "喔喔～要來開箱了！";
					m = new Msg(msg);
					m.addOption("開吧", new Msg("勇者已裝備樹枝，取得開箱技能！")
							.addOption("我戳", new Msg("框啷啷……")
							.addOption("再戳", new Msg("框啷框啷框啷……")
							.addOption("用力戳", new Msg("框啷框啷框啷框啷框啷……碰！")
							.addOption("WTF", new Msg("……箱子噴裝了，是一把鑰匙，看起來就超關鍵的東西阿！")
							.addOption("觀察", new Msg("這把鑰匙上面還有個標籤，寫說在「倉庫」！")
							.addOption("觀察", new Msg("喔？這是在村莊內的某個房子嗎？"),new Event() {
									@Override
									public void action() {
										storyController.nextSF1();	// => 劇情7
									}					
								})))).addOption("不戳了",new Msg("痾……總覺得有不好的預感……")
						)))).addOption("猶豫",new Msg("要開、不開、要開、不開、要開、不開……嗯！小花說不開！"));
					return new MsgFlow(m);
				}
			case Global.GROUND_WOOD:	//劇情5
				switch(storyController.getSF1()) {
				case 5:
					msg = "長條物……樹枝應該可以吧？";
					m = new Msg(msg);
					m.addOption("可以", new Msg("就借用一根應該沒關係吧？")
							.addOption("沒關係", new Msg("等等就拿回來了啦～")
									.addOption("拿取", new Msg("感謝村民！讚嘆村民！"),new Event() {
										@Override
										public void action() {
											storyController.nextSF1();	// => 劇情6
										}					
									}))
									.addOption("有關係",new Msg("唉呦～不可以亂拿村民的血汗勞力成果！")
							)).addOption("不行",new Msg("樹枝太脆弱了！無法保護更脆弱的我～"));
					return new MsgFlow(m);
				default:
					return new MsgFlow(new Msg("村民們平常累積並取用的木材堆！"));
				}
			case Global.GROUND_OBSTACLE_HOME:	//劇情7
				if(storyController.getSF1() == 7) {
					msg = "這把鎖好像可以進入這棟房子！";
					m = new Msg(msg);
					m.addOption("敲門", new Msg("……咳！（左顧右盼）……叩叩叩……")
							.addOption("觀察", new Msg("（靜默）……好像沒人……")
							.addOption("開門", new Msg("擅闖民宅是不對的，小朋友請不要學！")
									.addOption("離開", new Msg("回到村莊！"),new Event() {
										@Override
										public void action() {
											sc.changeScene(sc.getBlackScene(),pic,ac);
											ac.changeDirection(Global.DOWN);
										}					
									}))
									.addOption("不開",new Msg("哇咧……感覺是關鍵劇情，需要點心理準備！")
							))).addOption("不敲",new Msg("先不要輕舉妄動好了！"));
					return new MsgFlow(m);
				}else if (storyController.getSF1() >= 7 && storyController.getSF1() < 14){
					return new MsgFlow(new Msg("已經進去過了！沒有必要再進去！"));
				}else if (storyController.getSF1() >= 14){
					return new MsgFlow(new Msg("現在不是進入的時機了！"));
				}else {
					return new MsgFlow(new Msg("被上鎖的房子，看起來像村民的倉庫！"));
				}
			///////////////////////////進入黑色場景//////////////////////////////////
			case -10:	//obj==null;	
				if(storyController.getSF1()==16) {	//結局
					tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
					tmpPath[1]="我";
					m = new Msg("……………………（打呼聲）",tmpPath[0],tmpPath[1]);
					m.addOption("驚醒",new Msg("阿！好痛……撞到頭了！",tmpPath[0],tmpPath[1])
					.addOption("自言自語",new Msg("…………我睡著了嗎？",tmpPath[0],tmpPath[1])
					.addOption("晃晃腦",new Msg("……頭好暈，我是睡多久啊？",tmpPath[0],tmpPath[1])
					.addOption("沉思",new Msg("（摸下巴）……剛剛我好像夢到什麼不得了的東西？",tmpPath[0],tmpPath[1])
					.addOption("沉思",new Msg("……我好像變成了一隻雞？……",tmpPath[0],tmpPath[1])
					.addOption("沉思",new Msg("……是晚餐吃太多炸雞的現世報嗎！？",tmpPath[0],tmpPath[1])
					.addOption("沉思",new Msg("……然後還遇到了很多事情……是什麼呢？……",tmpPath[0],tmpPath[1])
					.addOption("沉思",new Msg("嗯……想不起來了！……",tmpPath[0],tmpPath[1])
					.addOption("沉思",new Msg("算了！現在幾點了？",tmpPath[0],tmpPath[1])
					.addOption("看時間",new Msg("……糟了！這不是超晚的嗎？……還要DEBUG！！！",tmpPath[0],tmpPath[1])
					.addOption("欲哭無淚",new Msg("嗚嗚嗚……怎麼總覺得在夢中我也是這樣的心情呢……",tmpPath[0],tmpPath[1])
					.addOption("TRUE END", null,new Event() {
						@Override
						public void action() {
							sc.changeScene(new Game_AfterEnd(sc));
						}
					}))))))))))));
					return new MsgFlow(m);
				}
				tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
				tmpPath[1]="？？？";
				switch(sc.getBlackScene().getEntrance()) {	//黑色場景的入口處
				case 0:
					switch(storyController.getSF1()) {
					case 0:
						m = new Msg("…………？", tmpPath[0], tmpPath[1]);
						m.addOption("思考", new Msg("我是誰？我在哪？我在幹嘛？", tmpPath[0], tmpPath[1])
								.addOption("伸展身體", new Msg("嗯……怎麼覺得手怪怪的……？", tmpPath[0], tmpPath[1])
										.addOption("動動手臂", new Msg("有風從我的臂膀邊掠過……", tmpPath[0], tmpPath[1])
										.addOption("……", new Msg("……痾……有一股不祥的預感！", tmpPath[0], tmpPath[1])
										.addOption("睜開雙眼", new Msg("！！！")
										.addOption("審視自己", new Msg("我怎麼變成一隻雞了！？阿阿阿阿阿阿阿阿阿阿阿阿～～")
										.addOption("驚醒", null ,new Event() {
											@Override
											public void action() {
//												sc.changeScene(new Game_MainScene(sc));
												sc.changeScene(new NewGameHintScene(sc));
											}	
										}))))))
										.addOption("不想動彈",new Msg("身體逐漸沉重……思緒也越來越發散……", tmpPath[0], tmpPath[1])
										.addOption("BAD END",null,new Event() {
											@Override
											public void action() {
												sc.changeScene(sc.getGameOverScene());
											}					
										}))
								)
							.addOption("放棄思考",new Msg("陷入一片渾沌之中……永遠沉寂下去……", tmpPath[0], tmpPath[1])
							.addOption("BAD END",null,new Event() {
									@Override
									public void action() {
										sc.changeScene(sc.getGameOverScene());
									}					
								}));
						return new MsgFlow(m);
					case 10:	//死局
						m = new Msg("…………？？？");
						m.addOption("………", new Msg("…………我死了？")
								.addOption("………", new Msg("…………………為什麼？")
								.addOption("………", new Msg("怎麼回事！？！？！？")
								.addOption("崩潰", null,new Event() {
							@Override
							public void action() {
								sc.changeScene(sc.getGameOverScene());
							}	
						}))));
						return new MsgFlow(m);
					case 11:	//活局
						m = new Msg("………！！！");
						m.addOption("震驚", new Msg("……我靠………一情急我拿起鍋蓋………………")
								.addOption("震驚", new Msg("……居然………居然格檔成功！")
								.addOption("震驚", new Msg("哇賽！鍋蓋的正確用途！武器店老闆我誤會你了！")
								.addOption("回過神", null,new Event() {
							@Override
							public void action() {
								storyController.nextSF1(); // => 劇情12
//								storyController.nextSF1(); // => 暫時先跳到13
								ac.setStep(Global.ACTOR_STEP);
								sc.changeScene(sc.getMainScene());
							}	
						}))));
						return new MsgFlow(m);
					}
				case Global.GROUND_OBSTACLE_HOME:
					msg = "進來了？也太暗……";
					m = new Msg(msg);
					m.addOption("硬著頭皮", new Msg("嗯……好吧……我可是有主角光環，橫豎也不會死……應該……")
							.addOption("自我鼓勵", new Msg("說不定死了還可以死回去！呸呸呸……能死當然不要死！")
							.addOption("走更深入", new Msg("（乒乒乓乓）嘶！好痛！一堆雜物！")
							.addOption("翻找摸索", new Msg("到底我為啥會醒來就跑來這鬼地方，我還在作夢？")
							.addOption("摸到桌子", new Msg("喔？透過窗外超級微薄的光線……")
							.addOption("瞇起雞眼", new Msg("好像可以看到這裡有一些陳舊的紙張報導！")
							.addOption("細細研讀", new Msg("我看看喔……這舊報導有寫說過去也有像我這樣變成雞的倒楣鬼跑來這……")
							.addOption("自言自語", new Msg("他們都跑去哪了呢？至少還能找個同溫層……")
							.addOption("繼續研讀", new Msg("咦！？這裡面還寫著這村莊沒事就有人離奇失蹤，至今未下落不明……")
							.addOption("……", new Msg("（打寒顫）（頭皮發麻）（起雞皮疙瘩）……")
							.addOption("……", new Msg("痾……我是不是要趕快想辦法脫身了……")
									.addOption("繼續",null,new Event() {
										@Override
										public void action() {
											storyController.nextSF1();	// => 劇情8
											sc.changeScene(sc.getMainScene());
										}					
									})))))))))))
							).addOption("我的老天鵝",new Msg("黑暗中能得到的訊息量太少，且無法隨時應付危險。")
									.addOption("……", new Msg("我需要萬全的準備再進入，才是上上策。")
									.addOption("……", new Msg("……絕對不是因為我怕黑！")
										.addOption("迷：是是是",null,new Event() {
											@Override
											public void action() {
												sc.changeScene(sc.getMainScene());
											}					
										}
								))));
						return new MsgFlow(m);
				case Global.GROUND_OBSTACLE_MOUNTAIN:
					if(storyController.getSF1() >= 15) {
						tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
						tmpPath[1]="追殺的村民";
						m = new Msg("呼呼……（氣喘吁吁）……我不行了……沒打砍死也要累死了…………");
						m.addOption("繼續", new Msg("……居然一路上都追著我…………")
						.addOption("繼續", new Msg("……如今還被逼著要跳崖……這啥咪世界！？")
						.addOption("崩潰", new Msg("不！我要相信我有主角光環！")
						.addOption("繼續", new Msg("………………這火燒屁股時刻，總該有吧！")
						.addOption("繼續", new Msg("不要這麼倒楣吧！嗚嗚嗚～～")
						.addOption("繼續", new Msg("勇者大人！吼～～～（嘶吼聲）",tmpPath[0],tmpPath[1])
						.addOption("崩潰", new Msg("阿阿阿阿阿阿阿～已經追上來了嗎！？")
						.addOption("崩潰", new Msg("沒有選擇了！跳下去吧！！")
						.addOption("崩潰", new Msg("請老天爺、阿拉、耶穌……還是哪位神明也好！")
						.addOption("崩潰", new Msg("請保佑我阿阿阿阿阿阿阿阿阿阿！！！！！")
						.addOption("奮力一跳", null,new Event() {
							@Override
							public void action() {
								storyController.nextSF1();	// => 劇情16
								sc.changeScene(new Game_End(sc));	//結局
							}
						})))))))))));
						return new MsgFlow(m);
					}else {
						m = new Msg("………………這裡風好大！");
						m.addOption("自言自語", new Msg("……如今變成雞的我……應該是不會飛的…………（看向萬丈深淵）")
								.addOption("自言自語", new Msg("……我來這裡到底要幹嘛………")
								.addOption("靈機一動", new Msg("對了！不都說主角跳崖一定大難不死，必有後福嗎？")
								.addOption("猶豫", new Msg("我怎樣也有著主角光環吧？我要相信跳崖不死定律嗎？")
								.addOption("相信", new Msg("（奮力一跳）哈雷路亞～我是神所眷顧的寵兒阿阿阿阿～～")
										.addOption("展開雙翅", null,new Event() {
											@Override
											public void action() {
												sc.changeScene(sc.getGameOverScene());
											}					
										})).addOption("不信",new Msg("想死回去也不是這樣的吧！閃了閃了！")
												.addOption("離開",null,new Event() {
											@Override
											public void action() {
												sc.changeScene(sc.getMainScene());
											}					
										}
								))))));
						return new MsgFlow(m);
					}
				case Global.GROUND_OBSTACLE_CENTER:
					m = new Msg("偷偷摸摸潛入，真是緊張刺激！");
					m.addOption("躡手躡腳", new Msg("喔～村長家非常的氣派呢！")
							.addOption("躡手躡腳", new Msg("沒想到村長還挺有品味的～！")
							.addOption("四處觀察", new Msg("牆上有關於雞和村民的圖騰及類似的裝飾品……")
							.addOption("觀察裝飾", new Msg("這裡還真的很推崇雞所化身的救世主呢！")
							.addOption("四處觀察", new Msg("旁邊的矮櫃上有十字架吊飾！")
							.addOption("拿取觀察", new Msg("村長平常有上教堂的習慣嗎？")
							.addOption("沉浸思考", new Msg("但上面還有一些圖文符號……身為外來者的我實在看不懂……（搔頭摸耳）")
							.addOption("沉浸思考", new Msg("阿！這讓我想到，據村民所說，半山腰住了一位很厲害的旅者！")
							.addOption("沉浸思考", new Msg("上知天理、下知地理，還總是賣一些新奇古怪的東西！")
							.addOption("沉浸思考", new Msg("也許他會知道十字架上面的符號是什麼意思！")
							.addOption("沉浸思考", new Msg("………………………………")
							.addOption("沉浸思考", new Msg("話說……在這世界也待了一陣子了………")
							.addOption("沉浸思考", new Msg("在村莊裡，村民們都很和樂，對我也很親切！")
							.addOption("沉浸思考", new Msg("………但總覺得有點違和感呢！")
							.addOption("沉浸思考", new Msg("嗯……是什麼呢？說不太上來……")
							.addOption("放棄思考", new Msg("算了！趁被發現前先撤退吧！")
							.addOption("離開", new Msg("對了！在走之前拿走十字架嗎？")
							.addOption("拿取", new Msg("（順手牽羊技能已爐火純青）好了！趕快偷偷離開吧！")
									.addOption("離開", null,new Event() {
										@Override
										public void action() {
											if(ac.getHasPotLid()) {	// 跳到劇情11:活局
												storyController.nextSF1();
												storyController.nextSF1();
//												sc.getMainScene().getCamera().setIsRain(true);
												sc.changeScene(sc.getMainScene());
												ac.setHasCross(true); //有拿到十字架才可以順利推動劇情13
											}else {
												storyController.nextSF1(); // 劇情10:死局
//												sc.getMainScene().getCamera().setIsRain(true);
												sc.changeScene(sc.getMainScene());
											}
										}					
									})).addOption("不拿",new Msg("太可疑了～這樣隨意取走會遭人質疑！")
											.addOption("離開",null,new Event() {
										@Override
										public void action() {
											if(ac.getHasPotLid()) {
												storyController.nextSF1(); // 跳到劇情11
												storyController.nextSF1();
//												sc.getMainScene().getCamera().setIsRain(true);
												sc.changeScene(sc.getMainScene());
											}else {
												storyController.nextSF1(); // 劇情10:死局
//												sc.getMainScene().getCamera().setIsRain(true);
												sc.changeScene(sc.getMainScene());
											}
										}					
									}
							)))))))))))))))))));
					return new MsgFlow(m);
				case Global.GROUND_OBSTACLE_STORE:
					tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
					tmpPath[1]="商店街的村民";
					switch(storyController.getSF1()) {
					case 3:
						m = new Msg("這不是勇者大人嗎！",tmpPath[0],tmpPath[1]);
						m.addOption("打招呼", new Msg("嗨～你們好啊！‥…不好意思‥…借過一下～")
								.addOption("艱難移動", new Msg("勇者大人來這邊是要買什麼嗎？",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("沒沒沒！我就想來借用一下大會廣播講個話一下！")
								.addOption("繼續", new Msg("勇者大人要振奮一下村莊的經濟繁榮嗎！",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("對阿！發大財嘛！‥…‥…阿不是啦！我有急事！")
								.addOption("繼續", new Msg("勇者大人，麥克風在這裡，請使用！",tmpPath[0],tmpPath[1])
								.addOption("接過麥克風", new Msg("咳！大會報告，那個誰誰誰家爸嗎，趕快接你家小朋友回家囉！‥…‥…")
										.addOption("完成任務", null,new Event() {
											@Override
											public void action() {
												storyController.nextSF1();  // => 劇情4
												sc.changeScene(sc.getMainScene());
											}
										}))))))));
						return new MsgFlow(m);
					default:
						m = new Msg("商店街生意非常好，村民們絡繹不絕，完全擠不進去！");
						m.addOption("離開", null,new Event() {
							@Override
							public void action() {
								sc.changeScene(sc.getMainScene());
							}
						});
						return new MsgFlow(m);
					}
				case Global.GROUND_OBSTACLE_PROP:
					if(ac.getHasCross()) {
						tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
						tmpPath[1]="旅者";
						m = new Msg("不好意思……打擾了…………");
						m.addOption("觀察", new Msg("乒乒乓乓………（一陣整理東西的聲音）",tmpPath[0],tmpPath[1])
								.addOption("嘗試搭話", new Msg("痾……那個…………？")
								.addOption("繼續", new Msg("（轉頭）是這一任的勇者嗎？這次發作也太快……",tmpPath[0],tmpPath[1])
								.addOption("疑問", new Msg("什麼意思？發生什麼事了？")
								.addOption("繼續", new Msg("……（整理行囊背起）………你似乎發現這個村莊的真相了！",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("之後會暫時一陣騷亂，我得趕快逃離這裡了！",tmpPath[0],tmpPath[1])
								.addOption("挽留", new Msg("等等！你說的真相是……？會是跟這個十字架有關嗎？")
								.addOption("繼續", new Msg("（一瞥）……這是？……你去了村長家嗎！？",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("難怪……唉！這村莊每經過一段時間，便會洗劫一次！",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("你是經由需要幫助的村民所呼喚而來的，被推崇為勇者！",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("這十字架上寫的字是「信雞神、得永生」，是個對雞很虔誠的信仰！",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("但事實上他們認為吃掉雞神的肉，就真的可以得永生！",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("你是被當作神降的餽贈，而被召喚而來！",tmpPath[0],tmpPath[1])
								.addOption("雞身一震", new Msg("‥…‥…‥…‥…‥…‥！！！！")
								.addOption("震驚", new Msg("什麼！？所以他們把我利用完之後‥…‥拆骨入腹嗎！？")
								.addOption("繼續", new Msg("而他們召喚你來也會付出相應的代價，會以獻祭的方式！",tmpPath[0],tmpPath[1])
								.addOption("震驚", new Msg("所以‥…以前的那些勇者‥……還有失蹤的村民‥…‥…是活祭品‥…‥…")
								.addOption("繼續", new Msg("我都在尚未召喚勇者的時期，才能和平的在此，看來不行了！",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("但弔詭的是‥……多年下來，村長都從未變過‥……難道真如他們所說‥……",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("咳咳咳咳！")
								.addOption("繼續", new Msg("不管了！如果你想解決這件事的話‥……你之後去一趟教堂吧！",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("本代的勇者阿！你好自為之！",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("‥…‥我知道了‥…‥謝謝你告訴我這麼多‥……‥")
										.addOption("離開", null,new Event() {
											@Override
											public void action() {
												storyController.nextSF1();  // => 劇情13
												sc.changeScene(sc.getMainScene());
											}
										}))))))))))))))))))))))));
						return new MsgFlow(m);
					}else {	
						tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
						tmpPath[1]="隱藏的士兵";
						m = new Msg("不好意思……打擾了…………");
						m.addOption("觀察",new Msg("帳篷裡看起來很凌亂……到處都是翻倒的雜物……")
							.addOption("走動",new Msg("奇怪……？沒有人嗎？")
							.addOption("沉思",new Msg("有些生活用品不見了，難道那個人已經離開了嗎？")
							.addOption("沉思",new Msg("……這就麻煩了！……先出去再說吧！")
							.addOption("轉身",new Msg("（突然之間）－－－－咻－－－－－",tmpPath[0],tmpPath[1])
							.addOption("繼續",new Msg("受死吧！！！！！（突刺）",tmpPath[0],tmpPath[1])
							.addOption("來不及反應",null,new Event() {
									@Override
									public void action() {
										sc.changeScene(sc.getGameOverScene());
									}
								})))))));
						return new MsgFlow(m);
					}
				case Global.GROUND_OBSTACLE_EQUIPMENT:
					tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
					tmpPath[1]="武器店老闆";
					if(ac.getHasBoon() == 0) {
						m = new Msg("哇！");
						m.addOption("逛逛", new Msg("喔～這堅硬閃亮的金屬光澤真不錯～")
								.addOption("繼續", new Msg("嘿！這不是勇者大人嗎？歡迎大駕光臨！",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("老闆客氣了！隨便看看！")
								.addOption("繼續", new Msg("身為勇者一定要給力的行頭！我懂的！",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("（湊近低語）我這有超厲害的鎮店之寶……不知道勇者有沒有興趣？",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("（同樣低語）嘿嘿！不知老闆有什麼條件，盡量開！一定使命必達！")
								.addOption("繼續", new Msg("其實這東西也算是流傳已久的骨董，原本想說送給勇者大人成為一大助力！",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("咳！但就是缺了一些重要的材料！",tmpPath[0],tmpPath[1])
								.addOption("繼續", new Msg("是什麼材料呢？")
								.addOption("繼續", new Msg("必須用傳說中極其堅硬之物，所磨成的粉末才能去修復它！‥…‥…")
								.addOption("繼續", new Msg("喔‥…‥…這樣喔！那是什麼東西呢？（沉浸思考）")
										.addOption("離開", null,new Event() {
											@Override
											public void action() {
												sc.changeScene(sc.getMainScene());
											}
										}))))))))))));
						return new MsgFlow(m);
					}else if(ac.getHasBoon() >= 4) {
						m = new Msg("這觸感！這堅硬度！就是它！真是太棒了！",tmpPath[0],tmpPath[1]);
						m.addOption("繼續", new Msg("呼……這……頗有份量……！（氣喘吁吁）")
								.addOption("繼續", new Msg("勇者大人請稍等我一會！",tmpPath[0],tmpPath[1])
								.addOption("等待", new Msg("（躲進布簾後）（傳來磨刀霍霍聲）",tmpPath[0],tmpPath[1])
								.addOption("等待", new Msg("（深吸一口氣）………咻咻咻！………鏘！………",tmpPath[0],tmpPath[1])
								.addOption("………", new Msg("……………………………")
								.addOption("繼續", new Msg("（老闆出現）這就是本店鎮店之寶！獻給勇者大人！",tmpPath[0],tmpPath[1])
								.addOption("取走", new Msg("………謝謝老闆！")
								.addOption("繼續", new Msg("哎呀！都自己人！不用謝！",tmpPath[0],tmpPath[1])
								.addOption("揉揉雙眼", new Msg("………（OS:這…是……鍋蓋沒錯吧！我真不是被誆吧？）")
								.addOption("裝備", new Msg("………………（勇者已裝備鍋蓋）")
								.addOption("離開", null,new Event() {
								@Override
								public void action() {
									sc.changeScene(sc.getMainScene());
									ac.setHasBoon(0);
									ac.setHasPotLid(true);
								}
							})))))))))));
						return new MsgFlow(m);
					}else if (ac.getHasBoon() > 0 && ac.getHasBoon() < 4){
						m = new Msg("這傳說中極其堅硬之物，到底為何物呢？",tmpPath[0],tmpPath[1]);
						m.addOption("繼續", new Msg("阿！勇者大人來了！上次忘了跟你說這東西大概需要「四個」才夠！",tmpPath[0],tmpPath[1])
						.addOption("繼續", new Msg("上次忘了跟您說修復的材料需要「四個」才夠！",tmpPath[0],tmpPath[1])
						.addOption("繼續", new Msg("什麼！？不早點講！～～")
						.addOption("離開", null,new Event() {
							@Override
							public void action() {
								sc.changeScene(sc.getMainScene());
							}
						}))));
						return new MsgFlow(m);
					}
				case Global.GROUND_OBSTACLE_CHURCH:
					tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
					tmpPath[1]="魔化的村長";
					m = new Msg("………村長………直接變成怪物了！！！");
					m.addOption("驚嚇", new Msg("………阿阿阿………（嘶吼聲）………是勇者大人阿………",tmpPath[0],tmpPath[1])
						.addOption("繼續", new Msg("這副醜陋樣子真是獻醜了",tmpPath[0],tmpPath[1])
						.addOption("繼續", new Msg("………請問勇者大人何事這麼行色匆匆呢？",tmpPath[0],tmpPath[1])
						.addOption("繼續", new Msg("………村民們都很愛戴您……擁護您……",tmpPath[0],tmpPath[1])
							.addOption("繼續", new Msg("請繼續留在村莊內吧……呵呵呵呵呵呵…………",tmpPath[0],tmpPath[1])
							.addOption("繼續", new Msg("…………我的天啊…………（倒退三步）")
							.addOption("驚嚇", new Msg("這……我到底是先落跑、落跑、還是落跑呢？（低聲）")
							.addOption("繼續", new Msg("……呵呵呵……勇者大人想去哪呢？（漸漸逼近）",tmpPath[0],tmpPath[1])
							.addOption("周旋", new Msg("……沒有阿……這裡挺好的阿……哈哈……（漸漸退後）")
							.addOption("繼續", new Msg("………嘶嘶嘶………（生物快速的爬行聲音）",tmpPath[0],tmpPath[1])
							.addOption("驚嚇", new Msg("快跑！！！！！村長追來了！！！！！！")
							.addOption("逃跑", null,new Event() {
								@Override
								public void action() {
									storyController.nextSF1();  // => 閹割劇情14
									storyController.nextSF1();  // => 先跳到15
									sc.changeScene(sc.getMainScene());
								}
						}))))))))))));
					return new MsgFlow(m);
				default:
					return new MsgFlow(new Msg("從窗外看進去一片漆黑！"));
				}
			///////////////////////////黑色場景end//////////////////////////////////
			/////////////////////////// 劇情end//////////////////////////////////
			case Global.GROUND_WATER:
				msg = "好美的蓮花池！（回復魔力）";
				m = new Msg(msg);
				m.addOption("是", new Msg("池水鮮美好喝～感覺精力充沛！（MP全滿）"),new Event() {
					@Override
					public void action() {
						ac.getStatus().setCurrentMP(ac.getStatus().getTotalMP());
					}					
				}).addOption("否",new Msg("蓮花可遠觀而不可褻玩焉！"));
				return new MsgFlow(m);
			case Global.GROUND_FRUIT:
				msg = "這些水果看起來好好吃！（回復體力）";
				m = new Msg(msg);
				m.addOption("是", new Msg("感謝村民的熱情贈送～吃得好撐！（HP全滿）"),new Event() {
					@Override
					public void action() {
						ac.getStatus().setCurrentHP(ac.getStatus().getTotalHP());
					}					
				}).addOption("否",new Msg("再吃下去，嘴巴就要被塞橘子供奉起來了！"));
				return new MsgFlow(m);	
			case Global.GROUND_LIGHT:
				msg = "溫暖明亮的篝火！";
				m = new Msg(msg);
				m.addOption("手伸過去", new Msg("好燙！被燙傷了！")
					.addOption("嚇死",null,new Event() {
					@Override
					public void action() {
						sc.changeScene(sc.getGameOverScene());
					}					
				})).addOption("保持距離",new Msg("好暖和～覺得昏昏欲睡！（得到休息+10MP）"),new Event() {
					@Override
					public void action() {
						ac.getStatus().setCurrentHP(ac.getStatus().getCurrentHP()+10);
					}					
				});
				return new MsgFlow(m);	
			case Global.GROUND_WATERPOOL:
				msg = "氣勢磅礡的瀑布下，有波光粼粼的溪流！";
				m = new Msg(msg);
				m.addOption("游泳", new Msg("阿靠！不對呀！我現在是一隻雞！咕嚕咕嚕……（變成落湯雞）")
					.addOption("重創", null,new Event() {
					@Override
					public void action() {
						sc.changeScene(sc.getGameOverScene());
					}					
				})).addOption("欣賞",new Msg("阿～一個適合拍照打卡的好景點的說～")
						.addOption("繼續",new Msg("阿～～好舒壓喔～～～（得到休息+10MP）"),new Event() {
							@Override
							public void action() {
								ac.getStatus().setCurrentHP(ac.getStatus().getCurrentHP()+10);
							}					
						}));
				return new MsgFlow(m);	
			case Global.GROUND_OBSTACLE_MOUNTAIN:
				if (storyController.getSF1() >= 15) {
					msg = "這裡是通往後山的路！";
					m = new Msg(msg);
					m.addOption("繼續", new Msg("太可怕了！大逃殺阿阿阿！")
							.addOption("連滾帶爬", new Msg("快走快走快走！")
							.addOption("衝刺", new Msg("不能回來啦！"),new Event() {
										@Override
										public void action() {
											sc.changeScene(sc.getBlackScene(),pic,ac);
											ac.changeDirection(Global.DOWN);
										}					
							})));
					return new MsgFlow(m);
				}else {
					msg = "這裡是通往後山的路！";
					m = new Msg(msg);
					m.addOption("自言自語", new Msg("村民說再過去就是懸崖了！")
							.addOption("自言自語", new Msg("要過去嗎？")
							.addOption("走啊", new Msg("就是要去看看～")
									.addOption("前往", new Msg("回到主村莊！"),new Event() {
										@Override
										public void action() {
											sc.changeScene(sc.getBlackScene(),pic,ac);
											ac.changeDirection(Global.DOWN);
										}					
									})).addOption("不要",new Msg("去那邊是要幹嘛？"))));
					return new MsgFlow(m);
				}
			case Global.GROUND_OBSTACLE_ENTRANCE:
				if(storyController.getSF1() < 8) {
					m = new Msg("這裡是村莊的門口！");
					m.addOption("自言自語", new Msg("在外面不知道會通往哪呢～")
							.addOption("自言自語", new Msg("是要離開了嗎？")
							.addOption("毫無留戀", new Msg("好想去探索外面的世界！")
									.addOption("準備前往", new Msg("（停止腳步）……不行拉！要把村莊的事情處理完畢！"),new Event() {
										@Override
										public void action() {
											ac.changeDirection(Global.UP);
										}					
									})
							).addOption("依依不捨",new Msg("跟村民們都建立感情了，再逗留一下好了！"))));
					return new MsgFlow(m);
				} else if (storyController.getSF1() == 8) {
					tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.VILLAGE_HEAD);
					tmpPath[1]="村長";
					m = new Msg("太可怕了～趕快閃人嚕！");
					m.addOption("離開", new Msg("溜了溜了～！")
							.addOption("繼續", new Msg("勇者大人請留步！",tmpPath[0],tmpPath[1])
							.addOption("轉身", new Msg("請問勇者大人要前往何處呢？",tmpPath[0],tmpPath[1])
							.addOption("藏起行李", new Msg("痾……沒拉！……天氣真好～想出去走走………（吹口哨）")
							.addOption("繼續", new Msg("這樣啊！但等一下天氣似乎會不太好呢！",tmpPath[0],tmpPath[1])
							.addOption("繼續", new Msg("請勇者暫時留在村莊內吧！",tmpPath[0],tmpPath[1])
							.addOption("繼續", new Msg("阿……好的好的……（汗顏）"),new Event() {
								@Override
								public void action() {
									ac.changeDirection(Global.UP);
									sound=new MusicController(PathBuilder.getAudio(MusicPath.Sound.THUNDER));
									sound.playOnce();
									storyController.nextSF1();	// => 劇情9
								}	
							})))))));
					return new MsgFlow(m);
				}else if (storyController.getSF1() == 9) {
					m = new Msg("出去的事得緩緩了！先從長計議！");
					m.addOption("自言自語", new Msg("那現在還可以做什麼呢？")
							.addOption("自言自語", new Msg("到底為什麼過往村莊的村民會失蹤呢？")
							.addOption("自言自語", new Msg("既然不能出去，那就在村莊內尋找線索好了！")
							.addOption("自言自語", new Msg("村莊內大多房子已經去過了！")
							.addOption("自言自語",new Msg("還有哪些地方沒有去過呢？"))))));
					return new MsgFlow(m);
				}else if (storyController.getSF1() >= 15) {
					m = new Msg("…………QQ！！！");
					m.addOption("繼續", new Msg("阿阿阿阿阿～～為什麼村莊還是無法出去！？")
							.addOption("崩潰", new Msg("這可怎麼辦………整個村莊的人全部崩壞………")
							.addOption("繼續", new Msg("………我都死這麼多次了………！")
							.addOption("繼續", new Msg("………我可不想一直死在這裡阿阿阿阿阿！")
							.addOption("再次崩潰", new Msg("讓我死回去吧！")
							.addOption("繼續", new Msg("……難不成………再去跳一次崖………！？")
							.addOption("……", new Msg("………………………")
							.addOption("繼續", new Msg("不管了！死馬當活馬醫！這緊急時刻，跳崖不死定律總會生效吧！")
							.addOption("轉身", new Msg("趕快打出一個突破口，衝到後山吧！"),new Event() {
								@Override
								public void action() {
									ac.changeDirection(Global.UP);
								}	
							})))))))));
					return new MsgFlow(m);
				}else {
					return new MsgFlow(new Msg("糟了！通往村莊外的道路被封住了！"));
				}
			case Global.GROUND_OBSTACLE_CENTER:
				if(storyController.getSF1() < 9) {
					return new MsgFlow(new Msg("村長受到村民的愛戴，且居住在村子的中央！"));
				}
				if(storyController.getSF1() == 9) {
					m = new Msg("這裡是村長家！");
					m.addOption("自言自語", new Msg("村長在村中是十分德高望重的人物！")
							.addOption("自言自語", new Msg("村長的家裡不知道有沒有什麼關鍵道具或線索？")
							.addOption("自言自語", new Msg("要進去看看嗎？")
							.addOption("當然", new Msg("最好能發現個什麼寶物！")
									.addOption("潛入", new Msg("回到主村莊"),new Event() {
										@Override
										public void action() {
											sc.changeScene(sc.getBlackScene(),pic,ac);
											ac.changeDirection(Global.DOWN);
										}					
									})
							).addOption("不行",new Msg("就算我變成雞了！我也要留有我這品性高潔的人格！")))));
					return new MsgFlow(m);
				}else {
					return new MsgFlow(new Msg("現在不是進入的時機了！"));
				}
			case Global.GROUND_OBSTACLE_EQUIPMENT:
				if(storyController.getSF1() >= 15) {
					return new MsgFlow(new Msg("現在不是進入的時機了！"));
				}
				if(ac.getHasPotLid()) {
					m = new Msg("繼上次的鍋蓋事件之後……");
					m.addOption("自言自語", new Msg("裝備店的老闆就去旅行了！")
							.addOption("自言自語", new Msg("說是要找尋傳說中的裝備材料了！")
							.addOption("…………", new Msg("……………………")
							.addOption("沉思", new Msg("………真的不是落跑嗎？")))));
					return new MsgFlow(m);
				}else {
					if(ac.getHasBoon() == 0) {
						m = new Msg("這裡是裝備店！");
						m.addOption("自言自語", new Msg("看店外陳列了很多很厲害的武器！")
								.addOption("自言自語", new Msg("嘿嘿～也許裡面有什麼更厲害的沒拿出來！")
								.addOption("自言自語", new Msg("要進去看看嗎？")
								.addOption("當然", new Msg("看看又不用錢～是吧！")
										.addOption("進入", new Msg("回到主村莊"),new Event() {
											@Override
											public void action() {
												sc.changeScene(sc.getBlackScene(),pic,ac);
												ac.changeDirection(Global.DOWN);
											}					
										})
								).addOption("不了",new Msg("哎呀～沒什麼心情逛逛！")))));
						return new MsgFlow(m);
					}else if(ac.getHasBoon() > 0) {
						m = new Msg("這裡是裝備店！");
						m.addOption("自言自語", new Msg("好想知道老闆的所謂鎮店之寶是多厲害！")
								.addOption("自言自語", new Msg("要進去看看嗎？")
										.addOption("進入", new Msg("回到主村莊"),new Event() {
											@Override
											public void action() {
												sc.changeScene(sc.getBlackScene(),pic,ac);
												ac.changeDirection(Global.DOWN);
											}					
										}).addOption("不了",new Msg("下次吧！還有的是機會！"))));
						return new MsgFlow(m);
					}
				}
			case Global.GROUND_OBSTACLE_CHURCH:
				if(storyController.getSF1() < 13) {
					m = new Msg("這裡是教堂，但我沒有這個信仰，不知道進去要幹嘛！");
					m.addOption("自言自語", new Msg("但教堂可所謂說是村莊重要的精神支柱！"));				
					return new MsgFlow(m);
				}
				if(storyController.getSF1() == 13) {
					m = new Msg("這裡就是教堂了！");
					m.addOption("屏氣凝神", new Msg("………………………")
					.addOption("屏氣凝神", new Msg("……如果旅者所言屬實…………")
					.addOption("屏氣凝神", new Msg("那進去後，面臨的將是…………最後的關鍵！")
					.addOption("屏氣凝神", new Msg("……（抖）………嗚嗚……不想面對…………")
					.addOption("屏氣凝神", new Msg("那…………進入教堂裡嗎？")
							.addOption("是", new Msg("回到主村莊"),new Event() {
								@Override
								public void action() {
									sc.changeScene(sc.getBlackScene(),pic,ac);
									ac.changeDirection(Global.DOWN);
								}					
							}).addOption("否",new Msg("等等！這時候應該要存復活點！")))))));
					return new MsgFlow(m);
				}else {
					return new MsgFlow(new Msg("現在不是進入的時機了！"));
				}
			case Global.GROUND_BONE:
				m = new Msg("不知名生物的殘骸，令人毛骨悚然！");
				m.addOption("觀察", new Msg("要拿一些走嗎？也許有用！")
						.addOption("取走", new Msg("拿回去研究研究，也許還能賣錢！"),new Event() {
									@Override
									public void action() {
										ac.IncreaseHasBoon();
										// 想讓骨頭消失
									}					
								}).addOption("不取",new Msg("不要亂撿東西，小心立flag！")));
				return new MsgFlow(m);
			case Global.SLIME:
				tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
				tmpPath[1]="史萊姆";
				msg = "新手村必刷的史萊姆～朝你衝過來了！";
				m = new Msg(msg, tmpPath[0], tmpPath[1]);
				m.addOption("是",new Msg("戰鬥結束！"),new Event() {
					@Override
					public void action() {
						sc.changeScene(new BattleScene(sc, ac, (Actor)obj));
					}
				});
				return new MsgFlow(m);	
			case Global.SLIME-2:
				tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
				tmpPath[1]="食人花";
				msg = "可怕的食人花～朝你衝過來了！";
				m = new Msg(msg, tmpPath[0], tmpPath[1]);
				m.addOption("是",new Msg("戰鬥結束！"),new Event() {
					@Override
					public void action() {
						sc.changeScene(new BattleScene(sc, ac, (Actor)obj));
					}
				});
				return new MsgFlow(m);	
			case Global.MONSTER:
				tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
				tmpPath[1]="魔化村長";
				msg = "變成怪物的村長～朝你衝過來了！";				
				m = new Msg(msg, tmpPath[0], tmpPath[1]);
				m.addOption("是",new Msg("戰鬥結束！"),new Event() {
					@Override
					public void action() {
						sc.changeScene(new BattleScene(sc,ac,(Actor)obj));
					}
				});
				return new MsgFlow(m);
			case Global.SOLDIER:
				if(storyController.getSF1() >= 10) {
					tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
					tmpPath[1]="巡邏士兵";
					msg = "曾經面善親切的士兵，如今凶神惡煞的朝你衝過來了！";				
					m = new Msg(msg, tmpPath[0], tmpPath[1]);
					m.addOption("是",new Msg("戰鬥結束！"),new Event() {
						@Override
						public void action() {
							sc.changeScene(new BattleScene(sc,ac,(Actor)obj));
						}
					});
					return new MsgFlow(m);
				}else {
					return new MsgFlow(new Msg("保衛村莊安全的無名英雄！"));
				}
			case Global.GROUND_OBSTACLE_PROP:
				if(storyController.getSF1() >= 13) {
					return new MsgFlow(new Msg("現在不是進入的時機了！"));
				}else if(storyController.getSF1() < 12) {
					return new MsgFlow(new Msg("帳篷的主人似乎不在，不要亂闖進去好了！"));
				}else {
					m = new Msg("這裡就是旅者的帳篷！");
					m.addOption("氣喘吁吁", new Msg("到底我為什麼一出來村長家，就被全村敵視！？")
							.addOption("氣喘吁吁", new Msg("雖然我做的事的確不妥……")
							.addOption("氣喘吁吁", new Msg("但這村莊真的有問題啊！")
							.addOption("自言自語", new Msg("過去為何有失蹤的村民？")
							.addOption("自言自語", new Msg("那些過往來這邊的勇者前輩們去哪了？")
							.addOption("自言自語", new Msg("雖然可能看似巧合…………")
							.addOption("自言自語", new Msg("但我一直無法出去這個村莊！")
							.addOption("自言自語", new Msg("…………我到底該怎麼回來原來的世界呢？")
							.addOption("自言自語", new Msg("唉！總之這地方，不宜久留了！")
							.addOption("自言自語", new Msg("要進去帳篷了嗎？")
							.addOption("進入", new Msg("回到主村莊"),new Event() {
								@Override
								public void action() {
									sc.changeScene(sc.getBlackScene(),pic,ac);
									ac.changeDirection(Global.DOWN);
								}					
							}).addOption("先不",new Msg("再等等好了！"))))))))))));
					return new MsgFlow(m);
				}
			///////////////////////////////////////////////////////
			case Global.GROUND_SKYTREE:
				msg = "遮天蓋地的天空樹！";
				break;
			case Global.GROUND_TENT:
				msg = "是個很大的帳篷，看起來足夠裝很多雜物！";
				break;
			case Global.GROUND_SHELF:
				if(storyController.getSF1()>=11) {
					msg = "現在沒有休息的心情了！";
				}else {
					msg = "愜意又涼爽的休息處！";
				}
				break;
			case Global.GROUND_HOUSE:
				if(storyController.getSF1()>=15) {
					msg = "房子著火了！！！";
				}else {
					msg = "房子蓋的很堅固安全！";
				}
				break;
			case Global.GROUND_TREE:
				if(storyController.getSF1()>=15) {
					msg = "樹著火了！！！";
				}else {
					msg = "芳草萋萋、鬱鬱蔥蔥！";
				}
				break;
			case Global.GROUND_TURF_WALL:
				msg = "有些峭壁還纏繞著藤蔓！";
				break;
			case Global.GROUND_TURF_LADDER:
				msg = "階梯上長滿了青苔！";
				break;
			case Global.GROUND_CHAIR:
				if(storyController.getSF1()>=15) {
					msg = "現在不是坐下的時候！";
				}else {
					msg = "供人歇息的長椅，可惜開發者不讓我坐QQ！";
				}
				break;
			case Global.GROUND_STATUE1:
				msg = "氣勢非凡的飛龍雕像！";
				break;
			case Global.GROUND_GOODS:
			case Global.GROUND_BOXES:
				msg = "這裡堆放著雜物！";
				break;
			case Global.GROUND_STATUE2:
				msg = "女神雕像面露著祥和平靜的神情！";
				break;
			case Global.GROUND_EQUIPMENT1:
			case Global.GROUND_EQUIPMENT2:
			case Global.GROUND_EQUIPMENT3:
				msg = "好多屌炸天的炫酷裝備！";
				break;
			case Global.GROUND_PROP1:
			case Global.GROUND_PROP2:
			case Global.GROUND_PROP3:
			case Global.GROUND_PROP4:
				msg = "好多神神秘秘的古怪道具！";
				break;
			case Global.GROUND_GROSS:
				msg = "教堂標誌性的十字架標誌！";
				break;
			case Global.GROUND_GRAVE:
				msg = "Rest In Peace……";
				break;
			case Global.BUTTERFLY:
				msg = "蝴蝶～蝴蝶～生的真美麗！";
				break;
			case Global.GROUND_PAINO:
				if(storyController.getSF1()>=15) {
					msg = "現在不是聽琴聲的時候了！";
				}else {
					msg = "優美的琴聲，在小山坡上，好浪漫啊！";
				}
				break;
			default:
				msg = "開發者說我是雞，我就是隻雞！";
				break;
		}
//		Msg m = new Msg(msg);			
//		Msg test = new Msg("測試");
//		test.addOption("測試中測試");
//		
//		m.addOption("下一個視窗", new Msg("測試")).addOption("視窗", test);
//		
//		Msg m = Msg.normal(msg);
//		return new MsgFlow(m);	
		
		return new MsgFlow(new Msg(msg));
	}
}
