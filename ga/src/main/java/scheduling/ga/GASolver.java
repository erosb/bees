package scheduling.ga;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import scheduling.model.Problem;
import scheduling.model.SchedulingFactory;
import scheduling.model.Solution;
import scheduling.quality.SchedulingEvaluator;

public class GASolver {
  

  public static void main(String[] args) {
    new GASolver(Problem.getDefault(), 200, 100).solve();
  }

  private void solve() {
    Population population = new Population(schedFactory, populationSize);
    while (generationCount-- > 0) {
      System.out.println(generationCount);
      List<Solution> parents = population.select(150);
      Collection<Solution> offsprings = new ArrayList<Solution>(parents.size());
      for (int i = 0; i < parents.size() - 1; i += 2) {
        Solution parent1 = parents.get(i);
        Solution parent2 = parents.get(i+1);
        Solution offspring1 = mutate(schedFactory.cross(parent1, parent2));
        Solution offspring2 = mutate(schedFactory.cross(parent2, parent1));
        offsprings.add(offspring1);
        offsprings.add(offspring2);
      }
    }
  }
  
  private Solution mutate(Solution original) {
    if (Math.random() < 0.05) {
      return schedFactory.randomSwap(original);
    }
    return original;
  }

  private Problem problem;
  
  private int populationSize;
  
  private int generationCount;
  
  private SchedulingFactory schedFactory;
  
  private SchedulingEvaluator evaluator;

  public GASolver(Problem problem, int populationSize, int generationCount) {
    this.problem = problem;
    this.populationSize = populationSize;
    this.generationCount = generationCount;
    evaluator = new SchedulingEvaluator(problem);
    schedFactory = new SchedulingFactory(problem, evaluator);
  }

}
