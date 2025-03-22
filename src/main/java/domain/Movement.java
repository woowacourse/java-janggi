package domain;

import java.util.List;

public record Movement(List<Directions> obstaclePaths, Directions destinationPath) {

}
