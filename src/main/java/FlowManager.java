import domain.board.Board;
import domain.board.BoardGenerator;
import domain.piece.Team;
import view.InputView;
import view.OutputView;
import view.SangMaOrderCommand;

public class FlowManager {

    public void startGame() {
        OutputView.printStart();

        SangMaOrderCommand hanSangMaOrderCommand = InputView.inputSangMaOrder(Team.HAN.title());
        SangMaOrderCommand choSangMaOrderCommand = InputView.inputSangMaOrder(Team.CHO.title());

        BoardGenerator boardGenerator = new BoardGenerator();
        Board board = boardGenerator.generateBoard(hanSangMaOrderCommand, choSangMaOrderCommand);
        OutputView.printBoard(board);
    }
}
