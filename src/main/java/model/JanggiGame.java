package model;

import java.util.List;
import java.util.Map;
import model.board.Board;
import model.coordinate.Position;
import model.piece.Piece;
import model.state.Finished;
import model.state.Running;

public class JanggiGame {
    private final Board board;
    private JanggiState state;

    public JanggiGame(Board board) {
        this(board, new Running(Team.startTurn(), GameStatus.PLAYING));
    }

    public JanggiGame(Board board, JanggiState state) {
        this.board = board;
        this.state = state;
    }

    public void movePiece(Position current, Position next) {
        Piece piece = selectPiece(current);

        List<Position> path = piece.pathTo(current, next);
        List<Piece> pieces = board.extractPiecesByPath(path);
        piece.validatePathCondition(pieces);

        board.move(current, next);
        this.state = state.next(board);
    }

    public Piece selectPiece(Position position) {
        Piece piece = board.pickPiece(position);
        validateAlly(piece);
        return piece;
    }

    public Team resolveWinner() {
        return state.resolveWinner(board);
    }

    public Map<Team, Double> calculateFinalScore() {
        return JanggiReferee.collectScores(board);
    }

    public void finishByBigJang() {
        if (state.status() != GameStatus.BIG_JANG) {
            throw new IllegalStateException("현재 빅장 상태가 아닙니다.");
        }
        Team winner = JanggiReferee.judgeBigJangWinner(board);
        this.state = new Finished(winner, GameStatus.BIG_JANG_DONE);
    }

    public boolean canPlaying() {
        return state.canPlaying();
    }

    public boolean isBigJang() {
        return state.status() == GameStatus.BIG_JANG;
    }

    public boolean isBigJangDone() {
        return state.status() == GameStatus.BIG_JANG_DONE;
    }

    private void validateAlly(Piece piece) {
        if (!piece.isSameTeam(turn())) {
            throw new IllegalArgumentException(turn().getName() + "의 기물이 아닙니다.");
        }
    }

    public Team turn() {
        return state.turn();
    }

    public GameStatus status() {
        return state.status();
    }

    public Map<Position, Piece> getBoard() {
        return board.board();
    }
}
