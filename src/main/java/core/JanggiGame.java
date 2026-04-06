package core;

import board.Board;
import board.SangSetup;
import board.SangSetupType;
import java.util.Optional;
import participant.Score;
import participant.Turn;
import pieces.PieceType;
import pieces.Side;
import position.Position;

public class JanggiGame {

    private final Board board;
    private final Turn turn;
    private final GameStatus status;

    public JanggiGame(Board board, Turn turn, GameStatus status) {
        this.board = board;
        this.turn = turn;
        this.status = status;
    }

    public static JanggiGame startWith(Board board) {
        return new JanggiGame(board, Turn.CHO_TURN, GameStatus.PLAYING);
    }

    public static JanggiGame of(SangSetupType choSangSetup, SangSetupType hanSangSetup) {
        return JanggiGame.startWith(SangSetup.initialize(choSangSetup, hanSangSetup));
    }

    public Board getBoard() {
        return board;
    }

    public boolean isOver() {
        return status.isOver();
    }

    public Side getTurnSide() {
        return turn.getSide();
    }

    public Turn getTurn() {
        return turn;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Score calculateScoreOf(Side side) {
        Score score = board.calculateScoreOf(side);
        if (side.isCho()) {
            return score;
        }
        return score.addHandicap();
    }

    public GameStatus getResult() {
        if (!status.isOver()) {
            throw new IllegalArgumentException("게임이 종료되지 않아 승리 진영을 조회할 수 없습니다.");
        }
        return status;
    }

    public JanggiGame endByScore() {
        if (this.status.isOver()) {
            throw new IllegalArgumentException("이미 종료된 게임입니다.");
        }

        Score choScore = calculateScoreOf(Side.CHO);
        Score hanScore = calculateScoreOf(Side.HAN);
        if (choScore.isGreaterThan(hanScore)) {
            return new JanggiGame(board, turn, GameStatus.CHO_WIN_BY_GUNG);
        }
        return new JanggiGame(board, turn, GameStatus.HAN_WIN_BY_SCORE);
    }

    public JanggiGame move(Position departure, Position destination) {
        validateMoveRequest(departure, destination);
        Optional<PieceType> capturedPieceType = board.getPieceTypeAt(destination);

        Board updatedBoard = board.move(departure, destination);
        if (capturedPieceType.isEmpty()) {
            return nextTurn(updatedBoard);
        }
        return createNextGame(updatedBoard, capturedPieceType.get());
    }

    private void validateMoveRequest(Position departure, Position destination) {
        if (status.isOver()) {
            throw new IllegalArgumentException("게임이 종료되어 더 이상 말을 이동시킬 수 없습니다.");
        }
        if (departure.equals(destination)) {
            throw new IllegalArgumentException("출발지와 도착지는 동일할 수 없습니다.");
        }
        board.validateDeparturePiece(departure, turn);
    }

    private JanggiGame createNextGame(Board updatedBoard, PieceType targetPieceType) {
        if (targetPieceType.isGung()) {
            return gameOverByGung(updatedBoard);
        }
        return nextTurn(updatedBoard);
    }

    private JanggiGame nextTurn(Board updatedBoard) {
        return new JanggiGame(updatedBoard, turn.other(), GameStatus.PLAYING);
    }

    private JanggiGame gameOverByGung(Board updatedBoard) {
        if (turn.isCho()) {
            return new JanggiGame(updatedBoard, turn, GameStatus.CHO_WIN_BY_GUNG);
        }
        return new JanggiGame(updatedBoard, turn, GameStatus.HAN_WIN_BY_GUNG);
    }
}