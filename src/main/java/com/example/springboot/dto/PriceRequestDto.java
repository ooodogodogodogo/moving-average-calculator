package com.example.springboot.dto;

public class PriceRequestDto {
    private Double price;
    public PriceRequestDto() {
    } // default constructor, required by object mapper
    public PriceRequestDto(Double price) {
        this.price = price;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
}
