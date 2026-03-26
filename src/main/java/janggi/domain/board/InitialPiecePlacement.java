package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.strategy.MoveStrategy;
import java.util.HashMap;
import java.util.Map;

public enum InitialPiecePlacement {

    CHO_SOLDIER_1(3, 0, Camp.CHO, PieceRule.SOLDIER, null),
    CHO_SOLDIER_2(3, 2, Camp.CHO, PieceRule.SOLDIER, null),
    CHO_SOLDIER_3(3, 4, Camp.CHO, PieceRule.SOLDIER, null),
    CHO_SOLDIER_4(3, 6, Camp.CHO, PieceRule.SOLDIER, null),
    CHO_SOLDIER_5(3, 8, Camp.CHO, PieceRule.SOLDIER, null),
    CHO_CHARIOT_LEFT(0, 0, Camp.CHO, PieceRule.CHARIOT, null),
    CHO_GUARD_LEFT(0, 3, Camp.CHO, PieceRule.GUARD, null),
    CHO_GUARD_RIGHT(0, 5, Camp.CHO, PieceRule.GUARD, null),
    CHO_CHARIOT_RIGHT(0, 8, Camp.CHO, PieceRule.CHARIOT, null),
    CHO_GENERAL(1, 4, Camp.CHO, PieceRule.GENERAL, null),
    CHO_CANNON_LEFT(2, 1, Camp.CHO, PieceRule.CANNON, null),
    CHO_CANNON_RIGHT(2, 7, Camp.CHO, PieceRule.CANNON, null),

    HAN_SOLDIER_1(6, 0, Camp.HAN, PieceRule.SOLDIER, null),
    HAN_SOLDIER_2(6, 2, Camp.HAN, PieceRule.SOLDIER, null),
    HAN_SOLDIER_3(6, 4, Camp.HAN, PieceRule.SOLDIER, null),
    HAN_SOLDIER_4(6, 6, Camp.HAN, PieceRule.SOLDIER, null),
    HAN_SOLDIER_5(6, 8, Camp.HAN, PieceRule.SOLDIER, null),
    HAN_CANNON_LEFT(7, 1, Camp.HAN, PieceRule.CANNON, null),
    HAN_CANNON_RIGHT(7, 7, Camp.HAN, PieceRule.CANNON, null),
    HAN_GENERAL(8, 4, Camp.HAN, PieceRule.GENERAL, null),
    HAN_CHARIOT_LEFT(9, 0, Camp.HAN, PieceRule.CHARIOT, null),
    HAN_GUARD_LEFT(9, 3, Camp.HAN, PieceRule.GUARD, null),
    HAN_GUARD_RIGHT(9, 5, Camp.HAN, PieceRule.GUARD, null),
    HAN_CHARIOT_RIGHT(9, 8, Camp.HAN, PieceRule.CHARIOT, null);

    private final int row;
    private final int column;
    private final Camp camp;
    private final PieceRule pieceRule;
    private final MoveStrategy moveStrategy;

    InitialPiecePlacement(int row, int column, Camp camp, PieceRule pieceRule, MoveStrategy moveStrategy) {
        this.row = row;
        this.column = column;
        this.camp = camp;
        this.pieceRule = pieceRule;
        this.moveStrategy = moveStrategy;
    }

    // TODO : 둘 다 같은 나라의 ElephantSetting 들어와도 컴파일 에러 X -> 타입 강제 고려하기
    public static Map<Position, Piece> init(ElephantSetting choElephantSetting, ElephantSetting hanElephantSetting) {
        Map<Position, Piece> board = new HashMap<>();

        for (InitialPiecePlacement piece : values()) {
            board.put(new Position(piece.row, piece.column)
                    , new Piece(piece.pieceRule, piece.camp, piece.moveStrategy));
        }

        Map<Position, Piece> choElephants = choElephantSetting.makeElephants();
        Map<Position, Piece> hanElephants = hanElephantSetting.makeElephants();

        board.putAll(choElephants);
        board.putAll(hanElephants);
        return board;
    }
}
