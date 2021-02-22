package com.zallpy.dataprocessing.entities;

import java.util.Objects;
import java.util.StringJoiner;

public class Target {

    private final Long id;
    private final String name;

    public Target(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Target target = (Target) o;
        return Objects.equals(id, target.id) && Objects.equals(name, target.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Target.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .toString();
    }
}
