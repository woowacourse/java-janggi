package game;

import piece.Piece;
import piece.PieceType;
import store.Board;
import location.Position;
import location.PathUtility;
import store.Pieces;
import store.Player;
import view.AnswerType;
import view.InputView;
import view.OutputView;

public class JanggiGame {
    private static final int HORIZONTAL_START = 1;
    private static final int HORIZONTAL_END = 9;
    private static final int VERTICAL_START = 1;
    private static final int VERTICAL_END = 10;

    private final Board gameBoard;

    public JanggiGame(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void showInitialBoard() {
        OutputView.displayBoard(gameBoard);
    }

    public void run() {
        Team winTeam = play();
        Player greenPlayer = gameBoard.findPlayerBy(Team.GREEN);
        Player redPlayer = gameBoard.findPlayerBy(Team.RED);
        if(winTeam.isNotDecided()) {
            if(greenPlayer.calculateTotalScore() > redPlayer.calculateTotalScore()) {
                winTeam = Team.GREEN;
            }
            if (greenPlayer.calculateTotalScore() < redPlayer.calculateTotalScore()) {
                winTeam = Team.RED;
            }
        }
        OutputView.displayResult(winTeam, greenPlayer.calculateTotalScore(), redPlayer.calculateTotalScore());
    }

    private Team play() {
        Team currentTeam = Team.RED;
        while (true) {
            currentTeam = Team.findOpponentBy(currentTeam);
            Player currentPlayer = gameBoard.findPlayerBy(currentTeam);

            AnswerType isEndAnswer = InputView.requestEndGame();
            if(isEndAnswer.isPositive()) {
                return Team.NONE;
            }

            Position start = requestMovementStartPosition(currentPlayer);
            Position end = InputView.requestMovementEndPosition();
            validateRange(end);

            currentPlayer.checkPlayerPieceAlreadyInDestination(end);
            PathUtility.checkNotSameStartWithEnd(start, end);

            move(currentPlayer, start, end);
            boolean isGeneralCatched = catchPiece(currentPlayer, Team.findOpponentBy(currentTeam), end);
            if(isGeneralCatched) {
                return currentTeam;
            }
            OutputView.displayBoard(gameBoard);
        }
    }

    private Position requestMovementStartPosition(Player player) {
        while (true) {
            Position start = InputView.requestMoveStartPosition();
            validateRange(start);

            if (player.isContainedPiece(start)) {
                return start;
            }
            OutputView.displayWrongPoint();
        }
    }

    public void move(Player currentPlayer, Position start, Position end) {
        Pieces allPieces = gameBoard.findAllPieces();
        Piece piece = currentPlayer.getPieceByPoint(start);

        piece.validateDestination(end);
        piece.validatePaths(allPieces, end);
        Piece movedPiece = piece.move(end);

        currentPlayer.delete(piece);
        currentPlayer.add(movedPiece);
    }

    private boolean catchPiece(Player currentPlayer, Team opponent, Position end) {
        Player opponentPlayer = gameBoard.findPlayerBy(opponent);
        if (opponentPlayer.isContainedPiece(end)) {
            Piece opponentPiece = opponentPlayer.getPieceByPoint(end);
            currentPlayer.catchPiece(opponentPiece);
            opponentPlayer.delete(opponentPiece);
            return PieceType.isGeneral(opponentPiece);
        }
        return false;
    }

    private void validateRange(Position position) {
        if (position.x() < HORIZONTAL_START || position.x() > HORIZONTAL_END ||
                position.y() < VERTICAL_START || position.y() > VERTICAL_END) {
            throw new IllegalArgumentException("[ERROR] 위치할 수 없는 좌표입니다.");
        }
    }
}
