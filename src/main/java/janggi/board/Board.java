package janggi.board;

import janggi.piece.Piece;
import janggi.piece.Team;
import janggi.piece.Type;
import janggi.position.Position;
import janggi.score.Score;
import janggi.score.ScoreBoard;
import janggi.turn.ChoTurn;
import janggi.turn.Turn;
import java.util.Map;

public final class Board {

    private final Map<Position, Piece> board;
    private Turn turn;
    private final int setupOption;

    public Board(final Map<Position, Piece> board, final Turn turn, final int setupOption) {
        this.board = board;
        this.turn = turn;
        this.setupOption = setupOption;
    }

    public Board(final Map<Position, Piece> board, final int setupOption) {
        this(board, new ChoTurn(), setupOption);
    }

    public void move(final Position start, final Position end) {
        validateStartPosition(start);
        final Piece piece = board.get(start);
        validateTurn(piece);
        validateRoute(start, end, piece);
        board.remove(start);
        board.put(end, piece);
        turn = turn.nextTurn();
    }

    public boolean isPresentSameTeam(final Team team, final Position position) {
        if (board.containsKey(position)) {
            final Piece piece = board.get(position);
            return piece.isSameTeam(team);
        }
        return false;
    }

    public boolean isPresent(final Position position) {
        return board.containsKey(position);
    }

    public boolean isExistCannon(final Position position) {
        if (board.containsKey(position)) {
            final Piece piece = board.get(position);
            return piece.getType() == Type.CANNON;
        }
        return false;
    }

    public Piece getPiece(final Position position) {
        return board.get(position);
    }

    public Team getTurn() {
        return turn.getTeam();
    }

    public ScoreBoard calculateScoreBoard() {
        final ScoreBoard scoreBoard = new ScoreBoard();
        for (Piece piece : board.values()) {
            final Team team = piece.getTeam();
            final Score score = Type.getScore(piece.getType());
            scoreBoard.add(team, score);
        }
        return scoreBoard;
    }

    public boolean isGeneralDead() {
        int generalCount = (int) board.values().stream()
                .filter(piece -> piece.getType() == Type.GENERAL)
                .count();
        return generalCount == 1;
    }

    public Team findWinner() {
        if (isGeneralDead()) {
            return board.values().stream()
                    .filter(piece -> piece.getType() == Type.GENERAL)
                    .map(Piece::getTeam)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("[ERROR] 로직이 잘못됐습니다."));
        }
        return calculateScoreBoard().getWinner();
    }

    public int getSetupOption() {
        return setupOption;
    }

    private void validateStartPosition(final Position start) {
        if (!board.containsKey(start)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d%d 위치에 기물이 없습니다.", start.getRowValue(), start.getColumnValue()));
        }
    }

    private void validateTurn(final Piece piece) {
        if (!turn.isMovingSameTeam(piece.getTeam())) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] 다른 팀의 기물을 움직이고 있습니다."));
        }
    }

    private void validateRoute(final Position start, final Position end, final Piece piece) {
        if (!piece.canMove(start, end, this)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d%d 위치로 이동할 수 없습니다.", end.getRowValue(), end.getColumnValue()));
        }
    }
}
