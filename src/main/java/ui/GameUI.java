package ui;

import board.Board;
import move.Move;

/**
 * Интерфейс пользовательского интерфейса игры.
 */
public interface GameUI {

    /**
     * Отображает игровую доску.
     *
     * @param board игровая доска
     */
    void render(Board board);

    /**
     * Получает ход игрока.
     *
     * @return ход
     */
    Move getMove();

    /**
     * Показывает сообщение пользователю.
     *
     * @param message текст сообщения
     */
    void showMessage(String message);
}
