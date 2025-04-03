package controller;

import domain.JanggiGame;
import domain.Position;
import domain.boardgenerator.JanggiBoardGenerator;
import domain.exception.DatabaseException;
import domain.game.GameService;
import domain.game.Games;
import domain.game.Status;
import domain.palace.Palace;
import domain.piece.Piece;
import domain.piece.PieceService;
import domain.player.Player;
import domain.player.PlayerService;
import domain.player.Players;
import java.util.List;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class JanggiController {

    private final InputView inputView;
    private final OutputView outputView;
    private final PlayerService playerService;
    private final GameService gameService;
    private final PieceService pieceService;

    public JanggiController(InputView inputView, OutputView outputView, PlayerService playerService,
                            GameService gameService, PieceService pieceService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.playerService = playerService;
        this.gameService = gameService;
        this.pieceService = pieceService;
    }

    public void run() {
        int gameId = inputView.readGameId();
        try {
            boolean isGameExist = gameService.checkIfGameExists(gameId);
            if (isGameExist) {
                outputView.startExistedGame(gameId);
                loadExistingGame(gameId);
            }
            if (!isGameExist) {
                outputView.startNewGame(gameId);
                startNewGame(gameId);
            }
        } catch (DatabaseException de) {
            outputView.displayErrorMessage(de.getMessage());
        }
    }

    private void loadExistingGame(int gameId) {
        try {
            Games existingGame = gameService.getGameById(gameId);
            Players players = playerService.getPlayersByGameId(gameId);
            Map<Position, Piece> boardState = pieceService.getAllPiecesByGameId(gameId, players);

            outputView.displayPlayerInfo(players);
            outputView.displayJanggiBoard(boardState);

            JanggiGame janggiGame = new JanggiGame(new JanggiBoardGenerator(players), players, new Palace());
            janggiGame.setSequence(existingGame.getCurrentTurn()); // 저장된 턴 정보 불러오기

            while (true) {
                Player thisTurnPlayer = janggiGame.getThisTurnPlayer();
                gameProcess(thisTurnPlayer, janggiGame, pieceService, gameId);

                Map<Player, Integer> score = janggiGame.calculateScore();
                outputView.displayJanggiBoard(janggiGame.getBoardState());
                outputView.displayJanggiScore(score);

                if (janggiGame.checkKingIsDead()) {
                    outputView.displayJanggiBoard(janggiGame.getBoardState());
                    outputView.displayGameResult(thisTurnPlayer);
                    return;
                }
            }
        } catch (DatabaseException de) {
            outputView.displayErrorMessage(de.getMessage());
        }
    }

    private void startNewGame(int gameId) {
        try {
            List<String> playerNames = inputView.readPlayerNames();
            gameService.createGame(gameId);
            Players players = playerService.savePlayer(playerNames, gameId);
            gameService.updateGameInfo(Status.STARTED, gameId, players, 0);

            outputView.displayPlayerInfo(players);

            JanggiGame janggiGame = new JanggiGame(new JanggiBoardGenerator(players), players, new Palace());
            pieceService.saveAllPieces(janggiGame.getBoardState(), gameId);
            outputView.displayJanggiBoard(janggiGame.getBoardState());

            while (true) {
                Player thisTurnPlayer = janggiGame.getThisTurnPlayer();
                gameProcess(thisTurnPlayer, janggiGame, pieceService, gameId);

                Map<Player, Integer> score = janggiGame.calculateScore();
                outputView.displayJanggiBoard(janggiGame.getBoardState());
                outputView.displayJanggiScore(score);

                if (janggiGame.checkKingIsDead()) {
                    outputView.displayJanggiBoard(janggiGame.getBoardState());
                    outputView.displayGameResult(thisTurnPlayer);
                    return;
                }
            }
        } catch (DatabaseException de) {
            outputView.displayErrorMessage(de.getMessage());
        }
    }

    private void gameProcess(Player thisTurnPlayer, JanggiGame janggiGame, PieceService pieceService, int gameId) {
        while (true) {
            try {
                List<Integer> startRowAndColumn = inputView.readMovePiecePosition(thisTurnPlayer);
                List<Integer> targetRowAndColumn = inputView.readTargetPosition(thisTurnPlayer);

                Position startPosition = new Position(startRowAndColumn.getFirst(), startRowAndColumn.getLast());
                Position targetPosition = new Position(targetRowAndColumn.getFirst(), targetRowAndColumn.getLast());

                boolean targetPositionIsEmpty = janggiGame.isTargetPositionIsEmpty(targetPosition);
                Map<Position, Piece> startAndTargetPieces = janggiGame.move(startPosition, targetPosition);

                gameService.updateGameSequence(gameId, janggiGame.getSequence());
                Piece startPiece = startAndTargetPieces.get(startPosition);

                if (targetPositionIsEmpty) {
                    pieceService.updateMovingPiece(startPiece, startPosition, targetPosition, gameId);
                    return;
                }
                Piece targetPiece = startAndTargetPieces.get(targetPosition);
                pieceService.removeTargetPiece(targetPiece, targetPosition, gameId);
                pieceService.updateMovingPiece(startPiece, startPosition, targetPosition, gameId);
                return;

            } catch (IllegalArgumentException iae) {
                outputView.displayErrorMessage(iae.getMessage());
            }
        }
    }
}
