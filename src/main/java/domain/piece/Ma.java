package domain.piece;

import domain.JanggiCoordinate;

import java.util.ArrayList;
import java.util.List;

public class Ma implements Piece{
    private static final List<JanggiCoordinate> OFFSET_MOVEMENT = List.of(
            new JanggiCoordinate(2,1),
            new JanggiCoordinate(2,-1),
            new JanggiCoordinate(-2,1),
            new JanggiCoordinate(-2,-1),
            new JanggiCoordinate(1, 2),
            new JanggiCoordinate(1,-2),
            new JanggiCoordinate(-1,2),
            new JanggiCoordinate(-1,-2)
    );

    private final JanggiCoordinate currCoordinate;

    public Ma(JanggiCoordinate currCoordinate) {
        this.currCoordinate = currCoordinate;
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions(){
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        for(JanggiCoordinate coordinate : OFFSET_MOVEMENT){
            availablePositions.add(movePosition(coordinate));
        }
        return availablePositions;
    }

    @Override
    public JanggiCoordinate movePosition(JanggiCoordinate moveOffset){
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
