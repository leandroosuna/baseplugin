package com.nix.baseplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("bp"))
        {
            // Main.LOGGER.info("Bp commands online");
            if(sender instanceof Player){
                Player p =(Player)sender; 
                
                if(args.length >0){
                    switch(args[0])
                    {
                        case "recover":
                            p.setHealth(20);
                            p.setFoodLevel(20);
                            break;
                        case "save":
                            if(args.length > 3){
                                p.sendMessage("error. uso: \n  Ver ubicaciones: /bp save\n  Guardar ubicacion: /bp save [nombre]\n  Sobreescribir: /bp save [nombre] true");
                                return true;
                            }
                            if(args.length == 1)
                            {
                                p.sendMessage(Locations.GetNames());
                            }
                            if(args.length == 2)
                            {
                                for(Player op : Bukkit.getOnlinePlayers())
                                {
                                    if(op.getName().equals(args[1])){
                                        p.sendMessage("No podes guardar ubicaciones con nombres de jugadores");
                                        return true;
                                    }
                                }

                                if(Locations.AddLocation(args[1], p.getLocation(), false))
                                    p.sendMessage("guardada ubicacion con nombre "+args[1]);
                                else
                                    p.sendMessage("cuchame, ya existe "+args[1]+", si queres reemplazar usa /bp save [nombre] true");
                            }
                            else if(args.length == 3)
                            {
                                boolean replace = Boolean.valueOf(args[2]);
                                
                                if(replace)
                                {
                                    if(Locations.AddLocation(args[1], p.getLocation(), true))
                                        p.sendMessage("reemplazada ubicacion con nombre "+args[1]);
                                }
                                else
                                    p.sendMessage("cuchame, ya existe "+args[1]+", si queres reemplazar usa /bp save [nombre] true");
                            }
                            break;
                        case "remove":
                            if(args.length != 2){
                                p.sendMessage("error. uso: /bp remove [nombre]");
                                return true;
                            }
                            if(Locations.RemoveLocation(args[1]))
                                p.sendMessage(args[1] + " removida");
                            else
                                p.sendMessage(args[1] + " no encontrada");
                            break;
                        case "tp":
                            if(args.length != 2){
                                p.sendMessage("error. uso: /bp tp [nombre]");
                                return true;
                            }
                            boolean playerFound = false;
                            for(Player op : Bukkit.getOnlinePlayers())
                            {
                                if(op.getName().equals(args[1])){
                                    Location l = op.getLocation().clone();
                                    l.setPitch(0);
                                    p.teleport(l);
                                    playerFound = true;
                                    p.sendMessage("yendo no, llegando con "+args[1]);
                                }
                            }
                            if(!playerFound)
                            {
                                if(Locations.Teleport(p, args[1]))
                                    p.sendMessage("yendo no, llegando a "+args[1]);
                                else
                                    p.sendMessage("que flasheas, no existe "+args[1]+" bro");
                            }
                            break;
                        
                    }
                }
                
                
                
                
            }
        }

        return true;
    }
}
