package com.m1kes.nits.objects;


public class Extension
{
    private String name;
    private String number;

    public Extension() {}

    public Extension(String name, String number)
    {
        this.name = name;
        this.number = number;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNumber()
    {
        return this.number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String toString()
    {
        return "Extension{name='" + this.name + '\'' + ", number='" + this.number + '\'' + '}';
    }
}

