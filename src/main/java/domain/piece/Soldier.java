package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;

import java.util.List;

public class Soldier extends Piece {

    private static final List<List<Integer>> CHO_MOVABLE_LOCATION = List.of(List.of(-1, 0), List.of(0, -1), List.of(1, 0));
    private static final List<List<Integer>> HAN_MOVABLE_LOCATION = List.of(List.of(-1, 0), List.of(0, 1), List.of(1, 0));

    public Soldier(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.SOLDIER;
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        List<Integer> diff = List.of(from.differentColumn(to), from.differentRow(to));
        List<List<Integer>> movable = team == Team.CHO ? CHO_MOVABLE_LOCATION : HAN_MOVABLE_LOCATION;
        if (!movable.contains(diff)) {
            throw new PieceException(IMPOSSIBLE_MOVE);
        }
    }

    @Override
    public List<Coordination> resolvePath(Coordination from, Coordination to) {
        return List.of();
    }
}
