package domain.rule;

import domain.board.Board;
import domain.player.Team;
import domain.position.Position;

public class BigJangDrawRule implements DrawRule {

    @Override
    public boolean isDraw(Board board) {
        Position choJangPosition = board.findJangPosition(Team.CHO);
        Position hanJangPosition = board.findJangPosition(Team.HAN);

        if (choJangPosition.column() != hanJangPosition.column()) {
            return false;
        }

        int startRow = Math.min(choJangPosition.row(), hanJangPosition.row()) + 1;
        int endRow = Math.max(choJangPosition.row(), hanJangPosition.row());
        int column = choJangPosition.column();

        for (int row = startRow; row < endRow; row++) {
            if (!board.findPiece(new Position(row, column)).isNone()) {
                return false;
            }
        }
        return true;
    }
}

