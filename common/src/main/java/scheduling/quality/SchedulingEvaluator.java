package scheduling.quality;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import scheduling.model.Employee;
import scheduling.model.Problem;
import scheduling.model.Skill;
import scheduling.model.WeekdayShift;
import scheduling.model.WeekendShift;
import scheduling.model.WeeklyScheduling;

public class SchedulingEvaluator {

    private final Problem problem;

    public SchedulingEvaluator(Problem problem) {
        this.problem = requireNonNull(problem, "problem cannot be null");
    }

    private int unsatisfiedRequirementCount(WeeklyScheduling sched) {
        int rval = 0;
        for (WeekdayShift shift : problem.weekdayReq().keySet()) {
            HashMap<Skill, Integer> shiftReqs = new HashMap<>(problem.weekdayReq().get(shift));
            Set<Employee> shiftEmployees = sched.weekdaySched().get(shift);
            if (shiftEmployees != null) {
                for (Employee e : shiftEmployees) {
                    for (Skill skill : e.skills()) {
                        Integer prevVal = shiftReqs.get(skill);
                        if (prevVal != null) {
                            shiftReqs.put(skill, prevVal - 1);
                        }
                    }
                }
            }
            for (Integer i : shiftReqs.values()) {
                if (i > 0) {
                    rval += i;
                }
            }
        }
        
        for (WeekendShift shift : problem.weekendReq().keySet()) {
            HashMap<Skill, Integer> shiftReqs = new HashMap<>(problem.weekendReq().get(shift));
            Set<Employee> shiftEmployees = sched.weekendSched().get(shift);
            if (shiftEmployees != null) {
                for (Employee e : shiftEmployees) {
                    for (Skill skill : e.skills()) {
                        Integer prevVal = shiftReqs.get(skill);
                        if (prevVal != null) {
                            shiftReqs.put(skill, prevVal - 1);
                        }
                    }
                }
            }
            for (Integer i : shiftReqs.values()) {
                if (i > 0) {
                    rval += i;
                }
            }
        }
        return rval;
    }

    public int unsatisfiedRequirementCount(List<WeeklyScheduling> sched) {
        if (sched.size() != problem.weekCount()) {
            throw new IllegalArgumentException(format("expected scheduling for %d weeks, got for %d weeks",
                    problem.weekCount(), sched.size()));
        }
        return sched.stream().mapToInt(this::unsatisfiedRequirementCount).sum();
    }

    public int employeePreference(List<WeeklyScheduling> sched) {
        int rval = 0;
        for (int i = 0; i < sched.size() - 1; ++i) {
            WeeklyScheduling currWeek = sched.get(i);
            WeeklyScheduling nextWeek = sched.get(i + 1);
            for (Employee e : problem.employees()) {
                WeekdayShift currShift = currWeek.weekdayShiftOf(e);
                WeekdayShift nextShift = nextWeek.weekdayShiftOf(e);
                if (!(currShift == null || nextShift == null)) {
                    rval -= WeekdayShift.diff(currShift, nextShift);
                }
            }
        }
        return rval;
    }

    public Quality quality(List<WeeklyScheduling> weeklyScheds) {
        return new Quality(unsatisfiedRequirementCount(weeklyScheds), employeePreference(weeklyScheds));
    }

}
