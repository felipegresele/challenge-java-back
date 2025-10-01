🛠️ Mottu - API REST em Java
Este é o back-end da aplicação Mottu, desenvolvido em Java. Ele fornece uma API RESTful para operações como cadastro de usuários, cadastro de motos e listagem de motos, conectando-se diretamente com o front-end da aplicação via requisições HTTP.

Video de apresentação do projeto: https://www.youtube.com/watch?v=HGVIq_CFf2M

📚 Funcionalidades
✅ Cadastro de usuários

✅ Cadastro de motos

✅ Listagem de motos cadastradas

✅ Integração com front-end React via API REST

🔄 Integração com o Front-End
A aplicação se comunica com o front-end (desenvolvido em React) através de requisições HTTP. O front consome os endpoints da API, permitindo que o usuário interaja com o sistema de forma intuitiva via interface gráfica.

O front faz POST para /api/usuario/save ao cadastrar um novo usuário.

Faz POST para /api/moto/save ao cadastrar uma moto.

Faz GET para /api/moto/ git para exibir a lista de motos.

Certifique-se de que o servidor Java esteja rodando na mesma porta esperada pelo front (http://localhost:8080) ou configure o CORS caso necessário.

🔧 Tecnologias Utilizadas
Java 17+

Spring Boot ou Servlets (dependendo da versão)

JPA / Hibernate ou JDBC

MySQL

Maven

