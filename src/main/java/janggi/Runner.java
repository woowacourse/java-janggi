package janggi;

import janggi.domain.Arrangement;
import janggi.domain.Game;
import janggi.domain.GameInfo;
import janggi.domain.GameInfos;
import janggi.domain.GameName;
import janggi.domain.PieceInitInfo;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.PieceAttribute;
import janggi.dto.GameDto;
import janggi.dto.GameResponseDto;
import janggi.dto.PieceDto;
import janggi.dto.TurnDto;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Runner {
    public static final String END_TEXT = "종료";

    private static final String UNEXPECTED_SERVER_ERROR_LOG_MESSAGE = "예측하지 못한 시스템 오류 발생";
    private static final String SYSTEM_ERROR_MESSAGE = "시스템 오류가 발생하여 게임을 종료합니다.";

    private static final Logger logger = Logger.getLogger(Runner.class.getName());

    private final JanggiService janggiService;
    private final Game game;

    public Runner(JanggiService janggiService, Game game) {
        this.janggiService = janggiService;
        this.game = game;
    }

    public int initBoard() {
        Optional<Integer> previousBoard = getPreviousBoard();
        return previousBoard.orElseGet(() -> createNewBoard().id());
    }

    public void runJanggi(int gameId) {
        while (playTurnGame(gameId)) {
        }
        endGame(gameId);
    }

    private Optional<Integer> getPreviousBoard() {
        GameInfos gameInfos = new GameInfos(janggiService.getEntireGame().stream()
                .map(GameResponseDto::toGameInfo)
                .toList());


        if(gameInfos.isEmpty()) {
            return Optional.empty();
        }

        GameInfo selectedGame = askUntilValid(() -> getSelectedGame(gameInfos));

        List<PieceInitInfo> pieceInitInfos = janggiService.getPieceInitInfos(selectedGame.id()).stream()
                .map(PieceDto::toPieceInitInfo)
                .toList();

        game.init(pieceInitInfos, selectedGame.side(), selectedGame.turn());
        return Optional.of(selectedGame.id());
    }

    private GameInfo getSelectedGame(GameInfos gameInfos) {
        OutputView.printGameRoom(gameInfos.getGameInfos());
        Optional<Integer> input = InputView.askLoadGame();

        if(input.isEmpty()) {
            return createNewBoard();
        }
        return gameInfos.getGameInfo(input.get() - 1);
    }

    private GameInfo createNewBoard() {
        GameName gameName = new GameName(InputView.askGameName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = LocalDateTime.now().format(formatter);

        String hanArrangementInput = InputView.askHanArrangement();
        Arrangement hanArrangement = Arrangement.from(hanArrangementInput);

        String choArrangementInput = InputView.askChoArrangement();
        Arrangement choArrangement = Arrangement.from(choArrangementInput);

        List<PieceDto> pieceDtos = game.init(choArrangement, hanArrangement).stream()
                .map(PieceDto::from)
                .toList();

        int id = janggiService.addGameData(new GameDto(gameName.name(), formattedNow, formattedNow, Side.CHO.getName(), 1), pieceDtos);
        return new GameInfo(id, gameName.name(), formattedNow, formattedNow, Side.CHO, 1);
    }

    private boolean playTurnGame(int gameId) {
        try {
            OutputView.printLine();
            OutputView.printBoard(game.getCurrentBoardDto());
            OutputView.printTurn(game.getCurrentSide());

            return executeTurn(gameId);
        } catch (IllegalArgumentException e) {
            OutputView.printErrorMessage(e.getMessage());
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, UNEXPECTED_SERVER_ERROR_LOG_MESSAGE, e);
            OutputView.printErrorMessage(SYSTEM_ERROR_MESSAGE);
            return false;
        }
    }

    private boolean executeTurn(int gameId) {
        Optional<List<Integer>> startPositionInput = InputView.askStartPosition();

        if(startPositionInput.isEmpty()) {
            return consentEndGame(gameId);
        }

        Position startPosition = Position.from(startPositionInput.get());

        List<Integer> endPositionInput = InputView.askEndPosition();
        Position endPosition = Position.from(endPositionInput);

        OutputView.printLine();

        PieceAttribute movedPiece = game.move(startPosition, endPosition);
        TurnDto turnDto = new TurnDto(movedPiece.side().getOppositeSide().getName(), game.getCurrentTurn());

        janggiService.movePiece(gameId, startPosition, endPosition, movedPiece.side(), movedPiece.pieceType(), turnDto);

        OutputView.printScore(game.getCurrentSideScore());
        return !game.isFinished();
    }

    private void endGame(int gameId) {
        Side winnerSide = game.getWinnerSide();
        OutputView.printWinner(winnerSide);
        if(!game.isFinished()) {
            OutputView.printScore(game.getCurrentSideScore());
        }
        janggiService.removeGame(gameId);
    }

    private boolean consentEndGame(int gameId) {
        String consentInput = InputView.consentEnd();

        if(consentInput.equals(END_TEXT)) {
            return false;
        }

        return executeTurn(gameId);
    }

    private static <T> T askUntilValid(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (Exception e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
