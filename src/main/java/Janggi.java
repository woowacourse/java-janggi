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

        Side winner = play(players, board);
        OutputView.printWinner(winner);
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

    private Side play(Players players, Board board) {
        Side turn = Side.CHO;

        while (board.isAliveGeneral(turn)) {
            turn = processTurn(players, board, turn);
        }

        return turn.opposite();
    }

    private Side processTurn(Players players, Board board, Side turn) {
        Player player = players.getPlayer(turn);

        OutputView.printBoard(board.getFormatBoard(), board.getSideBoard());
        printScore(board);
        executeTurn(player, board);
        printCheckIfNeeded(board, turn);

        return turn.opposite();
    }

    private void printCheckIfNeeded(Board board, Side turn) {
        if (board.isCheck(turn)) {
            OutputView.printCheck(turn.opposite());
        }
    }

    private void printScore(Board board) {
        Side cho = Side.CHO;
        Side han = Side.HAN;

        int choScore = board.getSideScore(cho);
        int hanScore = board.getSideScore(han);

        OutputView.printScore(cho, choScore, han, hanScore);
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
