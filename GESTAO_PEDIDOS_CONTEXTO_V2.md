# CONTEXTO — Sistema de Gestão de Pedidos (Estudo)

## Objetivo
Projeto de estudo e portfólio com backend Java (Spring Boot),  
seguindo aprendizado progressivo no modo **Shu** (Shu-Ha-Ri).

## Stack
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Bean Validation
- PostgreSQL
- Maven

## Estilo
- API REST
- DTO para entrada e saída
- Validação com `@Valid`
- Exceptions customizadas
- Handler global com `@RestControllerAdvice`
- Commits organizados por steps (`step-01`, `step-02`, …)


## Steps concluídos

### Base
- **step-01**: criação do projeto Spring Boot
- **step-02**: entidade `Produto`
- **step-03**: `ProdutoRepository`
- **step-04**: GET `/produtos`
- **step-05**: DTO de saída (`ProdutoDTO`)
- **step-06**: POST básico
- **step-07**: `ProdutoCreateDTO`
- **step-08**: validação com Bean Validation
- **step-09**: POST com DTO + HTTP 201
- **step-10**: `GlobalExceptionHandler`, `ApiError` e 404 para produto não encontrado

---

### Domínio de Pedidos
- **step-11**:
    - Criação do domínio **Pedido** e **ItemPedido**
    - Enum `PedidoStatus`
    - Relacionamentos JPA (`@OneToMany`, `@ManyToOne`)
    - Cálculo do total do pedido
    - Persistência correta no banco
    - Criação das tabelas `pedidos` e `itens_pedido`

- **step-12**:
    - DTOs compostos:
        - `PedidoCreateDTO`
        - `ItemPedidoCreateDTO`
        - `PedidoDTO`
        - `ItemPedidoDTO`
    - `PedidoRepository`
    - `PedidoService`
    - POST `/pedidos` funcionando
    - Pedido salvo com múltiplos itens
    - Aplicação validada via `curl`
    - Separação correta:
        - Controller → DTO
        - Service → Entidade
        - Repository → Persistência

---

## Branch's criadas
- `master`
- `step-02-first-service`
- `step-03-domain-model`
- `step-04-in-memory-repository`
- `step-05-jpa-postgresql`
- `step-06-repository`
- `step-06-repository_v2`
- `step-07-dto-read`
- `step-08-dto-write`
- `step-09-dto-post-validation`
- `step-10-exception-handler`
- `step-11-pedido-itempedido`
- `step-12-post-pedido`

---

## Padrões importantes
- Controller **retorna DTO**
- Service **trabalha com entidade**
- Nunca expor entidade diretamente na API
- HTTP Status corretos sempre
- Repository **nunca é usado no Controller**
- Commit description começa com `STEP-XX`
- Sem pular etapas (modo **Shu**)

---

## Próximo passo planejado
**STEP-13: GET Pedido por ID**
- `GET /pedidos/{id}`
- Busca via Service
- Retorno em `PedidoDTO`
- Tratamento de pedido não encontrado
- Manter padrão dos steps anteriores

## Estrutura de Diretorios

everson@SitioPC:~/IdeaProjects/gestao-pedidos/src$ ls -R
.:
main  test

./main:
java  resources

./main/java:
br

./main/java/br:
com

./main/java/br/com:
everson

./main/java/br/com/everson:
gestaopedidos

./main/java/br/com/everson/gestaopedidos:
controller  domain  dto  exception  GestaoPedidosApplication.java  repository  service

./main/java/br/com/everson/gestaopedidos/controller:
HelloController.java  PedidoController.java  ProdutoController.java  ProdutoFakeController.java

./main/java/br/com/everson/gestaopedidos/domain:
pedido  Produto.java

./main/java/br/com/everson/gestaopedidos/domain/pedido:
ItemPedido.java  Pedido.java  PedidoStatus.java

./main/java/br/com/everson/gestaopedidos/dto:
ItemPedidoCreateDTO.java  ItemPedidoDTO.java  PedidoCreateDTO.java  PedidoDTO.java  ProdutoCreateDTO.java  ProdutoDTO.java

./main/java/br/com/everson/gestaopedidos/exception:
ApiError.java  GlobalExceptionHandler.java  ProdutoNaoEncontradoException.java

./main/java/br/com/everson/gestaopedidos/repository:
PedidoRepository.java  ProdutoFakeRepository.java  ProdutoRepository.java

./main/java/br/com/everson/gestaopedidos/service:
HelloService.java  PedidoService.java  ProdutoFakeService.java  ProdutoService.java

./main/resources:
application.properties  static  templates

./main/resources/static:

./main/resources/templates:

./test:
java

./test/java:
br

./test/java/br:
com

./test/java/br/com:
everson

./test/java/br/com/everson:
gestaopedidos

./test/java/br/com/everson/gestaopedidos:
GestaoPedidosApplicationTests.java