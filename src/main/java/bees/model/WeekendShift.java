package bees.model;

public enum WeekendShift {

    DAY("06:00", "18:00"), NIGHT("18:00", "06:00");

    private final Interval interval;

    private WeekendShift(String from, String until) {
        this(Interval.of(from, until));
    }

    private WeekendShift(Interval interval) {
        this.interval = interval;
    }

    public Interval interval() {
        return interval;
    }

}
