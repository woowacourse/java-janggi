package janggi.dao;

import janggi.piece.Byeong;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.position.Position;
import janggi.team.TeamType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FakeJanggiDaoTest {

    private FakeJanggiDao fakeJanggiDao;

    @BeforeEach
    void setUp() {
        fakeJanggiDao = new FakeJanggiDao();
    }

    @Nested
    class FAKE_객체를_만들어_테스트한다 {

        @Test
        void 초기_장기_기물_종류를_추가한다() {
            // When
            fakeJanggiDao.insertInitialPieceType();

            // Then
            assertThat(fakeJanggiDao.getPieceTypes().size())
                    .isEqualTo(PieceType.values().length);
        }

        @Test
        void 초기_팀을_추가한다() {
            // When
            fakeJanggiDao.insertInitialTeam(TeamType.CHO);

            // Then
            assertThat(fakeJanggiDao.getTeamTypes().size())
                    .isEqualTo(TeamType.values().length);
        }

        @Test
        void ID로_팀을_조회한다() {
            // Given
            final int id = 1;
            fakeJanggiDao.insertInitialTeam(TeamType.CHO);

            // When & Then
            assertThat(fakeJanggiDao.findTeamById(id))
                    .isEqualTo(fakeJanggiDao.getTeamTypes().getFirst());
        }

        @Test
        void ID로_기물_타입을_조회한다() {
            // Given
            final int id = 1;
            fakeJanggiDao.insertInitialPieceType();

            // When & Then
            assertThat(fakeJanggiDao.findPieceTypeById(id))
                    .isEqualTo(fakeJanggiDao.getPieceTypes().getFirst());
        }

        @Test
        void 초기_기물을_장기판에_추가한다() {
            // Given
            final Map<Position, Piece> pieces = new HashMap<>();
            pieces.put(new Position(1, 1), new Byeong());
            fakeJanggiDao.insertInitialTeam(TeamType.CHO);
            fakeJanggiDao.insertInitialPieceType();

            // When
            fakeJanggiDao.insertPieces(pieces);

            // Then
            assertThat(fakeJanggiDao.findPieces().size())
                    .isEqualTo(pieces.size());
        }

        @Test
        void 기물_종류를_모두_삭제한다() {
            // When
            fakeJanggiDao.deleteAllPieceTypeIfExists();

            // Then
            assertThat(fakeJanggiDao.getPieceTypes().isEmpty())
                    .isTrue();
        }

        @Test
        void 팀을_모두_삭제한다() {
            // When
            fakeJanggiDao.deleteAllTeamIfExists();

            // Then
            assertThat(fakeJanggiDao.getTeamTypes().isEmpty())
                    .isTrue();
        }

        @Test
        void 기물을_모두_삭제한다() {
            // When
            fakeJanggiDao.deleteAllPieceIfExists();

            // Then
            assertThat(fakeJanggiDao.findPieces().isEmpty())
                    .isTrue();
        }

        @Test
        void 해당_팀의_순서로_변경한다() {
            // Given
            final TeamType currentTeam = TeamType.HAN;
            fakeJanggiDao.insertInitialTeam(TeamType.CHO);

            // When
            fakeJanggiDao.updateTeamOrder(currentTeam);

            // Then
            assertThat(fakeJanggiDao.findTeams().getFirst().isCurrent())
                    .isTrue();
        }
    }
}
