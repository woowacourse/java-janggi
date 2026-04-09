package domain.piece;

import domain.Team;
import domain.strategy.CarStrategy;

public class Car extends MoveablePiece {

    public Car(Team team) {
        super(team, new CarStrategy());
    }
}
