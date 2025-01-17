package com.tomo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tomo.model.dto.TokenRiskGoplusDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TokenRiskGoplusMapper extends BaseMapper<TokenRiskGoplusDTO> {

    @Insert({
            """
            INSERT INTO token_risk_goplus (
                chain_id, address, is_open_source, is_proxy, is_mintable, owner_address, can_take_back_ownership, owner_change_balance, hidden_owner, selfdestruct, external_call, gas_abuse, is_in_dex, buy_tax, sell_tax, cannot_buy, cannot_sell_all, slippage_modifiable, is_honeypot, transfer_pausable, is_blacklisted, is_whitelisted, dex, is_anti_whale, anti_whale_modifiable, trading_cooldown, personal_slippage_modifiable, holder_count, total_supply, holders, owner_balance, owner_percent, creator_address, creator_balance, creator_percent, lp_holder_count, lp_total_supply, lp_holders, is_true_token, is_airdrop_scam, trust_list, other_potential_risks, note, fake_token, metadata, default_account_state, non_transferable, creator, transfer_fee, transfer_hook, trusted_token, metadata_mutable, mintable, freezable, closable, default_account_state_upgradable, balance_mutable_authority, transfer_fee_upgradable, transfer_hook_upgradable, risk_level
            ) VALUES (
                #{tokenRiskGoplusDTO.chainId}, #{tokenRiskGoplusDTO.address}, #{tokenRiskGoplusDTO.isOpenSource}, #{tokenRiskGoplusDTO.isProxy}, #{tokenRiskGoplusDTO.isMintable}, #{tokenRiskGoplusDTO.ownerAddress}, #{tokenRiskGoplusDTO.canTakeBackOwnership}, #{tokenRiskGoplusDTO.ownerChangeBalance}, #{tokenRiskGoplusDTO.hiddenOwner}, #{tokenRiskGoplusDTO.selfdestruct}, #{tokenRiskGoplusDTO.externalCall}, #{tokenRiskGoplusDTO.gasAbuse}, #{tokenRiskGoplusDTO.isInDex}, #{tokenRiskGoplusDTO.buyTax}, #{tokenRiskGoplusDTO.sellTax}, #{tokenRiskGoplusDTO.cannotBuy}, #{tokenRiskGoplusDTO.cannotSellAll}, #{tokenRiskGoplusDTO.slippageModifiable}, #{tokenRiskGoplusDTO.isHoneypot}, #{tokenRiskGoplusDTO.transferPausable}, #{tokenRiskGoplusDTO.isBlacklisted}, #{tokenRiskGoplusDTO.isWhitelisted}, #{tokenRiskGoplusDTO.dex}, #{tokenRiskGoplusDTO.isAntiWhale}, #{tokenRiskGoplusDTO.antiWhaleModifiable}, #{tokenRiskGoplusDTO.tradingCooldown}, #{tokenRiskGoplusDTO.personalSlippageModifiable}, #{tokenRiskGoplusDTO.holderCount}, #{tokenRiskGoplusDTO.totalSupply}, #{tokenRiskGoplusDTO.holders}, #{tokenRiskGoplusDTO.ownerBalance}, #{tokenRiskGoplusDTO.ownerPercent}, #{tokenRiskGoplusDTO.creatorAddress}, #{tokenRiskGoplusDTO.creatorBalance}, #{tokenRiskGoplusDTO.creatorPercent}, #{tokenRiskGoplusDTO.lpHolderCount}, #{tokenRiskGoplusDTO.lpTotalSupply}, #{tokenRiskGoplusDTO.lpHolders}, #{tokenRiskGoplusDTO.isTrueToken}, #{tokenRiskGoplusDTO.isAirdropScam}, #{tokenRiskGoplusDTO.trustList}, #{tokenRiskGoplusDTO.otherPotentialRisks}, #{tokenRiskGoplusDTO.note}, #{tokenRiskGoplusDTO.fakeToken}, #{tokenRiskGoplusDTO.metadata}, #{tokenRiskGoplusDTO.defaultAccountState}, #{tokenRiskGoplusDTO.nonTransferable}, #{tokenRiskGoplusDTO.creator}, #{tokenRiskGoplusDTO.transferFee}, #{tokenRiskGoplusDTO.transferHook}, #{tokenRiskGoplusDTO.trustedToken}, #{tokenRiskGoplusDTO.metadataMutable}, #{tokenRiskGoplusDTO.mintable}, #{tokenRiskGoplusDTO.freezable}, #{tokenRiskGoplusDTO.closable}, #{tokenRiskGoplusDTO.defaultAccountStateUpgradable}, #{tokenRiskGoplusDTO.balanceMutableAuthority}, #{tokenRiskGoplusDTO.transferFeeUpgradable}, #{tokenRiskGoplusDTO.transferHookUpgradable}, #{tokenRiskGoplusDTO.riskLevel}
            )
            ON CONFLICT (chain_id, address) 
            DO UPDATE SET
                chain_id = COALESCE(#{tokenRiskGoplusDTO.chainId}, token_risk_goplus.chain_id),
                address = COALESCE(#{tokenRiskGoplusDTO.address}, token_risk_goplus.address),
                is_open_source = COALESCE(#{tokenRiskGoplusDTO.isOpenSource}, token_risk_goplus.is_open_source),
                is_proxy = COALESCE(#{tokenRiskGoplusDTO.isProxy}, token_risk_goplus.is_proxy),
                is_mintable = COALESCE(#{tokenRiskGoplusDTO.isMintable}, token_risk_goplus.is_mintable),
                owner_address = COALESCE(#{tokenRiskGoplusDTO.ownerAddress}, token_risk_goplus.owner_address),
                can_take_back_ownership = COALESCE(#{tokenRiskGoplusDTO.canTakeBackOwnership}, token_risk_goplus.can_take_back_ownership),
                owner_change_balance = COALESCE(#{tokenRiskGoplusDTO.ownerChangeBalance}, token_risk_goplus.owner_change_balance),
                hidden_owner = COALESCE(#{tokenRiskGoplusDTO.hiddenOwner}, token_risk_goplus.hidden_owner),
                selfdestruct = COALESCE(#{tokenRiskGoplusDTO.selfdestruct}, token_risk_goplus.selfdestruct),
                external_call = COALESCE(#{tokenRiskGoplusDTO.externalCall}, token_risk_goplus.external_call),
                gas_abuse = COALESCE(#{tokenRiskGoplusDTO.gasAbuse}, token_risk_goplus.gas_abuse),
                is_in_dex = COALESCE(#{tokenRiskGoplusDTO.isInDex}, token_risk_goplus.is_in_dex),
                buy_tax = COALESCE(#{tokenRiskGoplusDTO.buyTax}, token_risk_goplus.buy_tax),
                sell_tax = COALESCE(#{tokenRiskGoplusDTO.sellTax}, token_risk_goplus.sell_tax),
                cannot_buy = COALESCE(#{tokenRiskGoplusDTO.cannotBuy}, token_risk_goplus.cannot_buy),
                cannot_sell_all = COALESCE(#{tokenRiskGoplusDTO.cannotSellAll}, token_risk_goplus.cannot_sell_all),
                slippage_modifiable = COALESCE(#{tokenRiskGoplusDTO.slippageModifiable}, token_risk_goplus.slippage_modifiable),
                is_honeypot = COALESCE(#{tokenRiskGoplusDTO.isHoneypot}, token_risk_goplus.is_honeypot),
                transfer_pausable = COALESCE(#{tokenRiskGoplusDTO.transferPausable}, token_risk_goplus.transfer_pausable),
                is_blacklisted = COALESCE(#{tokenRiskGoplusDTO.isBlacklisted}, token_risk_goplus.is_blacklisted),
                is_whitelisted = COALESCE(#{tokenRiskGoplusDTO.isWhitelisted}, token_risk_goplus.is_whitelisted),
                dex = COALESCE(#{tokenRiskGoplusDTO.dex}, token_risk_goplus.dex),
                is_anti_whale = COALESCE(#{tokenRiskGoplusDTO.isAntiWhale}, token_risk_goplus.is_anti_whale),
                anti_whale_modifiable = COALESCE(#{tokenRiskGoplusDTO.antiWhaleModifiable}, token_risk_goplus.anti_whale_modifiable),
                trading_cooldown = COALESCE(#{tokenRiskGoplusDTO.tradingCooldown}, token_risk_goplus.trading_cooldown),
                personal_slippage_modifiable = COALESCE(#{tokenRiskGoplusDTO.personalSlippageModifiable}, token_risk_goplus.personal_slippage_modifiable),
                holder_count = COALESCE(#{tokenRiskGoplusDTO.holderCount}, token_risk_goplus.holder_count),
                total_supply = COALESCE(#{tokenRiskGoplusDTO.totalSupply}, token_risk_goplus.total_supply),
                holders = COALESCE(#{tokenRiskGoplusDTO.holders}, token_risk_goplus.holders),
                owner_balance = COALESCE(#{tokenRiskGoplusDTO.ownerBalance}, token_risk_goplus.owner_balance),
                owner_percent = COALESCE(#{tokenRiskGoplusDTO.ownerPercent}, token_risk_goplus.owner_percent),
                creator_address = COALESCE(#{tokenRiskGoplusDTO.creatorAddress}, token_risk_goplus.creator_address),
                creator_balance = COALESCE(#{tokenRiskGoplusDTO.creatorBalance}, token_risk_goplus.creator_balance),
                creator_percent = COALESCE(#{tokenRiskGoplusDTO.creatorPercent}, token_risk_goplus.creator_percent),
                lp_holder_count = COALESCE(#{tokenRiskGoplusDTO.lpHolderCount}, token_risk_goplus.lp_holder_count),
                lp_total_supply = COALESCE(#{tokenRiskGoplusDTO.lpTotalSupply}, token_risk_goplus.lp_total_supply),
                lp_holders = COALESCE(#{tokenRiskGoplusDTO.lpHolders}, token_risk_goplus.lp_holders),
                is_true_token = COALESCE(#{tokenRiskGoplusDTO.isTrueToken}, token_risk_goplus.is_true_token),
                is_airdrop_scam = COALESCE(#{tokenRiskGoplusDTO.isAirdropScam}, token_risk_goplus.is_airdrop_scam),
                trust_list = COALESCE(#{tokenRiskGoplusDTO.trustList}, token_risk_goplus.trust_list),
                other_potential_risks = COALESCE(#{tokenRiskGoplusDTO.otherPotentialRisks}, token_risk_goplus.other_potential_risks),
                note = COALESCE(#{tokenRiskGoplusDTO.note}, token_risk_goplus.note),
                fake_token = COALESCE(#{tokenRiskGoplusDTO.fakeToken}, token_risk_goplus.fake_token),
                metadata = COALESCE(#{tokenRiskGoplusDTO.metadata}, token_risk_goplus.metadata),
                default_account_state = COALESCE(#{tokenRiskGoplusDTO.defaultAccountState}, token_risk_goplus.default_account_state),
                non_transferable = COALESCE(#{tokenRiskGoplusDTO.nonTransferable}, token_risk_goplus.non_transferable),
                creator = COALESCE(#{tokenRiskGoplusDTO.creator}, token_risk_goplus.creator),
                transfer_fee = COALESCE(#{tokenRiskGoplusDTO.transferFee}, token_risk_goplus.transfer_fee),
                transfer_hook = COALESCE(#{tokenRiskGoplusDTO.transferHook}, token_risk_goplus.transfer_hook),
                trusted_token = COALESCE(#{tokenRiskGoplusDTO.trustedToken}, token_risk_goplus.trusted_token),
                metadata_mutable = COALESCE(#{tokenRiskGoplusDTO.metadataMutable}, token_risk_goplus.metadata_mutable),
                mintable = COALESCE(#{tokenRiskGoplusDTO.mintable}, token_risk_goplus.mintable),
                freezable = COALESCE(#{tokenRiskGoplusDTO.freezable}, token_risk_goplus.freezable),
                closable = COALESCE(#{tokenRiskGoplusDTO.closable}, token_risk_goplus.closable),
                default_account_state_upgradable = COALESCE(#{tokenRiskGoplusDTO.defaultAccountStateUpgradable}, token_risk_goplus.default_account_state_upgradable),
                balance_mutable_authority = COALESCE(#{tokenRiskGoplusDTO.balanceMutableAuthority}, token_risk_goplus.balance_mutable_authority),
                transfer_fee_upgradable = COALESCE(#{tokenRiskGoplusDTO.transferFeeUpgradable}, token_risk_goplus.transfer_fee_upgradable),
                transfer_hook_upgradable = COALESCE(#{tokenRiskGoplusDTO.transferHookUpgradable}, token_risk_goplus.transfer_hook_upgradable),
                risk_level = COALESCE(#{tokenRiskGoplusDTO.riskLevel}, token_risk_goplus.risk_level)
            """
    })
    boolean insertOrUpdate(@Param("tokenRiskGoplusDTO") TokenRiskGoplusDTO tokenRiskGoplusDTO);
}