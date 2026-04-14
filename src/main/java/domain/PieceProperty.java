package domain;

import domain.strategy.MoveStrategy;

public record PieceProperty(PieceType pieceType, Team team) {

    public static PieceProperty of(PieceType pieceType, Team team) {
        return new PieceProperty(pieceType, team);
    }

    public static PieceProperty none() {
        return new PieceProperty(PieceType.EMPTY_VALUE, Team.NONE);
    }

    public MoveStrategy moveStrategy(Position position) {
        return pieceType.createStrategy(position);
    }

    public boolean isGeneral() {
        return pieceType == PieceType.GENERAL;
    }

    public boolean isCannon () {
        return pieceType == PieceType.CANNON;
    }

    public String name() {
        return pieceType.description();
    }

    public boolean isGreenTeam() {
        return team.equals(Team.GREEN);
    }

    public boolean isRedTeam() {
        return team.equals(Team.RED);
    }

    public boolean isNone() {
        return team.equals(Team.NONE);
    }

    public int point() {
        return pieceType.point();
    }
}
