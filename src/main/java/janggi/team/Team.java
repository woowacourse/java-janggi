package janggi.team;

import janggi.board.TableOption;
import janggi.piece.Piece;
import janggi.position.Row;

import java.util.Arrays;
import java.util.List;

public enum Team {
    CHO("초", new Row(10)),
    HAN("한", new Row(1));

    private final String value;
    private final Row StartingRow;

    Team(String value, Row row) {
        this.value = value;
        this.StartingRow = row;
    }

    public static Team from(String teamName) {
        return Arrays.stream(Team.values()).filter(team -> team.getValue().equals(teamName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("팀 이름이 존재하지 않습니다"));
    }

    public List<Piece> locatePiece(TableOption option) {
        return option.generateTableSetPieces(this, StartingRow);
    }

    public String getValue() {
        return value;
    }
}
