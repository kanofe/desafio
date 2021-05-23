# Desafio Pro-Juris

Projeto Criado para o desafio ProJuris de API-REST para controle de Ordens. Criado utilizando o Spring boot, persistencia de dados usando o H2, Hibernate e JPA, utlizando-se do Faker para adição/inicialização de Clientes/Itens no H2.
Criado alguns testes unitarios package test

Passos para Executar o projeto:

1) Clone o projeto do repositorio
2) Acesse os arquivos da pasta "desafioProJuris"
3) Rode  a aplicação usando o "mvn spring-boot:run"
4) Agora você pode acessar os endpoints HTTP http://localhost:8080
5) Pode-se usar o H2 console para validar a inicialização de dados "http://localhost:8080/h2-console"
   1) Clientes e Itens Inicializados através do Faker
6) Observe a Documentação http://localhost:8080/swagger-ui.html