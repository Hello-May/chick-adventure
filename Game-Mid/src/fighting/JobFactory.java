/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fighting;

import fighting.SkillList.*;

/**
 *
 * @author User
 */
public class JobFactory { //工廠模式<固定製作流程>

    public static Job gen(String name) {
        Job job = null;
        switch (name) {
            case "法師":
                job = new Job("法師");
                job.setSkills(new FireBall());
                job.setSkills(new Curse());
                job.setSkills(new Summon());
                break;
            case "戰士":
                job = new Job("戰士");
                job.setSkills(new Ax());
                job.setSkills(new Curse());
                job.setSkills(new Summon());
                break;
            case "盜賊":
                job = new Job("盜賊");
                job.setSkills(new Tartness());
                job.setSkills(new Curse());
                job.setSkills(new Summon());
                break;
        }
        return job;
    }
}
