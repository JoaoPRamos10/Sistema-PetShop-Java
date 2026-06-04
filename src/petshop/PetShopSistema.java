package petshop;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PetShopSistema {

    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Animal> animais = new ArrayList<>();
    private static List<Servico> servicos = new ArrayList<>();
    private static List<Agendamento> agendamentos = new ArrayList<>();
    private static List<Produto> produtos = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            mostrarMenuPrincipal();
            int opcao = lerOpcao();

            try {
                switch (opcao) {
                    case 1:
                        gerenciarClientes();
                        break;
                    case 2:
                        gerenciarAnimais();
                        break;
                    case 3:
                        gerenciarServicos();
                        break;
                    case 4:
                        gerenciarProdutos();
                        break;
                    case 5:
                        gerenciarAgendamentos();
                        break;
                    case 6:
                        System.out.println("Saindo do sistema...");
                        scanner.close();
                        return;
                    default:
                        throw new ExcecaoPersonalizada("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Erro: Entrada inválida. Digite um número.");
                scanner.next(); // Limpar o buffer do scanner
            } catch (ExcecaoPersonalizada e) {
                System.err.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n--- Sistema Pet Shop ---");
        System.out.println("1. Gerenciar Clientes");
        System.out.println("2. Gerenciar Animais");
        System.out.println("3. Gerenciar Serviços");
        System.out.println("4. Gerenciar Produtos");
        System.out.println("5. Gerenciar Agendamentos");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static int lerOpcao() {
        return scanner.nextInt();
    }

    // --- Métodos de Gerenciamento (CRUD com Tratamento de Exceções) ---

    private static void gerenciarClientes() {
        while (true) {
            System.out.println("\n--- Gerenciar Clientes ---");
            System.out.println("1. Criar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Editar Cliente");
            System.out.println("4. Remover Cliente");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = lerOpcao();
            scanner.nextLine();

            try {
                switch (opcao) {
                    case 1:
                        criarCliente();
                        break;
                    case 2:
                        listarClientes();
                        break;
                    case 3:
                        editarCliente();
                        break;
                    case 4:
                        removerCliente();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (ExcecaoPersonalizada e) {
                System.err.println("Erro ao gerenciar cliente: " + e.getMessage());
            }
        }
    }

    private static void criarCliente() throws ExcecaoPersonalizada {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Rua: ");
        String rua = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("CEP: ");
        String cep = scanner.nextLine();

        if (nome.trim().isEmpty() || rua.trim().isEmpty() || cidade.trim().isEmpty() || estado.trim().isEmpty() || cep.trim().isEmpty()) {
            throw new ExcecaoPersonalizada("Todos os campos do cliente devem ser preenchidos.");
        }

        Endereco endereco = new Endereco(rua, cidade, estado, cep);
        Cliente cliente = new Cliente(nome, endereco);
        clientes.add(cliente);
        System.out.println("Cliente criado: " + cliente);
    }

    private static void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            System.out.println("--- Lista de Clientes ---");
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
    }
    private static void editarCliente() throws ExcecaoPersonalizada {
        listarClientes();
        System.out.print("Digite o nome do cliente a ser editado: ");
        String nome = scanner.nextLine();

        Cliente cliente = clientes.stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nome))
                .findFirst().orElse(null);

        if (cliente == null) {
            throw new ExcecaoPersonalizada("Cliente não encontrado.");
        }

        System.out.print("Novo nome do cliente: ");
        String novoNome = scanner.nextLine();
        System.out.print("Nova rua: ");
        String novaRua = scanner.nextLine();
        System.out.print("Nova cidade: ");
        String novaCidade = scanner.nextLine();
        System.out.print("Novo estado: ");
        String novoEstado = scanner.nextLine();
        System.out.print("Novo CEP: ");
        String novoCep = scanner.nextLine();

        cliente.setNome(novoNome);
        cliente.getEndereco().setRua(novaRua);
        cliente.getEndereco().setCidade(novaCidade);
        cliente.getEndereco().setEstado(novoEstado);
        cliente.getEndereco().setCep(novoCep);

        System.out.println("Cliente atualizado: " + cliente);
    }

    private static void removerCliente() throws ExcecaoPersonalizada {
        listarClientes();
        System.out.print("Digite o nome do cliente a ser removido: ");
        String nome = scanner.nextLine();

        Cliente cliente = clientes.stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nome))
                .findFirst().orElse(null);

        if (cliente == null) {
            throw new ExcecaoPersonalizada("Cliente não encontrado.");
        }

        // Verificar se o cliente tem agendamentos antes de remover
        boolean temAgendamentos = agendamentos.stream()
                .anyMatch(a -> a.getCliente().equals(cliente));

        if (temAgendamentos) {
            throw new ExcecaoPersonalizada("Não é possível remover cliente com agendamentos ativos.");
        }

        clientes.remove(cliente);
        System.out.println("Cliente removido com sucesso.");
    }

    private static void gerenciarAnimais() {
        while (true) {
            System.out.println("\n--- Gerenciar Animais ---");
            System.out.println("1. Criar Animal");
            System.out.println("2. Listar Animais");
            System.out.println("3. Editar Animal");
            System.out.println("4. Remover Animal");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = lerOpcao();
            scanner.nextLine();

            try {
                switch (opcao) {
                    case 1:
                        criarAnimal();
                        break;
                    case 2:
                        listarAnimais();
                        break;
                    case 3:
                        editarAnimal();
                        break;
                    case 4:
                        removerAnimal();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (ExcecaoPersonalizada e) {
                System.err.println("Erro ao gerenciar animal: " + e.getMessage());
            }
        }
    }

    private static void criarAnimal() throws ExcecaoPersonalizada {
        System.out.print("Nome do animal: ");
        String nome = scanner.nextLine();
        System.out.print("Espécie: ");
        String especie = scanner.nextLine();
        System.out.print("Raça: ");
        String raca = scanner.nextLine();

        if (nome.trim().isEmpty() || especie.trim().isEmpty() || raca.trim().isEmpty()) {
            throw new ExcecaoPersonalizada("Todos os campos do animal devem ser preenchidos.");
        }

        Animal animal = new Animal(nome, raca, especie);
        animais.add(animal);
        System.out.println("Animal criado: " + animal);
    }

    private static void listarAnimais() {
        if (animais.isEmpty()) {
            System.out.println("Nenhum animal cadastrado.");
        } else {
            System.out.println("--- Lista de Animais ---");
            for (Animal animal : animais) {
                System.out.println(animal);
            }
        }
    }
    private static void editarAnimal() throws ExcecaoPersonalizada {
        listarAnimais();
        System.out.print("Digite o nome do animal a ser editado: ");
        String nome = scanner.nextLine();

        Animal animal = animais.stream()
                .filter(a -> a.getNome().equalsIgnoreCase(nome))
                .findFirst().orElse(null);

        if (animal == null) {
            throw new ExcecaoPersonalizada("Animal não encontrado.");
        }

        System.out.print("Novo nome do animal: ");
        String novoNome = scanner.nextLine();
        System.out.print("Nova raça: ");
        String novaRaca = scanner.nextLine();

        animal.setNome(novoNome);
        animal.setRaca(novaRaca);

        System.out.println("Animal atualizado: " + animal);
    }

    private static void removerAnimal() throws ExcecaoPersonalizada {
        listarAnimais();
        System.out.print("Digite o nome do animal a ser removido: ");
        String nome = scanner.nextLine();

        Animal animal = animais.stream()
                .filter(a -> a.getNome().equalsIgnoreCase(nome))
                .findFirst().orElse(null);

        if (animal == null) {
            throw new ExcecaoPersonalizada("Animal não encontrado.");
        }

        // Verificar se o animal está em algum agendamento
        boolean temAgendamentos = agendamentos.stream()
                .anyMatch(a -> a.getAnimal().equals(animal));

        if (temAgendamentos) {
            throw new ExcecaoPersonalizada("Não é possível remover animal com agendamentos ativos.");
        }

        animais.remove(animal);
        System.out.println("Animal removido com sucesso.");
    }

    private static void gerenciarServicos() {
        while (true) {
            System.out.println("\n--- Gerenciar Serviços ---");
            System.out.println("1. Criar Serviço");
            System.out.println("2. Listar Serviços");
            System.out.println("3. Editar Serviço");
            System.out.println("4. Remover Serviço");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = lerOpcao();
            scanner.nextLine();

            try {
                switch (opcao) {
                    case 1:
                        criarServico();
                        break;
                    case 2:
                        listarServicos();
                        break;
                    case 3:
                        editarServico();
                        break;
                    case 4:
                        removerServico();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (ExcecaoPersonalizada e) {
                System.err.println("Erro ao gerenciar serviço: " + e.getMessage());
            }
        }
    }

    private static void criarServico() throws ExcecaoPersonalizada {
        System.out.print("Descrição do serviço: ");
        String descricao = scanner.nextLine();
        if(descricao.trim().isEmpty()){
            throw new ExcecaoPersonalizada("Descrição do serviço não pode ser vazia.");
        }
        System.out.print("Preço do serviço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine(); // limpar o buffer

        Servico servico = new Servico(descricao, preco);
        servicos.add(servico);

        System.out.println("Serviço criado: " + servico);
    }

    private static void listarServicos() {
        if (servicos.isEmpty()) {
            System.out.println("Nenhum serviço cadastrado.");
        } else {
            System.out.println("--- Lista de Serviços ---");
            for (Servico servico : servicos) {
                System.out.println(servico);
            }
        }
    }
    private static void editarServico() throws ExcecaoPersonalizada {
        listarServicos();
        System.out.print("Digite a descrição do serviço a ser editado: ");
        String descricao = scanner.nextLine();

        Servico servico = servicos.stream()
                .filter(s -> s.getDescricao().equalsIgnoreCase(descricao))
                .findFirst().orElse(null);

        if (servico == null) {
            throw new ExcecaoPersonalizada("Serviço não encontrado.");
        }

        System.out.print("Nova descrição: ");
        String novaDescricao = scanner.nextLine();
        System.out.print("Novo preço: ");
        double novoPreco = scanner.nextDouble();
        scanner.nextLine();

        servico.setDescricao(novaDescricao);
        servico.setPreco(novoPreco);

        System.out.println("Serviço atualizado: " + servico);
    }

    private static void removerServico() throws ExcecaoPersonalizada {
        listarServicos();
        System.out.print("Digite a descrição do serviço a ser removido: ");
        String descricao = scanner.nextLine();

        Servico servico = servicos.stream()
                .filter(s -> s.getDescricao().equalsIgnoreCase(descricao))
                .findFirst().orElse(null);

        if (servico == null) {
            throw new ExcecaoPersonalizada("Serviço não encontrado.");
        }

        // Verificar se o serviço está em algum agendamento
        boolean temAgendamentos = agendamentos.stream()
                .anyMatch(a -> a.getServico().equals(servico));

        if (temAgendamentos) {
            throw new ExcecaoPersonalizada("Não é possível remover serviço com agendamentos ativos.");
        }

        servicos.remove(servico);
        System.out.println("Serviço removido com sucesso.");
    }

    private static void gerenciarProdutos() {
        while (true) {
            System.out.println("\n--- Gerenciar Produtos ---");
            System.out.println("1. Criar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Editar Produto");
            System.out.println("4. Remover Produto");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = lerOpcao();
            scanner.nextLine();

            try {
                switch (opcao) {
                    case 1:
                        criarProduto();
                        break;
                    case 2:
                        listarProdutos();
                        break;
                    case 3:
                        editarProduto();
                        break;
                    case 4:
                        removerProduto();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (ExcecaoPersonalizada e) {
                System.err.println("Erro ao gerenciar produto: " + e.getMessage());
            }
        }
    }

    private static void criarProduto() throws ExcecaoPersonalizada {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Preço do produto: ");
        double preco = scanner.nextDouble();
        scanner.nextLine(); // limpar buffer

        if (nome.trim().isEmpty() || preco <= 0) {
            throw new ExcecaoPersonalizada("Nome ou preço inválido.");
        }

        Produto produto = new Produto(nome, preco);
        produtos.add(produto);
        System.out.println("Produto criado: " + produto);
    }

    private static void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            System.out.println("--- Lista de Produtos ---");
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
        }
    }

    private static void editarProduto() throws ExcecaoPersonalizada {
        listarProdutos();
        System.out.print("Digite o nome do produto a ser editado: ");
        String nome = scanner.nextLine();

        Produto produto = produtos.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst().orElse(null);

        if (produto == null) {
            throw new ExcecaoPersonalizada("Produto não encontrado.");
        }

        System.out.print("Novo nome do produto: ");
        String novoNome = scanner.nextLine();
        System.out.print("Novo preço do produto: ");
        double novoPreco = scanner.nextDouble();
        scanner.nextLine();

        produto.setNome(novoNome);
        produto.setPreco(novoPreco);
        System.out.println("Produto atualizado: " + produto);
    }

    private static void removerProduto() throws ExcecaoPersonalizada {
        listarProdutos();
        System.out.print("Digite o nome do produto a ser removido: ");
        String nome = scanner.nextLine();

        Produto produto = produtos.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nome))
                .findFirst().orElse(null);

        if (produto == null) {
            throw new ExcecaoPersonalizada("Produto não encontrado.");
        }

        produtos.remove(produto);
        System.out.println("Produto removido.");
    }

    private static void gerenciarAgendamentos() {
        while (true) {
            System.out.println("\n--- Gerenciar Agendamentos ---");
            System.out.println("1. Criar Agendamento");
            System.out.println("2. Listar Agendamentos");
            System.out.println("3. Editar Agendamento");
            System.out.println("4. Cancelar Agendamento");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");
            int opcao = lerOpcao();
            scanner.nextLine();

            try {
                switch (opcao) {
                    case 1:
                        criarAgendamento();
                        break;
                    case 2:
                        listarAgendamentos();
                        break;
                    case 3:
                        editarAgendamento();
                        break;
                    case 4:
                        cancelarAgendamento();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (ExcecaoPersonalizada e) {
                System.err.println("Erro ao gerenciar agendamento: " + e.getMessage());
            }
        }
    }

    private static void criarAgendamento() throws ExcecaoPersonalizada {
        if (clientes.isEmpty() || animais.isEmpty() || servicos.isEmpty()) {
            throw new ExcecaoPersonalizada("É necessário cadastrar clientes, animais e serviços antes de agendar.");
        }

        System.out.println("\n--- Criar Agendamento ---");
        System.out.print("Nome do cliente: ");
        String nomeCliente = scanner.nextLine();
        System.out.print("Nome do animal: ");
        String nomeAnimal = scanner.nextLine();
        System.out.print("Descrição do serviço: ");
        String descricaoServico = scanner.nextLine();
        System.out.print("Data e Hora do agendamento: ");
        String dataHora = scanner.nextLine();

        Cliente clienteSelecionado = clientes.stream().filter(c -> c.getNome().equalsIgnoreCase(nomeCliente)).findFirst().orElse(null);
        Animal animalSelecionado = animais.stream().filter(a -> a.getNome().equalsIgnoreCase(nomeAnimal)).findFirst().orElse(null);
        Servico servicoSelecionado = servicos.stream().filter(s -> s.getDescricao().equalsIgnoreCase(descricaoServico)).findFirst().orElse(null);

        if (clienteSelecionado == null || animalSelecionado == null || servicoSelecionado == null || dataHora.trim().isEmpty()) {
            throw new ExcecaoPersonalizada("Cliente, animal ou serviço não encontrado ou data/hora inválida.");
        }

        Agendamento agendamento = new Agendamento(clienteSelecionado, animalSelecionado, servicoSelecionado, dataHora);
        agendamentos.add(agendamento);
        System.out.println("Agendamento criado: " + agendamento);
    }

    private static void listarAgendamentos() {
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento cadastrado.");
        } else {
            System.out.println("--- Lista de Agendamentos ---");
            for (Agendamento agendamento : agendamentos) {
                System.out.println(agendamento);
            }
        }
    }
    private static void editarAgendamento() throws ExcecaoPersonalizada {
        listarAgendamentos();
        System.out.print("Digite o ID do agendamento a ser editado: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (id < 0 || id >= agendamentos.size()) {
            throw new ExcecaoPersonalizada("ID de agendamento inválido.");
        }

        Agendamento agendamento = agendamentos.get(id);

        System.out.println("Editando agendamento: " + agendamento);
        System.out.println("Deixe em branco para manter o valor atual.");

        System.out.print("Nova data/hora (" + agendamento.getDataHora() + "): ");
        String novaDataHora = scanner.nextLine();
        if (!novaDataHora.trim().isEmpty()) {
            agendamento.setDataHora(novaDataHora);
        }

        System.out.println("Agendamento atualizado: " + agendamento);
    }

    private static void cancelarAgendamento() throws ExcecaoPersonalizada {
        listarAgendamentos();
        System.out.print("Digite o ID do agendamento a ser cancelado: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (id < 0 || id >= agendamentos.size()) {
            throw new ExcecaoPersonalizada("ID de agendamento inválido.");
        }

        Agendamento agendamento = agendamentos.remove(id);
        System.out.println("Agendamento cancelado: " + agendamento);
    }
}