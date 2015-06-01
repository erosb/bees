package bees.model;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Set;

public class Employee {

    private final String name;

    private final Set<Skill> skills;

    public Employee(String name, Set<Skill> skills) {
        this.name = requireNonNull(name, "name cannot be null");
        this.skills = Collections.unmodifiableSet(requireNonNull(skills, "skills cannot be null"));
    }

    public String name() {
        return name;
    }

    public Set<Skill> skills() {
        return skills;
    }

    @Override
    public String toString() {
        return name;
    }

    
}
