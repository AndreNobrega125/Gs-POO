// ============================================================
// SENSOR DE RADIAÇÃO
// Herda de ComponenteEspacial (classe abstrata)
// Implementa a interface Sensor
// ============================================================

public class SensorRadiacao extends ComponenteEspacial implements Sensor {

    // Limite máximo de radiação antes de disparar alerta (em mSv/h)
    private double limiteMaximo;

    // Último valor lido pelo sensor
    private double ultimaLeitura;

    // --- Construtor ---
    public SensorRadiacao(int id, String nome) {
        super(id, nome, 25.0); // temperatura inicial do sensor
        this.limiteMaximo = 2.0; // limite: 2.0 mSv/h (perigoso acima disso)
        this.ultimaLeitura = 0.0;
    }

    // ============================================================
    // Implementação da Interface Sensor
    // ============================================================

    // Simula uma leitura de radiação entre 0.1 e 5.0 mSv/h
    @Override
    public double lerValor() {
        ultimaLeitura = 0.1 + (Math.random() * 4.9); // 0.1 a 5.0 mSv/h
        ultimaLeitura = Math.round(ultimaLeitura * 100.0) / 100.0;
        return ultimaLeitura;
    }

    // Verifica se a radiação está dentro do nível seguro
    @Override
    public boolean verificarFuncionamento() {
        return ultimaLeitura <= limiteMaximo;
    }

    // Retorna o tipo do sensor
    @Override
    public String getTipo() {
        return "RADIAÇÃO";
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
        System.out.println("  Última leitura: " + ultimaLeitura + " mSv/h");
        System.out.println("  Limite máximo: " + limiteMaximo + " mSv/h");
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
