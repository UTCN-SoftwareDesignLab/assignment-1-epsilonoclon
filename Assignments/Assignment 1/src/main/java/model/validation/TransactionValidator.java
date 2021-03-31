package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.List;

public class TransactionValidator implements Validator
{
    private final Account account;
    private final int sum;
    private final List<String> errors;

    public TransactionValidator(Account account, int sum)
    {
        this.account=account;
        this.sum=sum;
        errors = new ArrayList<>();
    }
    @Override
    public boolean validate() {
        validateSum();
        validateFunds();
        return errors.isEmpty();
    }

    private void validateSum()
    {
        if(sum<=0)
            errors.add("I am sorry you are in such terrible debt, friend");
    }
    private void validateFunds()
    {
        if(account.getAmount()-sum<0)
            errors.add("Insufficient funds!");
    }
    @Override
    public List<String> getErrors() {
        return errors;
    }
}
