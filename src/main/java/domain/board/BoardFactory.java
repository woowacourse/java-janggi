package domain.board;

import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.General;
import domain.piece.Guard;
import domain.piece.Soldier;
import domain.piece.Horse;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class BoardFactory {

    private static final Map<PieceType, Function<Team, Piece>> PIECE_GENERATORS = Map.of(
            PieceType.ELEPHANT, Elephant::new,
            PieceType.HORSE, Horse::new,
            PieceType.SOLDIER, Soldier::new,
            PieceType.CHARIOT, Chariot::new,
            PieceType.CANNON, Cannon::new,
            PieceType.GUARD, Guard::new,
            PieceType.GENERAL, General::new
    );

    private static final List<Integer> SOLDIER_COLUMNS = List.of(0, 2, 4, 6, 8);
    private static final List<Integer> CHARIOT_COLUMNS = List.of(0, 8);
    private static final List<Integer> GUARD_COLUMNS = List.of(3, 5);
    private static final List<Integer> CANNON_COLUMNS = List.of(1, 7);

    private BoardFactory() {
    }

    public static Board createBoard(InitializeSetting choInitialSetting, InitializeSetting hanInitialSetting) {
        Map<Position, Piece> pieces = new HashMap<>();
        List<PieceType> choSetting = choInitialSetting.getInitialSetting();
        List<PieceType> hanSetting = hanInitialSetting.getInitialSetting();

        for (Integer soliderColumns : SOLDIER_COLUMNS) {
            pieces.put(new Position(soliderColumns, 3), createPiece(PieceType.SOLDIER, Team.CHO));
            pieces.put(new Position(soliderColumns, 6), createPiece(PieceType.SOLDIER, Team.HAN));
        }

        for (Integer chariotColumn : CHARIOT_COLUMNS) {
            pieces.put(new Position(chariotColumn, 0), createPiece(PieceType.CHARIOT, Team.CHO));
            pieces.put(new Position(chariotColumn, 9), createPiece(PieceType.CHARIOT, Team.HAN));
        }

        for (Integer guardColumn : GUARD_COLUMNS) {
            pieces.put(new Position(guardColumn, 0), createPiece(PieceType.GUARD, Team.CHO));
            pieces.put(new Position(guardColumn, 9), createPiece(PieceType.GUARD, Team.HAN));
        }

        for (Integer cannonColumn : CANNON_COLUMNS) {
            pieces.put(new Position(cannonColumn, 2), createPiece(PieceType.CANNON, Team.CHO));
            pieces.put(new Position(cannonColumn, 7), createPiece(PieceType.CANNON, Team.HAN));
        }

        pieces.put(new Position(4, 1), new General(Team.CHO));
        pieces.put(new Position(4, 8), new General(Team.HAN));

        pieces.put(new Position(1, 0), createPiece(choSetting.get(0), Team.CHO));
        pieces.put(new Position(2, 0), createPiece(choSetting.get(1), Team.CHO));
        pieces.put(new Position(6, 0), createPiece(choSetting.get(2), Team.CHO));
        pieces.put(new Position(7, 0), createPiece(choSetting.get(3), Team.CHO));

        pieces.put(new Position(7, 9), createPiece(hanSetting.get(0), Team.HAN));
        pieces.put(new Position(6, 9), createPiece(hanSetting.get(1), Team.HAN));
        pieces.put(new Position(2, 9), createPiece(hanSetting.get(2), Team.HAN));
        pieces.put(new Position(1, 9), createPiece(hanSetting.get(3), Team.HAN));

        return new Board(pieces);
    }

    private static Piece createPiece(PieceType pieceType, Team team) {
        return PIECE_GENERATORS.get(pieceType).apply(team);
    }
}
