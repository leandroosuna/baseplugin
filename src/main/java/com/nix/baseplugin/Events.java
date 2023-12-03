package com.nix.baseplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Events implements Listener{
    Main main;
    public Events()
    {
        main = Main.getInstance();
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        e.setJoinMessage(null);
        p.sendMessage(ChatColor.AQUA + "Holis "+p.getDisplayName());
        for(Player other : Bukkit.getOnlinePlayers())
        {
            if(!other.equals(p))
            {
                other.sendMessage(ChatColor.AQUA + "Se unio "+p.getDisplayName());
            }
        }
    }
    
}
