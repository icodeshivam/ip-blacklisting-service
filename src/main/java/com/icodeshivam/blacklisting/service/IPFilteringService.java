package com.icodeshivam.blacklisting.service;

import com.icodeshivam.blacklisting.model.CIDRIpRange;
import com.icodeshivam.blacklisting.model.IpAddressMaskIpRange;
import com.icodeshivam.blacklisting.model.IpRange;

import java.util.List;
import java.util.Set;

public interface IPFilteringService {

    void addBlacklistedIpRange(IpRange ipRange);

    boolean deleteBlacklistedIpRange(IpRange ipRange);

    boolean isBlackListedIP(String ipAddress);

    Set<IpRange> getAllFilters();
}