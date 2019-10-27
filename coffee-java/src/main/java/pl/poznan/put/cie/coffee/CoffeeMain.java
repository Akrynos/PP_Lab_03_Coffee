package pl.poznan.put.cie.coffee;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class CoffeeMain {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		System.out.println("Coffee");
		while (true) {
			System.out.println("\n"
					  + "Choose an action\n"
					  + "(a) select coffee,\n"
					  + "(b) list all,\n"
					  + "(c) create new coffee,\n"
					  + "(d) update coffee,\n"
					  + "(e) delete coffee,\n"
					  + "(any other key) exit.\n");

			CoffeeDao dao = new CoffeeDao();
			Scanner in = new Scanner(System.in);
			switch (in.nextLine()) {
				case "a": {
					System.out.println("Please enter coffee name : ");
					String name = in.nextLine();
					System.out.println(dao.get(name));
					break;
				}
				case "b": {
					List<Coffee> coffeeList = dao.getAll();
					for (Coffee c : coffeeList) {
						System.out.println(c.toString());
					}
					break;
				}
				case "c": {
					System.out.print("Please enter coffee name : ");
					String name = in.nextLine();
					System.out.print("Please enter coffee supplier ID : ");
					int supId = Integer.parseInt(in.nextLine());
					System.out.print("Please enter coffee price: ");
					BigDecimal price = new BigDecimal(in.nextLine());
					System.out.print("Please enter coffee sales : ");
					int sales = Integer.parseInt(in.nextLine());
					System.out.print("Please enter coffee total : ");
					int total = Integer.parseInt(in.nextLine());
					dao.create(new Coffee(name, supId, price, sales, total));
					break;
				}
				case "d": {
					System.out.print("Please enter coffee name : ");
					String name = in.nextLine();
					System.out.print("Please enter coffee supplier ID : ");
					int supId = Integer.parseInt(in.nextLine());
					System.out.print("Please enter coffee price: ");
					BigDecimal price = new BigDecimal(in.nextLine());
					System.out.print("Please enter coffee sales : ");
					int sales = Integer.parseInt(in.nextLine());
					System.out.print("Please enter coffee total : ");
					int total = Integer.parseInt(in.nextLine());
					dao.update(new Coffee(name, supId, price, sales, total));
					break;
				}
				case "e": {
					System.out.print("Please enter coffee name : ");
					String name = in.nextLine();
					System.out.print("Please enter coffee suppplier ID : ");
					int supId = Integer.parseInt(in.nextLine());
					dao.delete(name, supId);
					break;
				}
				default:
					return;
			}
		}
	}

}
