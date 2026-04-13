package janggi;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.ElephantFormation;
import janggi.domain.board.InitialPiecePlacement;
import janggi.domain.game.Game;
import janggi.domain.game.GameSelectionFormat;
import janggi.domain.piece.Piece;
import janggi.domain.piece.camp.CampType;
import janggi.dto.MoveResultDto;
import janggi.dto.PiecePositionDto;
import janggi.service.GameService;
import janggi.util.RetryHandler;
import janggi.view.InputView;
import janggi.view.OutputView;
import janggi.view.format.ElephantSetUpFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JanggiGame {

    private final GameService gameService;

    public JanggiGame(GameService gameService) {
        this.gameService = gameService;
    }

    public void run() {
        Game game = createOrLoadGame();
        OutputView.printBoard(toPiecePositions(game.getPiecePositions()));
        play(game);
    }

    private Game createOrLoadGame() {
        GameSelectionFormat gameSelectionFormat = InputView.readGameSelection();

        if (gameSelectionFormat == GameSelectionFormat.NEW_GAME) {
            return createGame();
        }
        return loadGame();
    }

    private Game createGame() {
        Board board = createBoard();
        return gameService.createGame(board);
    }

    private Board createBoard() {
        ElephantFormation hanElephantFormation = readElephantFormation(CampType.HAN);
        ElephantFormation choElephantFormation = readElephantFormation(CampType.CHO);
        return InitialPiecePlacement.initialize(hanElephantFormation, choElephantFormation);
    }

    private ElephantFormation readElephantFormation(CampType campType) {
        return RetryHandler.retryOnInvalidInput(() -> {
            ElephantSetUpFormat elephantSetUpFormat = InputView.readElephantSettingCommand(campType);
            return elephantSetUpFormat.toElephantFormation(campType);
        });
    }

    private Game loadGame() {
        List<Long> gameRoomIds = gameService.findPlayingGameRoomIds();

        Optional<Long> gameRoomId = InputView.readGameId(gameRoomIds);
        if (gameRoomId.isEmpty()) {
            return createOrLoadGame();
        }
        return gameService.loadGame(gameRoomId.get());
    }

    private List<PiecePositionDto> toPiecePositions(Map<Position, Piece> boardState) {
        return boardState.entrySet().stream()
                .map(entry -> PiecePositionDto.of(entry.getKey(), entry.getValue()))
                .toList();
    }

    private void play(Game game) {
        while (!game.isFinished()) {
            processTurn(game);
        }
        OutputView.printWinner(game.getCurrentTurn());
    }

    private void processTurn(Game game) {
        RetryHandler.retryOnInvalidInput(() -> {
            OutputView.printScore(game.getScoreBoard());
            Position source = readSource(game.getBoard(), game.getCurrentTurn());
            Position destination = readDestination(game.getBoard(), source, game.getCurrentTurn());

            gameService.move(game, source, destination);
        });
        OutputView.printBoard(toPiecePositions(game.getPiecePositions()));
    }

    private Position readSource(Board board, CampType campType) {
        return RetryHandler.retryOnInvalidInput(() -> {
            Position source = Position.from(InputView.readSource(campType));
            board.validateSource(source, campType);
            return source;
        });
    }

    private Position readDestination(Board board, Position source, CampType campType) {
        return RetryHandler.retryOnInvalidInput(() -> {
            Position destination = Position.from(InputView.readDestination());
            board.validateDestination(destination, source, campType);
            return destination;
        });
    }
}
