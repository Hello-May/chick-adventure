package fighting;

import GameObject.Actor;
import GameObject.Chicken;

public class SkillList {

    public interface Skill {
        public void action(Chicken chicken, Actor target);
    }

    public static class Attack implements Skill {
        @Override
        public void action(Chicken chicken, Actor target) {
//            target.setHp(target.getHp() - chicken.getAtk());
        }
    }

    public static class FireBall implements Skill {

        @Override
        public void action(Chicken chicken, Actor target) {
//            target.
//            target.setHp(target. - chicken.getAtk() * 2);
        }
    }

    public static class Curse implements Skill {

        @Override
        public void action(Chicken chicken, Actor target) {
//            target.setHp(target.getHp() - chicken.getAtk() * 2);
        }
    }

    public static class Summon implements Skill {

        @Override
        public void action(Chicken chicken, Actor target) {
//            target.setHp(target.getHp() - chicken.getAtk() * 2);
        }
    }

    public static class Ax implements Skill {
        @Override
        public void action(Chicken chicken, Actor target) {
        }
    }

    public static class Tartness implements Skill {
        @Override
        public void action(Chicken chicken, Actor target) {

        }
    }
}
