package domain.strategy;

import domain.board.Board;
import domain.board.Palace;
import domain.board.Team;
import domain.vo.Position;

public class GeneralMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (!arePositionsInSamePalace(from, to, board))
            return false;

        if (!from.isOneStepStraightTo(to)
                && !MoveValidator.canMoveOneStepDiagonal(from, to, board))
            return false;

        return board.canOccupy(from, to);
    }

    private boolean arePositionsInSamePalace(Position from, Position to, Board board) {
        Team team = board.findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 움직일 기물이 존재하지 않습니다."))
                .getTeam();

        Palace palace = board.getPalace(team);
        return palace.isInPalace(from) && palace.isInPalace(to);
    }
}
