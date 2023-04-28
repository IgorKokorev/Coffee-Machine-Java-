package machine;

import java.util.Scanner;

public class CoffeeMachine {

    static Machine cm;
    static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        cm = new Machine();

        while (true) {
            System.out.println("\nWrite action (buy, fill, take, remaining, exit):");

            String inp = scanner.nextLine();
            switch (inp) {
                case "buy" -> buy();
                case "fill" -> fillMachine();
                case "take" -> takeMoney();
                case "remaining" -> cm.printStatus();
                case "exit" -> {
                    return;
                }
                default -> System.out.println("Wrong input");
            }
        }


    }

    private static void takeMoney() {
        System.out.println("I gave you $" + cm.money);
        cm.money = 0;
    }

    private static void fillMachine() {
        System.out.println("\nWrite how many ml of water you want to add:");
        cm.water += Integer.parseInt(scanner.nextLine());

        System.out.println("Write how many ml of milk you want to add:");
        cm.milk += Integer.parseInt(scanner.nextLine());

        System.out.println("Write how many grams of coffee beans you want to add:");
        cm.coffee += Integer.parseInt(scanner.nextLine());

        System.out.println("Write how many disposable cups you want to add:");
        cm.cups += Integer.parseInt(scanner.nextLine());
    }

    private static void buy() {
        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String inp = scanner.nextLine();

        if (inp.equals("back")) return;

        int choice = Integer.parseInt(inp);
        Coffee coffeeCup;
        switch (choice) {
            case 1 -> coffeeCup = new Espresso();
            case 2 -> coffeeCup = new Latte();
            case 3 -> coffeeCup = new Cappuccino();
            default -> {
                System.out.println("Wrong input");
                return;
            }
        }

        String result = cm.buyCoffee(coffeeCup);
        if (result != null) {
            System.out.println("Sorry, not enough " + result + "!");
        } else System.out.println("I have enough resources, making you a coffee!");
    }
}

class Machine {
    int money = 550;
    int water = 400;
    int milk = 540;
    int coffee = 120;
    int cups = 9;

    public void printStatus() {
        System.out.println("\nThe coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(coffee + " g of coffee beans");
        System.out.println(cups + " disposable cups");
        System.out.println("$" + money + " of money");
    }

    public String buyCoffee(Coffee coffeeCup) {

        if (water < coffeeCup.waterPerCup) return "water";
        if (milk < coffeeCup.milkPerCup) return "milk";
        if (coffee < coffeeCup.coffeePerCup) return "coffee beans";
        if (cups < 1) return "cups";

        water -= coffeeCup.waterPerCup;
        milk -= coffeeCup.milkPerCup;
        coffee -= coffeeCup.coffeePerCup;
        cups--;
        money += coffeeCup.price;

        return null;
    }
}
abstract class Coffee {
    String name;
    int waterPerCup;
    int milkPerCup;
    int coffeePerCup;
    int price;

    public Coffee(String name, int waterPerCup, int milkPerCup, int coffeePerCup, int price) {
        this.name = name;
        this.waterPerCup = waterPerCup;
        this.milkPerCup = milkPerCup;
        this.coffeePerCup = coffeePerCup;
        this.price = price;
    }
}

class Espresso extends Coffee {
    public Espresso() {
        super("espresso", 250, 0, 16, 4);
    }
}

class Latte extends Coffee {
    public Latte() {
        super("latte", 350, 75, 20, 7);
    }
}

class Cappuccino extends Coffee {
    public Cappuccino() {
        super("cappuccino", 200, 100, 12, 6);
    }
}
