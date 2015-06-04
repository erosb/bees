package bees.solver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bees.SchedulingEvaluator;
import bees.SchedulingFactory;
import bees.model.Problem;
import bees.model.Solution;
import bees.model.WeeklyScheduling;

public class Solver {
    
    private final Problem problem;
    
    private final int explorerBeeCount;
    
    private final SchedulingFactory schedFactory;
    
    private final SchedulingEvaluator evaluator;
    
    private final ExecutorService executor;
    
    private final int iterationCount;
    
    public Solver(Problem p, int explorerBeeCount, int iterationCount) {
        this.problem = p;
        this.explorerBeeCount = explorerBeeCount;
        this.iterationCount = iterationCount;
        this.evaluator = new SchedulingEvaluator(p);
        this.schedFactory = new SchedulingFactory(p, evaluator);
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public static void main(String[] args) {
        Problem p = Problem.getDefault();
        new Solver(p, 8, 500).solve();
    }

    public void solve() {
        List<Solution> explorerBees = IntStream.range(0, explorerBeeCount)
                .mapToObj(i -> schedFactory.randomScheduling())
                .collect(Collectors.toList());
        for (int i = 0; i < iterationCount; ++i) {
            explorerBees = runSwarms(explorerBees);
            System.out.println(i + "th iteration: " + Collections.max(explorerBees).quality());
        }
        executor.shutdown();
        explorerBees.stream().forEach(bee -> {
            System.out.println("\t"+bee.quality());
        });
        Solution tst = explorerBees.get(0);
    }

    private List<Solution> runSwarms(List<Solution> explorerBees) {
        Collections.sort(explorerBees);
        List<Solution> result = new ArrayList<>(explorerBees.size());
        for (Solution explorer: explorerBees) {
            Solution best = new BeeSwarm(explorer, 10, 3, schedFactory, executor).run();
            result.add(best);
        }
        return result;
    }

}
