# Mandabank - Fintech Simulator API

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.1-green)
![Database](https://img.shields.io/badge/H2-In_Memory-blue)

[🇺🇸 English](#-english) | [🇧🇷 Português](#-português)

---

<a name="-english"></a>
## 🇺🇸 English

### 📖 About the Project
**Mandabank** is a simplified Fintech API designed to simulate banking operations, specifically focusing on PIX transfers between different types of users. The project implements a robust architecture with validation, security, and transaction consistency.

### 🛠 Tech Stack
* **Java 21**
* **Spring Boot 4.0.1** (Web, Data JPA, Validation, Security)
* **H2 Database** (In-memory database for rapid development/testing)
* **JWT (JSON Web Token)** for stateless authentication
* **Lombok** to reduce boilerplate code
* **MapStruct** for DTO <-> Entity mapping
* **Maven** for dependency management

### ⚙️ Key Features & Business Rules
1.  **User Types:**
    * **Common:** Can send and receive money. Validated by **CPF**.
    * **Merchant:** Can **only receive** money. Validated by **CNPJ**.
2.  **Validations:**
    * A user cannot be both Common and Merchant simultaneously (validated via custom validator `ValidCpfOrCnpj`).
    * Merchants are strictly prohibited from making transfers (throws `MerchantNotAuthorizedToMakeTransactionsException`).
    * Senders must have sufficient balance.
    * Senders cannot transfer money to themselves.
3.  **Security:**
    * Endpoints are protected by JWT.
    * Passwords are encrypted using BCrypt.

### 🚀 How to Run

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/DaniloAlves1902/mandabank-fintech-simulator-backend.git](https://github.com/DaniloAlves1902/mandabank-fintech-simulator-backend.git)
    cd mandabank-fintech-simulator-backend
    ```

2.  **Build and Run (using Maven Wrapper):**
    ```bash
    ./mvnw spring-boot:run
    ```
   

3.  **Access the H2 Console (Optional):**
    * URL: `http://localhost:8080/h2-console`
    * JDBC URL: `jdbc:h2:mem:testdb`
    * Username: `sa`
    * Password: *(leave empty)*
   

### 🔌 API Endpoints

#### 1. User Registration
`POST /api/v1/users/register`
* **Common User (CPF):**
    ```json
    {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john@example.com",
      "password": "secretPassword",
      "cpf": "123.456.789-00"
    }
    ```
* **Merchant User (CNPJ):**
    ```json
    {
      "firstName": "Tech Store",
      "lastName": "Ltda",
      "email": "store@example.com",
      "password": "secretPassword",
      "cnpj": "12.345.678/0001-90"
    }
    ```

#### 2. Authentication
`POST /api/v1/auth/login`
* Returns a JWT Token required for transactions.
    ```json
    {
      "login": "john@example.com",
      "password": "secretPassword"
    }
    ```

#### 3. Transactions (PIX)
`POST /api/v1/transactions/pix`
* **Header:** `Authorization: Bearer <YOUR_JWT_TOKEN>`
* **Body:**
    ```json
    {
      "payerId": "uuid-of-payer",
      "payeeId": "uuid-of-payee",
      "transactionValue": 100.00,
      "transactionType": "PIX"
    }
    ```

---

<a name="-português"></a>
## 🇧🇷 Português

### 📖 Sobre o Projeto
**Mandabank** é uma API simuladora de Fintech projetada para gerenciar operações bancárias, com foco específico em transferências PIX entre diferentes tipos de usuários. O projeto implementa uma arquitetura robusta com validações, segurança e consistência transacional.

### 🛠 Tecnologias Utilizadas
* **Java 21**
* **Spring Boot 4.0.1** (Web, Data JPA, Validation, Security)
* **Banco de Dados H2** (Em memória para desenvolvimento/testes rápidos)
* **JWT (JSON Web Token)** para autenticação stateless
* **Lombok** para redução de código repetitivo
* **MapStruct** para mapeamento DTO <-> Entidade
* **Maven** para gerenciamento de dependências

### ⚙️ Funcionalidades e Regras de Negócio
1.  **Tipos de Usuários:**
    * **Comum (Common):** Pode enviar e receber dinheiro. Validado por **CPF**.
    * **Lojista (Merchant):** Pode **apenas receber** dinheiro. Validado por **CNPJ**.
2.  **Validações:**
    * Um usuário não pode ter CPF e CNPJ simultaneamente (validado via validador customizado `ValidCpfOrCnpj`).
    * Lojistas são estritamente proibidos de realizar transferências (lança `MerchantNotAuthorizedToMakeTransactionsException`).
    * O pagador deve ter saldo suficiente.
    * O pagador não pode transferir dinheiro para si mesmo.
3.  **Segurança:**
    * Endpoints protegidos por JWT.
    * Senhas criptografadas usando BCrypt.

### 🚀 Como Executar

1.  **Clonar o repositório:**
    ```bash
    git clone [https://github.com/DaniloAlves1902/mandabank-fintech-simulator-backend.git](https://github.com/DaniloAlves1902/mandabank-fintech-simulator-backend.git)
    cd mandabank-fintech-simulator-backend
    ```

2.  **Compilar e Rodar (usando Maven Wrapper):**
    ```bash
    ./mvnw spring-boot:run
    ```
   

3.  **Acessar o Console H2 (Opcional):**
    * URL: `http://localhost:8080/h2-console`
    * JDBC URL: `jdbc:h2:mem:testdb`
    * Username: `sa`
    * Password: *(deixar vazio)*
   

### 🔌 Endpoints da API

#### 1. Cadastro de Usuário
`POST /api/v1/users/register`
* **Usuário Comum (CPF):**
    ```json
    {
      "firstName": "João",
      "lastName": "Silva",
      "email": "joao@exemplo.com",
      "password": "senhaSecreta",
      "cpf": "123.456.789-00"
    }
    ```
* **Lojista (CNPJ):**
    ```json
    {
      "firstName": "Tech Store",
      "lastName": "Ltda",
      "email": "loja@exemplo.com",
      "password": "senhaSecreta",
      "cnpj": "12.345.678/0001-90"
    }
    ```

#### 2. Autenticação
`POST /api/v1/auth/login`
* Retorna um Token JWT necessário para as transações.
    ```json
    {
      "login": "joao@exemplo.com",
      "password": "senhaSecreta"
    }
    ```

#### 3. Transações (PIX)
`POST /api/v1/transactions/pix`
* **Header:** `Authorization: Bearer <SEU_TOKEN_JWT>`
* **Body:**
    ```json
    {
      "payerId": "uuid-do-pagador",
      "payeeId": "uuid-do-beneficiario",
      "transactionValue": 100.00,
      "transactionType": "PIX"
    }
    ```
