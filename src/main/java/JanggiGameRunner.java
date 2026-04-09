import common.exception.JanggiException;
import dao.GameInfo;
import domain.board.Formation;
import domain.manager.JanggiGameManager;
import domain.player.Name;
import domain.player.Player;
import domain.player.PlayerProfile;
import domain.player.Team;
import domain.position.Position;
import service.JanggiGamePlayService;
import service.JanggiGameSession;
import service.JanggiGameSetupService;
import view.InputView;
import view.OutputView;

import java.util.List;
import java.util.function.Supplier;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

public class JanggiGameRunner {

    private final InputView inputView;
    private final OutputView outputView;
    private final JanggiGameSetupService janggiGameSetupService;
    private final JanggiGamePlayService janggiGamePlayService;
    private JanggiGameManager janggiGameManager;
    private long gameId;

    public JanggiGameRunner(
            InputView inputView,
            OutputView outputView,
            JanggiGameSetupService setupService,
            JanggiGamePlayService playService
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.janggiGameSetupService = setupService;
        this.janggiGamePlayService = playService;
    }

    public void run() {
        initializeGameChoice();
        outputView.printBoard(janggiGameManager.getBoard());

        PlayerProfile winnerProfile = playGame();
        outputView.printResult(winnerProfile);
    }

    private PlayerProfile playGame() {
        while (janggiGameManager.isGameRunning()) {
            playTurn();
        }

        return janggiGameManager.calculateFinalScore();
    }

    private void playTurn() {
        Player currentPlayer = janggiGameManager.getCurrentPlayer();
        outputView.printPlayerTurnMessage(currentPlayer.getProfile());

        retryOnInvalidInput(() -> {
            Position source = createSource();
            Position destination = createDestination();
            janggiGamePlayService.playTurn(gameId, janggiGameManager, source, destination);
        });

        outputView.printBoard(janggiGameManager.getBoard());
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
        this.janggiGameManager = session.janggiGameManager();
    }

    private void initializeLoadedGame() {
        List<GameInfo> games = janggiGameSetupService.findProgressGames();
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
        this.janggiGameManager = loadedSession.janggiGameManager();
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
