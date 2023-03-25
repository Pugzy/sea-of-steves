package tc.oc.seaofsteves;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;

public class Commands extends BaseCommand {

  @CommandAlias("reload")
  @CommandPermission("seaofsteves.reload")
  public void reload(String target, String message) {
    Main.get().reloadConfig();
    Config.create(Main.get().getConfig());
  }
}
