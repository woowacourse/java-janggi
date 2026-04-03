package domain.board;

import common.exception.JanggiException;
import domain.piece.BasicPiece;
import domain.piece.MovablePiece;
import domain.piece.None;
import domain.piece.PieceType;
import domain.player.Player;
import domain.player.Team;
import domain.position.Path;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, BasicPiece> board;

    public Board(Map<Position, BasicPiece> board) {
        this.board = board;
    }

    public BasicPiece move(Position source, Position destination, Player player) {
        validateSource(source, player);
        BasicPiece movePiece = findPiece(source);

        validateMovement((MovablePiece) movePiece, source, destination);

        BasicPiece caughtPiece = findPiece(destination);
        board.put(source, None.getInstance());
        board.put(destination, movePiece);

        return caughtPiece;
    }

    public Position findJangPosition(Team team) {
        return board.entrySet().stream()
                .filter(entry -> {
                    BasicPiece piece = entry.getValue();
                    return !piece.isNone()
                            && piece.isType(PieceType.JANG)
                            && piece.getTeam() == team;
                })
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public BasicPiece findPiece(Position position) {
        return board.get(position);
    }

    public double calculateRawScore(Team team) {
        return board.values().stream()
                .filter(piece -> !piece.isDifferentTeam(team))
                .mapToDouble(piece -> piece.getPieceType().score())
                .sum();
    }

    public void validateSource(Position source, Player player) {
        BasicPiece piece = findPiece(source);

        if (piece.isNone()) {
            throw new JanggiException("비어있는 곳입니다.");
        }

        if (player.isDifferentTeam(piece)) {
            throw new JanggiException("자신의 기물이 아닙니다.");
        }
    }

    private void validateMovement(MovablePiece piece, Position source, Position destination) {
        Path path = piece.calculatePath(source, destination);
        PathPieces pathPieces = createPathPieces(piece, path);
        if (!piece.validatePath(pathPieces)) {
            throw new JanggiException("기물을 이동할 수 없습니다.");
        }
    }

    private PathPieces createPathPieces(MovablePiece sourcePiece, Path path) {
        List<BasicPiece> pieces = path.waypoints().stream()
                .map(this::findPiece)
                .filter(piece -> !piece.isNone())
                .toList();

        BasicPiece destinationPiece = findPiece(path.destination());
        MoveMeta moveMeta = createMoveMeta(path);
        return new PathPieces(sourcePiece, pieces, destinationPiece, moveMeta);
    }

    private MoveMeta createMoveMeta(Path path) {
        Position source = path.source();
        Position destination = path.destination();
        return new MoveMeta(
                source.isInPalace(),
                destination.isInPalace(),
                isDiagonalMove(source, destination),
                source.isPalaceDiagonalReachable(destination)
        );
    }

    private boolean isDiagonalMove(Position source, Position destination) {
        int rowDiff = Math.abs(source.row() - destination.row());
        int columnDiff = Math.abs(source.column() - destination.column());
        return rowDiff > 0 && rowDiff == columnDiff;
    }

}
