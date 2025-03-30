package janggi.domain.game;

import janggi.console.GameStatus;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.util.EnumMap;

public final class Game {

    private Team turn;
    private final Board board;

    public Game(final Team turn, final Board board) {
        this.turn = turn;
        this.board = board;
    }

    public Game(final Board board) {
        this(Team.CHO, board);
    }

    public void move(final Position source, final Position destination) {
        final Piece piece = board.get(source);
        validateTurn(piece);
        validatePositions(source, destination, piece);
        piece.validateMove(source, destination, board);
        board.move(source, destination, piece);
        turn = turn.opposite();
    }

    public EnumMap<Team, Double> getTeamPoints() {
        return board.getTeamPoints();
    }

    public GameStatus getStatus() {
        if (isEnd() && turn == Team.CHO) {
            return GameStatus.HAN_WIN;
        }
        if (isEnd() && turn == Team.HAN) {
            return GameStatus.CHO_WIN;
        }
        return GameStatus.PLAYING;
    }

    private boolean isEnd() {
        return !board.hasGeneralOf(Team.HAN) || !board.hasGeneralOf(Team.CHO);
    }

    public boolean hasPieceAt(final Position position) {
        return board.hasPieceAt(position);
    }

    public Piece getPieceAt(final Position position) {
        return board.get(position);
    }

    private void validateTurn(final Piece piece) {
        if (piece.team() != turn) {
            throw new IllegalArgumentException(String.format("[ERROR] %s팀의 순서가 아닙니다.", piece.team().getName()));
        }
    }

    private void validatePositions(final Position source, final Position destination, final Piece piece) {
        if (board.hasPieceAt(destination, piece.team())) {
            throw new IllegalArgumentException("[ERROR] 같은 팀의 기물은 잡을 수 없습니다.");
        }
        if (source.equals(destination)) {
            throw new IllegalArgumentException("[ERROR] 본인의 위치로는 이동할 수 없습니다.");
        }
    }

    public Team getTurn() {
        return turn;
    }
}
