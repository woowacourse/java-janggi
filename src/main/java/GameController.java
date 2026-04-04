import domain.Board;
import domain.Game;
import domain.HorseElephantFormation;
import domain.Team;
import dto.MoveCommand;
import repository.GameRepository;
import strategy.InitializeStrategy;
import util.Parser;
import view.InputView;
import view.OutputView;

public class GameController {
    private final GameRepository gameRepository;
    private Game game;
    private String choPlayer;
    private String hanPlayer;

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
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

        InitializeStrategy choStrategy = choFormation.createStrategy();
        InitializeStrategy hanStrategy = hanFormation.createStrategy();

        Board board = new Board(choStrategy, hanStrategy);
        game = new Game(board);
        gameRepository.save(game, board);

        OutputView.printBoard(board);

        run();
    }

    private void resumeGame() {
        this.game = gameRepository.findLatest();

        OutputView.printBoard(game.board());

        run();
    }


    public void run() {
        while (true) {
            if (!game.board().canNextTurn()) {
                OutputView.printGameOver();
                start();
                break;
            }
            try {
                playTurn(game.board());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void playTurn(Board board) {
        String team = "초나라";
        Team teamType = game.turn();
        String player = choPlayer;

        if (teamType == Team.HAN) {
            team = "한나라";
            player = hanPlayer;
        }

        String moveCommand = InputView.readMoveCommand(team);

        MoveCommand command = Parser.parse(moveCommand);

        board.move(command.from(), command.to(), command.pieceType(), teamType);

        OutputView.printBoard(board);

        game.changeTurn();

        gameRepository.update(game, board);
    }
}
