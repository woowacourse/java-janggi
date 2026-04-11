package model.game;

import model.board.Board;
import model.board.Route;
import model.board.ScoreResult;
import model.coordinate.Position;
import model.piece.Piece;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Janggi {

    public static final int CANNON_HURDLE_COUNT = 1;
    private static final double HAN_BONUS = 1.5;

    private final Board board;
    private Team turn;
    private GameStatus status;

    public Janggi(Board board) {
        this(board, Team.CHO);
    }

    public Janggi(Board board, Team turn) {
        this.board = board;
        this.turn = turn;
        this.status = GameStatus.PLAYING;
    }

    public GameStatus move(Position current, Position next) {
        Piece piece = findPieceAt(current, turn);
        validateMovement(current, next, piece);

        Optional<Piece> capturedPiece = board.movePiece(current, next);
        if (capturedPiece.map(Piece::isGeneral).orElse(false)) {
            this.status = GameStatus.WIN_BY_CAPTURE;
            return status;
        }
        this.turn = turn.next();
        return status;
    }

    public Piece findPieceAt(Position position, Team turn) {
        Piece piece = board.pickPiece(position);
        if (piece.isEnemy(turn)) {
            throw new IllegalArgumentException(turn.getKoreanName() + "의 기물이 아닙니다.");
        }
        return piece;
    }

    private void validateMovement(Position current, Position next, Piece piece) {
        if (!piece.canMove(current, next)) {
            throw new IllegalArgumentException("해당 기물이 이동할 수 없는 위치입니다.");
        }

        if (piece.isCannon()) {
            validateMovementOfCannon(current, next, piece);
            return;
        }

        List<Position> path = piece.extractPath(current, next);
        Route route = board.createRoute(path);
        if (route.hasPiece()) {
            throw new IllegalArgumentException("이동 경로에 기물이 있어 이동할 수 없는 위치입니다.");
        }
    }

    private void validateMovementOfCannon(Position current, Position next, Piece piece) {
        List<Position> path = piece.extractPath(current, next);
        Route route = board.createRoute(path);
        int countedPieces = route.countPieces();
        if (countedPieces != CANNON_HURDLE_COUNT) {
            throw new IllegalArgumentException("포는 1개의 기물만 건너 뛰어야 합니다.");
        }
        if (route.hasCannon()) {
            throw new IllegalArgumentException("포는 포를 건너뛸 수 없습니다.");
        }
    }

    public boolean isPlaying() {
        return !status.isFinished();
    }

    public Team getWinnerByCapture() {
        return this.turn;
    }

    public ScoreResult calculateScoreResultOfTeams() {
        this.status = GameStatus.WIN_BY_SCORE;
        double hanScore = board.calculateScore(Team.HAN) + HAN_BONUS;
        double choScore = board.calculateScore(Team.CHO);
        return new ScoreResult(hanScore, choScore);
    }

    public void quit() {
        this.status = GameStatus.QUIT;
    }

    public Map<Position, Piece> board() {
        return board.getBoardMap();
    }

    public Team getTurn() {
        return turn;
    }
}
