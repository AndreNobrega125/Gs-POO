// ============================================================
// PROPULSÃO QUÍMICA - herda de SistemaPropulsao
// Usa combustão química para gerar empuxo.
// Características: alto empuxo, consome combustível rapidamente.
// ============================================================

public class PropulsaoQuimica extends SistemaPropulsao {

    // --- Atributos específicos da Propulsão Química ---
    private String tipoCombustivel;
    private double temperaturaCombustao; // temperatura da câmara de combustão em °C

    // --- Construtor ---
    // Chama super() para inicializar os atributos da classe mãe
    public PropulsaoQuimica(String nome, double empuxoMaximo, String tipoCombustivel) {
        super(nome, empuxoMaximo); // chama o construtor de SistemaPropulsao
        this.tipoCombustivel = tipoCombustivel;
        this.temperaturaCombustao = 0.0;
    }

    // ============================================================
    // Implementação do método abstrato: acelerar()
    // @Override indica que está sobrescrevendo o método da classe mãe
    // ============================================================
    @Override
    public void acelerar(int potencia) {
        if (potencia < 0 || potencia > 100) {
            System.out.println("  [ERRO] Potência inválida! Insira um valor entre 0 e 100.");
            return;
        }

        if (!isLigado()) {
            System.out.println("  [ERRO] O sistema de propulsão está desligado! Ligue antes de acelerar.");
            return;
        }

        setPotenciaAtual(potencia);
        this.temperaturaCombustao = 1500 + (potencia * 35);

        System.out.println("  [" + getNome() + "] Combustão QUÍMICA ativada!");
        System.out.println("  Combustível: " + tipoCombustivel);
        System.out.println("  Potência definida: " + potencia + "%");
        System.out.printf("  Temperatura de combustão: %.0f °C%n", temperaturaCombustao);
        System.out.printf("  Empuxo gerado: %.2f kN%n", calcularEmpuxo());
    }

    // ============================================================
    // Sobrescrita de exibirStatus para incluir dados específicos
    // Usa super.exibirStatus() para aproveitar o código da classe mãe
    // ============================================================
    @Override
    public void exibirStatus() {
        super.exibirStatus(); // chama o método da classe mãe primeiro
        System.out.println("  Tipo: Propulsão QUÍMICA");
        System.out.println("  Combustível: " + tipoCombustivel);
        System.out.printf("  Temp. de combustão: %.0f °C%n", temperaturaCombustao);
        System.out.println("------------------------------------");
    }

    // --- Getters específicos ---
    public String getTipoCombustivel() {
        return tipoCombustivel;
    }

    public double getTemperaturaCombustao() {
        return temperaturaCombustao;
    }
}
