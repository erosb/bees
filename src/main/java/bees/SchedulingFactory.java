package bees;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bees.model.Employee;
import bees.model.Problem;
import bees.model.ShiftRequirement;
import bees.model.WeekdayShift;
import bees.model.WeekendShift;
import bees.model.WeeklyScheduling;

public class SchedulingFactory {

    private final Problem problem;

    public SchedulingFactory(Problem problem) {
        this.problem = problem;
    }

    private WeeklyScheduling randomWeeklyScheduling() {
        LinkedList<Employee> employees = new LinkedList<>(problem.employees());
        Collections.shuffle(employees);
        Map<WeekdayShift, Set<Employee>> weekdaySched = new HashMap<>();
        for (WeekdayShift shift : problem.weekdayReq().keySet()) {
            ShiftRequirement shiftReqs = problem.weekdayReq().get(shift);
            Set<Employee> employeesInShift = new HashSet<>();
            for (int i = 0; i < shiftReqs.maxRequirementCount(); ++i) {
                Employee e = employees.poll();
                if (e == null) {
                    throw new IllegalArgumentException("not enough employees");
                }
                employeesInShift.add(e);
            }
            weekdaySched.put(shift, employeesInShift);
        }
        Map<WeekendShift, Set<Employee>> weekendSched = new HashMap<>();
        for (WeekendShift shift: problem.weekendReq().keySet()) {
            ShiftRequirement shiftReqs = problem.weekendReq().get(shift);
            Set<Employee> employeesInShift = new HashSet<>();
            for (int i = 0; i < shiftReqs.maxRequirementCount(); ++i) {
                Employee e = employees.poll();
                if (e == null) {
                    throw new IllegalArgumentException("not enough employees");
                }
                employeesInShift.add(e);
            }
            weekendSched.put(shift, employeesInShift);
        }
        return new WeeklyScheduling(weekdaySched, weekendSched);
    }

    public List<WeeklyScheduling> randomScheduling(int weekCount) {
        return IntStream.range(0, weekCount)
                .mapToObj(i -> randomWeeklyScheduling())
                .collect(Collectors.toList());
    }

}
