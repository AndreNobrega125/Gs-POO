// ============================================================
// CLASSE DE ENCAPSULAMENTO - DadosMissao
// Todos os atributos são PRIVATE.
// Dados sensíveis são protegidos por senha.
// Todos os setters possuem validação.
// ============================================================

public class DadosMissao {

    // --- Atributos PRIVADOS ---
    private String nomeMissao;
    private String coordenadas;         // dado sensível - protegido por senha
    private double nivelCombustivel;    // 0.0 a 100.0 (%)
    private String codigoAcesso;        // senha para acessar dados restritos
    private String trajetoria;
    private int numeroDeTripulantes;

    // --- Construtor ---
    public DadosMissao(String nomeMissao, String coordenadas, String codigoAcesso,
                       String trajetoria, int numeroDeTripulantes) {
        this.nomeMissao = nomeMissao;
        this.coordenadas = coordenadas;
        this.codigoAcesso = codigoAcesso;
        this.trajetoria = trajetoria;
        this.nivelCombustivel = 100.0; // começa com tanque cheio
        setNumeroDeTripulantes(numeroDeTripulantes);
    }

    // ============================================================
    // GETTERS - leitura dos dados
    // ============================================================

    public String getNomeMissao() {
        return nomeMissao;
    }

    public double getNivelCombustivel() {
        return nivelCombustivel;
    }

    public String getTrajetoria() {
        return trajetoria;
    }

    public int getNumeroDeTripulantes() {
        return numeroDeTripulantes;
    }

    // --- Dado restrito: só retorna coordenadas se a senha estiver correta ---
    public String getCoordenadas(String senhaDigitada) {
        if (senhaDigitada.equals(codigoAcesso)) {
            return coordenadas;
        } else {
            System.out.println("  [ACESSO NEGADO] Senha incorreta para visualizar coordenadas.");
            return null;
        }
    }

    // ============================================================
    // SETTERS - com validação
    // ============================================================

    public void setNomeMissao(String nomeMissao) {
        if (nomeMissao == null || nomeMissao.isBlank()) {
            System.out.println("  [ERRO] Nome da missão não pode ser vazio.");
            return;
        }
        this.nomeMissao = nomeMissao;
    }

    // Validação: combustível deve estar entre 0% e 100%
    public void setNivelCombustivel(double nivel) {
        if (nivel < 0) {
            System.out.println("  [ERRO] Nível de combustível não pode ser negativo.");
            return;
        }
        if (nivel > 100) {
            System.out.println("  [ERRO] Nível de combustível não pode ultrapassar 100%.");
            return;
        }
        this.nivelCombustivel = nivel;
        verificarAlertaCombustivel(); // verifica automaticamente após atualizar
    }

    public void setTrajetoria(String trajetoria) {
        if (trajetoria == null || trajetoria.isBlank()) {
            System.out.println("  [ERRO] Trajetória não pode ser vazia.");
            return;
        }
        this.trajetoria = trajetoria;
    }

    // Validação: não aceitar número negativo de tripulantes
    public void setNumeroDeTripulantes(int numero) {
        if (numero <= 0) {
            System.out.println("  [ERRO] Número de tripulantes deve ser maior que zero.");
            return;
        }
        this.numeroDeTripulantes = numero;
    }

    // Setter de coordenadas exige senha para alteração
    public void setCoordenadas(String novasCoordenadas, String senhaDigitada) {
        if (senhaDigitada.equals(codigoAcesso)) {
            this.coordenadas = novasCoordenadas;
            System.out.println("  [OK] Coordenadas atualizadas com sucesso.");
        } else {
            System.out.println("  [ACESSO NEGADO] Senha incorreta. Coordenadas não alteradas.");
        }
    }

    // ============================================================
    // ALERTA AUTOMÁTICO de combustível
    // Chamado sempre que o nível de combustível é atualizado.
    // ============================================================
    private void verificarAlertaCombustivel() {
        if (nivelCombustivel < 10) {
            System.out.println("  *** CRÍTICO *** Combustível em " + nivelCombustivel + "%! EMERGÊNCIA!");
        } else if (nivelCombustivel < 20) {
            System.out.println("  *** ALERTA *** Combustível baixo: " + nivelCombustivel + "%!");
        } else if (nivelCombustivel < 30) {
            System.out.println("  *** ATENÇÃO *** Combustível em " + nivelCombustivel + "%. Reabastecer em breve.");
        }
    }

    // --- Exibe todos os dados da missão (exceto dados restritos) ---
    public void exibirDados() {
        System.out.println("========================================");
        System.out.println("         DADOS DA MISSÃO                ");
        System.out.println("========================================");
        System.out.println("  Nome da Missão: " + nomeMissao);
        System.out.println("  Trajetória: " + trajetoria);
        System.out.println("  Tripulantes: " + numeroDeTripulantes);
        System.out.printf("  Combustível: %.1f%%%n", nivelCombustivel);
        System.out.println("  Coordenadas: [PROTEGIDAS - requer senha]");
        System.out.println("========================================");
    }
}
