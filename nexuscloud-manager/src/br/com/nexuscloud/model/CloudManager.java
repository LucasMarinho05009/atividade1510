package br.com.nexuscloud.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Gerenciador central da NexusCloud: o "banco de dados" principal em memória.
 * Relaciona cada Cliente aos Servidores que ele contratou (RN02) e calcula a fatura (RN04). RA: 25017750-2
 */
public class CloudManager {

    // RN02: Map (interface) instanciado com HashMap (implementação baseada em Hash, busca rápida).
    // Chave = Cliente (por isso o equals/hashCode por CPF é essencial).
    // Valor = Set<Servidor>: conjunto sem duplicatas (mesmo IP) e sem ordem específica. RA: 25017750-2
    private final Map<Cliente, Set<Servidor>> bancoDeDados = new HashMap<>();

    /**
     * Construtor padrão (UML: +CloudManager()). O mapa já nasce inicializado na declaração. RA: 25017750-2
     */
    public CloudManager() {
    }

    /**
     * Registra um cliente no banco de dados, começando com um conjunto vazio de servidores.
     * putIfAbsent só insere se a chave ainda NÃO existir, então registrar duas vezes
     * o mesmo CPF não apaga os servidores que o cliente já tem. RA: 25017750-2
     */
    public void registrarCliente(Cliente c) {
        this.bancoDeDados.putIfAbsent(c, new HashSet<>());
    }

    /**
     * Associa um servidor a um cliente. computeIfAbsent cria o conjunto caso o cliente ainda
     * não esteja registrado, evitando NullPointerException; o Set ignora IP repetido. RA: 25017750-2
     */
    public void provisionarServidor(Cliente c, Servidor s) {
        this.bancoDeDados.computeIfAbsent(c, chave -> new HashSet<>()).add(s);
    }

    /**
     * RN04: calcula a fatura ativa de um cliente com a Streams API, sem laços imperativos.
     * Fluxo: servidores do cliente -> filter mantém APENAS os ligados -> mapToDouble extrai
     * o preço por hora -> sum() devolve o total. Cliente desconhecido ou sem servidores ligados = 0.0. RA: 25017750-2
     */
    public double calcularFaturaAtiva(Cliente c) {
        // getOrDefault devolve um conjunto vazio se o cliente não existir (evita NullPointerException). RA: 25017750-2
        Set<Servidor> servidoresDoCliente = this.bancoDeDados.getOrDefault(c, Collections.emptySet());

        return servidoresDoCliente.stream()
                .filter(Servidor::isStatusLigado)
                .mapToDouble(Servidor::getPrecoPorHora)
                .sum();
    }
}

// RA: 25017750-2
