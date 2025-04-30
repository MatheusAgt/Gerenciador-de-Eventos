import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorDeEventos gerenciador = new GerenciadorDeEventos();

        int opcao;
        do {
            System.out.println("\n==== MENU ====");
            System.out.println("1 - Cadastrar evento");
            System.out.println("2 - Listar eventos");
            System.out.println("3 - Salvar eventos");
            System.out.println("4 - Cadastrar usuário");
            System.out.println("5 - Salvar usuários");
            System.out.println("6 - Confirmar participação em evento");
            System.out.println("7 - Cancelar participação em evento");
            System.out.println("8 - Listar eventos que o usuário está participando");
            System.out.println("9 - Ver eventos futuros");
            System.out.println("10 - Ver eventos em andamento");
            System.out.println("11 - Ver eventos passados");
            System.out.println("0 - Sair");

            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    // Cadastrar evento
                    System.out.println("Digite o nome do evento:");
                    String nomeEvento = scanner.nextLine();

                    System.out.println("Digite o endereço do evento:");
                    String enderecoEvento = scanner.nextLine();

                    System.out.println("Digite a categoria do evento (Festa, Esporte, Show):");
                    String categoriaEvento = scanner.nextLine();

                    System.out.println("Digite a data e hora do evento (dd/MM/yyyy HH:mm):");
                    String dataEvento = scanner.nextLine();

                    System.out.println("Digite a descrição do evento:");
                    String descricaoEvento = scanner.nextLine();

                    try {
                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                        LocalDateTime horarioEvento = LocalDateTime.parse(dataEvento, fmt);
                        gerenciador.cadastrarEvento(nomeEvento, enderecoEvento, categoriaEvento, horarioEvento, descricaoEvento);
                        System.out.println("Evento cadastrado com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Formato de data e hora inválido. Tente novamente.");
                    }
                    break;

                case 2:
                    // Listar eventos
                    gerenciador.listarEventos();
                    break;

                case 3:
                    // Salvar eventos
                    gerenciador.salvarEventosEmArquivo();
                    break;

                case 4:
                    // Cadastrar usuário
                    System.out.println("Digite o nome do usuário:");
                    String nomeUsuario = scanner.nextLine();

                    System.out.println("Digite o email do usuário:");
                    String emailUsuario = scanner.nextLine();

                    System.out.println("Digite o telefone do usuário:");
                    String telefoneUsuario = scanner.nextLine();

                    System.out.println("Digite o endereço do usuário:");
                    String enderecoUsuario = scanner.nextLine();

                    System.out.println("Digite a data de nascimento (dd/MM/yyyy):");
                    String dataNascimento = scanner.nextLine();

                    System.out.println("Digite a profissão:");
                    String profissao = scanner.nextLine();

                    gerenciador.cadastrarUsuario(nomeUsuario, emailUsuario, telefoneUsuario, enderecoUsuario, dataNascimento, profissao);
                    System.out.println("Usuário cadastrado com sucesso!");
                    break;

                case 5:
                    // Salvar usuários
                    gerenciador.salvarUsuariosEmArquivo();
                    break;

                case 6:
                    // Confirmar participação
                    System.out.println("Digite o nome do usuário:");
                    String nomeConfirma = scanner.nextLine();

                    System.out.println("Digite o nome do evento:");
                    String nomeEventoConfirma = scanner.nextLine();

                    Usuario usuarioConfirma = gerenciador.buscarUsuario(nomeConfirma);
                    Evento eventoConfirma = gerenciador.buscarEventoPorNome(nomeEventoConfirma);

                    if (usuarioConfirma != null && eventoConfirma != null) {
                        gerenciador.confirmarParticipacao(usuarioConfirma, eventoConfirma);
                    } else {
                        System.out.println("Usuário ou evento não encontrado.");
                    }
                    break;

                case 7:
                    // Cancelar participação
                    System.out.println("Digite o nome do usuário:");
                    String nomeCancela = scanner.nextLine();

                    System.out.println("Digite o nome do evento:");
                    String nomeEventoCancela = scanner.nextLine();

                    Usuario usuarioCancela = gerenciador.buscarUsuario(nomeCancela);
                    Evento eventoCancela = gerenciador.buscarEventoPorNome(nomeEventoCancela);

                    if (usuarioCancela != null && eventoCancela != null) {
                        gerenciador.cancelarParticipacao(usuarioCancela, eventoCancela);
                    } else {
                        System.out.println("Usuário ou evento não encontrado.");
                    }
                    break;

                case 8:
                    // Listar eventos do usuário
                    System.out.println("Digite o nome do usuário:");
                    String nomeLista = scanner.nextLine();
                    Usuario usuarioLista = gerenciador.buscarUsuario(nomeLista);
                    if (usuarioLista != null) {
                        gerenciador.exibirEventosDoUsuario(usuarioLista);
                    } else {
                        System.out.println("Usuário não encontrado.");
                    }
                    break;

                case 9:
                    // Ver eventos futuros
                    gerenciador.exibirEventosProximos();
                    break;

                case 10:
                    // Ver eventos em andamento
                    gerenciador.exibirEventosOcorrendoAgora();
                    break;

                case 11:
                    // Ver eventos passados
                    gerenciador.exibirEventosPassados();
                    break;

                case 0:
                    System.out.println("Encerrando o programa...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}