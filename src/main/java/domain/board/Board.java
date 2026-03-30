package domain.board;

import common.exception.JanggiException;
import domain.piece.None;
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

    public Piece move(Position source, Position destination) {
        validateMovement(source, destination);
        Piece movePiece = findPiece(source);
        Piece destinationPiece = findPiece(destination);

        board.put(source, new None());
        board.put(destination, movePiece);

        return destinationPiece;
    }

    public Piece findPiece(Position position) {
        return board.get(position);
    }

    private void validateMovement(Position source, Position destination) {
        Piece piece = findPiece(source);
        Path path = piece.calculatePath(source, destination);
        PathPieces pathPieces = createPathPieces(path);
        if (!piece.validatePath(pathPieces)) {
            throw new JanggiException("기물을 이동할 수 없습니다.");
        }
    }

    public Team getTeamAt(Position position) {
        Piece piece = findPiece(position);

        if (!piece.isNotNone()) {
            throw new JanggiException("비어있는 곳입니다.");
        }

        return piece.getTeam();
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
        if (piece.isNotNone()) {
            pieces.add(piece);
        }
    }
}
