package controller;

import domain.board.Board;
import domain.piece.PieceColor;
import dto.MoveCommandDTO;
import service.GameService;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final GameService gameService;
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiController(GameService gameService, InputView inputView, OutputView outputView) {
        this.gameService = gameService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        boolean loadGame = inputView.readLoadGameSelected();
        gameService.startGame(loadGame);
        Board board = gameService.getBoard();
        outputView.printBorad(board);

        while (!gameService.isGameFinished()) {
            try {
                playTurn(gameService.getTurnColor(), board);
            } catch (Exception e) {
                outputView.printError("[ERROR] " + e.getMessage());
            }
        }

        printResult();
    }

    private void printResult() {
        outputView.printWinner(gameService.getWinner());
        outputView.printTeamScore(gameService.getRedTeamScore(), PieceColor.RED);
        outputView.printTeamScore(gameService.getBlueTeamScore(), PieceColor.BLUE);
    }

    private void playTurn(PieceColor turnColor, Board board) {
        outputView.printTurnNotice(turnColor);
        MoveCommandDTO commands = inputView.readMoveCommand();

        gameService.playTurn(commands);

        outputView.printBorad(board);
    }
}
