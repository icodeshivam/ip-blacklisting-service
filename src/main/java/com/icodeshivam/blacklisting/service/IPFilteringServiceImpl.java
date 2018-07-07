package com.icodeshivam.blacklisting.service;

import com.icodeshivam.blacklisting.exception.ValidationException;
import com.icodeshivam.blacklisting.model.*;
import org.apache.commons.net.util.SubnetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Pattern;

@Service
public class IPFilteringServiceImpl implements IPFilteringService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IPFilteringServiceImpl.class);
    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    private Set<IpRange> blacklistedIpRangeRegistry = new CopyOnWriteArraySet<>();

    @Override
    public void addBlacklistedIpRange(IpRange ipRange) {
        validateAndGetSubnetInfo(ipRange);
        blacklistedIpRangeRegistry.add(ipRange);
    }

    @Override
    public boolean deleteBlacklistedIpRange(IpRange ipAddressMaskIpRange) {
        validateAndGetSubnetInfo(ipAddressMaskIpRange);
        return blacklistedIpRangeRegistry.remove(ipAddressMaskIpRange);
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

    @Override
    public Set<IpRange> getAllFilters() {
        return blacklistedIpRangeRegistry;
    }

    private void validateIp(String ipAddress) {
        if(!PATTERN.matcher(ipAddress).matches()) {
            throw new ValidationException(ErrorCodes.INVALID_IP);
        }
    }

    private boolean checkInRange(IpRange ipRange, String ipAddress) {
        SubnetUtils.SubnetInfo subnetInfo = validateAndGetSubnetInfo(ipRange);
        boolean isBlacklisted = false;
        if(null != subnetInfo) {
            isBlacklisted = subnetInfo.isInRange(ipAddress);
        }
        return isBlacklisted;
    }

    private SubnetUtils.SubnetInfo validateAndGetSubnetInfo(IpRange ipRange) {
        SubnetUtils.SubnetInfo subnetInfo = null;
        try {
            if(Notation.CIDR == ipRange.getNotation()) {
                CIDRIpRange cidrIpRange = (CIDRIpRange) ipRange;
                subnetInfo = new SubnetUtils(cidrIpRange.getCidrAddress()).getInfo();
            } else  {
                IpAddressMaskIpRange ipAddressMaskIpRange = (IpAddressMaskIpRange) ipRange;
                subnetInfo = new SubnetUtils(ipAddressMaskIpRange.getAddress(), ipAddressMaskIpRange.getMask()).getInfo();
            }
        } catch (IllegalArgumentException ex) {
            throw new ValidationException(ErrorCodes.INVALID_MASK_NOTATION);
        }
        return subnetInfo;
    }
}
