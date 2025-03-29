package domain;

import domain.board.Board;
import domain.board.Point;
import domain.board.factory.BoardFactory;
import domain.pieces.Piece;
import domain.player.Player;
import domain.player.Score;
import domain.player.Team;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import vo.Choice;

public final class JanggiGame {

    private final Board board;
    private final List<Player> players;

    public JanggiGame(final Board board, final List<Player> players) {
        this.board = Objects.requireNonNull(board, "Board가 NULL일 수 없습니다.");
        this.players = Objects.requireNonNull(players, "플레이어 정보가 NULL일 수 없습니다.");
    }

    public static JanggiGame setup(final Map<Player, Choice> elephantLocatorByTeam,
                                   final List<Player> players) {
        final Board board = BoardFactory.generateBoard(elephantLocatorByTeam);
        return new JanggiGame(board, players);
    }

    public boolean canMove(final Point start, final Point arrival) {
        final Team currentTeam = getTeamOnCurrentTurn();
        return board.canMovePiece(start, arrival, currentTeam);
    }

    public void movePieceOnBoard(final Point start, final Point arrival) {
        final Player player = retrievePlayerOnCurrentTurn();
        Score score = board.movePieceOnLocations(start, arrival);
        player.addScore(score);
    }

    public void switchTurn() {
        players.forEach(Player::switchTurn);
    }

    public Team getTeamOnCurrentTurn() {
        return retrievePlayerOnCurrentTurn().getTeam();
    }

    public Map<Team, Score> wrapPlayersScore() {
        return players.stream()
                .collect(Collectors.toMap(
                        Player::getTeam,
                        Player::getScore
                ));
    }

    public Map<Point, Piece> getBoard() {
        return board.getLocations();
    }

    private Player retrievePlayerOnCurrentTurn() {
        return players.stream()
                .filter(Player::isTurn)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("플레이어 정보에 오류가 발생했습니다."));
    }
}
