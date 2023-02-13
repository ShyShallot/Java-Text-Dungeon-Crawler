import java.util.ArrayList;
import java.util.HashMap;

public class SkillsTree {

    public static ArrayList<Skill> skills = new ArrayList<>();

    public SkillsTree(){
        skills.add(new Speed1());
        skills.add(new Weather1());
        skills.add(new Weather2());
        skills.add(new Weather3());
    }

    public void printSkillsTree(Skill skill){
        //System.out.println(skill);
        Skill mostPrevSkill = null;
        if(skill.hasPrevLevel()){
            mostPrevSkill = getSkillFromName(skill.previousSkillLevel());
        } else {
            mostPrevSkill = skill;
        }
        Skill highestSkill = null;
        if(skill.hasNextLevel()){
            highestSkill = getSkillFromName(skill.nextSkillLevel());
        } else {
            highestSkill = skill;
        }
        while(mostPrevSkill != null){
            if(!mostPrevSkill.hasPrevLevel()){
                break;
            }
            mostPrevSkill = getSkillFromName(mostPrevSkill.previousSkillLevel());
            System.out.println(mostPrevSkill);
        }
        while(highestSkill != null){
            if(!highestSkill.hasNextLevel()){
                break;
            }
            highestSkill = getSkillFromName(highestSkill.nextSkillLevel());
        }

        System.out.print(String.format("%s \n", mostPrevSkill.toStringSimplified()));
        System.out.println(CusLib.colorText("|\nV", "red"));
        System.out.print(String.format("%s \n",skill.toStringSimplified()));
        System.out.println(CusLib.colorText("|\nV", "red"));
        if(highestSkill != null){
            System.out.print(String.format("%s",highestSkill.toStringSimplified()));
        }
        System.out.println();
    }

    public static Skill getSkillFromName(String name){
        for(int i=0;i<skills.size();i++){
            if(skills.get(i).name().toLowerCase().equals(name.toLowerCase())){
                return skills.get(i);
            }
        }
        return null;
    }
}
