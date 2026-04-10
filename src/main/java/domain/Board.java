package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static java.util.Optional.ofNullable;

import domain.palace.Palace;
import domain.palace.PalaceRouter;
import strategy.move.MoveStrategy;

public class Board implements PalaceRouter {

    private static final Palace hanPalace = new Palace(Position.of(1, 4));
    private static final Palace choPalace = new Palace(Position.of(8, 4));

    private final Map<Position, Piece> pieces;

    public Board(Map<Position, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public GameSnapshot capture() {
        return GameSnapshot.from(pieces);
    }

    public List<Piece> getBlockingPieces(Route route) {
        return route.intermediateNodes().stream()
                .map(pieces::get)
                .filter(piece -> piece != null)
                .toList();
    }

    public Optional<Piece> getDestinationPiece(Route route) {
        return ofNullable(pieces.get(route.endPos()));
    }

    public Optional<Position> findPositionOf(Piece piece) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue() == piece)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public Optional<Piece> findPiece(Position position) {
        return ofNullable(pieces.get(position));
    }

    public List<Map.Entry<Position, Piece>> findPiecesByTeam(TeamColor teamColor) {
        return pieces.entrySet().stream()
                .filter(entry -> entry.getValue().isOnTeam(teamColor))
                .sorted(Map.Entry.comparingByKey())
                .toList();
    }

    public List<Piece> piecesOfTeam(TeamColor teamColor) {
        return pieceValuesFromEntries(findPiecesByTeam(teamColor));
    }

    private static List<Piece> pieceValuesFromEntries(List<Map.Entry<Position, Piece>> entries) {
        List<Piece> result = new ArrayList<>(entries.size());
        for (Map.Entry<Position, Piece> entry : entries) {
            result.add(entry.getValue());
        }
        return List.copyOf(result);
    }

    public MovableRoutes findMovableRoutes(Piece piece) {
        List<Route> routes = computeMovableRoutes(piece);
        return new MovableRoutes(routes, hasKingAtAnyDestination(routes));
    }

    private List<Route> computeMovableRoutes(Piece piece) {
        Position currentPosition = findPositionOf(piece)
                .orElseThrow(() -> new IllegalArgumentException("보드에 없는 기물입니다."));

        MoveStrategy moveStrategy = piece.getPieceType().moveStrategy();

        return moveStrategy.makeRoutes(currentPosition, piece, this).stream()
                .filter(route -> route.endPos().isInsideBoard())
                .filter(route -> moveStrategy.canMove(route, getBlockingPieces(route),
                        getDestinationPiece(route).orElse(null), piece.getTeamColor()))
                .toList();
    }

    private boolean hasKingAtAnyDestination(List<Route> routes) {
        return routes.stream()
                .map(this::getDestinationPiece)
                .flatMap(Optional::stream)
                .anyMatch(Piece::isKing);
    }

    public Optional<Piece> move(Piece piece, Position destination) {
        Position currentPosition = findPositionOf(piece)
                .orElseThrow(() -> new IllegalArgumentException("보드에 없는 기물입니다."));

        MoveStrategy moveStrategy = piece.getPieceType().moveStrategy();

        Route route = moveStrategy.makeRoutes(currentPosition, piece, this).stream()
                .filter(candidate -> candidate.endPos().equals(destination))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 기물은 목적지로 이동할 수 없습니다."));

        List<Piece> blockingPieces = getBlockingPieces(route);
        Piece pieceAtDestination = getDestinationPiece(route).orElse(null);

        if (!moveStrategy.canMove(route, blockingPieces, pieceAtDestination, piece.getTeamColor())) {
            throw new IllegalArgumentException("현재 판 상태에서는 해당 목적지로 이동할 수 없습니다.");
        }

        Optional<Piece> captured = Optional.ofNullable(pieceAtDestination);
        pieces.remove(currentPosition);
        pieces.put(destination, piece);
        return captured;
    }

    @Override
    public boolean isInsidePalace(Position position) {
        return hanPalace.contains(position) || choPalace.contains(position);
    }

    @Override
    public List<Position> getDiagonalAdjacents(Position position) {
        if (hanPalace.contains(position)) return hanPalace.getDiagonalAdjacents(position);
        if (choPalace.contains(position)) return choPalace.getDiagonalAdjacents(position);
        return List.of();
    }
}
