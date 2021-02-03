package com.sonia.java.bankcheckapplication.service.parser;

import com.sonia.java.bankcheckapplication.model.bank.discharge.BankDischarge;

import java.util.List;

public interface ResponseParser {

    public List<BankDischarge> parseDischarge(String parsableResponse);

    public float parseBalance(String parsableResponse);
}
