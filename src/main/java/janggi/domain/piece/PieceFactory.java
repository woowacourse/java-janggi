package janggi.domain.piece;

import janggi.domain.board.BoardDirection;
import janggi.domain.movestrategy.*;
import janggi.domain.movestrategy.rule.*;
import janggi.domain.palace.PalaceFactory;

import java.util.List;

public class PieceFactory {

    public static Piece createGeneral(Team team) {
        return new Piece(PieceType.GENERAL, team, new GeneralStrategy(
                PalaceFactory.createPalace(team),
                List.of(new StraightOneStepMoveRule(),
                        new PalaceDiagonalOneStepMoveRule(PalaceFactory.createPalace(team)))
        ));
    }

    public static Piece createGuard(Team team) {
        return new Piece(PieceType.GUARD, team, new GuardStrategy(
                PalaceFactory.createPalace(team),
                List.of(new StraightOneStepMoveRule(),
                        new PalaceDiagonalOneStepMoveRule(PalaceFactory.createPalace(team)))
        ));
    }

    public static Piece createChariot(Team team) {
        return new Piece(PieceType.CHARIOT, team, new ChariotStrategy(
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
        return new Piece(PieceType.HORSE, team, new HorseStrategy());
    }

    public static Piece createElephant(Team team) {
        return new Piece(PieceType.ELEPHANT, team, new ElephantStrategy());
    }

    public static Piece createSolider(Team team, BoardDirection direction) {
        return new Piece(PieceType.SOLDIER, team, new SoliderStrategy(
                List.of(new DirectionalOneStepMoveRule(direction),
                        new PalaceDiagonalDirectionalOneStepMoveRule(PalaceFactory.createPalace(team), direction))));
    }

    public static Piece create(PieceType pieceType, Team team) {
        if (pieceType == PieceType.GENERAL) {
            return createGeneral(team);
        }
        if (pieceType == PieceType.GUARD) {
            return createGuard(team);
        }
        if (pieceType == PieceType.CHARIOT) {
            return createChariot(team);
        }
        if (pieceType == PieceType.CANNON) {
            return createCannon(team);
        }
        if (pieceType == PieceType.HORSE) {
            return createHorse(team);
        }
        if (pieceType == PieceType.ELEPHANT) {
            return createElephant(team);
        }
        if (pieceType == PieceType.SOLDIER) {
            return createSolider(team, BoardDirection.of(team));
        }
        throw new IllegalArgumentException("알 수 없는 기물 타입입니다: " + pieceType);
    }
}
