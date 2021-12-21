package remme;

import java.awt.*;

public class Monitor extends Panel {
	private static final long serialVersionUID = 1L;

	RM2Projekat owner;

	private Link link;

	Chart chart = new Chart(this);

	CircularArray<Integer> cpu5SecArray;
	CircularArray<Integer> cpu1MinArray;
	CircularArray<Integer> cpu5MinArray;
	
	// CircularArray<Integer> memUsedPArray;
	// CircularArray<Integer> memFreePArray;
	// CircularArray<Integer> memUsedIOArray;
	// CircularArray<Integer> memFreeIOArray;

	CircularArray<Integer> memUsedPArrayPerc;
	CircularArray<Integer> memFreePArrayPerc;
	CircularArray<Integer> memUsedIOArrayPerc;
	CircularArray<Integer> memFreeIOArrayPerc;

	public Monitor(RM2Projekat owner, String host) {
		this.owner = owner;
		link = new Link(host);

		cpu5SecArray = new CircularArray<>(owner.startLimit);
		cpu1MinArray = new CircularArray<>(owner.startLimit);
		cpu5MinArray = new CircularArray<>(owner.startLimit);
		
		// memUsedPArray = new CircularArray<>(owner.startLimit);
		// memFreePArray = new CircularArray<>(owner.startLimit);
		// memUsedIOArray = new CircularArray<>(owner.startLimit);
		// memFreeIOArray = new CircularArray<>(owner.startLimit);
		
		memUsedPArrayPerc = new CircularArray<>(owner.startLimit);
		memFreePArrayPerc = new CircularArray<>(owner.startLimit);
		memUsedIOArrayPerc = new CircularArray<>(owner.startLimit);
		memFreeIOArrayPerc = new CircularArray<>(owner.startLimit);
		
		setLayout(new BorderLayout());
		chart.setPreferredSize(new Dimension(1200, 450));
		
		add(chart, BorderLayout.CENTER);
	}

	public void nextUpdate() {
		link.refresh();

		cpu5SecArray.add(link.getCPUint(Link.CPU_5SEC));
		cpu1MinArray.add(link.getCPUint(Link.CPU_1MIN));
		cpu5MinArray.add(link.getCPUint(Link.CPU_5MIN));

		int used = link.getMEMint(Link.MEM_PROCESSOR, Link.MEM_USED);
		int free = link.getMEMint(Link.MEM_PROCESSOR, Link.MEM_FREE);

		// memUsedPArray.add(used);
		// memFreePArray.add(free);

		memUsedPArrayPerc.add((int) ((used * 100.0) / (used + free)));
		memFreePArrayPerc.add((int) ((free * 100.0) / (used + free)));

		used = link.getMEMint(Link.MEM_IO, Link.MEM_USED);
		free = link.getMEMint(Link.MEM_IO, Link.MEM_FREE);

		// memUsedIOArray.add(used);
		// memFreeIOArray.add(free);

		memUsedIOArrayPerc.add((int) ((used * 100.0) / (used + free)));
		memFreeIOArrayPerc.add((int) ((free * 100.0) / (used + free)));
	}

	public void setText() {
		owner.cpu5SecLabel.setText(link.getCPUstring(Link.CPU_5SEC) + "%");
		owner.cpu1MinLabel.setText(link.getCPUstring(Link.CPU_1MIN) + "%");
		owner.cpu5MinLabel.setText(link.getCPUstring(Link.CPU_5MIN) + "%");

		int used = link.getMEMint(Link.MEM_PROCESSOR, Link.MEM_USED);
		int free = link.getMEMint(Link.MEM_PROCESSOR, Link.MEM_FREE);

		owner.memUsedPLabel.setText(String.format("%.2f%%", (used * 100.0) / (used + free)));
		owner.memFreePLabel.setText(String.format("%.2f%%", (free * 100.0) / (used + free)));

		owner.memPLabel.setText(used + "B / " + (used + free) + "B");

		used = link.getMEMint(Link.MEM_IO, Link.MEM_USED);
		free = link.getMEMint(Link.MEM_IO, Link.MEM_FREE);

		owner.memUsedIOLabel.setText(String.format("%.2f%%", (used * 100.0) / (used + free)));
		owner.memFreeIOLabel.setText(String.format("%.2f%%", (free * 100.0) / (used + free)));

		owner.memIOLabel.setText(used + "B / " + (used + free) + "B");
	}

	public void clear() {
		cpu5SecArray.clear();
		cpu1MinArray.clear();
		cpu5MinArray.clear();
		// memUsedPArray.clear();
		// memFreePArray.clear();
		// memUsedIOArray.clear();
		// memFreeIOArray.clear();
		memUsedPArrayPerc.clear();
		memFreePArrayPerc.clear();
		memUsedIOArrayPerc.clear();
		memFreeIOArrayPerc.clear();
	}

	public void setLimit(int limit) {
		cpu5SecArray.limit(limit);
		cpu1MinArray.limit(limit);
		cpu5MinArray.limit(limit);
		// memUsedPArray.limit(limit);
		// memFreePArray.limit(limit);
		// memUsedIOArray.limit(limit);
		// memFreeIOArray.limit(limit);
		memUsedPArrayPerc.limit(limit);
		memFreePArrayPerc.limit(limit);
		memUsedIOArrayPerc.limit(limit);
		memFreeIOArrayPerc.limit(limit);
	}

}
