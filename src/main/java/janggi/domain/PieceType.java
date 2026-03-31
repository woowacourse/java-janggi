package janggi.domain;

import janggi.domain.moveRules.ChaMoveRule;
import janggi.domain.moveRules.KingMoveRule;
import janggi.domain.moveRules.MaMoveRule;
import janggi.domain.moveRules.PoMoveRule;
import janggi.domain.moveRules.SaMoveRule;
import janggi.domain.moveRules.SangMoveRule;
import janggi.domain.moveRules.ZolMoveRule;
import java.util.List;

public enum PieceType {
    KING("왕", List.of(5), 9, new KingMoveRule()),
    SA("사", List.of(4, 6), 10, new SaMoveRule()),
    SANG("상", List.of(3, 8), 10, new SangMoveRule()),
    MA("마", List.of(2, 7), 10, new MaMoveRule()),
    CHA("차", List.of(1, 9), 10, new ChaMoveRule()),
    PO("포", List.of(2, 8), 8, new PoMoveRule()),
    ZOL("졸", List.of(1, 3, 5, 7, 9), 7, new ZolMoveRule());

    private final String name;
    private final List<Integer> xPositions;
    private final int yPosition;
    private final MoveRule moveRule;

    PieceType(String name, List<Integer> xPositions, int yPosition, MoveRule moveRule) {
        this.name = name;
        this.xPositions = xPositions;
        this.yPosition = yPosition;
        this.moveRule = moveRule;
    }

    public String getName() {
        return name;
    }

    public void placeOnBoard(Board board, Team team) {
        for(int xPosition:xPositions) {
            Position position = new Position(xPosition, calculateYPositionByTeam(team));
            Piece piece = new Piece(team, this);
            board.place(position, piece);
        }
    }

    private int calculateYPositionByTeam(Team team) {
        if(team == Team.HAN) {
            return 11 - yPosition;
        }
        return yPosition;
    }

    public List<Route> findRoutes(Team team) {
        return moveRule.findRoutes(team);
    }
}
