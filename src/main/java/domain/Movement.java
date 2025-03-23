package domain;

import java.util.List;

public record Movement(List<Path> obstaclePaths, Path destinationPath) {

}
