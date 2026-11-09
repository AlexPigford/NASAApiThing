import java.util.*;
import java.io.*;
public class Main{
    public static void main(String[] args){
        ArrayList<Asteroid> asts = new ArrayList<Asteroid>();
        AsteroidGetter a = new AsteroidGetter();
        String s = "";

        try{
            s = a.getAsteroids();
            
            asts = a.getAllAsteroids(s);
            Collections.sort(asts);
            for(Asteroid g : asts){
                g.printData();
            }
        }
        catch(Exception e){
            System.out.println("Something went wrong");
            System.exit(1);
        }

    }
}