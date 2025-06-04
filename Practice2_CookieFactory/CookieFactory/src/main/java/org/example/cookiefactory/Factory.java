package org.example.cookiefactory;

public class Factory {
    private int cookiesNum;
    private int materialsForCookies;
    private int boughtCookies;

    //Doc This constructor initializes a new Factory instance with zero cookies, materials, and bought cookies.
    public Factory() {
        cookiesNum = 0;
        materialsForCookies = 0;
        boughtCookies = 0;
    }

    // Getters
    //Doc This method returns the current number of cookies in the factory.
    public int getCookiesNum() {
        return cookiesNum;
    }

    //Doc This method returns the current number of materials available for making cookies.
    public int getMaterialsForCookies() {
        return materialsForCookies;
    }

    //Doc This method returns the total number of cookies that have been bought from the factory.
    public int getBoughtCookies() {
        return boughtCookies;
    }

    //Doc This method attempts to make a cookie, consuming one material if available, and returns true if successful, false otherwise.
    public boolean makeCookies(){
        if (materialsForCookies>0){
            materialsForCookies--;
            cookiesNum++;
            return true;
        }
        else return false;
    }

    //Doc This method increases the number of materials for cookies by one and always returns true.
    public boolean buyMaterialsForCookies(){
        materialsForCookies++;
        return true;
    }

    //Doc This method attempts to buy a cookie, decreasing the cookie count if available, and returns true if successful, false otherwise.
    public boolean buyCookies(){
        if (cookiesNum>0){
            cookiesNum--;
            boughtCookies++;
            return true;
        }
        else return false;
    }
}