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
    private final boolean isOver;

    public JanggiGame(Board board, Turn turn, boolean isOver) {
        this.board = board;
        this.turn = turn;
        this.isOver = isOver;
    }

    public JanggiGame(Board board) {
        this(board, Turn.CHO_TURN, false);
    }

    public static JanggiGame of(SangSetupType choSangSetup, SangSetupType hanSangSetup) {
        return new JanggiGame(SangSetup.initialize(choSangSetup, hanSangSetup));
    }

    public Board getBoard() {
        return board;
    }

    public boolean isOver() {
        return isOver;
    }

    public Side getTurnSide() {
        return turn.getSide();
    }

    public Score calculateScoreOf(Side side) {
        Score score = board.calculateScoreOf(side);
        if (side.isCho()) {
            return score;
        }
        return score.addHandicap();
    }

    public Side getWinnerSide() {
        if (!isOver) {
            throw new IllegalArgumentException("게임이 종료되지 않아 승리 진영을 조회할 수 없습니다.");
        }
        // TODO: 점수비교
        return getTurnSide();
    }

    public JanggiGame move(Position departure, Position destination) {
        validateMoveRequest(departure);
        Optional<PieceType> capturedPieceType = board.getPieceTypeAt(destination);

        Board updatedBoard = board.move(departure, destination);
        if (capturedPieceType.isEmpty()) {
            return nextTurn(updatedBoard);
        }
        return createNextGame(updatedBoard, capturedPieceType.get());
    }

    private void validateMoveRequest(Position departure) {
        // TODO: 게임 진행 상태 객체화 고민해보기 (과한지? 합리적인지?)
        if (isOver) {
            throw new IllegalArgumentException("게임이 종료되어 더 이상 말을 이동시킬 수 없습니다.");
        }
        board.validateDeparturePiece(departure, turn);
    }

    private JanggiGame createNextGame(Board updatedBoard, PieceType targetPieceType) {
        if (targetPieceType.isGung()) {
            return gameOver(updatedBoard);
        }
        return nextTurn(updatedBoard);
    }

    private JanggiGame nextTurn(Board updatedBoard) {
        return new JanggiGame(updatedBoard, turn.other(), false);
    }

    private JanggiGame gameOver(Board updatedBoard) {
        return new JanggiGame(updatedBoard, turn, true);
    }
}
