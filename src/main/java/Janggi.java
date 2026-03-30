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
        while (isGameRunning(board)) {
            players.forEachPlayer(player -> turn(player, board));
        }
    }

    private boolean isGameRunning(Board board) {
        //todo: 게임이 끝났는지 판단하는 로직 추가
        return true;
    }

    private void turn(Player player, Board board) {
        OutputView.printBoard(board.getFormatBoard(), board.getSideBoard());

        executeTurn(player, board);
    }

    private void executeTurn(Player player, Board board) {
        while (true) {
            try {
                move(player, board);
                return;
            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
            }
        }
    }

    private void move(Player player, Board board) {
        Position from = getFrom(player);
        Position to = getTo(player);
        board.move(from, to, player.getSide());
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
