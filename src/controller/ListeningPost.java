package stagingPackage;

public class ListeningPost {

	private static ListeningPost l;
	private Controller c;
	private ListeningPost() {
	}
	
	public static ListeningPost invokeListeningPost() {
		if (l==null){
			l=new ListeningPost();
			return l;
		}
		else
			return l;
	}
	
	public void bindController(Controller c) {
		this.c=c;
	}
	public void NotifyListeningPost(String strategyRequired, String[] args) {
		if (c== null) {
			System.out.println("Controller not bound. Bind controller to run this method.");
		}
		else
		{
			c.strategyCall(strategyRequired, args);
		}
	}
}
