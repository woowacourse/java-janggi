package controller;

import domain.board.Board;
import domain.board.BoardLocation;
import domain.game.JanggiGame;
import domain.game.Turn;
import domain.piece.Piece;
import domain.piece.Team;
import dto.BoardDto;
import dto.TurnDto;
import java.util.Map;
import java.util.Optional;
import service.GameService;
import service.PieceService;
import view.ConsoleView;

public class JanggiController {

    private final ConsoleView consoleView;
    private final GameService gameService;
    private final PieceService pieceService;

    public JanggiController(ConsoleView consoleView, GameService gameService, PieceService pieceService) {
        this.consoleView = consoleView;
        this.gameService = gameService;
        this.pieceService = pieceService;
    }

    public void start() {
        Optional<BoardDto> boardDto = pieceService.getAlivePieces();
        Optional<TurnDto> turnDto = gameService.findTurn();
        JanggiGame janggiGame;
        if (boardDto.isEmpty() || turnDto.isEmpty()) {
            janggiGame = initializeJanggiGame();
        } else {
            janggiGame = new JanggiGame(boardDto.get().toBoard(), turnDto.get().toTurn());
        }

        consoleView.showBoard(janggiGame.getBoard().getPieces());
        boolean isGameStopped = false;
        while (!isGameStopped) {
            try {
                consoleView.showScore(janggiGame.getTotalScore(Team.HAN), janggiGame.getTotalScore(Team.CHO));
                consoleView.showTurn(janggiGame.getTurn());
                BoardLocation current = consoleView.requestCurrent();
                BoardLocation destination = consoleView.requestDestination();

                janggiGame.process(current, destination);
                pieceService.movePiece(current, destination);
                gameService.saveTurn(janggiGame);
                isGameStopped = janggiGame.isGameStopped();
                consoleView.showBoard(janggiGame.getBoard().getPieces());
            } catch (RuntimeException e) {
                consoleView.showMessage(e.getMessage());
            }
        }
        consoleView.showWinner(janggiGame.getTurn());
    }

    private JanggiGame initializeJanggiGame() {
        JanggiGame janggiGame = createJanggiGame();
        gameService.insertInitializeGameTurn();
        pieceService.insertInitializePieceIfNotExists(janggiGame);
        return janggiGame;
    }

    private JanggiGame createJanggiGame() {
        Map<BoardLocation, Piece> placements = consoleView.requestPlacements();
        Board board = Board.createWithPieces(placements);
        Turn turn = Turn.getStartingTurn();
        return new JanggiGame(board, turn);
    }
}
