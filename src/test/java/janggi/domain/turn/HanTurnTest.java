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

public class HanTurnTest {
    @Test
    void 항상_끝났음을_false로_반환한다() {
        Board board = new Board(BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG), 0 ,0);
        HanTurn hanTurn = new HanTurn(board, 1);

        assertThat(hanTurn.isFinished()).isFalse();
    }

    @Test
    void 이동_시_게임이_종료되지_않을_때_다음_턴은_초의_턴이며_턴_수가_1_증가한다() {
        Board board = new Board(BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG), 0, 0);
        HanTurn hanTurn = new HanTurn(board, 1);
        TurnState turnState = hanTurn.move(new Position(4, 1), new Position(5 , 1));

        PlayerTurn nextTurn = turnState.playerTurn();

        assertThat(nextTurn).isInstanceOf(ChoTurn.class);
        assertThat(nextTurn.getCurrentTurn()).isEqualTo(2);
    }

    @Test
    void 최대_턴에_도달하면_게임이_종료되고_FinishTurn으로_반환한다() {
        Board board = new Board(BoardInitializer.createBoard(Arrangement.MA_SANG_MA_SANG, Arrangement.MA_SANG_MA_SANG), 0, 0);
        HanTurn hanTurn = new HanTurn(board, 200);

        TurnState turnState = hanTurn.move(new Position(4, 1), new Position(5 , 1));

        assertThat(turnState.playerTurn()).isInstanceOf(FinishTurn.class);
    }

    @Test
    void 궁을_잡아_게임이_끝나면_게임이_종료되고_FinishTurn으로_반환한다() {
        Map<Position, Piece> initBoard = new HashMap<>(Map.of(new Position(2, 5), new Gung(Side.HAN), new Position(8, 5), new Pawn(Side.HAN), new Position(9, 5), new Gung(Side.CHO)));
        Board board = new Board(initBoard, 0, 0);
        HanTurn hanTurn = new HanTurn(board, 2);

        TurnState turnState = hanTurn.move(new Position(8, 5), new Position(9, 5));

        assertThat(turnState.playerTurn()).isInstanceOf(FinishTurn.class);
    }

    @Test
    void 게임_진행_중_승자를_조회하면_현재_점수가_높은_진영을_반환한다() {
        Map<Position, Piece> initBoard = new HashMap<>(Map.of(new Position(2, 5), new Gung(Side.HAN), new Position(8, 5), new Pawn(Side.HAN), new Position(9, 5), new Gung(Side.CHO)));
        Board board = new Board(initBoard, 5, 10);
        ChoTurn choTurn = new ChoTurn(board, 1);

        assertThat(choTurn.getWinnerSide()).isEqualTo(Side.CHO);
    }

    @Test
    void 상속받은_공통_로직에_대해서_제대로_반환한다() {
        int turn = 5;
        Map<Position, Piece> initBoard = new HashMap<>(Map.of(new Position(2, 5), new Gung(Side.HAN), new Position(8, 5), new Pawn(Side.HAN), new Position(9, 5), new Gung(Side.CHO)));
        Board board = new Board(initBoard, 0, 0);

        HanTurn hanTurn = new HanTurn(board, turn);

        assertThat(hanTurn.isFinished()).isFalse();
        assertThat(hanTurn.getCurrentSide()).isEqualTo(Side.HAN);
        assertThat(hanTurn.getCurrentTurn()).isEqualTo(turn);
    }
}
