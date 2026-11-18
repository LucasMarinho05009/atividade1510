package br.com.nexuscloud.exception;

/**
 * Exceção UNCHECKED (não verificada): estende RuntimeException.
 * O compilador NÃO obriga quem chama o método a tratá-la com try-catch nem a declará-la com throws.
 * Ela representa um erro de uso/estado (alocar container em servidor desligado). RA: 25017750-2
 */
public class ServidorOfflineException extends RuntimeException {

    /**
     * Construtor que recebe a mensagem descritiva do erro e a repassa
     * para a classe pai (RuntimeException) por meio do super. RA: 25017750-2
     */
    public ServidorOfflineException(String msg) {
        super(msg);
    }
}

// RA: 25017750-2
