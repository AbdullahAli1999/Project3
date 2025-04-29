package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    //get
    public List<Account> getAllAccount(){
        return accountRepository.findAll();
    }

    //ADD
    public void addAccount(Integer customer_id,Account account){
        Customer customer = customerRepository.findCustomerById(customer_id);
        if(customer == null){
            throw new ApiException("Customer not found");
        }
        account.setCustomer(customer);
        accountRepository.save(account);
    }

    //Active Account by employee
    public void activeAccount(Integer account_id){
        Account account = accountRepository.findAccountById(account_id);
        if(account == null){
            throw new ApiException("Account not found");
        }
        if(account.getIsActive()){
            throw new ApiException("Account is Active");
        }
        account.setIsActive(true);
        accountRepository.save(account);
    }

    //Deposit by customer
    public void deposit(Integer account_id,Double amount){
        Account account = accountRepository.findAccountById(account_id);
        if(account == null){
            throw new ApiException("Account not found");
        }
        if(!account.getIsActive()){
            throw new ApiException("Account must be active");
        }
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    //Withdraw by customer
    public void withdraw(Integer account_id,Double amount){
        Account account = accountRepository.findAccountById(account_id);
        if(account == null){
            throw new ApiException("Account not found");
        }
        if(account.getBalance() < amount){
            throw new ApiException("Not enough money");
        }
        if(!account.getIsActive()){
            throw new ApiException("Account must be active");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

    }

    //Transfer funds between accounts

    public void transferMoney(Integer account1_id,Integer account2_id,Double amount){
        Account sender = accountRepository.findAccountById(account1_id);
        if(sender == null){
            throw new ApiException("Sender not found");
        }
        if(sender.getBalance() < amount){
            throw new ApiException("Not enough money");
        }

        Account receiver = accountRepository.findAccountById(account2_id);
        if(receiver == null){
            throw new ApiException("Receiver not found");
        }
        if(!sender.getIsActive() || !receiver.getIsActive()){
            throw new ApiException("must be active");

        }
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        accountRepository.save(sender);
        accountRepository.save(receiver);

    }

    //block Account
    public void blockAccount(Integer account_id){
        Account blockAccount = accountRepository.findAccountById(account_id);
        if(blockAccount == null){
            throw new ApiException("Account not found");
        }
        if(!blockAccount.getIsActive()){
            throw new ApiException("is blocked");
        }
        blockAccount.setIsActive(false);
        accountRepository.save(blockAccount);
    }

    //get my account
    public Set<Account> getMyAccount(Integer customer_id){
        Customer customer = customerRepository.findCustomerById(customer_id);
        if(customer == null){
            throw new ApiException("Customer not found");
        }
        return customer.getAccounts();
    }


}
