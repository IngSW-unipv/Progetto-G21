package stagingPackage;

public class ListeningPost {

	private static ListeningPost l;
	
	private ListeningPost() {
		
	}
	
	public static ListeningPost CreateListeningPost() {
		if (l==null){
			l=new ListeningPost();
			return l;
		}
		else
			return l;
	}
	
	public void NotifyListeningPost(String strategyRequired, String[] args) {
		
	}
}
