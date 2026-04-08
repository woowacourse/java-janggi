package domain.piece;

import domain.board.Position;
import domain.board.Route;
import domain.palace.PalaceRouteGenerator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import strategy.move.CannonMoveStrategy;
import strategy.move.ElephantMoveStrategy;
import strategy.move.HorseMoveStrategy;
import strategy.move.MoveStrategy;
import strategy.move.PawnMoveStrategy;
import strategy.move.RookMoveStrategy;

public class Piece {
    private static final PalaceRouteGenerator PALACE_ROUTE_GENERATOR = new PalaceRouteGenerator();
    private static final Map<PieceType, MoveStrategy> MOVE_STRATEGIES = createMoveStrategies();

    private final TeamColor teamColor;
    private final PieceType pieceType;
    private final Optional<MoveStrategy> moveStrategy;

    private Piece(TeamColor teamColor, PieceType pieceType, Optional<MoveStrategy> moveStrategy) {
        this.teamColor = teamColor;
        this.pieceType = pieceType;
        this.moveStrategy = moveStrategy;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public TeamColor getTeamColor() {
        return this.teamColor;
    }

    public List<Route> makeRoutes(Position from) {
        if (isPalacePiece()) {
            return PALACE_ROUTE_GENERATOR.createRoutes(from, pieceType, teamColor);
        }
        return currentMoveStrategy().makeRoutes(from, teamColor);
    }

    public boolean canMove(Route route, List<Piece> blockingPieces, Optional<Piece> destinationPiece) {
        return canFollowPieceMovementRule(route, blockingPieces, destinationPiece)
                && canOccupy(destinationPiece);
    }

    public boolean isSameTeam(Piece other) {
        return teamColor == other.teamColor;
    }

    public boolean isEnemy(Piece other) {
        return !isSameTeam(other);
    }

    public boolean canOccupy(Optional<Piece> destinationPiece) {
        return destinationPiece.isEmpty() || isEnemy(destinationPiece.get());
    }

    public static Piece of(TeamColor teamColor, PieceType pieceType) {
        return new Piece(teamColor, pieceType, createMoveStrategy(pieceType));
    }

    private boolean isPalacePiece() {
        return pieceType == PieceType.KING || pieceType == PieceType.GUARD;
    }

    private boolean canFollowPieceMovementRule(Route route, List<Piece> blockingPieces, Optional<Piece> destinationPiece) {
        if (isPalacePiece()) {
            return blockingPieces.isEmpty();
        }
        return currentMoveStrategy().canMove(route, blockingPieces, destinationPiece);
    }

    private MoveStrategy currentMoveStrategy() {
        return moveStrategy.orElseThrow(() -> new IllegalArgumentException("지원하지 않는 기물 타입입니다."));
    }

    private static Optional<MoveStrategy> createMoveStrategy(PieceType pieceType) {
        if (pieceType == PieceType.KING || pieceType == PieceType.GUARD) {
            return Optional.empty();
        }
        return Optional.ofNullable(MOVE_STRATEGIES.get(pieceType));
    }

    private static Map<PieceType, MoveStrategy> createMoveStrategies() {
        final Map<PieceType, MoveStrategy> moveStrategies = new EnumMap<>(PieceType.class);
        moveStrategies.put(PieceType.CANNON, new CannonMoveStrategy());
        moveStrategies.put(PieceType.ELEPHANT, new ElephantMoveStrategy());
        moveStrategies.put(PieceType.HORSE, new HorseMoveStrategy());
        moveStrategies.put(PieceType.PAWN, new PawnMoveStrategy());
        moveStrategies.put(PieceType.ROOK, new RookMoveStrategy());
        return moveStrategies;
    }
}
