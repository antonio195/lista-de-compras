# Lista de Compras

Uma aplicação Android moderna para gerenciamento de listas de compras, desenvolvida com as melhores práticas de desenvolvimento Android.

## Tecnologias e Bibliotecas

- **[Kotlin](https://kotlinlang.org/)**: Linguagem de programação principal.
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)**: Toolkit moderno para construção de UI nativa.
- **[Material 3](https://m3.material.io/)**: Última versão do sistema de design do Google.
- **[Room Database](https://developer.android.com/training/data-storage/room)**: Persistência de dados local fluida.
- **[Koin](https://insert-koin.io/)**: Framework de Injeção de Dependência leve e pragmático.
- **[Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)**: Arquitetura organizada em camadas (Data, Domain, Presenter, Core).

## Arquitetura do Projeto

O projeto segue os princípios da Clean Architecture:

- **`core`**: Elementos compartilhados e tema da aplicação (`UI Theme`).
- **`data`**: Implementação da persistência de dados (Room), DAOs e repositórios.
- **`domain`**: Regras de negócio, modelos (`Product`) e interfaces de repositórios.
- **`presenter`**: Camada de interface de usuário (Composables e ViewModels).

## Funcionalidades

- [x] Listagem de produtos.
- [x] Persistência local com Room.
- [x] Interface reativa com Compose.
- [x] Adição, edição e exclusão de itens (em implementação).

## Como executar o projeto

1. Clone o repositório.
2. Abra no Android Studio.
3. Sincronize o Gradle e execute no emulador ou dispositivo físico.
