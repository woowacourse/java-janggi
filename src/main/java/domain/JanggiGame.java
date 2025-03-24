package domain;

import domain.board.Board;
import domain.board.Point;
import domain.board.factory.BoardFactory;
import domain.pieces.Piece;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class JanggiGame {

    private final Board board;
    private final List<Player> players;

    private boolean isFirstPlayerTurn = true;

    public JanggiGame(final Board board, final List<Player> players) {
        this.board = board;
        this.players = players;
    }

    public static JanggiGame setup(final EnumMap<Team, Integer> elephantLocatorByTeam) {
        final Board board = BoardFactory.generateBoard(elephantLocatorByTeam);
        final List<Player> players = elephantLocatorByTeam.keySet().stream()
                .map(Player::new)
                .collect(Collectors.toList());
        return new JanggiGame(board, players);
    }

    public Map<Point, Piece> getBoard() {
        return board.getLocations();
    }

    public void move(final Point startPoint, final Point arrivalPoint) {
        board.movePiece(startPoint, arrivalPoint, getCurrentPlayerTeam());
        manageCurrentPlayerTurn();
    }

    public Team getCurrentPlayerTeam() {
        final Player currentPlayer = players.stream()
                .filter(player -> player.isFirstAttack() == isFirstPlayerTurn)
                .findFirst()
                .orElseThrow();
        return currentPlayer.getTeam();
    }

    private void manageCurrentPlayerTurn() {
        isFirstPlayerTurn = !isFirstPlayerTurn;
    }
}
