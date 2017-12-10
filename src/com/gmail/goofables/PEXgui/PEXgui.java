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
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PEXgui extends JavaPlugin {
   private ArrayList<String> permissionList = new ArrayList<>();
   private Map<String, ArrayList<String>> permsPlugins = new HashMap<>();
   
   @Override
   public void onEnable() {
      getServer().getPluginManager().registerEvents(new Listeners(this), this);
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
               
               
               case "regen":
                  File file = new File(this.getDataFolder(), "permsList.list");
                  FileWriter fw = null;
                  BufferedWriter bw = null;
                  if (!file.exists()) {
                     try {
                        if (file.createNewFile()) sender.sendMessage("Creating file...");
                        else throw new IOException("Create failed");
                     } catch (IOException e1) {
                        sender.sendMessage("Error! Could not create file!");
                        e1.printStackTrace();
                        return true;
                     }
                  }
                  try {
                     sender.sendMessage("File wrote to " + file.getPath());
                     fw = new FileWriter(file);
                     bw = new BufferedWriter(fw);
                     
                     //##########################
                     //#### Main file output ####
                     //##########################
                     ArrayList<String> tempPerms = (ArrayList<String>)permissionList.clone();
                     for (String key : permsPlugins.keySet()) {
                        bw.write("<>" + key);
                        bw.newLine();
                        for (String perm : permsPlugins.get(key)) {
                           bw.write(perm);
                           bw.newLine();
                           if (tempPerms.contains(perm)) tempPerms.remove(perm);
                        }
                     }
                     bw.write("<>Other");
                     bw.newLine();
                     for (String perm : tempPerms) {
                        bw.write(perm);
                        bw.newLine();
                     }
                     
                     
                  } catch (IOException e) {
                     sender.sendMessage("§4Error! Could not write to file.");
                     e.printStackTrace();
                  } finally {
                     try {
                        if (bw != null) bw.close();
                        if (fw != null) fw.close();
                     } catch (IOException ex) {
                        sender.sendMessage("§4Error! Could not save file.");
                        ex.printStackTrace();
                        return true;
                     }
                  }
                  sender.sendMessage("File write finished successfully!");
                  break;
               
               
            }
            return true;
      }
      return false;
   }
}

