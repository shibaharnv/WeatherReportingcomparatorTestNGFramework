package com.restassuredapi;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.Map;
//import static pages.NdtvHomePage.CITY_NAME;

import pages.NdtvHomePage;
import pages.NdtvHomePage.*;


public class WeatherApiTest {

    public int apiTemperature;
    String cityNameValue = NdtvHomePage.CITY_NAME;

    @Test
    public void weatherApiTest(){


        //setProxymethod();
        String response = RestAssured.given().log().all()
                .queryParam("q", ""+cityNameValue+"")
                .queryParam("appid", "7fe67bf08c80ded756e598d6f8fedaea")
                .queryParam("units", "metric")
                .when().get("https://api.openweathermap.org/data/2.5/weather").then()
                .log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println("response "+response);
        JsonPath js = new JsonPath(response);
        Map<Object, Object> main = js.getMap("main");

        String tempdatatype= main.get("temp").getClass().getSimpleName();

        System.out.println("tempdatatype " +tempdatatype);

        if(tempdatatype.equalsIgnoreCase("Integer"))
        {
            //Float tempvalue = main.get("temp");
            apiTemperature= (int) main.get("temp");
            System.out.println("apiTemperature integer value " +apiTemperature);
        }
        else
        {

            Float tempvalue = (Float) main.get("temp");
            apiTemperature=Math.round(tempvalue);
            System.out.println("apiTemperature integer value from float " +apiTemperature);

        }


    }

    @Test(dependsOnMethods = "weatherApiTest")
    public void comparatorTest()
    {
       int tempindegreesactualvalue =27;

       int differenceTemp=tempindegreesactualvalue-apiTemperature;

        System.out.println("differenceTemp" +differenceTemp);


    }



    public void setProxymethod() {
        System.setProperty("https.proxyHost", "proxy-chain.intel.com");
        System.setProperty("https.proxyPort", "912");
        System.setProperty("http.proxyPort", "911");
        //RestAssured.useRelaxedHTTPSValidation();
    }
}