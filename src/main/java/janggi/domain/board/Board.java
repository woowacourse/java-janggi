package janggi.domain.board;

import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.game.Side;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    // 점수 계산
    private static final double HAN_BONUS_SCORE = 1.5;

    private final Map<Position, Piece> piecePosition;

    private Board(Map<Position, Piece> piecePosition) {
        this.piecePosition = new HashMap<>(piecePosition);
    }

    public static Board from(Map<Position, Piece> piecePosition) {
        return new Board(piecePosition);
    }

    public static Board initialize() {
        Map<Position, Piece> initialBoard = new HashMap<>();

        // 한나라 초기화
        initSide(initialBoard, Side.HAN,
                HAN_BASE_ROW, HAN_PALACE_ROW,
                HAN_CANNON_ROW, HAN_SOLDIER_ROW
        );

        // 초나라 초기화
        initSide(initialBoard, Side.CHO,
                CHO_BASE_ROW, CHO_PALACE_ROW,
                CHO_CANNON_ROW, CHO_SOLDIER_ROW
        );

        return new Board(initialBoard);
    }

    private static void initSide(Map<Position, Piece> initialBoard, Side side, int baseRow, int palaceRow,
                                 int cannonRow, int soldierRow) {
        initMajorPieces(initialBoard, side, baseRow);
        initPalaceAndCannons(initialBoard, side, palaceRow, cannonRow);
        initSoldiers(initialBoard, side, soldierRow);
    }

    private static void initMajorPieces(Map<Position, Piece> initialBoard, Side side, int row) {
        put(initialBoard, row, CHARIOT_LEFT, side, PieceType.CHARIOT, ID_FIRST);
        put(initialBoard, row, CHARIOT_RIGHT, side, PieceType.CHARIOT, ID_SECOND);

        put(initialBoard, row, ELEPHANT_LEFT, side, PieceType.ELEPHANT, ID_FIRST);
        put(initialBoard, row, ELEPHANT_RIGHT, side, PieceType.ELEPHANT, ID_SECOND);

        put(initialBoard, row, HORSE_LEFT, side, PieceType.HORSE, ID_FIRST);
        put(initialBoard, row, HORSE_RIGHT, side, PieceType.HORSE, ID_SECOND);

        put(initialBoard, row, GUARD_LEFT, side, PieceType.GUARD, ID_FIRST);
        put(initialBoard, row, GUARD_RIGHT, side, PieceType.GUARD, ID_SECOND);
    }

    private static void initPalaceAndCannons(Map<Position, Piece> initialBoard, Side side, int palaceRow, int cannonRow) {
        put(initialBoard, palaceRow, PALACE_COL, side, PieceType.GENERAL, ID_FIRST);
        put(initialBoard, cannonRow, ELEPHANT_LEFT, side, PieceType.CANNON, ID_FIRST);
        put(initialBoard, cannonRow, ELEPHANT_RIGHT, side, PieceType.CANNON, ID_SECOND);
    }

    private static void initSoldiers(Map<Position, Piece> initialBoard, Side side, int row) {
        for (int i = 0; i < SOLDIER_TOTAL_COUNT; i++) {
            put(initialBoard, row, i * SOLDIER_INTERVAL, side, PieceType.SOLDIER, String.valueOf(i));
        }
    }

    private static void put(Map<Position, Piece> initialBoard, int row, int column, Side side, PieceType pieceType,
                            String pieceNumber) {
        initialBoard.put(new Position(row, column), new Piece(side, pieceType, pieceNumber));
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
        if (pieceAt(position).isEmpty()) {
            throw new IllegalArgumentException(ERROR_NOT_FOUND_PIECE);
        }
    }

    private Piece pieceAt(Position position) {
        return piecePosition.getOrDefault(position, Piece.createEmpty());
    }

    private void validateOwnPiece(Position position, Side currentTurn) {
        Piece piece = piecePosition.get(position);
        if (!piece.isOwnedBy(currentTurn)) {
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
            Piece piece = pieceAt(position);
            if (!piece.isEmpty()) {
                boardState.put(position, piece);
            }
        });
    }

    public void movePiece(Position selected, Position target) {
        validatePieceExist(selected);
        Piece movingPiece = piecePosition.remove(selected);
        piecePosition.put(target, movingPiece);
    }

    public boolean isGameOver() {
        return !hasGeneral(Side.CHO) || !hasGeneral(Side.HAN);
    }

    private boolean hasGeneral(Side side) {
        return piecePosition.values().stream()
                .anyMatch(piece -> piece.isOwnedBy(side) && piece.isGeneral());
    }

    public double calculateScore(Side side) {
        double totalScore = piecePosition.values().stream()
                .filter(piece -> piece.isOwnedBy(side))
                .mapToDouble(Piece::getScore)
                .sum();

        if (side == Side.HAN) {
            totalScore += HAN_BONUS_SCORE;
        }

        return totalScore;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Board board = (Board) o;
        return Objects.equals(piecePosition, board.piecePosition);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(piecePosition);
    }
}
