package dto;

import domain.Position;
import domain.Piece;

public record PieceInfo(PiecePosition position, PieceIdentity identity) {

    public static PieceInfo from(Piece piece) {
        Position position = piece.position();
        return new PieceInfo(
                new PiecePosition(position.row(), position.col()),
                new PieceIdentity(piece.name(), piece.isGreenTeam(), piece.isRedTeam())
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

    public boolean isGreenTeam() {
        return identity.isGreenTeam();
    }

    public boolean isRedTeam() {
        return identity.isRedTeam();
    }

    private record PiecePosition(int row, int col) {
    }

    private record PieceIdentity(String name, boolean isGreenTeam, boolean isRedTeam) {
        private boolean isNone() {
            return !isGreenTeam && !isRedTeam;
        }
    }
}
