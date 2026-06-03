// ============================================================
// PROPULSÃO ELÉTRICA - herda de SistemaPropulsao
// Usa energia elétrica (íons) para gerar empuxo.
// Características: menor empuxo, muito mais eficiente.
// ============================================================

public class PropulsaoEletrica extends SistemaPropulsao {

    // --- Atributos específicos da Propulsão Elétrica ---
    private double nivelBateria;   // nível da bateria em % (0 a 100)
    private double eficiencia;     // eficiência do motor em % (fixo por design)

    // --- Construtor ---
    // Chama super() para inicializar os atributos da classe mãe
    public PropulsaoEletrica(String nome, double empuxoMaximo, double eficiencia) {
        super(nome, empuxoMaximo); // chama o construtor de SistemaPropulsao
        this.nivelBateria = 100.0; // bateria começa cheia
        this.eficiencia = eficiencia;
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

        if (nivelBateria <= 0) {
            System.out.println("  [ERRO] Bateria esgotada! Impossível acelerar.");
            return;
        }

        setPotenciaAtual(potencia);
        double consumo = (potencia / 100.0) * (1.0 - eficiencia / 100.0) * 10;
        nivelBateria = Math.max(0, nivelBateria - consumo);

        System.out.println("  [" + getNome() + "] Propulsão ELÉTRICA ativada!");
        System.out.printf("  Eficiência do motor: %.1f%%%n", eficiencia);
        System.out.println("  Potência definida: " + potencia + "%");
        System.out.printf("  Empuxo gerado: %.2f kN%n", calcularEmpuxo());
        System.out.printf("  Bateria restante: %.1f%%%n", nivelBateria);

        if (nivelBateria < 20) {
            System.out.println("  *** ATENÇÃO *** Bateria baixa: " + String.format("%.1f", nivelBateria) + "%!");
        }
    }

    // ============================================================
    // Sobrescrita de exibirStatus para incluir dados específicos
    // Usa super.exibirStatus() para aproveitar o código da classe mãe
    // ============================================================
    @Override
    public void exibirStatus() {
        super.exibirStatus(); // chama o método da classe mãe primeiro
        System.out.println("  Tipo: Propulsão ELÉTRICA (Íons)");
        System.out.printf("  Nível da bateria: %.1f%%%n", nivelBateria);
        System.out.printf("  Eficiência: %.1f%%%n", eficiencia);
        System.out.println("------------------------------------");
    }

    // --- Getters específicos ---
    public double getNivelBateria() {
        return nivelBateria;
    }

    public double getEficiencia() {
        return eficiencia;
    }
}
