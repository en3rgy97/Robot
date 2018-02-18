package com.fo0.robot.controller.chain;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.fo0.robot.model.ActionItem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionContext {

	private int start = 0;
	private int end = 0;

	private int current = 0;

	@Builder.Default
	private Map<Integer, ActionItem> map = new TreeMap<Integer, ActionItem>();

	public ActionItem push(int id, ActionItem item) {
		return map.put(id, item);
	}

	public Entry<Integer, ActionItem> push(ActionItem item) {
		Entry<Integer, ActionItem> tmpItem = map.entrySet().stream().filter(e -> e.getValue().equals(item)).findFirst()
				.orElse(null);

		if (tmpItem != null) {
			return tmpItem;
		}

		int id = determineNextId();
		map.put(id, item);
		return new SimpleEntry<Integer, ActionItem>(id, item);
	}

	public void remove(ActionItem item) {
		Entry<Integer, ActionItem> foundItem = map.entrySet().stream()
				.filter(e -> e.getValue().getId().equals(item.getId())).findFirst().orElse(null);
		map.remove(foundItem.getKey());
		end = map.size();
	}

	public Entry<Integer, ActionItem> pop() {
		ActionItem latest = map.get(current);
		SimpleEntry<Integer, ActionItem> entry = new SimpleEntry<Integer, ActionItem>(current, latest);
		current++;
		end = map.size();
		return entry;
	}

	public Entry<Integer, ActionItem> peek() {
		ActionItem latest = map.get(current);
		return new SimpleEntry<Integer, ActionItem>(current, latest);
	}

	public void reset() {
		start = 0;
		end = 0;
		current = 0;
	}

	public int getCurrent() {
		return current;
	}

	private int determineNextId() {
		if (map.size() == 0) {
			return 0;
		} else {
			int id = map.entrySet().stream().map(e -> e.getKey()).max(Integer::compareTo).orElse(0);
			return id = id + 1;
		}
	}
}
