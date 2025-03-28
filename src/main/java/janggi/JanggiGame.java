package janggi;

import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Side;
import janggi.view.InputView;
import janggi.view.OutputView;

import java.util.List;

public class JanggiGame {

    private final InputView inputView;
    private final OutputView outputView;

    public JanggiGame(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void play() {
        JanggiBoard board = JanggiBoard.initializeWithPieces();
        while (true) {
            for (Side side : Side.getSides()) {
                Piece catchedPiece = playTurn(side, board);
                if (board.checkGameIsOver(side)) {
                    outputView.printEndMessage(side, catchedPiece);

                    printTotalScores(board);
                    return;
                }
            }
        }
    }

    private Piece playTurn(final Side side, JanggiBoard board) {
        while (true) {
            try {
                outputView.printBoard(board);
                outputView.printTurn(side);
                Position selectedPiecePosition = inputView.selectPiece();

                List<Position> reachableDestinations = computeReachableDestinations(side, board, selectedPiecePosition);
                return processMove(board, selectedPiecePosition, reachableDestinations);
            } catch (IllegalArgumentException | IllegalStateException e) {
                outputView.printExceptionMessage(e);
            }
        }
    }

    private List<Position> computeReachableDestinations(final Side side, final JanggiBoard board, final Position selectedPiecePosition) {
        List<Position> reachablePositions = board.computeReachableDestination(side, selectedPiecePosition);
        outputView.printReachableDestinations(reachablePositions);
        return reachablePositions;
    }

    private Piece processMove(final JanggiBoard board, final Position selectedPiecePosition, final List<Position> reachableDestinations) {
        Position destination = inputView.askMoveDestination();
        validateSelectedDestination(destination, reachableDestinations);

        Piece catchedPiece = board.moveOrCatchPiece(selectedPiecePosition, destination);
        outputView.printMoveResult(catchedPiece);
        return catchedPiece;
    }

    public void printTotalScores(final JanggiBoard board) {
        int choTotalScore = board.sumSideTotalScore(Side.CHO);
        int hanTotalScore = board.sumSideTotalScore(Side.HAN);
        outputView.printTotalScores(choTotalScore, hanTotalScore);
    }

    private void validateSelectedDestination(final Position destination, final List<Position> reachableDestinations) {
        if (!reachableDestinations.contains(destination)) {
            throw new IllegalArgumentException("[ERROR] 선택한 목적지로 이동할 수 없습니다.");
        }
    }
}
