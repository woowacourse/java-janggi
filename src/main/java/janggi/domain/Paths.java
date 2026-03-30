package janggi.domain;

import janggi.domain.position.Position;

import java.util.List;

public class Paths {
    private final List<Path> paths;

    public Paths(List<Path> paths) {
        this.paths = paths;
    }

    public Path findPathByDestination(Position position){
        return paths.stream()
                .filter(p -> p.isDestination(position))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 좌표입니다."));
    }
}

