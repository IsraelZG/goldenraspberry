# Golden Raspberry Awards Project

(Don't speak portuguese? [Click here](https://github.com/IsraelZG/goldenraspberry/blob/main/README-en.md) to view this page in English.)

Este projeto é uma aplicação Java Spring Boot que gerencia informações sobre filmes e produtores, incluindo aqueles que receberam o infame prêmio Golden Raspberry. Este guia irá ajudá-lo a configurar e executar o projeto, assim como rodar os testes de integração.

## Pré-requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas em seu ambiente de desenvolvimento:

- [Java Development Kit (JDK) 22+](https://www.oracle.com/java/technologies/javase-jdk22-downloads.html)
- [Git](https://git-scm.com/)

*Nota: Este projeto inclui o Maven Wrapper (`mvnw`), portanto, não é necessário ter o Maven instalado separadamente.*

## Clonando o Repositório

Primeiro, clone o repositório para sua máquina local usando o comando Git:

```bash
git clone https://github.com/seu-usuario/golden-raspberry-awards.git
cd golden-raspberry-awards
```

## Configurando o Projeto

Este projeto utiliza um banco de dados em memória H2 para facilidade de desenvolvimento e testes. Não é necessário configurar um banco de dados externo.

## Rodando a Aplicação

Para executar a aplicação localmente, siga os passos abaixo:

1. **Compile o Projeto**: Navegue até o diretório do projeto e execute o seguinte comando para compilar o projeto e baixar as dependências:

   ```bash
   ./mvnw clean install
   ```
   
2. **Execute a Aplicação**: Após a compilação bem-sucedida, inicie a aplicação com o seguinte comando:

   ```bash
   ./mvnw spring-boot:run
   ```
A aplicação estará disponível em http://localhost:8082.

## Rodando os Testes

Para garantir que tudo está funcionando conforme esperado, é importante rodar os testes de integração incluídos no projeto. Siga os passos abaixo:

1. **Executar Testes**: Use o Maven Wrapper para rodar todos os testes:

   ```bash
   ./mvnw test

Isso executará todos os testes unitários e de integração definidos no projeto.

2. **Verificar Resultados**: Após a execução, verifique os resultados no console. Todos os testes devem passar sem erros.

## Acessando o Console do H2

Durante a execução da aplicação, você pode acessar o console web do banco de dados H2 para visualizar e interagir com os dados:

- Acesse `http://localhost:8082/h2-console`
- Use as seguintes credenciais:
  - **JDBC URL**: `jdbc:h2:mem:testdb`
  - **User Name**: `sa`
  - **Password**: *(deixe em branco)*



   
