package com.icodeshivam.blacklisting.service;

import com.icodeshivam.blacklisting.exception.ValidationException;
import com.icodeshivam.blacklisting.model.CIDRIpRange;
import com.icodeshivam.blacklisting.model.IpAddressMaskIpRange;
import com.icodeshivam.blacklisting.model.IpRange;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IPFilterSerivceTest {

    @Autowired
    private IPFilteringService ipFilteringService;

    @Test(expected = ValidationException.class)
    public void whenInvalidIp_exceptionThrown() {
        String ip = "garbage";
        ipFilteringService.isBlackListedIP(ip);
    }

    @Test(expected = ValidationException.class)
    public void whenInvalidCIDRIpRangeAdd_exceptionThrown() {
        CIDRIpRange cidrIpRange = new CIDRIpRange("garbage");
        ipFilteringService.addBlacklistedIpRange(cidrIpRange);
    }

    @Test(expected = ValidationException.class)
    public void whenInvalidCIDRIpRangeRemove_exceptionThrown() {
        CIDRIpRange cidrIpRange = new CIDRIpRange("garbage");
        ipFilteringService.deleteBlacklistedIpRange(cidrIpRange);
    }

    @Test(expected = ValidationException.class)
    public void whenInvalidMaskIpRangeAdd_exceptionThrown() {
        IpAddressMaskIpRange ipAddressMaskIpRange = new IpAddressMaskIpRange("garbage", "garbage");
        ipFilteringService.addBlacklistedIpRange(ipAddressMaskIpRange);
    }

    @Test(expected = ValidationException.class)
    public void whenInvalidMaskIpRangeRemove_exceptionThrown() {
        IpAddressMaskIpRange ipAddressMaskIpRange = new IpAddressMaskIpRange("garbage", "garbage");
        ipFilteringService.addBlacklistedIpRange(ipAddressMaskIpRange);
    }

    @Test
    public void whenBlacklistedIPCIDR_flowsucceeds() {
        ipFilteringService.addBlacklistedIpRange(new CIDRIpRange("192.168.0.0/23"));
        boolean blackListedIP = ipFilteringService.isBlackListedIP("192.168.1.1");
        Assert.assertTrue(blackListedIP);
    }

    @Test
    public void whenNotInRangeNotBlackListedCIDR_flowsucceeds() {
        ipFilteringService.addBlacklistedIpRange(new CIDRIpRange("192.168.0.0/23"));
        boolean blackListedIP = ipFilteringService.isBlackListedIP("192.167.1.1");
        Assert.assertTrue(!blackListedIP);
    }

    @Test
    public void whenBlacklistedIPMask_flowsucceeds() {
        ipFilteringService.addBlacklistedIpRange(new IpAddressMaskIpRange("192.168.0.0", "255.255.254.0"));
        boolean blackListedIP = ipFilteringService.isBlackListedIP("192.168.1.1");
        Assert.assertTrue(blackListedIP);
    }

    @Test
    public void whenNotInRangeNotBlackListedMask_flowsucceeds() {
        ipFilteringService.addBlacklistedIpRange(new IpAddressMaskIpRange("192.168.0.0", "255.255.254.0"));
        boolean blackListedIP = ipFilteringService.isBlackListedIP("192.167.1.1");
        Assert.assertTrue(!blackListedIP);
    }

    @Test
    public void whenCorrectIpRangeAdded_flowsucceeds() {
        ipFilteringService.addBlacklistedIpRange(new CIDRIpRange("192.168.0.0/23"));
        ipFilteringService.addBlacklistedIpRange(new IpAddressMaskIpRange("192.168.0.0", "255.255.254.0"));
        List<IpRange> allFilters = ipFilteringService.getAllFilters();
        Assert.assertTrue(allFilters.size() == 2);
    }

}
