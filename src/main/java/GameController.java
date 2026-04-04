import domain.Board;
import domain.HorseElephantFormation;
import domain.Team;
import dto.MoveCommand;
import strategy.InitializeStrategy;
import util.Parser;
import view.InputView;
import view.OutputView;

public class GameController {
    private final Board board;
    private final String choPlayer;
    private final String hanPlayer;
    private boolean choTurn = true;

    public GameController() {
        // 보드판 초기화까지 수행
        this.choPlayer = InputView.readPlayerName("초나라");
        this.hanPlayer = InputView.readPlayerName("한나라");

        String choFormationInput = InputView.readFormation("초나라");
        String hanFormationInput = InputView.readFormation("한나라");

        HorseElephantFormation choFormation = HorseElephantFormation.from(choFormationInput);
        HorseElephantFormation hanFormation = HorseElephantFormation.from(hanFormationInput);

        InitializeStrategy choStrategy = choFormation.createStrategy();
        InitializeStrategy hanStrategy = hanFormation.createStrategy();

        this.board = new Board(choStrategy, hanStrategy);

        OutputView.printBoard(this.board);
    }

    public void run() {

        while (true) {
            try {
                playTurn();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void playTurn() {
        String team = "초나라";
        Team teamType = Team.CHO;
        String player = choPlayer;

        if (!choTurn) {
            team = "한나라";
            player = hanPlayer;
            teamType = Team.HAN;
        }

        String moveCommand = InputView.readMoveCommand(team, player);

        MoveCommand command = Parser.parse(moveCommand);

        board.move(command.from(), command.to(), command.pieceType(), teamType);

        OutputView.printBoard(board);

        choTurn = !choTurn;
    }
}
