import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

import service.JanggiGamePlayService;
import service.JanggiGameSession;
import service.JanggiGameSetupService;
import dao.BoardRepository;
import dao.GameRoom;
import common.exception.JanggiException;
import domain.board.Formation;
import domain.manager.GameManager;
import domain.player.Name;
import domain.player.Player;
import domain.player.PlayerProfile;
import domain.player.Team;
import domain.position.Position;
import java.util.List;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class Runner {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiGameSetupService janggiGameSetupService;
    private final JanggiGamePlayService janggiGamePlayService;
    private GameManager gameManager;
    private long gameId;

    public Runner(InputView inputView, OutputView outputView) {
        this(inputView, outputView, new GameRoom(), new BoardRepository());
    }

    Runner(InputView inputView, OutputView outputView, GameRoom gameRoom, BoardRepository boardRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiGameSetupService = new JanggiGameSetupService(gameRoom, boardRepository);
        this.janggiGamePlayService = new JanggiGamePlayService(gameRoom, boardRepository);
    }

    public void run() {
        initializeGameChoice();
        outputView.printBoard(gameManager.getBoard());

        while (gameManager.isGameRunning()) {
            playTurn();
        }

        PlayerProfile winnerProfile = gameManager.calculateFinalScore();
        janggiGamePlayService.finishGame(gameId, winnerProfile.team());
        outputView.printResult(winnerProfile);
    }

    private void playTurn() {
        Player currentPlayer = gameManager.getCurrentPlayer();
        outputView.printPlayerTurnMessage(currentPlayer.getProfile());

        retryOnInvalidInput(() -> {
            Position source = createSource();
            Position destination = createDestination();
            janggiGamePlayService.playTurn(gameId, gameManager, source, destination);
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
        JanggiGameSession session = janggiGameSetupService.createNewGame(
            choPlayer,
            hanPlayer,
            retryOnInvalidInput(this::createChoFormation),
            retryOnInvalidInput(this::createHanFormation)
        );
        this.gameId = session.gameId();
        this.gameManager = session.gameManager();
    }

    private void initializeLoadedGame() {
        java.util.List<dao.GameInfo> games = janggiGameSetupService.findProgressGames();
        if (games.isEmpty()) {
            outputView.printErrorMessage("저장된 게임이 없습니다. 새 게임을 생성합니다.");
            initializeNewGame();
            return;
        }

        outputView.printAvailableGames(games);
        int choice = retryOnInvalidInput(() -> inputView.askSelectGame(games.size()));
        if (choice == games.size() + 1) {
            initializeNewGame();
            return;
        }

        JanggiGameSession loadedSession = janggiGameSetupService.loadSessionById(games.get(choice - 1).gameId())
            .orElse(null);
        if (loadedSession == null) {
            outputView.printErrorMessage("게임을 불러올 수 없습니다. 새 게임을 생성합니다.");
            initializeNewGame();
            return;
        }

        this.gameId = loadedSession.gameId();
        this.gameManager = loadedSession.gameManager();
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
