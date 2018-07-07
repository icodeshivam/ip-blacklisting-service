package com.icodeshivam.blacklisting.service;

import com.icodeshivam.blacklisting.exception.ValidationException;
import com.icodeshivam.blacklisting.model.*;
import org.apache.commons.net.util.SubnetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

@Service
public class IPFilteringServiceImpl implements IPFilteringService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IPFilteringServiceImpl.class);
    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    private List<IpRange> blacklistedIpRangeRegistry = new CopyOnWriteArrayList<>();

    @Override
    public void addBlacklistedIpRange(IpAddressMaskIpRange ipAddressMaskIpRange) {
        validateMaskRange(ipAddressMaskIpRange);
        blacklistedIpRangeRegistry.add(ipAddressMaskIpRange);
    }

    @Override
    public void addBlacklistedIpRange(CIDRIpRange cidrNotation) {
        validateCidrRange(cidrNotation);
        blacklistedIpRangeRegistry.add(cidrNotation);
    }

    @Override
    public boolean deleteBlacklistedIpRange(IpAddressMaskIpRange ipAddressMaskIpRange) {
        validateMaskRange(ipAddressMaskIpRange);
        return blacklistedIpRangeRegistry.remove(ipAddressMaskIpRange);
    }

    @Override
    public boolean deleteBlacklistedIpRange(CIDRIpRange cidrIpRange) {
        validateCidrRange(cidrIpRange);
        return blacklistedIpRangeRegistry.remove(cidrIpRange);
    }

    @Override
    public boolean isBlackListedIP(String ipAddress) {
        validateIp(ipAddress);
        boolean isBlacklisted = blacklistedIpRangeRegistry.stream()
                .anyMatch(((IpRange ipRange) -> {
                     return this.checkInRange(ipRange, ipAddress);
                }));


        return isBlacklisted;
    }

    private void validateIp(String ipAddress) {
        if(!PATTERN.matcher(ipAddress).matches()) {
            throw new ValidationException(ErrorCodes.INVALID_IP);
        }
    }

    private boolean checkInRange(IpRange ipRange, String ipAddress) {
        SubnetUtils.SubnetInfo subnetInfo = null;
        if(Notation.CIDR == ipRange.getNotation()) {
            CIDRIpRange cidrIpRange = (CIDRIpRange) ipRange;
            subnetInfo = new SubnetUtils(cidrIpRange.getCidrAddress()).getInfo();
        } else  {
            IpAddressMaskIpRange ipAddressMaskIpRange = (IpAddressMaskIpRange) ipRange;
            subnetInfo = new SubnetUtils(ipAddressMaskIpRange.getAddress(), ipAddressMaskIpRange.getMask()).getInfo();
        }
        return subnetInfo.isInRange(ipAddress);
    }

    private void validateMaskRange(IpAddressMaskIpRange ipAddressMaskIpRange) {
        try {
            new SubnetUtils(ipAddressMaskIpRange.getAddress(), ipAddressMaskIpRange.getMask());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException(ErrorCodes.INVALID_MASK_NOTATION);
        }
    }

    private void validateCidrRange(CIDRIpRange cidrIpRange) {
        try {
            new SubnetUtils(cidrIpRange.getCidrAddress());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException(ErrorCodes.INVALID_CIDR_NOTATION);
        }
    }
}
