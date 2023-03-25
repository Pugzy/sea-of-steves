package tc.oc.seaofsteves;

import org.bukkit.configuration.Configuration;

public class Config {

  private static Config config;
  private boolean enabled;

  public Config(Configuration config) {
    load(config);
  }

  public static void create(Configuration config) {
    Config.config = new Config(config);
  }

  public static Config get() {
    return config;
  }

  public void load(Configuration config) {
    this.enabled = config.getBoolean("enabled", true);
  }

  public boolean getEnabled() {
    return enabled;
  }
}
