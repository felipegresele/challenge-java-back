README – Projeto Java Challenge Mottu (Entrega 3)
1. Visão Geral

Este projeto é uma aplicação Java com Spring Boot desenvolvida como entrega do Challenge Mottu – Entrega 3.
A aplicação gerencia registros de Manutenção, Motos, Galpões e Motoqueiros, permitindo diferentes operações de acordo com o tipo de usuário.

Banco de Dados: PostgreSQL

Versionamento do Banco: Flyway (com população automática de dados iniciais)

Execução do projeto: mvn spring-boot:run

Portas:

Backend (API): 8080

Frontend (Web): 8080/home

2. Endpoints
2.1 Manutenção
Método	Endpoint	Descrição	Permissão
GET	/manutencoes/listar	Lista todas as manutenções	Admin e Operador
POST	/manutencoes/save	Adiciona uma nova manutenção	Admin
PUT	/manutencoes/{id}	Edita manutenção pelo ID	Admin
DELETE	/manutencoes/{id}	Exclui manutenção pelo ID	Admin
2.2 Motos
Método	Endpoint	Descrição	Permissão
GET	/motos/listar	Lista todas as motos	Admin e Operador
POST	/motos/save	Adiciona uma nova moto	Admin
PUT	/motos/{id}	Edita moto pelo ID	Admin
DELETE	/motos/{id}	Exclui moto pelo ID	Admin
2.3 Galpões
Método	Endpoint	Descrição	Permissão
GET	/galpoes/listar	Lista todos os galpões	Admin e Operador
POST	/galpoes/save	Adiciona um novo galpão	Admin
PUT	/galpoes/{id}	Edita galpão pelo ID	Admin
DELETE	/galpoes/{id}	Exclui galpão pelo ID	Admin
2.4 Motoqueiros
Método	Endpoint	Descrição	Permissão
GET	/motoqueiros/listar	Lista todos os motoqueiros	Admin e Operador
POST	/motoqueiros/save	Adiciona um novo motoqueiro	Admin
PUT	/motoqueiros/{id}	Edita motoqueiro pelo ID	Admin
DELETE	/motoqueiros/{id}	Exclui motoqueiro pelo ID	Admin
3. Regras de Permissão

Admin: Pode listar, adicionar, editar e excluir todos os registros.

Operador: Pode apenas listar registros. Tentativas de adicionar, editar ou excluir resultam em mensagem de erro de permissão na aplicação web.

4. Fluxo da Aplicação Web

Usuário acessa: http://localhost:8080/home

Tela inicial: Login / Cadastro

Após login bem-sucedido:

Redirecionamento para o Dashboard

Dashboard exibe todos os grupos (Motos, Galpões, Motoqueiros, Manutenção)

Usuário vê os dados conforme sua permissão

Interações:

Admin: pode clicar em Adicionar / Editar / Excluir

Operador: apenas visualiza, sem ação nos botões CRUD

5. Banco de Dados

Tipo: PostgreSQL

Versionamento e População Inicial: Flyway

Funcionalidade:

Cria tabelas automaticamente

Popula dados padrão para testes iniciais

6. Comando para Rodar a Aplicação
mvn spring-boot:run

7. Vídeo de Apresentação

Vídeo demonstrativo do projeto:
https://www.youtube.com/watch?v=HGVIq_CFf2M
