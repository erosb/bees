package bees.model;

import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.MediaSize.Other;

import bees.Quality;

public class Solution extends ArrayList<WeeklyScheduling> implements Comparable<Solution> {
    private static final long serialVersionUID = -8233015353134023195L;
    
    private final Quality quality;

    public Solution(List<WeeklyScheduling> weeklyScheds, Quality quality) {
        this.quality = quality;
        addAll(weeklyScheds);
    }

    public Quality quality() {
        return quality;
    }

    @Override
    public int compareTo(Solution o) {
        return quality.compareTo(o.quality);
    }

}
