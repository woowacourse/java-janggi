package janggi.domain;

import java.util.Arrays;

public enum Arrangement {
    마상마상("마상마상"),
    마상상마("마상상마"),
    상마마상("상마마상"),
    상마상마("상마상마");

    private final String openningSetup;

    Arrangement(String openningSetup) {
        this.openningSetup = openningSetup;
    }

    public String getOpenningSetup() {
        return openningSetup;
    }
}
