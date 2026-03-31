package domain.board;

import common.exception.JanggiException;
import domain.piece.Piece;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public void move(Position source, Position destination, Team currentTurnTeam) {
        validateSource(source, currentTurnTeam);
        Piece movePiece = findPiece(source);

        validateMovement(movePiece, source, destination);

        board.remove(source);
        board.put(destination, movePiece);
    }

    public boolean hasPiece(Position position) {
        return board.containsKey(position);
    }

    public Piece findPiece(Position position) {
        if (!hasPiece(position)) {
            throw new JanggiException("비어있는 곳입니다.");
        }
        return board.get(position);
    }

    public void validateSource(Position source, Team currentTurnTeam) {
        if (!hasPiece(source)) {
            throw new JanggiException("비어있는 곳입니다.");
        }
        Piece piece = findPiece(source);
        if (piece.isDifferentTeam(currentTurnTeam)) {
            throw new JanggiException("자신의 기물이 아닙니다.");
        }
    }

    private void validateMovement(Piece piece, Position source, Position destination) {
        Path path = piece.calculatePath(source, destination);
        PathPieces pathPieces = createPathPieces(piece, path);
        if (!piece.validatePath(pathPieces)) {
            throw new JanggiException("기물을 이동할 수 없습니다.");
        }
    }

    private PathPieces createPathPieces(Piece sourcePiece, Path path) {
        List<Piece> pieces = path.waypoints().stream()
                .filter(this::hasPiece)
                .map(this::findPiece)
                .toList();

        if (hasPiece(path.destination())) {
            return new PathPieces(sourcePiece, pieces, findPiece(path.destination()));
        }
        return new PathPieces(sourcePiece, pieces);
    }
}
