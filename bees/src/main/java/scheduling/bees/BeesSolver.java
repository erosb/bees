package scheduling.bees;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import scheduling.model.Problem;
import scheduling.model.SchedulingFactory;
import scheduling.model.Solution;
import scheduling.model.WeeklyScheduling;
import scheduling.quality.SchedulingEvaluator;

public class BeesSolver {
    
    private static final double KEPT_BEE_RATIO = 0.65;

    private final Problem problem;
    
    private final int explorerBeeCount;
    
    private final SchedulingFactory schedFactory;
    
    private final SchedulingEvaluator evaluator;
    
    private final ExecutorService executor;
    
    private final int iterationCount;
    
    public BeesSolver(Problem p, int explorerBeeCount, int iterationCount) {
        this.problem = p;
        this.explorerBeeCount = explorerBeeCount;
        this.iterationCount = iterationCount;
        this.evaluator = new SchedulingEvaluator(p);
        this.schedFactory = new SchedulingFactory(p, evaluator);
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public static void main(String[] args) {
        Problem p = Problem.getDefault();
        new BeesSolver(p, 8, 500).solve();
    }

    public void solve() {
        List<Solution> explorerBees = IntStream.range(0, explorerBeeCount)
                .mapToObj(i -> schedFactory.randomScheduling())
                .collect(Collectors.toList());
        for (int i = 0; i < iterationCount; ++i) {
            List<Solution> bestBeesOfSwarms = runSwarms(explorerBees);
            explorerBees = new ArrayList<>(bestBeesOfSwarms.size());
            int j;
            for (j = 0; j < bestBeesOfSwarms.size() * KEPT_BEE_RATIO; ++j) {
                explorerBees.add(bestBeesOfSwarms.get(j));
            }
            for (; j < bestBeesOfSwarms.size(); ++j) {
                explorerBees.add(schedFactory.randomScheduling());
            }
            System.out.println(i + "th iteration: " + Collections.min(explorerBees).quality());
        }
        executor.shutdown();
        System.out.println(Collections.min(explorerBees));
        Solution tst = explorerBees.get(0);
    }

    private List<Solution> runSwarms(List<Solution> explorerBees) {
        Collections.sort(explorerBees);
        List<Solution> result = new ArrayList<>(explorerBees.size());
        for (int i = 0; i < explorerBees.size(); ++i) {
            Solution explorer = explorerBees.get(i);
            int carrierBeeCount = (explorerBees.size() - i) * 10;
            Solution best = new BeeSwarm(explorer, carrierBeeCount, 3, schedFactory, executor).run();
            result.add(best);
        }
        return result;
    }

}
