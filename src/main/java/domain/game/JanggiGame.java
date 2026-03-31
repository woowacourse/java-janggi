package domain.game;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.coordination.Coordination;
import domain.game.dto.BoardDto;
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

    public boolean isGameEnd() {
        return !board.hasTwoGenerals();
    }

    public Turn turn() {
        return turn;
    }

    public BoardDto playTurn(List<Integer> from, List<Integer> to) {
        board.move(
                Coordination.of(from.get(0), from.get(1)),
                Coordination.of(to.get(0), to.get(1))
        );
        BoardDto boardDto = BoardDto.from(board.getBoard());
        turn = turn.reverse();
        return boardDto;
    }

    public void checkSameTeam(List<Integer> inputTokens, Turn turn) {
        board.checkSameTeam(inputTokens, turn);
    }
}
