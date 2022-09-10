import java.util.Collections;
import java.util.List;

public class SelectionUtils {
    public static <E> List<E> getNRandomGenomes(List<E> population, int numberOfGenomes){
        int length = population.size();
        List<E> selectedGenomes = null;

        if(length >= numberOfGenomes){
            for(int i=0; i<=numberOfGenomes; i++){
                Collections.swap(population, i, getRandomNumberInRange(0, length - 1));
            }

            selectedGenomes = population.subList(0, numberOfGenomes -1);
        }

        return selectedGenomes;
    }

    public static int getRandomNumberInRange(int min, int max){
       return (int) ((Math.random() * (max - min)) + min);
    }
}
