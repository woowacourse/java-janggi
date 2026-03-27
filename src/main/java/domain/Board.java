package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Board {

    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public List<Piece> getBlockingPieces(Route route) {
        return route.intermeidateNodes().stream()
                .map(pieces::get)
                .filter(piece -> piece != null)
                .toList();
    }

    public Optional<Piece> getDestinationPiece(Route route) {
        return Optional.ofNullable(pieces.get(route.endPos()));
    }

    public Optional<Position> findPositionOf(Piece piece) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue() == piece)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public Optional<Piece> findPiece(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    public List<Map.Entry<Position, Piece>> findPiecesByTeam(TeamColor teamColor) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue().getTeamColor() == teamColor)
                .sorted((left, right) -> {
                    int rowCompare = Integer.compare(left.getKey().row(), right.getKey().row());
                    if (rowCompare != 0) {
                        return rowCompare;
                    }
                    return Integer.compare(left.getKey().column(), right.getKey().column());
                })
                .toList();
    }

    public List<Route> findMovableRoutes(Piece piece) {
        Position currentPosition = findPositionOf(piece)
                .orElseThrow(() -> new IllegalArgumentException("보드에 없는 기물입니다."));

        return piece.makeRoutes(currentPosition).stream()
                .filter(route -> piece.canMove(route, getBlockingPieces(route), getDestinationPiece(route)))
                .toList();
    }

    public void move(Piece piece, Position destination) {
        Position currentPosition = findPositionOf(piece)
                .orElseThrow(() -> new IllegalArgumentException("보드에 없는 기물입니다."));

        Route route = piece.makeRoutes(currentPosition).stream()
                .filter(candidate -> candidate.endPos().equals(destination))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 기물은 목적지로 이동할 수 없습니다."));

        List<Piece> blockingPieces = getBlockingPieces(route);
        Optional<Piece> destinationPiece = getDestinationPiece(route);

        if (!piece.canMove(route, blockingPieces, destinationPiece)) {
            throw new IllegalArgumentException("현재 판 상태에서는 해당 목적지로 이동할 수 없습니다.");
        }

        pieces.remove(currentPosition);
        pieces.put(destination, piece);
    }
}
