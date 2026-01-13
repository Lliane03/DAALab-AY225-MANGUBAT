import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class SortingGUI extends JFrame {

    private JTextArea textArea;
    private JLabel statusLabel;
    private JButton uploadButton, bubbleButton, insertionButton, mergeButton, refreshButton;
    private JRadioButton ascButton, descButton;
    private int[] data;

    private final Color SELECTED_COLOR = new Color(173, 216, 230);
    private final Color DEFAULT_COLOR = UIManager.getColor("Button.background");

    public SortingGUI() {
        setTitle("Sorting Algorithms");
        setSize(1090, 670);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ===== TOP PANEL =====
        uploadButton = new JButton("Upload Dataset (.txt)");
        refreshButton = new JButton("Refresh");

        JPanel uploadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        uploadPanel.add(uploadButton);
        uploadPanel.add(refreshButton);

        bubbleButton = new JButton("Bubble Sort");
        insertionButton = new JButton("Insertion Sort");
        mergeButton = new JButton("Merge Sort");

        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        sortPanel.add(bubbleButton);
        sortPanel.add(insertionButton);
        sortPanel.add(mergeButton);

        ascButton = new JRadioButton("Ascending");
        descButton = new JRadioButton("Descending", true);

        ButtonGroup orderGroup = new ButtonGroup();
        orderGroup.add(ascButton);
        orderGroup.add(descButton);

        JPanel orderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        orderPanel.add(new JLabel("Order:"));
        orderPanel.add(ascButton);
        orderPanel.add(descButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 5, 10));
        topPanel.add(uploadPanel, BorderLayout.NORTH);
        topPanel.add(sortPanel, BorderLayout.CENTER);
        topPanel.add(orderPanel, BorderLayout.SOUTH);

        // ===== TEXT AREA =====
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new TitledBorder("Result"));

        // ===== STATUS BAR =====
        statusLabel = new JLabel(" Ready");
        statusLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        // ===== ADD TO FRAME =====
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // ===== ACTIONS =====
        uploadButton.addActionListener(e -> loadFile());
        refreshButton.addActionListener(e -> refresh());

        bubbleButton.addActionListener(e -> sortData("Bubble"));
        insertionButton.addActionListener(e -> sortData("Insertion"));
        mergeButton.addActionListener(e -> sortData("Merge"));
    }

    // ===== FILE LOAD =====
    private void loadFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (Scanner scanner = new Scanner(chooser.getSelectedFile())) {
                ArrayList<Integer> list = new ArrayList<>();
                while (scanner.hasNextInt()) list.add(scanner.nextInt());
                data = list.stream().mapToInt(i -> i).toArray();

                textArea.setText("Dataset Loaded (" + data.length + " values)\n\n");
                displayResult(data);
                statusLabel.setText(" Dataset loaded successfully");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid file format!");
            }
        }
    }

    // ===== SORT HANDLER =====
    private void sortData(String type) {
        if (data == null) {
            JOptionPane.showMessageDialog(this, "Upload a dataset first!");
            return;
        }

        highlightButton(type);

        int[] temp = Arrays.copyOf(data, data.length);
        long start = System.nanoTime();

        boolean ascending = ascButton.isSelected();

        switch (type) {
            case "Bubble" -> bubbleSort(temp, ascending);
            case "Insertion" -> insertionSort(temp, ascending);
            case "Merge" -> mergeSort(temp, 0, temp.length - 1, ascending);
        }

        long end = System.nanoTime();
        double seconds = (end - start) / 1_000_000_000.0;

        textArea.setText(type + " Sort (" + (ascending ? "Ascending" : "Descending") + ")\n\n");
        displayResult(temp);

        statusLabel.setText(
                " Algorithm: " + type +
                " | Order: " + (ascending ? "Ascending" : "Descending") +
                " | Time: " + String.format("%.6f", seconds) + " seconds"
        );
    }

    // ===== DISPLAY =====
    private void displayResult(int[] arr) {
        int count = 0;
        for (int v : arr) {
            textArea.append(String.format("%5d ", v));
            if (++count == 50) {
                textArea.append("\n");
                count = 0;
            }
        }
        textArea.append("\n");
    }

    // ===== HIGHLIGHT BUTTON =====
    private void highlightButton(String type) {
        bubbleButton.setBackground(DEFAULT_COLOR);
        insertionButton.setBackground(DEFAULT_COLOR);
        mergeButton.setBackground(DEFAULT_COLOR);

        switch (type) {
            case "Bubble" -> bubbleButton.setBackground(SELECTED_COLOR);
            case "Insertion" -> insertionButton.setBackground(SELECTED_COLOR);
            case "Merge" -> mergeButton.setBackground(SELECTED_COLOR);
        }
    }

    // ===== REFRESH =====
    private void refresh() {
        data = null;
        textArea.setText("");
        statusLabel.setText(" Ready");

        bubbleButton.setBackground(DEFAULT_COLOR);
        insertionButton.setBackground(DEFAULT_COLOR);
        mergeButton.setBackground(DEFAULT_COLOR);

        descButton.setSelected(true);
    }

    // ===== SORTING ALGORITHMS =====
    private void bubbleSort(int[] a, boolean asc) {
        for (int i = 0; i < a.length - 1; i++)
            for (int j = 0; j < a.length - i - 1; j++)
                if ((asc && a[j] > a[j + 1]) || (!asc && a[j] < a[j + 1])) {
                    int t = a[j]; a[j] = a[j + 1]; a[j + 1] = t;
                }
    }

    private void insertionSort(int[] a, boolean asc) {
        for (int i = 1; i < a.length; i++) {
            int key = a[i], j = i - 1;
            while (j >= 0 && ((asc && a[j] > key) || (!asc && a[j] < key))) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    private void mergeSort(int[] a, int l, int r, boolean asc) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(a, l, m, asc);
            mergeSort(a, m + 1, r, asc);
            merge(a, l, m, r, asc);
        }
    }

    private void merge(int[] a, int l, int m, int r, boolean asc) {
        int[] L = Arrays.copyOfRange(a, l, m + 1);
        int[] R = Arrays.copyOfRange(a, m + 1, r + 1);
        int i = 0, j = 0, k = l;

        while (i < L.length && j < R.length) {
            if ((asc && L[i] <= R[j]) || (!asc && L[i] >= R[j])) {
                a[k++] = L[i++];
            } else {
                a[k++] = R[j++];
            }
        }
        while (i < L.length) a[k++] = L[i++];
        while (j < R.length) a[k++] = R[j++];
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SortingGUI().setVisible(true));
    }
}
