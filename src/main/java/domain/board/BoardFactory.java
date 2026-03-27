package domain.board;

import domain.piece.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {
    private static final List<Integer> SOLDIER_COLUMNS = List.of(0, 2, 4, 6, 8);
    private static final List<Integer> CHARIOT_COLUMNS = List.of(0, 8);
    private static final List<Integer> GUARD_COLUMNS = List.of(3, 5);
    private static final List<Integer> CANNON_COLUMNS = List.of(1, 7);

    private BoardFactory() {
    }

    public static Board createBoard(InitializeSetting choInitialSetting, InitializeSetting hanInitialSetting) {
        Map<Position, Piece> pieces = new HashMap<>();


        for (Integer soliderColumns : SOLDIER_COLUMNS) {
            pieces.put(new Position(soliderColumns, 3), new Piece(PieceType.SOLDIER, Team.CHO, new ChoSoldierStrategy()));
            pieces.put(new Position(soliderColumns, 6), new Piece(PieceType.SOLDIER, Team.HAN, new HanSoldierStrategy()));
        }

        for (Integer chariotColumn : CHARIOT_COLUMNS) {
            pieces.put(new Position(chariotColumn, 0), new Piece(PieceType.CHARIOT, Team.CHO, new ChariotStrategy()));
            pieces.put(new Position(chariotColumn, 9), new Piece(PieceType.CHARIOT, Team.HAN, new ChariotStrategy()));

        }

        for (Integer guardColumn : GUARD_COLUMNS) {
            pieces.put(new Position(guardColumn, 0), new Piece(PieceType.GUARD, Team.CHO, new GuardStrategy()));
            pieces.put(new Position(guardColumn, 9), new Piece(PieceType.GUARD, Team.HAN, new GuardStrategy()));

        }

        for (Integer cannonColumn : CANNON_COLUMNS) {
            pieces.put(new Position(cannonColumn, 2), new Piece(PieceType.CANNON, Team.CHO, new CannonStrategy()));
            pieces.put(new Position(cannonColumn, 7), new Piece(PieceType.CANNON, Team.HAN, new CannonStrategy()));

        }

        pieces.put(new Position(4, 1), new Piece(PieceType.GENERAL, Team.CHO, new GeneralStrategy()));
        pieces.put(new Position(4, 8), new Piece(PieceType.GENERAL, Team.HAN, new GeneralStrategy()));

        List<PieceType> choSetting = choInitialSetting.getInitialSetting();
        pieces.put(new Position(1, 0), createPiece(choSetting.get(0), Team.CHO));
        pieces.put(new Position(2, 0), createPiece(choSetting.get(1), Team.CHO));
        pieces.put(new Position(6, 0), createPiece(choSetting.get(2), Team.CHO));
        pieces.put(new Position(7, 0), createPiece(choSetting.get(3), Team.CHO));

        List<PieceType> hanSetting = hanInitialSetting.getInitialSetting();
        pieces.put(new Position(1, 9), createPiece(hanSetting.get(0), Team.HAN));
        pieces.put(new Position(2, 9), createPiece(hanSetting.get(1), Team.HAN));
        pieces.put(new Position(6, 9), createPiece(hanSetting.get(2), Team.HAN));
        pieces.put(new Position(7, 9), createPiece(hanSetting.get(3), Team.HAN));


        return new Board(pieces);
    }

    private static Piece createPiece(PieceType pieceType, Team team) {
        if (pieceType == PieceType.ELEPHANT) {
            return new Piece(pieceType, team, new ElephantStrategy());
        }
        return new Piece(pieceType, team, new HorseStrategy());
    }
}
