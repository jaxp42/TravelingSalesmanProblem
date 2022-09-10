import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SalesmanGenome implements Comparable{
    private List<Integer> citiesOrder;
    private int[][] travelPrices;
    private int startingCity;
    private int numberOfCities;
    private int fitness;

    public SalesmanGenome(int numberOfCities, int[][] travelPrices, int startingCity){
        this.numberOfCities = numberOfCities;
        this.travelPrices = travelPrices;
        this.startingCity = startingCity;
        this.citiesOrder = generateRandomCitiesOrder();
        this.fitness = calculateFitness();
    }

    public SalesmanGenome(SalesmanGenome salesmanGenome){
        this.citiesOrder = salesmanGenome.getCitiesOrder();
        this.travelPrices = salesmanGenome.getTravelPrices();
        this.travelPrices = salesmanGenome.getTravelPrices();
        this.startingCity = salesmanGenome.getStartingCity();
        this.numberOfCities = salesmanGenome.getNumberOfCities();
        this.fitness = calculateFitness();
    }

    private int calculateFitness(){
        int fitness = 0;
        int currentCity = startingCity;

        for(int city : citiesOrder){
            fitness += travelPrices[currentCity][city];
            currentCity = city;
        }

        fitness += travelPrices[citiesOrder.get(numberOfCities-2)][startingCity];
        return fitness;
    }

    private List<Integer> generateRandomCitiesOrder(){
        List<Integer> randomCitiesOrder = new ArrayList<>();

        for(int i=0; i<numberOfCities; i++){
            if(i != startingCity){
                randomCitiesOrder.add(i);
            }
        }

        Collections.shuffle(randomCitiesOrder);
        return randomCitiesOrder;
    }

    @Override
    public int compareTo(Object o) {
        SalesmanGenome genome = (SalesmanGenome) o;

        if(this.fitness > genome.getFitness())
            return 1;
        else if(this.fitness < genome.getFitness())
            return -1;
        else
            return 0;
    }

    public List<Integer> getCitiesOrder() {
        return citiesOrder;
    }

    public void setCitiesOrder(List<Integer> citiesOrder) {
        this.citiesOrder = citiesOrder;
    }

    public int[][] getTravelPrices() {
        return travelPrices;
    }

    public void setTravelPrices(int[][] travelPrices) {
        this.travelPrices = travelPrices;
    }

    public int getStartingCity() {
        return startingCity;
    }

    public void setStartingCity(int startingCity) {
        this.startingCity = startingCity;
    }

    public int getNumberOfCities() {
        return numberOfCities;
    }

    public void setNumberOfCities(int numberOfCities) {
        this.numberOfCities = numberOfCities;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
}
