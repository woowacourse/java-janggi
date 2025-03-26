package janggi.board;

import janggi.piece.Elephant;
import janggi.piece.Horse;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.team.Team;
import janggi.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public enum TableOption {
    EHEH(new Column(7), new Column(2), new Column(8), new Column(3)),
    HEHE(new Column(8), new Column(3), new Column(7), new Column(2)),
    HEEH(new Column(7), new Column(3), new Column(8), new Column(2)),
    EHHE(new Column(8), new Column(2), new Column(7), new Column(3));


    private final Column rightElephantColumn;
    private final Column leftElephantColumn;
    private final Column rightHorseColumn;
    private final Column leftHorseColumn;

    TableOption(Column rightElephantColumn, Column leftElephantColumn, Column rightHorseColumn, Column leftHorseColumn) {
        this.rightElephantColumn = rightElephantColumn;
        this.leftElephantColumn = leftElephantColumn;
        this.rightHorseColumn = rightHorseColumn;
        this.leftHorseColumn = leftHorseColumn;
    }

    public List<Piece> generateTableSetPieces(Team team, Row row) {
        List<Piece> tableSettings = new ArrayList<>();
        tableSettings.add(new Elephant(team, new Position(row, rightElephantColumn)));
        tableSettings.add(new Elephant(team, new Position(row, leftElephantColumn)));
        tableSettings.add(new Horse(team, new Position(row, rightHorseColumn)));
        tableSettings.add(new Horse(team, new Position(row, leftHorseColumn)));
        return tableSettings;
    }
}
