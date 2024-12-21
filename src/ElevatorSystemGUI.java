import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

class ElevatorSystemGUI {
    private Building building;
    private List<Elevator> elevators;
    private JPanel elevatorPanel;

    public ElevatorSystemGUI() {
        building = new Building(4, this); // Создаем здание с 4 лифтами
        elevators = building.elevators;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Система управления лифтами");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        elevatorPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawElevators(g);
            }
        };
        elevatorPanel.setPreferredSize(new Dimension(100, 200));
        frame.add(elevatorPanel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        JLabel nameLabel = new JLabel("Имя пассажира:");
        JTextField nameField = new JTextField();
        JLabel fromFloorLabel = new JLabel("Этаж отправления:");
        JTextField fromFloorField = new JTextField();
        JLabel toFloorLabel = new JLabel("Этаж назначения:");
        JTextField toFloorField = new JTextField();
        JButton submitButton = new JButton("Вызвать лифт");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int fromFloor = Integer.parseInt(fromFloorField.getText());
                int toFloor = Integer.parseInt(toFloorField.getText());
                Passenger passenger = new Passenger(name, fromFloor, toFloor);
                building.addPassenger(passenger);
                nameField.setText("");
                fromFloorField.setText("");
                toFloorField.setText("");
            }
        });

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(fromFloorLabel);
        inputPanel.add(fromFloorField);
        inputPanel.add(toFloorLabel);
        inputPanel.add(toFloorField);
        inputPanel.add(submitButton);

        frame.add(inputPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void updateElevatorPosition(Elevator elevator) {
        elevatorPanel.repaint();
        System.out.println("Лифт на этаже: " + elevator.getCurrentFloor());
    }

    private void drawElevators(Graphics g) {
        g.setColor(Color.BLUE);
        for (int i = 0; i < elevators.size(); i++) {
            int floor = elevators.get(i).getCurrentFloor();
            int y = 200 - (floor * 40); // Преобразование этажа в координаты
            g.fillRect(i * 40 + 10, y, 30, 30); // Рисуем лифт
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ElevatorSystemGUI());
    }
}