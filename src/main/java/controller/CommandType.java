package controller;

import view.InputView;
import view.OutputView;
import java.util.function.BiFunction;

public enum CommandType {

    MOVE((inputView, outputView) -> new MoveController(inputView, outputView)),
    PASS((inputView, outputView) -> new PassController(outputView)),
    SURRENDER((inputView, outputView) -> new SurrenderController(outputView));

    private final BiFunction<InputView, OutputView, GameCommand> mapper;

    CommandType(BiFunction<InputView, OutputView, GameCommand> mapper) {
        this.mapper = mapper;
    }

    public GameCommand createController(InputView inputView, OutputView outputView) {
        return mapper.apply(inputView, outputView);
    }
}
