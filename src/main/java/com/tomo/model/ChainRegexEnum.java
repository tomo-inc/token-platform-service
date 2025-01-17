package com.tomo.model;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public enum ChainRegexEnum {
    EVM("evm", "^0x[a-fA-F0-9]{40}$"),
    BTC("btc", "^(bc1|[13])[a-zA-HJ-NP-Z0-9]{25,39}$"),
    SOLANA("solana", "[1-9A-HJ-NP-Za-km-z]{32,44}"),
    TON("ton", "^(0|-1):[a-fA-F0-9]{64}|^[a-zA-Z0-9_\\-\\\\]{48}$"),
    TRON("tron", "T[A-Za-z1-9]{33}$"),
    SUI("sui", "0x[a-fA-F0-9]{64}::.+::.+$"),
    COSMOS("cosmos", "^cosmos1[a-z0-9]{38}$"),
    DOGE("doge","^D{1}[5-9A-HJ-NP-Ua-km-z1-9]{25,34}$");
    @EnumValue
    private final String value;

    private final String regex;

    ChainRegexEnum(String value, String regex) {
        this.value = value;
        this.regex = regex;
    }

    public static boolean isEVM(String address) {
        Pattern pattern = Pattern.compile(EVM.getRegex());
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }

    public static boolean isAddress(String nameOrAddress) {
        for (ChainRegexEnum value : ChainRegexEnum.values()) {
            Pattern pattern = Pattern.compile(value.getRegex());
            Matcher matcher = pattern.matcher(nameOrAddress);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }
}