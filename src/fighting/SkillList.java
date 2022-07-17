package fighting;

import GameObject.BattleActor;

public class SkillList {

	public interface Skill {
		public void action(BattleActor actor, BattleActor target);
	}

	public static class Attack implements Skill {
		@Override
		public void action(BattleActor actor, BattleActor target) {
			target.getStatus().setAttackHP(-(actor.getStatus().getAttack()));
//   System.out.println("ActorHP:" + actor.getStatus().getCurrentHP());
//   System.out.println("EnemyHP:" + target.getStatus().getCurrentHP());
		}
	}

	public static class FireBall implements Skill {

		@Override
		public void action(BattleActor actor, BattleActor target) {
			target.getStatus().setAttackHP(-(2 * actor.getStatus().getAttack()));
			actor.getStatus().setSkillMP(-(1 * actor.getStatus().getAttack()));
		}
	}

	public static class Curse implements Skill {

		@Override
		public void action(BattleActor actor, BattleActor target) {
			target.getStatus().setAttackHP(-(3 * actor.getStatus().getAttack()));
			actor.getStatus().setSkillMP(-(2 * actor.getStatus().getAttack()));

		}
	}

	public static class Summon implements Skill {

		@Override
		public void action(BattleActor actor, BattleActor target) {
			target.getStatus().setAttackHP(-(4 * actor.getStatus().getAttack()));
			actor.getStatus().setSkillMP(-(3 * actor.getStatus().getAttack()));
		}
	}
}