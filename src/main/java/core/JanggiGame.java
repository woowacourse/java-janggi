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

    public Score calculateScoreOf(Side side) {
        Score score = board.calculateScoreOf(side);
        if (side.isCho()) {
            return score;
        }
        return score.addHandicap();
    }

    public Side getWinnerSide() {
        if (!status.isOver()) {
            throw new IllegalArgumentException("게임이 종료되지 않아 승리 진영을 조회할 수 없습니다.");
        }
        if (status.isChoWinByGung()) {
            return Side.CHO;
        }
        if (status.isHanWinByGung()) {
            return Side.HAN;
        }

        Score choScore = board.calculateScoreOf(Side.CHO);
        Score hanScore = board.calculateScoreOf(Side.HAN);
        if (choScore.isGreaterThan(hanScore)) {
            return Side.CHO;
        }
        return Side.HAN;
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
        if (status.isOver()) {
            throw new IllegalArgumentException("게임이 종료되어 더 이상 말을 이동시킬 수 없습니다.");
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
