public class GameManager {
    private final InputView inputView;
    private final OutputView outputView;

    public GameManager(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        Player choPlayer = new Player(inputView.readChoPlayerName());
        Player hanPlayer = new Player(inputView.readHanPlayerName());

        Formation choFormation = Formation.from(Selection.from(inputView.readChoFormation()));
        Formation hanFormation = Formation.from(Selection.from(inputView.readHanFormation()));

        Board board = new InitialBoardFactory().create(choFormation, hanFormation);

        Game game = new Game(board, choPlayer, hanPlayer);
    }
}
