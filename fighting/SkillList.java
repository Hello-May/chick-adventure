/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fighting;

import actor.Actor;
import actor.Chicken;

/**
 *
 * @author User
 */
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

    public static class FireBall implements Skill {//法師-火球

        @Override
        public void action(Chicken chicken, Actor target) {
//            target.
//            target.setHp(target. - chicken.getAtk() * 2);
        }
    }

    public static class Curse implements Skill {//詛咒

        @Override
        public void action(Chicken chicken, Actor target) {
//            target.setHp(target.getHp() - chicken.getAtk() * 2);
        }
    }

    public static class Summon implements Skill {//召喚

        @Override
        public void action(Chicken chicken, Actor target) {
//            target.setHp(target.getHp() - chicken.getAtk() * 2);
        }
    }

    public static class Ax implements Skill {//戰士-斧頭

        @Override
        public void action(Chicken chicken, Actor target) {

        }
    }

    public static class Tartness implements Skill {//盜賊-鋒

        @Override
        public void action(Chicken chicken, Actor target) {

        }
    }
}
