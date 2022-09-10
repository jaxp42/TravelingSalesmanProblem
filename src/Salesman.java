import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Salesman {
    private int pupulationSize,
            numberOfCities,
            reproductionSize,
            maxGenerations,
            mutationRate,
            startingCity,
            minFitness,
            tournamentSize;
    private int[][] travelPrices[];
    private SelectionType selectionType;


    public Salesman(){}

    public List<SalesmanGenome> runSelection(List<SalesmanGenome> population, SelectionType selectionType){
        List<SalesmanGenome> selectedPopulation = new ArrayList<>();
        SalesmanGenome selectedGenome;

        for(SalesmanGenome genome : population){
            switch (selectionType){
                case ROULETTE:
                    selectedPopulation.add(rouletteSelection(population));
                    break;
                case TOURNAMENT:
                    selectedPopulation.add(tournamentSelection(population));
                    break;
            }
        }

        return selectedPopulation;
    }

    private SalesmanGenome rouletteSelection(List<SalesmanGenome> population){
        Random random = new Random();
        SalesmanGenome selectedGenome = null;
        int totalFitness = population.stream().map(SalesmanGenome::getFitness).mapToInt(Integer::intValue).sum();
        int selectedValue = random.nextInt(totalFitness);

        float minValueIndex = (float) 1/selectedValue;

        for(SalesmanGenome genome : population){
            float genomeValueIndex = (float) 1/genome.getFitness();
            if(genomeValueIndex >= minValueIndex){
                selectedGenome = genome;
            }
        }

        if(selectedGenome == null){
            int selectRandom = random.nextInt(getPupulationSize());
            selectedGenome = population.get(selectRandom);
        }

        return selectedGenome;
    }

    private SalesmanGenome tournamentSelection(List<SalesmanGenome> population){
        List<SalesmanGenome> selectedGenomes = SelectionUtils.getNRandomGenomes(population, tournamentSize);
        return Collections.min(selectedGenomes);
    }

    public int getPupulationSize() {
        return pupulationSize;
    }

    public void setPupulationSize(int pupulationSize) {
        this.pupulationSize = pupulationSize;
    }

    public int getNumberOfCities() {
        return numberOfCities;
    }

    public void setNumberOfCities(int numberOfCities) {
        this.numberOfCities = numberOfCities;
    }

    public int getReproductionSize() {
        return reproductionSize;
    }

    public void setReproductionSize(int reproductionSize) {
        this.reproductionSize = reproductionSize;
    }

    public int getMaxGenerations() {
        return maxGenerations;
    }

    public void setMaxGenerations(int maxGenerations) {
        this.maxGenerations = maxGenerations;
    }

    public int getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(int mutationRate) {
        this.mutationRate = mutationRate;
    }

    public int getStartingCity() {
        return startingCity;
    }

    public void setStartingCity(int startingCity) {
        this.startingCity = startingCity;
    }

    public int getMinFitness() {
        return minFitness;
    }

    public void setMinFitness(int minFitness) {
        this.minFitness = minFitness;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }

    public void setTournamentSize(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    public int[][][] getTravelPrices() {
        return travelPrices;
    }

    public void setTravelPrices(int[][][] travelPrices) {
        this.travelPrices = travelPrices;
    }

    public SelectionType getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
    }
}
