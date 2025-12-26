package player;

import board.Board;
import move.Move;
import pieces.PieceColor;

import java.util.List;
import java.util.Random;

/**
 * Бот, выполняющий случайные корректные ходы.
 */
public class BotPlayer implements Player {

    /** Генератор случайных чисел. */
    private final Random random = new Random();

    /** Цвет бота. */
    private final PieceColor color;

    /** Имя бота. */
    private final String name;

    public BotPlayer(PieceColor color, String name) {
        this.color = color;
        this.name = name;
    }

    @Override
/**
 * Возвращает значение color.
 * @return результат выполнения
 */
    public PieceColor getColor() {
        return color;
    }

    @Override
/**
 * Возвращает значение name.
 * @return результат выполнения
 */
    public String getName() {
        return name;
    }

    /**
     * Выбирает случайный легальный ход из всех возможных.
     */
    @Override
/**
 * Выполняет действие: makeMove.
 * @param board параметр метода
 * @return результат выполнения
 */
    public Move makeMove(Board board) {
        List<Move> legal = board.getLegalMoves(color);
        if (legal.isEmpty()) {
            return null;
        }
        return legal.get(random.nextInt(legal.size()));
    }
}
