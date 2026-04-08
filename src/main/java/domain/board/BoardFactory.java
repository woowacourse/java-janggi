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

    private static final int GENERAL_COLUMN = 4;
    private static final List<Integer> SOLDIER_COLUMNS = List.of(0, 2, 4, 6, 8);
    private static final List<Integer> CHARIOT_COLUMNS = List.of(0, 8);
    private static final List<Integer> GUARD_COLUMNS = List.of(3, 5);
    private static final List<Integer> CANNON_COLUMNS = List.of(1, 7);
    private static final List<Integer> CHO_DYNAMIC_COLUMNS = List.of(1, 2, 6, 7);
    private static final List<Integer> HAN_DYNAMIC_COLUMNS = List.of(7, 6, 2, 1);

    private BoardFactory() {
    }

    public static Board createBoard(InitializeSetting choInitialSetting, InitializeSetting hanInitialSetting) {
        Map<Position, Piece> pieces = new HashMap<>();
        placeSoldiers(pieces);
        placeChariots(pieces);
        placeGuards(pieces);
        placeCannons(pieces);
        placeGeneral(pieces);
        placeDynamicPieces(pieces, choInitialSetting, hanInitialSetting);



        return new Board(pieces);
    }

    public static Piece createPiece(PieceType pieceType, Team team) {
        return PIECE_GENERATORS.get(pieceType).apply(team);
    }

    private static void placeGeneral(Map<Position, Piece> pieces) {
        int choGeneralRow = 1;
        int hanGeneralRow = 8;
        pieces.put(new Position(GENERAL_COLUMN, choGeneralRow), createPiece(PieceType.GENERAL, Team.CHO));
        pieces.put(new Position(GENERAL_COLUMN, hanGeneralRow), createPiece(PieceType.GENERAL, Team.HAN)); // 🌟 꼬여있던 X, Y 버그 해결
    }

    private static void placeSoldiers(Map<Position, Piece> pieces) {
        int choSoldiersRow = 3;
        int hanSoldiersRow = 6;
        for (Integer column : SOLDIER_COLUMNS) {
            pieces.put(new Position(column, choSoldiersRow), createPiece(PieceType.SOLDIER, Team.CHO));
            pieces.put(new Position(column, hanSoldiersRow), createPiece(PieceType.SOLDIER, Team.HAN));
        }
    }

    private static void placeCannons(Map<Position, Piece> pieces) {
        int choCannonsRow = 2;
        int hanCannonsRow = 7;
        for (Integer column : CANNON_COLUMNS) {
            pieces.put(new Position(column, choCannonsRow), createPiece(PieceType.CANNON, Team.CHO));
            pieces.put(new Position(column, hanCannonsRow), createPiece(PieceType.CANNON, Team.HAN));
        }
    }

    private static void placeGuards(Map<Position, Piece> pieces) {
        int choGuardsRow = 0;
        int hanGuardsRow = 9;
        for (Integer column : GUARD_COLUMNS) {
            pieces.put(new Position(column, choGuardsRow), createPiece(PieceType.GUARD, Team.CHO));
            pieces.put(new Position(column, hanGuardsRow), createPiece(PieceType.GUARD, Team.HAN));
        }
    }

    private static void placeChariots(Map<Position, Piece> pieces) {
        int choChariotsRow = 0;
        int hanChariotsRow = 9;
        for (Integer column : CHARIOT_COLUMNS) {
            pieces.put(new Position(column, choChariotsRow), createPiece(PieceType.CHARIOT, Team.CHO));
            pieces.put(new Position(column, hanChariotsRow), createPiece(PieceType.CHARIOT, Team.HAN));
        }
    }

    private static void placeDynamicPieces(Map<Position, Piece> pieces, InitializeSetting choSetting, InitializeSetting hanSetting) {
        List<PieceType> choTypes = choSetting.getInitialSetting();
        List<PieceType> hanTypes = hanSetting.getInitialSetting();

        for (int i = 0; i < 4; i++) {
            pieces.put(new Position(CHO_DYNAMIC_COLUMNS.get(i), 0), createPiece(choTypes.get(i), Team.CHO));
            pieces.put(new Position(HAN_DYNAMIC_COLUMNS.get(i), 9), createPiece(hanTypes.get(i), Team.HAN));
        }
    }
}
