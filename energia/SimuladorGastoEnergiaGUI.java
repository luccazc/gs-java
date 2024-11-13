package energia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimuladorGastoEnergiaGUI extends JFrame {
    private JTextField tarifaField;
    private JTextField nomeField;
    private JTextField potenciaField;
    private JTextField horasPorDiaField;
    private JTextArea resultadoArea;
    private Casa casa;

    public SimuladorGastoEnergiaGUI() {
        setTitle("Simulador de Gasto de Energia");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel tipoCasaLabel = new JLabel("Escolha o tipo de residência:");
        add(tipoCasaLabel);

        String[] tiposCasa = {"Residencial", "Comercial"};
        JComboBox<String> tipoCasaBox = new JComboBox<>(tiposCasa);
        add(tipoCasaBox);

        JLabel tarifaLabel = new JLabel("Tarifa por kWh:");
        add(tarifaLabel);

        tarifaField = new JTextField(10);
        add(tarifaField);

        JButton configurarCasaButton = new JButton("Configurar Casa");
        configurarCasaButton.addActionListener(e -> configurarCasa(tipoCasaBox));
        add(configurarCasaButton);

        JLabel nomeLabel = new JLabel("Nome do Aparelho:");
        add(nomeLabel);

        nomeField = new JTextField(10);
        add(nomeField);

        JLabel potenciaLabel = new JLabel("Potência (W):");
        add(potenciaLabel);

        potenciaField = new JTextField(10);
        add(potenciaField);

        JLabel horasPorDiaLabel = new JLabel("Horas de Uso Diárias:");
        add(horasPorDiaLabel);

        horasPorDiaField = new JTextField(10);
        add(horasPorDiaField);

        JButton adicionarAparelhoButton = new JButton("Adicionar Aparelho");
        adicionarAparelhoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarAparelho();
            }
        });
        add(adicionarAparelhoButton);

        JButton calcularButton = new JButton("Calcular Consumo e Custo");
        calcularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularConsumoCusto();
            }
        });
        add(calcularButton);

        resultadoArea = new JTextArea(10, 30);
        resultadoArea.setEditable(false);
        add(new JScrollPane(resultadoArea));
    }

    private void configurarCasa(JComboBox<String> tipoCasaBox) {
        try {
            double tarifaKWh = Double.parseDouble(tarifaField.getText());
            String tipo = (String) tipoCasaBox.getSelectedItem();

            if ("Residencial".equals(tipo)) {
                casa = new Residencial(tarifaKWh);
            } else {
                casa = new Comercial(tarifaKWh);
            }

            resultadoArea.setText("Casa configurada como " + tipo + " com tarifa de " + tarifaKWh + " R$/kWh.\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um valor válido para a tarifa.");
        }
    }

    private void adicionarAparelho() {
        try {
            String nome = nomeField.getText();
            double potencia = Double.parseDouble(potenciaField.getText());
            double horasPorDia = Double.parseDouble(horasPorDiaField.getText());

            Eletrodomestico eletrodomesticos = new Eletrodomestico(nome, potencia, horasPorDia);
            casa.adicionarEletrodomestico(eletrodomesticos);

            resultadoArea.append("Aparelho " + nome + " adicionado.\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite valores válidos para potência e horas por dia.");
        }
    }

    private void calcularConsumoCusto() {
        if (casa == null) {
            JOptionPane.showMessageDialog(this, "Configure a casa antes de calcular.");
            return;
        }

        double consumoTotal = casa.calcularConsumoTotal();
        double custoTotal = casa.calcularCustoTotal();

        resultadoArea.append("\nConsumo total mensal: " + String.format("%.2f", consumoTotal) + " kWh\n");
        resultadoArea.append("Custo total estimado: R$ " + String.format("%.2f", custoTotal) + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimuladorGastoEnergiaGUI frame = new SimuladorGastoEnergiaGUI();
            frame.setVisible(true);
        });
    }
}
