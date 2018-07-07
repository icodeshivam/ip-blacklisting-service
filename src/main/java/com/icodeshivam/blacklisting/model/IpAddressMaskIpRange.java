package com.icodeshivam.blacklisting.model;

import java.util.Objects;

public class IpAddressMaskIpRange implements IpRange{

    private Notation notation = Notation.MASK_ADDRESS;

    private String address;
    private String mask;

    public IpAddressMaskIpRange() {
    }

    public IpAddressMaskIpRange(String address, String mask) {
        this.address = address;
        this.mask = mask;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    @Override
    public Notation getNotation() {
        return notation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpAddressMaskIpRange that = (IpAddressMaskIpRange) o;
        return notation == that.notation &&
                Objects.equals(address, that.address) &&
                Objects.equals(mask, that.mask);
    }

    @Override
    public int hashCode() {

        return Objects.hash(notation, address, mask);
    }
}
