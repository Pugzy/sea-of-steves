package tc.oc.seaofsteves;

import co.aikar.commands.BukkitCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Skin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import tc.oc.pgm.api.PGM;
import tc.oc.pgm.events.PlayerPartyChangeEvent;
import tc.oc.pgm.tablist.MatchTabManager;
import tc.oc.pgm.util.tablist.PlayerTabEntry;
import tc.oc.pgm.util.tablist.TabEntry;

public class Main extends JavaPlugin implements Listener {

  private static Main plugin;
  MatchTabManager matchTabManager;

  private BukkitCommandManager commands;

  @Override
  public void onEnable() {
    plugin = this;

    saveDefaultConfig();
    Config.create(getConfig());

    // Setup the command manager and register all commands
    this.commands = new BukkitCommandManager(this);
    commands.registerCommand(new Commands());

    // Register listener
    Bukkit.getServer().getPluginManager().registerEvents(this, this);

    matchTabManager = PGM.get().getMatchTabManager();

    getLogger().info("[SeaOfSteves] SeaOfSteves has been enabled!");
  }

  @Override
  public void onDisable() {
    plugin = null;
    getLogger().info("[SeaOfSteves] SeaOfSteves has been disabled!");
  }

  public static Main get() {
    return plugin;
  }

  @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
  public void onPlayerPartyChange(final PlayerPartyChangeEvent event) {
    if (!Config.get().getEnabled()) return;

    Player player = event.getPlayer().getBukkit();
    Skin skin =
        SkinUtils.getSkin(
            event.getNewParty() != null ? event.getNewParty().getColor() : ChatColor.AQUA);

    player.setSkin(skin);
    NMSHacks.forceSkinChange(player);

    TabEntry playerEntry = matchTabManager.getPlayerEntry(player);
    if (playerEntry instanceof PlayerTabEntry) {
      ((PlayerTabEntry) playerEntry).refresh();
    }
  }
}
