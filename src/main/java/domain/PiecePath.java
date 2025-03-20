package domain;

import java.util.List;

public record PiecePath(List<Directions> obstaclePaths, Directions destinationPath) {

}
