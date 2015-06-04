package bees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bees.model.Employee;
import bees.model.Problem;
import bees.model.ShiftRequirement;
import bees.model.Solution;
import bees.model.WeekdayShift;
import bees.model.WeekendShift;
import bees.model.WeeklyScheduling;

public class SchedulingFactory {

    private final Problem problem;

    private final SchedulingEvaluator evaluator;

    private final Random random = new Random();
    
    private final List<Employee> employeesByOrder;

    public SchedulingFactory(Problem problem, SchedulingEvaluator evaluator) {
        this.problem = problem;
        this.evaluator = evaluator;
        this.employeesByOrder = new ArrayList<>(problem.employees());
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
        for (WeekendShift shift : problem.weekendReq().keySet()) {
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

    private List<WeeklyScheduling> randomScheduling(int weekCount) {
        return IntStream.range(0, weekCount)
                .mapToObj(i -> randomWeeklyScheduling())
                .collect(Collectors.toList());
    }

    public Solution create(List<WeeklyScheduling> weeklyScheds) {
        return new Solution(weeklyScheds, evaluator.quality(weeklyScheds));
    }

    public Solution randomScheduling() {
        List<WeeklyScheduling> weeklyScheds = randomScheduling(problem.weekCount());
        return create(weeklyScheds);
    }

    public Solution randomSwap(Solution original) {
        int weekIdx = random.nextInt(original.size());
        List<WeeklyScheduling> newSched = new ArrayList<>(original.size());
        List<Employee> unscheduledEmployees;
        double rand = Math.random();
        if (rand < 0.5) {
            for (int i = 0; i < original.size(); ++i) {
                WeeklyScheduling origWeeklySched = original.get(i);
                if (i == weekIdx) {
                newSched.add(new WeeklyScheduling(origWeeklySched.weekdaySched(), randomizeWeekendSched(origWeeklySched)));
                } else {
                    newSched.add(origWeeklySched);
                }
            }
        } else if (rand < 0.75 && !(unscheduledEmployees = original.unscheduledEmployeesForWeekdays(weekIdx, problem)).isEmpty()) {
            Employee replacement = unscheduledEmployees.get(random.nextInt(unscheduledEmployees.size()));
            WeeklyScheduling weeklySched = original.get(weekIdx);
            WeekdayShift[] allShifts = WeekdayShift.values();
            WeekdayShift changedShift = allShifts[random.nextInt(allShifts.length)];
            for (WeeklyScheduling origWeekSched: original) {
                if (origWeekSched == weeklySched) {
                    List<Employee> newEmps = new ArrayList<>(weeklySched.weekdaySched().get(changedShift));
                    newEmps.set(random.nextInt(newEmps.size() - 1), replacement);
                    Map<WeekdayShift, Set<Employee>> weekdaySched = new HashMap<>(origWeekSched.weekdaySched());
                    weekdaySched.put(changedShift, new HashSet<>(newEmps));
                    newSched.add(new WeeklyScheduling(weekdaySched, origWeekSched.weekendSched()));
                } else {
                    newSched.add(origWeekSched);
                }
            }
        } else {
            performWeekdayShiftSwap(original, weekIdx, newSched);
        }
        return create(newSched);
    }

    private void performWeekdayShiftSwap(Solution original, int weekIdx, List<WeeklyScheduling> newSched) {
        WeeklyScheduling sched = original.get(weekIdx);
        Map<WeekdayShift, Set<Employee>> origWeekdaySched = sched.weekdaySched();
        WeekdayShift[] allWeekdayShifts = WeekdayShift.values();
        WeekdayShift fromShift;
        WeekdayShift toShift;
        do {
            fromShift = allWeekdayShifts[random.nextInt(allWeekdayShifts.length - 1)];
            toShift = allWeekdayShifts[random.nextInt(allWeekdayShifts.length - 1)];
        } while (fromShift == toShift);
        List<Employee> fromShiftEmployees = new ArrayList<>(origWeekdaySched.get(fromShift));
        List<Employee> toShiftEmployees = new ArrayList<>(origWeekdaySched.get(toShift));
        int fromEmployeeIdx = random.nextInt(fromShiftEmployees.size());
        int toEmployeeIdx = random.nextInt(toShiftEmployees.size());
        Employee tmp = fromShiftEmployees.get(fromEmployeeIdx);
        fromShiftEmployees.set(fromEmployeeIdx, toShiftEmployees.get(toEmployeeIdx));
        toShiftEmployees.set(toEmployeeIdx, tmp);
        for (int i = 0; i < original.size(); ++i) {
            WeeklyScheduling origWeeklySched = original.get(i);
            if (i == weekIdx) {
                Map<WeekdayShift, Set<Employee>> newWeekdaySched = new HashMap<>();
                for (WeekdayShift shift : allWeekdayShifts) {
                    if (shift == fromShift) {
                        newWeekdaySched.put(shift, new HashSet<>(fromShiftEmployees));
                    } else if (shift == toShift) {
                        newWeekdaySched.put(shift, new HashSet<>(toShiftEmployees));
                    } else {
                        newWeekdaySched.put(shift, origWeeklySched.weekdaySched().get(shift));
                    }
                }
                WeeklyScheduling newWeeklySched = new WeeklyScheduling(newWeekdaySched,
                        origWeeklySched.weekendSched());
                newSched.add(newWeeklySched);
            } else {
                newSched.add(origWeeklySched);
            }
        }
    }

    private Map<WeekendShift, Set<Employee>> randomizeWeekendSched(WeeklyScheduling weeklySched) {
        if (Math.random() < 0.5) {
            Map<WeekendShift, Set<Employee>> newWeekendSched = new HashMap<>(weeklySched.weekendSched());
            WeekendShift[] allWeekendShifts = WeekendShift.values();
            WeekendShift shiftToBeChanged = allWeekendShifts[random.nextInt(allWeekendShifts.length)];
            Employee replacement;
            do {
                replacement = employeesByOrder.get(random.nextInt(employeesByOrder.size()));
            } while(weeklySched.weekendShiftOf(replacement) != null);
            List<Employee> newEmplList = new ArrayList<>(newWeekendSched.get(shiftToBeChanged));
            newEmplList.set(random.nextInt(newEmplList.size()), replacement);
            newWeekendSched.put(shiftToBeChanged, new HashSet<>(newEmplList));
            return newWeekendSched;
        }
        return weeklySched.weekendSched();
    }

}
