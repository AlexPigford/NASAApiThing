import java.util.*;
import java.io.*;
import java.net.*;
import java.net.http.*;
public class AsteroidGetter{
    HttpClient c;
    String apiKey;
    int cur;
    public AsteroidGetter(){
        apiKey = "Get your own API key";
        c = HttpClient.newHttpClient();
        cur = 0;
    }

    public int statusCode(HttpResponse r) throws Exception{
        return r.statusCode();
    }

    public String getAsteroids() throws Exception{
        String temp = "https://api.nasa.gov/neo/rest/v1/neo/browse?api_key=" + apiKey;
        HttpRequest r = HttpRequest.newBuilder()
            .uri(URI.create(temp))
            .GET()
            .build();
        HttpResponse<String> res = c.send(r,HttpResponse.BodyHandlers.ofString());

        try{
            int stat = statusCode(res);
            if(stat != 200){
                System.out.println("Not good status code: " + stat);
                System.exit(0);
            }

        }
        catch(Exception e){
            System.out.println("Something went wrong");
            System.exit(1);
        }
        return res.body();
    }

    public void setCur(int i){
        cur = i;
    }

    public void resetCur(){
        cur = 0;
    }

    public String getNextId() throws Exception{
        String raw = getAsteroids();
        for(int i = cur;i<raw.length()-4;i++){
            if(raw.substring(i,i+5).equals("\"id\":")){
                i +=6;
                cur=i+7;
                int end = 0;
                for(int j = i;true;j++){
                    if(raw.charAt(j) == '"'){
                        end = j;
                        break;
                    }
                }
                cur = end+1;
                return raw.substring(i,end);
            }
        }
        
        return "Reached end";
    }

    public String getNextId(String raw) throws Exception{

        for(int i = cur;i<raw.length()-4;i++){
            if(raw.substring(i,i+5).equals("\"id\":")){
                i +=6;
                cur=i+7;
                int end = 0;
                for(int j = i;true;j++){
                    if(raw.charAt(j) == '"'){
                        end = j;
                        break;
                    }
                }
                cur = end+1;
                
                return raw.substring(i,end);
            }
        }
        return "Reached end";
    }

    public String getNextName(String raw){
        for(int i = cur;i<raw.length()-6;i++){
            if(raw.substring(i,i+7).equals("\"name\":")){
                i +=8;
                int end = 0;
                for(int j = i;true;j++){
                    if(raw.charAt(j) == '"'){
                        end = j;
                        break;
                    }
                }
                cur = end+1;
                
                return raw.substring(i,end);
            }
        }
        return "Reached end";
    }

    public String getNextName() throws Exception{
        return getNextName(getAsteroids());
    }
    
    public String getNextNameLimited(String raw){
        for(int i = cur;i<raw.length()-14;i++){
            if(raw.substring(i,i+15).equals("\"name_limited\":")){
                i +=16;
                int end = 0;
                for(int j = i;true;j++){
                    if(raw.charAt(j) == '"'){
                        end = j;
                        break;
                    }
                }
                cur = end+1;
                
                return raw.substring(i,end);
            }
        }
        return "Reached end";
    }
    
    public String getNextNameLimited() throws Exception{
        return getNextNameLimited(getAsteroids());
    }
    
    public boolean getNextDanger(String raw){
        for(int i = cur;i<raw.length()-"\"is_potentially_hazardous_asteroid\":".length()+1;i++){
            if(raw.substring(i,i+"\"is_potentially_hazardous_asteroid\":".length()).equals("\"is_potentially_hazardous_asteroid\":")){
                i +="is_potentially_hazardous_asteroid".length()+3;
                cur=i+5;
                
                if(raw.substring(i,i+4).equals("true")){
                    
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }

    public boolean getNextDanger() throws Exception{
        return getNextDanger(getAsteroids());
    }

    public Asteroid getNextAsteroid(String raw) throws Exception{
        return new Asteroid(this, raw);
    }
    
    public ArrayList<Asteroid> getAllAsteroids(String raw) throws Exception{
        ArrayList<Asteroid> as = new ArrayList<Asteroid>();
        Asteroid n = getNextAsteroid(raw);
        while(n.good == true){
            as.add(n);
            n = getNextAsteroid(raw);
        }return as;
    }
}