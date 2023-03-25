package tc.oc.seaofsteves;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutRespawn;
import net.minecraft.server.v1_8_R3.WorldSettings;
import net.minecraft.server.v1_8_R3.WorldType;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class NMSHacks {

  static void sendPacket(Player bukkitPlayer, Object packet) {
    if (bukkitPlayer.isOnline()) {
      EntityPlayer nmsPlayer = ((CraftPlayer) bukkitPlayer).getHandle();
      nmsPlayer.playerConnection.sendPacket((Packet) packet);
    }
  }

  static void forceSkinChange(Player player) {
    PacketPlayOutRespawn respawnPacket =
        new PacketPlayOutRespawn(
            player.getWorld().getEnvironment().getId(),
            EnumDifficulty.getById(player.getWorld().getDifficulty().getValue()),
            WorldType.getType(player.getWorld().getWorldType().getName()),
            WorldSettings.EnumGamemode.getById(player.getGameMode().getValue()));

    sendPacket(player, respawnPacket);

    Location location = player.getLocation().clone();
    Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> set = new HashSet<>();
    PacketPlayOutPosition packetPlayOutPosition =
        new PacketPlayOutPosition(
            location.getX(),
            location.getY(),
            location.getZ(),
            location.getYaw(),
            location.getPitch(),
            set);

    sendPacket(player, packetPlayOutPosition);

    player.setWalkSpeed(player.getWalkSpeed());
    player.setExp(player.getExp());

    player.updateInventory();
    PlayerInventory inventory = player.getInventory();
    inventory.setHeldItemSlot(inventory.getHeldItemSlot());
  }
}
