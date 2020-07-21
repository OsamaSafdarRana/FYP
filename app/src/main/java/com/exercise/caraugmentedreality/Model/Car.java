package com.exercise.caraugmentedreality.Model;

public class Car {
    private String Manufacturer, Model, Variant, Year, RegistrationNumber,EngineCapacity;

    public Car() {
    }

    public Car(String manufacturer, String model, String variant, String year, String registrationNumber,String engineCapacity) {
        Manufacturer = manufacturer;
        Model = model;
        Variant = variant;
        Year = year;
        RegistrationNumber = registrationNumber;
        EngineCapacity = engineCapacity;
    }

    public String getEngineCapacity() {
        return EngineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        EngineCapacity = engineCapacity;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getVariant() {
        return Variant;
    }

    public void setVariant(String variant) {
        Variant = variant;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getRegistrationNumber() {
        return RegistrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        RegistrationNumber = registrationNumber;
    }
}
