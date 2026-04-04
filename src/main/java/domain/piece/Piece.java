package domain.piece;

import domain.board.Position;
import domain.board.Route;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import strategy.move.CannonMoveStrategy;
import strategy.move.ElephantMoveStrategy;
import strategy.move.GuardMoveStrategy;
import strategy.move.HorseMoveStrategy;
import strategy.move.KingMoveStrategy;
import strategy.move.MoveStrategy;
import strategy.move.PawnMoveStrategy;
import strategy.move.RookMoveStrategy;

public class Piece {
    private static final Map<PieceType, MoveStrategy> MOVE_STRATEGIES = createMoveStrategies();

    private final TeamColor teamColor;
    private final PieceType pieceType;
    private final MoveStrategy moveStrategy;

    private Piece(TeamColor teamColor, PieceType pieceType, MoveStrategy moveStrategy) {
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
        return moveStrategy.makeRoutes(from, teamColor);
    }

    public boolean canMove(Route route, List<Piece> blockingPieces, Optional<Piece> destinationPiece) {
        return moveStrategy.canMove(route, blockingPieces, destinationPiece)
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

    public boolean isSameType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public static Piece of(TeamColor teamColor, PieceType pieceType) {
        return new Piece(teamColor, pieceType, createMoveStrategy(pieceType));
    }

    private static MoveStrategy createMoveStrategy(PieceType pieceType) {
        return Optional.ofNullable(MOVE_STRATEGIES.get(pieceType))
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 기물 타입입니다."));
    }

    private static Map<PieceType, MoveStrategy> createMoveStrategies() {
        final Map<PieceType, MoveStrategy> moveStrategies = new EnumMap<>(PieceType.class);
        moveStrategies.put(PieceType.CANNON, new CannonMoveStrategy());
        moveStrategies.put(PieceType.ELEPHANT, new ElephantMoveStrategy());
        moveStrategies.put(PieceType.GUARD, new GuardMoveStrategy());
        moveStrategies.put(PieceType.HORSE, new HorseMoveStrategy());
        moveStrategies.put(PieceType.KING, new KingMoveStrategy());
        moveStrategies.put(PieceType.PAWN, new PawnMoveStrategy());
        moveStrategies.put(PieceType.ROOK, new RookMoveStrategy());
        return moveStrategies;
    }
}



