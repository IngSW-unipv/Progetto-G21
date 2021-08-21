package chefsProgram.strategies;

import chefsProgram.controller.ChefsGuiController;

public abstract class StrategyAbstract implements StrategyInterface{
	
	private ChefsGuiController controller;
	protected StrategyAbstract(ChefsGuiController controller){
		this.controller=controller;
	}
	public ChefsGuiController getController() {
		return controller;
	}
	
	public String getStrategyName() {
		return this.getClass().getSimpleName();
	}
	
	public abstract void execute(String[] args);
	
}
