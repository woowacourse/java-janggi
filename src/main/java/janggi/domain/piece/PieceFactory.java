package janggi.domain.piece;

import janggi.domain.board.BoardDirection;
import janggi.domain.movestrategy.*;

public class PieceFactory {

    public static Piece createGeneral(Team team) {
        return new Piece(PieceType.GENERAL, team, new GeneralStrategy());
    }
    public static Piece createGuard(Team team) {
        return new Piece(PieceType.GUARD, team, new GuardStrategy());
    }
    public static Piece createChariot(Team team) {
        return new Piece(PieceType.CHARIOT, team, new ChariotStrategy());
    }
    public static Piece createCannon(Team team) {
        return new Piece(PieceType.CANNON, team, new CannonStrategy());
    }
    public static Piece createHorse(Team team) {
        return new Piece(PieceType.HORSE, team, new HorseStrategy());
    }
    public static Piece createElephant(Team team) {
        return new Piece(PieceType.ELEPHANT, team, new ElephantStrategy());
    }
    public static Piece createSolider(Team team, BoardDirection direction) {
        return new Piece(PieceType.SOLDIER, team, new SoliderStrategy(direction));
    }

}
