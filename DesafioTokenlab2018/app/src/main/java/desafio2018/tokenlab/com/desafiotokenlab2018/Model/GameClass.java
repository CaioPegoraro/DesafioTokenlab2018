package desafio2018.tokenlab.com.desafiotokenlab2018.Model;

import java.util.List;

/**
 * Created by caio on 27/03/2018.
 */

public class GameClass {

    //Atributos
    private int id;
    private String name;
    private String image;
    private String release_date;
    private String trailer;
    private List<String> platforms;

    //Metodos GET
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getImage(){
        return image;
    }
    public String getRelease_date(){
        return release_date;
    }
    public String getTrailer(){
        return trailer;
    }
    public List<String> getPlatforms(){
        return platforms;
    }

    //Metodos SET
    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setImage(String image){
        this.image=image;
    }
    public void setRelease_date(String release_date){
        this.release_date=release_date;
    }
    public void setTrailer(String trailer){
        this.trailer=trailer;
    }
    public void setPlatforms(List<String> platforms){
        this.platforms = platforms;
    }

}
