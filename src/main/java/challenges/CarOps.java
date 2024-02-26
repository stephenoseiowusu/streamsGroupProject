package challenges;

import domain.Car;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CarOps {

    //21. Filter by Make: Filter the list of cars to only include cars with a specific make.
    public static List<Car> carWithSpecificMake(List<Car> cars){
        return cars.stream()
                .filter(car -> car.getMake().equalsIgnoreCase("Ford"))
                .collect(Collectors.toList());
    }

    //22. Filter by Year: Filter the list of cars by year
    public static List<Car> cartWithSpecificYear(List<Car> cars, int year){
        return cars.stream().filter(car -> car.getYear() == year).collect(Collectors.toList());
    }

    //23.Filter by Price: Filter the list of cars by price range
    public List<Car> filterCarsByPriceRange(List<Car> cars, double starting_price, double ending_price){
        return cars.stream().filter(car  -> {return car.getPrice() >= starting_price && car.getPrice() <= ending_price;}).collect(Collectors.toList());
    }

    //24. Map Car to model names: Map car to model names
    public Map<String,List<Car>> mapCarToModelNames(List<Car> cars){
        return cars.stream().collect(Collectors.groupingBy(Car::getModel));
    }

    //25.Map to uppercase makes
    public Map<String,List<Car>> mapCarsToListOfMakes(List<Car> cars){
        return cars.stream().collect(Collectors.groupingBy(Car::getMake));
    }

    //26.Sort list of cars by year
    public List<Car> sortCarsByYear(List<Car> cars){
        return cars.stream().sorted((car1,car2) ->{
            return car1.getYear() - car2.getYear();
        }).collect(Collectors.toList());
    }
    //27. Sort list of cars by price
    public List<Car> sortListOfCarsByPrice(List<Car> cars){
        List<Car> result =  cars.stream().sorted(new Comparator<Car>() {
            @Override
            public int compare(Car o1, Car o2) {
                return (int)(o1.getPrice() - o2.getPrice());
            }
        }).collect(Collectors.toList());
        Collections.reverse(result);
        return result;
    }

    //28. Get highest priced car.
    public Car getHighestPricedCar(List<Car> cars){
        return cars.stream().max((c1,c2) ->{
            return (int)(c1.getPrice() - c2.getPrice());
        }).orElse(null);
    }

    //29. Get lowest price car.
    public Car getLowestPricedCar(List<Car> cars){
        return cars.stream().min((c1,c2) ->{
            return (int)(c1.getPrice() - c2.getPrice());
        }).orElse(null);
    }

    //30. Group the cars by make
    public Map<String,List<Car>> getCarsByMakes(List<Car> cars){
        return cars.stream().collect(Collectors.groupingBy(Car::getMake));
    }
    //31. Count Cars by Make: Count the number of cars for each make.
    public static Map<String, Long> countCarsByMake (List<Car> cars){
        return cars.stream()
                .collect(Collectors.groupingBy(Car::getMake, Collectors.counting()));
    }


    //32. Average Price: Calculate the average price of all cars.
    public static double averagePriceOfCars (List<Car> cars){
                OptionalDouble avg = cars.stream()
                .mapToDouble(Car::getPrice)
                .average();
        return avg.orElse(0.0);
    }

    //33. Sum of Prices: Calculate the sum of all car prices.
    public static double sumOfPriceOfCars(List<Car> cars){
         return cars.stream()
                 .mapToDouble(car -> (double) car.getPrice())
                 .reduce(0, Double::sum);

    }
    //34. Any Car with Blue Color: Check if there's any car with a blue color.
    public static boolean carsWithBlueColor(List<Car> cars){
       return cars.stream()
                .anyMatch(car -> car.getColor().equalsIgnoreCase("blue"));
    }

    //35. All Cars are Expensive: Check if all cars are expensive (e.g., price > 50000).
    public static boolean isAllCarExpensive(List<Car> cars) {
        return cars.stream()
                .anyMatch(car -> car.getPrice() > 50000);
    }

    //36. None Match the Condition: Check if none of the cars match a specific condition.
    public static boolean isThisConditionMatch(List<Car> cars) {
        return  cars.stream()
                .noneMatch(car -> car.getModel().equalsIgnoreCase("Sonata"));
    }

    //37. Skip First N Cars: Skip the first N cars from the list.
    public static List<Car> skippingTheFirst5Cars(List<Car> cars, int n){
         return cars.stream()
                 .skip(n)
                 .collect(Collectors.toList());
    }

    //38. Limit to N Cars: Limit the list to the first N cars.
    public static List<Car> limitingTheFirstNCars(List<Car> cars, int n){
        return cars.stream()
                .limit(n)
                .collect(Collectors.toList());
    }

    //39. Distinct Colors: Get a list of distinct car colors.
    public static List<String> listOfDistinctCarColor(List<Car> cars){
        return cars.stream()
                .map(Car::getColor)
                .distinct()
                .collect(Collectors.toList());
    }

    //40. Concatenate Make and Model: Concatenate the make and model of each car.
    public static List<String> concatenatingMakeAndModel(List<Car> cars){
        return cars.stream()
                .map(car -> car.getModel() + " " + car.getModel())
                .collect(Collectors.toList());
    }

    //41. Find First Car: Find the first car in the list.
    public static Optional<Car> findTheFirstCar(List<Car> cars){
        return cars.stream()
                .findFirst();
    }

    //42. Find Any Car: Find any car in the list.
    public static Optional<Car> findAnyCar(List<Car> cars){
        return cars.stream()
                .findAny();
    }

    //43. Remove Duplicates: Remove duplicate cars from the list based on make and model.

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    public static List<Car> removeDuplicateCars(List<Car> cars) {
        return cars.stream()
                .filter(distinctByKey(car -> car.getMake() + car.getModel()))
                .collect(Collectors.toList());

    }

    //44. Partition Cars by Price: Partition the cars into two groups based on whether their price is above a certain value.
    public static Map<Boolean, List<Car>> highPriceAndLowPrice(List<Car> cars, double idealPrice){
        return cars.stream()
                .collect(Collectors.partitioningBy(car -> car.getPrice() > idealPrice));
    }

    //45. Calculate Total Price by Make: Calculate the total price of cars for each make.
    public static Map<String, Double> calculateTotalPriceByMake(List<Car> cars) {
        return cars.stream()
                .collect(Collectors.groupingBy(
                        Car::getMake,
                        Collectors.summingDouble(Car::getPrice)
                ));
    }

    //46. Join Car Names into a String: Join the names of all cars into a single comma-separated string.
    public static String joinCarsByNames(List<Car> cars) {
        return cars.stream()
                .map(car -> car.getMake() + " " + car.getModel())
                .collect(Collectors.joining(", "));
    }

    //47. Peek and Print: Use peek to print the details of each car in the stream.
    public static void printCarDetails(List<Car> cars) {
        cars.stream()
                .peek(car -> System.out.println("Id: " + car.getId() + ", Make: " + car.getMake() + ", Model: "
                        + car.getModel() + ", Year " + car.getYear() + ", Price: " + car.getPrice() + ", Color: " + car.getColor()))
                .forEach(car -> {});
    }

    //48. Average Price by Make: Calculate the average price of cars for each make.
    public static Map<String, Double> averagePriceOfCarsByMake(List<Car> cars){
        return cars.stream()
                .collect(Collectors.groupingBy(Car::getMake, Collectors.averagingDouble(Car::getPrice)));
    }

    //49. Concatenate All Car Details: Concatenate all car details into a single string.
    public static String concatenateAllCarDetails(List<Car> cars) {
        return cars.stream()
                .map(car -> car.getId() + car.getMake() + car.getModel() + car.getYear()
                        + car.getPrice() + car.getColor())
                .collect(Collectors.joining());
    }

    //50. Find the Newest Car: Find the newest (latest year) car in the list.
    public static List<Car> newestCarFromTheList (List<Car> cars){
        Optional<Integer> newCar = cars.stream()
                .map(Car::getYear)
                .max(Integer::compareTo);
                //.max(Comparator.comparingDouble(Car::getYear));
        if(newCar.isPresent()) {
            int newestCar = newCar.get();
            return cars.stream()
                    .filter(car -> car.getYear() == newestCar)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

}
