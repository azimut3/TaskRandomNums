package task;

import net.minidev.json.JSONArray;

import java.util.*;

public class Array {
    private static List<Long> numSet = new ArrayList<>();

    /**
     * Данный метод формирует выборку случайных чисел и проводит ее анализ
     * @param randomNumQtt размер выборки случайных чисел
     * @param numsInQuerry кол-во случайных чиселб которые можно получить с сервиса random.org
     *                     (до 20_000 включительно)
     * @param matchesAllowed параметр, определяющий вариант анализа "последовательностей"
     */
    public Array(int randomNumQtt, int numsInQuerry, boolean matchesAllowed){
        long start = Calendar.getInstance().getTimeInMillis();
        /**
         * Данное условие работает при условии что сервис не дает ограничений на кол-во запросов
         */
        /*while (numSet.size()<randomNumQtt){
            JSONArray arr = Json.callQuerry(numsInQuerry);
            for (Object i : arr){
                numSet.add((Long)i);
            }
        }*/
        /**
         * Данное условие работает в условиях ограниченного числа запросов
         */
        JSONArray arr = Json.callQuerry(numsInQuerry);
        while (numSet.size()<randomNumQtt){
            for (Object i : arr){
                numSet.add((Long)i);
            }
        }
        long finish = Calendar.getInstance().getTimeInMillis();
        //System.out.println("Общее время " + (finish-start));
        System.out.println("Выборка: ".concat(numSet.toString()));
        //System.out.println(numSet.size());
        System.out.println("Состояние полученной выборки: ");
        System.out.println(analyseListContent());
        System.out.println(analyseSet(matchesAllowed));
    }

    private static String analyseListContent(){
        String array = numSet.toString().replaceAll("\\D", "");
        int ones = array.replaceAll("0", "").length();
        int zeroes = array.length() - ones;
        double onesPercents = (double)Math.round(((double)ones/array.length())*10000)/100;
        double zeroesPercents = (double)100-onesPercents;
        StringBuilder builder = new StringBuilder();
        builder.append("Всего единиц - ").append(ones).append("(")
                .append(onesPercents).append("%)").append(System.lineSeparator())
                .append("Всего нулей - ").append(zeroes).append("(")
                .append(zeroesPercents).append("%)").append(System.lineSeparator());
        return builder.toString();
    }

    private static String analyseSet(boolean matchesAllowed){
        StringBuilder builder = new StringBuilder();
        if (matchesAllowed) {
            builder.append(checkSet(2)).append(System.lineSeparator());
            builder.append(checkSet(3)).append(System.lineSeparator());
        } else {
            builder.append(checkSetNoMatches(2)).append(System.lineSeparator());
            builder.append(checkSetNoMatches(3)).append(System.lineSeparator());
        }

        return builder.toString();
    }

    private static String checkSet(int sequenceNum){
        int beginIndex = 0;
        TreeMap<String, Integer> sequencesMap = new TreeMap<>();
        for (int endIndex = sequenceNum-1; endIndex<numSet.size(); endIndex++){
            StringBuilder numInstance = new StringBuilder();
            numInstance.append(numSet.get(beginIndex));
            int tempIndex = beginIndex++;
            for (int n = 0; n < sequenceNum-1; n++){
                numInstance.append(numSet.get(++tempIndex));
            }
            tempIndex = 0;
            //System.out.println(numInstance.toString());
            if (sequencesMap.containsKey(numInstance.toString())) {
                sequencesMap.put(numInstance.toString(), sequencesMap.get(numInstance.toString())+1);
            }
            else sequencesMap.put(numInstance.toString(), 1);
        }

        return getResults(sequencesMap);
    }

    private static String checkSetNoMatches(int sequenceNum){
        TreeMap<String, Integer> sequencesMap = new TreeMap<>();

        List<String> list = Permutation.getVariations(sequenceNum);
        for (int i = 0; i < list.size(); i++){
            String array = numSet.toString().replaceAll("\\D", "");
            String line = list.get(i);
            sequencesMap.put(line, (array.length() - array.replace(line, "").length())/line.length());
        }

        return getResults(sequencesMap);
    }

    public static String getResults(TreeMap<String, Integer> sequencesMap){
        StringBuilder results = new StringBuilder();
        int totalElementsInMap = 0;
        for (String num : sequencesMap.keySet()) {
            totalElementsInMap += sequencesMap.get(num);
        }
        //System.out.println(sequencesMap);
        for (String nums : sequencesMap.keySet()) {
            int numOfEntries = sequencesMap.get(nums);
            double percents = (double)Math.round( ( (double)numOfEntries/totalElementsInMap ) * 10000 )/100;
            results.append("Последовательность \"").append(nums).append("\" встречается ")
                    .append(numOfEntries).append(" (раз) - ")
                    .append(percents).append("%")
                    .append(System.lineSeparator());
        }
        return results.toString();
    }
}
