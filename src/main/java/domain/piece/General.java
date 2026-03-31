package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;
import util.ErrorMessage;

import java.util.List;

public class General extends Piece {

    private static final List<List<Integer>> MOVABLE_LOCATION = List.of(List.of(-1, 0), List.of(0, -1), List.of(1, 0), List.of(0, 1));

    public General(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.GENERAL;
    }

    @Override
    public boolean isGeneral() {
        return true;
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        List<Integer> diff = List.of(from.differentColumn(to), from.differentRow(to));
        if (!MOVABLE_LOCATION.contains(diff)) {
            throw new PieceException(ErrorMessage.IMPOSSIBLE_MOVE.getMessage());
        }
    }

    @Override
    public List<Coordination> resolvePath(Coordination from, Coordination to) {
        return List.of();
    }
}
