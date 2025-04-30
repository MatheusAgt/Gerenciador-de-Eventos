import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GerenciadorDeEventos {

    private ArrayList<Evento> eventos;
    private ArrayList<Usuario> usuarios;

    public GerenciadorDeEventos() {
        eventos = new ArrayList<Evento>();
        usuarios = new ArrayList<Usuario>();
        carregarEventosDoArquivo();
        carregarUsuariosDoArquivo();
    }

    public void carregarEventosDoArquivo() {
        File arquivo = new File("events.data");
        System.out.println("Tentando carregar o arquivo: " + arquivo.getAbsolutePath());

        if (!arquivo.exists()) {
            System.out.println("Arquivo de eventos não encontrado.");
            return;
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(arquivo));
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(";");

                if (dados.length < 5) {
                    System.out.println("Linha mal formatada ignorada: " + linha);
                    continue;
                }

                String nome = dados[0];
                String endereco = dados[1];
                String categoria = dados[2];
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime horario = LocalDateTime.parse(dados[3], fmt);
                String descricao = dados[4];

                eventos.add(new Evento(nome, endereco, categoria, horario, descricao));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar eventos do arquivo: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar o arquivo: " + e.getMessage());
                }
            }
        }
    }

    public void salvarEventosEmArquivo() {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("events.data"));
            for (Evento evento : eventos) {
                writer.write(evento.toDataString());
                writer.newLine();
            }
            System.out.println("Eventos salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar eventos: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar o arquivo: " + e.getMessage());
                }
            }
        }
    }

    public void salvarUsuariosEmArquivo() {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("usuarios.data"));
            for (Usuario usuario : usuarios) {
                writer.write(usuario.toDataString());
                writer.newLine();
            }
            System.out.println("Usuários salvos com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar o arquivo: " + e.getMessage());
                }
            }
        }
    }

    public void carregarUsuariosDoArquivo() {
        File arquivo = new File("usuarios.data");
        System.out.println("Tentando carregar o arquivo de usuários: " + arquivo.getAbsolutePath());

        if (!arquivo.exists()) {
            System.out.println("Arquivo de usuários não encontrado.");
            return;
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(arquivo));
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(";");

                if (dados.length < 6) {
                    System.out.println("Linha mal formatada ignorada: " + linha);
                    continue;
                }

                String nome = dados[0];
                String email = dados[1];
                String telefone = dados[2];
                String endereco = dados[3];
                String dataNascimento = dados[4];
                String profissao = dados[5];

                usuarios.add(new Usuario(nome, email, telefone, endereco, dataNascimento, profissao));
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários do arquivo: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar o arquivo: " + e.getMessage());
                }
            }
        }
    }

    public void cadastrarEvento(String nome, String endereco, String categoria, LocalDateTime horario, String descricao) {
        if (!categoriaValida(categoria)) {
            System.out.println("Categoria inválida. Categorias permitidas: Festa, Esportivo, Show, Corporativo");
            return;
        }
        eventos.add(new Evento(nome, endereco, categoria, horario, descricao));
    }

    private boolean categoriaValida(String categoria) {
        return categoria.equalsIgnoreCase("festa") ||
               categoria.equalsIgnoreCase("esportivo") ||
               categoria.equalsIgnoreCase("show") ||
               categoria.equalsIgnoreCase("corporativo");
    }

    public void listarEventos() {
        if (eventos.isEmpty()) {
            System.out.println("Não há eventos cadastrados.");
            return;
        }

        ArrayList<Evento> copiaOrdenada = new ArrayList<Evento>(eventos);
        Collections.sort(copiaOrdenada, new Comparator<Evento>() {
            public int compare(Evento e1, Evento e2) {
                return e1.getHorario().compareTo(e2.getHorario());
            }
        });

        for (Evento evento : copiaOrdenada) {
            System.out.println(evento);
        }
    }

    public void cadastrarUsuario(String nome, String email, String telefone, String endereco, String dataNascimento, String profissao) {
        usuarios.add(new Usuario(nome, email, telefone, endereco, dataNascimento, profissao));
    }

    public Usuario buscarUsuario(String nome) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equalsIgnoreCase(nome)) {
                return usuario;
            }
        }
        return null;
    }

    public void confirmarParticipacao(Usuario usuario, Evento evento) {
        if (usuario != null && evento != null) {
            if (!evento.temParticipante(usuario)) {
                evento.adicionarParticipante(usuario);
                System.out.println(usuario.getNome() + " confirmou presença no evento: " + evento.getNome());
            } else {
                System.out.println(usuario.getNome() + " já está participando deste evento.");
            }
        }
    }

    public void cancelarParticipacao(Usuario usuario, Evento evento) {
        if (usuario != null && evento != null) {
            if (evento.temParticipante(usuario)) {
                evento.removerParticipante(usuario);
                System.out.println(usuario.getNome() + " cancelou a participação no evento: " + evento.getNome());
            } else {
                System.out.println(usuario.getNome() + " não está participando deste evento.");
            }
        }
    }

    public void exibirEventosProximos() {
        LocalDateTime agora = LocalDateTime.now();
        for (Evento evento : eventos) {
            if (evento.getHorario().isAfter(agora)) {
                System.out.println(evento);
            }
        }
    }

    public void exibirEventosPassados() {
        LocalDateTime agora = LocalDateTime.now();
        for (Evento evento : eventos) {
            if (evento.getHorario().isBefore(agora)) {
                System.out.println(evento);
            }
        }
    }

    public void exibirEventosOcorrendoAgora() {
        LocalDateTime agora = LocalDateTime.now();
        boolean encontrou = false;

        for (Evento evento : eventos) {
            LocalDateTime inicio = evento.getHorario();
            LocalDateTime fim = inicio.plusHours(3); // duração estimada de 3h

            if (!agora.isBefore(inicio) && !agora.isAfter(fim)) {
                System.out.println(evento);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Nenhum evento ocorrendo no momento.");
        }
    }

    public void exibirEventosDoUsuario(Usuario usuario) {
        boolean encontrou = false;
        for (Evento evento : eventos) {
            if (evento.temParticipante(usuario)) {
                System.out.println(evento);
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("O usuário não está participando de nenhum evento.");
        }
    }

    public Evento buscarEventoPorNome(String nome) {
        for (Evento evento : eventos) {
            if (evento.getNome().equalsIgnoreCase(nome)) {
                return evento;
            }
        }
        return null;
    }
}
