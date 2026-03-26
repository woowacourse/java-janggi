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

public class Janggi {

    public void run() {
        Players players = getPlayer();
        Board board = getBoard();

        OutputView.printBoard(board.getFormatBoard());
        play(players, board);
    }

    private Players getPlayer() {
        OutputView.printInputPlayerNames();
        String input = InputView.readLine();
        List<String> names = PlayerNameParser.splitNames(input);

        return Players.from(names);
    }

    private Board getBoard() {
        HorseElephantFormation cho = getHorseElephantFormation(Side.CHO);
        HorseElephantFormation han = getHorseElephantFormation(Side.HAN);

        return BoardFactory.create(cho, han);
    }

    private HorseElephantFormation getHorseElephantFormation(Side side) {
        OutputView.printHorseElephantFormation(side);
        String input = InputView.readLine();

        return HorseElephantFormation.from(input);
    }

    private void play(Players players, Board board) {
        while (true) {
            turn(players.getPlayerBySide(Side.CHO), board);
            turn(players.getPlayerBySide(Side.HAN), board);

            //todo 장군 및 외통수 추가 시 종료 조건 및 반복
        }
    }

    private void turn(Player player, Board board) {
        OutputView.printBoard(board.getFormatBoard());
        Position from = getFrom(player);
        Position to = getTo(player);

        board.move(from, to, player.getSide());

        OutputView.printBoard(board.getFormatBoard());
    }

    private Position getFrom(Player player) {
        OutputView.printPieceMove(player.getName(), player.getSide());
        return PositionParser.parsePosition(InputView.readLine());
    }

    private Position getTo(Player player) {
        OutputView.printPositionMove(player.getName(), player.getSide());
        return PositionParser.parsePosition(InputView.readLine());
    }
}
