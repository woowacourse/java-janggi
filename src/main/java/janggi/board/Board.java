package janggi.board;

import janggi.position.Position;
import janggi.team.Team;

public class Board {
    private final BoardCho boardCho;
    private final BoardHan boardHan;

    public Board(BoardCho boardCho, BoardHan boardHan) {
        this.boardCho = boardCho;
        this.boardHan = boardHan;
    }

    public boolean isAvailablePiece(Team team, String pieceName, Position position) {
        if (team.equals(Team.CHO)){
            return boardCho.containPiece(pieceName, position);
        }
        return boardHan.containPiece(pieceName, position);
    }
}
