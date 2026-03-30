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
    private static final List<Integer> ELEPHANT_SETUP_POSITION = List.of(2, 3, 7, 8);

    private static final int LEFT_CANNON_X_COORDINATE = 2;
    private static final int RIGHT_CANNON_X_COORDINATE = 8;
    private static final int LEFT_CHARIOT_X_COORDINATE = 1;
    private static final int RIGHT_CHARIOT_X_COORDINATE = 9;
    private static final int LEFT_GUARD_X_COORDINATE = 4;
    private static final int RIGHT_GUARD_X_COORDINATE = 6;
    private static final int GENERAL_X_COORDINATE = 5;

    public static Map<Position, Piece> init(SetUp choSetUp, SetUp hanSetUp) {
        Map<Position, Piece> initialBoard = new HashMap<>();

        setUpElephantAndHorse(initialBoard, Camp.CHO, choSetUp);
        setUpElephantAndHorse(initialBoard, Camp.HAN, hanSetUp);
        setUpOtherPieces(initialBoard);

        return initialBoard;
    }

    private static void setUpOtherPieces(Map<Position, Piece> initialBoard) {
        setUpCampOtherPieces(initialBoard, Camp.HAN);
        setUpCampOtherPieces(initialBoard, Camp.CHO);
    }

    private static void setUpCampOtherPieces(Map<Position, Piece> initialBoard, Camp camp) {
        int otherY = camp.resolveY(HAN_OTHER_PIECES_Y_COORDINATE);
        int cannonY = camp.resolveY(HAN_CANNON_Y_COORDINATE);
        int generalY = camp.resolveY(HAN_GENERAL_Y_COORDINATE);
        int soldierY = camp.resolveY(HAN_SOLDIERS_Y_COORDINATE);

        setUpSoldier(initialBoard, camp, soldierY);
        setUpCannon(initialBoard, camp, cannonY);
        setUpChariot(initialBoard, camp, otherY);
        setUpGuard(initialBoard, camp, otherY);
        setUpGeneral(initialBoard, camp, generalY);
    }

    private static void setUpSoldier(Map<Position, Piece> initialBoard, Camp camp, int soldierY) {
        initialBoard.put(
                new Position(1, soldierY),
                new Piece(camp, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy(camp))
        );
        initialBoard.put(
                new Position(3, soldierY),
                new Piece(camp, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy(camp))
        );
        initialBoard.put(
                new Position(5, soldierY),
                new Piece(camp, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy(camp))
        );
        initialBoard.put(
                new Position(7, soldierY),
                new Piece(camp, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy(camp))
        );
        initialBoard.put(
                new Position(9, soldierY),
                new Piece(camp, PieceType.SOLDIER, PieceType.SOLDIER.createStrategy(camp))
        );
    }

    private static void setUpCannon(Map<Position, Piece> initialBoard, Camp camp, int cannonY) {
        initialBoard.put(
                new Position(LEFT_CANNON_X_COORDINATE, cannonY),
                new Piece(camp, PieceType.CANNON, PieceType.CANNON.createStrategy(camp))
        );

        initialBoard.put(
                new Position(RIGHT_CANNON_X_COORDINATE, cannonY),
                new Piece(camp, PieceType.CANNON, PieceType.CANNON.createStrategy(camp))
        );
    }

    private static void setUpChariot(Map<Position, Piece> initialBoard, Camp camp, int otherY) {
        initialBoard.put(
                new Position(LEFT_CHARIOT_X_COORDINATE, otherY),
                new Piece(camp, PieceType.CHARIOT, PieceType.CHARIOT.createStrategy(camp))
        );

        initialBoard.put(
                new Position(RIGHT_CHARIOT_X_COORDINATE, otherY),
                new Piece(camp, PieceType.CHARIOT, PieceType.CHARIOT.createStrategy(camp))
        );
    }

    private static void setUpGuard(Map<Position, Piece> initialBoard, Camp camp, int otherY) {
        initialBoard.put(
                new Position(LEFT_GUARD_X_COORDINATE, otherY),
                new Piece(camp, PieceType.GUARD, PieceType.GUARD.createStrategy(camp))
        );

        initialBoard.put(
                new Position(RIGHT_GUARD_X_COORDINATE, otherY),
                new Piece(camp, PieceType.GUARD, PieceType.GUARD.createStrategy(camp))
        );
    }

    private static void setUpGeneral(Map<Position, Piece> initialBoard, Camp camp, int generalY) {
        initialBoard.put(new Position(GENERAL_X_COORDINATE, generalY),
                new Piece(camp, PieceType.GENERAL, PieceType.GENERAL.createStrategy(camp)));
    }

    private static void setUpElephantAndHorse(Map<Position, Piece> initialBoard, Camp camp, SetUp setUp) {
        List<PieceType> pieceTypes = camp.arrange(setUp.placeOrder());

        int y = camp.resolveY(HAN_OTHER_PIECES_Y_COORDINATE);

        for (int i = 0; i < ELEPHANT_SETUP_POSITION.size(); i++) {
            initialBoard.put(
                    new Position(ELEPHANT_SETUP_POSITION.get(i), y),
                    new Piece(camp, pieceTypes.get(i), pieceTypes.get(i).createStrategy(camp))
            );
        }
    }
}
