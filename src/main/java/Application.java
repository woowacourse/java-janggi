import controller.GameController;
import domain.Janggi;
import domain.board.Board;
import domain.board.BoardFactory;
import domain.board.HorseElephantFormation;
import domain.place.piece.Side;
import domain.player.Player;
import domain.player.Players;
import domain.position.Position;
import java.util.List;
import parser.PlayerNameParser;
import parser.PositionParser;
import view.InputView;
import view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        GameController controller = new GameController(inputView, outputView);

        Players players = controller.getPlayer();
        Board board = controller.getBoard();
        Janggi janggi = new Janggi(players, board);

        controller.run(janggi);
    }
}
