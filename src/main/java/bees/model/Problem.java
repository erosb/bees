package bees.model;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Problem {
    
    public static Problem getDefault() {
        Set<Employee> employees = allEmployees();
        Map<WeekdayShift, ShiftRequirement> weekdayReqs = new HashMap<>(WeekdayShift.values().length);
        weekdayReqs.put(WeekdayShift.MORNING, morningReq());
        weekdayReqs.put(WeekdayShift.DAY, dayReq());
        weekdayReqs.put(WeekdayShift.NIGHT, nightReq());
        weekdayReqs.put(WeekdayShift.AFTERNOON, afternoonReq());
        Map<WeekendShift, ShiftRequirement> weekendReqs = new HashMap<>(WeekendShift.values().length);
        weekendReqs.put(WeekendShift.DAY, weekendReqs());
        weekendReqs.put(WeekendShift.NIGHT  , weekendReqs());
        WeeklyRequirement weeklyReq = new WeeklyRequirement(weekdayReqs, weekendReqs);
        return new Problem(employees, weeklyReq, 2);
    }
    
    private static Set<Employee> employees(int count, Skill... skills) {
        Set<Skill> skillSet = new HashSet<>(Arrays.asList(skills));
        String namePrefix = "Emp " + Arrays.stream(skills)
                .map(Skill::toString)
                .collect(Collectors.joining(" ")) + " ";
        return IntStream.range(0, count)
                .mapToObj(i -> namePrefix + i)
                .map(name -> new Employee(name, skillSet))
                .collect(Collectors.toSet());
    }

    private static Set<Employee> allEmployees() {
        Set<Employee> rval = new HashSet<>(30);
        rval.addAll(employees(3, Skill.LANG_EN, Skill.LANG_PL));
        rval.addAll(employees(3, Skill.LANG_EN, Skill.LANG_ES));
        rval.addAll(employees(7, Skill.LANG_EN, Skill.LANG_DE));
        rval.addAll(employees(7, Skill.LANG_EN, Skill.LANG_FR));
        rval.addAll(employees(7, Skill.LANG_EN, Skill.LANG_ITA));
        return rval;
    }

    private static ShiftRequirement weekendReqs() {
        ShiftRequirement req = new ShiftRequirement();
        req.put(Skill.LANG_EN, 3);
        req.put(Skill.LANG_DE, 1);
        req.put(Skill.LANG_FR, 1);
        req.put(Skill.LANG_ITA, 1);
        return req;
    }

    private static ShiftRequirement afternoonReq() {
        ShiftRequirement req = new ShiftRequirement();
        req.put(Skill.LANG_EN, 3);
        req.put(Skill.LANG_DE, 1);
        req.put(Skill.LANG_FR, 1);
        req.put(Skill.LANG_ITA, 1);
        return req;
    }

    private static ShiftRequirement dayReq() {
        ShiftRequirement req = new ShiftRequirement();
        req.put(Skill.LANG_EN, 9);
        req.put(Skill.LANG_DE, 3);
        req.put(Skill.LANG_FR, 3);
        req.put(Skill.LANG_ITA, 3);
        req.put(Skill.LANG_PL, 1);
        req.put(Skill.LANG_ES, 1);
        return req;
    }

    private static ShiftRequirement nightReq() {
        ShiftRequirement req = new ShiftRequirement();
        req.put(Skill.LANG_EN, 4);
        req.put(Skill.LANG_DE, 2);
        req.put(Skill.LANG_FR, 1);
        req.put(Skill.LANG_ITA, 1);
        return req;
    }

    private static ShiftRequirement morningReq() {
        ShiftRequirement morningReq = new ShiftRequirement();
        morningReq.put(Skill.LANG_EN, 4);
        return morningReq;
    }

    private final Set<Employee> employees;

    private final WeeklyRequirement weekReq;

    private final int weekCount;

    public Problem(Set<Employee> employees, WeeklyRequirement weekReq, int weekCount) {
        this.employees = requireNonNull(employees, "employees cannot be null");
        this.weekReq = requireNonNull(weekReq, "weekReq cannot be null");
        if (weekCount < 0) {
            throw new IllegalArgumentException("weekCount must be non-negative");
        }
        this.weekCount = weekCount;
    }

    public Set<Employee> employees() {
        return employees;
    }

    public WeeklyRequirement weekReq() {
        return weekReq;
    }

    public int weekCount() {
        return weekCount;
    }
    
    public Map<WeekdayShift, ShiftRequirement> weekdayReq() {
        return weekReq.weekdayReq();
    }
    
    public Map<WeekendShift, ShiftRequirement> weekendReq() {
        return weekReq.weekendReq();
    }

}
