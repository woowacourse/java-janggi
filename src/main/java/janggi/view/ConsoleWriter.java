package janggi.view;

public class ConsoleWriter implements Output {
    @Override
    public void printPromptMessage(String promptMessage) {
        System.out.println(promptMessage);
    }

    @Override
    public void printErrorMessage(RuntimeException e) {
        System.out.println("[ERROR] " + e.getMessage());
    }
}
