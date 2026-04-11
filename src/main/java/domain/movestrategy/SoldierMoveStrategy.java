package domain.movestrategy;

import domain.board.Board;
import domain.piece.Delta;
import domain.piece.Position;
import domain.player.Team;

import java.util.ArrayList;
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
        final List<Position> movable = new ArrayList<>();

        movable.addAll(calculateDefaultMoves(from, board));
        movable.addAll(calculatePalaceMovablePositions(from, board));

        return movable;
    }

    private List<Position> calculateDefaultMoves(final Position from, final Board board) {
        final Team team = board.getPiece(from).getTeam();

        return getDefaultPathsByTeam(team).stream()
                .map(from::move)
                .filter(board::inBoard)
                .filter(to -> !isAlly(from, to, board))
                .toList();
    }

    @Override
    public List<Position> calculatePalaceMovablePositions(final Position from, final Board board) {
        if (!board.inPalace(from)) {
            return List.of();
        }

        final Team team = board.getPiece(from).getTeam();

        return board.getPalaceDeltas(from).stream()
                .filter(delta -> isForward(delta, team))
                .map(from::move)
                .filter(to -> !isAlly(from, to, board))
                .toList();
    }


    private boolean isForward(final Delta delta, final Team team) {
        final int dy = delta.row();

        if (team == Team.CHO) {
            return dy < 0;
        }
        return dy > 0;
    }

    private static List<Delta> getDefaultPathsByTeam(final Team team) {
        if (team == Team.HAN) {
            return HAN_PATHS;
        }
        return CHO_PATHS;
    }
}
