package stagingPackage;

public class Class1 {
	
	private String[] str;
	public Class1(){
		this.str= new String[1];
		str[0]="La classe 1 è stata creata";
		ListeningPost.invokeListeningPost().NotifyListeningPost(GenericTestStrategy.getStrategyName(), str);
	}
	
	public void repeatIntroduction() {
		ListeningPost.invokeListeningPost().NotifyListeningPost(GenericTestStrategy.getStrategyName(), str);
	}
	
	
}
