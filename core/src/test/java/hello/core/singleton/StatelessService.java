package hello.core.singleton;

public class StatelessService {
	
	public int order(String name, int price) {
		
		System.out.println("name = " + name + "prcice = " +  price);
		return price;
	}
}
