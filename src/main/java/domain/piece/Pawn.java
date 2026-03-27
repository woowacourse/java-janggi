package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public class Pawn extends Piece {
    public Pawn(Team team) {
        super(team, PieceType.PAWN);
    }

    @Override
    public boolean canMove(Position from, Position to, Board board) {
        /**
         * TODO:
         * 0. 졸/병이 실제로 이동 가능한 거리인가 확인
         * 1. 이동 위치에, 같은 팀이 있는 경우 예외
         * 2. 판의 범위를 넘어서는 경우 예외 -> 옆인지, 앞인지로 분기
         * 3. 초나라 구현 후 한나라 상황을 고려하여 수정한다.
         */

        return isEmptySpace(from, to, board);
    }

    private boolean isEmptySpace(Position from, Position to, Board board) {
        return board.isEmpty(to);
    }
}
