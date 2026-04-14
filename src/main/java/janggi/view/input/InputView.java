package janggi.view.input;

import java.util.Optional;

public interface InputView {

    String readLine();

    Optional<String> readCancelableLine();
}
