package com.nix.baseplugin;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class HUD {
    Main main;
    public HUD()
    {
        main = Main.getInstance();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, this::updateActionBar , 0, 1);
    }

    void updateActionBar()
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            Block b = p.getLocation().getBlock();
            String str = "XYZ " + b.getX() +" "+ b.getY() +" "+ b.getZ();
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(str).create()); 
        }
    }
}
