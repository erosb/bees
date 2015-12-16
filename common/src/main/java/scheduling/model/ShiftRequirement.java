package scheduling.model;

import java.util.HashMap;

public class ShiftRequirement extends HashMap<Skill, Integer> {
    private static final long serialVersionUID = -1464435329016162158L;

    public int maxRequirementCount() {
        return this.values().stream()
                .mapToInt(Integer::intValue)
                .max().orElse(0);
    }
}
