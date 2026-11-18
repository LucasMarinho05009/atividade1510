# NexusCloud Manager

Atividade em sala de aula • Programação Orientada a Objetos • UNICESUMAR (Engenharia de Software / ADSIS)

**Aluno:** _(preencher nome completo)_ • **RA:** 25017750-2 • **Turma:** _(ADSIS / ESOFT)_

Núcleo de orquestração de servidores virtuais e containers da *NexusCloud*, com identidade por dados lógicos, coleções de alta performance, exceções customizadas (checked e unchecked) e processamento funcional com Streams.

## Estrutura

```
src/br/com/nexuscloud/
├── exception/
│   ├── ServidorOfflineException.java      # Unchecked (extends RuntimeException)
│   └── CapacidadeExcedidaException.java   # Checked   (extends Exception)
├── model/
│   ├── Cliente.java                       # identidade: CPF
│   ├── Servidor.java                      # identidade: IP
│   ├── Container.java                     # identidade: ID
│   └── CloudManager.java                  # Map<Cliente, Set<Servidor>>
└── main/
    └── MainCloud.java                     # roteiro de testes (10 passos)
```

## Requisitos atendidos

| Requisito | Implementação |
|-----------|---------------|
| RN01 | Encapsulamento rigoroso; `equals`/`hashCode` por CPF (Cliente), IP (Servidor) e ID (Container) |
| RN02 | `Servidor.containers` = `new ArrayList<>()` na declaração; `CloudManager.bancoDeDados` = `HashMap<Cliente, Set<Servidor>>` |
| RN03 | `alocarContainer` valida antes de alterar o estado: servidor desligado lança `ServidorOfflineException` (unchecked); RAM insuficiente lança `CapacidadeExcedidaException` (checked) |
| RN04 | `calcularRamEmUso` e `calcularFaturaAtiva` com Streams (`filter`, `mapToDouble`, `sum`), sem laços `for` |

## Como executar

```bash
javac -encoding UTF-8 -d out src/br/com/nexuscloud/exception/*.java src/br/com/nexuscloud/model/*.java src/br/com/nexuscloud/main/*.java
java -cp out br.com.nexuscloud.main.MainCloud
```

## Saída esperada

```
Passo 8 - Sucesso: container C-01 alocado. RAM em uso: 10.0 GB de 16.0 GB.
Passo 9 - Erro tratado com sucesso: Falha Crítica: Memória RAM insuficiente no servidor 192.168.0.1.
Passo 10 - Fatura ativa de Wayne Enterprises: R$ 5.50
```

O Passo 6 (falha *unchecked* sem `try-catch`) está comentado em `MainCloud.java`, como pede o Passo 7. Ao descomentá-lo, o programa para com `ServidorOfflineException: Erro: Não é possível alocar containers em um servidor desligado.`
