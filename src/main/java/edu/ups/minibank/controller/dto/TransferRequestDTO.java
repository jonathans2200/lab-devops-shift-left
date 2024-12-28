package edu.ups.minibank.controller.dto;

import java.math.BigDecimal;

public record TransferRequestDTO(
        int destinyAccountId,
        BigDecimal amount
){}
