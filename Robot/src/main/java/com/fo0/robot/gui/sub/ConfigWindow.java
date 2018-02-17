package com.fo0.robot.gui.sub;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.fo0.robot.controller.ControllerChain;
import com.fo0.robot.controller.chain.ActionContext;
import com.fo0.robot.gui.main.MainGUI;
import com.fo0.robot.utils.Parser;
import com.google.gson.Gson;

public class ConfigWindow {

	private JFrame frame;
	private JTextArea console;

	/**
	 * Create the application.
	 */
	public ConfigWindow() {
		initialize();
		frame.setVisible(true);
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Console");
		frame.setBounds(100, 100, 413, 216);

		// center frame on screen
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Robot", "robot");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();

					// save config to disk -> file
					Parser.write(ControllerChain.getChain().getContext(), file);
				}
			}
		});
		btnNewButton.setBounds(33, 55, 94, 78);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Open");
		btnNewButton_1.setBounds(247, 55, 94, 78);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Robot", "robot");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();

					// load config from disk -> file
					ActionContext ctx = Parser.parse(file, ActionContext.class);

					if (ctx == null)
						return;

					ControllerChain.getChain().setContext(ctx);
					MainGUI.getTableModel().loadActionContextFromController();
				}
			}
		});
		frame.getContentPane().add(btnNewButton_1);

	}
}
