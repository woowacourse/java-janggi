package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.piece.moverules.ChaMoveRule;
import janggi.domain.piece.moverules.KingMoveRule;
import janggi.domain.piece.moverules.MaMoveRule;
import janggi.domain.piece.moverules.MoveRule;
import janggi.domain.piece.moverules.PoMoveRule;
import janggi.domain.piece.moverules.SaMoveRule;
import janggi.domain.piece.moverules.SangMoveRule;
import janggi.domain.piece.moverules.ZolMoveRule;
import java.util.List;

public enum PieceType {
    KING("왕", new KingMoveRule(), 0.0),
    SA("사", new SaMoveRule(), 3.0),
    SANG("상", new SangMoveRule(), 3.0),
    MA("마", new MaMoveRule(), 5.0),
    CHA("차", new ChaMoveRule(), 13.0),
    PO("포", new PoMoveRule(), 7.0),
    ZOL("졸", new ZolMoveRule(), 2.0);

    private final String name;
    private final MoveRule moveRule;
    private final double score;

    PieceType(String name, MoveRule moveRule, double score) {
        this.name = name;
        this.moveRule = moveRule;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public List<Position> findMovablePositions(Board board, Position position, Team team) {
        return moveRule.findMovablePositions(board, position, team);
    }

    public double addScore(double currentTotalScore) {
        return currentTotalScore + score;
    }
}
