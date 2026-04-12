package janggi.domain.turn;

import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.board.Board;

public abstract class Turn {

    public abstract Turn move(Position source, Position target, Board board);

    public boolean isFinished() {
        return false;
    }

    public abstract Team getTeam();

    public void validateIsNull(Piece piece) {
        if (piece == null) {
            throw new IllegalArgumentException("[ERROR] 빈 칸을 선택할 수 없습니다.");
        }
    }

    public void validateTurn(Piece piece) {
        if (piece.getTeam() != getTeam()) {
            throw new IllegalArgumentException("[ERROR] 상대팀 기물을 선택할 수 없습니다.");
        }
    }
}
