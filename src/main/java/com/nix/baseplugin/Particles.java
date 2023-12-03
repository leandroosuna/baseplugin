package com.nix.baseplugin;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.Particle.DustOptions;

public class Particles {
    Main main;
    DustOptions cyanDust;
    public Particles()
    {
        main = Main.getInstance();
        cyanDust = new Particle.DustOptions(Color.fromRGB(0,255,255), 1);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, this::updateParticles , 0, 1);
        
    }
    double spin = 0;
    int particlesPerRing = 30;
    double ringSize = 1.5;
    public void updateParticles()
    {
        double dx, dy, dz, dx2, dz2;
        Location temp = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        
        spin += (2*Math.PI)/particlesPerRing;
        
        dx = ringSize * Math.sin(spin);
        dz = ringSize * Math.cos(spin);
            
        dx2 = ringSize * Math.sin(spin + Math.PI);
        dz2 = ringSize * Math.cos(spin + Math.PI);
        
        dy = (Math.sin(spin * .25) + 1);
        for (SavedLocation sl : Locations.locations)
        {
            Location l = sl.location;
            World w = l.getWorld();
            temp.setWorld(w);
            
            temp.setX(l.getX() + dx);
            temp.setY(l.getY() + dy);
            temp.setZ(l.getZ() + dz);
            w.spawnParticle(Particle.REDSTONE, temp, 1,0,0,0, cyanDust);
            temp.setX(l.getX() + dx2);
            temp.setY(l.getY() + dy);
            temp.setZ(l.getZ() + dz2);
            w.spawnParticle(Particle.REDSTONE, temp, 1,0,0,0, cyanDust);
        }
    }
}
