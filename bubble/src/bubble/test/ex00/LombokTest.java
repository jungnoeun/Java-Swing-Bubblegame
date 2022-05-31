package bubble.test.ex00;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Dog{
	private String name; //변수가 10개이면 메서드가 20개 필요(getter,setter)

	/*public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}*/
	
}

public class LombokTest {

	public static void main(String[] args) {
		Dog d = new Dog();
		d.setName("토토");
		System.out.println(d.getName());

	}

}
