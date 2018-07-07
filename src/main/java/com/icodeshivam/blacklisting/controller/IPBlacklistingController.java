package com.icodeshivam.blacklisting.controller;

import com.icodeshivam.blacklisting.model.CIDRIpRange;
import com.icodeshivam.blacklisting.model.IpAddressMaskIpRange;
import com.icodeshivam.blacklisting.model.IpRange;
import com.icodeshivam.blacklisting.service.IPFilteringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ipblacklisting")
public class IPBlacklistingController {

    @Autowired
    private IPFilteringService ipFilteringService;

    @RequestMapping("/addCidrFilter")
    @PostMapping
    public ResponseEntity<Void> addCIDRFilter(@RequestBody CIDRIpRange cidrNotation) {
        ipFilteringService.addBlacklistedIpRange(cidrNotation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/addMaskFilter")
    @PostMapping
    public ResponseEntity<Void> addMaskFilter(@RequestBody IpAddressMaskIpRange ipAddressMaskIpRange) {
        ipFilteringService.addBlacklistedIpRange(ipAddressMaskIpRange);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/removeCidrFilter")
    @DeleteMapping
    public ResponseEntity<Void> deleteCIDRFilter(@RequestBody CIDRIpRange cidrNotation) {
        ipFilteringService.deleteBlacklistedIpRange(cidrNotation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/removeMaskFilter")
    @DeleteMapping
    public ResponseEntity<Void> deleteMaskFilter(@RequestBody IpAddressMaskIpRange ipAddressMaskIpRange) {
        ipFilteringService.deleteBlacklistedIpRange(ipAddressMaskIpRange);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/isBlacklisted")
    @GetMapping
    public ResponseEntity<Boolean> checkIfBlacklistedIp(@RequestParam String ip) {
        boolean blackListedIP = ipFilteringService.isBlackListedIP(ip);
        return new ResponseEntity<>(blackListedIP, HttpStatus.OK);
    }

    @RequestMapping("/allFilters")
    @GetMapping
    @ResponseBody
    public List<IpRange> getAllFilters() {
        return ipFilteringService.getAllFilters();
    }



}
