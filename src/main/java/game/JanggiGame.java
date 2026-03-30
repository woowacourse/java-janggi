package game;

import board.Board;
import board.SangSetup;
import pieces.Side;
import position.Position;

public class JanggiGame {
    private final Board board;
    private boolean isChoTurn = true;

    public JanggiGame(Board board) {
        this.board = board;
    }

    public static JanggiGame of(SangSetup choSangSetup, SangSetup hanSangSetup) {
        Board choBoard = choSangSetup.initialize(Side.CHO);
        Board hanBoard = hanSangSetup.initialize(Side.HAN);
        return new JanggiGame(choBoard.merge(hanBoard));
    }

    public void move(Position departure, Position destination, Side side) {
        if (isChoTurn && side.isHan()) {
            throw new IllegalArgumentException("현재는 초의 공격 차례 입니다.");
        }
        if (!isChoTurn && side.isCho()) {
            throw new IllegalArgumentException("현재는 한의 공격 차례 입니다.");
        }
        board.move(departure, destination);
        isChoTurn = !isChoTurn;
    }

    public Side currentTurn() {
        return isChoTurn ? Side.CHO : Side.HAN;
    }

    public Board board() {
        return board;
    }
}
