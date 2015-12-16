package scheduling.model;

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
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Weekday shifts:\n");
        for (WeekdayShift shift: weekdaySched.keySet()) {
            sb.append('\t').append(String.format("%-12s", shift)).append(":\t");
            weekdaySched.get(shift).forEach(e -> sb.append(e).append(", "));
            sb.append("\n");
        }
        sb.append("Weekend shifts:\n");
        for (WeekendShift shift: weekendSched.keySet()) {
            sb.append('\t').append(String.format("%-10s", shift)).append(":\t");
            weekendSched.get(shift).forEach(e -> sb.append(e).append(", "));
            sb.append("\n");
        }
        return sb.toString();
    }

    public WeekdayShift weekdayShiftOf(Employee e) {
        for (WeekdayShift candidate: weekdaySched.keySet()) {
            if (weekdaySched.get(candidate).contains(e)) {
                return candidate;
            }
        }
        return null;
    }
    
    public WeekendShift weekendShiftOf(Employee e) {
        for (WeekendShift candidate: weekendSched.keySet()) {
            if (weekendSched.get(candidate).contains(e)) {
                return candidate;
            }
        }
        return null;
    }

    
}
