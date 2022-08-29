import java.util.*;

public class Main {
    public static void main(String[] args) {

        System.out.println("==== Homework 1 ====\n");

        List<Map<String, String>> animalList = List.of(
                Map.of("name", "shark", "habitat", "ocean"),
                Map.of("name", "bear", "habitat", "land"),
                Map.of("name", "moose", "habitat", "land"),
                Map.of("name", "frog", "habitat", "swamp"),
                Map.of("name", "jelly fish", "habitat", "ocean"),
                Map.of("name", "heron", "habitat", "swamp"),
                Map.of("name", "whale", "habitat", "ocean")
        );
        Map<String, List<String>> result = new HashMap<>();

        for (Map<String, String> map : animalList) {
            String name = map.get("name");
            String habitat = map.get("habitat");
            if (!result.containsKey(habitat)) {
                result.put(habitat, new ArrayList<>());
            }
            result.get(habitat).add(name);
        }

        for (Map.Entry<String, List<String>> entry : result.entrySet()) {
            String habitat = entry.getKey();
            List<String> nameList = entry.getValue();
            System.out.println(habitat + ": " + String.join(", ", nameList));
        }

        System.out.println("\n==== Homework 2 ====\n");

        List<String> capitalList = List.of(
                "USA", "Washington",
                "Japan", "Tokyo",
                "Thailand", "Bangkok",
                "UK", "London",
                "Australia", "Canberra",
                "Denmark", "Copenhagen",
                "Egypt", "Cairo",
                "Vietnam", "Hanoi",
                "Italy", "Rome",
                "Brazil", "Brazilia"
        );
        Map<String, String> capitalMap = new LinkedHashMap<>();
        for (int i = 0; i < capitalList.size(); i = i + 2) {
            capitalMap.put(capitalList.get(i), capitalList.get(i + 1));
        }

        for (Map.Entry<String, String> entry : capitalMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }
}