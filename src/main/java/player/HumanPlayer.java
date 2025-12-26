package player;

import board.Board;
import move.Move;
import pieces.PieceColor;
import ui.GameUI;

/**
 * Игрок, управляемый человеком.
 */
public class HumanPlayer implements Player {

    /** Пользовательский интерфейс, откуда читается ход. */
    private final GameUI ui;

    /** Цвет игрока. */
    private final PieceColor color;

    /** Имя игрока. */
    private final String name;

    public HumanPlayer(GameUI ui, PieceColor color, String name) {
        this.ui = ui;
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
     * Запрашивает ход у пользователя через UI.
     */
    @Override
/**
 * Выполняет действие: makeMove.
 * @param board параметр метода
 * @return результат выполнения
 */
    public Move makeMove(Board board) {
        return ui.getMove();
    }
}
