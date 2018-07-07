package com.icodeshivam.blacklisting.service;

import com.icodeshivam.blacklisting.exception.ValidationException;
import com.icodeshivam.blacklisting.model.*;
import org.apache.commons.net.util.SubnetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class IPFilteringServiceImpl implements IPFilteringService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IPFilteringServiceImpl.class);

    private List<IpRange> blacklistedIpRangeRegistry = new CopyOnWriteArrayList<>();

    @Override
    public void addBlacklistedIpRange(IpAddressMaskIpRange ipAddressMaskIpRange) {
        validate(ipAddressMaskIpRange);
        blacklistedIpRangeRegistry.add(ipAddressMaskIpRange);
    }

    @Override
    public void addBlacklistedIpRange(CIDRIpRange cidrNotation) {
        validate(cidrNotation);
        blacklistedIpRangeRegistry.add(cidrNotation);
    }

    @Override
    public boolean deleteBlacklistedIpRange(IpAddressMaskIpRange ipAddressMaskIpRange) {
        validate(ipAddressMaskIpRange);
        return blacklistedIpRangeRegistry.remove(ipAddressMaskIpRange);
    }

    @Override
    public boolean deleteBlacklistedIpRange(CIDRIpRange cidrIpRange) {
        validate(cidrIpRange);
        return blacklistedIpRangeRegistry.remove(cidrIpRange);
    }

    @Override
    public boolean isBlackListedIP(String ipAddress) {
        boolean isBlacklisted = blacklistedIpRangeRegistry.stream()
                .anyMatch(((IpRange ipRange) -> {
                     return this.checkInRange(ipRange, ipAddress);
                }));


        return isBlacklisted;
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

    private void validate(IpAddressMaskIpRange ipAddressMaskIpRange) {
        try {
            new SubnetUtils(ipAddressMaskIpRange.getAddress(), ipAddressMaskIpRange.getMask());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException(ErrorCodes.INVALID_MASK_NOTATION);
        }
    }

    private void validate(CIDRIpRange cidrIpRange) {
        try {
            new SubnetUtils(cidrIpRange.getCidrAddress());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException(ErrorCodes.INVALID_CIDR_NOTATION);
        }
    }
}
