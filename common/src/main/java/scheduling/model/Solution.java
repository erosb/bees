package scheduling.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import scheduling.quality.Quality;


public class Solution extends ArrayList<WeeklyScheduling> implements Comparable<Solution> {
    private static final long serialVersionUID = -8233015353134023195L;

    private final Quality quality;

    public Solution(List<WeeklyScheduling> weeklyScheds, Quality quality) {
        this.quality = quality;
        addAll(weeklyScheds);
    }

    public Quality quality() {
        return quality;
    }

    @Override
    public int compareTo(Solution o) {
        return quality.compareTo(o.quality);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size(); ++i) {
            sb.append("=== Week " + (i + 1) + " ===\n");
            sb.append(get(i).toString());
        }
        return sb.toString();
    }

    public List<Employee> unscheduledEmployeesForWeekdays(int weekIdx, Problem problem) {
        List<Employee> unscheduledEmployees = new ArrayList<>(problem.employees());
        for (Set<Employee> emps : get(weekIdx).weekdaySched().values()) {
            unscheduledEmployees.removeAll(emps);
        }
        return unscheduledEmployees;
    }

}
