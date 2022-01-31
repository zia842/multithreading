package multithreading;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JavaEight {
	
	public static void main(String []args) {
		int numbers[] = new int[] {9,8,7,5,4,2,1,2,3,1};
		int min = IntStream.of(numbers)
						.min()
						.getAsInt();
		System.out.println(min);
		
		IntStream.of(numbers)
		         .min()
		         .ifPresent(min1 -> System.out.println(min1));
		
		IntStream.of(numbers)
		         .min()
		         .ifPresent(System.out::println);
		
		System.out.println("*******Stats**********");
		IntSummaryStatistics stats = IntStream.of(numbers).summaryStatistics();
		System.out.println(stats.getAverage());
		System.out.println(stats.getCount());
		System.out.println(stats.getMax());
		System.out.println(stats.getMin());
		System.out.println(stats.getSum());
		
		System.out.println("*******Streams**********");
		
		IntStream.of(numbers)
			     .distinct()
			     .sorted()
			     .limit(3)
			     .forEach(System.out::println);
		
		IntStream.of(numbers)
			     .sorted()
			     .limit(3)
			     .sum();
		
		IntStream.of(numbers).sorted(); //Sort
		IntStream.of(numbers).distinct(); //Distinct 
		IntStream.of(numbers).limit(2); //Limit first 2
		IntStream.of(numbers).skip(2); // Skip first 2
		IntStream.of(numbers).filter(num -> num % 2 == 0); //Filter only Even
		IntStream.of(numbers).map(num -> num * 2); //Multiply by 2 every number
		IntStream.of(numbers).boxed(); //Convert each number to Integer
		IntStream.of(numbers).anyMatch(num -> num % 2 == 0); //If any Number is Even
		IntStream.of(numbers).allMatch(num -> num % 2 == 0); //If all Numbers are Even
		
		Employee e = new Employee();
		List<Employee> employees = e.getAllEmployees();
		//Fetch first 3 highest earning employees
		//Sort Employees based on salary. Limit to 3 and print there names
		employees.stream()
				 .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
				 .limit(3)
				 .map(Employee::getName)
				 .forEach(System.out::println);
		
		//Fetch first 3 highest earning employees if they are active
		//Sort Employees based on salary. Filter, Limit to 3 and print there names
				
		List<String> eList = employees.stream()
									  .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
									  .filter(employee -> isActive(employee))
									  .limit(3)
									  .map(Employee::getName)
									  .collect(Collectors.toList()); //Collect end result as List
		
		Set<String> setOfEmps = employees.stream()
										 .sorted(Comparator.comparingInt(Employee::getSalary).reversed())
										 .filter(employee -> isActive(employee))
										 .limit(3)
										 .map(Employee::getName)
										 .collect(Collectors.toSet()); //Collect end result as Set
		
		
		Map<String, Employee> mapOfEmps = employees.stream()
													.sorted(Comparator.comparingInt(Employee::getSalary).reversed())
													.filter(employee -> isActive(employee))
													.limit(3)
													.collect(Collectors.toMap(e1-> e1.name, e1 -> e1)); //Collect end result as Set
		
		
	}
	
	public static boolean isActive(Employee e) {
		return e != null;
	}

}

class Employee {
	
	String name;
	int salary;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	public List<Employee> getAllEmployees(){
		return new ArrayList<Employee>();
	}
	
	
}
