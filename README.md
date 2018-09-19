# TransportesDimed
### 1.0.0 
### [ Base URL: com.br.dieggocarrilho/ ]
API para gestão e integração de linhas de ônibus e seus itinerários. As principais guides para definição da api são consultas a API PoaTransporte Operações (Integração) 
Listar as linhas de ônibus - http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%&t=o 
Listar itinerário de uma determinada unidade de transporte - http://www.poatransporte.com.br/php/facades/process.php?a=il&p=5566

Aplicaço baseada no spring boot usando spring data jpa e hibernate
Ela segue abordage de "contract-first" e sendo assim um (ou mais) arquivo Swagger  definido e apontado no POM. O código é gerado a cada build e a implementação da API usa a interface e o modelo gerado automáticamente a cada Build da aplicação.

Utiliza injeção de componentes (@INJECT) o que torna o código de serviços compativel com qualquer servidor Java EE, apenas a implementaço da API é dependente do spring boot.

## Levantando serviço via Eclipse
Executar a classe com.br.dieggocarrilho.linhas.LinhasApplication

### Documentaço para uso da API encontra-se no LINK: ###
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
Todos os lists so enviados ao front-end com o formato paginado.




