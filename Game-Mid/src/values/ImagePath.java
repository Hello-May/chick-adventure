/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package values;

import java.awt.Graphics;

/**
 *
 * @author user1
 */
public class ImagePath {

    // Airplane
    public static class AirPlane {

        public static final String FOLDER_AIRPLANE = "/airplane";
        public static final String AIRCRAFT = FOLDER_AIRPLANE + "/airplane1.png";
        public static final String BULLET_STATE0 = FOLDER_AIRPLANE + "/boom.png";
        public static final String BULLET_STATE1 = FOLDER_AIRPLANE + "/boom2.png";
    }

    // Character
    public static class Character {

        public static final String FOLDER_CHARACTER = "/Characters";

        public static class Actor {

            public static final String A1 = FOLDER_CHARACTER + "/Actor1.png";
            public static final String A2 = FOLDER_CHARACTER + "/Actor2.png";
            public static final String CHICKEN = FOLDER_CHARACTER + "/chicken.png";
        }
    }

    //上下左右圖片
    public static class Direction {

//        public static final String BATTLE = "/dir.jpg";  //可刪掉
        public static final String WHITE = "/1.png";
        public static final String GREEN = "/2.png";
        public static final String RED = "/3.png";

    }

    //場景圖
    public static class Scene {

        public static final String SWORD = "/sword.png";
        public static final String GRASSLAND = "/grassland.jpg";
    }

    //戰鬥畫面會有的東西
    public static class Battle {

        public static final String FOLDER_BATTLE = "/Battle";

        public static class battle { //選單圖片

            public static final String ATTACK = FOLDER_BATTLE + "/attack.png";
            public static final String SKILL = FOLDER_BATTLE + "/skill.png";
            public static final String CAST = FOLDER_BATTLE + "/cast.png";
            public static final String SOS = FOLDER_BATTLE + "/sos.png";
            public static final String TREATMENT = FOLDER_BATTLE + "/treatment.png";

        }

    }

    //技能
    public static class Skills {

        public static final String FOLDER_SKILL = "/Skill";

        public static class Skill { //技能圖片

            public static final String ATTACK = FOLDER_SKILL + "/attack.png"; //普通攻擊效果
            public static final String FIREBALL = FOLDER_SKILL + "/fireball.jpg";
            public static final String CURSE = FOLDER_SKILL + "/curse.jpg"; //
            public static final String CALL = FOLDER_SKILL + "/call.jpg";
        }

        public static class Mage { //法師技能效果

            public static final String FIRE = FOLDER_SKILL + "/fire.png";
        }
    }

    public static class Counter {

        public static final String TIMER = "/timer.jpg";
    }

//    public static class Frames { // 血條法條框
//
//        public static final String FOLDER_FRAMES = "/frame";
//
//        public static class Frame {
//            public static final String FRAME = FOLDER_FRAMES + "/frame.png";
//            public static final String HP = FOLDER_FRAMES + "/hp.png";
//            public static final String MP = FOLDER_FRAMES + "/mp.png";
//        }
//    }
}
