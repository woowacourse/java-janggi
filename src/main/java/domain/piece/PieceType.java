package domain.piece;

import domain.movestrategy.CannonMoveStrategy;
import domain.movestrategy.ChariotMoveStrategy;
import domain.movestrategy.ElephantMoveStrategy;
import domain.movestrategy.GeneralMoveStrategy;
import domain.movestrategy.GuardMoveStrategy;
import domain.movestrategy.HorseMoveStrategy;
import domain.movestrategy.MoveStrategy;
import domain.movestrategy.SoldierMoveStrategy;
import domain.player.Team;

public enum PieceType {

    GENERAL("楚", "漢", new GeneralMoveStrategy()),
    GUARD("士", "士", new GuardMoveStrategy()),
    HORSE("馬", "馬", new HorseMoveStrategy()),
    ELEPHANT("象", "象", new ElephantMoveStrategy()),
    SOLDIER("卒", "兵", new SoldierMoveStrategy()),
    CANNON("砲", "炮", new CannonMoveStrategy()),
    CHARIOT("車", "車", new ChariotMoveStrategy());

    private final String nameOfCho;
    private final String nameOfHan;
    private final MoveStrategy moveStrategy;

    PieceType(final String nameOfCho, final String nameOfHan, final MoveStrategy moveStrategy) {
        this.nameOfCho = nameOfCho;
        this.nameOfHan = nameOfHan;
        this.moveStrategy = moveStrategy;
    }

    public String getNameOf(Team team) {
        if (team == Team.CHO) {
            return nameOfCho;
        }
        return nameOfHan;
    }
}
