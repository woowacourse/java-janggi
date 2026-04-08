package domain;

public record PieceProperty(PieceType pieceType, Team team) {

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

    public boolean isNoneTeam() {
        return team.equals(Team.NONE);
    }

    public int score() {
        return pieceType().score();
    }
}
