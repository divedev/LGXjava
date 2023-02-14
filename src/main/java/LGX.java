import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.DiscordEntity;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.event.Event;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LGX {
    public static void main(String[] args){
        Dotenv dotenv = Dotenv.load();
        final String JAVACORD_TOKEN = dotenv.get("JAVACORD_TOKEN");
        final String OPENAI_TOKEN = dotenv.get("OPENAI_TOKEN");
        OpenAiService service = new OpenAiService(OPENAI_TOKEN);
        DiscordApi api = new DiscordApiBuilder()
                .setToken(JAVACORD_TOKEN)
                .addIntents(Intent.GUILD_MESSAGES, Intent.MESSAGE_CONTENT)
                .login()
                .join();

        api.addMessageCreateListener(event -> {
            if(event.getMessageAuthor().isBotUser()){
                return;
            }

            CompletableFuture<Void> future = CompletableFuture.runAsync(()->{
                respondToChannelMessage(service, event);
            });
        });

//        List<Long> serverIDs = api.getServers().stream().map(DiscordEntity::getId).toList();
    }

    private static void respondToChannelMessage(OpenAiService service, MessageCreateEvent event){
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(event.getMessageContent())
                .maxTokens(200)
                .echo(false)
                .user(event.getMessageAuthor().toString())
                .n(1)
                .build();

        String response = service.createCompletion(completionRequest).getChoices().get(0).getText();

        event.getChannel().sendMessage(response);
    }
}
