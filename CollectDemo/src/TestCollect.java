import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestCollect {

	public static void main(String[] args) {
		// stream is closed when run once, stream has already been operated upon or closed 
		Stream<Account> myStream = 
					Stream.of(new Account(1, "Checking", 100, "NY"), new Account(2, "Savings", 250, "NY"),
							new Account(3, "Checking", 300, "NY"),new Account(4, "Checking", 150, "Las Vegas"));
		// reuse a stream
		Supplier<Stream<Account>> streamSupplier = 
				() -> Stream.of(new Account(1, "Checking", 100, "NY"), new Account(2, "Savings", 250, "NY"),
				new Account(3, "Checking", 300, "NY"),new Account(4, "Checking", 150, "Las Vegas"));
		
		//Provide a list of all Checking accounts
		List<Integer> idChecking = myStream.filter(e -> e.getType().equalsIgnoreCase("checking"))
										.map(e -> e.getId())
										.collect(Collectors.toList());
		
		System.out.println(idChecking);
		
		//Provide a distinct list of cities
		Set<String> cities = streamSupplier.get().map(e -> e.getCity()).collect(Collectors.toSet());
		System.out.println(cities);
		
		// Provide a sum of balances of Checking accounts
		double sumBalances = streamSupplier.get().filter(e->e.getType().equalsIgnoreCase("checking")).mapToDouble(Account::getBalance).sum();
//				                            .collect(Collectors.summingDouble(Account::getBalance));
		System.out.println(sumBalances);
		
		// Find account with maximum balance
		Optional<Account> accMaxBalance = streamSupplier.get().collect(Collectors.maxBy(Comparator.comparing(Account::getBalance)));
		System.out.println(accMaxBalance);
		// Find account with minimum balance
		Optional<Account> accMinBalance = streamSupplier.get().collect(Collectors.minBy(Comparator.comparing(Account::getBalance)));
		System.out.println(accMinBalance);
		
		// Group accounts by account type and sum their balances
		Map<String, Double> groupAcc = streamSupplier.get().collect(Collectors.groupingBy(Account::getType,Collectors.summingDouble(Account::getBalance)));
		System.out.println(groupAcc);
		
		// Group accounts by account type and then further by city and sum their balances
		Map<String, Map<String, Double>> groupAccCity = streamSupplier.get().collect(Collectors
						.groupingBy(Account::getType,Collectors.groupingBy(Account::getCity,Collectors.summingDouble(Account::getBalance))));
		System.out.println(groupAccCity);
		
		// Partitioning accounts with low balance and high balance
		Map<Boolean, List<Account>> result = streamSupplier.get().collect(Collectors.partitioningBy(e->e.getBalance() > 125));
		System.out.println(result);
	}

}
