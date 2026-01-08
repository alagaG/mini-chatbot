# Projeto ChatBot 🤖

Este repositório possui um mini-projeto de chatbot contando com a funcionalidade principal de conversação. Ele foi dividido em 2 _branchs_, a "frontend" para consumir a API através de uma interface e a "main" que contém o código fonte do backend que disponibiliza a IA através de uma API REST

## Tecnologias

Neste projeto foram utilizadas as seguintes tecnologias:

-   Java 17
-   Node 20.9+
-   Spring
    -   Spring Web
    -   Spring DevTools
    -   Spring Google GenAi
    -   Lombok
-   Next JS
    -   Typescript
    -   React
    -   Clsx

## Como Rodar 🔨

Para testar o projeto, basta clonar ambas as _branchs_, instalar suas dependências e dentro de uma IDE invocar o comando de rodar.

### Backend

1. Instalar o Java 17
2. Instalar uma IDE Java (Eclipse, IntelliJ)
3. Navegue até `src/main/java/iachat/src/main/resources` e crie um arquivo `.env`
4. Adicione a sua chave da API do Gemini ao `.env` como `GEMINI_API_KEY`
5. Rode seu projeto dentro da IDE com Java 17

### Frontend

1. Intalar o Node 20.9+
2. Abrir um console na raíz do projeto
3. Passar o comando `npm install` para instalar as dependências
4. Rodar com `npm run dev`
