package janggi.board.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TeamDAOTest {

    private FakeTeamDao fakeTeamDao;

    @BeforeEach
    public void setUp() {
        fakeTeamDao = new FakeTeamDao();
    }

    @DisplayName("팀 테이블에 insert 확인")
    @Test
    void test1() {
        //when & then
        Assertions.assertThatCode(fakeTeamDao::insertTeam).doesNotThrowAnyException();
    }
}
