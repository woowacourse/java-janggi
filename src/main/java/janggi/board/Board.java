package janggi.board;

import janggi.exception.ErrorException;
import janggi.piece.Camp;
import janggi.piece.Empty;
import janggi.piece.Piece;
import janggi.position.Movement;
import janggi.position.Position;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Board {

    public static final int COLUMN = 9;
    public static final int ROW = 10;
    private static final Map<Camp, Position> PALACE_POSITIONS = Map.of(
            Camp.CHO, new Position(4, 1),
            Camp.HAN, new Position(4, 8)
    );

    private final Map<Position, Piece> cells;
    private Camp currentCamp;

    public Board(Map<Position, Piece> cells, Camp currentCamp) {
        this.cells = cells;
        this.currentCamp = currentCamp;
    }

    public void placePiece(Position position, Piece piece) {
        validatePosition(position);
        cells.put(position, piece);
    }

    private void validatePosition(Position position) {
        if (position.x() < 0 || COLUMN <= position.x() || position.y() < 0 || ROW <= position.y()) {
            throw new ErrorException("기물은 9x10 크기의 보드 내에서만 이동 가능합니다.");
        }
    }

    public void move(Movement movement) {
        validateBorder(movement);
        validateTurn(movement);
        validateMove(movement);
        updateBoard(movement);
        currentCamp = currentCamp.switchTurn();
    }

    private void validateBorder(Movement movement) {
        Position origin = movement.origin();
        Position target = movement.target();
        validatePosition(origin);
        validatePosition(target);
    }

    private void validateTurn(Movement movement) {
        Piece originPiece = getOriginPiece(movement);
        if (originPiece.isOppositeCampTo(currentCamp)) {
            throw new ErrorException("현재 턴에 해당하지 않는 진영의 기물을 선택할 수 없습니다.");
        }
    }

    private void validateMove(Movement movement) {
        Piece originPiece = getOriginPiece(movement);
        Piece targetPiece = getTargetPiece(movement);
        originPiece.validateCatch(targetPiece);
        originPiece.validateMove(movement);

    }

    private void updateBoard(Movement movement) {
        cells.put(movement.target(), getOriginPiece(movement));
        cells.put(movement.origin(), Empty.INSTANCE);
    }

    private Piece getOriginPiece(Movement movement) {
        Position position = movement.origin();
        Piece piece = getPieceByPosition(position);
        if (piece.isEmpty()) {
            throw new ErrorException("해당 위치에 기물이 존재하지 않습니다.");
        }
        return piece;
    }

    private Piece getTargetPiece(Movement movement) {
        Position position = movement.target();
        return getPieceByPosition(position);
    }

    private Piece getPieceByPosition(Position position) {
        return cells.get(position);
    }

    public void validateCampPalace(Position piecePosition, Camp baseCamp) {
        Position palaceCenter = PALACE_POSITIONS.get(baseCamp);
        List<Position> surroundingPositions = findPalacePositions(palaceCenter.x(), palaceCenter.y());
        if (!surroundingPositions.contains(piecePosition)) {
            throw new ErrorException("궁성 안에서 이동해야 합니다.");
        }
    }

    public Set<Piece> getPiecesByPosition(Set<Position> route) {
        Set<Piece> pieces = new HashSet<>();
        for (Position position : route) {
            Piece piece = cells.get(position);
            if (!piece.isEmpty()) {
                pieces.add(piece);
            }
        }
        return pieces;
    }

    public Map<Position, Piece> getCells() {
        return cells;
    }

    private List<Position> findPalacePositions(int centerX, int centerY) {
        List<Integer> directions = List.of(-1, 0, 1);
        return directions.stream()
                .flatMap(dx -> directions.stream().map(dy -> new Position(centerX + dx, centerY + dy)))
                .toList();
    }
}
