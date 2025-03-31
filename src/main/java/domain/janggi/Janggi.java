package domain.janggi;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.BoardPosition;
import domain.piece.Score;
import domain.piece.Team;
import domain.turn.Turn;
import java.util.EnumMap;
import java.util.Map;

public class Janggi {

    private final Board board;
    private final Turn turn;

    public Janggi(
        final Board board,
        final Turn turn
    ) {
        validateNotNull(board, turn);
        this.board = board;
        this.turn = turn;
    }

    private void validateNotNull(
        final Board board,
        final Turn turn
    ) {
        if (board == null || turn == null) {
            throw new IllegalArgumentException("장기는 보드와 턴을 가져야합니다.");
        }
    }

    public static Janggi initialize() {
        final Board board = BoardFactory.createInitialBoard();
        final Turn turn = new Turn(Team.GREEN);

        return new Janggi(board, turn);
    }

    public void validateSelectedPiece(final BoardPosition selectBoardPosition) {
        board.findSelectedPiece(selectBoardPosition, turn.getCurrentTeam())
            .orElseThrow(() -> new IllegalArgumentException("해당 위치에 말이 없거나 상대팀의 말입니다."));
    }

    public void processTurn(
        final BoardPosition selectBoardPosition,
        final BoardPosition destinationBoardPosition
    ) {
        board.movePiece(selectBoardPosition, destinationBoardPosition, turn.getCurrentTeam());
        turn.proceed();
    }

    public Map<Team, Score> calculateTeamScores() {
        final Map<Team, Score> teamScore = new EnumMap<>(Team.class);
        for (final Team team : Team.values()) {
            teamScore.put(team, board.calculateScore(team));
        }

        return teamScore;
    }

    public boolean isGameOver() {
        return board.isOnlyOneKingLeft();
    }

    public Team calculateWinner() {
        if (!isGameOver()) {
            throw new IllegalStateException("게임이 종료되지 않았습니다.");
        }

        return turn.getCurrentTeam()
            .nextTeam();
    }

    public Board getBoard() {
        return board;
    }

    public Turn getTurn() {
        return turn;
    }
}
