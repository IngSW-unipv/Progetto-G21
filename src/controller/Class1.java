package controller;

import strategies.GenericTestStrategy;

public class Class1 {
	
	/** Simple test class, this behaviour represents the standard invocation approach
	 * for the listening post*/
	private String[] str;
	public Class1(){
		/**Very important, always use the method [name of the strategy class].getStrategyName, it will return
		 * the exact name of the strategy and you'll be sure that the invocation goes correctly*/
		this.str= new String[1];
		str[0]="La classe 1 è stata creata";
		ListeningPost.invokeListeningPost().NotifyListeningPost(GenericTestStrategy.getStrategyName(), str);
	}
	
	public void repeatIntroduction() {
		ListeningPost.invokeListeningPost().NotifyListeningPost(GenericTestStrategy.getStrategyName(), str);
	}
	
	
}
