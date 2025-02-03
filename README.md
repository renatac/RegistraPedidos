# RegistraPedidos - Aplicativo de Gerenciamento de Pedidos de Venda

**RegistraPedidos** é um aplicativo móvel projetado para otimizar o processo de pedidos de venda para vendedores. Ele fornece uma interface amigável para registrar e gerenciar pedidos de venda de forma eficiente.

## Principais Recursos

*   **Registro de Pedidos:** Os vendedores podem registrar facilmente novos pedidos de venda, capturando detalhes essenciais como:
    *   Nome do Cliente;
    *   Nome do Produto;
    *   Quantidade;
    *   Preço Unitário (R$);
*   **Gerenciamento de Descontos:**
    *   Aplicar e remover descontos em pedidos.
    *   Os descontos são calculados proporcionalmente em todos os itens de um pedido.
        ***Cálculo Proporcional de Descontos:** O aplicativo distribui de forma inteligente os descontos entre várias vendas dentro de um único pedido. Por exemplo, se uma cliente (ex: Maria) tiver dois pedidos de venda totalizando R$10,00 e R$20,00, e um vendedor aplicar um desconto de R$15,00, o aplicativo ajustará os totais dos pedidos proporcionalmente. Neste exemplo, os pedidos de Maria seriam reduzidos para R$5,00 e R$10,00, respectivamente.
*   **Visão Geral dos Pedidos:**
    *   Visualize todos os pedidos de venda registrados de forma organizada.
    *   Adicione novos pedidos ou exclua os existentes com facilidade.
* **Interface Amigável:**
    * Design intuitivo para fácil navegação e uso.

## Tecnologias Utilizadas

Este aplicativo é construído usando as melhores práticas modernas de desenvolvimento Android e uma arquitetura robusta:

*   **Arquitetura:** MVVM (Model-View-ViewModel) para uma separação clara de responsabilidades e testabilidade.
*   **Persistência de Dados:** Room Persistence Librarypara armazenamento local de dados eficiente e confiável.
*   **Injeção de Dependência:** Koin para gerenciamento simplificado de dependências e melhor testabilidade.
*   **Testes Unitários:** MockK para criar objetos simulados (mocks) e simplificar os testes unitários.
*   **Framework de UI:** Jetpack Compose para construir uma interface de usuário moderna e declarativa.
*   **Código Limpo:** A base de código é estruturada com forte ênfase nos princípios de Código Limpo e melhores práticas, garantindo manutenibilidade e escalabilidade.

## Estrutura do Project

O projeto é organizado em camadas bem definidas, seguindo os princípios da Arquitetura Limpa:

*   **`database`:** Contém a implementação do banco de dados Room, entidades e DAOs.
*   **`domain`:** Contém a lógica de negócios, casos de uso e interfaces de repositório.
*   **`presentation`:** Contém os componentes da UI (Jetpack Compose), ViewModels e lógica relacionada à UI.
*   **`di`:** Contém os módulos Koin para injeção de dependência.
* **`domain`:** Contém a implementação do repositório.
