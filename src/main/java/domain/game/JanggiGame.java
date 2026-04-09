package domain.game;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.coordination.Coordination;
import domain.piece.Team;
import dto.BoardRowDetails;
import dto.BoardViewSnapshot;
import dto.GameSaveRequest;

import java.util.List;

public class JanggiGame {

    private final Board board;
    private Turn turn;

    private JanggiGame(Board board, Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    public static JanggiGame start(String inputCho, String inputHan) {
        return new JanggiGame(BoardFactory.initialize(inputCho, inputHan), Turn.CHO);
    }

    public static JanggiGame resume(Board board, Turn turn) {
        return new JanggiGame(board, turn);
    }

    public GameSaveRequest toSaveRequest() {
        return new GameSaveRequest(BoardRowDetails.from(this.board), turn.getName());
    }

    public String getTurnName() {
        return turn.getName();
    }

    public String getWinnerName() {
        return turn.reverse().getName();
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

    public BoardViewSnapshot gameSnapshot() {
        return BoardViewSnapshot.from(this.board);
    }

    public double getScore(Team team) {
        return board.calculateScore(team);
    }
}
