package Values;

public class ImagePath { // 如果有餘力，找對話頭像 //還有商店、教堂、倉庫等平面圖 //None.png再調整
	public static class Character {
		public static final String FOLDER_CHARACTER = "/Characters";

		public static class Facial {
			public static final String VILLAGE_HEAD = FOLDER_CHARACTER + "/villageHead.png";
			public static final String NONE = FOLDER_CHARACTER + "/None.png";
			public static final String MONSTER = FOLDER_CHARACTER + "/Monster_Village_Head.png";
		}

		public static class Actor {
			public static final String A1 = FOLDER_CHARACTER + "/Actor1.png";
			public static final String A2 = FOLDER_CHARACTER + "/Actor2.png";
			public static final String A3 = FOLDER_CHARACTER + "/Actor3.png";
			public static final String A4 = FOLDER_CHARACTER + "/Actor4.png";
			public static final String A5 = FOLDER_CHARACTER + "/Actor5.png";
			public static final String CHICKEN = FOLDER_CHARACTER + "/chicken.png";
			public static final String FIRE = FOLDER_CHARACTER + "/!Flame.png";
		}

		public static class Damage {
			public static final String D1 = FOLDER_CHARACTER + "/Damage1.png";
		}

		public static class Monster {
			public static final String M1 = FOLDER_CHARACTER + "/Monster1.png";
			public static final String M2 = FOLDER_CHARACTER + "/Monster2.png";
			public static final String M3 = FOLDER_CHARACTER + "/Monster3.png";
			public static final String BM = FOLDER_CHARACTER + "/shadow01.png";
			public static final String WOLF = FOLDER_CHARACTER + "/Wolf.png";
		}

		public static class People {
			public static final String P1 = FOLDER_CHARACTER + "/People1.png";
			public static final String P2 = FOLDER_CHARACTER + "/People2.png";
			public static final String P3 = FOLDER_CHARACTER + "/People3.png";
			public static final String P4 = FOLDER_CHARACTER + "/People4.png";
			public static final String P5 = FOLDER_CHARACTER + "/People5.png";
		}

		public static class Crazy {
			public static final String C1 = FOLDER_CHARACTER + "/Insane1.png";
			public static final String C2 = FOLDER_CHARACTER + "/Insane2.png";
			public static final String O1 = FOLDER_CHARACTER + "/!Other2.png";
		}

		public static class Animal {
			public static final String A1 = FOLDER_CHARACTER + "/Animal.png";
		}
	}

	// 上下左右圖片
	public static class Direction {
		public static final String FOLDER_SCENE = "/BattleScene";

		public static final String WHITE = FOLDER_SCENE + "/1.png";
		public static final String GREEN = FOLDER_SCENE + "/2.png";
		public static final String RED = FOLDER_SCENE + "/3.png";
	}

	public static class Scene {
		public static final String FOLDER_SCENE = "/Scene";
		public static final String MENU = FOLDER_SCENE + "/Menu.jpg";
		public static final String LOAD = FOLDER_SCENE + "/load.jpg";
		public static final String INSTRUCTIONS = FOLDER_SCENE + "/Instructions.jpg";
		public static final String GAME_OVER = FOLDER_SCENE + "/GameOver.jpg";
		public static final String SAVE_OK = FOLDER_SCENE + "/saveok.png";
		public static final String END = FOLDER_SCENE + "/END.jpg";
		public static final String EQUIPMENT = FOLDER_SCENE + "/EQUIPMENT.JPG";
		public static final String CHURCH = FOLDER_SCENE + "/CHURCH.jpg";
		public static final String MOUNTAIN = FOLDER_SCENE + "/MOUNTAIN.JPG";
		public static final String CENTER = FOLDER_SCENE + "/CENTER.jpg";
		public static final String STORE = FOLDER_SCENE + "/STORE.jpg";
		public static final String HOME = FOLDER_SCENE + "/HOME.jpg";
		public static final String HINT = FOLDER_SCENE + "/hint.png";
		public static final String PROP = FOLDER_SCENE + "/PROP.jpg";
	}

	// 場景圖
	public static class BattleScene {
		public static final String FOLDER_BATTLESCENE = "/BattleScene";

		public static final String SWORD = FOLDER_BATTLESCENE + "/sword.png";
		public static final String GRASSLAND = FOLDER_BATTLESCENE + "/grassland.jpg";
	}

	// 戰鬥畫面會有的東西
	public static class Battles {
		public static final String FOLDER_BATTLE = "/Battle";

		public static class Battle { // 選單圖片
			public static final String ATTACK = FOLDER_BATTLE + "/attack.png";
			public static final String SKILL = FOLDER_BATTLE + "/skill.png";
			public static final String SOS = FOLDER_BATTLE + "/sos.png";
		}
	}

	// 表情符號
	public static class Emoticons {
		public static final String FOLDER_BATTLE = "/Emoticons";

		public static final String NORMAL = FOLDER_BATTLE + "/normal.png";
		public static final String HAPPY = FOLDER_BATTLE + "/happy.png";
		public static final String CRY = FOLDER_BATTLE + "/cry.png";
		public static final String ANGRY = FOLDER_BATTLE + "/angry.png";
		public static final String CONFUSION = FOLDER_BATTLE + "/confusion.png";
		public static final String LIGHT = FOLDER_BATTLE + "/light.png";
		public static final String MUSIC = FOLDER_BATTLE + "/music.png";
		public static final String QUESTION = FOLDER_BATTLE + "/question.png";
		public static final String SURPRISED = FOLDER_BATTLE + "/surprised.png";
	}

	// 技能
	public static class Skills {
		public static final String FOLDER_SKILL = "/Skill";

		public static class Skill { // 技能圖片
			public static final String FIREBALL = FOLDER_SKILL + "/fireball.jpg";
			public static final String CURSE = FOLDER_SKILL + "/curse.jpg"; //
			public static final String CALL = FOLDER_SKILL + "/call.jpg";
		}

		public static class SkillResult { // 技能效果
			public static final String ATTACK = FOLDER_SKILL + "/attack.png"; // 攻擊效果
			public static final String FIREBALL = FOLDER_SKILL + "/fire.png";// 火球
			public static final String CURSE = FOLDER_SKILL + "/curse.png";// 詛咒
			public static final String SUMMON = FOLDER_SKILL + "/summon.png";// 召喚
			public static final String CRITICAL = FOLDER_SKILL + "/critical.png";// //敵人技能效果
			public static final String ENEMY_SKILL = FOLDER_SKILL + "/enemyskill.png";// 敵人技能效果
		}
	}

	public static class Tileset {
		public static final String FOLDER_TILESET = "/Tilesets";

		public static class Outside {
			public static final String O1 = FOLDER_TILESET + "/Outside_A1.png";
			public static final String O2 = FOLDER_TILESET + "/Outside_A2.png";
			public static final String O3 = FOLDER_TILESET + "/Outside_A3.png";
			public static final String O4 = FOLDER_TILESET + "/Outside_A4.png";
			public static final String O5 = FOLDER_TILESET + "/Outside_A5.png";
			public static final String OB = FOLDER_TILESET + "/Outside_B.png";
			public static final String OC = FOLDER_TILESET + "/Outside_C.png";
			public static final String FLOWER = FOLDER_TILESET + "/FLOWER.png";
		}

		public static class Dungeon {
			public static final String D1 = FOLDER_TILESET + "/Dungeon_A1.png";
			public static final String D2 = FOLDER_TILESET + "/Dungeon_A2.png";
			public static final String D4 = FOLDER_TILESET + "/Dungeon_A4.png";
			public static final String DB = FOLDER_TILESET + "/Dungeon_B.png";
			public static final String DC = FOLDER_TILESET + "/Dungeon_C.png";
		}

		public static class World {
			public static final String W1 = FOLDER_TILESET + "/World_A1.png";
		}

		public static class Inside {
			public static final String I1 = FOLDER_TILESET + "/Inside_A1.png";
			public static final String I2 = FOLDER_TILESET + "/Inside_A2.png";
			public static final String I3 = FOLDER_TILESET + "/Inside_A3.png";
			public static final String I4 = FOLDER_TILESET + "/Inside_A4.png";
			public static final String IB = FOLDER_TILESET + "/Inside_B.png";
			public static final String IC = FOLDER_TILESET + "/Inside_C.png";
		}

		public static class Other {
			public static final String XP = FOLDER_TILESET + "/RPGM_XP_Tiles.png";
			public static final String P1 = FOLDER_TILESET + "/Park.png";
		}
	}

	public static class Bird {
		public static final String FOLDER_BIRD = "/Bird";

		public static class Facial {
			public static final String B0 = FOLDER_BIRD + "/harp10.png";
		}

		public static class Body {
			public static final String B1 = FOLDER_BIRD + "/grif04b.png";
			public static final String B2 = FOLDER_BIRD + "/grif08b.png";
			public static final String B3 = FOLDER_BIRD + "/grif01b.png";
		}

	}

	public static class Animations {
		public static final String FOLDER_ANIMATIONS = "/Animations";

	}

}