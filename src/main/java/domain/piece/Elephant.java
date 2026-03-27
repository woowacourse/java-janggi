package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public class Elephant extends Piece {
    public Elephant(Team team) {
        super(team, PieceType.ELEPHANT);
    }

    /**
     * 1. 직진 2칸 + 대각선 1칸만 이동 가능
     * 2. 도착지에 같은 팀이 존재하는 경우 이동 불가
     * 3. 도착지랑 출발지 사이에 말이 존재하지 않는 경우 이동 가능
     * 4. 이외는 이동 가능
     */
    @Override
    boolean canMove(Position from, Position to, Board board) {
        return false;
    }
}
