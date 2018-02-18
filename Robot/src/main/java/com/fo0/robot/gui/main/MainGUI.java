package com.fo0.robot.gui.main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.fo0.robot.controller.ControllerChain;
import com.fo0.robot.gui.sub.AddChainItemWindow;
import com.fo0.robot.gui.sub.ConfigWindow;
import com.fo0.robot.gui.sub.ConsoleWindow;
import com.fo0.robot.model.ActionItem;
import com.fo0.robot.model.BeanTableModelAction;
import com.fo0.robot.utils.CONSTANTS;

public class MainGUI {

	private static MainGUI window = null;
	private static JFrame frame;

	private static BeanTableModelAction tableModel;
	private static JTable actionTable;

	/**
	 * Launch the application.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public static void bootstrap() {
		window = new MainGUI();
		initialize();
		MainGUI.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		// create main frame
		frame = new JFrame();
		frame.setTitle("Robot v" + CONSTANTS.VERSION);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 516, 319);

		// center frame on screen
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setResizable(false);

		JPanel panelTop = new JPanel();
		panelTop.setBounds(0, 0, 514, 25);
		frame.getContentPane().add(panelTop);
		panelTop.setLayout(null);

		JButton btnAdd = new JButton("ADD");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AddChainItemWindow();
			}
		});

		btnAdd.setBounds(97, 0, 73, 24);
		panelTop.add(btnAdd);

		JButton btnDel = new JButton("DEL");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int col = actionTable.getSelectedRow();
				tableModel.removeRow(tableModel.getRow(col));
			}
		});
		btnDel.setBounds(345, 0, 73, 24);
		panelTop.add(btnDel);

		JButton btnUp = new JButton("UP");
		btnUp.setBounds(182, 0, 73, 24);
		panelTop.add(btnUp);

		JButton btnDown = new JButton("DOWN");
		btnDown.setBounds(254, 0, 79, 24);
		panelTop.add(btnDown);

		JButton btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ConsoleWindow();
				ControllerChain.getChain().start();
			}
		});
		btnStart.setBounds(0, 0, 98, 24);
		panelTop.add(btnStart);

		JButton btnSave = new JButton("CONFIG");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ConfigWindow();
			}
		});
		btnSave.setBounds(422, 0, 92, 24);
		panelTop.add(btnSave);

		JPanel panelTable = new JPanel();
		panelTable.setBounds(0, 26, 514, 265);
		frame.getContentPane().add(panelTable);

		panelTable.setLayout(new CardLayout(0, 0));

		actionTable = new JTable();
		actionTable.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					System.out.println("doubleclick");
					JTable table = (JTable) e.getSource();
					Point point = e.getPoint();
					int row = table.rowAtPoint(point);
					ActionItem item = tableModel.getRow(row);
					new AddChainItemWindow(item);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		tableModel = new BeanTableModelAction();
		actionTable.setModel(tableModel);
		JScrollPane scrollPane = new JScrollPane(actionTable);
		panelTable.add(scrollPane, "name_7985051461163");
		MainGUI.refreshTable();
	}

	public static void addItem(ActionItem action) {
		tableModel.addRow(action);
	}

	public static void refreshTable() {
		tableModel.fireTableDataChanged();
	}

	public static void deleteItem(ActionItem action) {
		tableModel.removeRow(action);
	}

	public static BeanTableModelAction getTableModel() {
		return tableModel;
	}

	public void refreshGUI() {
		refreshTable();
	}
}
