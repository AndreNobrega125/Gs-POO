// ============================================================
// CLASSE ABSTRATA - ComponenteEspacial
// Serve como "molde" para todos os componentes da estação.
// Não pode ser instanciada diretamente.
// ============================================================

public abstract class ComponenteEspacial {

    private int id;
    private String nome;
    private boolean status;
    private double temperatura;

    public ComponenteEspacial(int id, String nome, double temperatura) {
        this.id = id;
        this.nome = nome;
        this.temperatura = temperatura;
        this.status = false;
    }

    public void ligar() {
        this.status = true;
        System.out.println("[" + nome + "] Componente LIGADO.");
    }

    public void desligar() {
        this.status = false;
        System.out.println("[" + nome + "] Componente DESLIGADO.");
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public boolean isLigado() {
        return status;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        if (temperatura < -100 || temperatura > 200) {
            System.out.println("  [ERRO] Temperatura inválida.");
            return;
        }
        this.temperatura = temperatura;
    }

    protected void setStatus(boolean status) {
        this.status = status;
    }

    // ============================================================
    // MÉTODO ABSTRATO - obrigatório nas subclasses
    // Cada componente exibe seu status de um jeito diferente.
    // ============================================================
    public abstract void exibirStatus();
}
