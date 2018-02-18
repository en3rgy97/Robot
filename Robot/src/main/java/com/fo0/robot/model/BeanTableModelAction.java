package com.fo0.robot.model;

import com.fo0.robot.controller.ControllerChain;

public class BeanTableModelAction extends BeanTableModel<ActionItem> {

	private static final long serialVersionUID = -4091648872922934132L;

	public BeanTableModelAction() {
		super(ActionItem.class);

		addColumn("Type", "type");
		addColumn("Description", "description");
		addColumn("Value", "value");

		loadActionContextFromController();
	}

	@Override
	public void addRow(ActionItem row) {
		ActionItem tmpRow = getRow(row);
		if (tmpRow != null) {
			removeRow(row);
		}
		super.addRow(row);
		ControllerChain.getChain().addActionItem(row);
	}

	@Override
	public void removeRow(ActionItem row) {
		super.removeRow(row);
		ControllerChain.getChain().removeActionItem(row);
	}

	public ActionItem getRow(ActionItem item) {
		return super.getRow(item);
	}

	public void loadActionContextFromController() {
		ControllerChain.getChain().getContext().getMap().entrySet().stream().forEach(e -> {
			addRow(e.getValue());
		});
	}

}
