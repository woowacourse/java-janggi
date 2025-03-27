package domain;

import domain.board.Board;
import domain.board.Point;
import domain.board.factory.BoardFactory;
import domain.pieces.Piece;
import domain.player.Player;
import domain.player.Score;
import domain.player.TeamType;
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

    public static JanggiGame setup(final EnumMap<TeamType, Integer> elephantLocatorByTeam) {
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
        final TeamType currentTeamType = getTeamOnCurrentTurn();
        return board.canMovePiece(start, arrival, currentTeamType);
    }

    public void switchTurn() {
        players.forEach(Player::switchTurn);
    }


    public TeamType getTeamOnCurrentTurn() {
        return getPlayerOnCurrentTurn().getTeam();
    }

    public void movePieceOnBoard(final Point start, final Point arrival) {
        final Player player = getPlayerOnCurrentTurn();
        Score score = board.movePieceOnLocations(start, arrival);
        player.addScore(score);
    }

    public Map<TeamType, Score> getScores() {
        return players.stream()
                .collect(Collectors.toMap(
                        Player::getTeam,
                        Player::getScore
                ));
    }

    private Player getPlayerOnCurrentTurn() {
        return players.stream()
                .filter(Player::isTurn)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("플레이어 정보에 오류가 발생했습니다."));
    }


}
