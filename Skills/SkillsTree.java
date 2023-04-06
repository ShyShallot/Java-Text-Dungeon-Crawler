public class SkillsTree {

    public static void printSkillsTree(Skill skill){
        //System.out.println(skill);
        Skill mostPrevSkill = skill;

        while(mostPrevSkill.hasPrevLevel()){
            mostPrevSkill = getSkillFromName(mostPrevSkill.previousSkillLevel());
        }
        System.out.print(String.format("%s \n", mostPrevSkill.toStringSimplified()));
        System.out.println(CusLib.colorText("|\nV", "red"));

        Skill curSkill = mostPrevSkill;
        if(curSkill.hasNextLevel()){
            curSkill = getSkillFromName(curSkill.nextSkillLevel());
        }
        System.out.print(String.format("%s \n", curSkill.toStringSimplified()));
        System.out.println(CusLib.colorText("|\nV", "red"));
        while(curSkill.hasNextLevel()){
            curSkill = getSkillFromName(curSkill.nextSkillLevel());
            System.out.print(String.format("%s \n", curSkill.toStringSimplified()));
            if(curSkill.hasNextLevel()){
                System.out.println(CusLib.colorText("|\nV", "red"));
            }
        }
        System.out.println();
    }

    public static Skill getSkillFromName(String name){
        for(int i=0;i<Skill.SkillList.size();i++){
            if(Skill.SkillList.get(i).name().toLowerCase().equals(name.toLowerCase())){
                return Skill.SkillList.get(i);
            }
        }
        return null;
    }
}
