package com.icodeshivam.blacklisting.model;

import java.util.Objects;

public class CIDRIpRange implements IpRange{

    private Notation notation = Notation.CIDR;

    private String cidrAddress;

    public CIDRIpRange() {
    }

    public CIDRIpRange(String cidrAddress) {
        this.cidrAddress = cidrAddress;
    }

    public String getCidrAddress() {
        return cidrAddress;
    }

    public void setCidrAddress(String cidrAddress) {
        this.cidrAddress = cidrAddress;
    }

    @Override
    public Notation getNotation() {
        return notation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CIDRIpRange ipRange = (CIDRIpRange) o;
        return notation == ipRange.notation &&
                Objects.equals(cidrAddress, ipRange.cidrAddress);
    }

    @Override
    public int hashCode() {

        return Objects.hash(notation, cidrAddress);
    }
}
