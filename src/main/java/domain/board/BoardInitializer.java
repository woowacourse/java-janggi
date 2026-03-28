package domain.board;

import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardInitializer {
    private static final int HAN_SOLDIERS_Y_COORDINATE = 4;
    private static final int HAN_CANNON_Y_COORDINATE = 3;
    private static final int HAN_GENERAL_Y_COORDINATE = 2;
    private static final int HAN_OTHER_PIECES_Y_COORDINATE = 1;
    private static final int TOTAL_Y_COORDINATE = 11;
    private static final List<Integer> ELEPHANT_SETUP_POSITION = List.of(2, 3, 7, 8);

    private static final int LEFT_CANNON_X_COORDINATE = 2;
    private static final int RIGHT_CANNON_X_COORDINATE = 8;
    private static final int LEFT_CHARIOT_X_COORDINATE = 1;
    private static final int RIGHT_CHARIOT_X_COORDINATE = 9;
    private static final int LEFT_GUARD_X_COORDINATE = 4;
    private static final int RIGHT_GUARD_X_COORDINATE = 6;
    private static final int GENERAL_X_COORDINATE = 5;

    public static Map<Position, Piece> init(int choInput, int hanInput) {
        Map<Position, Piece> initialBoard = new HashMap<>();

        setUpElephantAndHorse(initialBoard, Camp.CHO, choInput);
        setUpElephantAndHorse(initialBoard, Camp.HAN, hanInput);
        setUpOtherPieces(initialBoard);

        return initialBoard;
    }

    private static void setUpOtherPieces(Map<Position, Piece> initialBoard) {
        setUpCampOtherPieces(initialBoard, Camp.HAN);
        setUpCampOtherPieces(initialBoard, Camp.CHO);
    }

    private static void setUpCampOtherPieces(Map<Position, Piece> initialBoard, Camp camp) {
        int otherY = resolveY(camp, HAN_OTHER_PIECES_Y_COORDINATE);
        int cannonY = resolveY(camp, HAN_CANNON_Y_COORDINATE);
        int generalY = resolveY(camp, HAN_GENERAL_Y_COORDINATE);
        int soldierY = resolveY(camp, HAN_SOLDIERS_Y_COORDINATE);

        setUpSoldier(initialBoard, camp, soldierY);
        setUpCannon(initialBoard, camp, cannonY);
        setUpChariot(initialBoard, camp, otherY);
        setUpGuard(initialBoard, camp, otherY);
        setUpGeneral(initialBoard, camp, generalY);
    }

    private static void setUpSoldier(Map<Position, Piece> initialBoard, Camp camp, int soldierY) {
        for (int soldierX = 1; soldierX <= 9; soldierX += 2) {
            initialBoard.put(new Position(soldierX, soldierY), new Piece(camp, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy()));
        }
    }

    private static void setUpCannon(Map<Position, Piece> initialBoard, Camp camp, int cannonY) {
        initialBoard.put(
                new Position(LEFT_CANNON_X_COORDINATE, cannonY),
                new Piece(camp, PieceType.CANNON, PieceType.CANNON.createStrategy())
        );

        initialBoard.put(
                new Position(RIGHT_CANNON_X_COORDINATE, cannonY),
                new Piece(camp, PieceType.CANNON, PieceType.CANNON.createStrategy())
        );
    }

    private static void setUpChariot(Map<Position, Piece> initialBoard, Camp camp, int otherY) {
        initialBoard.put(
                new Position(LEFT_CHARIOT_X_COORDINATE, otherY),
                new Piece(camp, PieceType.CHARIOT, PieceType.CHARIOT.createStrategy())
        );

        initialBoard.put(
                new Position(RIGHT_CHARIOT_X_COORDINATE, otherY),
                new Piece(camp, PieceType.CHARIOT, PieceType.CHARIOT.createStrategy())
        );
    }

    private static void setUpGuard(Map<Position, Piece> initialBoard, Camp camp, int otherY) {
        initialBoard.put(
                new Position(LEFT_GUARD_X_COORDINATE, otherY),
                new Piece(camp, PieceType.GUARD, PieceType.GUARD.createStrategy())
        );

        initialBoard.put(
                new Position(RIGHT_GUARD_X_COORDINATE, otherY),
                new Piece(camp, PieceType.GUARD, PieceType.GUARD.createStrategy())
        );
    }

    private static void setUpGeneral(Map<Position, Piece> initialBoard, Camp camp, int generalY) {
        initialBoard.put(new Position(GENERAL_X_COORDINATE, generalY),
                new Piece(camp, PieceType.GENERAL, PieceType.GENERAL.createStrategy()));
    }

    private static void setUpElephantAndHorse(Map<Position, Piece> initialBoard, Camp camp, int input) {
        List<PieceType> pieceTypes = SetUp.from(input);
        pieceTypes = arrangeByCamp(camp, pieceTypes);

        int y = resolveY(camp, HAN_OTHER_PIECES_Y_COORDINATE);

        for (int i = 0; i < ELEPHANT_SETUP_POSITION.size(); i++) {
            initialBoard.put(
                    new Position(ELEPHANT_SETUP_POSITION.get(i), y),
                    new Piece(camp, pieceTypes.get(i), pieceTypes.get(i).createStrategy())
            );
        }
    }

    private static int resolveY(Camp camp, int defaultHanY) {
        if (camp == Camp.CHO) {
            return TOTAL_Y_COORDINATE - defaultHanY;
        }

        return defaultHanY;
    }

    private static List<PieceType> arrangeByCamp(Camp camp, List<PieceType> pieceTypes) {
        if (camp == Camp.HAN) {
            return pieceTypes.reversed();
        }

        return pieceTypes;
    }
}
