package com.example.myapp.controller;

import com.example.myapp.ds.BookDto;
import com.example.myapp.ds.Customer;
import com.example.myapp.service.CartService;
import com.example.myapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CartService cartService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError",true    );
        return "login";
    }

    private List<Integer> bookQuantityList;
    @RequestMapping("/customer/register")
    public String register(Model model, BookDto bookDto){
        this.bookQuantityList=bookDto.getBookNumberList();
       // System.out.println("==========="+bookQuantityList);
        model.addAttribute("customer",new Customer());
        return "register";
    }

    @PostMapping("/customer/save-customer")
    public String saveCustomer(Customer customer, BindingResult result){
        Set<BookDto> bookDtoSet=cartService.listCart();
        int index = 0;
        for (BookDto bookDto : bookDtoSet) {
            bookDto.setQuantity(this.bookQuantityList.get(index));
            index++;
        }

        if(result.hasErrors()){
            return "register";
        }
        Customer exitingCustomer=customerService.findCustomerByName(customer.getName());
        if(exitingCustomer==null){
            customerService.register(customer,bookDtoSet);
        }else{
            customerService.saveCustomerBookOrder(bookDtoSet,exitingCustomer);
        }
        cartService.clearCart();
        return "redirect:/login";
    }
}
