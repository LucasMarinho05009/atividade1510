package br.com.nexuscloud.model;

import java.util.Objects;

/**
 * POJO de domínio: representa o cliente da NexusCloud, dono dos servidores contratados.
 * Regra de identidade: dois Clientes são o MESMO cliente se tiverem o mesmo CPF
 * (e não se estiverem no mesmo endereço de memória). RA: 25017750-2
 */
public class Cliente {

    // Atributos privados (encapsulamento rigoroso): só se acessa por meio dos métodos públicos.
    // São final porque o Cliente será usado como CHAVE de um HashMap: se o CPF mudasse
    // depois de inserido, o hashCode mudaria e o cliente "sumiria" do mapa. RA: 25017750-2
    private final String cpf;
    private final String nome;

    /**
     * Construtor: recebe e guarda os dados do cliente conforme o diagrama UML. RA: 25017750-2
     */
    public Cliente(String cpf, String nome) {
        // this.atributo diferencia o atributo da classe do parâmetro de mesmo nome. RA: 25017750-2
        this.cpf = cpf;
        this.nome = nome;
    }

    // Getters: expõem os valores sem permitir alteração direta (não há setters, pois são imutáveis). RA: 25017750-2
    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    /**
     * equals: define a igualdade LÓGICA. Dois Clientes são iguais se o CPF for igual.
     * Obedece ao contrato do Java: reflexivo, simétrico, transitivo e consistente. RA: 25017750-2
     */
    @Override
    public boolean equals(Object obj) {
        // Mesma referência de memória: com certeza são iguais (atalho de desempenho). RA: 25017750-2
        if (this == obj) {
            return true;
        }
        // Nulo ou de outra classe: nunca pode ser igual. RA: 25017750-2
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Conversão segura (cast) e comparação apenas pelo CPF; Objects.equals tolera null. RA: 25017750-2
        Cliente outro = (Cliente) obj;
        return Objects.equals(this.cpf, outro.cpf);
    }

    /**
     * hashCode: calculado SOMENTE com o CPF, o mesmo campo usado no equals.
     * Contrato: objetos iguais precisam ter o mesmo hashCode, senão HashMap/HashSet falham. RA: 25017750-2
     */
    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}

// RA: 25017750-2
