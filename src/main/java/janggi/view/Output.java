package janggi.view;

public interface Output {
    void printPromptMessage(String promptMessage);

    void printErrorMessage(RuntimeException e);
}
