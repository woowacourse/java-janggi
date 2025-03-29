package janggi.board.controller;

import janggi.view.View;

public final class OnlineController implements Controller {

    private final View view;

    public OnlineController(View view) {
        this.view = view;
    }

    @Override
    public void gameStart() {
        // TODO: DB 연결이 가능할 때 흐름 구현 예정
    }
}
