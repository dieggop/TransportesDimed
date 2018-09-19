# TransportesDimed
### 1.0.0 
### [ Base URL: com.br.dieggocarrilho/ ]
API para gestão e integração de linhas de ônibus e seus itinerários. As principais guides para definição da api são consultas a API PoaTransporte Operações (Integração)

Listar as linhas de ônibus - http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%&t=o 

Listar itinerário de uma determinada unidade de transporte - http://www.poatransporte.com.br/php/facades/process.php?a=il&p=5566

Aplicação baseada no spring boot usando spring data jpa e hibernate, seguindo abordagem de "contract-first" e sendo assim um (ou mais) arquivo Swagger definido e apontado no POM. O código é gerado a cada build e a implementação da API usa a interface e o modelo gerado automaticamente a cada Build da aplicação.

Utiliza injeção de componentes (@INJECT) o que torna o código de serviços compatível com qualquer servidor Java EE, apenas a implementação da API é dependente do spring boot.

## Levantando serviço via Eclipse
Executar a classe com.br.dieggocarrilho.linhas.LinhasApplication

### Documentação para uso da API encontra-se no LINK: ###
+ https://app.swaggerhub.com/apis-docs/dieggop/transportes-dimed/1.0.0

Sistema criado em Spring Boot, utilizando das seguintes tecnologias:
- SpringBoot
- Spring Security
- GSON
- Swagger
- JJWT
- jackson
- Org.Json
- JPA
- Hibernate
- MySql

As Dependências são instaladas utilizando o gerenciador MAVEN

## Combos
Todos os lists so enviados ao front-end com o formato páginado.

### Utilizando

Deve ser criado um banco de nome: linhasonibus

- create table linhasonibus;

As entidades estão mapeadas para gerar suas respectivas tabelas.

#### O sistema foi feito como se estivesse sendo consumido de um aplicativo Mobile, o cadastro é realizado pelo cliente.

## Cadastro
Para criar um novo usuário, não há necessidade de estar logado, uma vez que após o cadastro ter sido realizado ele seguirar para realizar seu login

Para criar seu cadastro um POST deve ser enviado para: http://localhost:8080/cliente/sign-up

Modelo de envio do objeto JSON:
```
{
    "id": null,
    "username": "oliveira",
    "name": "Leticia Oliveira",
    "email": "oliveira@gmail.com",
    "contato": "81998257282",
    "password" : 123456
} 
```
* A senha será criptografada na aplicação, para armazenamento na base de dados

Para alimentar/atualizar a base com as informações provenientes da api "PoaTransporte" o usuário tem que estar logado.

## Login
Para realizar o Login deve acessar: **http://localhost:8080/login**

O envio é em formato Json, deve ser enviado o username e a senha;
```
{
	"username": "oliveira",
	"password" : "123456"
}
```
Obtendo sucesso no login, será retornado no header do response um token de authenticação. 
Para cada requisição deve ser encaminhado o Token como header.

Com estas informações, o sistema tem o seguinte comportamento:
>  * Obter um Token
> * Fazer um request enviando o Token
> * Validar o token
> * Retornar o resultado da requisição

Isto acontece pois o contexto de serviços é diferente das aplicações convencionais, onde temos a duração do mesmo para apenas uma requisição. A requisição acontece, você autentica seu usuário, retorna o resultado (200, 404, 401) e essa requisição terminou.
Estamos trabalhando com o Bearer pois ele trafega um token e não um usuário/senha no header. A cada requisição devemos enviar o Token, sendo assim, uma requisição a um endpoint ficaria neste formato:

**Bearer**
```
Content-Type: application/json
Authorization: Bearer SEUTOKENAQUI
```

## Dados do cliente logado
Para  visualizar dados cadastrais do usuário logado acessar: localhost:8080/cliente/
o http do acesso é GET

### Atualizando Dados do cliente logado
Para realizar uma atualização dos dados cadastrais deve enviar uma requisição PUT para: localhost:8080/cliente/{id} --> (onde ID é o id do cliente logado)

Formato do Objeto enviado para alteração:
```
{
    "id": 46,
    "username": "oliveira",
    "name": "Leticia Oliveira Carrilho",
    "email": "oliveira@gmail.com",
    "contato": "81998257282",
    "password" : 123456
}
```

### Desativando conta
Para desativar o cadastro do cliente, deve fazer requisição HTTP do tipo DELETE para a rota: localhost:8080/cliente/

## Primeira Consulta
### Consultando Unidades de transportes do sistema

Para listar as linhas de ônibus cadastradas a requisição deve ser para: localhost:8080/linhas/filtrar
Como parâmetros tipo query é possível enviar: 
* page -> Página a ser recuperada, comecando de 1 e se omitida a primeira página com 200 registros é retornada
* per_page -> Quantidade de itens por página, no máximo 200
Exemplo de retorno:
```
{
    "page": 1,
    "per_page": 200,
    "pages": 5,
    "total": 983,
    "linhas": [
        {
            "id": 5000,
            "codigo": "110-1",
            "nome": "RESTINGA NOVA VIA TRISTEZA"
        },
        {
            "id": 5001,
            "codigo": "110-2",
            "nome": "RESTINGA NOVA VIA TRISTEZA"
        }
}
```

### Consultando Itinerário de uma determinada Unidade de Transporte
Para listar o itinerário de uma UT deve ser enviada uma requisição para: localhost:8080/coordenadas/listar?idUt={idUT}&page=1&per_page=20

Como parâmetros tipo query é possível enviar: 
* idUT ->  identificação de uma unidade de transporte
* page -> Página a ser recuperada, comecando de 1 e se omitida a primeira página com 200 registros é retornada
* per_page -> Quantidade de itens por página, no máximo 200

Exemplo: localhost:8080/coordenadas/listar?idUt=5021page=1&per_page=50
* Exemplo de retorno:
```
{
    "page": 1,
    "per_page": 200,
    "pages": 2,
    "total": 377,
    "itinerarios": [
        {
            "idlinha": 5021,
            "lat": -30.027321287659,
            "lng": -51.229266198927
        },
}
```
### Cadastrando unidade de transporte
### Consultando suas unidades de transportes

Para listar as linhas de ônibus do cliente deve acessar: http://localhost:8080/cliente/linhas/listar?page=1&per_page=10

Como parâmetros tipo query é possível enviar: 
* page -> Página a ser recuperada, comecando de 1 e se omitida a primeira página com 200 registros é retornada
* per_page -> Quantidade de itens por página, no máximo 200


### Cadastrando Unidade de Transporte do cliente
Para cadastrar uma UT para o cliente deve ser enviado requisição POST para o endereço: localhost:8080/cliente/linhas/cadastrar
contendo como Body o objeto json:
```
{
    "id": 5003,
    "codigo": "1101-2",
    "nome": "RESTINGA NOVA VIA TRISTEZA/DOMINGOS E FERIADOS"
}
```
### Removendo UT do cliente
Para descadastrar uma UT do cliente basta enviar uma requisição DELETE para a rota: localhost:8080/cliente/linhas/{idUT}/remover
> idUt -> é o ID da unidade de transporte.

### Listando UTs do cliente
Para listar as UTs cadastradas basta acessar a rota: localhost:8080/cliente/linhas/listar?per_page=10&page=1

Como parâmetros tipo query é possível enviar: 
* page -> Página a ser recuperada, comecando de 1 e se omitida a primeira página com 200 registros é retornada
* per_page -> Quantidade de itens por página, no máximo 200

Retorno:
```
{
    "page": 1,
    "per_page": 10,
    "pages": 1,
    "total": 1,
    "linhas": [
        {
            "id": 5003,
            "codigo": "1101-2",
            "nome": "RESTINGA NOVA VIA TRISTEZA/DOMINGOS E FERIADOS"
        }
    ]
}
```

### ATUALIZANDO O SISTEMA
#### Unidades de Transportes
Uma requisição deve ser enviada para localhost:8080/linhas/baixar

O retorno é um Status Header 200 caso tenha dado certo ou 409 caso tenha dado errado

#### Itinerários de uma Unidade de Transporte
Uma requisição deve ser enviada para localhost:8080/coordenadas/baixar?idUt={idUt}

* onde idUT é um ID de uma Unidade de Transporte

O retorno é um Status Header 200 caso tenha dado certo ou 409 caso tenha dado errado

