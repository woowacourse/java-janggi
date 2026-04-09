package janggi.domain.piece;

public class PieceFactory {

    public Piece create(Name name, Team team) {
        if (name == Name.GENERAL) {
            return new GeneralPiece(team);
        }
        if (name == Name.CHARIOT) {
            return new ChariotPiece(team);
        }
        if (name == Name.CANNON) {
            return new CannonPiece(team);
        }
        if (name == Name.HORSE) {
            return new HorsePiece(team);
        }
        if (name == Name.ELEPHANT) {
            return new ElephantPiece(team);
        }
        if (name == Name.GUARD) {
            return new GuardPiece(team);
        }
        return new SoldierPiece(team);
    }
}
