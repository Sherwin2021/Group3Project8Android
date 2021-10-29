package model;


import android.icu.util.Currency;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class Package implements Serializable

{

    private Integer packageId;

    private String pkgName;

    private String pkgStartDate;

    private String pkgEndDate;

    private String pkgDesc;

    private Double pkgBasePrice;

    private Double pkgAgencyCommission;


    public Double getPkgAgencyCommission()
    {
        return pkgAgencyCommission;
    }

    public void setPkgAgencyCommission(Double pkgAgencyCommission)
    {
        this.pkgAgencyCommission = pkgAgencyCommission;
    }

    public Double getPkgBasePrice()
    {
        return pkgBasePrice;
    }

    public void setPkgBasePrice(Double pkgBasePrice)
    {
        this.pkgBasePrice = pkgBasePrice;
    }

    public String getPkgDesc()
    {
        return pkgDesc;
    }

    public void setPkgDesc(String pkgDesc)
    {
        this.pkgDesc = pkgDesc;
    }

    public String getPkgEndDate()
    {
        return pkgEndDate;
    }

    public void setPkgEndDate(String pkgEndDate)
    {
        this.pkgEndDate = pkgEndDate;
    }

    public String  getPkgStartDate()
    {
        return pkgStartDate;
    }

    public void setPkgStartDate(String  pkgStartDate)
    {
        this.pkgStartDate = pkgStartDate;
    }

    public String getPkgName()
    {
        return pkgName;
    }

    public void setPkgName(String pkgName)
    {
        this.pkgName = pkgName;
    }

    public Integer getId()
    {
        return packageId;
    }

    public void setId(Integer id)
    {
        this.packageId = id;
    }

    public Package(Integer packageId, String pkgName, String  pkgStartDate, String  pkgEndDate, String pkgDesc, Double pkgBasePrice, Double pkgAgencyCommission) {
        this.packageId = packageId;
        this.pkgName = pkgName;
        this.pkgStartDate = pkgStartDate;
        this.pkgEndDate = pkgEndDate;
        this.pkgDesc = pkgDesc;
        this.pkgBasePrice = pkgBasePrice;
        this.pkgAgencyCommission = pkgAgencyCommission;
    }
}