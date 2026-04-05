package janggi.domain.piece;

import janggi.domain.board.BoardDirection;
import janggi.domain.movestrategy.*;
import janggi.domain.movestrategy.rule.DirectionalOneStepMoveRule;
import janggi.domain.movestrategy.rule.StraightForwardMoveRule;
import janggi.domain.movestrategy.rule.StraightOneStepMoveRule;
import janggi.domain.palace.PalaceFactory;

import java.util.List;

public class PieceFactory {

    public static Piece createGeneral(Team team) {
        return new Piece(PieceType.GENERAL, team, new GeneralStrategy(
                PalaceFactory.createPalace(team),
                List.of(new StraightOneStepMoveRule())
        ));
    }

    public static Piece createGuard(Team team) {
        return new Piece(PieceType.GUARD, team, new GuardStrategy(
                PalaceFactory.createPalace(team),
                List.of(new StraightOneStepMoveRule())
        ));
    }

    public static Piece createChariot(Team team) {
        return new Piece(PieceType.CHARIOT, team, new ChariotStrategy(
                List.of(new StraightForwardMoveRule())
        ));
    }

    public static Piece createCannon(Team team) {
        return new Piece(PieceType.CANNON, team, new CannonStrategy(
                List.of(new StraightForwardMoveRule())
        ));
    }

    public static Piece createHorse(Team team) {
        return new Piece(PieceType.HORSE, team, new HorseStrategy());
    }

    public static Piece createElephant(Team team) {
        return new Piece(PieceType.ELEPHANT, team, new ElephantStrategy());
    }

    public static Piece createSolider(Team team, BoardDirection direction) {
        return new Piece(PieceType.SOLDIER, team, new SoliderStrategy(
                List.of(new DirectionalOneStepMoveRule(direction))
        ));
    }

}
