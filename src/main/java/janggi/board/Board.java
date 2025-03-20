package janggi.board;

import janggi.piece.Piece;
import janggi.position.Position;
import janggi.team.Team;

import java.util.List;

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

    public boolean checkLegalMove(List<Position> positionsOnPath) {
       return boardHan.isLegalMove(positionsOnPath) || boardCho.isLegalMove(positionsOnPath);
    }

    // 포의 경우, 자기 자신과 같은 종류인 경우, 건너뛰기 불가
    public void checkLegalMoveForCannon() {

    }
}
