package org.innopolis.kuzymvas.exceptions;

/**
 * Класс исключения, генерируемого сортировщиками при обнаружении равных элементов в массиве
 */
public class EqualElementsDuringSortException extends Exception {

    public EqualElementsDuringSortException(String msg) {
        super(msg);
    }
}
