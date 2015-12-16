package scheduling.model;

import static java.util.Objects.requireNonNull;

import java.util.Map;

public class WeeklyRequirement {
    
    private final Map<WeekdayShift, ShiftRequirement> weekdayReq;

    private final Map<WeekendShift, ShiftRequirement> weekendReq;

    public WeeklyRequirement(Map<WeekdayShift, ShiftRequirement> weekdayReq, Map<WeekendShift, ShiftRequirement> weekendReq) {
        this.weekdayReq = requireNonNull(weekdayReq, "weekdayReq cannot be null");
        this.weekendReq = requireNonNull(weekendReq, "weekendReq cannot be null");
    }

    public Map<WeekdayShift, ShiftRequirement> weekdayReq() {
        return weekdayReq;
    }

    public Map<WeekendShift, ShiftRequirement> weekendReq() {
        return weekendReq;
    }
    
}
