package com.fo0.robot.chain.action;

import java.util.Map.Entry;

import com.fo0.robot.chain.Chain;
import com.fo0.robot.chain.ChainItem;
import com.fo0.robot.controller.Controller;
import com.fo0.robot.model.ActionItem;

public class ChainActions extends Chain<ActionContext> {

	public ChainActions() {
		super("Chain Actions", ActionContext.builder().build());
	}

	public Entry<Integer, ActionItem> addActionItem(ActionItem item) {
		return getContext().push(item);
	}

	public void removeActionItem(ActionItem item) {
		getContext().remove(item);
	}

	public void createChains() {
		getChains().clear();
		getContext().getMap().entrySet().stream().forEach(e -> {
			addToChain(e.getValue().getType().name(), e.getValue().getDescription(),
					ChainItem.<ActionContext>builder().command(ChainActionItem.builder().build()).build());
		});
	}

	@Override
	public void start() {
		try {
			setFailOnError(!Controller.getConfig().ignoreErrors);
		} catch (Exception e) {
		}
		createChains();
		super.start();
		getContext().reset();
	}

}
