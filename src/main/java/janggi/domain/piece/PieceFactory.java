package janggi.domain.piece;

import janggi.domain.board.BoardDirection;
import janggi.domain.movestrategy.CannonStrategy;
import janggi.domain.movestrategy.DefaultMoveStrategy;
import janggi.domain.movestrategy.PalaceRestrictMoveStrategy;
import janggi.domain.movestrategy.rule.*;
import janggi.domain.palace.PalaceFactory;

import java.util.List;

public class PieceFactory {

    public static Piece createGeneral(Team team) {
        return new Piece(PieceType.GENERAL, team, new PalaceRestrictMoveStrategy(
                PalaceFactory.createPalace(team),
                List.of(new StraightOneStepMoveRule(),
                        new PalaceDiagonalOneStepMoveRule(PalaceFactory.createPalace(team)))
        ));
    }

    public static Piece createGuard(Team team) {
        return new Piece(PieceType.GUARD, team, new PalaceRestrictMoveStrategy(
                PalaceFactory.createPalace(team),
                List.of(new StraightOneStepMoveRule(),
                        new PalaceDiagonalOneStepMoveRule(PalaceFactory.createPalace(team)))
        ));
    }

    public static Piece createChariot(Team team) {
        return new Piece(PieceType.CHARIOT, team, new DefaultMoveStrategy(
                List.of(new StraightForwardMoveRule(),
                        new PalaceDiagonalForwardMoveRule(List.of(
                                PalaceFactory.createPalace(Team.HAN),
                                PalaceFactory.createPalace(Team.CHO))))));
    }

    public static Piece createCannon(Team team) {
        return new Piece(PieceType.CANNON, team, new CannonStrategy(
                List.of(new StraightForwardMoveRule(),
                        new PalaceDiagonalForwardMoveRule(List.of(
                                PalaceFactory.createPalace(Team.HAN),
                                PalaceFactory.createPalace(Team.CHO))))));
    }

    public static Piece createHorse(Team team) {
        return new Piece(PieceType.HORSE, team, new DefaultMoveStrategy(List.of(new HorseMoveRule())));
    }

    public static Piece createElephant(Team team) {
        return new Piece(PieceType.ELEPHANT, team, new DefaultMoveStrategy(
                List.of(new ElephantMoveRule())
        ));
    }

    public static Piece createSolider(Team team, BoardDirection direction) {
        return new Piece(PieceType.SOLDIER, team, new DefaultMoveStrategy(
                List.of(new DirectionalOneStepMoveRule(direction),
                        new PalaceDiagonalDirectionalOneStepMoveRule(PalaceFactory.createPalace(team), direction))));
    }
}
