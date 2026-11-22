package org.example.user;
import java.time.LocalDate;
import java.util.ArrayList;

public class CaptacaoDeEmergencia {
    private int limiteCritico;

    public CaptacaoDeEmergencia(int limiteCritico) {
        this.limiteCritico = limiteCritico;
    }

    public ArrayList<PerfilEstadoCritico> listarPerfisCriticos(EstoqueDeSangue estoque) {
        ArrayList<PerfilEstadoCritico> criticos = new ArrayList<>();
        for (TipoSanguineo tipo : TipoSanguineo.values()) {
            for (FatorRh rh : FatorRh.values()) {
                int qtd = estoque.getUnidades(tipo, rh);
                if (qtd < limiteCritico) {
                    criticos.add(new PerfilEstadoCritico(tipo, rh, qtd));
                }
            }
        }

        ArrayList<PerfilEstadoCritico> ordenados = new ArrayList<>();
        while (!criticos.isEmpty()) {
            PerfilEstadoCritico menor = criticos.get(0);
            for (PerfilEstadoCritico p : criticos) {
                if (p.getUnidades() < menor.getUnidades()) {
                    menor = p;
                }
            }
            ordenados.add(menor);
            criticos.remove(menor);
        }
        return ordenados;
    }

    public ArrayList<Doador> executar(HemominasVerificacao sistema, LocalDate hoje) {
        ArrayList<Doador> convocados = new ArrayList<>();
        ArrayList<PerfilEstadoCritico> criticos = listarPerfisCriticos(sistema.getEstoque());

        if (criticos.isEmpty()) {
            System.out.println(" Nenhum perfil em nível crítico (limite: " + limiteCritico + " bolsas).");
            return convocados;
        }

        for (PerfilEstadoCritico perfil : criticos) {
            int faltam = limiteCritico - perfil.getUnidades();
            System.out.println(" * " + perfil.getNome() + ": " + perfil.getUnidades()
                    + " bolsas (faltam " + faltam + " para o limite)");

            ArrayList<Doador> aptos = sistema.buscarCompativeis(
                    perfil.getTipo(), perfil.getRh(), true, hoje);

            int convocadosNoPerfil = 0;

            for (int rodada = 0; rodada < 2; rodada++) {
                for (Doador d : aptos) {
                    boolean mesmoPerfil = d.getTipoSanguineo() == perfil.getTipo()
                            && d.getFatorRh() == perfil.getRh();
                    boolean daRodada = (rodada == 0) == mesmoPerfil;

                    if (daRodada && !convocados.contains(d) && convocadosNoPerfil < faltam) {
                        convocados.add(d);
                        convocadosNoPerfil++;
                        String motivo = mesmoPerfil ? "mesmo perfil" : "compatível";
                        System.out.println("     -> convocar " + d.getNome() + " (" + d.getPerfilSanguineo()
                                + ", " + motivo + ") | tel. " + d.getTelefone());
                    }
                }
            }

            if (convocadosNoPerfil == 0) {
                System.out.println("     (nenhum doador apto disponível)");
            }
        }
        return convocados;
    }
}
