package janggi;

import janggi.domain.repository.JanggiRepository;
import janggi.infrastructure.FakeJanggiRepository;
import janggi.view.InputView;
import janggi.view.OutputView;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiRepository repository = new FakeJanggiRepository();
        GameManager gameManager = new GameManager(outputView, inputView, repository);
        gameManager.run();
        inputView.close();
    }
}
