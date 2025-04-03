package piece;

import game.Team;
import location.PathManagerImpl;
import location.Position;

public enum PieceType {
    CANNON {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new Cannon(pieceId, team, new PathManagerImpl(), currentPosition);
        }
    },
    CHARIOT {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new Chariot(pieceId, team, new PathManagerImpl(), currentPosition);
        }
    },
    ELEPHANT {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new Elephant(pieceId, team, currentPosition);
        }
    },
    GENERAL {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new General(pieceId, team, new PathManagerImpl(), currentPosition);
        }
    },
    SOLDIER {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            if (team == Team.GREEN) {
                return new GreenSoldier(pieceId, team, currentPosition);
            }
            return new RedSoldier(pieceId, team, currentPosition);
        }
    },
    GUARD {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new Guard(pieceId, team, new PathManagerImpl(), currentPosition);
        }
    },
    HORSE {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new Horse(pieceId, team, currentPosition);
        }
    };

    public static PieceType fromName(String pieceTypeName) {
        for (PieceType pieceType : PieceType.values()) {
            if (pieceType.name().equalsIgnoreCase(pieceTypeName)) {
                return pieceType;
            }
        }
        throw new IllegalStateException("[ERROR] 해당 이름의 기물이 존재하지 않습니다.");
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
}
