package janggi.domain.board;

import janggi.domain.board.strategy.ElephantHorseElephantHorse;
import janggi.domain.board.strategy.ElephantHorseHorseElephant;
import janggi.domain.board.strategy.FormationStrategy;
import janggi.domain.board.strategy.HorseElephantElephantHorse;
import janggi.domain.board.strategy.HorseElephantHorseElephant;

import java.util.List;

public class FormationStrategyFactory {
    private static final List<FormationStrategy> FORMATIONS = List.of(
            new HorseElephantElephantHorse(),
            new HorseElephantHorseElephant(),
            new ElephantHorseHorseElephant(),
            new ElephantHorseElephantHorse()
    );

    public static FormationStrategy from(int number) {
        if (number < 1 || number > FORMATIONS.size()) {
            throw new IllegalArgumentException("마상 전략은 1 ~ 4까지만 입력이 가능합니다.");
        }
        return FORMATIONS.get(number - 1);
    }
}
