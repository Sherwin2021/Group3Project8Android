package model;

/*
 *   Krzysztof Stalmach
 *   Project 8 - CMPP-264 Android
 *   Agency class
 */


import java.io.Serializable;

public class Agency implements Serializable
{

    private Integer agencyId;

    private String agncyAddress;

    private String agncyCity;

    private String agncyProv;

    private String agncyPostal;

    private String agncyCountry;

    private String agncyPhone;

    private String agncyFax;


    public Agency(Integer agencyId, String agncyAddress, String agncyCity, String agncyProv, String agncyPostal, String agncyCountry, String agncyPhone, String agncyFax) {
        this.agencyId = agencyId;
        this.agncyAddress = agncyAddress;
        this.agncyCity = agncyCity;
        this.agncyProv = agncyProv;
        this.agncyPostal = agncyPostal;
        this.agncyCountry = agncyCountry;
        this.agncyPhone = agncyPhone;
        this.agncyFax = agncyFax;
    }

    public String getAgncyFax()
    {
        return agncyFax;
    }

    public void setAgncyFax(String agncyFax)
    {
        this.agncyFax = agncyFax;
    }

    public String getAgncyPhone()
    {
        return agncyPhone;
    }

    public void setAgncyPhone(String agncyPhone)
    {
        this.agncyPhone = agncyPhone;
    }

    public String getAgncyCountry()
    {
        return agncyCountry;
    }

    public void setAgncyCountry(String agncyCountry)
    {
        this.agncyCountry = agncyCountry;
    }

    public String getAgncyPostal()
    {
        return agncyPostal;
    }

    public void setAgncyPostal(String agncyPostal)
    {
        this.agncyPostal = agncyPostal;
    }

    public String getAgncyProv()
    {
        return agncyProv;
    }

    public void setAgncyProv(String agncyProv)
    {
        this.agncyProv = agncyProv;
    }

    public String getAgncyCity()
    {
        return agncyCity;
    }

    public void setAgncyCity(String agncyCity)
    {
        this.agncyCity = agncyCity;
    }

    public String getAgncyAddress()
    {
        return agncyAddress;
    }

    public void setAgncyAddress(String agncyAddress)
    {
        this.agncyAddress = agncyAddress;
    }

    public Integer getAgencyId()
    {
        return agencyId;
    }

    public void setAgencyId(Integer id)
    {
        this.agencyId = id;
    }


}