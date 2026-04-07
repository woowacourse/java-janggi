import domain.HorseElephantFormation;
import domain.Team;
import dto.MoveCommand;
import util.Parser;
import view.InputView;
import view.OutputView;

public class GameController {
    private final GameService gameService;
    private String choPlayer;
    private String hanPlayer;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    public void start() {
        int choice = InputView.choiceGame();

        if (choice == 1) {
            startNewGame();
            return;
        }

        if (choice == 2) {
            resumeGame();
            return;
        }

        throw new IllegalArgumentException("잘못된 입력값입니다.");
    }

    private void startNewGame() {
        this.choPlayer = InputView.readPlayerName("초나라");
        this.hanPlayer = InputView.readPlayerName("한나라");

        String choFormationInput = InputView.readFormation("초나라");
        String hanFormationInput = InputView.readFormation("한나라");

        HorseElephantFormation choFormation = HorseElephantFormation.from(choFormationInput);
        HorseElephantFormation hanFormation = HorseElephantFormation.from(hanFormationInput);

        gameService.startNewGame(choFormation, hanFormation);

        OutputView.printBoard(gameService.getBoard());

        run();
    }

    private void resumeGame() {
        gameService.resumeGame();

        OutputView.printBoard(gameService.getBoard());

        run();
    }


    public void run() {
        while (true) {
            if (gameService.isGameOver()) {
                OutputView.printGameOver();
                start();
                break;
            }
            try {
                playTurn();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void playTurn() {
        String team = "초나라";
        Team teamType = gameService.getCurrentTeam();
        String player = choPlayer;

        if (teamType == Team.HAN) {
            team = "한나라";
            player = hanPlayer;
        }

        String moveCommand = InputView.readMoveCommand(team);

        MoveCommand command = Parser.parse(moveCommand);

        gameService.playTurn(command);

        OutputView.printBoard(gameService.getBoard());
    }
}
