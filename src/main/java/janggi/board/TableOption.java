package janggi.board;

import janggi.piece.DefaultPiece;
import janggi.piece.PieceType;
import janggi.position.Position;
import janggi.team.Team;
import janggi.piece.Piece;

import java.util.HashMap;
import java.util.Map;

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

    public Map<Position, Piece> generateTableSetPieces(Team team, int row) {
        Map<Position,Piece> tableSettings = new HashMap<>();
        tableSettings.put(new Position(row, rightElephantColumn) ,new DefaultPiece(team, PieceType.ELEPHANT));
        tableSettings.put(new Position(row, leftElephantColumn) ,new DefaultPiece(team, PieceType.ELEPHANT));
        tableSettings.put(new Position(row, rightHorseColumn) ,new DefaultPiece(team, PieceType.HORSE));
        tableSettings.put(new Position(row, leftHorseColumn) ,new DefaultPiece(team, PieceType.HORSE));
        return tableSettings;
    }
}
