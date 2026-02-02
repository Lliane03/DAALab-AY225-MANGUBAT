import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class SortingStressTest extends JFrame {

    private JTextArea resultArea;
    private JLabel statusLabel;
    private JButton loadButton, bubbleButton, insertionButton, mergeButton, resetButton;
    private JComboBox<String> columnComboBox, rowCountComboBox;
    private JTable previewTable;
    private DefaultTableModel tableModel;
    
    private List<Person> allData;
    private List<Person> workingData;
    private String currentColumn = "ID";
    private int rowCount = 1000;
    private double loadTime = 0;

    // Modern Color Scheme
    private final Color PRIMARY = new Color(99, 102, 241);
    private final Color PRIMARY_DARK = new Color(79, 70, 229);
    private final Color SECONDARY = new Color(236, 72, 153);
    private final Color ACCENT = new Color(59, 130, 246);
    private final Color SUCCESS = new Color(34, 197, 94);
    private final Color WARNING = new Color(234, 179, 8);
    private final Color BG_LIGHT = new Color(248, 250, 252);
    private final Color BG_DARK = new Color(241, 245, 249);
    private final Color TEXT_PRIMARY = new Color(30, 41, 59);
    private final Color TEXT_SECONDARY = new Color(100, 116, 139);
    private final Color SELECTED_GLOW = new Color(167, 139, 250);

    // Person class to hold CSV data
    static class Person {
        int id;
        String firstName;
        String lastName;

        Person(int id, String firstName, String lastName) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public String toString() {
            return String.format("%-10d %-20s %-20s", id, firstName, lastName);
        }
    }

    public SortingStressTest() {
        setTitle("Sorting Algorithm Stress Test - Prelim Exam");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));

        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Main panel with gradient
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, BG_LIGHT, 0, getHeight(), BG_DARK);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setOpaque(false);

        // ===== TOP CONTROL PANEL =====
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));
        controlPanel.setOpaque(false);

        // File loading section
        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filePanel.setOpaque(false);
        
        loadButton = createModernButton("Load CSV File", PRIMARY, Color.WHITE);
        resetButton = createModernButton("Reset", TEXT_SECONDARY, Color.WHITE);
        
        filePanel.add(loadButton);
        filePanel.add(resetButton);

        // Configuration section
        JPanel configPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        configPanel.setOpaque(false);

        JLabel columnLabel = new JLabel("Sort by Column:");
        columnLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        columnLabel.setForeground(TEXT_PRIMARY);

        columnComboBox = new JComboBox<>(new String[]{"ID", "FirstName", "LastName"});
        columnComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        columnComboBox.setPreferredSize(new Dimension(150, 35));
        columnComboBox.addActionListener(e -> currentColumn = (String) columnComboBox.getSelectedItem());

        JLabel rowLabel = new JLabel("Number of Rows:");
        rowLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        rowLabel.setForeground(TEXT_PRIMARY);

        rowCountComboBox = new JComboBox<>(new String[]{"1,000", "10,000", "50,000", "100,000"});
        rowCountComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rowCountComboBox.setPreferredSize(new Dimension(120, 35));
        rowCountComboBox.addActionListener(e -> {
            String selected = (String) rowCountComboBox.getSelectedItem();
            rowCount = Integer.parseInt(selected.replace(",", ""));
        });

        configPanel.add(columnLabel);
        configPanel.add(columnComboBox);
        configPanel.add(Box.createHorizontalStrut(20));
        configPanel.add(rowLabel);
        configPanel.add(rowCountComboBox);

        // Algorithm buttons
        JPanel algoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        algoPanel.setOpaque(false);

        bubbleButton = createModernButton("Bubble Sort", ACCENT, Color.WHITE);
        insertionButton = createModernButton("Insertion Sort", SUCCESS, Color.WHITE);
        mergeButton = createModernButton("Merge Sort", SECONDARY, Color.WHITE);

        bubbleButton.setEnabled(false);
        insertionButton.setEnabled(false);
        mergeButton.setEnabled(false);

        algoPanel.add(bubbleButton);
        algoPanel.add(insertionButton);
        algoPanel.add(mergeButton);

        controlPanel.add(filePanel, BorderLayout.NORTH);
        controlPanel.add(configPanel, BorderLayout.CENTER);
        controlPanel.add(algoPanel, BorderLayout.SOUTH);

        // ===== CENTER PANEL - Split view =====
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setOpaque(false);
        splitPane.setDividerLocation(600);
        splitPane.setBorder(null);

        // Left: Preview Table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(createModernTitledBorder("Data Preview (First 10 Records)"));

        String[] columns = {"ID", "First Name", "Last Name"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        previewTable = new JTable(tableModel);
        previewTable.setFont(new Font("Consolas", Font.PLAIN, 12));
        previewTable.setRowHeight(25);
        previewTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        previewTable.getTableHeader().setBackground(PRIMARY);
        previewTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane tableScroll = new JScrollPane(previewTable);
        tableScroll.setBorder(null);
        tablePanel.add(tableScroll, BorderLayout.CENTER);

        // Right: Results Area
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setOpaque(false);
        resultPanel.setBorder(createModernTitledBorder("Sorting Results & Performance"));

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        resultArea.setBackground(Color.WHITE);
        resultArea.setForeground(TEXT_PRIMARY);
        resultArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setBorder(null);
        resultScroll.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        resultScroll.getHorizontalScrollBar().setUI(new ModernScrollBarUI());

        resultPanel.add(resultScroll, BorderLayout.CENTER);

        splitPane.setLeftComponent(tablePanel);
        splitPane.setRightComponent(resultPanel);

        // ===== STATUS BAR =====
        statusLabel = new JLabel(" Ready - Load CSV file to begin") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY, getWidth(), 0, PRIMARY_DARK);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(new EmptyBorder(12, 15, 12, 15));
        statusLabel.setOpaque(false);

        // ===== ADD TO FRAME =====
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // ===== EVENT HANDLERS =====
        loadButton.addActionListener(e -> loadCSV());
        resetButton.addActionListener(e -> reset());
        
        bubbleButton.addActionListener(e -> performSort("Bubble"));
        insertionButton.addActionListener(e -> performSort("Insertion"));
        mergeButton.addActionListener(e -> performSort("Merge"));
    }

    // ===== MODERN BUTTON CREATOR =====
    private JButton createModernButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (!isEnabled()) {
                    g2d.setColor(new Color(200, 200, 200));
                } else if (getModel().isPressed()) {
                    g2d.setColor(bg.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(bg.brighter());
                } else {
                    g2d.setColor(bg);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                super.paintComponent(g);
            }
        };
        
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 40));
        
        return btn;
    }

    private Border createModernTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY, 2, true),
            title,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 13),
            PRIMARY
        );
        return BorderFactory.createCompoundBorder(border, new EmptyBorder(5, 5, 5, 5));
    }

    // ===== CSV LOADING =====
    private void loadCSV() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select generated_data.csv");
        
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            
            // Show loading dialog
            JDialog loadingDialog = createLoadingDialog();
            
            SwingWorker<Void, Integer> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    long start = System.nanoTime();
                    
                    allData = new ArrayList<>();
                    
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String line = br.readLine(); // Skip header
                        
                        while ((line = br.readLine()) != null) {
                            String[] parts = line.split(",");
                            if (parts.length == 3) {
                                int id = Integer.parseInt(parts[0].trim());
                                String firstName = parts[1].trim();
                                String lastName = parts[2].trim();
                                allData.add(new Person(id, firstName, lastName));
                            }
                        }
                    }
                    
                    long end = System.nanoTime();
                    loadTime = (end - start) / 1_000_000.0;
                    
                    return null;
                }
                
                @Override
                protected void done() {
                    loadingDialog.dispose();
                    
                    try {
                        get(); // Check for exceptions
                        
                        resultArea.setText(String.format(
                            "✓ CSV FILE LOADED SUCCESSFULLY\n\n" +
                            "Total Records: %,d\n" +
                            "Load Time: %.2f ms\n\n" +
                            "Ready to sort. Select column, row count, and algorithm.\n",
                            allData.size(), loadTime
                        ));
                        
                        updatePreviewTable(allData);
                        
                        bubbleButton.setEnabled(true);
                        insertionButton.setEnabled(true);
                        mergeButton.setEnabled(true);
                        
                        statusLabel.setText(String.format(" Data loaded: %,d records | Load time: %.2f ms", 
                            allData.size(), loadTime));
                        
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(SortingStressTest.this, 
                            "Error loading CSV: " + e.getMessage(), 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            
            worker.execute();
            loadingDialog.setVisible(true);
        }
    }

    private JDialog createLoadingDialog() {
        JDialog dialog = new JDialog(this, "Loading CSV", true);
        dialog.setLayout(new BorderLayout(20, 20));
        dialog.setSize(300, 120);
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        JLabel label = new JLabel("Loading CSV file...", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(label, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);
        
        dialog.add(panel);
        return dialog;
    }

    // ===== SORTING EXECUTION =====
    private void performSort(String algorithm) {
        if (allData == null || allData.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please load CSV data first!");
            return;
        }

        // Prepare subset of data
        int actualCount = Math.min(rowCount, allData.size());
        workingData = new ArrayList<>(allData.subList(0, actualCount));

        // Warning for large O(n²) operations
        if ((algorithm.equals("Bubble") || algorithm.equals("Insertion")) && actualCount >= 50000) {
            int response = JOptionPane.showConfirmDialog(this,
                String.format("⚠️ WARNING ⚠️\n\n" +
                    "You are about to sort %,d records using %s Sort (O(n²)).\n" +
                    "This may take several minutes or longer.\n\n" +
                    "Consider using Merge Sort for large datasets.\n\n" +
                    "Continue anyway?", actualCount, algorithm),
                "Performance Warning",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (response != JOptionPane.YES_OPTION) {
                return;
            }
        }

        highlightButton(algorithm);

        // Disable buttons during sort
        bubbleButton.setEnabled(false);
        insertionButton.setEnabled(false);
        mergeButton.setEnabled(false);

        // Show progress dialog
        JDialog progressDialog = createProgressDialog(algorithm, actualCount);

        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                long start = System.nanoTime();

                switch (algorithm) {
                    case "Bubble" -> bubbleSort(workingData, currentColumn);
                    case "Insertion" -> insertionSort(workingData, currentColumn);
                    case "Merge" -> mergeSort(workingData, 0, workingData.size() - 1, currentColumn);
                }

                long end = System.nanoTime();
                double sortTime = (end - start) / 1_000_000.0;

                publish(String.format(
                    "═══════════════════════════════════════════\n" +
                    "  %s SORT COMPLETED\n" +
                    "═══════════════════════════════════════════\n\n" +
                    "Configuration:\n" +
                    "  • Algorithm: %s Sort\n" +
                    "  • Sort Column: %s\n" +
                    "  • Records Sorted: %,d\n\n" +
                    "Performance:\n" +
                    "  • Load Time: %.2f ms\n" +
                    "  • Sort Time: %.2f ms\n" +
                    "  • Total Time: %.2f ms\n\n" +
                    "Complexity:\n" +
                    "  • Time: %s\n\n" +
                    "First 10 Sorted Records:\n" +
                    "─────────────────────────────────────────────\n",
                    algorithm.toUpperCase(), algorithm, currentColumn, actualCount,
                    loadTime, sortTime, loadTime + sortTime,
                    algorithm.equals("Merge") ? "O(n log n)" : "O(n²)"
                ));

                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String msg : chunks) {
                    resultArea.setText(msg);
                }
            }

            @Override
            protected void done() {
                progressDialog.dispose();

                try {
                    get();

                    // Display first 10 records in result area
                    StringBuilder sb = new StringBuilder(resultArea.getText());
                    for (int i = 0; i < Math.min(10, workingData.size()); i++) {
                        sb.append(String.format("%d. %s\n", i + 1, workingData.get(i)));
                    }
                    sb.append("─────────────────────────────────────────────\n");
                    resultArea.setText(sb.toString());

                    // Update preview table
                    updatePreviewTable(workingData);

                    // Update status
                    statusLabel.setText(String.format(" %s Sort completed | %,d records | Column: %s",
                        algorithm, workingData.size(), currentColumn));

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(SortingStressTest.this,
                        "Error during sorting: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    bubbleButton.setEnabled(true);
                    insertionButton.setEnabled(true);
                    mergeButton.setEnabled(true);
                }
            }
        };

        worker.execute();
        progressDialog.setVisible(true);
    }

    private JDialog createProgressDialog(String algorithm, int count) {
        JDialog dialog = new JDialog(this, "Sorting in Progress", true);
        dialog.setLayout(new BorderLayout(20, 20));
        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JLabel label = new JLabel(String.format(
            "<html><center>Sorting %,d records using %s Sort...<br>" +
            "Please wait...</center></html>", count, algorithm), 
            SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(label, BorderLayout.NORTH);
        panel.add(progressBar, BorderLayout.CENTER);

        dialog.add(panel);
        return dialog;
    }

    private void updatePreviewTable(List<Person> data) {
        tableModel.setRowCount(0);
        int limit = Math.min(10, data.size());
        for (int i = 0; i < limit; i++) {
            Person p = data.get(i);
            tableModel.addRow(new Object[]{p.id, p.firstName, p.lastName});
        }
    }

    private void highlightButton(String type) {
        bubbleButton.setBorder(null);
        insertionButton.setBorder(null);
        mergeButton.setBorder(null);

        JButton selected = switch (type) {
            case "Bubble" -> bubbleButton;
            case "Insertion" -> insertionButton;
            case "Merge" -> mergeButton;
            default -> null;
        };

        if (selected != null) {
            selected.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SELECTED_GLOW, 3, true),
                new EmptyBorder(2, 2, 2, 2)
            ));
        }
    }

    private void reset() {
        allData = null;
        workingData = null;
        tableModel.setRowCount(0);
        resultArea.setText("");
        statusLabel.setText(" Ready - Load CSV file to begin");
        
        bubbleButton.setEnabled(false);
        insertionButton.setEnabled(false);
        mergeButton.setEnabled(false);
        
        bubbleButton.setBorder(null);
        insertionButton.setBorder(null);
        mergeButton.setBorder(null);
        
        columnComboBox.setSelectedIndex(0);
        rowCountComboBox.setSelectedIndex(0);
    }

    // ═════════════════════════════════════════════════════════════
    //  SORTING ALGORITHMS - Comparison based on selected column
    // ═════════════════════════════════════════════════════════════

    private int compare(Person p1, Person p2, String column) {
        return switch (column) {
            case "ID" -> Integer.compare(p1.id, p2.id);
            case "FirstName" -> p1.firstName.compareToIgnoreCase(p2.firstName);
            case "LastName" -> p1.lastName.compareToIgnoreCase(p2.lastName);
            default -> 0;
        };
    }

    // ===== BUBBLE SORT =====
    private void bubbleSort(List<Person> list, String column) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (compare(list.get(j), list.get(j + 1), column) > 0) {
                    Collections.swap(list, j, j + 1);
                }
            }
        }
    }

    // ===== INSERTION SORT =====
    private void insertionSort(List<Person> list, String column) {
        int n = list.size();
        for (int i = 1; i < n; i++) {
            Person key = list.get(i);
            int j = i - 1;
            
            while (j >= 0 && compare(list.get(j), key, column) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    // ===== MERGE SORT =====
    private void mergeSort(List<Person> list, int left, int right, String column) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(list, left, mid, column);
            mergeSort(list, mid + 1, right, column);
            merge(list, left, mid, right, column);
        }
    }

    private void merge(List<Person> list, int left, int mid, int right, String column) {
        List<Person> leftList = new ArrayList<>(list.subList(left, mid + 1));
        List<Person> rightList = new ArrayList<>(list.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;

        while (i < leftList.size() && j < rightList.size()) {
            if (compare(leftList.get(i), rightList.get(j), column) <= 0) {
                list.set(k++, leftList.get(i++));
            } else {
                list.set(k++, rightList.get(j++));
            }
        }

        while (i < leftList.size()) {
            list.set(k++, leftList.get(i++));
        }

        while (j < rightList.size()) {
            list.set(k++, rightList.get(j++));
        }
    }

    // ===== MODERN SCROLLBAR =====
    static class ModernScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        private final Color THUMB_COLOR = new Color(156, 163, 175);
        private final Color TRACK_COLOR = new Color(243, 244, 246);

        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = THUMB_COLOR;
            this.trackColor = TRACK_COLOR;
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            return button;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(THUMB_COLOR);
            g2.fillRoundRect(thumbBounds.x + 2, thumbBounds.y + 2,
                           thumbBounds.width - 4, thumbBounds.height - 4, 10, 10);
            g2.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(TRACK_COLOR);
            g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SortingStressTest().setVisible(true));
    }
}