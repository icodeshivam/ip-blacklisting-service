package com.icodeshivam.blacklisting.service;

import com.icodeshivam.blacklisting.model.CIDRIpRange;
import com.icodeshivam.blacklisting.model.IpAddressMaskIpRange;

public interface IPFilteringService {

    void addBlacklistedIpRange(IpAddressMaskIpRange ipAddressMaskIpRange);

    void addBlacklistedIpRange(CIDRIpRange cidrNotation);

    boolean deleteBlacklistedIpRange(IpAddressMaskIpRange ipAddressMaskIpRange);

    boolean deleteBlacklistedIpRange(CIDRIpRange cidrIpRange);

    boolean isBlackListedIP(String ipAddress);

}