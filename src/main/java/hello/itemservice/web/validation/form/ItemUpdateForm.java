package hello.itemservice.web.validation.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
public class ItemUpdateForm {

    @NotNull
    private Long id;
    @NotNull
    private String itemName;

    @NotNull
    @Range(min=1000,max = 1000000)
    private Integer price;

    //수정에서는 수량은 자유롭게 변경 가능하다.
    private Integer quantity;
}
