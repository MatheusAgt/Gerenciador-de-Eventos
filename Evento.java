import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Evento {
    private String nome;
    private String endereco;
    private String categoria;
    private LocalDateTime horario;
    private String descricao;
    private ArrayList<Usuario> participantes;

    // Lista de categorias permitidas
    public static final List<String> CATEGORIAS_PERMITIDAS = Arrays.asList("Festa", "Esportivo", "Show", "Palestra", "Workshop");

    public Evento(String nome, String endereco, String categoria, LocalDateTime horario, String descricao) {
        if (!CATEGORIAS_PERMITIDAS.contains(categoria)) {
            throw new IllegalArgumentException("Categoria inválida. Categorias permitidas: " + CATEGORIAS_PERMITIDAS);
        }
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
        this.participantes = new ArrayList<Usuario>();
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public String getDescricao() {
        return descricao;
    }

    public ArrayList<Usuario> getParticipantes() {
        return participantes;
    }

    public String toDataString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return nome + ";" + endereco + ";" + categoria + ";" + horario.format(fmt) + ";" + descricao;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "[" + categoria + "] " + nome + " em " + endereco + " - " + horario.format(fmt) + "\n" + descricao;
    }

    public void adicionarParticipante(Usuario usuario) {
        if (usuario != null && !participantes.contains(usuario)) {
            participantes.add(usuario);
        }
    }

    public void removerParticipante(Usuario usuario) {
        if (usuario != null) {
            participantes.remove(usuario);
        }
    }

    public boolean temParticipante(Usuario usuario) {
        return participantes.contains(usuario);
    }

    public boolean estaOcorrendoAgora() {
        LocalDateTime agora = LocalDateTime.now();
        return !horario.isAfter(agora) && horario.plusHours(2).isAfter(agora); // Supondo duração de 2 horas
    }
}
