package core;

import board.Board;
import board.SangSetup;
import board.SangSetupType;
import participant.Score;
import participant.Turn;
import pieces.Piece;
import pieces.Side;
import position.Position;

public class JanggiGame {

    private final Board board;
    private final Turn turn;
    private final GameStatus status;

    public JanggiGame(final Board board, final Turn turn, final GameStatus status) {
        this.board = board;
        this.turn = turn;
        this.status = status;
    }

    public static JanggiGame startWith(final Board board) {
        return new JanggiGame(board, Turn.CHO_TURN, GameStatus.PLAYING);
    }

    public static JanggiGame of(final SangSetupType choSangSetup, final SangSetupType hanSangSetup) {
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

    public Score calculateScoreOf(final Side side) {
        final Score score = board.calculateScoreOf(side);
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

    public Piece getPieceAt(Position position) {
        return board.getPieceAt(position);
    }

    public JanggiGame endByScore() {
        if (status.isOver()) {
            throw new IllegalArgumentException("이미 종료된 게임입니다.");
        }

        final Score choScore = calculateScoreOf(Side.CHO);
        final Score hanScore = calculateScoreOf(Side.HAN);
        if (choScore.isGreaterThan(hanScore)) {
            return new JanggiGame(board, turn, GameStatus.CHO_WIN_BY_GUNG);
        }
        return new JanggiGame(board, turn, GameStatus.HAN_WIN_BY_SCORE);
    }

    public JanggiGame move(final Position departure, final Position destination) {
        validateMoveRequest(departure, destination);
        final Piece capturedPiece = board.getPieceAt(destination);

        final Board updatedBoard = board.move(departure, destination);
        if (capturedPiece == null) {
            return nextTurn(updatedBoard);
        }
        return createNextGame(updatedBoard, capturedPiece);
    }

    private void validateMoveRequest(final Position departure, final Position destination) {
        if (status.isOver()) {
            throw new IllegalArgumentException("게임이 종료되어 더 이상 말을 이동시킬 수 없습니다.");
        }
        board.validatePositions(departure, turn);
    }

    private JanggiGame nextTurn(final Board updatedBoard) {
        return new JanggiGame(updatedBoard, turn.other(), GameStatus.PLAYING);
    }

    private JanggiGame createNextGame(final Board updatedBoard, final Piece capturedPiece) {
        if (capturedPiece.isGung()) {
            return gameOverByGung(updatedBoard);
        }
        return nextTurn(updatedBoard);
    }

    private JanggiGame gameOverByGung(final Board updatedBoard) {
        if (turn.isCho()) {
            return new JanggiGame(updatedBoard, turn, GameStatus.CHO_WIN_BY_GUNG);
        }
        return new JanggiGame(updatedBoard, turn, GameStatus.HAN_WIN_BY_GUNG);
    }
}
