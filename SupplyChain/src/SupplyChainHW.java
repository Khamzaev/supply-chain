import java.util.Scanner;

public class SupplyChainHW {

    private static class Vehicle {
        String name;
        double mass;
        double maxCargo;
        double range;
        double speed;
        double hourlyCost;
        double fuelBurn;

        public Vehicle(String name, double mass, double maxCargo, double range, double speed, double hourlyCost, double fuelBurn) {
            this.name = name;
            this.mass = mass;
            this.maxCargo = maxCargo;
            this.range = range;
            this.speed = speed;
            this.hourlyCost = hourlyCost;
            this.fuelBurn = fuelBurn;
        }

        public double calculateCost(double distance) {
            double fuelCost = distance * fuelBurn;
            double time = distance / speed;
            double hourlyCost = time * this.hourlyCost;
            return fuelCost + hourlyCost;
        }

        @Override
        public String toString()
        {
            return "["+name+", "+mass+", "+maxCargo+", "+range+", "+speed+", "+hourlyCost+", "+fuelBurn+"]";
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numVehicles = scanner.nextInt();

        Vehicle[] fleet = new Vehicle[numVehicles];

        for (int i = 0; i < numVehicles; i++) {
            String name = scanner.next();
            double mass = scanner.nextDouble();
            double maxCargo = scanner.nextDouble();
            double range = scanner.nextDouble();
            double speed = scanner.nextDouble();
            double hourlyCost = scanner.nextDouble();
            double fuelBurn = scanner.nextDouble();

            fleet[i] = new Vehicle(name, mass, maxCargo, range, speed, hourlyCost, fuelBurn);
        }

        double totalProfit = 0.0;

        while (true) {
            String peek = scanner.next();
            double mass;
            if (peek.equals("quit"))
            {
                break;
            }
            else
            {
                mass = Double.parseDouble(peek);
            }
            double distance = scanner.nextDouble();
            double payment = scanner.nextDouble();

            String bestOption = evaluateContract(fleet, mass, distance, payment);

            double profit = getProfit(fleet, distance, bestOption, payment);
            if (bestOption.equals("decline")) {
                System.out.println(bestOption);
            }
            else {
                System.out.printf("%s %.2f\n", bestOption, profit);
            }
            totalProfit += profit;

        }
        System.out.printf("TotalProfit: %.2f\n", totalProfit);

    }

    private static String evaluateContract(Vehicle[] fleet, double mass, double distance, double payment) {
        String bestOption = "decline";
        double maxProfit = 0.0;

        for (Vehicle vehicle : fleet) {
            double cost = vehicle.calculateCost(distance);
            double profit = payment - cost;

            if (profit > maxProfit && mass <= vehicle.maxCargo && distance <= vehicle.range) {
                maxProfit = profit;
                bestOption = vehicle.name;
            }
        }

        return bestOption;
    }

    private static double getProfit(Vehicle[] fleet, double distance, String bestOption, double payment) {
        for (Vehicle vehicle : fleet) {
            if (vehicle.name.equals(bestOption)) {
                double cost = vehicle.calculateCost(distance);
                return payment - cost;
            }
        }
        return 0.0;
    }
}