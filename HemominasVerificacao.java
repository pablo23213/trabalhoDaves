package org.example.user;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HemominasVerificacao {
    private ArrayList<Doador> doadores = new ArrayList<>();
    private ArrayList<Funcionario> funcionarios = new ArrayList<>();
    private ArrayList<Doacao> doacoes = new ArrayList<>();
    private ArrayList<RequisitosDoacao> regras = new ArrayList<>();

    private EstoqueDeSangue estoque = new EstoqueDeSangue();
    private String ultimoErro;

    public HemominasVerificacao() {
        regras.add(new RequisitoIdade());
        regras.add(new RequisitoPeso() {});
    }

    public boolean cadastrarDoador(Doador d) {
        if (!d.dadosValidos()) {
            ultimoErro = "dados do doador inválidos (tipo, Rh, peso ou nascimento)";
            return false;
        }
        if (buscarDoadorPorCpf(d.getCpf()) != null) {
            ultimoErro = "CPF já cadastrado";
            return false;
        }
        doadores.add(d);
        return true;
    }

    public void cadastrarFuncionario(Funcionario f) {
        funcionarios.add(f);
    }

    public Doador buscarDoadorPorCpf(String cpf) {
        for (Doador d : doadores) {
            if (d.getCpf().equals(cpf)) return d;
        }
        return null;
    }

    public String verificarAptidao(Doador d, LocalDate data) {
        for (RequisitosDoacao regra : regras) {
            String motivo = regra.verificacao(d, data);
            if (motivo != null) return motivo;
        }
        return null;
    }

    public Doacao agendar(String cpf, LocalDate data) {
        Doador d = buscarDoadorPorCpf(cpf);
        if (d == null) {
            ultimoErro = "doador não cadastrado";
            return null;
        }
        String motivo = verificarAptidao(d, data);
        if (motivo != null) {
            ultimoErro = motivo;
            return null;
        }
        Doacao doacao = new Doacao(d, data);
        d.registrarDoacao(doacao);
        doacoes.add(doacao);
        ultimoErro = null;
        return doacao;
    }

    public boolean triar(Doacao doacao, ProfissionalDeSaude p, boolean apto, String motivo) {
        boolean ok = doacao.triar(p, apto, motivo);
        if (!ok) ultimoErro = "só é possível triar uma doação AGENDADA";
        return ok;
    }

    public boolean concluir(Doacao doacao, ProfissionalDeSaude resp, LocalDateTime quando, String idBolsa) {
        if (idBolsaExiste(idBolsa)) {
            ultimoErro = "identificador de bolsa " + idBolsa + " já utilizado";
            return false;
        }
        if (!doacao.concluir(resp, quando, idBolsa)) {
            ultimoErro = "só é possível concluir uma doação TRIADA (apta)";
            return false;
        }
        Doador d = doacao.getDoador();
        estoque.adicionar(d.getTipoSanguineo(), d.getFatorRh(), 1);
        return true;
    }


    private boolean idBolsaExiste(String id) {
        for (Doacao d : doacoes) {
            if (id.equals(d.getIdBolsa())) return true;
        }
        return false;
    }

    public void imprimirHistorico(Doador d) {
        ArrayList<Doacao> h = d.getHistorico();
        System.out.println("Histórico de " + d.getNome() + " (" + d.getPerfilSanguineo() + "):");
        if (h.isEmpty()) {
            System.out.println("   (sem doações registradas)");
        }
        for (int i = 0; i < h.size(); i++) {
            System.out.println("   " + (i + 1) + ". " + h.get(i));
        }
    }

    public ArrayList<Doador> buscar(char sexo, TipoSanguineo tipo, FatorRh rh,
                                    boolean somenteAptos, LocalDate data) {
        ArrayList<Doador> achados = new ArrayList<>();
        for (Doador d : doadores) {
            boolean ok = (sexo == '*' || d.getSexo() == sexo)
                    && (tipo == null || d.getTipoSanguineo() == tipo)
                    && (rh == null || d.getFatorRh() == rh)
                    && (!somenteAptos || verificarAptidao(d, data) == null);
            if (ok) achados.add(d);
        }
        return achados;
    }

    public ArrayList<Doador> buscarCompativeis(TipoSanguineo tipoReceptor, FatorRh rhReceptor,
                                               boolean somenteAptos, LocalDate data) {
        ArrayList<Doador> achados = new ArrayList<>();
        for (Doador d : doadores) {
            if (d.podeDoarPara(tipoReceptor, rhReceptor)
                    && (!somenteAptos || verificarAptidao(d, data) == null)) {
                achados.add(d);
            }
        }
        return achados;
    }

    public void imprimirDoadores(ArrayList<Doador> lista, LocalDate data) {
        if (lista.isEmpty()) {
            System.out.println("   (nenhum doador encontrado)");
        }
        for (Doador d : lista) {
            String motivo = verificarAptidao(d, data);
            String situacao = (motivo == null) ? "APTO" : "NÃO apto: " + motivo;
            System.out.println("   " + d + " -> " + situacao);
        }
    }

    public ArrayList<Doador> getDoadores() { return new ArrayList<>(doadores); }
    public EstoqueDeSangue getEstoque() { return estoque; }
    public String getUltimoErro() { return ultimoErro; }
}
