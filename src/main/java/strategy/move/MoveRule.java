package strategy.move;

import domain.board.Position;
import domain.board.Route;
import domain.palace.PalaceRouteGenerator;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.TeamColor;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MoveRule {
    private static final PalaceRouteGenerator PALACE_ROUTE_GENERATOR = new PalaceRouteGenerator();
    private static final Map<PieceType, MoveStrategy> MOVE_STRATEGIES = createMoveStrategies();
    private static final Map<PieceType, PalaceRouteRule> PALACE_ROUTE_GENERATORS = createPalaceRouteGenerators();

    public List<Route> makeRoutes(PieceType pieceType, Position position, TeamColor teamColor) {
        final List<Route> routes = new ArrayList<>(findMoveStrategy(pieceType).makeRoutes(position, teamColor));
        routes.addAll(findPalaceRouteGenerator(pieceType).generate(position, teamColor));
        return routes;
    }

    public boolean canMove(PieceType pieceType, Route route, List<Piece> blockingPieces, Optional<Piece> destinationPiece) {
        return findMoveStrategy(pieceType).canMove(route, blockingPieces, destinationPiece);
    }

    private MoveStrategy findMoveStrategy(PieceType pieceType) {
        final MoveStrategy moveStrategy = MOVE_STRATEGIES.get(pieceType);
        if (moveStrategy == null) {
            throw new IllegalArgumentException("지원하지 않는 기물 타입입니다.");
        }
        return moveStrategy;
    }

    private PalaceRouteRule findPalaceRouteGenerator(PieceType pieceType) {
        return PALACE_ROUTE_GENERATORS.getOrDefault(pieceType, (position, teamColor) -> List.of());
    }

    private static Map<PieceType, MoveStrategy> createMoveStrategies() {
        final Map<PieceType, MoveStrategy> moveStrategies = new EnumMap<>(PieceType.class);
        final MoveStrategy palaceMoveStrategy = new PalaceMoveStrategy();
        moveStrategies.put(PieceType.CANNON, new CannonMoveStrategy());
        moveStrategies.put(PieceType.ELEPHANT, new ElephantMoveStrategy());
        moveStrategies.put(PieceType.GUARD, palaceMoveStrategy);
        moveStrategies.put(PieceType.HORSE, new HorseMoveStrategy());
        moveStrategies.put(PieceType.KING, palaceMoveStrategy);
        moveStrategies.put(PieceType.PAWN, new PawnMoveStrategy());
        moveStrategies.put(PieceType.ROOK, new RookMoveStrategy());
        return moveStrategies;
    }

    private static Map<PieceType, PalaceRouteRule> createPalaceRouteGenerators() {
        final Map<PieceType, PalaceRouteRule> palaceRouteGenerators = new EnumMap<>(PieceType.class);
        palaceRouteGenerators.put(PieceType.CANNON,
                (position, teamColor) -> PALACE_ROUTE_GENERATOR.createCannonPalaceRoutes(position));
        palaceRouteGenerators.put(PieceType.PAWN, PALACE_ROUTE_GENERATOR::createPawnPalaceRoutes);
        palaceRouteGenerators.put(PieceType.ROOK,
                (position, teamColor) -> PALACE_ROUTE_GENERATOR.createRookPalaceRoutes(position));
        return palaceRouteGenerators;
    }
}
