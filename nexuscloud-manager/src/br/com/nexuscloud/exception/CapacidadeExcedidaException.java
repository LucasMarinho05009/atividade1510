package br.com.nexuscloud.exception;

/**
 * Exceção CHECKED (verificada): estende Exception diretamente (e NÃO RuntimeException).
 * O compilador OBRIGA quem chama um método que a lança a tratá-la com try-catch
 * ou a declará-la com throws. Representa uma falha prevista do negócio: a RAM do servidor acabou. RA: 25017750-2
 */
public class CapacidadeExcedidaException extends Exception {

    /**
     * Construtor que recebe a mensagem descritiva do erro e a repassa
     * para a classe pai (Exception) por meio do super. RA: 25017750-2
     */
    public CapacidadeExcedidaException(String msg) {
        super(msg);
    }
}

// RA: 25017750-2
