package dao;

import static org.assertj.core.api.Assertions.assertThat;

import dao.fake.FakeConnector;
import dao.fake.InMemoryDatabase;
import domain.board.Point;
import domain.pieces.Cannon;
import domain.pieces.Piece;
import domain.player.Player;
import domain.player.Team;
import dto.BoardLocation;
import dto.BoardLocations;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public final class BoardLocationDAOTest {
    private final InMemoryDatabase database = new InMemoryDatabase();
    private final Connector connector = new FakeConnector(database);
    private final BoardLocationDAO boardLocationDAO = new BoardLocationDAO(connector);

    @Test
    @DisplayName("보드 로케이션들을 생성한다.")
    void test_createBatch() {
        //given
        assertThat(database.getLocations().isEmpty()).isTrue();
        final Team team = Team.HAN;
        final Player player = new Player(1, team);
        final Map<Point, Piece> locations = new HashMap<>();
        locations.put(new Point(0, 0), new Cannon(player));
        locations.put(new Point(0, 1), new Cannon(player));
        locations.put(new Point(0, 2), new Cannon(player));
        locations.put(new Point(0, 3), new Cannon(player));

        final BoardLocations boardLocations = new BoardLocations(locations);
        //when
        boardLocationDAO.createBatch(boardLocations);

        //then
        assertThat(database.getLocations().size()).isEqualTo(4);
    }

    @Test
    @DisplayName("게임 내 특정 위치의 정보를 삭제한다.")
    void test_deleteLocationAt() {
        //given
        final Team team = Team.HAN;
        final Player player = new Player(1, team);
        final PlayerDAO playerDAO = new PlayerDAO(connector);
        playerDAO.createWithGameId(team, 1);

        final Map<Point, Piece> locations = new HashMap<>();
        final Point point = new Point(0, 0);
        locations.put(point, new Cannon(player));
        final BoardLocations boardLocations = new BoardLocations(locations);
        boardLocationDAO.createBatch(boardLocations);
        assertThat(database.getLocations().size()).isEqualTo(1);

        //when
        boardLocationDAO.deleteLocationAt(point, 1);

        //then
        assertThat(database.getLocations().isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Player 정보를 조회한다.")
    void test_updateLocation() {
        //given
        final Team team = Team.HAN;
        final Player player = new Player(1, team);
        final PlayerDAO playerDAO = new PlayerDAO(connector);
        playerDAO.createWithGameId(team, 1);

        final Map<Point, Piece> locations = new HashMap<>();
        final Point from = new Point(0, 0);
        final Point to = new Point(1, 0);
        locations.put(from, new Cannon(player));
        final BoardLocations boardLocations = new BoardLocations(locations);
        boardLocationDAO.createBatch(boardLocations);
        final BoardLocation oldLocation = database.getLocations().get(1);
        assertThat(oldLocation.getRow()).isEqualTo(from.row());

        //when
        boardLocationDAO.updateLocation(to, from, 1);

        //then
        final BoardLocation newLocation = database.getLocations().get(1);
        assertThat(newLocation.getRow()).isEqualTo(to.row());
    }

    @Test
    @DisplayName("같은 게임에 속한 위치 정보들을 조회한다.")
    void test_findAllByGameId() {
        //given
        assertThat(database.getLocations().isEmpty()).isTrue();
        final Team team = Team.HAN;
        final Player player = new Player(1, team);
        final Player otherGamePlayer = new Player(2, team);
        final PlayerDAO playerDAO = new PlayerDAO(connector);
        final int gameId = 1;
        playerDAO.createWithGameId(team, gameId);
        playerDAO.createWithGameId(team, 2);

        final Map<Point, Piece> locations = new HashMap<>();
        locations.put(new Point(0, 0), new Cannon(player));
        locations.put(new Point(0, 1), new Cannon(player));
        locations.put(new Point(0, 2), new Cannon(otherGamePlayer));
        locations.put(new Point(0, 3), new Cannon(otherGamePlayer));

        final BoardLocations boardLocations = new BoardLocations(locations);
        boardLocationDAO.createBatch(boardLocations);

        //when
        BoardLocations actual = boardLocationDAO.findAllByGameId(gameId);
        //then
        assertThat(actual.locations().size()).isEqualTo(2);
    }
}
