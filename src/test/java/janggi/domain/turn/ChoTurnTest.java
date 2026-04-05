package janggi.domain.turn;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Arrangement;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Pawn;
import janggi.domain.piece.Piece;
import janggi.initializer.BoardInitializer;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ChoTurnTest {
    @Test
    void 항상_끝났음을_false로_반환한다() {
        Board board = new Board(BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG), 0 ,0);
        ChoTurn choTurn = new ChoTurn(board, 1);

        assertThat(choTurn.isFinished()).isFalse();
    }

    @Test
    void 이동_시_게임이_종료되지_않을_때_다음_턴은_한의_턴이며_턴_수가_1_증가한다() {
        Board board = new Board(BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG), 0, 0);
        ChoTurn choTurn = new ChoTurn(board, 1);
        TurnState turnState = choTurn.move(new Position(7, 1), new Position(6 , 1));

        PlayerTurn nextTurn = turnState.playerTurn();

        assertThat(nextTurn).isInstanceOf(HanTurn.class);
        assertThat(nextTurn.getCurrentTurn()).isEqualTo(2);
    }

    @Test
    void 최대_턴에_도달하면_게임이_종료되고_FinishTurn으로_반환한다() {
        Board board = new Board(BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG), 0, 0);
        ChoTurn choTurn = new ChoTurn(board, 200);

        TurnState turnState = choTurn.move(new Position(7, 1), new Position(6 , 1));

        assertThat(turnState.playerTurn()).isInstanceOf(FinishTurn.class);
    }

    @Test
    void 궁을_잡아_게임이_끝나면_게임이_종료되고_FinishTurn으로_반환한다() {
        Map<Position, Piece> initBoard = new HashMap<>(Map.of(new Position(9, 5), new Gung(Side.CHO), new Position(3, 5), new Pawn(Side.CHO), new Position(2, 5), new Gung(Side.HAN)));
        Board board = new Board(initBoard, 0, 0);
        ChoTurn choTurn = new ChoTurn(board, 2);

        TurnState turnState = choTurn.move(new Position(3, 5), new Position(2, 5));

        assertThat(turnState.playerTurn()).isInstanceOf(FinishTurn.class);
    }

    @Test
    void 게임_진행_중_승자를_조회하면_현재_점수가_높은_진영을_반환한다() {
        Map<Position, Piece> initBoard = new HashMap<>(Map.of(new Position(9, 5), new Gung(Side.CHO), new Position(3, 5), new Pawn(Side.CHO), new Position(2, 5), new Gung(Side.HAN)));
        Board board = new Board(initBoard, 10, 5);
        ChoTurn choTurn = new ChoTurn(board, 1);

        assertThat(choTurn.getWinnerSide()).isEqualTo(Side.HAN);
    }

    @Test
    void 상속받은_공통_로직에_대해서_제대로_반환한다() {
        int turn = 5;
        Map<Position, Piece> initBoard = new HashMap<>(Map.of(new Position(9, 5), new Gung(Side.CHO), new Position(3, 5), new Pawn(Side.CHO), new Position(2, 5), new Gung(Side.HAN)));
        Board board = new Board(initBoard, 0, 0);

        ChoTurn choTurn = new ChoTurn(board, turn);

        assertThat(choTurn.isFinished()).isFalse();
        assertThat(choTurn.getCurrentSide()).isEqualTo(Side.CHO);
        assertThat(choTurn.getCurrentTurn()).isEqualTo(turn);
    }
}
