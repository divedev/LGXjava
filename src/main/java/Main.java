import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.intent.Intent;

public class Main {
    public static void main(String[] args){
        Dotenv dotenv = Dotenv.load();
        final String TOKEN = dotenv.get("TOKEN");

        DiscordApi api = new DiscordApiBuilder()
                .setToken(TOKEN)
                .addIntents(Intent.GUILD_MESSAGES)
                .addIntents(Intent.MESSAGE_CONTENT)
                .login().join();
    }
}
