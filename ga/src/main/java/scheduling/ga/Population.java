package scheduling.ga;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import scheduling.model.SchedulingFactory;
import scheduling.model.Solution;
import static java.util.Objects.requireNonNull;
import static java.lang.String.format;

public class Population extends AbstractList<Solution>{

  private final SchedulingFactory schedFactory;
  
  private final int populationSize;
  
  private final List<Solution> storage;
  
  private final Random random;
  
  public Population(SchedulingFactory schedFactory, int populationSize) {
    this(schedFactory, populationSize, new Random());
  }

  public Population(SchedulingFactory schedFactory, int populationSize, Random random) {
    this.schedFactory = requireNonNull(schedFactory, "schedFactory cannot be null");
    this.random = requireNonNull(random, "random cannot be null");
    if (populationSize < 2) {
      throw new IllegalArgumentException(format("populationSize must be at least 2, got %d", populationSize));
    }
    this.populationSize = populationSize;
    this.storage = new ArrayList<>(populationSize);
    for (int i = 0; i < populationSize; ++i) {
      storage.add(schedFactory.randomScheduling());
    }
  }

  public int size() {
    return storage.size();
  }

  @Override
  public Solution get(int index) {
    return storage.get(index);
  }

  public List<Solution> select(int toBeSelected) {
    return IntStream.range(0, toBeSelected)
        .map(i -> random.nextInt(populationSize))
        .mapToObj(this::get)
        .collect(Collectors.toList());
  }

}
