package domain.piece;

import domain.movestrategy.CannonMoveStrategy;
import domain.movestrategy.ChariotMoveStrategy;
import domain.movestrategy.ElephantMoveStrategy;
import domain.movestrategy.GeneralMoveStrategy;
import domain.movestrategy.GuardMoveStrategy;
import domain.movestrategy.HorseMoveStrategy;
import domain.movestrategy.MoveStrategy;
import domain.movestrategy.SoldierMoveStrategy;
import java.util.List;

public enum PieceType {

    GENERAL("楚", "漢", new GeneralMoveStrategy(),
            List.of(
                    Position.of(2, 5)
            )),

    GUARD("士", "士", new GuardMoveStrategy(),
            List.of(
                    Position.of(1, 4),
                    Position.of(1, 6)
            )),

    HORSE("馬", "馬", new HorseMoveStrategy(), List.of()),

    ELEPHANT("象", "象", new ElephantMoveStrategy(), List.of()),

    SOLDIER("卒", "兵", new SoldierMoveStrategy(),
            List.of(
                    Position.of(4, 1),
                    Position.of(4, 3),
                    Position.of(4, 5),
                    Position.of(4, 7),
                    Position.of(4, 9)
            )),

    CANNON("砲", "炮", new CannonMoveStrategy(),
            List.of(
                    Position.of(3, 2),
                    Position.of(3, 8)
            )),

    CHARIOT("車", "車", new ChariotMoveStrategy(),
            List.of(
                    Position.of(1, 1),
                    Position.of(1, 9)
            ));

    private final String nameOfCho;
    private final String nameOfHan;
    private final MoveStrategy moveStrategy;
    private final List<Position> initPositions;

    PieceType(final String nameOfCho,
              final String nameOfHan,
              final MoveStrategy moveStrategy,
              final List<Position> initPositions
    ) {
        this.nameOfCho = nameOfCho;
        this.nameOfHan = nameOfHan;
        this.moveStrategy = moveStrategy;
        this.initPositions = initPositions;
    }

    public List<Position> getInitPositions() {
        return initPositions;
    }
}
