package scheduling.bees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;

import scheduling.model.SchedulingFactory;
import scheduling.model.Solution;

public class BeeSwarm {

    private static final Random random = new Random();

    private final List<Solution> swarm = new ArrayList<>();

    private final int carrierBeeCount;
    
    private final int beeMovementCount;

    private final SchedulingFactory schedFactory;

    private CompletionService<Solution> complService;

    public BeeSwarm(Solution explorerBee, int carrierBeeCount, int beeMovementCount, SchedulingFactory schedFactory, Executor executor) {
        swarm.add(explorerBee);
        this.carrierBeeCount = carrierBeeCount;
        this.beeMovementCount = beeMovementCount;
        this.schedFactory = schedFactory;
        this.complService = new ExecutorCompletionService<>(executor);
    }

    public Solution run() {
        for (int i = 0; i < beeMovementCount; ++i) {
            moveBees();
        }
        return Collections.min(swarm);
    }

    private void moveBees() {
        for (int i = 0; i < carrierBeeCount; ++i) {
            Solution original = swarm.get(random.nextInt(swarm.size()));
            complService.submit(() -> trySwapping(original));
        }
        swarm.clear();
        for (int i = 0; i < carrierBeeCount; ++i) {
            try {
                swarm.add(complService.take().get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }

    private Solution trySwapping(Solution original) {
        Solution swapped = schedFactory.randomSwap(original);
        if (swapped.compareTo(original) < 0) {
            return swapped;
        }
        return Math.random() < 0.5 ? swapped : original;
    }

}
