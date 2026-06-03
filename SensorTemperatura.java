// ============================================================
// SENSOR DE TEMPERATURA
// Herda de ComponenteEspacial (classe abstrata)
// Implementa a interface Sensor
// ============================================================

public class SensorTemperatura extends ComponenteEspacial implements Sensor {

    // Limite máximo de temperatura antes de disparar alerta
    private double limiteMaximo;

    // Último valor lido pelo sensor
    private double ultimaLeitura;

    // --- Construtor ---
    public SensorTemperatura(int id, String nome) {
        super(id, nome, 20.0); // temperatura inicial: 20°C
        this.limiteMaximo = 80.0; // limite: 80°C
        this.ultimaLeitura = 0.0;
    }

    // ============================================================
    // Implementação da Interface Sensor
    // ============================================================

    // Simula uma leitura de temperatura entre -10°C e 120°C
    @Override
    public double lerValor() {
        ultimaLeitura = -10 + (Math.random() * 130); // -10 a 120°C
        ultimaLeitura = Math.round(ultimaLeitura * 100.0) / 100.0; // 2 casas decimais
        return ultimaLeitura;
    }

    // Verifica se a temperatura está dentro do limite
    @Override
    public boolean verificarFuncionamento() {
        return ultimaLeitura <= limiteMaximo;
    }

    // Retorna o tipo do sensor
    @Override
    public String getTipo() {
        return "TEMPERATURA";
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
        System.out.println("  Última leitura: " + ultimaLeitura + " °C");
        System.out.println("  Limite máximo: " + limiteMaximo + " °C");
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
