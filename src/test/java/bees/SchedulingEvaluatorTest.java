package bees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import bees.model.Employee;
import bees.model.Problem;
import bees.model.WeekdayShift;
import bees.model.WeekendShift;
import bees.model.WeeklyScheduling;
import static org.junit.Assert.*;

public class SchedulingEvaluatorTest {
    
    @Test
    public void testUnsatisfiedRequirementCount() {
        Problem problem = TestSupport.simpleProblem();
        List<WeeklyScheduling> sched = new ArrayList<>(1);
        Map<WeekdayShift, Set<Employee>> weekdaySched = new HashMap<>();
        Map<WeekendShift, Set<Employee>> weekendSched = new HashMap<>();
        WeeklyScheduling weekSched1 = new WeeklyScheduling(weekdaySched, weekendSched);
        sched.add(weekSched1);
        sched.add(weekSched1);
        SchedulingEvaluator subject = new SchedulingEvaluator(problem);
        assertEquals(6, subject.unsatisfiedRequirementCount(sched));
    }
    
    @Test
    public void employeePreferenceQualityHandlesWeekdayShifts() {
        Problem problem = TestSupport.simpleProblem();
        List<WeeklyScheduling> sched = new ArrayList<>(1);
        Map<WeekdayShift, Set<Employee>> weekdaySched = new HashMap<>();
        weekdaySched.put(WeekdayShift.DAY, asSet(problem.employeeByName("Emp EN DE 1")));
        Map<WeekendShift, Set<Employee>> weekendSched = new HashMap<>();
        WeeklyScheduling weekSched1 = new WeeklyScheduling(weekdaySched, weekendSched);
        
        weekdaySched = new HashMap<>();
        weekdaySched.put(WeekdayShift.AFTERNOON, asSet(problem.employeeByName("Emp EN DE 1")));
        weekendSched = new HashMap<>();
        WeeklyScheduling weekSched2 = new WeeklyScheduling(weekdaySched, weekendSched);
        
        SchedulingEvaluator subject = new SchedulingEvaluator(problem);
        int actual = subject.employeePreferenceQuality(Arrays.asList(weekSched1, weekSched2));
        assertEquals(-1, actual);
    }

    private Set<Employee> asSet(Employee employeeByName) {
        HashSet<Employee> rval = new HashSet<>();
        rval.add(employeeByName);
        return rval;
    }

}
