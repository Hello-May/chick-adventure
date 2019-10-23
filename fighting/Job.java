/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fighting;

import fighting.SkillList.Skill;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Job {

    private String jobName; //法師,戰士,盜賊
    private ArrayList<Skill> skills;

    public Job(String jobName) {
        this.jobName = jobName;
        skills = new ArrayList<>();
    }

    public void setSkills(Skill skill) {
        skills.add(skill);
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }
}
