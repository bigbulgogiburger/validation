package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import hello.itemservice.domain.item.SaveCheck;
import hello.itemservice.domain.item.UpdateCheck;
import hello.itemservice.web.validation.form.ItemSaveForm;
import hello.itemservice.web.validation.form.ItemUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
@Slf4j
public class ValidationItemControllerV4 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;


    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v4/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v4/addForm";
    }


    @PostMapping("/add")
    // modelattribute에 name 써주는거 진짜 중요
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
//        itemValidator.validate(item,bindingResult);
        //특정 필드가 아닌 복합 룰 검증
        if(form.getPrice()!=null && form.getQuantity()!=null){
            int resultPrice = form.getPrice() * form.getQuantity();
            if(resultPrice<10000){
                bindingResult.addError(new ObjectError("item","가격*수량의 합은 10,000원 이상이여야합니다. 현재 값 = " +resultPrice));

            }
        }

        if(bindingResult.hasErrors()){
            log.info("errors : {}",bindingResult);
            return "validation/v4/addForm";
        }

        Item item = new Item();
        item.setItemName(form.getItemName());
        item.setPrice(form.getPrice());
        item.setQuantity(form.getQuantity());
        //성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/editForm";
    }

//    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Valid @ModelAttribute Item item, BindingResult bindingResult) {

        //특정 필드가 아닌 복합 룰 검증
        if(item.getPrice()!=null && item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.addError(new ObjectError("item","가격*수량의 합은 10,000원 이상이여야합니다. 현재 값 = " +resultPrice));

            }
        }

        if(bindingResult.hasErrors()){
            log.info("errors : {}",bindingResult);
            return "validation/v4/editForm";
        }
        itemRepository.update(itemId, item);
        return "redirect:/validation/v4/items/{itemId}";
    }

    @PostMapping("/{itemId}/edit")
    public String editV2(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateForm form, BindingResult bindingResult) {

        //특정 필드가 아닌 복합 룰 검증
        if(form.getPrice()!=null && form.getQuantity()!=null){
            int resultPrice = form.getPrice() * form.getQuantity();
            if(resultPrice<10000){
                bindingResult.addError(new ObjectError("item","가격*수량의 합은 10,000원 이상이여야합니다. 현재 값 = " +resultPrice));

            }
        }

        Item itemParam = new Item();

        itemParam.setItemName(form.getItemName());
        itemParam.setPrice(form.getPrice());
        itemParam.setQuantity(form.getQuantity());

        if(bindingResult.hasErrors()){
            log.info("errors : {}",bindingResult);
            return "validation/v4/editForm";
        }
        itemRepository.update(itemId, itemParam);
        return "redirect:/validation/v4/items/{itemId}";
    }

}

