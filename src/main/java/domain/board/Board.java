package domain.board;

import common.exception.JanggiException;
import domain.piece.Piece;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public void move(Position source, Position destination, Team currentTurnTeam) {
        if (!hasPiece(source)) {
            throw new JanggiException("비어있는 곳입니다.");
        }
        Piece movePiece = findPiece(source);
        validateTurnTeam(movePiece, currentTurnTeam);
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

    private void validateMovement(Piece piece, Position source, Position destination) {
        Path path = piece.calculatePath(source, destination);
        PathPieces pathPieces = createPathPieces(piece, path);
        if (!piece.validatePath(pathPieces)) {
            throw new JanggiException("기물을 이동할 수 없습니다.");
        }
    }

    private void validateTurnTeam(Piece piece, Team currentTurnTeam) {
        if (piece.isDifferentTeam(currentTurnTeam)) {
            throw new JanggiException("다른 팀입니다.");
        }
    }

    private PathPieces createPathPieces(Piece sourcePiece, Path path) {
        List<Position> wayPoints = path.waypoints();
        List<Piece> pieces = new ArrayList<>();

        for (Position point : wayPoints) {
            if (hasPiece(point)) {
                pieces.add(findPiece(point));
            }
        }

        if (hasPiece(path.destination())) {
            return new PathPieces(sourcePiece, pieces, findPiece(path.destination()));
        }
        return new PathPieces(sourcePiece, pieces);
    }
}
