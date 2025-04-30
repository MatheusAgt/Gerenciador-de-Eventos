import java.util.ArrayList;

public class Usuario {
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private String dataNascimento;
    private String profissao;
    private ArrayList<Evento> eventosConfirmados;

    public Usuario(String nome, String email, String telefone, String endereco, String dataNascimento, String profissao) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
        this.profissao = profissao;
        this.eventosConfirmados = new ArrayList<Evento>();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getProfissao() {
        return profissao;
    }

    public void adicionarEvento(Evento evento) {
        if (!eventosConfirmados.contains(evento)) {
            eventosConfirmados.add(evento);
        }
    }

    public void removerEvento(Evento evento) {
        eventosConfirmados.remove(evento);
    }

    public ArrayList<Evento> getEventosConfirmados() {
        return eventosConfirmados;
    }

    public String toDataString() {
        return nome + ";" + email + ";" + telefone + ";" + endereco + ";" + dataNascimento + ";" + profissao;
    }

    @Override
    public String toString() {
        return "Nome: " + nome +
                "\nEmail: " + email +
                "\nTelefone: " + telefone +
                "\nEndereço: " + endereco +
                "\nData de Nascimento: " + dataNascimento +
                "\nProfissão: " + profissao;
    }
}