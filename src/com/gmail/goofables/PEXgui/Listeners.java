package com.gmail.goofables.PEXgui;

/**
 * PEX GUI created by Goofables on 12/03/2017
 * <p>
 * TODO:
 * Alot
 */

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Listeners implements Listener {
   Plugin plugin;
   private ArrayList<String> permissionList = new ArrayList<>();
   private Map<String, ArrayList<String>> permsPlugins = new HashMap<>();
   
   public Listeners(Plugin plugin) {
      this.plugin = plugin;
   }
   
   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent e) {
      Bukkit.getScheduler().runTaskLater(plugin, () -> {
         Player player = e.getPlayer();
         player.addAttachment(plugin);
         
         for (PermissionAttachmentInfo pai : player.getEffectivePermissions()) {
            if (!permissionList.contains(pai.getPermission())) permissionList.add(pai.getPermission());
         }
         
         PermissionUser user = PermissionsEx.getUser(player);
         Map<String, List<String>> perms = user.getAllPermissions();
         for (String key : perms.keySet()) {
            perms.get(key);
         }
      }, 25);
   }
   
}

