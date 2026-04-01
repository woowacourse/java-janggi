package janggi.domain.board;

import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.game.Side;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private static final String ERROR_NOT_FOUND_PIECE = "[ERROR] 해당 위치에 기물이 없습니다.";
    private static final String ERROR_NOT_OWNED_PIECE = "[ERROR] 상대방의 기물은 선택할 수 없습니다.";
    private static final String ERROR_NO_MOVABLE_PATH = "[ERROR] 이동할 수 있는 경로가 없는 기물입니다.";

    // 기물별 열(Column) 위치
    private static final int CHARIOT_LEFT = 0;
    private static final int CHARIOT_RIGHT = 8;
    private static final int ELEPHANT_LEFT = 1;
    private static final int ELEPHANT_RIGHT = 7;
    private static final int HORSE_LEFT = 2;
    private static final int HORSE_RIGHT = 6;
    private static final int GUARD_LEFT = 3;
    private static final int GUARD_RIGHT = 5;
    private static final int PALACE_COL = 4;

    // 진영별 행(Row) 위치
    // 한(HAN) 진영
    private static final int HAN_BASE_ROW = 0;
    private static final int HAN_PALACE_ROW = 1;
    private static final int HAN_CANNON_ROW = 2;
    private static final int HAN_SOLDIER_ROW = 3;

    // 초(CHO) 진영
    private static final int CHO_BASE_ROW = 9;
    private static final int CHO_PALACE_ROW = 8;
    private static final int CHO_CANNON_ROW = 7;
    private static final int CHO_SOLDIER_ROW = 6;

    // 배치 규칙 관련
    private static final int SOLDIER_TOTAL_COUNT = 5;
    private static final int SOLDIER_INTERVAL = 2;
    private static final String ID_FIRST = "0";
    private static final String ID_SECOND = "1";

    private final Map<Position, Piece> piecePosition;

    private Board(Map<Position, Piece> piecePosition) {
        this.piecePosition = new HashMap<>(piecePosition);
    }

    public static Board initialize() {
        Map<Position, Piece> initialBoard = new HashMap<>();

        // 한나라 초기화
        initSide(initialBoard, Side.HAN,
                HAN_BASE_ROW, HAN_PALACE_ROW,
                HAN_CANNON_ROW, HAN_SOLDIER_ROW,
                PieceType.SOLDIER);

        // 초나라 초기화
        initSide(initialBoard, Side.CHO,
                CHO_BASE_ROW, CHO_PALACE_ROW,
                CHO_CANNON_ROW, CHO_SOLDIER_ROW,
                PieceType.SOLDIER);

        return new Board(initialBoard);
    }

    private static void initSide(Map<Position, Piece> initialBoard, Side side, int bR, int gR, int pR, int sR, PieceType st) {
        initMajorPieces(initialBoard, side, bR);
        initPalaceAndCannons(initialBoard, side, gR, pR);
        initSoldiers(initialBoard, side, sR, st);
    }

    private static void initMajorPieces(Map<Position, Piece> initialBoard, Side side, int r) {
        put(initialBoard, r, CHARIOT_LEFT, side, PieceType.CHARIOT, ID_FIRST);
        put(initialBoard, r, CHARIOT_RIGHT, side, PieceType.CHARIOT, ID_SECOND);

        put(initialBoard, r, ELEPHANT_LEFT, side, PieceType.ELEPHANT, ID_FIRST);
        put(initialBoard, r, ELEPHANT_RIGHT, side, PieceType.ELEPHANT, ID_SECOND);

        put(initialBoard, r, HORSE_LEFT, side, PieceType.HORSE, ID_FIRST);
        put(initialBoard, r, HORSE_RIGHT, side, PieceType.HORSE, ID_SECOND);

        put(initialBoard, r, GUARD_LEFT, side, PieceType.GUARD, ID_FIRST);
        put(initialBoard, r, GUARD_RIGHT, side, PieceType.GUARD, ID_SECOND);
    }

    private static void initPalaceAndCannons(Map<Position, Piece> initialBoard, Side side, int gR, int pR) {
        put(initialBoard, gR, PALACE_COL, side, PieceType.PALACE, ID_FIRST);
        put(initialBoard, pR, ELEPHANT_LEFT, side, PieceType.CANNON, ID_FIRST);
        put(initialBoard, pR, ELEPHANT_RIGHT, side, PieceType.CANNON, ID_SECOND);
    }

    private static void initSoldiers(Map<Position, Piece> initialBoard, Side side, int r, PieceType type) {
        for (int i = 0; i < SOLDIER_TOTAL_COUNT; i++) {
            put(initialBoard, r, i * SOLDIER_INTERVAL, side, type, String.valueOf(i));
        }
    }

    private static void put(Map<Position, Piece> initialBoard, int r, int c, Side s, PieceType t, String n) {
        initialBoard.put(new Position(r, c), new Piece(s, t, n));
    }

    public Map<Position, Piece> getPiecePosition() {
        return Map.copyOf(this.piecePosition);
    }

    public List<Position> calculateDestinations(Position currentPosition, Side currentSide) {
        validateSelectablePiece(currentPosition, currentSide);
        List<Position> destinations = getMoveablePositions(currentPosition);
        validateDestinationsExist(destinations);
        return destinations;
    }

    private void validateSelectablePiece(Position position, Side currentTurn) {
        validatePieceExist(position);
        validateOwnPiece(position, currentTurn);
    }

    private void validatePieceExist(Position position) {
        if (piecePosition.get(position) == null) {
            throw new IllegalArgumentException(ERROR_NOT_FOUND_PIECE);
        }
    }

    private void validateOwnPiece(Position position, Side currentTurn) {
        Piece piece = piecePosition.get(position);
        if (piece.getSide() != currentTurn) {
            throw new IllegalArgumentException(ERROR_NOT_OWNED_PIECE);
        }
    }

    private void validateDestinationsExist(List<Position> destinations) {
        if (destinations.isEmpty()) {
            throw new IllegalArgumentException(ERROR_NO_MOVABLE_PATH);
        }
    }

    private List<Position> getMoveablePositions(Position currentPosition) {
        Piece piece = piecePosition.get(currentPosition);
        Paths paths = piece.calculatePaths(currentPosition);
        Map<Position, Piece> boardState = generateStateByPaths(paths);
        return piece.determineDestinations(paths, boardState);
    }

    private Map<Position, Piece> generateStateByPaths(Paths moveablePaths) {
        Map<Position, Piece> boardState = new HashMap<>();
        moveablePaths.forEach(path -> {
            generateStateByPath(path, boardState);
        });
        return boardState;
    }

    private void generateStateByPath(Path path, Map<Position, Piece> boardState) {
        path.forEach(position -> {
            Piece piece = piecePosition.get(position);
            if (piece != null) {
                boardState.put(position, piece);
            }
        });
    }

    public void movePiece(Position selected, Position target) {
        Piece movingPiece = piecePosition.remove(selected);
        piecePosition.put(target, movingPiece);
    }
}
