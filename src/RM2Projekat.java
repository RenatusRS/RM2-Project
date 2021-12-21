package remme;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RM2Projekat extends Frame implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final String[] hosts = { "192.168.10.1", "192.168.20.1", "192.168.30.1" };

	public int freq = 10;
	final int startLimit = 40;
	
	private ArrayList<Monitor> monitors = new ArrayList<>();
	Thread thread = new Thread(this);

	JTabbedPane routerPane = new JTabbedPane();

	JCheckBox cpu5SecCheckbox = new JCheckBox("5 sekundi", true);
	JCheckBox cpu1MinCheckbox = new JCheckBox("1 minut");
	JCheckBox cpu5MinCheckbox = new JCheckBox("5 minuta");
	JCheckBox memUsedPCheckbox = new JCheckBox("Iskorišćena memorija", true);
	JCheckBox memFreePCheckbox = new JCheckBox("Dostupna memorija");
	JCheckBox memUsedIOCheckbox = new JCheckBox("Iskorišćena memorija", true);
	JCheckBox memFreeIOCheckbox = new JCheckBox("Dostupna memorija");

	Label cpu5SecLabel = new Label("0%");
	Label cpu1MinLabel = new Label("0%");
	Label cpu5MinLabel = new Label("0%");
	Label memUsedPLabel = new Label("0.00%");
	Label memFreePLabel = new Label("0.00%");
	Label memUsedIOLabel = new Label("0.00%");
	Label memFreeIOLabel = new Label("0.00%");

	Label memPLabel = new Label("0 / 0");
	Label memIOLabel = new Label("0 / 0");

	private RM2Projekat() {	
		setLocation(250, 250);
		setTitle("RM2 Projekat - Varijanta 8");
		setMinimumSize(new Dimension(1060, 400));

		for (int i = 0; i < hosts.length; i++) {
			monitors.add(new Monitor(this, hosts[i]));
			routerPane.add("Router " + hosts[i], monitors.get(i));
		}

		routerPane.setBackground(Chart.SwingWhite);

		Panel selectionPanel = new Panel(new GridLayout(1, 3, 0, 0));

		JTabbedPane cpuPane = new JTabbedPane();

		Panel cpuPanel = new Panel(new BorderLayout());
		Panel cpuSelectionPanel = new Panel(new GridLayout(3, 1, 0, 0));

		cpuPane.add(cpuPanel, "CPU");
		cpuPanel.add(cpuSelectionPanel, BorderLayout.CENTER);

		cpu5SecCheckbox.setForeground(Chart.LightBlue);
		cpu1MinCheckbox.setForeground(Chart.Blue);
		cpu5MinCheckbox.setForeground(Chart.DarkBlue);
		memUsedPCheckbox.setForeground(Chart.Red);
		memFreePCheckbox.setForeground(Chart.Orange);
		memUsedIOCheckbox.setForeground(Chart.DarkGreen);
		memFreeIOCheckbox.setForeground(Chart.Green);

		Panel cpu5SecPanel = new Panel(new BorderLayout());
		Panel cpu1MinPanel = new Panel(new BorderLayout());
		Panel cpu5MinPanel = new Panel(new BorderLayout());
		Panel memUsedPPanel = new Panel(new BorderLayout());
		Panel memFreePPanel = new Panel(new BorderLayout());
		Panel memUsedIOPanel = new Panel(new BorderLayout());
		Panel memFreeIOPanel = new Panel(new BorderLayout());

		cpu5SecLabel.setBackground(Chart.SwingWhite);
		cpu1MinLabel.setBackground(Chart.SwingWhite);
		cpu5MinLabel.setBackground(Chart.SwingWhite);
		memUsedPLabel.setBackground(Chart.SwingWhite);
		memFreePLabel.setBackground(Chart.SwingWhite);
		memUsedIOLabel.setBackground(Chart.SwingWhite);
		memFreeIOLabel.setBackground(Chart.SwingWhite);

		cpu5SecPanel.add(cpu5SecCheckbox, BorderLayout.CENTER);
		cpu5SecPanel.add(cpu5SecLabel, BorderLayout.EAST);

		cpu1MinPanel.add(cpu1MinCheckbox, BorderLayout.CENTER);
		cpu1MinPanel.add(cpu1MinLabel, BorderLayout.EAST);

		cpu5MinPanel.add(cpu5MinCheckbox, BorderLayout.CENTER);
		cpu5MinPanel.add(cpu5MinLabel, BorderLayout.EAST);

		memUsedPPanel.add(memUsedPCheckbox, BorderLayout.CENTER);
		memUsedPPanel.add(memUsedPLabel, BorderLayout.EAST);

		memFreePPanel.add(memFreePCheckbox, BorderLayout.CENTER);
		memFreePPanel.add(memFreePLabel, BorderLayout.EAST);

		memUsedIOPanel.add(memUsedIOCheckbox, BorderLayout.CENTER);
		memUsedIOPanel.add(memUsedIOLabel, BorderLayout.EAST);

		memFreeIOPanel.add(memFreeIOCheckbox, BorderLayout.CENTER);
		memFreeIOPanel.add(memFreeIOLabel, BorderLayout.EAST);

		cpuSelectionPanel.add(cpu5SecPanel);
		cpuSelectionPanel.add(cpu1MinPanel);
		cpuSelectionPanel.add(cpu5MinPanel);

		JTabbedPane memPane = new JTabbedPane();

		Panel memProcessPanel = new Panel(new GridLayout(3, 1, 0, 0));
		Panel memIOPanel = new Panel(new GridLayout(3, 1, 0, 0));

		memProcessPanel.add(memUsedPPanel);
		memProcessPanel.add(memFreePPanel);
		memProcessPanel.add(memPLabel);
		memIOPanel.add(memUsedIOPanel);
		memIOPanel.add(memFreeIOPanel);
		memIOPanel.add(memIOLabel);

		memPane.add("Processor MEM", memProcessPanel);
		memPane.add("I/O MEM", memIOPanel);

		Panel settingsPanel = new Panel(new GridLayout(3, 1, 0, 0));
		Panel addNewPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
		Panel changeFreqPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
		Panel errorPanel = new Panel(new FlowLayout(FlowLayout.CENTER));

		Label errorLabel = new Label();
		errorLabel.setForeground(Color.red);

		errorPanel.add(errorLabel);

		JTextField hostText = new JTextField();
		Button addButton = new Button("Dodaj mrežu");
		Button clearButton = new Button("Očisti graf");

		hostText.setHorizontalAlignment(JTextField.CENTER);
		hostText.setForeground(Color.black);

		hostText.setColumns(13);

		addNewPanel.add(hostText);
		addNewPanel.add(addButton);
		addNewPanel.add(clearButton);

		JTextField freqText = new JTextField(Integer.toString(freq));
		Button setFreqButton = new Button("Promeni frekvenciju");

		freqText.setForeground(Color.black);
		freqText.setHorizontalAlignment(JTextField.CENTER);

		freqText.setColumns(2);

		JTextField graphSizeText = new JTextField(Integer.toString(startLimit));
		Button setSizeButton = new Button("Promeni širinu grafa");

		graphSizeText.setForeground(Color.black);
		graphSizeText.setHorizontalAlignment(JTextField.CENTER);

		graphSizeText.setColumns(2);

		changeFreqPanel.add(freqText);
		changeFreqPanel.add(setFreqButton);
		changeFreqPanel.add(graphSizeText);
		changeFreqPanel.add(setSizeButton);

		settingsPanel.add(errorPanel);
		settingsPanel.add(addNewPanel);
		settingsPanel.add(changeFreqPanel);

		selectionPanel.add(cpuPane);
		selectionPanel.add(memPane);
		selectionPanel.add(settingsPanel);

		add(routerPane, BorderLayout.CENTER);
		add(selectionPanel, BorderLayout.SOUTH);

		ItemListener checkboxIL = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				repaintAll();
			}
		};

		cpu5SecCheckbox.addItemListener(checkboxIL);
		cpu1MinCheckbox.addItemListener(checkboxIL);
		cpu5MinCheckbox.addItemListener(checkboxIL);
		memUsedPCheckbox.addItemListener(checkboxIL);
		memFreePCheckbox.addItemListener(checkboxIL);
		memUsedIOCheckbox.addItemListener(checkboxIL);
		memFreeIOCheckbox.addItemListener(checkboxIL);

		routerPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				monitors.get(routerPane.getSelectedIndex()).chart.repaint();
				monitors.get(routerPane.getSelectedIndex()).setText();
			}
		});

		addButton.addActionListener((ae) -> {
			if (hostText.getText().isEmpty())
				errorLabel.setText("Niste uneli mrežu!");
			else {
				monitors.add(new Monitor(this, hostText.getText()));
				routerPane.add("Router " + hostText.getText(), monitors.get(monitors.size() - 1));
				errorLabel.setText("");
				hostText.setText("");
			}

			revalidate();

		});

		clearButton.addActionListener((ae) -> {
			monitors.get(routerPane.getSelectedIndex()).clear();
			monitors.get(routerPane.getSelectedIndex()).chart.repaint();
		});

		setFreqButton.addActionListener((ae) -> {
			if (freqText.getText().isBlank())
				errorLabel.setText("Niste uneli frekveciju!");
			else {
				try {
					int newFreq = Integer.parseInt(freqText.getText());
					if (newFreq <= 0)
						errorLabel.setText("Vrednost frekvencije mora biti veća od 0!");
					else if (freq != newFreq) {
						freq = newFreq;
						clearMonitors();
						repaintAll();
						errorLabel.setText("");
					}
				} catch (NumberFormatException e) {
					errorLabel.setText("Vrednost frekvencije mora biti broj!");
				}
			}

			revalidate();
		});

		setSizeButton.addActionListener((ae) -> {
			if (graphSizeText.getText().isBlank())
				errorLabel.setText("Niste uneli veličinu!");
			else {
				try {
					int newSize = Integer.parseInt(graphSizeText.getText());
					if (newSize <= 1)
						errorLabel.setText("Vrednost veličine mora biti veća od 1!");
					else {
						setLimit(newSize);
						repaintAll();
						errorLabel.setText("");
					}
				} catch (NumberFormatException e) {
					errorLabel.setText("Vrednost veličine mora biti broj!");
				}
			}

			revalidate();
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});

		pack();
		thread.start();
		setVisible(true);

	}

	void clearMonitors() {
		for (int i = 0; i < monitors.size(); i++)
			monitors.get(i).clear();
	}

	void repaintAll() {
		for (int i = 0; i < monitors.size(); i++)
			monitors.get(i).chart.repaint();
	}

	void setLimit(int limit) {
		for (int i = 0; i < monitors.size(); i++)
			monitors.get(i).setLimit(limit);
	}

	@Override
	public void run() {
		while (true) {
			for (int i = 0; i < monitors.size(); i++)
				monitors.get(i).nextUpdate();

			monitors.get(routerPane.getSelectedIndex()).chart.repaint();
			monitors.get(routerPane.getSelectedIndex()).setText();

			try {
				Thread.sleep(freq * 100);
			} catch (InterruptedException ignored) {

			}
			revalidate();
		}
	}

	public static void main(String[] args) {
		new RM2Projekat();
	}
}
