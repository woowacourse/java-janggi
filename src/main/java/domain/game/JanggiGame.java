package domain.game;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.coordination.Coordination;
import dto.BoardSnapshot;

import java.util.List;

public class JanggiGame {

    private final Board board;
    private Turn turn = Turn.CHO;

    private JanggiGame(Board board) {
        this.board = board;
    }

    public static JanggiGame of(String inputCho, String inputHan) {
        return new JanggiGame(BoardFactory.create(inputCho, inputHan));
    }

    public String getTurnName() {
        return turn.getName();
    }

    public String getWinnerName() {
        return turn.getName();
    }

    public void checkSameTeam(List<Integer> inputTokens) {
        Coordination coordination = Coordination.of(inputTokens.get(0), inputTokens.get(1));
        board.checkSameTeam(coordination, turn);
    }

    public boolean isGameEnd() {
        return !board.hasTwoGenerals();
    }

    public void start(List<Integer> from, List<Integer> to) {
        board.move(
                Coordination.of(from.get(0), from.get(1)),
                Coordination.of(to.get(0), to.get(1))
        );

        turn = turn.reverse();
    }

    public BoardSnapshot captureBoard() {
        return BoardSnapshot.from(this.board);
    }
}
