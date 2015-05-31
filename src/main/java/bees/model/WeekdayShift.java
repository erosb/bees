package bees.model;

public enum WeekdayShift {

    MORNING("06:00", "14:00"), DAY("09:00", "17:00"), AFTERNOON("14:00", "22:00"), NIGHT("22:00", "06:00");

    private final Interval interval;

    private WeekdayShift(String from, String until) {
        this(Interval.of(from, until));
    }

    private WeekdayShift(Interval interval) {
        this.interval = interval;
    }

    public Interval interval() {
        return interval;
    }

}
