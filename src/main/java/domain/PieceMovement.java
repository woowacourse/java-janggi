package domain;

import java.util.List;

public record PieceMovement(List<PiecePath> obstaclePaths, PiecePath destinationPath) {

}
