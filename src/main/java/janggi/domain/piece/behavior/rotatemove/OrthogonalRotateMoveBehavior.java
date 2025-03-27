package janggi.domain.piece.behavior.rotatemove;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.move.Vectors;
import janggi.domain.piece.PieceBehavior;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class OrthogonalRotateMoveBehavior implements PieceBehavior {

    @Override
    public final Set<Position> generateAvailableMovePositions(Board board, Team team, Position position) {
        Set<Position> result = new HashSet<>();
        List<Vectors> rotatedVectors = new ArrayList<>(getVectorsList());

        for (int i = 0; i < 4; i++) {
            rotatedVectors = Vectors.rotate(rotatedVectors);
            searchAvailableMoves(result, board, position, rotatedVectors, team);
        }

        return result;
    }

    protected abstract List<Vectors> getVectorsList();

    protected abstract void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition,
                                                 List<Vectors> vectorsList,
                                                 Team team);

    protected abstract void searchAvailableMove(Set<Position> result, Board board, Position currentPosition, Team team,
                                                Vectors vectors);

    protected boolean canNotMove(Vectors vectors, Position currentPosition) {
        return vectors.vectors()
                .stream()
                .allMatch(currentPosition::canNotMove);
    }
}
