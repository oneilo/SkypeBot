package skypebot.commands;

import bsh.EvalError;
import bsh.Interpreter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import skypebot.permissions.Permission;
import skypebot.wrapper.*;

import java.io.*;

/**
 * Created by Kyle on 9/21/2015.
 */
public class CJava extends BotCommand {
    
    private Interpreter bsh;
    private ByteArrayOutputStream baos;
    
    public CJava(Bot bot) {
        super(bot, "java", "Run java", Permission.OWNER);
        baos = new ByteArrayOutputStream();
        bsh = new Interpreter();
        try {
            bsh.set("bot", botHost.getMain());
        } catch (EvalError evalError) {
            evalError.printStackTrace();
        }
    }
    
    @Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
        if (sender.getUsername().equalsIgnoreCase("rulingkyle1496")) {
            if (args.length != 0) {
                try {
                    String preappend = "import *;";
                    String result = bsh.eval(preappend + StringUtils.join(args, ' ')) + "";
                    String content = baos.toString("UTF-8");
                    return "Execution complete " + (content.isEmpty() ? "(Result)" : "(Content)") + ":\n" + (content.isEmpty() ? result :
                            content);
                } catch (EvalError evalError) {
                    evalError.printStackTrace();
                    String error = ToStringBuilder.reflectionToString(evalError.getStackTrace());
                    error = error.substring(1, error.length() - 1).replace(",", "\n");
                    return evalError.getMessage() + "\n" + error;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return e.getMessage();
                }
            } else {
                return "Usage: " + command + " [code]";
            }
        } else {
            return "Only the bot owner can run this command!";
        }
    }
}
