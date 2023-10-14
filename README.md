# Test Prático NetDeal FullStack

Projeto Front-End e Back-End para teste técnico de cadastrode hierarquia.

#### Banco de dados
- Mysql latest

> Usei o docker para montar um container com o mysql.
> - "docker compose up -d --build"

#### Back-End
- Java OpenJdk 21
- Maven 3.0
- String Boot 3.1.4
- Spring HATEOAS
- Spring Data
- Lombok 1.18
- MapStruct 1.5.5
- Spring REST API Using OpenAPI 3.0
- jBCrypt-0.4
- Junit
- Mockito

> Nome do banco: dbnetdeal <br>
> Url de conexão: jdbc:mysql://localhost:3306/dbnetdeal <br>
> Script para inicialização do data tabela: teste-netdeal/src/main/resources/schema.sql <br>
> Para inclusão na inicialização descomentar linhas no script
> Port utilizada 8080

#### Swagger
- http://localhost:8080/swagger-ui/index.html

#### Front-End
- AngularJs 1.8.2

> Pasta do front "teste-netdeal/front-end" <br><br>
> comando: <br>
> npm install -g http-server <br>
> npm run start

>  #### Funcionalidade e Regras:
>  - Novo colaborador:
>    - Preencher campo nome mínimo de 1 a no máximo 100 caracteres. (campo obrigatório).
>    - Preencher campo sehna mínimo de 8 a no máxmio 100 carateres. (campo obrigatório).
>    - Para selecionar um Superior e so clicar e um item da lista. (campo não obrigatorio, será exibido na tela o selecionao).
>    - Botão "Limpar" reseta os dados.
>    - Botão "Salvar" so ser habilitado se os dados estiverem válidos. <br>
>    
> 
>  - Edição de colaborador:
>    - Para a edição segue as mesmas regras de um novo cadastro.
>    - Se for selecionador um superior ao qual ele e filho da propria lista ele deixara de ser filho e passara a ser 
>     superior do item.
>    - Na edição a senha não e exibida (mas se inserida ela se alterada).
> 
>
>  - Exclusão de colaboradoes
>    - regra aplicada: se um item for excluído e se tiver inferiores os mesmos serao excluídos.
>

#### IDE utilizada Intellij IDEA 2023