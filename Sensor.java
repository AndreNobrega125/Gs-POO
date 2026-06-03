// ============================================================
// INTERFACE - Sensor
// "Contrato" que todos os sensores DEVEM cumprir.
// Qualquer classe que implemente Sensor DEVE ter esses métodos.
// ============================================================

public interface Sensor {

    // Lê e retorna o valor atual do sensor (simulado)
    double lerValor();

    // Verifica se o sensor está funcionando corretamente
    boolean verificarFuncionamento();

    // Retorna o tipo do sensor (ex: "TEMPERATURA", "PRESSÃO")
    String getTipo();
}
