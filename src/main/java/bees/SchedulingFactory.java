package bees;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import bees.model.Employee;
import bees.model.WeeklyRequirement;
import bees.model.WeeklyScheduling;

public class SchedulingFactory {

    public List<WeeklyScheduling> randomScheduling(Set<Employee> employees, WeeklyRequirement weekReq, int weekCount) {
        List<WeeklyScheduling> rval = new ArrayList<>(weekCount);
        return rval;
    }

}
