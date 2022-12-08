import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.DiscordEntity;
import org.javacord.api.entity.intent.Intent;

import java.util.List;

public class LGX {
    public static void main(String[] args){
        Dotenv dotenv = Dotenv.load();
        final String TOKEN = dotenv.get("TOKEN");

        DiscordApi api = new DiscordApiBuilder()
                .setToken(TOKEN)
                .addIntents(Intent.GUILD_MESSAGES, Intent.MESSAGE_CONTENT)
                .login()
                .join();

        api.addMessageCreateListener(event -> {
            // react to message
        });

        List<Long> serverIDs = api.getServers().stream().map(DiscordEntity::getId).toList();
    }
}
