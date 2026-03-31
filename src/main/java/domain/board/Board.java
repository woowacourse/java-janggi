package domain.board;

import static common.exception.ErrorMessage.INVALID_PIECE_MOVEMENT;

import common.exception.JanggiException;
import domain.piece.None;
import domain.piece.Piece;
import domain.player.Team;
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
        validateMovement(source, destination);
        Piece movePiece = findPiece(source);
        Piece destinationPiece = findPiece(destination);

        board.put(source, new None());
        board.put(destination, movePiece);

        return destinationPiece;
    }

    public boolean isPieceDifferentTeam(Position source, Team team) {
        return findPiece(source).isDifferentTeam(team);
    }

    public boolean isCho(Position position) {
        return findPiece(position).isCho();
    }

    public boolean isHan(Position position) {
        return findPiece(position).isHan();
    }

    public boolean isNonePiece(Position source) {
        return findPiece(source).isNone();
    }

    public Map<Position, Piece> getBoardMap() {
        return Collections.unmodifiableMap(board);
    }

    private Piece findPiece(Position position) {
        return board.get(position);
    }

    private void validateMovement(Position source, Position destination) {
        Piece piece = findPiece(source);
        Path path = piece.calculatePath(source, destination);
        PathPieces pathPieces = createPathPieces(path);
        if (!piece.validatePath(pathPieces)) {
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
