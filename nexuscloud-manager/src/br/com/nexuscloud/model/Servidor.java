package br.com.nexuscloud.model;

import br.com.nexuscloud.exception.CapacidadeExcedidaException;
import br.com.nexuscloud.exception.ServidorOfflineException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * POJO de domínio: representa um servidor virtual que hospeda containers (aplicações).
 * Regra de identidade: dois Servidores são iguais se tiverem o mesmo IP.
 * Contém a política Fail-Fast de alocação (RN03) e o cálculo de RAM com Streams (RN04). RA: 25017750-2
 */
public class Servidor {

    // Identidade e características fixas do servidor: privadas e final (não mudam depois de criado).
    // O ip é final também porque entra no hashCode e o Servidor ficará dentro de um HashSet. RA: 25017750-2
    private final String ip;
    private final double ramTotal;
    private final double precoPorHora;

    // Estado do servidor: nasce DESLIGADO (false), conforme o roteiro de testes. Muda com ligar()/desligar(). RA: 25017750-2
    private boolean statusLigado = false;

    // RN02: lista cronológica de containers, inicializada na própria declaração
    // para evitar NullPointerException ao chamar add() ou stream(). RA: 25017750-2
    private final List<Container> containers = new ArrayList<>();

    /**
     * Construtor conforme o UML. Não recebe status nem containers: o servidor começa
     * desligado e vazio. RA: 25017750-2
     */
    public Servidor(String ip, double ramTotal, double precoPorHora) {
        this.ip = ip;
        this.ramTotal = ramTotal;
        this.precoPorHora = precoPorHora;
    }

    // Getters. O nome isStatusLigado segue a convenção Java para atributos boolean. RA: 25017750-2
    public String getIp() {
        return ip;
    }

    public double getRamTotal() {
        return ramTotal;
    }

    public double getPrecoPorHora() {
        return precoPorHora;
    }

    public boolean isStatusLigado() {
        return statusLigado;
    }

    /**
     * Liga o servidor: a partir daqui ele passa a aceitar containers
     * e a gerar cobrança na fatura ativa. RA: 25017750-2
     */
    public void ligar() {
        this.statusLigado = true;
    }

    /**
     * Desliga o servidor: ele deixa de aceitar containers e de entrar na fatura ativa. RA: 25017750-2
     */
    public void desligar() {
        this.statusLigado = false;
    }

    /**
     * RN03: Motor de Alocação e Resiliência. Antes de adicionar o container na lista,
     * aplica a política Fail-Fast: falha logo no começo, antes de alterar qualquer estado.
     *
     * @throws CapacidadeExcedidaException (CHECKED) se a RAM não comportar o novo container
     *                                     (declarada no throws: quem chama é obrigado a tratar) RA: 25017750-2
     */
    public void alocarContainer(Container c) throws CapacidadeExcedidaException {
        // Proteção extra: um container nulo causaria NullPointerException mais adiante. RA: 25017750-2
        if (c == null) {
            throw new IllegalArgumentException("Erro: Container não pode ser nulo.");
        }

        // Validação 1 (UNCHECKED): servidor desligado não pode receber containers.
        // ServidorOfflineException não precisa de throws na assinatura. RA: 25017750-2
        if (!this.statusLigado) {
            throw new ServidorOfflineException("Erro: Não é possível alocar containers em um servidor desligado.");
        }

        // Validação 2 (CHECKED): a RAM já em uso somada à do novo container não pode ultrapassar a RAM total.
        // Usa-se > (e não >=): ocupar exatamente 100% da memória ainda é permitido. RA: 25017750-2
        double ramEmUso = this.calcularRamEmUso();
        if (ramEmUso + c.getRamNecessaria() > this.ramTotal) {
            throw new CapacidadeExcedidaException("Falha Crítica: Memória RAM insuficiente no servidor " + this.ip + ".");
        }

        // Passou nas duas barreiras: só agora o estado interno é alterado. RA: 25017750-2
        this.containers.add(c);
    }

    /**
     * RN04: soma a RAM de todos os containers alocados usando a Streams API, sem laços imperativos.
     * Fluxo: stream() abre o fluxo -> mapToDouble extrai a RAM de cada container -> sum() soma tudo.
     * Se a lista estiver vazia, o resultado é 0.0. RA: 25017750-2
     */
    public double calcularRamEmUso() {
        return this.containers.stream()
                .mapToDouble(Container::getRamNecessaria)
                .sum();
    }

    /**
     * equals: igualdade lógica baseada exclusivamente no IP do servidor. RA: 25017750-2
     */
    @Override
    public boolean equals(Object obj) {
        // Mesma referência: iguais. RA: 25017750-2
        if (this == obj) {
            return true;
        }
        // Nulo ou classe diferente: diferentes. RA: 25017750-2
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Só o IP entra na comparação (status e containers mudam, então não podem definir identidade). RA: 25017750-2
        Servidor outro = (Servidor) obj;
        return Objects.equals(this.ip, outro.ip);
    }

    /**
     * hashCode: usa apenas o IP, coerente com o equals. Assim o HashSet do CloudManager
     * não aceita dois servidores com o mesmo IP. RA: 25017750-2
     */
    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }
}

// RA: 25017750-2
