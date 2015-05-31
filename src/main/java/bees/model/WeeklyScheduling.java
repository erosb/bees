package bees.model;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.Set;

public class WeeklyScheduling {

    private final Map<WeekdayShift, Set<Employee>> weekdaySched;

    private final Map<WeekendShift, Set<Employee>> weekendSched;

    public WeeklyScheduling(Map<WeekdayShift, Set<Employee>> weekdaySched, Map<WeekendShift, Set<Employee>> weekendSched) {
        this.weekdaySched = requireNonNull(weekdaySched, "weekdaySched cannot be null");
        this.weekendSched = requireNonNull(weekendSched, "weekendSched cannot be null");
    }

    public Map<WeekdayShift, Set<Employee>> weekdaySched() {
        return weekdaySched;
    }

    public Map<WeekendShift, Set<Employee>> weekendSched() {
        return weekendSched;
    }

    
}
