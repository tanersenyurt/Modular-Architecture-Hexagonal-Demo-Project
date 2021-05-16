package com.hexagonaldemo.paymentapi.balance;

import com.hexagonaldemo.paymentapi.balance.command.BalanceCompensate;
import com.hexagonaldemo.paymentapi.balance.command.BalanceTransactionCreate;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.balance.model.BalanceTransactionType;
import com.hexagonaldemo.paymentapi.balance.port.BalancePort;
import com.hexagonaldemo.paymentapi.common.commandhandler.CommandHandler;
import com.hexagonaldemo.paymentapi.common.commandhandler.VoidCommandHandler;
import com.hexagonaldemo.paymentapi.payment.command.PaymentRollback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceCompensateCommandHandler implements VoidCommandHandler<BalanceCompensate> {

    private final BalancePort balancePort;

    @Override
    public void handle(BalanceCompensate balanceCompensate) {
        var balance = balancePort.retrieve(balanceCompensate.getAccountId());;
        balancePort.update(balance, BalanceTransactionCreate.builder()
                .accountId(balanceCompensate.getAccountId())
                .amount(balanceCompensate.getAmount())
                .type(BalanceTransactionType.COMPENSATE)
                .build());
    }
}