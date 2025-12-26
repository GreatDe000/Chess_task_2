package player;

import board.Board;
import move.Move;
import pieces.PieceColor;

/**
 * Интерфейс игрока.
 */
public interface Player {

    /**
     * Возвращает цвет игрока.
     *
     * @return цвет игрока
     */
    PieceColor getColor();

    /**
     * Возвращает имя игрока (для сообщений).
     *
     * @return имя
     */
    String getName();

    /**
     * Создаёт ход игрока.
     *
     * @param board игровая доска
     * @return ход (может быть null, если ход сделать невозможно)
     */
    Move makeMove(Board board);
}
