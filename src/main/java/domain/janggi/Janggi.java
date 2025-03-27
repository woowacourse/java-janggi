package domain.janggi;

import domain.board.Board;
import domain.board.BoardPosition;
import domain.piece.Piece;
import java.util.Map;

public class Janggi {

    private final int id;
    private final String title;
    private final Board board;
    private final Turn turn;

    public Janggi(
            final int id,
            final String title,
            final Board board,
            final Turn turn
    ) {
        validateNotNull(title, board, turn);
        validateTitle(title);
        this.id = id;
        this.title = title;
        this.board = board;
        this.turn = turn;
    }

    public void processTurn(
            final BoardPosition selectPosition,
            final BoardPosition destinationPosition
    ) {
        board.movePiece(
                selectPosition,
                destinationPosition,
                turn.currentTeam()
        );

        turn.change();
    }

    public Score findScore(final Team team) {
        return board.calculateScore().get(team);
    }

    public boolean isGameFinish() {
        return board.findAliveGenerals().size() == 1;
    }

    public Team findWinnerTeam() {
        if (!isGameFinish()) {
            throw new IllegalCallerException("게임이 종료되지 않았습니다.");
        }
        return board.findAliveGenerals()
                .getLast()
                .getTeam();
    }

    public Janggi takeSnapshot() {
        return new Janggi(
                id, title,
                new Board(board.getPieces()),
                new Turn(turn.currentTeam())
        );
    }

    private void validateNotNull(
            final String title,
            final Board board,
            final Turn turn
    ) {
        if (title == null || board == null || turn == null) {
            throw new IllegalArgumentException("보드는 게임 정보와 보드 그리고 현재 턴을 가져야합니다.");
        }
    }

    private void validateTitle(final String title) {
        if (title.isBlank() || title.length() > 30) {
            throw new IllegalArgumentException("장기 게임방 제목은 공백이거나 30자를 초과할 수 없습니다.");
        }
    }

    public static Janggi initialize(final int id, final String title) {
        final Board board = Board.initialize();
        final Turn turn = new Turn(Team.GREEN);
        return new Janggi(id, title, board, turn);
    }

    public Map<BoardPosition, Piece> getPieces() {
        return board.getPieces();
    }

    public String getTitle() {
        return title;
    }

    public Team getCurrentTeam() {
        return turn.currentTeam();
    }

    public int getId() {
        return id;
    }
}
