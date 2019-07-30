package base;

public class MyTest {
	public static void main(String[] args) {
		String temp = "W9XST48LW6ZNFAESSQ9OU1SXX5OS4VK9DA7FPCXX5L9";
		temp = temp.replaceAll("(\\w\\w)(.*)(\\w\\w)", "$1******$3");
		System.out.print(temp);
	}
}
