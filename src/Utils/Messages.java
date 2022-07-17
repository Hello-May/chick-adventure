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
			m.addOption("�O");
			return m;
		}

		public static Msg yesNo(String msg, Event yes, Event no) {
			Msg m = new Msg(msg);
			m.addOption("�O", new Msg("�^������I"), yes);
			m.addOption("�_", new Msg("��ܤ��a��K�K"), no);
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
			tmpPath[1]="����";
			msg = "�����̹��O���h�z����A�@�y�u�񪺴§A�ĹL�ӡI";				
			m = new Msg(msg, tmpPath[0], tmpPath[1]);
			m.addOption("�O",new Msg("�԰������I"),new Event() {
				@Override
				public void action() {
					sc.changeScene(new BattleScene(sc,ac,(Actor)obj));
				}
			});
			return new MsgFlow(m);
		}
		if(pic >= 80 && pic < 110 && pic != 86 && pic != 102 ) {
			tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
			tmpPath[1]="����";
			if(storyController.getSF1() == 3) {
				m = new Msg("�A�n�A���n�N��K�K");
				m.addOption("�~��", new Msg("�]�����Ʊ����s�h�ߡ^�p���o��K�K�K")
								.addOption("�~��", new Msg("����I�A�O�����ӽֽֽ֮a���Ĥl�̶ܡH",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("�ˡK�K���I�K�K���ӧa��")
								.addOption("�~��", new Msg("���ӽֽֽ֮a�������A�o�ɶ��I���|�X�S�b�ө���I",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("�n��I�ڪ��D�F��]OS�G�n�i�ȡI�H�K�@�ӧ������M���O�H�a�����ܡI�^")
								.addOption("�~��", new Msg("�A�h�ɥΰө��󪺤j�|�s���A�N�i�H���L�̳�I",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("�n���I���§A��"))))))));
				return new MsgFlow(m);
			}else if (storyController.getSF1() >= 10) {
				tmpArr= new String[]{"�K�K�K�K�]����^���^","�K�K�K�K�]�ֳt���h�^","�K�K�K�K�K�]�����f�z�^"};
				return new MsgFlow(new Msg(tmpArr[(int)(Math.random()*3)],tmpPath[0], tmpPath[1]));
			}else {
				tmpArr= new String[]{"�i�R�¾몺�����̡I","���Ѫ������̨���ڤ]�ܼ����I","�ˤ������۩I��"};
				return new MsgFlow(new Msg(tmpArr[(int)(Math.random()*3)]));
			}
		}
		if(pic >= 40 && pic < 45) {
			tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
			tmpPath[1]="�p�ʪ�";
			if(storyController.getSF1() >= 10) {
				tmpArr= new String[]{"�K�K�K�K�]�@�y���h�ˡ^","��K�K�]�λ�l�Q��^�K�K","�K�K�K�K�]�ײ������ۡ^"};
				return new MsgFlow(new Msg(tmpArr[(int)(Math.random()*3)],tmpPath[0], tmpPath[1]));
			}else {
				tmpArr= new String[]{"�i�R���p�ʪ��̡I","�d�z�̣z����n��¡����","���d�S�A�����p�ͩR�ڡ�"};
				return new MsgFlow(new Msg(tmpArr[(int)(Math.random()*3)]));
			}
		}
		switch (pic) {	
			//////////////////// �@��start////////////////////////////////
			case Global.GROUND_OBSTACLE_STORE:
				if(storyController.getSF1() >= 15) {
					return new MsgFlow(new Msg("�{�b���O�i�J���ɾ��F�I"));
				}
				if(storyController.getSF1() < 3) {
					return new MsgFlow(new Msg("���`���������ͬ��Ϋ~���ө���A�{�b�٨S����~�ɶ��I"));
				}
				if(storyController.getSF1() == 3) {
					m = new Msg("�o�̴N�O�ө���F�ܡH");
					m.addOption("�~��", new Msg("�w�g�}�l��~�F�O�I")
							.addOption("�~��", new Msg("�Ƥ��y��A���ֶi�J�a�I")
							.addOption("�i�J", new Msg("�^��D�����I"),new Event() {
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
					m = new Msg("�o�̴N�O�N�O�W���ӹL���ө���");
					m.addOption("�~��", new Msg("�{�b�̵M�b��~�O�I")
							.addOption("�~��", new Msg("�n�i�h�ܡH")
									.addOption("�i�J",new Msg("�^��D�����I"),new Event() {
								@Override
								public void action() {
									sc.changeScene(sc.getBlackScene(),pic,ac);
									ac.changeDirection(Global.DOWN);
								}					
							})).addOption("���F", new Msg("�����򻡩O�H�ҿפ@������˭^���~���I")));
					return new MsgFlow(m);
				}
			case Global.VILLAGE_HEAD:
				tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.VILLAGE_HEAD);
				tmpPath[1]="����";
				switch(storyController.getSF1()) {
				case 1:
					msg = "���ƨ����i�̤j�H�ש���{�o�ӥ@�ɤF�I";
					m = new Msg(msg, tmpPath[0], tmpPath[1]);
					m.addOption("��", new Msg("��H�]�@�y�j�G�^")
							.addOption("�~��", new Msg("�ڬO�o�ӧ����������C",tmpPath[0], tmpPath[1])
							.addOption("�~��", new Msg("�аݡK�K�o�̬O�H")
							.addOption("�߰�", new Msg("�z�O�]������ݭn���U�������̩ҩI��ӨӪ��I",tmpPath[0], tmpPath[1])
							.addOption("�~��", new Msg("�ˡK�K���ӡK�K")
							.addOption("���զA���߰�", new Msg("�{�b���l�̵o�{�j�ƤF�I",tmpPath[0], tmpPath[1])
							.addOption("�~��", new Msg("�K�K�ڡK�K�A�K�K�K�K")
							.addOption("���ե��_����", new Msg("�]�\�z�ߤ����ܦh�ðݡK�K",tmpPath[0], tmpPath[1])
							.addOption("�~��", new Msg("�O�K�K�K�K�]�¤H�ݸ��^")
							.addOption("����_", new Msg("���Ӥ��θ�z�����o��h�F�I",tmpPath[0], tmpPath[1])
							.addOption("�K�K", new Msg("�K�K�K�KQQ")
							.addOption("�~��", new Msg("�Ыi�̤j�H���ֲ��r�L�h�I",tmpPath[0], tmpPath[1]),new Event() {
								@Override
								public void action() {
									storyController.nextSF1();	// => �@��2
								}	
							})
							))))))))))).addOption("ԣ�p",new Msg("�ڤ�ť�I�ڤ�ť�I�ڤ�ť�I�]�������ա^"));
					return new MsgFlow(m);
				case 2:
					msg = "�ƺA���A�Ыi�̤j�H���ֲ��r�L�h�I";
					m = new Msg(msg,tmpPath[0], tmpPath[1]);
					m.addOption("�~��",new Msg("�����K�K")
							.addOption("�~��",new Msg("�ҥH���O���̰աI�H(�Y�쪬)")));
					return new MsgFlow(m);
				case 3:
				case 4:
				case 5:
					m = new Msg("�v�ܩi���ƥ�A�ܦn���ѨM�F�O�I",tmpPath[0], tmpPath[1]);
					m.addOption("�~��", new Msg("���\�O�i�̤j�H�I�����̳��ܷP�§A�I",tmpPath[0], tmpPath[1])
						.addOption("�~��", new Msg("�O�O�K�K�I�]OS:�H�������v�ܩi�G�~�F�ڤT�ѤT�]�^")
						.addOption("�~��",new Msg("�Ыi�̤j�H�Ȯɦw�~�b���l���a�I",tmpPath[0], tmpPath[1])
								.addOption("�ڡI", new Msg("�K�K�x�����I���n�������O�H")))));
					return new MsgFlow(m);
				default:
					msg = "�������ڬO�o�ӧ����������I";
					m = new Msg(msg,tmpPath[0], tmpPath[1]);
					return new MsgFlow(m);
				}
			case Global.GROUND_KEY_INSLIME:	// �����j�v�ܩi => �@��3
				msg = "�o�O�v�ܩi���ݴ�A���̪��D���O�K�K�U���������H";
				m = new Msg(msg);
				m.addOption("����", new Msg("�]½�ˤ��^���v�ܩi�]��O�ǥX���M�D�ҡK�K�������ҷ|��n")
						.addOption("�ۨ��ۻy", new Msg("�N�����|�Y�A�������K�K���޳�������N�ܥi�ȡI")
								.addOption("�ۨ��ۻy", new Msg("���ө_�Ǫ��U�����O�i�ȱ��I")
								.addOption("�ۨ��ۻy", new Msg("�쩳��ԣ�o�򻡩O�H�]�����N���n���ڽu���뵹�ڰT��")
								.addOption("�g�G�O�n������ѡH", new Msg("�e�F�@�ʾ�A��U���Ӱ{�{�o�����F��K�K")
								.addOption("�ۨ��ۻy", new Msg("��K�K�n��H�o��h��A�ڧ��`��a�I")
								.addOption("�g�G���F!����ۨ��ۻy", new Msg("����������p�B�Ͱe�^�h�a�I")
										)))))))
					.addOption("����",new Msg("�ᤣ�I�o�ڤ���A�o�c��A�e�ڥh����R�@�U�I"));
				return new MsgFlow(m);
			case 70:	//�Q�v�ܩi�ۭt������
			case 71:
			case 73:
				tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
				tmpPath[1]="�������p�B��";
				switch(storyController.getSF1()) {
				case 2:
					msg = "�����";
					m = new Msg(msg, tmpPath[0], tmpPath[1]);
					m.addOption("�w��", new Msg("�z�������", tmpPath[0], tmpPath[1])
							.addOption("��?�]�˰h�T�B�^", new Msg("OS�G��������F�I�H")
							.addOption("�~��", new Msg("�q�q����o�ͤ���Ƹ�j���������")
							.addOption("�~��", new Msg("���������H�H�Ǵۭt�ڭ̶����", tmpPath[0], tmpPath[1])
							.addOption("�K�K", new Msg("OS�G�������H")
							))))).addOption("��a",new Msg("�O���F���Ĥl�I"));
					return new MsgFlow(m);
				case 3:	
					msg = "���������§A��";
					m = new Msg(msg, tmpPath[0], tmpPath[1]);
					m.addOption("�~��", new Msg("���I�|�⤧�ҡ�A�̤]���֦^�a�a�I")
							.addOption("�~��", new Msg("�i�O�K�K����h�h�K�K�L�k���^�h�F�K�K", tmpPath[0], tmpPath[1])
									.addOption("�w��", new Msg("�I�I��h�h�����F��h�h�f�ۤ��b�^�~�Ӫ��o�I")		//�ﶵ�@
											.addOption("�~��", new Msg("�������F�H��z�����٬O�h�h����", tmpPath[0], tmpPath[1])
											.addOption("�~��", new Msg("�K�K�K�K�]����������믫�ˮ`-50MP�^"),new Event() {
												@Override
												public void action() {
													ac.getStatus().setCurrentMP(ac.getStatus().getCurrentMP()-50);
												}	
											})))
									.addOption("�I�q", new Msg("�K�K�K�K�K�K")		//�ﶵ�G
											.addOption("�~��", new Msg("�K�K�K�K�K�K�K�K", tmpPath[0], tmpPath[1])
											.addOption("�~��", new Msg("�K�K�K�K�K�K�K�K�K�K")
											.addOption("�~��", new Msg("�K�K���K�K�]���h�^�K�K", tmpPath[0], tmpPath[1])
											.addOption("�~��", new Msg("�K�K�K�K�K�K�K�K�K�K�K�K")
											.addOption("�~��", new Msg("�K�K�K�K�K�K�]�j�����p���̡^", tmpPath[0], tmpPath[1])))))))	
									.addOption("�@�e", new Msg("���M�A�̮a�b���A�ڰe�A�̦^�a��")	//�ﶵ�T
											.addOption("�~��", new Msg("���ڭ̼ȮɵL�k�ʼu�F�A�Хh������ڭ̪����I", tmpPath[0], tmpPath[1])
											.addOption("�~��", new Msg("���A�̪����b���̡H")
											.addOption("�~��", new Msg("�H�K��@�Ӫ������B�A�L�̷|���D�ڭ̪����b���I", tmpPath[0], tmpPath[1])
							))))));
					return new MsgFlow(m);
				}
			case Global.GROUND_SHINING:		// ��ݴ��ܡB�䪨���e�p�B�ͦ^�a => �@��4
				switch(storyController.getSF1()) {
				case 4:
					msg = "�_�ǡA���o�̦��o�F��ܡH";
					m = new Msg(msg);
					m.addOption("�n�_", new Msg("�o�F��t���Q��A�`�ڤ����a��K�K")
							.addOption("�ۨ��ۻy", new Msg("�o�ǭ��H��N���ͥ��ʪ������A�o�S���������a��o�F��")
							.addOption("�ۨ��ۻy", new Msg("�ҥH�~�E���o��h�b�o��r�ޤ��h�K�K")
							.addOption("����", new Msg("�Q���A���ڦ��I�ȩȡ�")
									.addOption("�ۨ��ۻy", new Msg("�o���񦳤���������������i�H�W�@�U�ܡH"),new Event() {
										@Override
										public void action() {
											storyController.nextSF1();	// => �@��5
										}					
									}))
									.addOption("����",new Msg("�ǩǪ��A����������þߪF��I")
							)))).addOption("����",new Msg("�o�F��{�M�ڪ��g�X�������I"));
					return new MsgFlow(m);
				case 5:
					return new MsgFlow(new Msg("���O���n����Ӫ��������F��ܡH"));
				case 6:
					msg = "����n�Ӷ}�c�F�I";
					m = new Msg(msg);
					m.addOption("�}�a", new Msg("�i�̤w�˳ƾ�K�A���o�}�c�ޯ�I")
							.addOption("���W", new Msg("���j�j�K�K")
							.addOption("�A�W", new Msg("���j���j���j�K�K")
							.addOption("�ΤO�W", new Msg("���j���j���j���j���j�K�K�I�I")
							.addOption("WTF", new Msg("�K�K�c�l�Q�ˤF�A�O�@���_�͡A�ݰ_�ӴN�W���䪺�F����I")
							.addOption("�[��", new Msg("�o���_�ͤW���٦��Ӽ��ҡA�g���b�u�ܮw�v�I")
							.addOption("�[��", new Msg("��H�o�O�b���������Y�өФl�ܡH"),new Event() {
									@Override
									public void action() {
										storyController.nextSF1();	// => �@��7
									}					
								})))).addOption("���W�F",new Msg("�ˡK�K�`ı�o�����n���w�P�K�K")
						)))).addOption("�S��",new Msg("�n�}�B���}�B�n�}�B���}�B�n�}�B���}�K�K��I�p�ỡ���}�I"));
					return new MsgFlow(m);
				}
			case Global.GROUND_WOOD:	//�@��5
				switch(storyController.getSF1()) {
				case 5:
					msg = "�������K�K��K���ӥi�H�a�H";
					m = new Msg(msg);
					m.addOption("�i�H", new Msg("�N�ɥΤ@�����ӨS���Y�a�H")
							.addOption("�S���Y", new Msg("�����N���^�ӤF�ա�")
									.addOption("����", new Msg("�P�§����I�g�ħ����I"),new Event() {
										@Override
										public void action() {
											storyController.nextSF1();	// => �@��6
										}					
									}))
									.addOption("�����Y",new Msg("����㤣�i�H�î��������妽�ҤO���G�I")
							)).addOption("����",new Msg("��K�ӯܮz�F�I�L�k�O�@��ܮz���ڡ�"));
					return new MsgFlow(m);
				default:
					return new MsgFlow(new Msg("�����̥��`�ֿn�è��Ϊ������I"));
				}
			case Global.GROUND_OBSTACLE_HOME:	//�@��7
				if(storyController.getSF1() == 7) {
					msg = "�o����n���i�H�i�J�o�ɩФl�I";
					m = new Msg(msg);
					m.addOption("�V��", new Msg("�K�K�y�I�]���U�k�ߡ^�K�K�n�n�n�K�K")
							.addOption("�[��", new Msg("�]�R�q�^�K�K�n���S�H�K�K")
							.addOption("�}��", new Msg("�������v�O���諸�A�p�B�ͽФ��n�ǡI")
									.addOption("���}", new Msg("�^������I"),new Event() {
										@Override
										public void action() {
											sc.changeScene(sc.getBlackScene(),pic,ac);
											ac.changeDirection(Global.DOWN);
										}					
									}))
									.addOption("���}",new Msg("�z���K�K�Pı�O����@���A�ݭn�I�߲z�ǳơI")
							))).addOption("���V",new Msg("�����n���|�k�ʦn�F�I"));
					return new MsgFlow(m);
				}else if (storyController.getSF1() >= 7 && storyController.getSF1() < 14){
					return new MsgFlow(new Msg("�w�g�i�h�L�F�I�S�����n�A�i�h�I"));
				}else if (storyController.getSF1() >= 14){
					return new MsgFlow(new Msg("�{�b���O�i�J���ɾ��F�I"));
				}else {
					return new MsgFlow(new Msg("�Q�W�ꪺ�Фl�A�ݰ_�ӹ��������ܮw�I"));
				}
			///////////////////////////�i�J�¦����//////////////////////////////////
			case -10:	//obj==null;	
				if(storyController.getSF1()==16) {	//����
					tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
					tmpPath[1]="��";
					m = new Msg("�K�K�K�K�K�K�K�K�]���I�n�^",tmpPath[0],tmpPath[1]);
					m.addOption("���",new Msg("���I�n�h�K�K�����Y�F�I",tmpPath[0],tmpPath[1])
					.addOption("�ۨ��ۻy",new Msg("�K�K�K�K�ںεۤF�ܡH",tmpPath[0],tmpPath[1])
					.addOption("�̸̮�",new Msg("�K�K�Y�n�w�A�ڬO�Φh�[�ڡH",tmpPath[0],tmpPath[1])
					.addOption("�I��",new Msg("�]�N�U�ڡ^�K�K���ڦn���ڨ줰�򤣱o�F���F��H",tmpPath[0],tmpPath[1])
					.addOption("�I��",new Msg("�K�K�ڦn���ܦ��F�@�����H�K�K",tmpPath[0],tmpPath[1])
					.addOption("�I��",new Msg("�K�K�O���\�Y�Ӧh�������{�@���ܡI�H",tmpPath[0],tmpPath[1])
					.addOption("�I��",new Msg("�K�K�M���ٹJ��F�ܦh�Ʊ��K�K�O����O�H�K�K",tmpPath[0],tmpPath[1])
					.addOption("�I��",new Msg("��K�K�Q���_�ӤF�I�K�K",tmpPath[0],tmpPath[1])
					.addOption("�I��",new Msg("��F�I�{�b�X�I�F�H",tmpPath[0],tmpPath[1])
					.addOption("�ݮɶ�",new Msg("�K�K�V�F�I�o���O�W�ߪ��ܡH�K�K�٭nDEBUG�I�I�I",tmpPath[0],tmpPath[1])
					.addOption("�����L�\",new Msg("����K�K����`ı�o�b�ڤ��ڤ]�O�o�˪��߱��O�K�K",tmpPath[0],tmpPath[1])
					.addOption("TRUE END", null,new Event() {
						@Override
						public void action() {
							sc.changeScene(new Game_AfterEnd(sc));
						}
					}))))))))))));
					return new MsgFlow(m);
				}
				tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
				tmpPath[1]="�H�H�H";
				switch(sc.getBlackScene().getEntrance()) {	//�¦�������J�f�B
				case 0:
					switch(storyController.getSF1()) {
					case 0:
						m = new Msg("�K�K�K�K�H", tmpPath[0], tmpPath[1]);
						m.addOption("���", new Msg("�ڬO�֡H�ڦb���H�ڦb�F���H", tmpPath[0], tmpPath[1])
								.addOption("���i����", new Msg("��K�K���ı�o��ǩǪ��K�K�H", tmpPath[0], tmpPath[1])
										.addOption("�ʰʤ��u", new Msg("�����q�ڪ��u�H�䱰�L�K�K", tmpPath[0], tmpPath[1])
										.addOption("�K�K", new Msg("�K�K�ˡK�K���@�Ѥ������w�P�I", tmpPath[0], tmpPath[1])
										.addOption("�C�}����", new Msg("�I�I�I")
										.addOption("�f���ۤv", new Msg("�ګ���ܦ��@�����F�I�H���������������������������")
										.addOption("���", null ,new Event() {
											@Override
											public void action() {
//												sc.changeScene(new Game_MainScene(sc));
												sc.changeScene(new NewGameHintScene(sc));
											}	
										}))))))
										.addOption("���Q�ʼu",new Msg("����v���I���K�K����]�V�ӶV�o���K�K", tmpPath[0], tmpPath[1])
										.addOption("BAD END",null,new Event() {
											@Override
											public void action() {
												sc.changeScene(sc.getGameOverScene());
											}					
										}))
								)
							.addOption("�����",new Msg("���J�@�����P�����K�K�û��I�I�U�h�K�K", tmpPath[0], tmpPath[1])
							.addOption("BAD END",null,new Event() {
									@Override
									public void action() {
										sc.changeScene(sc.getGameOverScene());
									}					
								}));
						return new MsgFlow(m);
					case 10:	//����
						m = new Msg("�K�K�K�K�H�H�H");
						m.addOption("�K�K�K", new Msg("�K�K�K�K�ڦ��F�H")
								.addOption("�K�K�K", new Msg("�K�K�K�K�K�K�K������H")
								.addOption("�K�K�K", new Msg("���^�ơI�H�I�H�I�H")
								.addOption("�Y��", null,new Event() {
							@Override
							public void action() {
								sc.changeScene(sc.getGameOverScene());
							}	
						}))));
						return new MsgFlow(m);
					case 11:	//����
						m = new Msg("�K�K�K�I�I�I");
						m.addOption("�_��", new Msg("�K�K�ھa�K�K�K�@����ڮ��_��\�K�K�K�K�K�K")
								.addOption("�_��", new Msg("�K�K�~�M�K�K�K�~�M���ɦ��\�I")
								.addOption("�_��", new Msg("�z�ɡI��\�����T�γ~�I�Z��������ڻ~�|�A�F�I")
								.addOption("�^�L��", null,new Event() {
							@Override
							public void action() {
								storyController.nextSF1(); // => �@��12
//								storyController.nextSF1(); // => �Ȯɥ�����13
								ac.setStep(Global.ACTOR_STEP);
								sc.changeScene(sc.getMainScene());
							}	
						}))));
						return new MsgFlow(m);
					}
				case Global.GROUND_OBSTACLE_HOME:
					msg = "�i�ӤF�H�]�ӷt�K�K";
					m = new Msg(msg);
					m.addOption("�w���Y��", new Msg("��K�K�n�a�K�K�ڥi�O���D�������A��ݤ]���|���K�K���ӡK�K")
							.addOption("�ۧڹ��y", new Msg("�����w���F�٥i�H���^�h�I�A�A�A�K�K�঺��M���n���I")
							.addOption("����`�J", new Msg("�]�����^�R�I�n�h�I�@�������I")
							.addOption("½��N��", new Msg("�쩳�ڬ�ԣ�|���ӴN�]�ӳo���a��A���٦b�@�ڡH")
							.addOption("�N���l", new Msg("��H�z�L���~�W�ŷL�������u�K�K")
							.addOption("�N�_����", new Msg("�n���i�H�ݨ�o�̦��@�ǳ��ª��ȱi���ɡI")
							.addOption("�ӲӬ�Ū", new Msg("�ڬݬݳ�K�K�o�³��ɦ��g���L�h�]�����ڳo���ܦ������˷����]�ӳo�K�K")
							.addOption("�ۨ��ۻy", new Msg("�L�̳��]�h���F�O�H�ܤ��ٯ��ӦP�żh�K�K")
							.addOption("�~���Ū", new Msg("�x�I�H�o�̭��ټg�۳o�����S�ƴN���H���_���ܡA�ܤ����U�������K�K")
							.addOption("�K�K", new Msg("�]���HŸ�^�]�Y�ֵo�¡^�]�_���֪��D�^�K�K")
							.addOption("�K�K", new Msg("�ˡK�K�ڬO���O�n���ַQ��k�樭�F�K�K")
									.addOption("�~��",null,new Event() {
										@Override
										public void action() {
											storyController.nextSF1();	// => �@��8
											sc.changeScene(sc.getMainScene());
										}					
									})))))))))))
							).addOption("�ڪ��Ѥ��Z",new Msg("�·t����o�쪺�T���q�Ӥ֡A�B�L�k�H�����I�M�I�C")
									.addOption("�K�K", new Msg("�ڻݭn�U�����ǳƦA�i�J�A�~�O�W�W���C")
									.addOption("�K�K", new Msg("�K�K���藍�O�]���کȶ¡I")
										.addOption("�g�G�O�O�O",null,new Event() {
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
						tmpPath[1]="�l��������";
						m = new Msg("�I�I�K�K�]��ݦS�S�^�K�K�ڤ���F�K�K�S���妺�]�n�֦��F�K�K�K�K");
						m.addOption("�~��", new Msg("�K�K�~�M�@���W���l�ۧڡK�K�K�K")
						.addOption("�~��", new Msg("�K�K�p���ٳQ�G�ۭn���V�K�K�oԣ�}�@�ɡI�H")
						.addOption("�Y��", new Msg("���I�ڭn�۫H�ڦ��D�������I")
						.addOption("�~��", new Msg("�K�K�K�K�K�K�o���N���Ѯɨ�A�`�Ӧ��a�I")
						.addOption("�~��", new Msg("���n�o��˷��a�I������")
						.addOption("�~��", new Msg("�i�̤j�H�I�q����]�R�q�n�^",tmpPath[0],tmpPath[1])
						.addOption("�Y��", new Msg("����������������w�g�l�W�ӤF�ܡI�H")
						.addOption("�Y��", new Msg("�S����ܤF�I���U�h�a�I�I")
						.addOption("�Y��", new Msg("�ЦѤѷݡB���ԡB�C�q�K�K�٬O���쯫���]�n�I")
						.addOption("�Y��", new Msg("�ЫO���ڪ��������������������I�I�I�I�I")
						.addOption("�ĤO�@��", null,new Event() {
							@Override
							public void action() {
								storyController.nextSF1();	// => �@��16
								sc.changeScene(new Game_End(sc));	//����
							}
						})))))))))));
						return new MsgFlow(m);
					}else {
						m = new Msg("�K�K�K�K�K�K�o�̭��n�j�I");
						m.addOption("�ۨ��ۻy", new Msg("�K�K�p���ܦ������ڡK�K���ӬO���|�����K�K�K�K�]�ݦV�U�V�`�W�^")
								.addOption("�ۨ��ۻy", new Msg("�K�K�ڨӳo�̨쩳�n�F���K�K�K")
								.addOption("�F���@��", new Msg("��F�I�������D�����V�@�w�j�������A������ֶܡH")
								.addOption("�S��", new Msg("�ګ�ˤ]���ۥD�������a�H�ڭn�۫H���V�����w�߶ܡH")
								.addOption("�۫H", new Msg("�]�ĤO�@���^���p���ȡ�ڬO���Ҳ��U���d������������")
										.addOption("�i�}����", null,new Event() {
											@Override
											public void action() {
												sc.changeScene(sc.getGameOverScene());
											}					
										})).addOption("���H",new Msg("�Q���^�h�]���O�o�˪��a�I�{�F�{�F�I")
												.addOption("���}",null,new Event() {
											@Override
											public void action() {
												sc.changeScene(sc.getMainScene());
											}					
										}
								))))));
						return new MsgFlow(m);
					}
				case Global.GROUND_OBSTACLE_CENTER:
					m = new Msg("�����N�N��J�A�u�O��i��E�I");
					m.addOption("�\���\�}", new Msg("�������a�D�`���𬣩O�I")
							.addOption("�\���\�}", new Msg("�S�Q������ٮ����~������I")
							.addOption("�|�B�[��", new Msg("��W���������M���������ˤ��������˹��~�K�K")
							.addOption("�[��˹�", new Msg("�o���ٯu���ܱ��R���Ҥƨ����ϥ@�D�O�I")
							.addOption("�|�B�[��", new Msg("���䪺�G�d�W���Q�r�[�Q���I")
							.addOption("�����[��", new Msg("�������`���W�а󪺲ߺD�ܡH")
							.addOption("�I�����", new Msg("���W���٦��@�ǹϤ�Ÿ��K�K�����~�Ӫ̪��ڹ�b�ݤ����K�K�]�k�Y�N�ա^")
							.addOption("�I�����", new Msg("���I�o���ڷQ��A�ڧ����һ��A�b�s�y��F�@��ܼF�`���Ȫ̡I")
							.addOption("�I�����", new Msg("�W���Ѳz�B�U���a�z�A���`�O��@�Ƿs�_�j�Ǫ��F��I")
							.addOption("�I�����", new Msg("�]�\�L�|���D�Q�r�[�W�����Ÿ��O����N��I")
							.addOption("�I�����", new Msg("�K�K�K�K�K�K�K�K�K�K�K�K")
							.addOption("�I�����", new Msg("�ܻ��K�K�b�o�@�ɤ]�ݤF�@�}�l�F�K�K�K")
							.addOption("�I�����", new Msg("�b�����̡A�����̳��ܩM�֡A��ڤ]�ܿˤ��I")
							.addOption("�I�����", new Msg("�K�K�K���`ı�o���I�H�M�P�O�I")
							.addOption("�I�����", new Msg("��K�K�O����O�H�����ӤW�ӡK�K")
							.addOption("�����", new Msg("��F�I�X�Q�o�{�e���M�h�a�I")
							.addOption("���}", new Msg("��F�I�b�����e�����Q�r�[�ܡH")
							.addOption("����", new Msg("�]����o�ϧޯ�w�l���«C�^�n�F�I���ְ������}�a�I")
									.addOption("���}", null,new Event() {
										@Override
										public void action() {
											if(ac.getHasPotLid()) {	// ����@��11:����
												storyController.nextSF1();
												storyController.nextSF1();
//												sc.getMainScene().getCamera().setIsRain(true);
												sc.changeScene(sc.getMainScene());
												ac.setHasCross(true); //������Q�r�[�~�i�H���Q���ʼ@��13
											}else {
												storyController.nextSF1(); // �@��10:����
//												sc.getMainScene().getCamera().setIsRain(true);
												sc.changeScene(sc.getMainScene());
											}
										}					
									})).addOption("����",new Msg("�ӥi�äF��o���H�N�����|�D�H��áI")
											.addOption("���}",null,new Event() {
										@Override
										public void action() {
											if(ac.getHasPotLid()) {
												storyController.nextSF1(); // ����@��11
												storyController.nextSF1();
//												sc.getMainScene().getCamera().setIsRain(true);
												sc.changeScene(sc.getMainScene());
											}else {
												storyController.nextSF1(); // �@��10:����
//												sc.getMainScene().getCamera().setIsRain(true);
												sc.changeScene(sc.getMainScene());
											}
										}					
									}
							)))))))))))))))))));
					return new MsgFlow(m);
				case Global.GROUND_OBSTACLE_STORE:
					tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
					tmpPath[1]="�ө��󪺧���";
					switch(storyController.getSF1()) {
					case 3:
						m = new Msg("�o���O�i�̤j�H�ܡI",tmpPath[0],tmpPath[1]);
						m.addOption("���۩I", new Msg("�١�A�̦n�ڡI�L�K���n�N��L�K�ɹL�@�U��")
								.addOption("�}������", new Msg("�i�̤j�H�ӳo��O�n�R����ܡH",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("�S�S�S�I�ڴN�Q�ӭɥΤ@�U�j�|�s�����Ӹܤ@�U�I")
								.addOption("�~��", new Msg("�i�̤j�H�n���Ĥ@�U�������g���c�a�ܡI",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("����I�o�j�]���I�L�K�L�K�����O�աI�ڦ���ơI")
								.addOption("�~��", new Msg("�i�̤j�H�A���J���b�o�̡A�ШϥΡI",tmpPath[0],tmpPath[1])
								.addOption("���L���J��", new Msg("�y�I�j�|���i�A���ӽֽֽ֮a���ܡA���ֱ��A�a�p�B�ͦ^�a�o�I�L�K�L�K")
										.addOption("��������", null,new Event() {
											@Override
											public void action() {
												storyController.nextSF1();  // => �@��4
												sc.changeScene(sc.getMainScene());
											}
										}))))))));
						return new MsgFlow(m);
					default:
						m = new Msg("�ө���ͷN�D�`�n�A�����̵�ö�����A���������i�h�I");
						m.addOption("���}", null,new Event() {
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
						tmpPath[1]="�Ȫ�";
						m = new Msg("���n�N��K�K���Z�F�K�K�K�K");
						m.addOption("�[��", new Msg("�����K�K�K�]�@�}��z�F�誺�n���^",tmpPath[0],tmpPath[1])
								.addOption("���շf��", new Msg("�ˡK�K���ӡK�K�K�K�H")
								.addOption("�~��", new Msg("�]���Y�^�O�o�@�����i�̶ܡH�o���o�@�]�ӧ֡K�K",tmpPath[0],tmpPath[1])
								.addOption("�ð�", new Msg("����N��H�o�ͤ���ƤF�H")
								.addOption("�~��", new Msg("�K�K�]��z���n�I�_�^�K�K�K�A���G�o�{�o�ӧ������u�ۤF�I",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("����|�Ȯɤ@�}�̶áA�ڱo���ְk���o�̤F�I",tmpPath[0],tmpPath[1])
								.addOption("���d", new Msg("�����I�A�����u�۬O�K�K�H�|�O��o�ӤQ�r�[�����ܡH")
								.addOption("�~��", new Msg("�]�@�h�^�K�K�o�O�H�K�K�A�h�F�����a�ܡI�H",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("���ǡK�K���I�o�����C�g�L�@�q�ɶ��A�K�|�~�T�@���I",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("�A�O�g�ѻݭn���U�������ҩI��ӨӪ��A�Q���R���i�̡I",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("�o�Q�r�[�W�g���r�O�u�H�����B�o�å͡v�A�O�ӹ����ܰ@�۪��H���I",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("���ƹ�W�L�̻{���Y���������סA�N�u���i�H�o�å͡I",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("�A�O�Q��@�������J�ءA�ӳQ�l��ӨӡI",tmpPath[0],tmpPath[1])
								.addOption("�����@�_", new Msg("�L�K�L�K�L�K�L�K�L�K�L�I�I�I�I")
								.addOption("�_��", new Msg("����I�H�ҥH�L�̧�ڧQ�Χ�����L�K�L��J���ܡI�H")
								.addOption("�~��", new Msg("�ӥL�̥l��A�Ӥ]�|�I�X�������N���A�|�H�m�����覡�I",tmpPath[0],tmpPath[1])
								.addOption("�_��", new Msg("�ҥH�L�K�H�e�����ǫi�̡L�K�K�٦����ܪ������L�K�L�K�O�����~�L�K�L�K")
								.addOption("�~��", new Msg("�ڳ��b�|���l��i�̪��ɴ��A�~��M�����b���A�ݨӤ���F�I",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("���ݸު��O�L�K�K�h�~�U�ӡA�������q���ܹL�L�K�K���D�u�p�L�̩һ��L�K�K",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("�y�y�y�y�I")
								.addOption("�~��", new Msg("���ޤF�I�p�G�A�Q�ѨM�o��ƪ��ܡL�K�K�A����h�@��а�a�I",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("���N���i�̪��I�A�n�۬����I",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("�L�K�L�ڪ��D�F�L�K�L���§A�i�D�ڳo��h�L�K�K�L")
										.addOption("���}", null,new Event() {
											@Override
											public void action() {
												storyController.nextSF1();  // => �@��13
												sc.changeScene(sc.getMainScene());
											}
										}))))))))))))))))))))))));
						return new MsgFlow(m);
					}else {	
						tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
						tmpPath[1]="���ê��h�L";
						m = new Msg("���n�N��K�K���Z�F�K�K�K�K");
						m.addOption("�[��",new Msg("�b�O�̬ݰ_�ӫܭ�áK�K��B���O½�˪������K�K")
							.addOption("����",new Msg("�_�ǡK�K�H�S���H�ܡH")
							.addOption("�I��",new Msg("���ǥͬ��Ϋ~�����F�A���D���ӤH�w�g���}�F�ܡH")
							.addOption("�I��",new Msg("�K�K�o�N�·ФF�I�K�K���X�h�A���a�I")
							.addOption("�ਭ",new Msg("�]��M�����^�СССЫ��СССС�",tmpPath[0],tmpPath[1])
							.addOption("�~��",new Msg("�����a�I�I�I�I�I�]���^",tmpPath[0],tmpPath[1])
							.addOption("�Ӥ��Τ���",null,new Event() {
									@Override
									public void action() {
										sc.changeScene(sc.getGameOverScene());
									}
								})))))));
						return new MsgFlow(m);
					}
				case Global.GROUND_OBSTACLE_EQUIPMENT:
					tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
					tmpPath[1]="�Z��������";
					if(ac.getHasBoon() == 0) {
						m = new Msg("�z�I");
						m.addOption("�}�}", new Msg("���o��w�{�G�����ݥ��A�u������")
								.addOption("�~��", new Msg("�K�I�o���O�i�̤j�H�ܡH�w��j�r���{�I",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("����Ȯ�F�I�H�K�ݬݡI")
								.addOption("�~��", new Msg("�����i�̤@�w�n���O�����Y�I�������I",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("�]���C�y�^�ڳo���W�F�`�������_�K�K�����D�i�̦��S������H",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("�]�P�˧C�y�^�K�K�I�������󦳤������A�ɶq�}�I�@�w�ϩR���F�I")
								.addOption("�~��", new Msg("���o�F��]��O�y�Ǥw�[�������A�쥻�Q���e���i�̤j�H�����@�j�U�O�I",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("�y�I���N�O�ʤF�@�ǭ��n�����ơI",tmpPath[0],tmpPath[1])
								.addOption("�~��", new Msg("�O������ƩO�H")
								.addOption("�~��", new Msg("�����ζǻ��������w�����A�ҿi���������~��h�״_���I�L�K�L�K")
								.addOption("�~��", new Msg("��L�K�L�K�o�˳�I���O����F��O�H�]�I����ҡ^")
										.addOption("���}", null,new Event() {
											@Override
											public void action() {
												sc.changeScene(sc.getMainScene());
											}
										}))))))))))));
						return new MsgFlow(m);
					}else if(ac.getHasBoon() >= 4) {
						m = new Msg("�oĲ�P�I�o��w�סI�N�O���I�u�O�ӴΤF�I",tmpPath[0],tmpPath[1]);
						m.addOption("�~��", new Msg("�I�K�K�o�K�K�ᦳ���q�K�K�I�]��ݦS�S�^")
								.addOption("�~��", new Msg("�i�̤j�H�еy���ڤ@�|�I",tmpPath[0],tmpPath[1])
								.addOption("����", new Msg("�]���i��î��^�]�Ǩӿi�M�N�N�n�^",tmpPath[0],tmpPath[1])
								.addOption("����", new Msg("�]�`�l�@�f��^�K�K�K�������I�K�K�K��I�K�K�K",tmpPath[0],tmpPath[1])
								.addOption("�K�K�K", new Msg("�K�K�K�K�K�K�K�K�K�K�K")
								.addOption("�~��", new Msg("�]����X�{�^�o�N�O���������_�I�m���i�̤j�H�I",tmpPath[0],tmpPath[1])
								.addOption("����", new Msg("�K�K�K���¦���I")
								.addOption("�~��", new Msg("�u�r�I���ۤv�H�I�����¡I",tmpPath[0],tmpPath[1])
								.addOption("�|�|����", new Msg("�K�K�K�]OS:�o�K�O�K�K��\�S���a�I�گu���O�Q�F�a�H�^")
								.addOption("�˳�", new Msg("�K�K�K�K�K�K�]�i�̤w�˳���\�^")
								.addOption("���}", null,new Event() {
								@Override
								public void action() {
									sc.changeScene(sc.getMainScene());
									ac.setHasBoon(0);
									ac.setHasPotLid(true);
								}
							})))))))))));
						return new MsgFlow(m);
					}else if (ac.getHasBoon() > 0 && ac.getHasBoon() < 4){
						m = new Msg("�o�ǻ��������w�����A�쩳���󪫩O�H",tmpPath[0],tmpPath[1]);
						m.addOption("�~��", new Msg("���I�i�̤j�H�ӤF�I�W���ѤF��A���o�F��j���ݭn�u�|�ӡv�~���I",tmpPath[0],tmpPath[1])
						.addOption("�~��", new Msg("�W���ѤF��z���״_�����ƻݭn�u�|�ӡv�~���I",tmpPath[0],tmpPath[1])
						.addOption("�~��", new Msg("����I�H�����I���I���")
						.addOption("���}", null,new Event() {
							@Override
							public void action() {
								sc.changeScene(sc.getMainScene());
							}
						}))));
						return new MsgFlow(m);
					}
				case Global.GROUND_OBSTACLE_CHURCH:
					tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
					tmpPath[1]="�]�ƪ�����";
					m = new Msg("�K�K�K�����K�K�K�����ܦ��Ǫ��F�I�I�I");
					m.addOption("���~", new Msg("�K�K�K�������K�K�K�]�R�q�n�^�K�K�K�O�i�̤j�H���K�K�K",tmpPath[0],tmpPath[1])
						.addOption("�~��", new Msg("�o���୮�ˤl�u�O�m��F",tmpPath[0],tmpPath[1])
						.addOption("�~��", new Msg("�K�K�K�аݫi�̤j�H��Ƴo����^�^�O�H",tmpPath[0],tmpPath[1])
						.addOption("�~��", new Msg("�K�K�K�����̳��ܷR���z�K�K���@�z�K�K",tmpPath[0],tmpPath[1])
							.addOption("�~��", new Msg("���~��d�b�������a�K�K�������������K�K�K�K",tmpPath[0],tmpPath[1])
							.addOption("�~��", new Msg("�K�K�K�K�ڪ��ѰڡK�K�K�K�]�˰h�T�B�^")
							.addOption("���~", new Msg("�o�K�K�ڨ쩳�O�����]�B���]�B�٬O���]�O�H�]�C�n�^")
							.addOption("�~��", new Msg("�K�K�������K�K�i�̤j�H�Q�h���O�H�]�����G��^",tmpPath[0],tmpPath[1])
							.addOption("�P��", new Msg("�K�K�S�����K�K�o�̮��n�����K�K�����K�K�]�����h��^")
							.addOption("�~��", new Msg("�K�K�K�R�R�R�K�K�K�]�ͪ��ֳt�������n���^",tmpPath[0],tmpPath[1])
							.addOption("���~", new Msg("�ֶ]�I�I�I�I�I�����l�ӤF�I�I�I�I�I�I")
							.addOption("�k�]", null,new Event() {
								@Override
								public void action() {
									storyController.nextSF1();  // => �I�μ@��14
									storyController.nextSF1();  // => ������15
									sc.changeScene(sc.getMainScene());
								}
						}))))))))))));
					return new MsgFlow(m);
				default:
					return new MsgFlow(new Msg("�q���~�ݶi�h�@�����¡I"));
				}
			///////////////////////////�¦����end//////////////////////////////////
			/////////////////////////// �@��end//////////////////////////////////
			case Global.GROUND_WATER:
				msg = "�n����������I�]�^�_�]�O�^";
				m = new Msg(msg);
				m.addOption("�O", new Msg("�����A���n�ܡ�Pı��O�R�K�I�]MP�����^"),new Event() {
					@Override
					public void action() {
						ac.getStatus().setCurrentMP(ac.getStatus().getTotalMP());
					}					
				}).addOption("�_",new Msg("����i���[�Ӥ��i�����j�I"));
				return new MsgFlow(m);
			case Global.GROUND_FRUIT:
				msg = "�o�Ǥ��G�ݰ_�Ӧn�n�Y�I�]�^�_��O�^";
				m = new Msg(msg);
				m.addOption("�O", new Msg("�P�§����������ذe��Y�o�n���I�]HP�����^"),new Event() {
					@Override
					public void action() {
						ac.getStatus().setCurrentHP(ac.getStatus().getTotalHP());
					}					
				}).addOption("�_",new Msg("�A�Y�U�h�A�L�ڴN�n�Q���l�ѩ^�_�ӤF�I"));
				return new MsgFlow(m);	
			case Global.GROUND_LIGHT:
				msg = "�ŷx���G��꺤��I";
				m = new Msg(msg);
				m.addOption("����L�h", new Msg("�n�S�I�Q�S�ˤF�I")
					.addOption("�~��",null,new Event() {
					@Override
					public void action() {
						sc.changeScene(sc.getGameOverScene());
					}					
				})).addOption("�O���Z��",new Msg("�n�x�M��ı�o�������ΡI�]�o���+10MP�^"),new Event() {
					@Override
					public void action() {
						ac.getStatus().setCurrentHP(ac.getStatus().getCurrentHP()+10);
					}					
				});
				return new MsgFlow(m);	
			case Global.GROUND_WATERPOOL:
				msg = "��սS򡪺�r���U�A���i���`�`���ˬy�I";
				m = new Msg(msg);
				m.addOption("��a", new Msg("���a�I����r�I�ڲ{�b�O�@�����I�B�P�B�P�K�K�]�ܦ��������^")
					.addOption("����", null,new Event() {
					@Override
					public void action() {
						sc.changeScene(sc.getGameOverScene());
					}					
				})).addOption("�Y��",new Msg("����@�ӾA�X��ӥ��d���n���I������")
						.addOption("�~��",new Msg("�����n���������]�o���+10MP�^"),new Event() {
							@Override
							public void action() {
								ac.getStatus().setCurrentHP(ac.getStatus().getCurrentHP()+10);
							}					
						}));
				return new MsgFlow(m);	
			case Global.GROUND_OBSTACLE_MOUNTAIN:
				if (storyController.getSF1() >= 15) {
					msg = "�o�̬O�q����s�����I";
					m = new Msg(msg);
					m.addOption("�~��", new Msg("�ӥi�ȤF�I�j�k���������I")
							.addOption("�s�u�a��", new Msg("�֨��֨��֨��I")
							.addOption("�Ĩ�", new Msg("����^�ӰաI"),new Event() {
										@Override
										public void action() {
											sc.changeScene(sc.getBlackScene(),pic,ac);
											ac.changeDirection(Global.DOWN);
										}					
							})));
					return new MsgFlow(m);
				}else {
					msg = "�o�̬O�q����s�����I";
					m = new Msg(msg);
					m.addOption("�ۨ��ۻy", new Msg("�������A�L�h�N�O�a�V�F�I")
							.addOption("�ۨ��ۻy", new Msg("�n�L�h�ܡH")
							.addOption("����", new Msg("�N�O�n�h�ݬݡ�")
									.addOption("�e��", new Msg("�^��D�����I"),new Event() {
										@Override
										public void action() {
											sc.changeScene(sc.getBlackScene(),pic,ac);
											ac.changeDirection(Global.DOWN);
										}					
									})).addOption("���n",new Msg("�h����O�n�F���H"))));
					return new MsgFlow(m);
				}
			case Global.GROUND_OBSTACLE_ENTRANCE:
				if(storyController.getSF1() < 8) {
					m = new Msg("�o�̬O���������f�I");
					m.addOption("�ۨ��ۻy", new Msg("�b�~�������D�|�q�����O��")
							.addOption("�ۨ��ۻy", new Msg("�O�n���}�F�ܡH")
							.addOption("�@�L�d��", new Msg("�n�Q�h�����~�����@�ɡI")
									.addOption("�ǳƫe��", new Msg("�]����}�B�^�K�K����ԡI�n��������Ʊ��B�z�����I"),new Event() {
										@Override
										public void action() {
											ac.changeDirection(Global.UP);
										}					
									})
							).addOption("�̨̤���",new Msg("������̳��إ߷P���F�A�A�r�d�@�U�n�F�I"))));
					return new MsgFlow(m);
				} else if (storyController.getSF1() == 8) {
					tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.VILLAGE_HEAD);
					tmpPath[1]="����";
					m = new Msg("�ӥi�ȤF�㻰�ְ{�H�P�I");
					m.addOption("���}", new Msg("�ȤF�ȤF��I")
							.addOption("�~��", new Msg("�i�̤j�H�Яd�B�I",tmpPath[0],tmpPath[1])
							.addOption("�ਭ", new Msg("�аݫi�̤j�H�n�e����B�O�H",tmpPath[0],tmpPath[1])
							.addOption("�ð_���", new Msg("�ˡK�K�S�ԡI�K�K�Ѯ�u�n��Q�X�h�����K�K�K�]�j�f��^")
							.addOption("�~��", new Msg("�o�˰ڡI�����@�U�Ѯ���G�|���Ӧn�O�I",tmpPath[0],tmpPath[1])
							.addOption("�~��", new Msg("�Ыi�̼Ȯɯd�b�������a�I",tmpPath[0],tmpPath[1])
							.addOption("�~��", new Msg("���K�K�n���n���K�K�]���C�^"),new Event() {
								@Override
								public void action() {
									ac.changeDirection(Global.UP);
									sound=new MusicController(PathBuilder.getAudio(MusicPath.Sound.THUNDER));
									sound.playOnce();
									storyController.nextSF1();	// => �@��9
								}	
							})))))));
					return new MsgFlow(m);
				}else if (storyController.getSF1() == 9) {
					m = new Msg("�X�h���Ʊo�w�w�F�I���q���pĳ�I");
					m.addOption("�ۨ��ۻy", new Msg("���{�b�٥i�H������O�H")
							.addOption("�ۨ��ۻy", new Msg("�쩳������L�������������|���ܩO�H")
							.addOption("�ۨ��ۻy", new Msg("�J�M����X�h�A���N�b�������M��u���n�F�I")
							.addOption("�ۨ��ۻy", new Msg("�������j�h�Фl�w�g�h�L�F�I")
							.addOption("�ۨ��ۻy",new Msg("�٦����Ǧa��S���h�L�O�H"))))));
					return new MsgFlow(m);
				}else if (storyController.getSF1() >= 15) {
					m = new Msg("�K�K�K�KQQ�I�I�I");
					m.addOption("�~��", new Msg("������������㬰��������٬O�L�k�X�h�I�H")
							.addOption("�Y��", new Msg("�o�i����K�K�K��ӧ������H�����Y�a�K�K�K")
							.addOption("�~��", new Msg("�K�K�K�ڳ����o��h���F�K�K�K�I")
							.addOption("�~��", new Msg("�K�K�K�ڥi���Q�@�����b�o�̪����������I")
							.addOption("�A���Y��", new Msg("���ڦ��^�h�a�I")
							.addOption("�~��", new Msg("�K�K�������K�K�K�A�h���@���V�K�K�K�I�H")
							.addOption("�K�K", new Msg("�K�K�K�K�K�K�K�K�K")
							.addOption("�~��", new Msg("���ޤF�I����������I�o���ɨ�A���V�����w���`�|�ͮħa�I")
							.addOption("�ਭ", new Msg("���֥��X�@�Ӭ�}�f�A�Ĩ��s�a�I"),new Event() {
								@Override
								public void action() {
									ac.changeDirection(Global.UP);
								}	
							})))))))));
					return new MsgFlow(m);
				}else {
					return new MsgFlow(new Msg("�V�F�I�q�������~���D���Q�ʦ�F�I"));
				}
			case Global.GROUND_OBSTACLE_CENTER:
				if(storyController.getSF1() < 9) {
					return new MsgFlow(new Msg("��������������R���A�B�~��b���l�������I"));
				}
				if(storyController.getSF1() == 9) {
					m = new Msg("�o�̬O�����a�I");
					m.addOption("�ۨ��ۻy", new Msg("�����b�����O�Q���w���歫���H���I")
							.addOption("�ۨ��ۻy", new Msg("�������a�̤����D���S����������D��νu���H")
							.addOption("�ۨ��ۻy", new Msg("�n�i�h�ݬݶܡH")
							.addOption("��M", new Msg("�̦n��o�{�Ӥ����_���I")
									.addOption("��J", new Msg("�^��D����"),new Event() {
										@Override
										public void action() {
											sc.changeScene(sc.getBlackScene(),pic,ac);
											ac.changeDirection(Global.DOWN);
										}					
									})
							).addOption("����",new Msg("�N����ܦ����F�I�ڤ]�n�d���ڳo�~�ʰ��䪺�H��I")))));
					return new MsgFlow(m);
				}else {
					return new MsgFlow(new Msg("�{�b���O�i�J���ɾ��F�I"));
				}
			case Global.GROUND_OBSTACLE_EQUIPMENT:
				if(storyController.getSF1() >= 15) {
					return new MsgFlow(new Msg("�{�b���O�i�J���ɾ��F�I"));
				}
				if(ac.getHasPotLid()) {
					m = new Msg("�~�W������\�ƥ󤧫�K�K");
					m.addOption("�ۨ��ۻy", new Msg("�˳Ʃ�������N�h�Ȧ�F�I")
							.addOption("�ۨ��ۻy", new Msg("���O�n��M�ǻ������˳Ƨ��ƤF�I")
							.addOption("�K�K�K�K", new Msg("�K�K�K�K�K�K�K�K")
							.addOption("�I��", new Msg("�K�K�K�u�����O���]�ܡH")))));
					return new MsgFlow(m);
				}else {
					if(ac.getHasBoon() == 0) {
						m = new Msg("�o�̬O�˳Ʃ��I");
						m.addOption("�ۨ��ۻy", new Msg("�ݩ��~���C�F�ܦh�ܼF�`���Z���I")
								.addOption("�ۨ��ۻy", new Msg("�K�K��]�\�̭��������F�`���S���X�ӡI")
								.addOption("�ۨ��ۻy", new Msg("�n�i�h�ݬݶܡH")
								.addOption("��M", new Msg("�ݬݤS���ο���O�a�I")
										.addOption("�i�J", new Msg("�^��D����"),new Event() {
											@Override
											public void action() {
												sc.changeScene(sc.getBlackScene(),pic,ac);
												ac.changeDirection(Global.DOWN);
											}					
										})
								).addOption("���F",new Msg("�u�r��S����߱��}�}�I")))));
						return new MsgFlow(m);
					}else if(ac.getHasBoon() > 0) {
						m = new Msg("�o�̬O�˳Ʃ��I");
						m.addOption("�ۨ��ۻy", new Msg("�n�Q���D���󪺩ҿ������_�O�h�F�`�I")
								.addOption("�ۨ��ۻy", new Msg("�n�i�h�ݬݶܡH")
										.addOption("�i�J", new Msg("�^��D����"),new Event() {
											@Override
											public void action() {
												sc.changeScene(sc.getBlackScene(),pic,ac);
												ac.changeDirection(Global.DOWN);
											}					
										}).addOption("���F",new Msg("�U���a�I�٦����O���|�I"))));
						return new MsgFlow(m);
					}
				}
			case Global.GROUND_OBSTACLE_CHURCH:
				if(storyController.getSF1() < 13) {
					m = new Msg("�o�̬O�а�A���ڨS���o�ӫH���A�����D�i�h�n�F���I");
					m.addOption("�ۨ��ۻy", new Msg("���а�i�ҿ׻��O�������n���믫��W�I"));				
					return new MsgFlow(m);
				}
				if(storyController.getSF1() == 13) {
					m = new Msg("�o�̴N�O�а�F�I");
					m.addOption("�̮𾮯�", new Msg("�K�K�K�K�K�K�K�K�K")
					.addOption("�̮𾮯�", new Msg("�K�K�p�G�Ȫ̩Ҩ��ݹ�K�K�K�K")
					.addOption("�̮𾮯�", new Msg("���i�h��A���{���N�O�K�K�K�K�̫᪺����I")
					.addOption("�̮𾮯�", new Msg("�K�K�]�ݡ^�K�K�K���K�K���Q����K�K�K�K")
					.addOption("�̮𾮯�", new Msg("���K�K�K�K�i�J�а�̶ܡH")
							.addOption("�O", new Msg("�^��D����"),new Event() {
								@Override
								public void action() {
									sc.changeScene(sc.getBlackScene(),pic,ac);
									ac.changeDirection(Global.DOWN);
								}					
							}).addOption("�_",new Msg("�����I�o�ɭ����ӭn�s�_���I�I")))))));
					return new MsgFlow(m);
				}else {
					return new MsgFlow(new Msg("�{�b���O�i�J���ɾ��F�I"));
				}
			case Global.GROUND_BONE:
				m = new Msg("�����W�ͪ������e�A�O�H�򰩮��M�I");
				m.addOption("�[��", new Msg("�n���@�Ǩ��ܡH�]�\���ΡI")
						.addOption("����", new Msg("���^�h��s��s�A�]�\�ٯ����I"),new Event() {
									@Override
									public void action() {
										ac.IncreaseHasBoon();
										// �Q�����Y����
									}					
								}).addOption("����",new Msg("���n�þߪF��A�p�ߥ�flag�I")));
				return new MsgFlow(m);
			case Global.SLIME:
				tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
				tmpPath[1]="�v�ܩi";
				msg = "�s������ꪺ�v�ܩi��§A�ĹL�ӤF�I";
				m = new Msg(msg, tmpPath[0], tmpPath[1]);
				m.addOption("�O",new Msg("�԰������I"),new Event() {
					@Override
					public void action() {
						sc.changeScene(new BattleScene(sc, ac, (Actor)obj));
					}
				});
				return new MsgFlow(m);	
			case Global.SLIME-2:
				tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
				tmpPath[1]="���H��";
				msg = "�i�Ȫ����H���§A�ĹL�ӤF�I";
				m = new Msg(msg, tmpPath[0], tmpPath[1]);
				m.addOption("�O",new Msg("�԰������I"),new Event() {
					@Override
					public void action() {
						sc.changeScene(new BattleScene(sc, ac, (Actor)obj));
					}
				});
				return new MsgFlow(m);	
			case Global.MONSTER:
				tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
				tmpPath[1]="�]�Ƨ���";
				msg = "�ܦ��Ǫ���������§A�ĹL�ӤF�I";				
				m = new Msg(msg, tmpPath[0], tmpPath[1]);
				m.addOption("�O",new Msg("�԰������I"),new Event() {
					@Override
					public void action() {
						sc.changeScene(new BattleScene(sc,ac,(Actor)obj));
					}
				});
				return new MsgFlow(m);
			case Global.SOLDIER:
				if(storyController.getSF1() >= 10) {
					tmpPath[0]=PathBuilder.getImg(ImagePath.Character.Facial.NONE);
					tmpPath[1]="���ޤh�L";
					msg = "���g�����ˤ����h�L�A�p�������c�٪��§A�ĹL�ӤF�I";				
					m = new Msg(msg, tmpPath[0], tmpPath[1]);
					m.addOption("�O",new Msg("�԰������I"),new Event() {
						@Override
						public void action() {
							sc.changeScene(new BattleScene(sc,ac,(Actor)obj));
						}
					});
					return new MsgFlow(m);
				}else {
					return new MsgFlow(new Msg("�O�ç����w�����L�W�^���I"));
				}
			case Global.GROUND_OBSTACLE_PROP:
				if(storyController.getSF1() >= 13) {
					return new MsgFlow(new Msg("�{�b���O�i�J���ɾ��F�I"));
				}else if(storyController.getSF1() < 12) {
					return new MsgFlow(new Msg("�b�O���D�H���G���b�A���n�����i�h�n�F�I"));
				}else {
					m = new Msg("�o�̴N�O�Ȫ̪��b�O�I");
					m.addOption("��ݦS�S", new Msg("�쩳�ڬ�����@�X�ӧ����a�A�N�Q�����ĵ��I�H")
							.addOption("��ݦS�S", new Msg("���M�ڰ����ƪ��T�����K�K")
							.addOption("��ݦS�S", new Msg("���o�����u�������D�ڡI")
							.addOption("�ۨ��ۻy", new Msg("�L�h���󦳥��ܪ������H")
							.addOption("�ۨ��ۻy", new Msg("���ǹL���ӳo�䪺�i�̫e���̥h���F�H")
							.addOption("�ۨ��ۻy", new Msg("���M�i��ݦ����X�K�K�K�K")
							.addOption("�ۨ��ۻy", new Msg("���ڤ@���L�k�X�h�o�ӧ����I")
							.addOption("�ۨ��ۻy", new Msg("�K�K�K�K�ڨ쩳�ӫ��^�ӭ�Ӫ��@�ɩO�H")
							.addOption("�ۨ��ۻy", new Msg("���I�`���o�a��A���y�[�d�F�I")
							.addOption("�ۨ��ۻy", new Msg("�n�i�h�b�O�F�ܡH")
							.addOption("�i�J", new Msg("�^��D����"),new Event() {
								@Override
								public void action() {
									sc.changeScene(sc.getBlackScene(),pic,ac);
									ac.changeDirection(Global.DOWN);
								}					
							}).addOption("����",new Msg("�A�����n�F�I"))))))))))));
					return new MsgFlow(m);
				}
			///////////////////////////////////////////////////////
			case Global.GROUND_SKYTREE:
				msg = "�B�ѻ\�a���Ѫž�I";
				break;
			case Global.GROUND_TENT:
				msg = "�O�ӫܤj���b�O�A�ݰ_�Ө����˫ܦh�����I";
				break;
			case Global.GROUND_SHELF:
				if(storyController.getSF1()>=11) {
					msg = "�{�b�S���𮧪��߱��F�I";
				}else {
					msg = "�g�N�S�D�n���𮧳B�I";
				}
				break;
			case Global.GROUND_HOUSE:
				if(storyController.getSF1()>=15) {
					msg = "�Фl�ۤ��F�I�I�I";
				}else {
					msg = "�Фl�\���ܰ�T�w���I";
				}
				break;
			case Global.GROUND_TREE:
				if(storyController.getSF1()>=15) {
					msg = "��ۤ��F�I�I�I";
				}else {
					msg = "�گ�ֵ֡B�{�{�����I";
				}
				break;
			case Global.GROUND_TURF_WALL:
				msg = "���Ǯk������¶���ý��I";
				break;
			case Global.GROUND_TURF_LADDER:
				msg = "����W�����F�C�a�I";
				break;
			case Global.GROUND_CHAIR:
				if(storyController.getSF1()>=15) {
					msg = "�{�b���O���U���ɭԡI";
				}else {
					msg = "�ѤH���������ȡA�i���}�o�̤����ڧ�QQ�I";
				}
				break;
			case Global.GROUND_STATUE1:
				msg = "��իD�Z�����s�J���I";
				break;
			case Global.GROUND_GOODS:
			case Global.GROUND_BOXES:
				msg = "�o�̰��������I";
				break;
			case Global.GROUND_STATUE2:
				msg = "�k���J�����S�۲��M���R�������I";
				break;
			case Global.GROUND_EQUIPMENT1:
			case Global.GROUND_EQUIPMENT2:
			case Global.GROUND_EQUIPMENT3:
				msg = "�n�h�x���Ѫ����Ÿ˳ơI";
				break;
			case Global.GROUND_PROP1:
			case Global.GROUND_PROP2:
			case Global.GROUND_PROP3:
			case Global.GROUND_PROP4:
				msg = "�n�h�����������j�ǹD��I";
				break;
			case Global.GROUND_GROSS:
				msg = "�а�лx�ʪ��Q�r�[�лx�I";
				break;
			case Global.GROUND_GRAVE:
				msg = "Rest In Peace�K�K";
				break;
			case Global.BUTTERFLY:
				msg = "�����㽹����ͪ��u���R�I";
				break;
			case Global.GROUND_PAINO:
				if(storyController.getSF1()>=15) {
					msg = "�{�b���Oť�^�n���ɭԤF�I";
				}else {
					msg = "�u�����^�n�A�b�p�s�Y�W�A�n�����ڡI";
				}
				break;
			default:
				msg = "�}�o�̻��ڬO���A�ڴN�O�����I";
				break;
		}
//		Msg m = new Msg(msg);			
//		Msg test = new Msg("����");
//		test.addOption("���դ�����");
//		
//		m.addOption("�U�@�ӵ���", new Msg("����")).addOption("����", test);
//		
//		Msg m = Msg.normal(msg);
//		return new MsgFlow(m);	
		
		return new MsgFlow(new Msg(msg));
	}
}
