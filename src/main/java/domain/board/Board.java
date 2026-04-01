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
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public Piece move(Position source, Position destination) {
        validateSource(source);
        validateMovement(source, destination);
        return executeMove(source, destination);
    }

    private Piece executeMove(Position source, Position destination) {
        Piece movePiece = findPiece(source);
        Piece destinationPiece = findPiece(destination);

        board.put(source, new None());
        board.put(destination, movePiece);
        return destinationPiece;
    }

    public List<Position> findMovablePositions(Position source) {
        validateSource(source);
        List<Position> movablePositions = new ArrayList<>();

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
        try {
            Piece piece = findPiece(source);
            Path path = piece.calculatePath(source, destination);
            PathPieces pathPieces = createPathPieces(path);
            return piece.validatePath(pathPieces);
        } catch (JanggiException exception) {
            return false;
        }
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

    private Piece findPiece(Position position) {
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
}
