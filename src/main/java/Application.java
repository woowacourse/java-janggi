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
        Players players = getPlayer();
        Board board = getBoard();
        Janggi janggi = new Janggi(players, board);

        run(janggi);
    }

    public static void run(Janggi janggi) {
        while (true) {
            Player player = janggi.getCurrentPlayer();
            OutputView.printBoard(janggi.getBoardFormat());

            playTurn(player, janggi);
            OutputView.printScore(janggi.getGameTotalScore());
            if(janggi.isGameOver()) break;
        }
    }

    private static Players getPlayer() {
        while (true) {
            try {
                OutputView.printInputPlayerNames();
                String input = InputView.readLine();
                List<String> names = PlayerNameParser.splitNames(input);
                return Players.from(names);
            } catch (IllegalArgumentException e) {
                OutputView.printMessage(e.getMessage());
            }
        }
    }

    private static Board getBoard() {
        HorseElephantFormation cho = getHorseElephantFormation(Side.CHO);
        HorseElephantFormation han = getHorseElephantFormation(Side.HAN);

        return BoardFactory.create(cho, han);
    }

    private static HorseElephantFormation getHorseElephantFormation(Side side) {
        while (true) {
            try {
                OutputView.printHorseElephantFormation(side);
                String input = InputView.readLine();
                return HorseElephantFormation.from(input);
            } catch (IllegalArgumentException e) {
                OutputView.printMessage(e.getMessage());
            }
        }
    }

    private static void playTurn(Player player, Janggi janggi) {
        while (true) {
            try {
                Position from = getFrom(player);
                Position to = getTo(player);
                janggi.playOneTurn(from, to);
                break;
            } catch (IllegalArgumentException e) {
                OutputView.printMessage(e.getMessage());
            }
        }
    }

    private static Position getFrom(Player player) {
        while (true) {
            try {
                OutputView.printPieceMove(player.getName(), player.getSide());
                return PositionParser.parsePosition(InputView.readLine());
            } catch (IllegalArgumentException e) {
                OutputView.printMessage(e.getMessage());
            }
        }
    }

    private static Position getTo(Player player) {
        while (true) {
            try {
                OutputView.printPositionMove(player.getName(), player.getSide());
                return PositionParser.parsePosition(InputView.readLine());
            } catch (IllegalArgumentException e) {
                OutputView.printMessage(e.getMessage());
            }
        }
    }
}
