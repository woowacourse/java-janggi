package janggi;

import java.util.List;

public enum AssignType {
    LEFT_TOP("왼상(상마상마)"),
    RIGHT_TOP("오른상(마상마상)"),
    IN_TOP("안상(마상상마)"),
    OUT_TOP("바깥상(상마마상)"),
    ;

    private final String name;

    AssignType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
