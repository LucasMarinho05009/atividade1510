package br.com.nexuscloud.model;

import java.util.Objects;

/**
 * POJO de domínio: representa um container, isto é, uma aplicação que roda dentro de um Servidor.
 * Regra de identidade: dois Containers são iguais se tiverem o mesmo ID. RA: 25017750-2
 */
public class Container {

    // Atributos privados (encapsulamento). O id é final porque é a identidade do objeto
    // e, como entra no hashCode, não pode mudar depois de criado. RA: 25017750-2
    private final String id;
    private final String nomeApp;
    private final double ramNecessaria;

    /**
     * Construtor conforme o UML: identificador, nome da aplicação e RAM (em GB) que ela consome. RA: 25017750-2
     */
    public Container(String id, String nomeApp, double ramNecessaria) {
        this.id = id;
        this.nomeApp = nomeApp;
        this.ramNecessaria = ramNecessaria;
    }

    // Getters (somente leitura). O getRamNecessaria será usado nas somas com Streams do Servidor. RA: 25017750-2
    public String getId() {
        return id;
    }

    public String getNomeApp() {
        return nomeApp;
    }

    public double getRamNecessaria() {
        return ramNecessaria;
    }

    /**
     * equals: igualdade lógica baseada exclusivamente no ID do container. RA: 25017750-2
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
        // Compara só o ID (nome da aplicação e RAM não entram na identidade). RA: 25017750-2
        Container outro = (Container) obj;
        return Objects.equals(this.id, outro.id);
    }

    /**
     * hashCode: usa apenas o ID, coerente com o equals (contrato equals/hashCode). RA: 25017750-2
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

// RA: 25017750-2
