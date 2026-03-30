package domain;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import strategy.move.CannonMoveStrategy;
import strategy.move.ElephantMoveStrategy;
import strategy.move.GuardMoveStrategy;
import strategy.move.HorseMoveStrategy;
import strategy.move.KingMoveStrategy;
import strategy.move.MoveStrategy;
import strategy.move.PawnMoveStrategy;
import strategy.move.RookMoveStrategy;

public class Piece {
    private static final EnumMap<PieceType, Supplier<MoveStrategy>> MOVE_STRATEGY_SUPPLIERS = new EnumMap<>(
            Map.of(
                    PieceType.CANNON, CannonMoveStrategy::new,
                    PieceType.ELEPHANT, ElephantMoveStrategy::new,
                    PieceType.GUARD, GuardMoveStrategy::new,
                    PieceType.HORSE, HorseMoveStrategy::new,
                    PieceType.KING, KingMoveStrategy::new,
                    PieceType.PAWN, PawnMoveStrategy::new,
                    PieceType.ROOK, RookMoveStrategy::new));

    private final TeamColor teamColor;
    private final PieceType pieceType;
    private final MoveStrategy moveStrategy;

    
    private Piece(TeamColor teamColor, PieceType pieceType, MoveStrategy moveStrategy) {
        this.teamColor = teamColor;
        this.pieceType = pieceType;
        this.moveStrategy = moveStrategy;
    }

    public static Piece of(TeamColor teamColor, PieceType pieceType) {
        return new Piece(teamColor, pieceType, createMoveStrategy(pieceType));
    }

    private static MoveStrategy createMoveStrategy(PieceType pieceType) {
        Supplier<MoveStrategy> supplier = MOVE_STRATEGY_SUPPLIERS.get(pieceType);
        if (supplier == null) {
            throw new IllegalArgumentException("지원하지 않는 기물 타입입니다.");
        }
        return supplier.get();
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
        return moveStrategy.canMove(route, blockingPieces, destinationPiece, teamColor);
    }


}
