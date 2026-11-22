package org.example.user;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    static void coletar(HemominasVerificacao h, String cpf, LocalDate data,
                        ProfissionalDeSaude p, String bolsa) {
        Doacao d = h.agendar(cpf, data);
        h.triar(d, p, true, null);
        h.concluir(d, p, data.atTime(9, 30), bolsa);
    }

    static void tentarAgendar(HemominasVerificacao h, String cpf, LocalDate data) {
        Doacao d = h.agendar(cpf, data);
        Doador doador = h.buscarDoadorPorCpf(cpf);
        if (d == null) {
            System.out.println(" X " + doador.getNome() + ": agendamento recusado -> " + h.getUltimoErro());
        } else {
            System.out.println(" OK " + doador.getNome() + ": agendado para " + data);
        }
    }

    public static void main(String[] args) {
        HemominasVerificacao hemominas = new HemominasVerificacao();
        LocalDate hoje = LocalDate.of(2026, 11, 5);

        Atendente marta = new Atendente("Marta Souza", "900.000.000-01", 333300001,
                "marta@hemominas.mg.gov.br", "A-100", "Recepção");
        Medico paulo = new Medico("Dr. Paulo Lima", "900.000.000-02", 333300002,
                "paulo@hemominas.mg.gov.br", "M-200", "CRM-MG 12345");
        Medico carla = new Medico("Dra. Carla Dias", "900.000.000-03", 333300003,
                "carla@hemominas.mg.gov.br", "M-300", "CRM-MG 54321");
        hemominas.cadastrarFuncionario(marta);
        hemominas.cadastrarFuncionario(paulo);
        hemominas.cadastrarFuncionario(carla);

        hemominas.cadastrarDoador(new DoadorHomem("João Silva", "111", 999990001, "joao@email.com",
                LocalDate.of(1990, 3, 10), 82, TipoSanguineo.AB, FatorRh.POSITIVO));
        hemominas.cadastrarDoador(new DoadorHomem("Pedro Alves", "222", 999990002, "pedro@email.com",
                LocalDate.of(1985, 7, 22), 75, TipoSanguineo.O, FatorRh.NEGATIVO));
        hemominas.cadastrarDoador(new DoadorMulher("Ana Costa", "333", 999990003, "ana@email.com",
                LocalDate.of(1995, 1, 5), 60, TipoSanguineo.O, FatorRh.NEGATIVO));
        hemominas.cadastrarDoador(new DoadorMulher("Bia Rocha", "444", 999990004, "bia@email.com",
                LocalDate.of(1998, 9, 30), 48, TipoSanguineo.A, FatorRh.POSITIVO));
        hemominas.cadastrarDoador(new DoadorHomem("Lucas Melo", "555", 999990005, "lucas@email.com",
                LocalDate.of(2012, 4, 18), 55, TipoSanguineo.B, FatorRh.POSITIVO));
        hemominas.cadastrarDoador(new DoadorMulher("Carol Nunes", "666", 999990006, "carol@email.com",
                LocalDate.of(1992, 12, 1), 65, TipoSanguineo.AB, FatorRh.NEGATIVO));
        hemominas.cadastrarDoador(new DoadorHomem("Davi Reis", "777", 999990007, "davi@email.com",
                LocalDate.of(1988, 6, 14), 90, TipoSanguineo.B, FatorRh.NEGATIVO));

        EstoqueDeSangue estoque = hemominas.getEstoque();
        estoque.adicionar(TipoSanguineo.A, FatorRh.POSITIVO, 20);
        estoque.adicionar(TipoSanguineo.B, FatorRh.POSITIVO, 15);
        estoque.adicionar(TipoSanguineo.O, FatorRh.POSITIVO, 30);
        estoque.adicionar(TipoSanguineo.A, FatorRh.NEGATIVO, 6);
        estoque.adicionar(TipoSanguineo.O, FatorRh.NEGATIVO, 2);
        estoque.adicionar(TipoSanguineo.AB, FatorRh.POSITIVO, 1);
        estoque.adicionar(TipoSanguineo.B, FatorRh.NEGATIVO, 3);

        coletar(hemominas, "111", LocalDate.of(2026, 8, 20), carla, "BOLSA-001");
        coletar(hemominas, "222", LocalDate.of(2026, 8, 25), carla, "BOLSA-002");
        coletar(hemominas, "333", LocalDate.of(2026, 9, 10), paulo, "BOLSA-003");
        coletar(hemominas, "777", LocalDate.of(2026, 9, 1), paulo, "BOLSA-004");

        System.out.println("=== 1) Agendamentos e validação das regras (hoje: " + hoje + ") ===");
        tentarAgendar(hemominas, "444", hoje.plusDays(5));
        tentarAgendar(hemominas, "555", hoje.plusDays(5));
        tentarAgendar(hemominas, "333", hoje.plusDays(5));
        tentarAgendar(hemominas, "111", hoje.plusDays(5));

        System.out.println("\n=== 2) Etapas da doação ===");
        Doacao doacaoJoao = hemominas.buscarDoadorPorCpf("111").getHistorico().get(1);
        hemominas.triar(doacaoJoao, carla, true, null);
        hemominas.concluir(doacaoJoao, carla, LocalDateTime.of(2026, 11, 10, 8, 45), "BOLSA-005");
        System.out.println(" João: " + doacaoJoao.getSituacao().getDescricao());

        Doacao doacaoCarol = hemominas.agendar("666", hoje.plusDays(3));
        hemominas.triar(doacaoCarol, paulo, false, "febre na triagem");
        System.out.println(" Carol: " + doacaoCarol.getSituacao().getDescricao()
                + " -> " + doacaoCarol.getResultado());

        Doacao doacaoCarol2 = hemominas.agendar("666", hoje.plusDays(10));
        hemominas.triar(doacaoCarol2, paulo, true, null);
        hemominas.concluir(doacaoCarol2, paulo, LocalDateTime.of(2026, 11, 15, 10, 0), "BOLSA-006");
        System.out.println(" Carol (2ª tentativa): " + doacaoCarol2.getSituacao().getDescricao());

        Doacao doacaoDavi = hemominas.agendar("777", hoje.plusDays(15));
        doacaoDavi.cancelar();
        System.out.println(" Davi: " + doacaoDavi.getSituacao().getDescricao());

        boolean concluiu = hemominas.concluir(doacaoDavi, paulo,
                LocalDateTime.of(2026, 11, 20, 9, 0), "BOLSA-007");
        if (!concluiu) {
            System.out.println(" Davi: conclusão recusada -> " + hemominas.getUltimoErro());
        }

        System.out.println("\n=== 3) Histórico completo ===");
        hemominas.imprimirHistorico(hemominas.buscarDoadorPorCpf("111"));
        hemominas.imprimirHistorico(hemominas.buscarDoadorPorCpf("666"));

        System.out.println("\n=== 4) Busca por perfil (aptos na data " + hoje + ") ===");
        System.out.println(" Homens AB:");
        hemominas.imprimirDoadores(hemominas.buscar('M', TipoSanguineo.AB, null, false, hoje), hoje);
        System.out.println(" Mulheres O-:");
        hemominas.imprimirDoadores(
                hemominas.buscar('F', TipoSanguineo.O, FatorRh.NEGATIVO, false, hoje), hoje);
        System.out.println(" Compatíveis com receptor A+ (somente aptos):");
        hemominas.imprimirDoadores(
                hemominas.buscarCompativeis(TipoSanguineo.A, FatorRh.POSITIVO, true, hoje), hoje);

        System.out.println("\n=== 5) Captação ativa: convocação de emergência ===");
        CaptacaoDeEmergencia captacao = new CaptacaoDeEmergencia(5);
        ArrayList<Doador> convocados = captacao.executar(hemominas, hoje);
        System.out.println(" Total de doadores convocados: " + convocados.size());
    }
}
