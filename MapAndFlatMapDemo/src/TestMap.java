import java.util.stream.Stream;

public class TestMap {

	public static void main(String[] args) {
		// map
		String[] array = {"Tom","Jack","Harry"};
		//Arrays.stream
//        Stream<String> stream1 = Arrays.stream(array);
		Stream<String> arrayStream = Stream.of(array);
		// lamda expression
		arrayStream.map(e -> e.toUpperCase())
		.forEach(e -> System.out.println(e));
	}

}
