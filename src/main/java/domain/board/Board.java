package domain.board;

import domain.piece.Piece;
import domain.piece.TeamColor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Board {

    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    private List<Piece> getBlockingPieces(Route route) {
        return route.intermediatePositions().stream()
                .map(this::findPiece)
                .flatMap(Optional::stream)
                .toList();
    }

    private Optional<Piece> getDestinationPiece(Route route) {
        return Optional.ofNullable(pieces.get(route.endPos()));
    }

    private Optional<Position> findPositionOf(Piece piece) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue() == piece)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public Optional<Piece> findPiece(Position position) {
        return Optional.ofNullable(pieces.get(position));
    }

    public List<PiecePosition> findPiecesByTeam(TeamColor teamColor) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue().getTeamColor() == teamColor)
                .sorted((left, right) -> left.getKey().compareBoardOrder(right.getKey()))
                .map(entry -> new PiecePosition(entry.getKey(), entry.getValue()))
                .toList();
    }

    public List<Route> findMovableRoutes(Piece piece) {
        final Position currentPosition = findPositionOf(piece)
                .orElseThrow(() -> new IllegalArgumentException("보드에 없는 기물입니다."));

        return piece.makeRoutes(currentPosition).stream()
                .filter(route -> piece.canMove(route, getBlockingPieces(route), getDestinationPiece(route)))
                .toList();
    }

    public MoveResult move(Piece piece, Position destination) {
        final Position currentPosition = findPositionOf(piece)
                .orElseThrow(() -> new IllegalArgumentException("보드에 없는 기물입니다."));

        final Route route = piece.makeRoutes(currentPosition).stream()
                .filter(candidate -> candidate.endPos().equals(destination))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 기물은 목적지로 이동할 수 없습니다."));

        final List<Piece> blockingPieces = getBlockingPieces(route);
        final Optional<Piece> destinationPiece = getDestinationPiece(route);

        if (!piece.canMove(route, blockingPieces, destinationPiece)) {
            throw new IllegalArgumentException("현재 판 상태에서는 해당 목적지로 이동할 수 없습니다.");
        }

        final MoveResult moveResult = MoveResult.from(destinationPiece);
        pieces.remove(currentPosition);
        pieces.put(destination, piece);
        return moveResult;
    }
}
