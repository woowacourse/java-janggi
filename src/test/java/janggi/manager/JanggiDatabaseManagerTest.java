package janggi.manager;

import janggi.dao.FakeJanggiDao;
import janggi.piece.Byeong;
import janggi.piece.Jol;
import janggi.piece.Piece;
import janggi.position.Position;
import janggi.team.TeamType;
import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiDatabaseManagerTest {

    private JanggiDatabaseManager janggiDatabaseManager;
    private FakeJanggiDao fakeJanggiDao;

    @BeforeEach
    void setUp() {
        fakeJanggiDao = new FakeJanggiDao();
        janggiDatabaseManager = new JanggiDatabaseManager(fakeJanggiDao, new JanggiMapper());
    }

    @Test
    void 기존_저장된_게임이_있다면_기물을_불러온다() {
        // Given
        Map<Position, Piece> pieces = Map.of(
                new Position(1, 1), new Byeong(),
                new Position(2, 1), new Byeong(),
                new Position(3, 1), new Jol()
        );
        fakeJanggiDao.insertInitialTeam(TeamType.CHO);
        fakeJanggiDao.insertInitialPieceType();
        fakeJanggiDao.insertPieces(pieces);

        // When & Then
        assertThat(janggiDatabaseManager.loadPiecesForProgressingGame().size())
                .isEqualTo(pieces.size());
    }

    @Test
    void 기존_저장된_게임이_있다면_순서를_불러온다() {
        // Given
        final TeamType currentTeam = TeamType.CHO;
        fakeJanggiDao.insertInitialTeam(currentTeam);
        fakeJanggiDao.updateTeamOrder(currentTeam);

        // When & Then
        assertThat(janggiDatabaseManager.loadOrdersForProgressingGame().getFirst())
                .isEqualTo(currentTeam);
    }

    @Test
    void 진행_중인_게임을_저장한다() {
        // Given
        Map<Position, Piece> pieces = Map.of(
                new Position(1, 1), new Byeong(),
                new Position(2, 1), new Byeong(),
                new Position(3, 1), new Jol()
        );
        fakeJanggiDao.insertInitialTeam(TeamType.CHO);
        fakeJanggiDao.insertInitialPieceType();
        fakeJanggiDao.insertPieces(pieces);

        // When
        janggiDatabaseManager.saveGame(TeamType.CHO, pieces);

        // Then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(fakeJanggiDao.findTeams())
                    .isNotEmpty();
            softAssertions.assertThat(fakeJanggiDao.findPieces())
                    .isNotEmpty();
            softAssertions.assertThat(fakeJanggiDao.getPieceTypes())
                    .isNotEmpty();
        });
    }

    @Test
    void 게임이_끝나_종료한다() {
        // Given
        Map<Position, Piece> pieces = Map.of(
                new Position(1, 1), new Byeong(),
                new Position(2, 1), new Byeong(),
                new Position(3, 1), new Jol()
        );
        fakeJanggiDao.insertInitialTeam(TeamType.CHO);
        fakeJanggiDao.insertInitialPieceType();
        fakeJanggiDao.insertPieces(pieces);

        // When
        janggiDatabaseManager.endGame();

        // Then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(fakeJanggiDao.findTeams())
                    .isEmpty();
            softAssertions.assertThat(fakeJanggiDao.findPieces())
                    .isEmpty();
            softAssertions.assertThat(fakeJanggiDao.getPieceTypes())
                    .isEmpty();
        });
    }
}
