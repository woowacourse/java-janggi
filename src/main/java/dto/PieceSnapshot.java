package dto;

import domain.Piece;
import domain.Position;

public record PieceSnapshot(PiecePosition position, PieceIdentity identity) {

    private static final String GREEN_TEAM = "GREEN";
    private static final String RED_TEAM = "RED";
    private static final String NONE = "NONE";

    public static PieceSnapshot of(Piece piece) {
        Position position = piece.position();
        return new PieceSnapshot(
                new PiecePosition(position.row(), position.col()),
                PieceIdentity.from(piece.name(), piece.isGreenTeam(), piece.isRedTeam())
        );
    }

    public static PieceSnapshot from(int row, int col, String name, String team) {
        return new PieceSnapshot(
                new PiecePosition(row, col),
                new PieceIdentity(name, team)
        );
    }

    public int row() {
        return position.row();
    }

    public int col() {
        return position.col();
    }

    public String name() {
        return identity.name();
    }

    public String team() {
        return identity.team();
    }

    private record PiecePosition(int row, int col) {
    }

    private record PieceIdentity(String name, String team) {
        private static PieceIdentity from(String name, boolean isGreenTeam, boolean isRedTeam) {
            if(isGreenTeam) {
                return new PieceIdentity(name, GREEN_TEAM);
            }

            if(isRedTeam){
                return new PieceIdentity(name, RED_TEAM);
            }

            return new PieceIdentity(name, NONE);
        }
    }
}
