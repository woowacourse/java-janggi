package janggi.board;

import janggi.piece.Elephant;
import janggi.piece.Horse;
import janggi.position.Position;
import janggi.team.Team;
import janggi.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public enum TableOption {
    EHEH(7, 2, 8, 3),
    HEHE(8, 3, 7, 2),
    HEEH(7, 3, 8, 2),
    EHHE(8, 2, 7, 3);


    private final int rightElephantColumn;
    private final int leftElephantColumn;
    private final int rightHorseColumn;
    private final int leftHorseColumn;

    TableOption(int rightElephantColumn, int leftElephantColumn, int rightHorseColumn, int leftHorseColumn) {
        this.rightElephantColumn = rightElephantColumn;
        this.leftElephantColumn = leftElephantColumn;
        this.rightHorseColumn = rightHorseColumn;
        this.leftHorseColumn = leftHorseColumn;
    }

    public List<Piece> generateTableSetPieces(Team team, int row) {
        List<Piece> tableSettings = new ArrayList<>();
        tableSettings.add(new Elephant(team, new Position(row, rightElephantColumn)));
        tableSettings.add(new Elephant(team, new Position(row, leftElephantColumn)));
        tableSettings.add(new Horse(team, new Position(row, rightHorseColumn)));
        tableSettings.add(new Horse(team, new Position(row, leftHorseColumn)));
        return tableSettings;
    }
}
