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
    private final Board board;
    private final Game game;
    private final String choPlayer;
    private final String hanPlayer;


    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        game = new Game();

        this.choPlayer = InputView.readPlayerName("초나라");
        this.hanPlayer = InputView.readPlayerName("한나라");

        String choFormationInput = InputView.readFormation("초나라");
        String hanFormationInput = InputView.readFormation("한나라");

        HorseElephantFormation choFormation = HorseElephantFormation.from(choFormationInput);
        HorseElephantFormation hanFormation = HorseElephantFormation.from(hanFormationInput);

        InitializeStrategy choStrategy = choFormation.createStrategy();
        InitializeStrategy hanStrategy = hanFormation.createStrategy();

        this.board = new Board(choStrategy, hanStrategy);

        gameRepository.save(game, board);

        OutputView.printBoard(this.board);
    }

    public void run() {

        while (true) {
            if (!board.canNextTurn()) {
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
