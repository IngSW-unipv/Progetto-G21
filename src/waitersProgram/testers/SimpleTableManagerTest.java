package waitersProgram.testers;

import java.util.HashSet;
import java.util.Iterator;

import waitersProgram.model.TableManager;

public class SimpleTableManagerTest {

	public static void main(String[] args) {

		TableManager tableManager = TableManager.getInstance();
		HashSet<Integer> tables = new HashSet<Integer>();
		Iterator<Integer> iterator = null;

		tables = tableManager.getTables();
		int lastTableInsert = 0;

		/**
		 * Tester of method getTable().
		 */
		if (tables.equals(tableManager.getTables())) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method addTable().
		 */
		iterator = tables.iterator();
		while (iterator.hasNext()) {
			lastTableInsert = iterator.next();
		}
		tableManager.addTable(lastTableInsert + 1);
		tables.add(lastTableInsert + 1);

		if (tables.equals(tableManager.getTables())) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

		/**
		 * Tester of method removeTable(), removing the last but one table.
		 */
		iterator = tables.iterator();
		while (iterator.hasNext()) {
			lastTableInsert = iterator.next();
		}

		tableManager.removeTable(lastTableInsert - 1);
		tables.remove(lastTableInsert - 1);

		if (tables.equals(tableManager.getTables())) {
			System.out.println("OK");
		} else {
			System.out.println("TEST FAILED");
		}

	}

}
