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
    KING("왕", new KingMoveRule()),
    SA("사", new SaMoveRule()),
    SANG("상", new SangMoveRule()),
    MA("마", new MaMoveRule()),
    CHA("차", new ChaMoveRule()),
    PO("포", new PoMoveRule()),
    ZOL("졸", new ZolMoveRule());

    private final String name;
    private final MoveRule moveRule;

    PieceType(String name, MoveRule moveRule) {
        this.name = name;
        this.moveRule = moveRule;
    }

    public String getName() {
        return name;
    }

    public List<Position> findMovablePositions(Board board, Position position, Team team) {
        return moveRule.findMovablePositions(board, position, team);
    }
}
