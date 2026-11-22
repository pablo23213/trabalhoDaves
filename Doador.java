package org.example.user;
import java.time.LocalDate;
import java.util.ArrayList;

    public abstract class Doador extends Pessoa {
        private LocalDate dataNascimento;
        private double peso;
        private TipoSanguineo tipoSanguineo;
        private FatorRh fatorRh;
        private ArrayList<Doacao> historicoDoacao = new ArrayList<>();

        public Doador(String nome, String cpf, String email, int telefone, LocalDate dataNascimento, double peso,
                      TipoSanguineo tipoSanguineo, FatorRh fatorRh){
            super(nome, cpf, telefone, email);
            this.dataNascimento = dataNascimento;
            this.peso = peso;
            this.tipoSanguineo = tipoSanguineo;
            this.fatorRh = fatorRh;
        }

        public abstract char getSexo();
        public abstract int getIntervaloDias();
        public abstract int getLimiteAnual();

        @Override
        public String getPapel() { return "Doador"; }

        public String getPerfilSanguineo() {
            return tipoSanguineo + "" + fatorRh.getSimbolo();
        }

        public boolean dadosValidos() {
            if (tipoSanguineo == null) return false;
            if (fatorRh == null) return false;
            if (dataNascimento == null) return false;
            if (peso <= 0) return false;
            return true;
        }

        public boolean podeDoarPara(TipoSanguineo tipoReceptor, FatorRh rhReceptor) {
            boolean tipoCompativel = tipoSanguineo.podeDoarPara(tipoReceptor);

            boolean rhCompativel = (fatorRh == FatorRh.NEGATIVO) || (rhReceptor == FatorRh.POSITIVO);

            return tipoCompativel && rhCompativel;
        }

        public void registrarDoacao(Doacao doacao) {
            historicoDoacao.add(doacao);
        }

        public ArrayList<Doacao> getHistorico() {
            return historicoDoacao;
        }

        public boolean temDoacaoAberta() {
            for (Doacao d : historicoDoacao) {
                if (d.estaAberta()) {
                    return true;
                }
            }
            return false;
        }

        public int contarConcluidasNoAno(int ano) {
            int total = 0;
            for (Doacao d : historicoDoacao) {
                if (d.estaConcluida() && d.getDataConclusao().getYear() == ano) {
                    total++;
                }
            }
            return total;
        }

        public LocalDate getDataUltimaDoacao() {
            LocalDate ultima = null;
            for (Doacao d : historicoDoacao) {
                if (d.estaConcluida()) {
                    LocalDate data = d.getDataConclusao();
                    if (ultima == null || data.isAfter(ultima)) {
                        ultima = data;
                    }
                }
            }
            return ultima;
        }

        public LocalDate getDataLiberacao() {
            LocalDate ultima = getDataUltimaDoacao();
            if (ultima == null) {
                return null;
            }
            return ultima.plusDays(getIntervaloDias());
        }

        @Override
        public String toString() {
            return getNome() + " | " + getSexo() + " | " + getPerfilSanguineo()
                    + " | " + peso + " kg | CPF " + getCpf();
        }

        public int getIdade(LocalDate referencia) {
            int idade = referencia.getYear() - dataNascimento.getYear();

            if (referencia.getMonthValue() < dataNascimento.getMonthValue()) {
                idade = idade - 1;
            }

            if (referencia.getMonthValue() == dataNascimento.getMonthValue()
                    && referencia.getDayOfMonth() < dataNascimento.getDayOfMonth()) {
                idade = idade - 1;
            }

            return idade;
        }

        public LocalDate getDataNascimento() {
            return dataNascimento;
        }

        public void setDataNascimento(LocalDate dataNascimento) {
            this.dataNascimento = dataNascimento;
        }

        public double getPeso() {
            return peso;
        }

        public void setPeso(double peso) {
            this.peso = peso;
        }

        public TipoSanguineo getTipoSanguineo() {
            return tipoSanguineo;
        }

        public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
            this.tipoSanguineo = tipoSanguineo;
        }

        public FatorRh getFatorRh() {
            return fatorRh;
        }

        public void setFatorRh(FatorRh fatorRh) {
            this.fatorRh = fatorRh;
        }

        public ArrayList<Doacao> getHistoricoDoacao() {
            return historicoDoacao;
        }

        public void setHistoricoDoacao(ArrayList<Doacao> historicoDoacao) {
            this.historicoDoacao = historicoDoacao;
        }

    }
