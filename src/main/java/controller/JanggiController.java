package controller;

import domain.board.Board;
import domain.board.BoardLocation;
import domain.game.JanggiGame;
import domain.game.Turn;
import domain.piece.Piece;
import domain.piece.Team;
import java.util.Map;
import service.GameService;
import view.ConsoleView;

public class JanggiController {

    private final ConsoleView consoleView;
    private final GameService gameService;

    public JanggiController(ConsoleView consoleView, GameService gameService) {
        this.consoleView = consoleView;
        this.gameService = gameService;
    }

    public void start(JanggiGame janggiGame) {
        if (!gameService.isGameContinuing()){
            janggiGame = createJanggiGame();
            gameService.initializeGame();
        }
        consoleView.showBoard(janggiGame.getBoard().getPieces());
        boolean isGameStopped = false;
        while (!isGameStopped) {
            try {
                consoleView.showScore(janggiGame.getTotalScore(Team.HAN), janggiGame.getTotalScore(Team.CHO));
                consoleView.showTurn(janggiGame.getTurn());
                int selectNumber = consoleView.showSelect();
                if (selectNumber == 1){
                    gameService.saveTurn(janggiGame.getTurn());
                    break;
                }
                BoardLocation current = consoleView.requestCurrent();
                BoardLocation destination = consoleView.requestDestination();

                janggiGame.process(current, destination);
                isGameStopped = janggiGame.isGameStopped();

                consoleView.showBoard(janggiGame.getBoard().getPieces());
            } catch (RuntimeException e) {
                consoleView.showMessage(e.getMessage());
            }
        }
        if (isGameStopped){
            consoleView.showWinner(janggiGame.getTurn());
        }
    }

    private JanggiGame createJanggiGame() {
        Map<BoardLocation, Piece> placements = consoleView.requestPlacements();
        Board board = Board.createWithPieces(placements);
        Turn turn = Turn.getStartingTurn();
        return new JanggiGame(board, turn);
    }
}
