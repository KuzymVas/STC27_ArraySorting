package org.innopolis.kuzymvas.exceptions;

/**
 * Класс исключения, генерируемого при обнаружении объектов Person с одинаковым именем и возрастом
 * Наследуемся от RuntimeException, чтобы прокинуть исключение через интерфейс компаратора,
 * который не объявляет проверяемых исключений.
 */
public class SameAgeNamePersonsException extends RuntimeException {

    public SameAgeNamePersonsException(String msg) {
        super(msg);
    }
}
