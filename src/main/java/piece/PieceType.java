package piece;

import game.Team;
import java.util.EnumSet;
import location.PathManagerImpl;
import location.Position;

public enum PieceType {
    CANNON("cannon") {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new Cannon(pieceId, team, new PathManagerImpl(), currentPosition);
        }
    },
    CHARIOT("chariot") {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new Chariot(pieceId, team, new PathManagerImpl(), currentPosition);
        }
    },
    ELEPHANT("elephant") {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new Elephant(pieceId, team, currentPosition);
        }
    },
    GENERAL("general") {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new General(pieceId, team, new PathManagerImpl(), currentPosition);
        }
    },
    SOLDIER("soldier") {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            if (team == Team.GREEN) {
                return new GreenSoldier(pieceId, team, currentPosition);
            }
            return new RedSoldier(pieceId, team, currentPosition);
        }
    },
    GUARD("guard") {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new Guard(pieceId, team, new PathManagerImpl(), currentPosition);
        }
    },
    HORSE("horse") {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new Horse(pieceId, team, currentPosition);
        }
    };

    private final String expression;

    PieceType(String expression) {
        this.expression = expression;
    }

    public static PieceType findByExpression(String expression) {
        return EnumSet.allOf(PieceType.class).stream()
                .filter(pieceType -> pieceType.getExpression().equals(expression))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("[ERROR] 해당하는 기물이 없습니다."));
    }

    public static boolean isCannon(Piece piece) {
        return piece.getPieceType() == CANNON;
    }

    public static boolean isNotCannon(Piece piece) {
        return piece.getPieceType() != CANNON;
    }

    public static boolean isGeneral(Piece piece) {
        return piece.getPieceType() == GENERAL;
    }

    public abstract Piece createPiece(int pieceId, Team team, Position currentPosition);

    public String getExpression() {
        return expression;
    }
}
