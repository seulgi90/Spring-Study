package hello.core.singleton;

public class SingletonService {

	private static final SingletonService instance = new SingletonService();
	
	// 이 객체 인스턴스가 필요하면 오직 getInstance() 메서드를 통해서만 조회 할 수 있다
	// 이 메서드를 호출하면 항상 같은 인스턴스를 반환
	public static SingletonService getInstance() {
		return instance;
	}
	
	// 딱 1개의 객체 인스턴스만 존재해야 하므로, 생성자를 private으로 막아서 혹시라도 외부에서 new 키워드로 객체 생성됨을 막는다
	private SingletonService() {
	}
	
	public void logic() {
		System.out.println("싱글톤 객체 로직 호출");
	}
}
