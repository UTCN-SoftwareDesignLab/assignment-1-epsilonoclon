package model.validation;

import model.Account;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class AccountValidator implements Validator
{
    private final Account account;
    private final List<String> errors;

    public AccountValidator(Account account)
    {
        this.account=account;
        errors = new ArrayList<>();
    }

    public boolean validate()
    {
        validateAmount(account.getAmount());
        validateCreation(account.getDateCreated());
        return errors.isEmpty();
    }

    private void validateAmount(Long amount)
    {
        if(amount<0)
            errors.add("Error: Nagative amount of money");
    }

    private void validateCreation(Date creation)
    {
        if(creation.after(new Date()))
            errors.add("Error: Date inconsistency");
    }

    public List<String> getErrors()
    {
        return errors;
    }
}
