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
    private final Map<Position, Piece> piecePosition;

    private Board(Map<Position, Piece> piecePosition) {
        this.piecePosition = new HashMap<>(piecePosition);
    }

    public static Board initialize() {
        Map<Position, Piece> initialBoard = new HashMap<>();

        // 한나라 초기화
        initSide(initialBoard, Side.HAN,
                BoardLayout.HAN_BASE_ROW, BoardLayout.HAN_PALACE_ROW,
                BoardLayout.HAN_CANNON_ROW, BoardLayout.HAN_SOLDIER_ROW,
                PieceType.HAN_SOLDIER);

        // 초나라 초기화
        initSide(initialBoard, Side.CHO,
                BoardLayout.CHO_BASE_ROW, BoardLayout.CHO_PALACE_ROW,
                BoardLayout.CHO_CANNON_ROW, BoardLayout.CHO_SOLDIER_ROW,
                PieceType.CHO_SOLDIER);

        return new Board(initialBoard);
    }

    private static void initSide(Map<Position, Piece> initialBoard, Side side, int bR, int gR, int pR, int sR, PieceType st) {
        initMajorPieces(initialBoard, side, bR);
        initPalaceAndCannons(initialBoard, side, gR, pR);
        initSoldiers(initialBoard, side, sR, st);
    }

    private static void initMajorPieces(Map<Position, Piece> initialBoard, Side side, int r) {
        put(initialBoard, r, BoardLayout.CHARIOT_LEFT, side, PieceType.CHARIOT, BoardLayout.ID_FIRST);
        put(initialBoard, r, BoardLayout.CHARIOT_RIGHT, side, PieceType.CHARIOT, BoardLayout.ID_SECOND);

        put(initialBoard, r, BoardLayout.ELEPHANT_LEFT, side, PieceType.ELEPHANT, BoardLayout.ID_FIRST);
        put(initialBoard, r, BoardLayout.ELEPHANT_RIGHT, side, PieceType.ELEPHANT, BoardLayout.ID_SECOND);

        put(initialBoard, r, BoardLayout.HORSE_LEFT, side, PieceType.HORSE, BoardLayout.ID_FIRST);
        put(initialBoard, r, BoardLayout.HORSE_RIGHT, side, PieceType.HORSE, BoardLayout.ID_SECOND);

        put(initialBoard, r, BoardLayout.GUARD_LEFT, side, PieceType.GUARD, BoardLayout.ID_FIRST);
        put(initialBoard, r, BoardLayout.GUARD_RIGHT, side, PieceType.GUARD, BoardLayout.ID_SECOND);
    }

    private static void initPalaceAndCannons(Map<Position, Piece> initialBoard, Side side, int gR, int pR) {
        put(initialBoard, gR, BoardLayout.PALACE_COL, side, PieceType.PALACE, BoardLayout.ID_FIRST);
        put(initialBoard, pR, BoardLayout.ELEPHANT_LEFT, side, PieceType.CANNON, BoardLayout.ID_FIRST);
        put(initialBoard, pR, BoardLayout.ELEPHANT_RIGHT, side, PieceType.CANNON, BoardLayout.ID_SECOND);
    }

    private static void initSoldiers(Map<Position, Piece> initialBoard, Side side, int r, PieceType type) {
        for (int i = 0; i < BoardLayout.SOLDIER_TOTAL_COUNT; i++) {
            put(initialBoard, r, i * BoardLayout.SOLDIER_INTERVAL, side, type, String.valueOf(i));
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
            throw new IllegalArgumentException("[ERROR] 해당 위치에 기물이 없습니다.");
        }
    }

    private void validateOwnPiece(Position position, Side currentTurn) {
        Piece piece = piecePosition.get(position);
        if (piece.getSide() != currentTurn) {
            throw new IllegalArgumentException("[ERROR] 상대방의 기물은 선택할 수 없습니다.");
        }
    }

    private void validateDestinationsExist(List<Position> destinations) {
        if (destinations.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 이동할 수 있는 경로가 없는 기물입니다.");
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
