package hello.itemservice.validation;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject(){
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        Arrays.asList(messageCodes).forEach(x-> System.out.println("x = " + x));

        assertThat(messageCodes).containsExactly("required.item","required");

    }

    @Test
    void messageCodesResolverField(){
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        Arrays.asList(messageCodes).forEach(x-> System.out.println("x = " + x));

        assertThat(messageCodes).containsExactly("required.item.itemName","required.itemName","required.java.lang.String","required");
    }
}
