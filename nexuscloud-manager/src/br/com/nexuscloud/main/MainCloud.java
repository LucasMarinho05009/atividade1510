package br.com.nexuscloud.main;

import br.com.nexuscloud.exception.CapacidadeExcedidaException;
import br.com.nexuscloud.model.Cliente;
import br.com.nexuscloud.model.CloudManager;
import br.com.nexuscloud.model.Container;
import br.com.nexuscloud.model.Servidor;

import java.util.Locale;

/**
 * Classe executável com o CAMPO DE TESTES: executa o roteiro de 10 passos do enunciado. RA: 25017750-2
 */
public class MainCloud {

    /**
     * O main declara "throws CapacidadeExcedidaException" porque os Passos 6 e 8 chamam
     * alocarContainer SEM try-catch (o enunciado proíbe try-catch no Passo 6) e essa é uma
     * exceção CHECKED: o compilador exige tratar ou declarar. A declaração resolve isso sem
     * esconder a ServidorOfflineException (unchecked), que continuará aparecendo em vermelho. RA: 25017750-2
     */
    public static void main(String[] args) throws CapacidadeExcedidaException {

        // Passo 1: instancia o CloudManager (o banco de dados principal). RA: 25017750-2
        CloudManager manager = new CloudManager();

        // Passo 2: instancia 1 Cliente com CPF e nome. RA: 25017750-2
        Cliente cliente = new Cliente("111.222.333-44", "Wayne Enterprises");

        // Passo 3: instancia 1 Servidor (IP, RAM total de 16.0 GB, preço de R$ 5.50 por hora).
        // Ele nasce DESLIGADO. RA: 25017750-2
        Servidor servidor = new Servidor("192.168.0.1", 16.0, 5.50);

        // Passo 4: registra o cliente no banco de dados e associa o servidor a ele. RA: 25017750-2
        manager.registrarCliente(cliente);
        manager.provisionarServidor(cliente, servidor);

        // Passo 5: instancia 2 Containers diferentes (ID, nome da aplicação, RAM necessária). RA: 25017750-2
        Container container1 = new Container("C-01", "API Gateway", 10.0);
        Container container2 = new Container("C-02", "Banco de Dados", 8.0);

        // Passo 6 (Teste Falha UNCHECKED): tentar alocar com o servidor desligado dispara
        // ServidorOfflineException, SEM try-catch, para provar que a regra funciona.
        // Foi executado e testado; conforme o Passo 7, a linha foi COMENTADA para o programa prosseguir. RA: 25017750-2
        // servidor.alocarContainer(container1);

        // Passo 7: segue em frente. Liga o servidor, que agora passa a aceitar containers. RA: 25017750-2
        servidor.ligar();

        // Passo 8: aloca o Container 1 com sucesso (10.0 de 16.0 GB). Agora vai dar certo. RA: 25017750-2
        servidor.alocarContainer(container1);
        System.out.println("Passo 8 - Sucesso: container " + container1.getId()
                + " alocado. RAM em uso: " + servidor.calcularRamEmUso() + " GB de " + servidor.getRamTotal() + " GB.");

        // Passo 9 (Teste Falha CHECKED): alocar o Container 2 (8.0) estoura o limite, pois 10.0 + 8.0 = 18.0 > 16.0.
        // O try-catch é OBRIGATÓRIO aqui: captura a CapacidadeExcedidaException e imprime o erro de forma
        // elegante, sem derrubar o programa. RA: 25017750-2
        try {
            servidor.alocarContainer(container2);
            System.out.println("Passo 9 - Falha: o container " + container2.getId() + " não deveria caber!");
        } catch (CapacidadeExcedidaException e) {
            System.out.println("Passo 9 - Erro tratado com sucesso: " + e.getMessage());
        }

        // Passo 10: imprime a fatura ativa do cliente. Só o servidor ligado conta: o valor esperado é 5.50.
        // String.format com Locale.US garante o ponto decimal, independente do idioma do computador. RA: 25017750-2
        double fatura = manager.calcularFaturaAtiva(cliente);
        System.out.println("Passo 10 - Fatura ativa de " + cliente.getNome() + ": R$ "
                + String.format(Locale.US, "%.2f", fatura));
    }
}

// RA: 25017750-2
