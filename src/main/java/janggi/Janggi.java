package janggi;

import janggi.domain.board.BoardInitializer;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.StandardBoardInitializer;
import janggi.view.InputView;

public class Janggi {

    public static void main(String[] args) {
        String hanCommand = InputView.readHanElephantSettingCommand();
        String choCommand = InputView.readChoElephantSettingCommand();

        BoardInitializer initializer = new StandardBoardInitializer(hanCommand, choCommand);
        JanggiBoard board = new JanggiBoard(initializer);
    }
}
