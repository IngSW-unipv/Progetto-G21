package storage;

public class StoredIngredient {

	/**There isn't much to say about this class, it's just a wrapper for all the
	 * required info on a single ingredient when it's stored. Appropriate getters and 
	 * setters to manipulate the entry in the storage manifest. This approach is similar to that
	 * of a database, we simply haven't used it because a restaurant usually doesn't have
	 * such a big storage to justify a fully implemented database, a file manifest will suffice.
	 * (If in the future this architectural choice is proven baseless, i will implement a DB, but 
	 * 20-50 ingredients is a joke of a db, so to speak)*/
	private String name;
	private double amount;
	private double lowerBound;
	private double upperBound;
	
	public StoredIngredient(String name, double amount, double lowerBound, double upperBound) {
		this.name=name;
		this.amount=amount;
		this.lowerBound=lowerBound;
		this.upperBound=upperBound;
		
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}

	public String getName() {
		return name;
	}

	public double getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}
	
	
	
	
}
