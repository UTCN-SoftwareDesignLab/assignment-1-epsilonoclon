package service.account;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.validation.AccountValidator;
import model.validation.Notification;
import model.validation.TransactionValidator;
import model.validation.Validator;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;
import service.client.ClientService;

import java.util.Date;
import java.util.List;

public class AccountServiceSQL implements AccountService {

    private final AccountRepository repository;
    private final ClientService clientService;

    public AccountServiceSQL(AccountRepository repository, ClientService clientService)
    {
        this.repository = repository;
        this.clientService = clientService;
    }

    @Override
    public List<Account> findAll()
    {
        return repository.findAll();
    }

    @Override
    public Notification<Account> findById(Long id)
    {/*
        Notification<Account> notification=new Notification<>();
        try {
            notification.setResult(AccountRepository.findById(id));
            return notification;
        }catch(EntityNotFoundException e)
        {
            notification.addError(e.getMessage());
            return notification;
        }*/
        return null; //idk why FindById needs to be static >_> I AM TOO STUPID TO GRASP THE WHOLE OOP CONCEPT I WILL NEVER SURVIVE THIS AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        //investigate further 
    }

    @Override
    public Notification<Account> save(String type, Long amount, String clientCNP, Date creation)
    {
        Account account=new AccountBuilder().setType(type).setAmount(amount).setDateCreated(creation).build();
        Validator accountValidator=new AccountValidator(account);

        Notification<Account> accountAddingNotification=new Notification<>();

        Notification<Client> clientFindNotification= findByCnp(clientCNP);

        if(clientFindNotification.hasErrors()) {
            accountValidator.getErrors().forEach(accountAddingNotification::addError);
            return accountAddingNotification;
        }
        boolean accountValid = accountValidator.validate();

        if (!accountValid) {
            accountValidator.getErrors().forEach(accountAddingNotification::addError);
        } else {
            accountAddingNotification.setResult(repository.save(account,clientFindNotification.getResult().getId()).getResult());
        }
        return accountAddingNotification;
    }

    private Notification<Client> findByCnp(String clientCNP) {
        return clientService.findByCnp(clientCNP);
    }

    @Override
    public Notification<Boolean> update(Long id, String type, Long balance, Date creation) {
        Account account=new AccountBuilder().setType(type).setAmount(balance).setDateCreated(creation).build();
        Validator accountValidator=new AccountValidator(account);

        boolean accountValid = accountValidator.validate();
        Notification<Boolean> accountAddingNotification = new Notification<>();

        if (!accountValid) {
            accountValidator.getErrors().forEach(accountAddingNotification::addError);
            accountAddingNotification.setResult(Boolean.FALSE);
        } else {
            accountAddingNotification.setResult(repository.update(id,account));
        }
        return accountAddingNotification;
    }

    @Override
    public Notification<Boolean> transfer(Long receiverId, Long senderId, int sum) {
        Notification<Boolean> transferNotification=new Notification<>();
        transferNotification.setResult(false);

        try {
            Account sender=repository.findById(senderId);

            Validator subtractionValidator=new TransactionValidator(sender,sum);
            if(subtractionValidator.validate())
            {
                sender.setAmount(sender.getAmount()-sum);
                if(!repository.update(senderId,sender)) {
                    transferNotification.addError("Error: can't update sender");
                    sender.setAmount(sender.getAmount() + sum);
                }
                else
                {
                    Account receiver=repository.findById(receiverId);
                    receiver.setAmount(receiver.getAmount()+sum);
                    if(!repository.update(receiverId,receiver))
                    {
                        transferNotification.addError("Error: can't update receiver");
                        receiver.setAmount(receiver.getAmount()-sum);
                        sender.setAmount(sender.getAmount()+sum);
                        repository.update(senderId,sender);
                    }
                    else
                        transferNotification.setResult(true);
                }
            }
            else
            {
                subtractionValidator.getErrors().forEach(transferNotification::addError);
                transferNotification.setResult(Boolean.FALSE);
            }


        } catch (EntityNotFoundException e) {
            transferNotification.addError("Accounts don't exist, possible database problems!");
        }
        return transferNotification;
    }

    @Override
    public boolean remove(Long id) {
        return repository.remove(id);
    }

    @Override
    public List<Account> findClientsAccounts(Long clientId) {
        return repository.findAccountsByOwner(clientId);
    }
}
