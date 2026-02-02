import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;

public class SortingGUI extends JFrame {

    private JTextArea textArea;
    private JLabel statusLabel;
    private JButton uploadButton, bubbleButton, insertionButton, mergeButton, refreshButton;
    private JRadioButton ascButton, descButton;
    private int[] data;

    // Modern Color Scheme
    private final Color PRIMARY = new Color(99, 102, 241);      // Indigo
    private final Color PRIMARY_DARK = new Color(79, 70, 229);  // Darker Indigo
    private final Color SECONDARY = new Color(236, 72, 153);    // Pink
    private final Color ACCENT = new Color(59, 130, 246);       // Blue
    private final Color SUCCESS = new Color(34, 197, 94);       // Green
    private final Color BG_LIGHT = new Color(248, 250, 252);    // Light Gray
    private final Color BG_DARK = new Color(241, 245, 249);     // Slightly Darker Gray
    private final Color TEXT_PRIMARY = new Color(30, 41, 59);   // Dark Slate
    private final Color TEXT_SECONDARY = new Color(100, 116, 139); // Slate Gray
    private final Color SELECTED_GLOW = new Color(167, 139, 250); // Light Purple
    
    public SortingGUI() {
        setTitle("Sorting Algorithms Visualizer");
        setSize(1090, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        
        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Main content panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, BG_LIGHT, 0, h, BG_DARK);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setOpaque(false);

        // ===== TOP PANEL =====
        uploadButton = createModernButton("Upload Dataset", PRIMARY, Color.WHITE);
        refreshButton = createModernButton("Refresh", TEXT_SECONDARY, Color.WHITE);

        JPanel uploadPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        uploadPanel.setOpaque(false);
        uploadPanel.add(uploadButton);
        uploadPanel.add(refreshButton);

        // Algorithm buttons with emoji icons
        bubbleButton = createModernButton("Bubble Sort", ACCENT, Color.WHITE);
        insertionButton = createModernButton("Insertion Sort", SUCCESS, Color.WHITE);
        mergeButton = createModernButton("Merge Sort", SECONDARY, Color.WHITE);

        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        sortPanel.setOpaque(false);
        sortPanel.add(bubbleButton);
        sortPanel.add(insertionButton);
        sortPanel.add(mergeButton);

        // Modern radio buttons
        ascButton = createModernRadio("Ascending");
        descButton = createModernRadio("Descending");
        descButton.setSelected(true);

        ButtonGroup orderGroup = new ButtonGroup();
        orderGroup.add(ascButton);
        orderGroup.add(descButton);

        JPanel orderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        orderPanel.setOpaque(false);
        JLabel orderLabel = new JLabel("Sort Order:");
        orderLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        orderLabel.setForeground(TEXT_PRIMARY);
        orderPanel.add(orderLabel);
        orderPanel.add(ascButton);
        orderPanel.add(descButton);

        JPanel topPanel = new JPanel(new BorderLayout(0, 15));
        topPanel.setOpaque(false);
        topPanel.add(uploadPanel, BorderLayout.NORTH);
        topPanel.add(sortPanel, BorderLayout.CENTER);
        topPanel.add(orderPanel, BorderLayout.SOUTH);

        // ===== TEXT AREA WITH MODERN STYLING =====
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(TEXT_PRIMARY);
        textArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        textArea.setLineWrap(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(createModernTitledBorder("Results"));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // Custom scrollbar styling
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());

        // ===== STATUS BAR WITH GRADIENT =====
        statusLabel = new JLabel(" Ready to sort") {
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
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        // ===== ACTIONS =====
        uploadButton.addActionListener(e -> loadFile());
        refreshButton.addActionListener(e -> refresh());

        bubbleButton.addActionListener(e -> sortData("Bubble"));
        insertionButton.addActionListener(e -> sortData("Insertion"));
        mergeButton.addActionListener(e -> sortData("Merge"));
    }

    // ===== MODERN BUTTON CREATOR =====
    private JButton createModernButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
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
        
        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.repaint();
            }
        });
        
        return btn;
    }

    // ===== MODERN RADIO BUTTON =====
    private JRadioButton createModernRadio(String text) {
        JRadioButton radio = new JRadioButton(text);
        radio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        radio.setForeground(TEXT_PRIMARY);
        radio.setOpaque(false);
        radio.setFocusPainted(false);
        radio.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return radio;
    }

    // ===== MODERN TITLED BORDER =====
    private Border createModernTitledBorder(String title) {
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(PRIMARY, 2, true),
            title,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            PRIMARY
        );
        return BorderFactory.createCompoundBorder(border, new EmptyBorder(5, 5, 5, 5));
    }

    // ===== FILE LOAD =====
    private void loadFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (Scanner scanner = new Scanner(chooser.getSelectedFile())) {
                ArrayList<Integer> list = new ArrayList<>();
                while (scanner.hasNextInt())
                    list.add(scanner.nextInt());
                data = list.stream().mapToInt(i -> i).toArray();

                textArea.setText("Dataset Loaded Successfully (" + data.length + " values)\n\n");
                displayResult(data);
                statusLabel.setText(" Dataset loaded: " + data.length + " integers");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid file format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===== SORT HANDLER =====
    private void sortData(String type) {
        if (data == null) {
            JOptionPane.showMessageDialog(this, "Please upload a dataset first!", "Warning", JOptionPane.WARNING_MESSAGE);
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
        double milliseconds = (end - start) / 1_000_000.0;

        textArea.setText(type + " Sort Complete - " + (ascending ? "Ascending" : "Descending") + "\n\n");
        displayResult(temp);

        statusLabel.setText(
                " Algorithm: " + type +
                        " | Order: " + (ascending ? "Ascending" : "Descending") +
                        " | Time: " + String.format("%.3f", milliseconds) + " ms");
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
        // Reset all
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

    // ===== REFRESH =====
    private void refresh() {
        data = null;
        textArea.setText("");
        statusLabel.setText(" Ready to sort");

        bubbleButton.setBorder(null);
        insertionButton.setBorder(null);
        mergeButton.setBorder(null);

        descButton.setSelected(true);
    }

    // ===== SORTING ALGORITHMS =====
    private void bubbleSort(int[] a, boolean asc) {
        for (int i = 0; i < a.length - 1; i++)
            for (int j = 0; j < a.length - i - 1; j++)
                if ((asc && a[j] > a[j + 1]) || (!asc && a[j] < a[j + 1])) {
                    int t = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = t;
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
        while (i < L.length)
            a[k++] = L[i++];
        while (j < R.length)
            a[k++] = R[j++];
    }

    // ===== MODERN SCROLLBAR UI =====
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
        SwingUtilities.invokeLater(() -> new SortingGUI().setVisible(true));
    }
}