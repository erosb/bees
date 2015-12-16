package scheduling.model;
import static org.junit.Assert.*;

import org.junit.Test;

import scheduling.model.Skill;
public class SkillTest {
    
    @Test
    public void testToString() {
        assertEquals("EN", Skill.LANG_EN.toString());
        assertEquals("ITA", Skill.LANG_ITA.toString());
    }

}
