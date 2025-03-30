package piece;

import game.Team;
import java.util.Arrays;
import location.Position;

public enum PieceType {
    CANNON(1, 7) {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new Cannon(pieceId, team, currentPosition);
        }
    },
    CHARIOT(2, 13) {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new Chariot(pieceId, team, currentPosition);
        }
    },
    ELEPHANT(3, 3) {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new Elephant(pieceId, team, currentPosition);
        }
    },
    GENERAL(4, 0) {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new General(pieceId, team, currentPosition);
        }
    },
    SOLDIER(5, 2) {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            if (team == Team.GREEN) {
                return new GreenSoldier(pieceId, team, currentPosition);
            }
            return new RedSoldier(pieceId, team, currentPosition);
        }
    },
    GUARD(6, 3) {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new Guard(pieceId, team, currentPosition);
        }
    },
    HORSE(7, 5) {
        @Override
        public Piece createPiece(int pieceId, Team team, Position currentPosition) {
            return new Horse(pieceId, team, currentPosition);
        }
    };

    private final int id;
    private final int score;

    PieceType(int id, int score) {
        this.id = id;
        this.score = score;
    }

    public static PieceType findById(int id) {
        return Arrays.stream(PieceType.values())
                .filter(pieceType -> pieceType.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] id에 해당하는 기물 타입이 없습니다."));
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

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }
}
