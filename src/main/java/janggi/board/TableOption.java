package janggi.board;

import janggi.piece.Elephant;
import janggi.piece.Horse;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.team.Team;
import janggi.piece.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TableOption {
    EHEH("상마상마", new Column(7),new Column(2),new Column(8),new Column(3)),
    HEHE( "마상마상", new Column(8),new Column(3),new Column(7),new Column(2)),
    HEEH( "마상상마", new Column(7),new Column(3),new Column(8),new Column(2)),
    EHHE( "상마마상", new Column(8),new Column(2),new Column(7),new Column(3));


    private final String option;
    private final Column rightElephantColumn;
    private final Column leftElephantColumn;
    private final Column rightHorseColumn;
    private final Column leftHorseColumn;

    TableOption(String option, Column rightElephantColumn, Column leftElephantColumn, Column rightHorseColumn, Column leftHorseColumn) {
        this.option = option;
        this.rightElephantColumn = rightElephantColumn;
        this.leftElephantColumn = leftElephantColumn;
        this.rightHorseColumn = rightHorseColumn;
        this.leftHorseColumn = leftHorseColumn;
    }

    public static TableOption of(String option) {
        return Arrays.stream(TableOption.values())
                .filter(value -> value.option.equals(option))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("선택한 옵션은 존재하지 않습니다."));
    }

    public List<Piece> generateTableSetPieces(Team team,Row row) {
        List<Piece> tableSettings = new ArrayList<>();
        tableSettings.add(new Elephant(team,new Position(row,rightElephantColumn)));
        tableSettings.add(new Elephant(team,new Position(row,leftElephantColumn)));
        tableSettings.add(new Horse(team,new Position(row,rightHorseColumn)));
        tableSettings.add(new Horse(team,new Position(row,leftHorseColumn)));
        return tableSettings;
    }
}
