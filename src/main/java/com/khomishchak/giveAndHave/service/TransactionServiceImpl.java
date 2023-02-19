package com.khomishchak.giveAndHave.service;

import com.khomishchak.giveAndHave.model.Transaction;
import com.khomishchak.giveAndHave.model.User;
import com.khomishchak.giveAndHave.repository.TransactionRepository;
import com.khomishchak.giveAndHave.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    private TransactionRepository transactionRepository;
    private UserRepository userRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository) {

        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Transaction createTransaction(User sender, User receiver, int cost) {

        Calendar calendar = Calendar.getInstance();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Transaction transaction = Transaction.builder()
                .createdAt(timestamp)
                .cost(cost)
                .build();

        return submitTransaction(sender, receiver, transaction);
    }

    @Override
    public List<Transaction> getAllTransaction() {

        return transactionRepository.findAll();
    }

    private Transaction submitTransaction(User sender, User receiver, Transaction transaction)  {

        changeBalance(sender, receiver, transaction.getCost());

        userRepository.save(sender);
        userRepository.save(receiver);

        return transactionRepository.save(transaction);
    }

    private boolean changeBalance(User sender, User receiver, int amount) {
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        return true;
    }
}
