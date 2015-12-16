package scheduling.quality;

public class Quality implements Comparable<Quality> {

    private final int unsatisfiedRequirementCount;

    private final int employeePreference;

    public Quality(int unsatisfiedRequirementCount, int employeePreference) {
        this.unsatisfiedRequirementCount = unsatisfiedRequirementCount;
        this.employeePreference = employeePreference;
    }

    public int unsatisfiedRequirementCount() {
        return unsatisfiedRequirementCount;
    }

    public int employeePreference() {
        return employeePreference;
    }

    @Override
    public int compareTo(Quality other) {
        if (other.unsatisfiedRequirementCount > unsatisfiedRequirementCount) {
            return -1;
        } else if (other.unsatisfiedRequirementCount < unsatisfiedRequirementCount) {
            return 1;
        }
        
        if (other.employeePreference < employeePreference) {
            return -1;
        } else if (other.employeePreference > employeePreference) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "[unsatisfiedRequirementCount=" + unsatisfiedRequirementCount + ", employeePreference="
                + employeePreference + "]";
    }
    
    

}
