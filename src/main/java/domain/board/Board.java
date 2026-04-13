package domain.board;

import common.JanggiException;
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
import java.util.Optional;
import java.util.Set;

public class Board {
    public static final int MIN_ROW = 0;
    public static final int MAX_ROW = 9;
    public static final int MIN_COLUMN = 0;
    public static final int MAX_COLUMN = 8;
    private static final String EMPTY_SOURCE_POSITION = "선택한 위치에 기물이 없습니다.";
    private static final String INVALID_MOVEMENT = "이동할 수 없습니다.";

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public void move(Position source, Position destination) {
        validateSource(source);
        validateMovement(source, destination);
        executeMove(source, destination);
    }

    private void executeMove(Position source, Position destination) {
        Piece movePiece = findPiece(source);

        board.put(source, new None());
        board.put(destination, movePiece);
    }

    public Set<Position> findMovablePositions(Position source) {
        validateSource(source);
        Piece sourcePiece = findPiece(source);
        Set<Position> movablePositions = new HashSet<>();

        for (Position destination : sourcePiece.findCandidateDestinations(source)) {
            if (canMove(source, destination)) {
                movablePositions.add(destination);
            }
        }
        return movablePositions;
    }

    public boolean canMove(Position source, Position destination) {
        validateSource(source);
        Piece piece = findPiece(source);
        Optional<Path> optionalPath = piece.calculatePath(source, destination);
        if (optionalPath.isEmpty()) {
            return false;
        }
        Path path = optionalPath.get();
        PathPieces pathPieces = createPathPieces(path);
        return piece.isValidPath(path, pathPieces);
    }

    private void validateSource(Position source) {
        Piece sourcePiece = findPiece(source);
        if (sourcePiece.isNone()) {
            throw new JanggiException(EMPTY_SOURCE_POSITION);
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
            throw new JanggiException(INVALID_MOVEMENT);
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


    public List<Piece> getRemainPieces() {
        return board.values().stream()
                .toList();
    }
}
