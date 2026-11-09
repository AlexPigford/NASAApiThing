class Asteroid implements Comparable<Asteroid>{
    String id, name, nameL;
    boolean danger;
    boolean good;
    public Asteroid(String id, String name, String nameL, boolean danger){
        this.id = id;
        this.name = name;
        this.nameL = nameL;
        this.danger = danger;
        good = id != null && name != null;
    }

    public Asteroid(AsteroidGetter a, String raw) throws Exception{
        
            id = a.getNextId(raw);
            name = a.getNextName(raw);
            nameL = a.getNextNameLimited(raw);
            danger = a.getNextDanger(raw);
            good = !id.equals("Reached end") && !name.equals("Reached end") && id != null && name != null;
    }
    
    public String toString(){
        return id;
    }
    
    public void printData(){
        System.out.println("Name: " + name);
        System.out.println("Is Dangerous: " + danger);
        System.out.println("Id: " + id);
    }
    
    public int compareTo(Asteroid a){
        if(nameL.equals(a.nameL)){
            return name.compareTo(a.nameL);
        }
        return nameL.compareTo(a.nameL);
    }
}