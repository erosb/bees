package bees.model;

public enum Skill {
    
    LANG_EN, LANG_DE, LANG_ITA, LANG_FR, LANG_PL, LANG_ES;

    @Override
    public String toString() {
        String fullStr = super.toString();
        return fullStr.substring(fullStr.indexOf('_') + 1);
    }
    
    

}
