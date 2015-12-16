package scheduling.quality;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import scheduling.model.Employee;
import scheduling.model.Problem;
import scheduling.model.ShiftRequirement;
import scheduling.model.Skill;
import scheduling.model.WeekdayShift;
import scheduling.model.WeekendShift;
import scheduling.model.WeeklyRequirement;

public class TestSupport {
    
    public static final Problem simpleProblem() {
        Set<Employee> employees = Problem.employees(2, Skill.LANG_EN, Skill.LANG_DE);
        Map<WeekdayShift, ShiftRequirement> weekdayReqs = new HashMap<>(WeekdayShift.values().length);
        ShiftRequirement shiftReq = new ShiftRequirement();
        shiftReq.put(Skill.LANG_EN, 3);
        weekdayReqs.put(WeekdayShift.DAY, shiftReq);
        Map<WeekendShift, ShiftRequirement> weekendReqs = new HashMap<>();
        shiftReq = new ShiftRequirement();
        return new Problem(employees, new WeeklyRequirement(weekdayReqs, weekendReqs), 2);
    }

}
