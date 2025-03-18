package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;

import java.util.ArrayList;
import java.util.List;

public class Cha implements Piece {

    private final JanggiCoordinate currCoordinate;
    private final Team team;


    public Cha(JanggiCoordinate currCoordinate, Team team) {
        this.currCoordinate = currCoordinate;
        this.team = team;
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions(JanggiBoard board) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        int row = currCoordinate.getRow();
        int col = currCoordinate.getCol();
        for (int i = 1; i <= 9; i++) {
            if (i == row) {
                continue;
            }
            availablePositions.add(currCoordinate.move(i, col));
        }
        for (int i = 1; i <= 10; i++) {
            if (i == col) {
                continue;
            }
            availablePositions.add(currCoordinate.move(row, i));
        }
        return availablePositions;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
