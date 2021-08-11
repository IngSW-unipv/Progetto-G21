package controller;

import strategies.GenericTestStrategy;

/**
 * Simple test class, this behaviour represents the standard invocation approach
 * for the listening post.
 */

public class Class1 {
	private String[] string;

	/**
	 * Very important, always use the method [name of the strategy
	 * class].getStrategyName, it will return the exact name of the strategy and
	 * you'll be sure that the invocation goes correctly.
	 */
	public Class1() {
		this.string = new String[1];
		string[0] = "La classe 1 è stata creata";
		ListeningPost.getInstance().notifyListeningPost(GenericTestStrategy.getStrategyName(), string);
	}

	public void repeatIntroduction() {
		ListeningPost.getInstance().notifyListeningPost(GenericTestStrategy.getStrategyName(), string);
	}

}
