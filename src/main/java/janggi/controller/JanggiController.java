package janggi.controller;

import janggi.domain.Position;
import janggi.domain.board.Board;
import janggi.domain.board.BoardGenerator;
import janggi.domain.board.BoardMediator;
import janggi.domain.board.BoardMediatorImpl;
import janggi.domain.command.SetupCommand;
import janggi.domain.game.TurnManager;
import janggi.domain.piece.Piece;
import janggi.domain.team.Team;
import janggi.domain.team.TeamGenerator;
import janggi.domain.team.TeamType;
import janggi.dto.BoardDto;
import janggi.dto.GameResultDto;
import janggi.global.Pair;
import janggi.service.BoardService;
import janggi.service.GameService;
import janggi.utils.Parser;
import janggi.utils.RetryExecutor;
import janggi.view.InputView;
import janggi.view.OutputView;
import java.util.List;

public class JanggiController {

    protected static final int MAXIMUM_GAMES_COUNT_IN_PROGRESS = 3;

    private final GameService gameService;
    private final BoardService boardService;

    private long gameId;

    public JanggiController(
        final GameService gameService,
        final BoardService boardService
    ) {
        this.gameService = gameService;
        this.boardService = boardService;
    }

    public void run() {
        final GameSelectCommand gameSelectCommand = selectGame();
        final TurnManager turnManager = loadOrSaveTurnManager(gameSelectCommand);
        final Board board = loadOrSaveBoard(turnManager);
        OutputView.printBoard(BoardDto.from(board, List.of()));
        playGame(turnManager, board);
        OutputView.printGameResult(GameResultDto.from(board));
        gameService.closeGame(gameId);
    }

    private GameSelectCommand selectGame() {
        final List<String> gameNames =
            gameService.getAllGameNamesInProgress(MAXIMUM_GAMES_COUNT_IN_PROGRESS);
        OutputView.printGameSelect(gameNames, MAXIMUM_GAMES_COUNT_IN_PROGRESS);

        return RetryExecutor.retry(this::readGameSelectCommand);
    }

    private TurnManager loadOrSaveTurnManager(final GameSelectCommand gameSelectCommand) {
        if (gameSelectCommand.isGenerateGame()) {
            return saveTurnManager();
        }
        return loadTurnManager(gameSelectCommand);
    }

    private TurnManager saveTurnManager() {
        OutputView.printGameCreationMessage();
        final String gameName = RetryExecutor.retry(this::readGameName);
        final TurnManager turnManager = TurnManager.init(setupTeam(TeamType.BLUE),
            setupTeam(TeamType.RED));
        gameId = gameService.createNewGame(gameName, turnManager);
        return turnManager;
    }

    private TurnManager loadTurnManager(final GameSelectCommand gameSelectCommand) {
        final Pair<String, TurnManager> game =
            gameService.loadGame(gameSelectCommand.getSelectedGameId());
        OutputView.printGameLoadingMessage(game.left());
        gameId = gameSelectCommand.getSelectedGameId();
        return game.right();
    }

    public Board loadOrSaveBoard(final TurnManager turnManager) {
        final List<Team> teams = turnManager.getTeams();
        final Board board = BoardGenerator.generate(teams.get(0), teams.get(1));

        return new Board(boardService.loadOrCreateBoard(gameId, board.getPositionPieceMap()));
    }

    private Team setupTeam(final TeamType teamType) {
        OutputView.printSetupGuide(teamType);
        final SetupCommand setupCommand = RetryExecutor.retry(this::readSetupCommand);
        return TeamGenerator.generate(teamType, setupCommand.toPolicy());
    }

    private void playGame(final TurnManager turnManager, final Board board) {
        final BoardMediator boardMediator = new BoardMediatorImpl(board);
        while (!board.isGameOver()) {
            final Team currentTeam = gameService.getCurrentTeam(gameId);
            OutputView.printTurnStatus(currentTeam);
            final Position from = RetryExecutor.retry(this::readFromPosition, currentTeam,
                boardMediator);
            final List<Position> movablePositions = displayMovablePositions(from,
                board, boardMediator);
            final Position to = RetryExecutor.retry(this::readTargetPosition, movablePositions);
            turnManager.progressToNext();
            gameService.progressTurn(gameId, turnManager, from, to,
                boardMediator.getPieceByPosition(from));
        }
    }

    private List<Position> displayMovablePositions(
        final Position positionOfMovingPiece,
        final Board board,
        final BoardMediator boardMediator
    ) {
        final Piece pieceToMove = boardMediator.getPieceByPosition(positionOfMovingPiece);
        final List<Position> movablePositions =
            pieceToMove.calculateMovablePositions(positionOfMovingPiece, boardMediator);
        OutputView.printBoard(BoardDto.from(board, movablePositions));

        return movablePositions;
    }

    private void validateSelectedPosition(
        final Position selectedPosition,
        final Team team,
        final BoardMediator boardMediator
    ) {
        final Piece selectedPiece = boardMediator.getPieceByPosition(selectedPosition);
        if (!boardMediator.existsByPosition(selectedPosition)) {
            throw new IllegalArgumentException("입력된 위치에 기물이 존재하지 않습니다.");
        }
        if (!team.hasPiece(selectedPiece)) {
            throw new IllegalArgumentException("입력된 위치에 있는 기물은 팀 기물이 아닙니다.");
        }
        if (selectedPiece.calculateMovablePositions(selectedPosition, boardMediator).isEmpty()) {
            throw new IllegalArgumentException("선택한 기물이 이동할 수 있는 지점이 없습니다.");
        }
    }

    private String readGameName() {
        final List<String> gameNames =
            gameService.getAllGameNamesInProgress(MAXIMUM_GAMES_COUNT_IN_PROGRESS);
        final String inputGameName = InputView.readGameName();
        if (gameNames.contains(inputGameName)) {
            throw new IllegalArgumentException("입력된 이름은 이미 존재하는 게임 이름입니다.");
        }
        return inputGameName;
    }

    private GameSelectCommand readGameSelectCommand() {
        final List<Long> gameIds =
            gameService.getAllGameInProgressIds(MAXIMUM_GAMES_COUNT_IN_PROGRESS);
        return GameSelectCommand.from(InputView.readGameSelection(), gameIds);
    }

    private SetupCommand readSetupCommand() {
        return SetupCommand.pick(InputView.readSetupCommand());
    }

    private Position readFromPosition(final Team team, final BoardMediator boardMediator) {
        final String rawPosition = InputView.readFromPosition();
        final Position selectedPosition = Position.from(Parser.parsePosition(rawPosition));

        validateSelectedPosition(selectedPosition, team, boardMediator);
        return selectedPosition;
    }

    private Position readTargetPosition(final List<Position> movablePositions) {
        final String rawPosition = InputView.readTargetPosition();
        final Position targetPosition = Position.from(Parser.parsePosition(rawPosition));

        if (!movablePositions.contains(targetPosition)) {
            throw new IllegalArgumentException("입력된 위치에는 이동할 수 없습니다.");
        }
        return targetPosition;
    }
}
