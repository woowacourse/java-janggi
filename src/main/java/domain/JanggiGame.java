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

    public boolean canMove(final Point start, final Point arrival) {
        final Team currentTeam = getTeamOnCurrentTurn();
        return board.canMovePiece(start, arrival, currentTeam);
    }

    public void switchTurn() {
        players.forEach(Player::switchTurn);
    }

    public Team getTeamOnCurrentTurn() {
        final Player currentPlayer = players.stream()
                .filter(Player::isTurn)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("플레이어 정보에 오류가 발생했습니다."));
        return currentPlayer.getTeam();
    }

    public void movePieceOnBoard(final Point start, final Point arrival) {
        board.movePieceOnLocations(start, arrival);
    }
}
