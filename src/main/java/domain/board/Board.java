package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;
import domain.piece.Team;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Board {

    public static final int MIN_COLUMN_RANGE = 1;
    public static final int MAX_COLUMN_RANGE = 10;
    public static final int MIN_ROW_RANGE = 1;
    public static final int MAX_ROW_RANGE = 9;

    private final Map<Position, Piece> pieces;

    private Board(final Map<Position, Piece> pieces) {
        this.pieces = pieces;
    }

    public static Board init(final ElephantSetup choSetup, final ElephantSetup hanSetup) {
        Map<Position, Piece> pieces = new HashMap<>();

        for (PieceType type : PieceType.values()) {
            initializeChoPieces(type, pieces);
            initializeHanPieces(type, pieces);
        }

        setupChoElephant(choSetup, pieces);
        setupHanElephant(hanSetup, pieces);

        return new Board(pieces);
    }


    private static void initializeChoPieces(final PieceType type, final Map<Position, Piece> pieces) {
        for (Position pos : type.getInitPositions()) {
            Position mirrored = mirror(pos);
            pieces.put(mirrored, Piece.choPieceOf(type));
        }
    }

    private static void initializeHanPieces(final PieceType type, final Map<Position, Piece> pieces) {
        for (Position pos : type.getInitPositions()) {
            pieces.put(pos, Piece.hanPieceOf(type));
        }
    }


    private static void setupChoElephant(final ElephantSetup choSetup, final Map<Position, Piece> pieces) {
        for (final Entry<Position, PieceType> entry : choSetup.getPiecePositions().entrySet()) {
            pieces.put(mirror(entry.getKey()), Piece.choPieceOf(entry.getValue()));
        }
    }

    private static void setupHanElephant(final ElephantSetup hanSetup, final Map<Position, Piece> pieces) {
        for (final Entry<Position, PieceType> entry : hanSetup.getPiecePositions().entrySet()) {
            pieces.put(entry.getKey(), Piece.hanPieceOf(entry.getValue()));
        }
    }


    private static Position mirror(Position pos) {
        int mirroredColumn = MAX_COLUMN_RANGE - pos.column() + 1;
        int mirroredRow = MAX_ROW_RANGE - pos.row() + 1;
        return Position.of(mirroredColumn, mirroredRow);
    }

    public List<Position> getMovablePositions(Position position) {
        Piece piece = pieces.get(position);
        if (piece == null) {
            return List.of();
        }
        return piece.calculateMovablePositions(position, pieces);
    }

    public void move(Position from, Position to, Team currentTeam) {
        Piece piece = pieces.get(from);
        if (piece == null || piece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("해당 위치에 움직일 수 있는 기물이 없습니다.");
        }

        List<Position> movablePositions = getMovablePositions(from);
        if (!movablePositions.contains(to)) {
            throw new IllegalArgumentException("올바르지 않은 이동입니다.");
        }

        pieces.remove(from);
        pieces.put(to, piece);
    }

    public Map<Position, Piece> getPieces() {
        return pieces;
    }

    public Piece getPieceAt(final Position position) {
        return pieces.get(position);
    }

    public Map<Position, List<Position>> getMoveOptionsFor(final Team team) {
        Map<Position, List<Position>> moveOptions = new HashMap<>();

        for (Entry<Position, Piece> entry : pieces.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();

            if (!piece.isSameTeam(team)) {
                continue;
            }

            List<Position> movablePositions = getMovablePositions(position);
            if (movablePositions.isEmpty()) {
                continue;
            }

            moveOptions.put(position, movablePositions);
        }

        return moveOptions;
    }
}
