package domain.movestrategy;

import domain.board.Board;
import domain.piece.Delta;
import domain.piece.Piece;
import domain.piece.Position;
import domain.player.Team;
import java.util.List;

public class SoldierMoveStrategy implements MoveStrategy {

    private static final List<Delta> CHO_PATHS = List.of(
            Delta.RIGHT, Delta.UP, Delta.LEFT
    );

    private static final List<Delta> HAN_PATHS = List.of(
            Delta.RIGHT, Delta.DOWN, Delta.LEFT
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Board board) {
        final List<Delta> paths = getPathsByTeam(from, board);

        return paths.stream()
                .map(from::move)
                .filter(board::inBoard)
                .filter(to -> !isAlly(from, to, board))
                .toList();
    }


    private static List<Delta> getPathsByTeam(final Position from, final Board board) {
        final Piece fromPiece = board.getPiece(from);

        if (fromPiece.isSameTeam(Team.HAN)) {
            return HAN_PATHS;
        }
        return CHO_PATHS;
    }
}
