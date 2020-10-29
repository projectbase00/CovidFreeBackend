package com.app.covidfree.service.dto;



public class HashDto {

    private String hash;

    public HashDto ( String hash){
        this.hash = hash;
    }


    /**
     * @return String return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash the hash to set
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

}