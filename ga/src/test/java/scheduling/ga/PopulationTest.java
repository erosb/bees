package scheduling.ga;

import java.util.Collection;
import java.util.Random;

import org.easymock.EasyMock;
import org.junit.Test;

import scheduling.model.Problem;
import scheduling.model.SchedulingFactory;
import scheduling.model.Solution;
import scheduling.quality.SchedulingEvaluator;
import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;
public class PopulationTest {
  
  @Test
  public void testPopulationInitialization() {
    Population subject = subject();
    assertEquals(200, subject.size());
    for (Solution entity: subject) {
      assertEquals(4, entity.size());
    }
  }
  
  @Test
  public void testSelection() {
    Random random = createMock(Random.class);
    expect(random.nextInt(200)).andReturn(3);
    expect(random.nextInt(200)).andReturn(4);
    expect(random.nextInt(200)).andReturn(4);
    expect(random.nextInt(200)).andReturn(5);
    replay(random);
    Population subject = subject(random);
    Collection<Solution> actual = subject.select(4);
    verify(random);
    assertEquals(4, actual.size());
    assertTrue(actual.contains(subject.get(3)));
    assertTrue(actual.contains(subject.get(4)));
    assertTrue(actual.contains(subject.get(5)));
  }

  private Population subject() {
    Random random = new Random();
    return subject(random);
  }

  private Population subject(Random random) {
    return new Population(new SchedulingFactory(Problem.getDefault(), new SchedulingEvaluator(Problem.getDefault())), 200, random);
  }
  

}
