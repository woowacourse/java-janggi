import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

import dao.BoardRepository;
import dao.GameLoader;
import dao.GameLoadResult;
import dao.GameRoom;
import common.exception.JanggiException;
import domain.board.Board;
import domain.board.Formation;
import domain.manager.GameManager;
import domain.player.Name;
import domain.player.Player;
import domain.player.Team;
import domain.position.Position;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class Runner {
    private static final String PROGRESS_STATUS = "PROGRESS";
    private static final String CHO_WIN_STATUS = "CHO_WIN";
    private static final String HAN_WIN_STATUS = "HAN_WIN";

    private final InputView inputView;
    private final OutputView outputView;
    private final GameRoom gameRoom;
    private final BoardRepository boardRepository;
    private GameManager gameManager;
    private long gameId;

    public Runner(InputView inputView, OutputView outputView) {
        this(inputView, outputView, new GameRoom(), new BoardRepository());
    }

    Runner(InputView inputView, OutputView outputView, GameRoom gameRoom, BoardRepository boardRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameRoom = gameRoom;
        this.boardRepository = boardRepository;
    }

    public void run() {
        initializeGameChoice();
        outputView.printBoard(gameManager.getBoard());

        while (gameManager.isGameRunning()) {
            playTurn();
        }

        Player winner = gameManager.getCurrentPlayer();
        gameRoom.updateGameState(gameId, winner.getProfile().team(), resolveFinishedStatus(winner));
        outputView.printResult(gameManager.calculateFinalScore());
    }

    private void playTurn() {
        Player currentPlayer = gameManager.getCurrentPlayer();
        outputView.printPlayerTurnMessage(currentPlayer.getProfile());

        retryOnInvalidInput(() -> {
            Position source = createSource();
            gameManager.validateSource(source);
            Position destination = createDestination();
            gameManager.move(source, destination);
            boardRepository.save(gameId, gameManager.getBoard());
            gameRoom.updateGameState(gameId, gameManager.getCurrentPlayer().getProfile().team(), PROGRESS_STATUS);
        });

        outputView.printBoard(gameManager.getBoard());
    }

    private Position createSource() {
        List<Integer> numbers = inputView.askSourcePosition();
        return new Position(numbers.getFirst(), numbers.getLast());
    }

    private Position createDestination() {
        List<Integer> numbers = inputView.askDestinationPosition();
        return new Position(numbers.getFirst(), numbers.getLast());
    }

    private void retryOnInvalidInput(Runnable action) {
        while (true) {
            try {
                action.run();
                return;
            } catch (JanggiException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private <T> T retryOnInvalidInput(Supplier<T> action) {
        while (true) {
            try {
                return action.get();
            } catch (JanggiException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void initializeGameChoice() {
        int mode = retryOnInvalidInput(inputView::askGameMode);

        if (mode == 1) {
            initializeNewGame();
        } else if (mode == 2) {
            initializeLoadedGame();
        }
    }

    private void initializeNewGame() {
        Player choPlayer = retryOnInvalidInput(this::createChoPlayer);
        Player hanPlayer = retryOnInvalidInput(() -> createHanPlayer(choPlayer));
        this.gameManager = new GameManager(choPlayer, hanPlayer,
            retryOnInvalidInput(this::createChoFormation),
            retryOnInvalidInput(this::createHanFormation));
        this.gameId = gameRoom.createGame(choPlayer.getProfile().name().value(), hanPlayer.getProfile().name().value());
        boardRepository.save(gameId, gameManager.getBoard());
        gameRoom.updateGameState(gameId, gameManager.getCurrentPlayer().getProfile().team(), PROGRESS_STATUS);
    }

    private void initializeLoadedGame() {
        java.util.List<dao.GameInfo> games = gameRoom.findAllProgressGames();
        if (games.isEmpty()) {
            outputView.printErrorMessage("저장된 게임이 없습니다. 새 게임을 생성합니다.");
            initializeNewGame();
            return;
        }

        outputView.printAvailableGames(games);
        int choice = retryOnInvalidInput(() -> inputView.askSelectGame(games.size()));
        if (choice != games.size() + 1) {
            loadGame(games.get(choice - 1).gameId());
        } else {
            initializeNewGame();
        }
    }

    private void loadGame(long gameId) {
        Optional<dao.GameLoadResult> loaded = new GameLoader(gameRoom, boardRepository).loadGameById(gameId);
        if (loaded.isEmpty()) {
            outputView.printErrorMessage("게임을 불러올 수 없습니다.");
            return;
        }

        dao.GameLoadResult state = loaded.get();
        Board board = new Board(state.boardMap());
        this.gameId = state.gameId();
        this.gameManager = GameManager.fromLoadedState(
            createDefaultPlayer(state.choName(), CHO),
            createDefaultPlayer(state.hanName(), HAN),
            board,
            state.currentTeam()
        );
    }

    private Player createDefaultPlayer(String namePrefix, Team team) {
        return new Player(new Name(namePrefix), team);
    }


    private String resolveFinishedStatus(Player winner) {
        if(winner.getProfile().team() == CHO) {
            return CHO_WIN_STATUS;
        }
        return HAN_WIN_STATUS;
    }

    private Player createChoPlayer() {
        String choName = inputView.askChoPlayerName();
        return createPlayer(choName, CHO);
    }

    private Player createHanPlayer(Player choPlayer) {
        String hanName = inputView.askHanPlayerName();
        if (choPlayer.hasName(hanName)) {
            throw new JanggiException("초나라 플레이어와 닉네임이 중복될 수 없습니다.");
        }
        return createPlayer(hanName, HAN);
    }

    private Player createPlayer(String name, Team team) {
        return new Player(new Name(name), team);
    }

    private Formation createChoFormation() {
        int choPositionInput = inputView.askChoPositionInput();
        return Formation.from(choPositionInput);
    }

    private Formation createHanFormation() {
        int hanPositionInput = inputView.askHanPositionInput();
        return Formation.from(hanPositionInput);
    }
}
