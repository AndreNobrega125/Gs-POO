// ============================================================
// CLASSE ABSTRATA - SistemaPropulsao
// Define os comportamentos comuns de qualquer sistema de propulsão.
// Não pode ser instanciada diretamente.
// ============================================================

public abstract class SistemaPropulsao {

    private String nome;
    private boolean ligado;
    private int potenciaAtual;
    private double empuxoMaximo;

    public SistemaPropulsao(String nome, double empuxoMaximo) {
        this.nome = nome;
        this.empuxoMaximo = empuxoMaximo;
        this.ligado = false;
        this.potenciaAtual = 0;
    }

    public void ligar() {
        this.ligado = true;
        this.potenciaAtual = 0;
        System.out.println("[" + nome + "] Sistema de propulsão LIGADO.");
    }

    public void desligar() {
        this.ligado = false;
        this.potenciaAtual = 0;
        System.out.println("[" + nome + "] Sistema de propulsão DESLIGADO. Potência zerada.");
    }

    public double calcularEmpuxo() {
        return (potenciaAtual / 100.0) * empuxoMaximo;
    }

    protected void setPotenciaAtual(int potencia) {
        if (potencia >= 0 && potencia <= 100) {
            this.potenciaAtual = potencia;
        }
    }

    protected void setLigado(boolean ligado) {
        this.ligado = ligado;
    }

    // ============================================================
    // MÉTODO ABSTRATO - acelerar
    // Cada tipo de propulsão acelera de um jeito diferente.
    // As subclasses são OBRIGADAS a implementar este método.
    // ============================================================
    public abstract void acelerar(int potencia);

    public String getNome() {
        return nome;
    }

    public boolean isLigado() {
        return ligado;
    }

    public int getPotenciaAtual() {
        return potenciaAtual;
    }

    public double getEmpuxoMaximo() {
        return empuxoMaximo;
    }

    // --- Exibe status do sistema ---
    public void exibirStatus() {
        System.out.println("------------------------------------");
        System.out.println("  Propulsão: " + nome);
        System.out.println("  Status: " + (ligado ? "LIGADO" : "DESLIGADO"));
        System.out.println("  Potência atual: " + potenciaAtual + "%");
        System.out.printf("  Empuxo atual: %.2f kN%n", calcularEmpuxo());
        System.out.printf("  Empuxo máximo: %.2f kN%n", empuxoMaximo);
        System.out.println("------------------------------------");
    }
}
