package board;

import pieces.Piece;

/**
 * Класс, представляющий клетку шахматной доски.
 */
public class Cell {

    /** Координата X (столбец) */
    private final int x;

    /** Координата Y (ряд) */
    private final int y;

    /** Фигура, находящаяся на клетке (null если пустая) */
    private Piece piece;

    /**
     * Конструктор клетки.
     *
     * @param x столбец (0-7)
     * @param y ряд (0-7)
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Проверяет, пуста ли клетка.
     *
     * @return true, если нет фигуры
     */
/**
 * Проверяет условие empty.
 * @return результат выполнения
 */
    public boolean isEmpty() {
        return piece == null;
    }

    /**
     * Устанавливает фигуру на клетку.
     *
     * @param piece фигура
     */
/**
 * Устанавливает значение piece.
 * @param piece параметр метода
 */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Получает фигуру на клетке.
     *
     * @return фигура или null
     */
/**
 * Возвращает значение piece.
 * @return результат выполнения
 */
    public Piece getPiece() {
        return piece;
    }

/**
 * Возвращает значение x.
 * @return результат выполнения
 */
    public int getX() {
        return x;
    }

/**
 * Возвращает значение y.
 * @return результат выполнения
 */
    public int getY() {
        return y;
    }
}
