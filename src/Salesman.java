import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Salesman {
    private Random random = new Random();
    private int populationSize,
            numberOfCities,
            reproductionSize,
            mutationRate,
            maxGenerations,
            startingCity,
            minFitness,
            tournamentSize,
            maxIterations;
    private int[][] travelPrices;
    private SelectionType selectionType;

    public Salesman(){}

    public List<SalesmanGenome> runSelection(List<SalesmanGenome> population){
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
            int selectRandom = random.nextInt(getPopulationSize());
            selectedGenome = population.get(selectRandom);
        }

        return selectedGenome;
    }

    private SalesmanGenome tournamentSelection(List<SalesmanGenome> population){
        List<SalesmanGenome> selectedGenomes = SelectionUtils.getNRandomGenomes(population, tournamentSize);
        return Collections.min(selectedGenomes);
    }

    public List<SalesmanGenome> crossover(List<SalesmanGenome> parents){
        List<SalesmanGenome> descendants = new ArrayList<>();
        int genomeSize = numberOfCities - 1;
        int crossoverPoint = random.nextInt(genomeSize);

        List<Integer> parent1CitiesOrder = parents.get(0).getCitiesOrder();
        List<Integer> parent2CitiesOrder = parents.get(1).getCitiesOrder();

        for(int i=0; i<crossoverPoint; i++) {
            int changedCity = parent2CitiesOrder.get(i);
            Collections.swap(parents.get(0).getCitiesOrder(), parents.get(0).getCitiesOrder().indexOf(changedCity), i);
        }
        descendants.add(new SalesmanGenome(parents.get(0)));
        parents.get(0).setCitiesOrder(parent1CitiesOrder);

        for(int i=crossoverPoint; i<genomeSize; i++) {
            int changedCity = parent1CitiesOrder.get(i);
            Collections.swap(parents.get(1).getCitiesOrder(), parents.get(1).getCitiesOrder().indexOf(changedCity), i);
        }
        descendants.add(new SalesmanGenome(parents.get(1)));
        parents.get(1).setCitiesOrder(parent2CitiesOrder);

        return descendants;
    }

    public SalesmanGenome mutate(SalesmanGenome genome){
        float randomNumber = random.nextFloat();
        int genomeSize = numberOfCities - 1;

        if(randomNumber < mutationRate){
            Collections.swap(genome.getCitiesOrder(), random.nextInt(genomeSize), random.nextInt(genomeSize));
        }

        return genome;
    }

    public List<SalesmanGenome> createGeneration(List<SalesmanGenome> population){
        List<SalesmanGenome> newGeneration = new ArrayList<>();
        int generationSize = numberOfCities - 1;
        int currentGenerationSize = 0;

        while(currentGenerationSize < generationSize){
            List<SalesmanGenome> parents = SelectionUtils.getNRandomGenomes(population, 2);
            List<SalesmanGenome> children = crossover(parents);

            children.set(0, mutate(children.get(0)));
            children.set(1, mutate(children.get(1)));
            newGeneration.addAll(children);
            currentGenerationSize += 2;
        }

        return newGeneration;
    }

    private List<SalesmanGenome> createInitialPopulation(){
        List<SalesmanGenome> initialPopulation = new ArrayList<>();
        for(int i = 0; i < populationSize; i++){
            initialPopulation.add(new SalesmanGenome(numberOfCities, travelPrices, startingCity));
        }

        return initialPopulation;
    }

    public SalesmanGenome optimize(){
        List<SalesmanGenome> population = createInitialPopulation();
        SalesmanGenome bestGenome = population.get(0);

        for(int i = 0; i < maxIterations; i++){
            List<SalesmanGenome> selectedPopulation = runSelection(population);
            population = createGeneration(selectedPopulation);
            bestGenome = Collections.min(population);

            if(bestGenome.getFitness() <= minFitness){
                break;
            }
        }

        return bestGenome;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
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

    public int[][] getTravelPrices() {
        return travelPrices;
    }

    public void setTravelPrices(int[][] travelPrices) {
        this.travelPrices = travelPrices;
    }

    public SelectionType getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }
}
