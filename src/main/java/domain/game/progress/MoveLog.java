package domain.game.progress;

import domain.board.BoardMove;
import domain.game.Team;
import domain.piece.Piece;
import domain.position.Position;

public record MoveLog(Type type, Team turn, Position source, Position destination, Piece piece) {

    public enum Type {
        MOVE, PASS
    }

    public static MoveLog move(Team turn, BoardMove boardMove) {
        return new MoveLog(Type.MOVE, turn, boardMove.source(), boardMove.destination(), boardMove.movedPiece());
    }

    public static MoveLog pass(Team turn) {
        return new MoveLog(Type.PASS, turn, null, null, null);
    }

    public boolean isPass() {
        return type == Type.PASS;
    }
}
