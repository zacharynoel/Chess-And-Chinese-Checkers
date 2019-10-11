
import java.awt.Color;
import java.util.Objects;


public class Team {
    public Color c;
    
    Team(){
        this.c = null;
    }
    
    Team(Color setC){
        c = setC;
    }
    
    @Override
    public boolean equals(Object team){
        if(team == this)
            return true;
        
        if(!(team instanceof Team))
            return false;
        
        Team t = (Team) team;
        
        return c == t.c;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.c);
        return hash;
    }
}
