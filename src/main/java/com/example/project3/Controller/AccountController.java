package com.example.project3.Controller;

import com.example.project3.Api.ApiResponse;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    //get my account
    @GetMapping("/myAccount")
    public ResponseEntity getMyAccount(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(accountService.getMyAccount(user.getCustomer().getId()));
    }

    //GET
    @GetMapping("/get")
    public ResponseEntity getAllAccount(){
        return ResponseEntity.status(200).body(accountService.getAllAccount());
    }

    //ADD
    @PostMapping("/add")
    public ResponseEntity addAccount(@AuthenticationPrincipal User user, @RequestBody @Valid Account account){
        accountService.addAccount(user.getCustomer().getId(),account);
        return ResponseEntity.status(200).body(new ApiResponse("Account Added"));
    }

    //Active Account
    @PutMapping("/active/{account_id}")
    public ResponseEntity activeAccount(@PathVariable Integer account_id){
        accountService.activeAccount(account_id);
        return ResponseEntity.status(200).body(new ApiResponse("Account activated"));
    }

    //deposit
    @PostMapping("/deposit/{account_id}/{amount}")
    public ResponseEntity deposit(@PathVariable Integer account_id,@PathVariable Double amount){
        accountService.deposit(account_id,amount);
        return ResponseEntity.status(200).body(new ApiResponse("Deposit : " + amount));
    }

    //withdraw
    @PostMapping("/withdraw/{account_id}/{amount}")
    public ResponseEntity withdraw(@PathVariable Integer account_id,@PathVariable Double amount){
        accountService.withdraw(account_id,amount);
        return ResponseEntity.status(200).body(new ApiResponse(amount + "Withdrawn"));
    }

    //transfer money
    @PutMapping("/transfer/{id1}/{id2}/{amount}")
    public ResponseEntity transferMoney(@PathVariable Integer id1,@PathVariable Integer id2,@PathVariable Double amount){
        accountService.transferMoney(id1, id2, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Transfer done"));
    }
    @PutMapping("/block/{id}")
    public ResponseEntity blockAccount(@PathVariable Integer id){
        accountService.blockAccount(id);
        return ResponseEntity.status(200).body(new ApiResponse("Account Blocked"));
    }

}
