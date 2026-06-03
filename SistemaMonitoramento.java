// ============================================================
// CLASSE PRINCIPAL - SistemaMonitoramento
// Ponto de entrada do programa (método main).
// Integra todos os componentes: sensores, propulsão e missão.
// Oferece um menu interativo para o usuário.
// ============================================================

import java.util.Scanner;

public class SistemaMonitoramento {

    // --- Objetos do sistema ---
    static SensorTemperatura sensorTemp = new SensorTemperatura(1, "Sensor-Temp-A1");
    static SensorPressao sensorPressao  = new SensorPressao(2, "Sensor-Press-B1");
    static SensorRadiacao sensorRad     = new SensorRadiacao(3, "Sensor-Rad-C1");

    static PropulsaoQuimica propQuimica  = new PropulsaoQuimica("Motor Químico Alpha", 500.0, "Hidrogênio Líquido");
    static PropulsaoEletrica propEletrica = new PropulsaoEletrica("Motor Elétrico Beta", 120.0, 92.0);

    static DadosMissao missao = new DadosMissao(
        "MISSÃO APOLLO-X",
        "LAT: -23.5 | LONG: 46.6 | ALT: 400km",
        "FIAP2026",
        "Terra → Lua → Marte",
        7
    );

    static Scanner scanner = new Scanner(System.in);
    static Thread monitorThread;

    // ============================================================
    // MÉTODO MAIN - ponto de entrada do programa
    // ============================================================
    public static void main(String[] args) {
        sensorTemp.ligar();
        sensorPressao.ligar();
        sensorRad.ligar();

        System.out.println("\n  Sistema de monitoramento inicializado.");
        System.out.println("  Monitor de alertas ATIVO - verificando sensores continuamente...\n");

        monitorThread = new Thread(new MonitorAlertas(sensorTemp, sensorPressao, sensorRad, missao));
        monitorThread.setDaemon(true);
        monitorThread.start();

        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();
            System.out.print("  Digite uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [ERRO] Digite apenas números.");
                continue;
            }

            switch (opcao) {
                case 1 -> menuSensores();
                case 2 -> menuPropulsao();
                case 3 -> menuMissao();
                case 4 -> simularAlertas();
                case 5 -> exibirStatusCompleto();
                case 0 -> {
                    System.out.println("\n  Encerrando missão...");
                    monitorThread.interrupt();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("  Até a próxima viagem espacial!");
                }
                default -> System.out.println("  [ERRO] Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    // ============================================================
    // MENU PRINCIPAL
    // ============================================================
    static void exibirMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║    PLATAFORMA ESPACIAL  v1.0         ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. Verificar Sensores               ║");
        System.out.println("║  2. Controlar Propulsão              ║");
        System.out.println("║  3. Dados da Missão                  ║");
        System.out.println("║  4. Simular Alertas                  ║");
        System.out.println("║  5. Status Completo do Sistema       ║");
        System.out.println("║  0. Encerrar Missão                  ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    // ============================================================
    // MENU 1 - SENSORES
    // ============================================================
    static void menuSensores() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n=== VERIFICAÇÃO DE SENSORES ===");
            System.out.println("  1. Ler Sensor de Temperatura");
            System.out.println("  2. Ler Sensor de Pressão");
            System.out.println("  3. Ler Sensor de Radiação");
            System.out.println("  4. Ler Todos os Sensores");
            System.out.println("  0. Voltar");
            System.out.print("  Opção: ");

            try {
                op = Integer.parseInt(scanner.nextLine().trim());
                switch (op) {
                    case 1 -> lerSensor(sensorTemp);
                    case 2 -> lerSensor(sensorPressao);
                    case 3 -> lerSensor(sensorRad);
                    case 4 -> {
                        lerSensor(sensorTemp);
                        lerSensor(sensorPressao);
                        lerSensor(sensorRad);
                    }
                    case 0 -> System.out.println("  Voltando ao menu principal...");
                    default -> System.out.println("  Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("  [ERRO] Digite apenas números.");
            }
        }
    }

    // Lê um sensor e exibe o resultado com nível de alerta
    static void lerSensor(ComponenteEspacial componente) {
        // O objeto é ao mesmo tempo ComponenteEspacial e Sensor
        Sensor sensor = (Sensor) componente;

        double valor = sensor.lerValor();
        boolean ok = sensor.verificarFuncionamento();

        System.out.println("\n  [Leitura: " + sensor.getTipo() + "]");
        System.out.println("  Valor: " + valor);

        if (ok) {
            System.out.println("  Status: NORMAL");
        } else {
            System.out.println("  *** ALERTA *** Valor acima do limite permitido!");
        }

        componente.exibirStatus(); // chama o método abstrato implementado
    }

    // ============================================================
    // MENU 2 - PROPULSÃO
    // ============================================================
    static void menuPropulsao() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n=== CONTROLE DE PROPULSÃO ===");
            System.out.println("  1. Propulsão Química");
            System.out.println("  2. Propulsão Elétrica");
            System.out.println("  0. Voltar");
            System.out.print("  Opção: ");

            try {
                op = Integer.parseInt(scanner.nextLine().trim());
                switch (op) {
                    case 1 -> submenuPropulsao(propQuimica);
                    case 2 -> submenuPropulsao(propEletrica);
                    case 0 -> System.out.println("  Voltando ao menu principal...");
                    default -> System.out.println("  Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("  [ERRO] Digite apenas números.");
            }
        }
    }

    static void submenuPropulsao(SistemaPropulsao prop) {
        int op = -1;

        // Loop para permitir várias ações antes de voltar ao menu principal
        while (op != 0) {
            System.out.println("\n  --- " + prop.getNome() + " ---");
            System.out.println("  1. Ligar");
            System.out.println("  2. Desligar");
            System.out.println("  3. Acelerar");
            System.out.println("  4. Ver Status");
            System.out.println("  0. Voltar");
            System.out.print("  Opção: ");

            try {
                op = Integer.parseInt(scanner.nextLine().trim());
                switch (op) {
                    case 1 -> prop.ligar();
                    case 2 -> prop.desligar();
                    case 3 -> {
                        System.out.print("  Potência (0-100): ");
                        try {
                            int potencia = Integer.parseInt(scanner.nextLine().trim());
                            if (potencia < 0 || potencia > 100) {
                                System.out.println("  [ERRO] Digite um valor entre 0 e 100.");
                            } else {
                                prop.acelerar(potencia);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("  [ERRO] Digite apenas números.");
                        }
                    }
                    case 4 -> prop.exibirStatus();
                    case 0 -> System.out.println("  Voltando ao menu principal...");
                    default -> System.out.println("  Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("  [ERRO] Digite apenas números.");
            }
        }
    }

    // ============================================================
    // MENU 3 - DADOS DA MISSÃO
    // ============================================================
    static void menuMissao() {
        int op = -1;

        while (op != 0) {
            System.out.println("\n=== DADOS DA MISSÃO ===");
            System.out.println("  1. Ver Dados Gerais");
            System.out.println("  2. Ver Coordenadas (requer senha)");
            System.out.println("  3. Atualizar Combustível");
            System.out.println("  4. Atualizar Trajetória");
            System.out.println("  0. Voltar");
            System.out.print("  Opção: ");

            try {
                op = Integer.parseInt(scanner.nextLine().trim());
                switch (op) {
                    case 1 -> missao.exibirDados();
                    case 2 -> {
                        System.out.print("  Digite a senha de acesso: ");
                        String senha = scanner.nextLine().trim();
                        String coords = missao.getCoordenadas(senha);
                        if (coords != null) {
                            System.out.println("  Coordenadas: " + coords);
                        }
                    }
                    case 3 -> {
                        System.out.print("  Novo nível de combustível (0-100): ");
                        try {
                            double nivel = Double.parseDouble(scanner.nextLine().trim());
                            missao.setNivelCombustivel(nivel);
                        } catch (NumberFormatException e) {
                            System.out.println("  [ERRO] Digite um número válido.");
                        }
                    }
                    case 4 -> {
                        System.out.print("  Nova trajetória: ");
                        String traj = scanner.nextLine().trim();
                        missao.setTrajetoria(traj);
                        System.out.println("  Trajetória atualizada.");
                    }
                    case 0 -> System.out.println("  Voltando ao menu principal...");
                    default -> System.out.println("  Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("  [ERRO] Valor inválido.");
            }
        }
    }

    // ============================================================
    // MENU 4 - SIMULAR ALERTAS
    // Faz várias leituras e avalia os níveis de alerta
    // ============================================================
    static void simularAlertas() {
        System.out.println("\n=== SIMULAÇÃO DE ALERTAS ===");
        System.out.println("  Realizando 5 leituras de cada sensor...\n");

        // Array de sensores para percorrer com loop
        Sensor[] sensores = { sensorTemp, sensorPressao, sensorRad };
        ComponenteEspacial[] componentes = { sensorTemp, sensorPressao, sensorRad };
        double[] limites = {
            sensorTemp.getLimiteMaximo(),
            sensorPressao.getLimiteMaximo(),
            sensorRad.getLimiteMaximo()
        };

        for (int i = 0; i < sensores.length; i++) {
            System.out.println("  >> Sensor de " + sensores[i].getTipo() + ":");

            for (int j = 0; j < 5; j++) {
                double valor = sensores[i].lerValor();
                double limite = limites[i];
                double percentual = (valor / limite) * 100;

                // Define o nível de alerta com base no percentual em relação ao limite
                String nivel;
                if (percentual <= 80) {
                    nivel = "NORMAL";
                } else if (percentual <= 100) {
                    nivel = "⚠ ATENÇÃO";
                } else if (percentual <= 130) {
                    nivel = "🔴 ALERTA";
                } else {
                    nivel = "💀 CRÍTICO";
                }

                System.out.printf("     Leitura %d: %.2f  →  %s%n", (j + 1), valor, nivel);
            }
            System.out.println();
        }
    }

    // ============================================================
    // MENU 5 - STATUS COMPLETO
    // ============================================================
    static void exibirStatusCompleto() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       STATUS COMPLETO DO SISTEMA     ║");
        System.out.println("╚══════════════════════════════════════╝");

        System.out.println("\n  >> SENSORES:");
        sensorTemp.exibirStatus();
        sensorPressao.exibirStatus();
        sensorRad.exibirStatus();

        System.out.println("\n  >> PROPULSÃO:");
        propQuimica.exibirStatus();
        propEletrica.exibirStatus();

        System.out.println("\n  >> MISSÃO:");
        missao.exibirDados();
    }

    static class MonitorAlertas implements Runnable {
        private SensorTemperatura sensorTemp;
        private SensorPressao sensorPressao;
        private SensorRadiacao sensorRad;
        private DadosMissao missao;

        public MonitorAlertas(SensorTemperatura temp, SensorPressao pressao,
                              SensorRadiacao rad, DadosMissao missao) {
            this.sensorTemp = temp;
            this.sensorPressao = pressao;
            this.sensorRad = rad;
            this.missao = missao;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    verificarSensores();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        private void verificarSensores() {
            if (!sensorTemp.isLigado() || !sensorPressao.isLigado() || !sensorRad.isLigado()) {
                return;
            }

            double tempValor = sensorTemp.lerValor();
            double pressaoValor = sensorPressao.lerValor();
            double radiacaoValor = sensorRad.lerValor();

            emitirAlerta("TEMPERATURA", tempValor, sensorTemp.getLimiteMaximo());
            emitirAlerta("PRESSÃO", pressaoValor, sensorPressao.getLimiteMaximo());
            emitirAlerta("RADIAÇÃO", radiacaoValor, sensorRad.getLimiteMaximo());

            verificarCombustivel();
        }

        private void emitirAlerta(String tipo, double valor, double limite) {
            double percentual = (valor / limite) * 100;

            if (percentual > 130) {
                System.out.println("\n🔴 ALERTA CRÍTICO [" + tipo + "]: " + String.format("%.2f", valor) +
                                 " (Limite: " + limite + ")");
            } else if (percentual > 100) {
                System.out.println("\n⚠ ALERTA [" + tipo + "]: " + String.format("%.2f", valor) +
                                 " (Limite: " + limite + ")");
            }
        }

        private void verificarCombustivel() {
            double combustivel = missao.getNivelCombustivel();
            if (combustivel < 20 && combustivel >= 10) {
                System.out.println("\n⚠ AVISO: Combustível baixo (" + String.format("%.1f", combustivel) + "%)");
            } else if (combustivel < 10) {
                System.out.println("\n🔴 CRÍTICO: Combustível crítico (" + String.format("%.1f", combustivel) + "%)");
            }
        }
    }
}
