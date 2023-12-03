package com.nix.baseplugin;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Locations {
    static String locationsFilePath = "plugins\\baseplugin\\saved_locations.json";

    static List<SavedLocation> locations = new ArrayList<SavedLocation>();
   
    public static void Init()
    {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(locationsFilePath)) {
            List<JsonLocation> fromFile = gson.fromJson(reader, new TypeToken<List<JsonLocation>>(){}.getType());
            
            locations = JsonLocation.fromJsonLocation(fromFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean AddLocation(String name, Location l, boolean replace)
    {
        List<SavedLocation> existing = getLocation(name);
        
        Location normalPitch = l.clone();
        normalPitch.setPitch(0);

        if(existing.size() > 0)
        {
            if(!replace)
                return false;
            existing.get(0).location = normalPitch;
            SaveToJson();
            return true;
        }
        locations.add(new SavedLocation(name, normalPitch));
        SaveToJson();
        return true;
    
    }
    public static boolean RemoveLocation(String name)
    {
        List<SavedLocation> existing = getLocation(name);
        if(existing.size() > 0)
        {
            locations.remove(existing.get(0));
            SaveToJson();
            return true;
        }
        return false;

    }

    public static boolean Teleport(Player p, String name)
    {
        List<SavedLocation> existing = getLocation(name);
        if(existing.size() > 0)
        {
            p.teleport(existing.get(0).location);
            return true;
        }
        return false;
    }
    static String GetNames()
    {
        String s = "Ubicaciones guardadas: ";
        for(SavedLocation sl : locations)
        {
            s += sl.name +" "; 
        }
        return s;
    }
    static List<SavedLocation> getLocation(String name)
    {
        return locations.stream().filter(loc -> loc.name.equals(name)).collect(Collectors.toList());
    }
    static void SaveToJson()
    {
        //Main.LOGGER.info("saving to json");
        Gson gson = new Gson();
        List<JsonLocation> jsonLocations = JsonLocation.toJsonLocation(locations);
        //Main.LOGGER.info("converted "+locations.size()+"->"+jsonLocations.size()+" locs");
        try(FileWriter writer = new FileWriter(locationsFilePath))
        {
            gson.toJson(jsonLocations, writer);
            //Main.LOGGER.info("saved");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    static void Retrieve()
    {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(locationsFilePath)) {
            List<JsonLocation> fromFile = gson.fromJson(reader, new TypeToken<List<JsonLocation>>(){}.getType());
            
            for(JsonLocation loc : fromFile)
            {
                Main.LOGGER.info(loc.name + " " + loc.worldName+ " "+ loc.x +" "+ loc.y +" "+ loc.z );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class SavedLocation
{
    String name;
    Location location;
    public SavedLocation(String name, Location location)
    {
        this.name = name;
        this.location = location;
    }
}

class JsonLocation
{
    public String name;
    public String worldName;
    public double x,y,z;

    public JsonLocation(String name, Location l)
    {
        this.name = name;
        worldName = l.getWorld().getName();
        x = l.getX();
        y = l.getY();
        z = l.getZ();
    }
    static List<JsonLocation> toJsonLocation(List<SavedLocation> saved)
    {
        List<JsonLocation> list = new ArrayList<>();
        for(SavedLocation sl : saved)
        {
            list.add(new JsonLocation(sl.name, sl.location));
        }
        return list;
    }
    static List<SavedLocation> fromJsonLocation(List<JsonLocation> saved)
    {
        List<SavedLocation> list = new ArrayList<>();
        for(JsonLocation l : saved)
        {
            list.add(new SavedLocation(l.name, new Location(Bukkit.getWorld(l.worldName), l.x, l.y, l.z)));
        }
        return list;
    }
       
}