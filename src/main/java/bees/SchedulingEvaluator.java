package bees;

import bees.model.Problem;
import static java.util.Objects.requireNonNull;
public class SchedulingEvaluator {
    
    private Problem problem;

    public SchedulingEvaluator(Problem problem) {
        this.problem = requireNonNull(problem, "problem cannot be null");
    }
    
    

}
