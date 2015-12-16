package scheduling.model;

import org.junit.Test;

import scheduling.model.Interval;
import static org.junit.Assert.*;
public class IntervalTest {
    
    @Test
    public void test() {
        assertEquals(0, Interval.tsFromString("00:00"));
        assertEquals(60, Interval.tsFromString("01:00"));
        assertEquals(630, Interval.tsFromString("10:30"));
    }

}
