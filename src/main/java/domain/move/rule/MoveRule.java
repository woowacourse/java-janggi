package domain.move.rule;

import domain.board.Intersection;
import domain.game.Side;
import domain.move.movement.Path;
import domain.piece.AlivePieces;
import java.util.List;

public interface MoveRule {

    List<Intersection> movableDestinations(Side side, List<Path> candidatePaths, AlivePieces alivePieces);
}
