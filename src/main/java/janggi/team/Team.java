package janggi.team;

import janggi.board.TableOption;
import janggi.piece.Piece;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Team {
    CHO("초", new Row(1)),
    HAN("한", new Row(10));

    private final String value;
    private final Row initialRow;

    Team(String value, Row row) {
        this.value = value;
        this.initialRow = row;
    }

    public static Team from(String teamName) {
        return Arrays.stream(Team.values()).filter(team -> team.getValue().equals(teamName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("팀 이름이 존재하지 않습니다"));
    }

    public List<Piece> locatePiece(Team team, TableOption option) {
        return option.generateTableSetPieces(this, initialRow);
    }

    public String getValue() {
        return value;
    }
}
