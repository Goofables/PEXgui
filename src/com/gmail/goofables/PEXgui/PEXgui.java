package com.gmail.goofables.PEXgui;

/**
 * PEX GUI created by Goofables on 12/03/2017
 * <p>
 * TODO:
 * Alot
 */

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PEXgui extends JavaPlugin implements Listener {
   private ArrayList<String> permissionList = new ArrayList<>();
   private Map<String, ArrayList<String>> permsPlugins = new HashMap<>();
   
   @Override
   public void onEnable() {
      getServer().getPluginManager().registerEvents(this, this);
      Bukkit.getScheduler().runTaskLater(this, () -> {
         System.out.println("Scanning permissions.");
         for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (!permsPlugins.containsKey(plugin.getName())) permsPlugins.put(plugin.getName(), new ArrayList<>());
            for (Permission perm : plugin.getDescription().getPermissions()) {
               if (!permissionList.contains(perm.getName())) permissionList.add(perm.getName());
               if (!permsPlugins.get(plugin.getName()).contains(perm.getName()))
                  permsPlugins.get(plugin.getName()).add(perm.getName());
            }
         }
         for (Permission perm : getServer().getPluginManager().getPermissions())
            if (!permissionList.contains(perm.getName())) permissionList.add(perm.getName());
         PermissionsEx.getPermissionManager();
         System.out.println("Scanning complete. Found " + permissionList.size() + " permissions.");
      }, 10);
   }
   
   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent e) {
      Bukkit.getScheduler().runTaskLater(this, () -> {
         Player player = e.getPlayer();
         player.addAttachment(this);
         
         for (PermissionAttachmentInfo pai : player.getEffectivePermissions()) {
            if (!permissionList.contains(pai.getPermission())) permissionList.add(pai.getPermission());
         }
      }, 25);
   }
   
   @Override
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      switch (command.getName().toLowerCase()) {
         case "pexgui":
            if (args.length == 0) {
               for (String s : permissionList) {
                  System.out.println(" >" + s);
               }
               for (String key : permsPlugins.keySet()) {
                  System.out.println(key + ":");
                  for (String perm : permsPlugins.get(key)) {
                     System.out.println("  > " + perm);
                  }
               }
            } else switch (args[0].toLowerCase()) {
               case "gui":
                  /* implementation */
                  sender.sendMessage("Error. Not setup yet.");
                  break;
            }
            return true;
      }
      return false;
   }
}

