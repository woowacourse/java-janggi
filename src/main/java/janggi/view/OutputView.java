package janggi.view;

import janggi.domain.Board;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import janggi.domain.piece.TeamType;

import java.util.Map;

public class OutputView {

    public void printBoard(Board board) {
        Map<Position, Piece> pieces = board.getPieces();
        for(int i = 1; i <= 10; i ++) {
            for(int j = 1; j <= 9; j ++) {
                Piece piece = pieces.get(new Position(i, j));
                System.out.printf("%s%2s%s ", getTeamColor(piece), piece.getName(), "\u001B[0m");
            }
            System.out.println();
        }
    }

    private String getTeamColor(Piece piece) {
        if(piece.getTeamType().equals(TeamType.BLUE)) {
            return "\u001B[34m";
        }
        if(piece.getTeamType().equals(TeamType.RED)) {
            return "\u001B[31m" ;
        }
        return "\u001B[30m" ;
    }
}
