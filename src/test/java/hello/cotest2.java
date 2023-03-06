package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class cotest2 {

    public int[] solution(String[] keymap, String[] targets) {
        int[] answer = new int[targets.length];

        ArrayList<HashMap<Character,Integer>> keyPointer = new ArrayList<>();

        for (String s : keymap) {
            HashMap<Character, Integer> characterIntegerHashMap = new HashMap<>();
            char[] chars = s.toCharArray();
            for(int i = chars.length-1; i>=0 ; i--){
                characterIntegerHashMap.put(chars[i],i+1);
            }
            keyPointer.add(characterIntegerHashMap);
        }
        keyPointer.forEach(x-> System.out.println("x.values() = " + x.values()));
        
        HashMap<Character,Integer> sortedMap = new HashMap<>();
        for (HashMap<Character, Integer> characterIntegerHashMap : keyPointer) {
            for (Character character : characterIntegerHashMap.keySet()) {
                Integer defval = sortedMap.getOrDefault(character, -1);
                if(defval == -1||defval>characterIntegerHashMap.get(character)){
                    sortedMap.put(character,characterIntegerHashMap.get(character));
                }
            }
        }


        for(int i=0;i< targets.length;i++){
            char[] targetCharArray = targets[i].toCharArray();
            for(int k = 0; k<targetCharArray.length;k++){
                int part = 0;
                for(char a :targetCharArray){
                    int index = sortedMap.getOrDefault(a,-1);
                    if(index==-1){
                        part = -1;
                        break;
                    }else{
                        part+=index;
                    }
                }
                answer[i] = part;
            }
        }



        return answer;
    }
}
