package pieces;

import board.Board;
import board.Cell;

/**
 * Абстрактный класс, представляющий шахматную фигуру.
 * Содержит общие поля и методы для всех фигур.
 */
public abstract class Piece {

    /** Цвет фигуры */
    protected PieceColor color;

    /** Тип фигуры */
    protected PieceType type;

    /**
     * Конструктор фигуры.
     *
     * @param color цвет фигуры
     * @param type тип фигуры
     */
    public Piece(PieceColor color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    /**
     * Возвращает цвет фигуры.
     *
     * @return цвет фигуры
     */
/**
 * Возвращает значение color.
 * @return результат выполнения
 */
    public PieceColor getColor() {
        return color;
    }

    /**
     * Возвращает тип фигуры.
     *
     * @return тип фигуры
     */
/**
 * Возвращает значение type.
 * @return результат выполнения
 */
    public PieceType getType() {
        return type;
    }

    /**
     * Проверяет, допустим ли ход для данной фигуры.
     *
     * @param from начальная клетка
     * @param to конечная клетка
     * @param board игровая доска
     * @return true, если ход допустим
     */
    public abstract boolean isValidMove(Cell from, Cell to, Board board);
}
