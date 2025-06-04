package org.example.cookieservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cookiefactory.Factory;

import java.io.File;
import java.io.IOException;

public class Service {

    private Factory privateFactory;

    public Service() {
        privateFactory = new Factory();
    }

    //Doc("Returns info for workers about cookies and materials.")
    public String makeInfoForWorker() {
        return "Cookies: " + privateFactory.getCookiesNum() +
                "\nMaterials for cookies: " + privateFactory.getMaterialsForCookies();
    }

    //Doc("Returns info for consumers about cookies bought and left.")
    public String makeInfoForConsumer() {
        return "Bought cookies: " + privateFactory.getBoughtCookies() +
                "\nCookies left: " + privateFactory.getCookiesNum();
    }

    //Doc("Attempts to make cookies. Returns true if successful.")
    public boolean makeCookies() {
        return privateFactory.makeCookies();
    }

    //Doc("Attempts to buy materials. Returns true if successful.")
    public boolean buyMaterials() {
        return privateFactory.buyMaterialsForCookies();
    }

    //Doc("Attempts to buy cookies. Returns true if successful.")
    public boolean buyCookies() {
        return privateFactory.buyCookies();
    }

    //Doc("Exports factory info to a JSON file.")
    public void exportToJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("CookieApp/src/main/resources/org/example/cookieapp/factoryInfo.json"), privateFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
