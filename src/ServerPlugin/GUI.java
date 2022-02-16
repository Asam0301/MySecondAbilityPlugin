package ServerPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import Character.*;

public class GUI implements Listener {
    

    private main plugin;

    private static Object GUI;
    public static Inventory SelectGUI = Bukkit.createInventory((InventoryHolder)GUI,9, "캐릭터 선택 창");

    public static void GUI(Player player){
        SelectGUI.setItem(0,Thor.Icon);
        SelectGUI.setItem(1,GodBow.Icon);
        SelectGUI.setItem(2,Resister.Icon);
        SelectGUI.setItem(3,Boomer.Icon);


        player.openInventory(SelectGUI);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(e.getInventory() == null) return;
        if(e.getWhoClicked() instanceof Player){
            Player player = (Player) e.getWhoClicked();
            if(e.getView().getTitle() == null) return;
            if(e.getCurrentItem() == null) return;
            if(e.getView().getTitle().equalsIgnoreCase("캐릭터 선택 창")){
                e.setCancelled(true);
                player.closeInventory();
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA +"천둥의 신 "+ChatColor.WHITE+"토르")){
                    Thor.LetMeThor(player);
                }
                else if(e.getCurrentItem().getType() == Material.BOW){
                    GodBow.LetMeGodBow(player);
                }
                else if(e.getCurrentItem().getType() == Material.NETHERITE_CHESTPLATE){
                    Resister.LetMeResister(player);
                }
                else if(e.getCurrentItem().getType() == Material.TNT){
                    Boomer.LetMeBoomer(player);
                }
            }
        }
    }
}
