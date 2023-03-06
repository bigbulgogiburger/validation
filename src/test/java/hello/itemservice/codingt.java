package hello.itemservice;

import org.junit.jupiter.api.Test;

public class codingt {

    @Test
    public String test() {
        String answer = "Yes";
        String[] cards1 = new String[]{"i"};
        String[] cards2 = new String[]{"i"};
        String[] goal = new String[]{"i", "like"};
        int counter1 = 0;
        int counter2 = 0;

        for(String word : goal){
            if(cards1[counter1].equals(word)&&counter1<cards1.length-1){
                answer="Yes";
                counter1++;
            }else if(cards2[counter2].equals(word)){
                answer="Yes";
                counter2++;
            }else{
                return "No";
            }
        }

        return "Yes";

    }
}
