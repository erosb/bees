package bees.model;

public class Interval {
    
    public static long tsFromString(String str) {
        String[] segments = str.split(":");
        long hours = Long.parseLong(segments[0]);
        return hours * 60 + Long.parseLong(segments[1]);
    }
    
    public static Interval of(String from, String until) {
        return new Interval(tsFromString(from), tsFromString(until));
    }

    private long from;

    private final long until;

    public Interval(long from, long until) {
        this.from = from;
        this.until = until;
    }

    public long from() {
        return from;
    }

    public long until() {
        return until;
    }

}
