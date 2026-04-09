package janggi.domain.piece;

import janggi.domain.team.TeamType;

public enum PieceType {
    CHA("차", 13) {
        @Override
        public Piece create(TeamType teamType) {
            return new Cha(teamType);
        }
    },
    GUNG("궁", 0){
        @Override
        public Piece create(TeamType teamType) {
            return new Gung(teamType);
        }
    },
    JOL("졸", 2){
        @Override
        public Piece create(TeamType teamType) {
            return new Jol(teamType);
        }
    },
    MA("마", 5){
        @Override
        public Piece create(TeamType teamType) {
            return new Ma(teamType);
        }
    },
    PO("포", 7){
        @Override
        public Piece create(TeamType teamType) {
            return new Po(teamType);
        }
    },
    SA("사", 3){
        @Override
        public Piece create(TeamType teamType) {
            return new Sa(teamType);
        }
    },
    SANG("상", 3){
        @Override
        public Piece create(TeamType teamType) {
            return new Sang(teamType);
        }
    },
    ;

    PieceType(String name, int score) {
        this.name = name;
        this.score = score;
    }

    private final String name;
    private final int score;

    public static PieceType from(String pieceTypeName) {
        for (PieceType pieceType : values()) {
            if (pieceType.name.equals(pieceTypeName)) {
                return pieceType;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 기물 이름입니다.");
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public abstract Piece create(TeamType teamType);
}
