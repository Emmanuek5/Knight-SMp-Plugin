package com.obsidian.knightsmp.events;

import com.obsidian.knightsmp.inventories.SelectionScreen;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SelectionEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if (event.getClickedInventory() == null) {
            return;
        }
        if (event.getClickedInventory().getHolder() instanceof SelectionScreen) {
            event.setCancelled(true);
          if (event.isLeftClick()){
              Player player = (Player) event.getWhoClicked();
              if (event.getCurrentItem() == null) {
                  return;
              }
              if (event.getCurrentItem().getType() == Material.LIME_STAINED_GLASS_PANE){
                  player.sendMessage("You Have Selected the Accept Button");
                  player.closeInventory();
              }
              if (event.getCurrentItem().getType() == Material.RED_STAINED_GLASS_PANE){
                  player.sendMessage("You Have Selected the Decline Button");
                  player.closeInventory();
              }
              if (event.getCurrentItem().getType() == Material.BOOK){
                  player.sendMessage("Make A Selection, I Dont Have all day");
                  player.closeInventory();
              }
          }

        }
    }
}
