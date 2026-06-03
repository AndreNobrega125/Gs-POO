// ============================================================
// SENSOR DE PRESSÃO
// Herda de ComponenteEspacial (classe abstrata)
// Implementa a interface Sensor
// ============================================================

public class SensorPressao extends ComponenteEspacial implements Sensor {

    // Limite máximo de pressão antes de disparar alerta (em kPa)
    private double limiteMaximo;

    // Último valor lido pelo sensor
    private double ultimaLeitura;

    // --- Construtor ---
    public SensorPressao(int id, String nome) {
        super(id, nome, 22.0); // temperatura inicial do sensor
        this.limiteMaximo = 120.0; // limite: 120 kPa
        this.ultimaLeitura = 0.0;
    }

    // ============================================================
    // Implementação da Interface Sensor
    // ============================================================

    // Simula uma leitura de pressão entre 50 e 200 kPa
    @Override
    public double lerValor() {
        ultimaLeitura = 50 + (Math.random() * 150); // 50 a 200 kPa
        ultimaLeitura = Math.round(ultimaLeitura * 100.0) / 100.0;
        return ultimaLeitura;
    }

    // Verifica se a pressão está dentro do limite
    @Override
    public boolean verificarFuncionamento() {
        return ultimaLeitura <= limiteMaximo;
    }

    // Retorna o tipo do sensor
    @Override
    public String getTipo() {
        return "PRESSÃO";
    }

    // ============================================================
    // Implementação do método abstrato de ComponenteEspacial
    // ============================================================
    @Override
    public void exibirStatus() {
        System.out.println("------------------------------------");
        System.out.println("  Sensor: " + getNome() + " [ID: " + getId() + "]");
        System.out.println("  Tipo: " + getTipo());
        System.out.println("  Status: " + (isLigado() ? "LIGADO" : "DESLIGADO"));
        System.out.println("  Última leitura: " + ultimaLeitura + " kPa");
        System.out.println("  Limite máximo: " + limiteMaximo + " kPa");
        System.out.println("  Funcionando: " + (verificarFuncionamento() ? "SIM" : "NÃO - ALERTA!"));
        System.out.println("------------------------------------");
    }

    // --- Getter do limite ---
    public double getLimiteMaximo() {
        return limiteMaximo;
    }

    // --- Getter da última leitura ---
    public double getUltimaLeitura() {
        return ultimaLeitura;
    }
}
