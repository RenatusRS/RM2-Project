package remme;

import java.io.IOException;
import java.text.ParseException;

import com.ireasoning.protocol.snmp.*;

public class Link {
	private SnmpSession session;
	private SnmpTableModel tableCPU;
	private SnmpTableModel tableMEM;

	public final static int CPU_5SEC = 5;
	public final static int CPU_1MIN = 6;
	public final static int CPU_5MIN = 7;

	public final static int MEM_NAME = 1;
	public final static int MEM_USED = 4;
	public final static int MEM_FREE = 5;

	public final static int MEM_PROCESSOR = 0;
	public final static int MEM_IO = 1;

	static {
		try {
			SnmpSession.loadMib("CISCO-PROCESS-MIB");
			SnmpSession.loadMib("CISCO-MEMORY-POOL-MIB");
		} catch (IOException e) {
			System.out.println("Link Static Greska: Fajl nije nadjen.");
		} catch (ParseException e) {
			System.out.println("Link Static Greska: Fajl nije ispravan.");
		}
	}

	public Link(String host) {
		try {
			session = new SnmpSession(host, 161, "si2019", "si2019", SnmpConst.SNMPV2);
		} catch (IOException e) {
			System.out.println("Link() Greska: Neuspesno povezivanje.");
		}

		refresh();
	}

	public void refresh() {
		try {
			tableCPU = session.snmpGetTable("cpmCPUTotalTable");
			tableMEM = session.snmpGetTable("ciscoMemoryPoolTable");
		} catch (IOException e) {
			System.out.println("Link.refresh() Greska");
		}
	}

	public int getMEMint(int row, int column) {
		return Integer.parseInt((String) tableMEM.getValueAt(row, column));
	}

	public String getMEMstring(int row, int column) {
		return (String) tableMEM.getValueAt(row, column);
	}

	public int getCPUint(int column) {
		return Integer.parseInt((String) tableCPU.getValueAt(0, column));
	}

	public String getCPUstring(int column) {
		return (String) tableCPU.getValueAt(0, column);
	}

	public static void main(String[] args) throws InterruptedException {
		Link conn = new Link("a");
		while (true) {
			System.out.println(conn.getMEMint(MEM_IO, MEM_USED));
			conn.refresh();
			Thread.sleep(1500);
		}

	}
}