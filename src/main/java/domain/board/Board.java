package domain.board;

import static common.Constants.MAX_COLUMN;
import static common.Constants.MAX_ROW;
import static common.Constants.MIN_COLUMN;
import static common.Constants.MIN_ROW;
import static common.exception.ErrorMessage.EMPTY_SOURCE_POSITION;
import static common.exception.ErrorMessage.INVALID_PIECE_MOVEMENT;

import common.exception.JanggiException;
import domain.piece.None;
import domain.piece.Piece;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public void move(Position source, Position destination) {
        validateSource(source);
        validateMovement(source, destination);
        executeMove(source, destination);
    }

    private Piece executeMove(Position source, Position destination) {
        Piece movePiece = findPiece(source);
        Piece destinationPiece = findPiece(destination);

        board.put(source, new None());
        board.put(destination, movePiece);
        return destinationPiece;
    }

    public Set<Position> findMovablePositions(Position source) {
        validateSource(source);
        Set<Position> movablePositions = new HashSet<>();

        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                Position destination = new Position(row, column);
                if (canMove(source, destination)) {
                    movablePositions.add(destination);
                }
            }
        }
        return movablePositions;
    }

    public boolean canMove(Position source, Position destination) {
        validateSource(source);
        Piece piece = findPiece(source);
        if (!piece.isPathPossible(source, destination)) {
            return false;
        }
        Path path = piece.calculatePath(source, destination);
        PathPieces pathPieces = createPathPieces(path);
        return piece.isValidPath(pathPieces);
    }

    private void validateSource(Position source) {
        Piece sourcePiece = findPiece(source);
        if (sourcePiece.isNone()) {
            throw new JanggiException(EMPTY_SOURCE_POSITION.getMessage());
        }
    }

    public boolean isCho(Position position) {
        return findPiece(position).isCho();
    }

    public boolean isHan(Position position) {
        return findPiece(position).isHan();
    }

    public Map<Position, Piece> getBoardMap() {
        return Collections.unmodifiableMap(board);
    }

    public Piece findPiece(Position position) {
        return board.get(position);
    }

    private void validateMovement(Position source, Position destination) {
        if (!canMove(source, destination)) {
            throw new JanggiException(INVALID_PIECE_MOVEMENT.formatted(source, destination));
        }
    }

    private PathPieces createPathPieces(Path path) {
        List<Position> wayPoints = path.waypoints();
        List<Piece> pieces = new ArrayList<>();

        for (Position point : wayPoints) {
            Piece pointPiece = findPiece(point);
            addPieceInPath(pointPiece, pieces);
        }

        return new PathPieces(findPiece(path.source()), pieces, findPiece(path.destination()));
    }

    private void addPieceInPath(Piece piece, List<Piece> pieces) {
        if (!piece.isNone()) {
            pieces.add(piece);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Board other = (Board) o;
        return Objects.equals(board, other.board);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(board);
    }
}
