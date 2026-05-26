# 🚗 VeloRent — Sistema de Gerenciamento de Locadora de Veículos

> Projeto acadêmico desenvolvido no curso de Bacharelado em Sistemas de Informação — UNIFACOL  
> Disciplina: Análise e Projeto Orientado a Objetos (APOO) · Versão 1.0 · 2026

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura do Projeto](#arquitetura-do-projeto)
- [Pré-requisitos](#pré-requisitos)
- [Configuração do Banco de Dados](#configuração-do-banco-de-dados)
- [Como Executar](#como-executar)
- [Estrutura de Pastas](#estrutura-de-pastas)
- [Diagrama de Classes](#diagrama-de-classes)
- [Atores do Sistema](#atores-do-sistema)
- [Equipe](#equipe)

---

## Sobre o Projeto

O **VeloRent** é um sistema de gerenciamento desenvolvido em Java para automatizar as operações de uma locadora de veículos. O sistema centraliza o controle de clientes, frota, disponibilidade, orçamentos e pagamentos em uma única plataforma, eliminando o controle manual e reduzindo erros operacionais.

### Problema Resolvido

Muitas locadoras ainda controlam suas operações via papel ou planilhas simples, o que gera perda de dados, erros de registro e dificuldade no acompanhamento das operações. O VeloRent resolve isso oferecendo:

- Cadastro centralizado de clientes e veículos
- Controle de disponibilidade com detecção de conflitos de período
- Registro e acompanhamento de orçamentos e pagamentos
- Controle de acesso por perfil (Administrador / Atendente)

---

## Funcionalidades

| Módulo | Operações |
|---|---|
| **Autenticação** | Login com usuário e senha, controle de perfil |
| **Clientes** | Cadastrar, editar, excluir e listar |
| **Veículos** | Cadastrar, editar, excluir e listar |
| **Disponibilidade** | Registrar, editar, excluir e consultar períodos |
| **Orçamentos** | Criar simulações de locação com cálculo automático de valor |
| **Pagamentos** | Registrar, editar, excluir e listar transações financeiras |
| **Usuários** | Gerenciamento de contas de acesso ao sistema |

> O sistema cria automaticamente um usuário administrador padrão na primeira execução.

---

## Tecnologias Utilizadas

- **Java 21**
- **Hibernate ORM** — mapeamento objeto-relacional
- **MySQL** — banco de dados relacional
- **Maven** — gerenciamento de dependências e build
- **Arquitetura em camadas**: Model → Repository → Service → UI

---

## Arquitetura do Projeto

O sistema segue o padrão de arquitetura em camadas:

```
UI (Menus de console)
    ↓
Service (Regras de negócio)
    ↓
Repository (Acesso a dados via Hibernate)
    ↓
Model (Entidades JPA)
    ↓
MySQL (Banco de Dados)
```

---

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- [Java JDK 21+](https://www.oracle.com/java/technologies/downloads/)
- [Maven 3.8+](https://maven.apache.org/download.cgi)
- [MySQL 8.0+](https://dev.mysql.com/downloads/)
- IDE de sua preferência (VS Code, IntelliJ IDEA, Eclipse)

---

## Configuração do Banco de Dados

1. Acesse o MySQL e crie o banco de dados:

```sql
CREATE DATABASE locadora;
```

2. Abra o arquivo de configuração do Hibernate:

```
src/main/resources/hibernate.cfg.xml
```

3. Ajuste as credenciais conforme seu ambiente:

```xml
<property name="hibernate.connection.url">
    jdbc:mysql://127.0.0.1:3306/locadora?useSSL=false&serverTimezone=America/Recife&allowPublicKeyRetrieval=true
</property>
<property name="hibernate.connection.username">root</property>
<property name="hibernate.connection.password">sua_senha_aqui</property>
```

> O Hibernate está configurado com `hbm2ddl.auto=update`, então as tabelas são criadas/atualizadas automaticamente na primeira execução.

---

## Como Executar

### Via Maven (linha de comando)

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/Projeto-VeloRent-APOO.git
cd Projeto-VeloRent-APOO

# Compile o projeto
mvn clean compile

# Execute a aplicação
mvn exec:java -Dexec.mainClass="Main"
```

### Via IDE

1. Importe o projeto como **Maven Project**
2. Aguarde o download das dependências
3. Configure o banco de dados (ver seção acima)
4. Execute a classe `Main.java`

### Acesso Inicial

Na primeira execução, o sistema cria automaticamente um usuário administrador padrão. Consulte a classe `UsuarioService.java` para verificar as credenciais iniciais e alterá-las após o primeiro acesso.

---

## Estrutura de Pastas

```
Projeto-VeloRent-APOO-main/
├── src/
│   └── main/
│       ├── java/
│       │   ├── Main.java                   # Ponto de entrada da aplicação
│       │   ├── model/                      # Entidades JPA
│       │   │   ├── Usuario.java
│       │   │   ├── Cliente.java
│       │   │   ├── Veiculo.java
│       │   │   ├── Disponibilidade.java
│       │   │   ├── Orcamento.java
│       │   │   ├── Pagamento.java
│       │   │   ├── Categoria.java
│       │   │   └── FormaPagamento.java
│       │   ├── repository/                 # Acesso a dados (Hibernate)
│       │   │   ├── ClienteRepository.java
│       │   │   ├── VeiculoRepository.java
│       │   │   ├── DisponibilidadeRepository.java
│       │   │   ├── OrcamentoRepository.java
│       │   │   ├── PagamentoRepository.java
│       │   │   └── UsuarioRepository.java
│       │   ├── service/                    # Regras de negócio
│       │   │   ├── ClienteService.java
│       │   │   ├── VeiculoService.java
│       │   │   ├── DisponibilidadeService.java
│       │   │   ├── OrcamentoService.java
│       │   │   ├── PagamentoService.java
│       │   │   └── UsuarioService.java
│       │   ├── ui/                         # Menus de interação (console)
│       │   │   ├── MenuLogin.java
│       │   │   ├── MenuCliente.java
│       │   │   ├── MenuVeiculo.java
│       │   │   ├── MenuDisponibilidade.java
│       │   │   ├── MenuOrcamento.java
│       │   │   ├── MenuPagamento.java
│       │   │   └── MenuUsuario.java
│       │   └── util/
│       │       ├── HibernateUtil.java      # Configuração da SessionFactory
│       │       ├── Entrada.java            # Utilitário de leitura de dados
│       │       └── SenhaUtil.java          # Utilitário de segurança
│       └── resources/
│           └── hibernate.cfg.xml           # Configuração do banco de dados
└── pom.xml                                 # Dependências Maven
```

---

## Diagrama de Classes

As principais entidades do sistema e seus relacionamentos:

```
Usuario ──────────────────────── registra ──→ Orcamento
                                                  │
Cliente ──── pertence a ──────────────────────────┤
                                                  │
Veiculo ──── inclui ──────────────────────────────┤
   │                                              │
   └──── possui ──→ Disponibilidade               └──── gera ──→ Pagamento
```

**Entidades:**

| Classe | Atributos principais |
|---|---|
| `Usuario` | login, senha, perfil |
| `Cliente` | nome, CPF, telefone, endereço, e-mail |
| `Veiculo` | modelo, marca, placa, ano, categoria, valorDiaria |
| `Disponibilidade` | dataInicio, dataFim, status |
| `Orcamento` | dataInicio, dataFim, valorEstimado |
| `Pagamento` | valorPago, formaPagamento, data |

---

## Atores do Sistema

| Ator | Descrição | Permissões |
|---|---|---|
| **Administrador** | Gestor com acesso total | Todas as operações, incluindo exclusões e gerenciamento de usuários |
| **Atendente** | Funcionário de atendimento | Cadastros, consultas, orçamentos e pagamentos (sem exclusão de dados sensíveis) |

---

## Equipe

| Nome | Função |
|---|---|
| Alice Flávia Félix Lucena | Documentadora |
| Adrian Gabriel Ferreira Oliveira | Desenvolvedor Front-end |
| Jady Wéllyda de Albuquerque Silva | Desenvolvedora |
| João Vitor Lemos de Souza | Desenvolvedor |
| Luis Henrique dos Anjos Silva | Product Owner |
| Vívian Gabryelle Gomes Lucena Silva | Modeladora de Sistema (UML) |
| Vitória de Santo Antão | — |

**Orientadora:** Profª Ana Cristina  
**Instituição:** Centro Universitário UNIFACOL  
**Curso:** Bacharelado em Sistemas de Informação  
**Projeto:** APOO-2026-01

---

## 📄 Documentação

A documentação completa do projeto está disponível no arquivo `VeloRent_Documentacao.pdf`, contendo:

- Termo de Abertura do Projeto
- Visão do Produto e Backlog
- Documento de Requisitos (RF e RNF)
- Estórias de Usuário (US-001 a US-021)
- Diagrama de Classes UML
- Casos de Teste (CT-001 a CT-021)

---

*Projeto acadêmico desenvolvido para fins educacionais — UNIFACOL, 2026.*
