# Rodando a aplicação

## Banco de Dados
Nesta aplicação foi utilizado o banco de dados Postgres, para rodar a aplicação é necessário criar um banco de dados com o nome `postgres` e configurar o arquivo `application.properties` com as credenciais e portas do seu banco de dados.
Caso queira utilizar o docker, existe um arquivo `docker-compose.yml` na raiz do projeto que pode ser utilizado para subir um container com o banco de dados configurado, para isso basta executar o comando `docker-compose up -d` na raiz do projeto.

## Rodando o backend
1. Navegue até o diretorio `backend` 
2. Instale as dependencias do projeto com o maven
3. Inicialize o servidor do Spring Boot com o comando `./mvnw spring-boot:run` ou atraves da sua IDE

## Rodando o frontend
1. Navegue até o diretorio `frontend`
2. Instale as dependencias do projeto com o comando `npm i`
3. Inicialize o servidor do frontend com o comando `npm run dev`
4. Acesse o endereço `http://localhost:5173/` no seu navegador

# Tecnologias utilizadas
- Java 21
- Spring Boot
- Postgres
- React
- Vite
- Chakra-UI

# Notas sobre o projeto e sugestao de melhorias - Backend
1. Existe uma rotina agendada que a cada x tempo verifica se existem novos eventos para serem ativados e eventos antigos para serem inativados. Para saber mais sobre essa rotina, acesse o arquivo `src/main/java/com/events/chalenge/services/EventSchedulerService.java`
2. Em um segundo passo, eu pensaria em uma estrutura de onion architecture com a introducao de Use Cases.
3. Nenhum endpoint de busca possui paginacao, isso pode ser um problema caso a quantidade de eventos seja muito grande.
4. Adicionaria mais testes unitarios e de integracao entre componentes.
5. Adicionaria um error handling melhor.

# Notas sobre o projeto e sugestao de melhorias - Frontend
1. As validacoes dos dados enviados para o backend foram feitas utilizando a biblioteca Zod.
1. Utilizaria o Zustand para gerenciar o estado da aplicacao.
2. Adicionaria mais interatividade e feedback na interface para que todas as operacoes pudessem ser mais fluidas e informativas, como animacoes, loaders, toasters e banners de erros.
3. Adicionaria mais testes unitarios e de integracao entre componentes.
4. Abstrairia os componentes em componentes mais simples e reutilizaveis a fim de melhorar a legibilidade do codigo.
