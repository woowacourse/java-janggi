package domain.piece;

public enum PieceType {
    CHARIOT {
        @Override
        public Piece createPiece(Team team) {
            return new Chariot(team);
        }
    },
    CANNON {
        @Override
        public Piece createPiece(Team team) {
            return new Cannon(team);
        }
    },
    HORSE {
        @Override
        public Piece createPiece(Team team) {
            return new Horse(team);
        }
    },
    ELEPHANT {
        @Override
        public Piece createPiece(Team team) {
            return new Elephant(team);
        }
    },
    SCHOLAR {
        @Override
        public Piece createPiece(Team team) {
            return new Scholar(team);
        }
    },
    KING {
        @Override
        public Piece createPiece(Team team) {
            if (team == Team.HAN){
                return new King(team, new Score(1.5));
            }
            return new King(team, new Score(0));
        }
    },
    PAWN {
        @Override
        public Piece createPiece(Team team) {
            return new Pawn(team);
        }
    };

    public abstract Piece createPiece(Team team);
}
