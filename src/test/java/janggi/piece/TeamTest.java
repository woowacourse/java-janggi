package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TeamTest {

    @DisplayName("팀을 뒤바꿀 수 있다.")
    @Test
    void changeTeam() {
        //given
        final Team han = Team.HAN;

        //when
        final Team actual = han.changeTeam();

        //then
        assertThat(actual).isEqualTo(Team.CHU);
    }

    @DisplayName("초나라 팀이라면 true를 반환한다.")
    @Test
    void isChu() {
        //given
        final Team team = Team.CHU;

        //when
        final boolean actual = Team.isChu(team);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("초나라 팀이 아니라면 false를 반환한다.")
    @Test
    void isNotChu() {
        //given
        final Team team = Team.HAN;

        //when
        final boolean actual = Team.isChu(team);

        //then
        assertThat(actual).isFalse();
    }

    @DisplayName("한나라 팀이라면 true를 반환한다.")
    @Test
    void isHan() {
        //given
        final Team team = Team.HAN;

        //when
        final boolean actual = Team.isHan(team);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("한나라 팀이 아니라면 fasle를 반환한다.")
    @Test
    void isNotHan() {
        //given
        final Team team = Team.CHU;

        //when
        final boolean actual = Team.isHan(team);

        //then
        assertThat(actual).isFalse();
    }

}
