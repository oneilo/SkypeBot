package skypebot.commands;

import expr.Expr;
import expr.Parser;
import expr.SyntaxException;
import org.apache.commons.lang.StringUtils;
import skypebot.wrapper.*;

import java.math.BigDecimal;

/**
 * Created by Kyle on May 17, 2015
 */
public class CCalc extends BotCommand {

	public CCalc(Bot bot) {
		super(bot, "calc", "Calculate an equation");
	}

	@Override
    public String called(BotUser sender, String command, BotMessage chatMessage, BotConversation chat, String[] args) {
		if (args.length != 0) {
			String all = StringUtils.join(args, ' ');
			try {
				Expr expr = Parser.parse(all);
                return all + " = " + new BigDecimal(expr.value()).toPlainString();
			} catch (SyntaxException e) {
                return "Syntax error: " + e.explain();
			} catch (NumberFormatException e) {
                return "Error " + e.getMessage();
            } catch (Exception e) {
                return "Error " + e.getMessage();
            }
		} else {
            return getUsage(command, "expression");
		}
	}
}
